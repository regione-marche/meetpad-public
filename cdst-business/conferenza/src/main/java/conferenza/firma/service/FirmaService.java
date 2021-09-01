package conferenza.firma.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import conferenza.DTO.RispostaBoolean;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.adapter.FirmaAdapter;
import conferenza.firma.model.bean.Firma;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.RegistroDocumento;
import conferenza.model.Utente;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.UtenteRepository;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;


/**
 * 
 * FirmaService utilizza il servizio FirmaAdapter per andare verso il livello adapter..
 * 
 * 
 * @author guideluc
 *
 */
@Service
public class FirmaService {

	private static final Logger logger = LoggerFactory.getLogger(FirmaService.class.getName());
	
	public static String TIPOFIRMA_FS="FIRMAFS";
	public static String SIGNED_FILE_EXTENSION="p7m";
	public static String SIGNED_PADES_FILE_EXTENSION="pdf";
	
	@Autowired
	FirmaAdapter firmaAdapterService;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	RegistroFirmaSessionSignerService registroFirmaSessionSignerService;
	
	@Autowired	
	ConferenzaRepository conferenzaRepository;
	
	@Autowired	
	UtenteRepository utenteRepository;
	
	/**
	 * 
	 * @param codice
	 * @return
	 */
	public  Integer getTipoFirma(String codice) {
		if(TIPOFIRMA_FS.equals(codice))
			return new Integer(1);		
		return null;
	}
	
	/**
	 * @TODO : implement
	 * [REQUISITO]: solo i responsabili possono caricare il file in prima firma.
	 * 
	 * @return
	 * @throws IOException 
	 */
	public boolean isResponsabile(FirmaDTO firma) throws IOException {
		if (firma.getIdConferenza() != null) {
			Conferenza conferenza = conferenzaRepository.findByIdConferenza(firma.getIdConferenza());
			if (conferenza != null) {
				return utenteService.isResponsabileConferenza(conferenza) || utenteService.isCreatoreConferenza(conferenza);
			}
		}
		throw new IOException("Conferenza non indicata o non valida");
	}
	
	private boolean isDelegato(Integer idConferenza) {
		boolean isDelegato = false;
		RispostaBoolean delagate = utenteService.isUtenteDelegatoConferenza(idConferenza);
		isDelegato = delagate.getData();
		return isDelegato;
	}
	/**
	 * 
	 * aggiunge un partecipante alla sessione di firma..
	 * registrazione sessione/partecipante
	 * 
	 * Se id documento è null o
	 * Se idsigner è null o
	 * Se la sesssione non è derivabile gg
	 * Allora vine generata un'eccezione...
	 * 
	 * @return
	 * @throws IOException 
	 */
	public boolean addParticipantInSigningSession(FirmaDTO firma, Integer idSigner) 
			throws IOException {
		Integer idUser=getAutenticatedUser();	
		firma.setIdUser(idUser);
		registroFirmaSessionSignerService.addSignerInSession(firma, idSigner);		
		return true;
	}
	
	/*
	 * aggiunge la lista di partecipanti alla sessione di firma a partire dalla lista di firmatari accreditati
	 */
	public void addPartecipanteInSigningSessionByAccreditamento(FirmaDTO firma, List<Accreditamento> firmatari) {
		
		//inserimento dei firmatari nella sessione di firma
		firmatari.forEach( (Accreditamento firmatario) -> {
			utenteRepository.findByCodiceFiscaleIgnoreCase(firmatario.getPersona().getCodiceFiscale()).ifPresent( utente -> {
				
				try {
					this.addParticipantInSigningSession(firma, utente.getIdUtente());
				} catch (IOException e) {
					e.printStackTrace();
				}
					
			});		
		});
		
	}

	/**
	//* 1 - Viene creata la session di firma
	//* 2 - Viene preparata la firma: creata la riga su "registroFirmaAdapter"	
	 * @param firma
	 * @throws Exception 
	 */	
	public FirmaDTO inizializzaSessioneDiFirma(FirmaDTO firma) throws Exception {	
		//inizializza l'utente authorizzato
		Integer idUser=getAutenticatedUser();	
		firma.setIdUser(idUser);
		
		//[CASO D'USO] - inizia nuova sessione di firma solo il responsabile
		if(!isResponsabile(firma)) {
			if(!isDelegato(firma.getIdConferenza())) 
				throw new IOException("Solo il responsabile può caricare il documento");			
		}

		if(firma.getIdDocumento() == null) {
			throw new IOException("L'id del doucmento non è presente");	
		}

		//set crc
		String crc= FirmaAdapter.getCRC(firma.getFile());
	    firma.setCrc(crc);
	    
		RegistroDocumento registro=registroDocumentoService.getRegistroDocumentoByIdDocumento(firma.getIdDocumento());
		firmaAdapterService.initializeSigningsessionRegistroFirmaAdapter(firma, registro);

		//inserisci responsabile come sessionSigner
		registroFirmaSessionSignerService.addSignerInSession(firma, firma.getIdUser());
		
		return firma;
	}
	
	/**
	//* 1 - Viene preparata la firma: creata la riga su "registroFirmaAdapter"
	//* 2 - Viene passato il file al sistema esterno di firma		
	 * @param firma
	 * @throws IOException 
	 */	
	public FirmaDTO doActionLock(FirmaDTO firma) throws IOException {	
		//inizializza l'utente authorizzato
		Integer idUser=getAutenticatedUser();	
		firma.setIdUser(idUser);
		return firmaAdapterService.doActionSendFileToFirma(firma);
	}
	
	/**
	 * 1 - Ottiene  il file firmato dal servizio di firma esterno
	 * 2 - storicizza il file firmato 
	 * @param firma
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */	
	public FirmaDTO doActionUnlck(FirmaDTO firma) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		//inizializza l'utente authorizzato
		Integer idUser=getAutenticatedUser();	
		firma.setIdUser(idUser);
		return firmaAdapterService.doActionPostFirma(firma);
	}
	
	public Resource getResourceFirma(FirmaDTO firma) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		//inizializza l'utente authorizzato
		Integer idUser=getAutenticatedUser();	
		firma.setIdUser(idUser);
		return firmaAdapterService.doActionRetriveResourceFilePostFirma(firma);
	}	
	
	/**
	 * Servizio per la cancellazione dell'intera sessioen di Firma
	 * @param firma
	 * @return
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public FirmaDTO doActionCancelSigningSession(FirmaDTO firma) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {				
		//inizializza l'utente authorizzato
		Integer idUser=getAutenticatedUser();	
		firma.setIdUser(idUser);
		String sessioneFirma=firma.getSessioneFirma();
		if(sessioneFirma==null) {				
			if(firma.getIdDocumento()==null )
				throw new IOException("Non è possibile cancellare una sessione di firma senza fornire l'id Documento o la sessione di firma");
			//recupera la sessione di firma..se non è fornita..
			sessioneFirma=getLastSigningSessionForIdDoc(firma);
			firma.setSessioneFirma(sessioneFirma);
		}
		return firmaAdapterService.cancel(firma);
	}
		
	/**
	 * 
	 * @param idDocument
	 * @return
	 */
	public String getLastCRCForUserDoc(Integer idDocument) {
		Integer idUser=getAutenticatedUser();				
		return firmaAdapterService.getLastCRCForUserDoc(idUser,idDocument);		
	}

	public Integer getDocIdByDownloadToken(String downloadToken) {
		return firmaAdapterService.getDocIdByDownloadToken( downloadToken);
		
	}
	
	public List<Firma> getListFirmaByConference(Integer  idConferenza){
		return  firmaAdapterService.getListFirmaByConference(idConferenza);
	}
	
	//======================================================================
	//Private metod!
	//======================================================================
	/**
	 * inizializza l'utente authorizzato
	 * @return
	 */
	protected Integer getAutenticatedUser() {
		Utente utente= utenteService.getAuthenticatedUser();
		return utente.getIdUtente();				
	}  	
	
	/**
	 * 
	 * @param firma
	 * @return
	 */
	private String getLastSigningSessionForIdDoc(FirmaDTO firma) {
		return  firmaAdapterService.getLastSigningSessionForIdDoc(firma);		
	}	
	
	/**
	 * 
	 * @param firma
	 * @return
	 */
	public String getLastSigningSession(FirmaDTO firma) {
		return  firmaAdapterService.getLastSigningSessionForIdDoc(firma);		
	}	
}
