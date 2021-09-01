package conferenza.firma.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.DTO.DocumentoFileDTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.repository.RegistroFirmaAdapterRepository;
import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.service.DocumentoService;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;

/**
 * 
 * @author guideluc
 *
 */
@Service
public class FirmaSemplificataService extends FirmaService{
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	RegistroFirmaSessionSignerService registroFirmaSessionSignerService;
	
	@Autowired	
	UtenteService utenteService;
		
	@Autowired	
	RegistroFirmaAdapterRepository registroFirmaAdapterRepository;

	public static String TIPOFIRMA_FSSEMPLIFICATA="FIRMAFSSEMPLIFICATA";
			
	/**
	 * 
	 * @param firma - idDocumento o sessione di firma devono essere null..
	 * @return
	 */
	public boolean isSigningSessionClosed(FirmaDTO firma) {
		return registroFirmaSessionSignerService.isClosableSession(firma);		
	}
			
	//===================================================================================
	// Nuovi Metodi FirmaSemplificataService END
	//===================================================================================	
	
	//===================================================================================
	// OVERRIDE - BEGIN
	//===================================================================================	
	@Override
	public Integer getTipoFirma(String codice) {
		if(TIPOFIRMA_FSSEMPLIFICATA.equals(codice))
			return new Integer(2);
		if(TIPOFIRMA_FS.equals(codice))
			return new Integer(1);		
		return null;
	}

	
	@Override
	public FirmaDTO doActionLock(FirmaDTO firma) throws IOException {	
		//inizializza l'utente authorizzato
		Integer idUser=super.getAutenticatedUser();	
		firma.setIdUser(idUser);
		
		//[VERIRIFCA] - se l'elenco dei firmatari è chiuso si impedisce il LOCK!
		boolean isClosable=registroFirmaSessionSignerService.isClosableSession(firma);
		if(isClosable)
			throw new IOException("Tentativo di lockare una risorsa per cui hanno firmato tutti ");
		
		return firmaAdapterService.doActionSendFileToFirma(firma);
	}
	
	/**
	 * Se esiste la sessioe di firma..
	 * 1 - Ottiene  il file firmato dal servizio di firma esterno
	 * 2 - storicizza il file firmato
	 * 
	 * Se NON esiste la sessioe di firma
	 * ed
	 * Il servizio è richiesto da un responsabile..
	 * Carica il file ed inizia la sessione di Frima.. 
	 *  
	 * @param firma
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */	
	@Override
	public FirmaDTO doActionUnlck(FirmaDTO firma) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		//inizializza l'utente authorizzato
		Integer idUser=super.getAutenticatedUser();	
		firma.setIdUser(idUser);
							
		return firmaAdapterService.doActionPostFirma(firma);
	}	
	
}
