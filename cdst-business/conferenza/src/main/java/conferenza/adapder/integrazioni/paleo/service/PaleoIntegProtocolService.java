package conferenza.adapder.integrazioni.paleo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.stereotype.Service;

import conferenza.DTO.AccreditamentoDTO;
import conferenza.DTO.EventoFileDTO;
import conferenza.DTO.RispostaJson;
import conferenza.adapder.documentale.service.DocumentProtocolService;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAdapterDTO;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoClientConfiguration;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryFilter;
import conferenza.adapder.integrazioni.paleo.repository.PaleoRegistryAdapterRepository;
import conferenza.adapder.integrazioni.paleo.repository.PaleoRegistryFilterRepository;
import conferenza.builder.DocumentoBuilder;
import conferenza.mail.JavaMailSenderConfigurator;
import conferenza.mail.MailContentBuilder;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.EmailPec;
import conferenza.model.Evento;
import conferenza.model.Mailer;
import conferenza.model.ModificaData;
import conferenza.model.Partecipante;
import conferenza.model.RegistroDocumento;
import conferenza.model.TipoEvento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.model.ObserverRegistryAudit;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.protocollo.model.Protocollo;
import conferenza.protocollo.service.ObserverRegistryListenerService;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EmailPecRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.MailerRepository;
import conferenza.repository.ModificaDataRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.service.DocumentoService;
import conferenza.service.EventoService;
import conferenza.util.DbConst;
import it.marche.regione.paleo.services.Corrispondente;
import it.marche.regione.paleo.services.DatiCorrispondente;
import it.marche.regione.paleo.services.RespDocumento;
import it.marche.regione.paleo.services.RespProtocollo;

@Service
public abstract class PaleoIntegProtocolService extends DocumentProtocolService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PaleoIntegProtocolService.class);
	
	@Autowired
	PaleoAdapterService paleoAdapterClient;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;	
	
	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	ObserverRegistryListenerService observerRegistryListenerService;
	
	@Autowired
	PaleoRegistryAdapterRepository paleoRegistryAdapterRepository;
	
	@Autowired
	TipoEventoRepository tipoEventoRepository;
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Autowired
	PaleoRegistryFilterRepository paleoRegistryFilterRepository;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	EventoService eventoService;

	@Autowired
	DocumentoBuilder documentoBuilder;
	
	@Autowired
	MailerRepository mailerRepository;
	
	@Autowired 
	PaleoClientConfiguration paleoConfigurator;
	
	@Autowired
	MailContentBuilder mailbuilder;

	@Autowired
	JavaMailSenderConfigurator mailSenderConfigurator;
	
	@Autowired
	EmailPecRepository emailPecRepository;

	@Autowired
	PartecipanteRepository partecipanteRepository;
	
	@Autowired
	ModificaDataRepository modificaDataRepo;

	private static String CODICE_SCRITTURA_DOCUMENTI_INTERNI_PALEO="SCRITTURA-DOCUMENTI-INTERNI-PALEO";
	
	
	protected boolean skipMeetpadMailDispatch = false;
	
	
	/**
	 * 
	 * @return
	 * @throws SOAPException 
	 * @throws IOException 
	 * @throws ServiceException 
	 */
	protected abstract ResponseEntity<PaleoDocumentAdapterDTO> doUploadCustomProtocolSingleFile(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, String idTipoEvento) throws ServiceException, IOException, SOAPException;
	
	//Protocollo in partenza
	protected PaleoRegistryAdapter saveResponseToRegitryAdapter(RegistroDocumento registroDocumento,RespProtocollo respObj) {
		int iDocNumber=respObj.getDocNumber();
		
		PaleoRegistryAdapter toSaveObj=new PaleoRegistryAdapter();
		toSaveObj.setId(null);
		toSaveObj.setFk_registroDocummenti(registroDocumento.getId());
		toSaveObj.setIdPaleoDoc(new Integer(iDocNumber));
		
		//toSaveObj.setPaleoSegnaturaProtocollo(respObj.getSegnatura()); non serve la signature ma il numero del documento
		toSaveObj.setPaleoSegnaturaProtocollo(respObj.getNumero());
		toSaveObj.setIdPaleoNumeroDoc(new Integer(iDocNumber));
		toSaveObj.setPaleoOggetto(respObj.getOggetto());
		LOGGER.debug(" obj number "+respObj.getNumero());
		toSaveObj.setPaleoProtocolloData(PaleoAdapterService.getStringFromCalendar(respObj.getDataProtocollazione()));
		toSaveObj=paleoRegistryAdapterRepository.save(toSaveObj);
		return toSaveObj;
	}
	
	//Protocollo in partenza PaleoGiunta
	protected PaleoRegistryAdapter saveResponseToRegitryAdapterPaleoGiunta(RegistroDocumento registroDocumento,paleoGiunta.it.marche.regione.paleo.services.RespProtocollo respObj) {
		int iDocNumber=respObj.getDocNumber();
		
		PaleoRegistryAdapter toSaveObj=new PaleoRegistryAdapter();
		toSaveObj.setId(null);
		toSaveObj.setFk_registroDocummenti(registroDocumento.getId());
		toSaveObj.setIdPaleoDoc(new Integer(iDocNumber));
		
		//toSaveObj.setPaleoSegnaturaProtocollo(respObj.getSegnatura()); non serve la signature ma il numero del documento
		toSaveObj.setPaleoSegnaturaProtocollo(respObj.getNumero());
		toSaveObj.setIdPaleoNumeroDoc(new Integer(iDocNumber));
		toSaveObj.setPaleoOggetto(respObj.getOggetto());
		LOGGER.debug(" obj number "+respObj.getNumero());
		toSaveObj.setPaleoProtocolloData(PaleoAdapterService.getStringFromCalendar(respObj.getDataProtocollazione()));
		toSaveObj=paleoRegistryAdapterRepository.save(toSaveObj);
		return toSaveObj;
	}
	
	//==============================================================================
	/**
	 *Documento Interno
	 * [ATTENZIONE] :
	 * 1 - Il documento interno NON ha signature.
	 * 2 - NON c'è data protocollo; ma la data è la data del documento.
	 * @param registroDocumento
	 * @param respObj
	 * @return
	 */
	private static String prefRespDocSignature="ID:  ";
	
	protected PaleoRegistryAdapter saveResponseToRegitryAdapter(RegistroDocumento registroDocumento,paleoGiunta.it.marche.regione.paleo.services.RespDocumento respObj) {
		int iDocNumber=respObj.getDocNumber();
		//==========================================================
		//[ATTENZIONE] - Il documento interno ha signature: ma NON ha valore legale !!!!!!!!!!!!!!
		//==========================================================
		String segnatura = respObj.getSegnaturaDocumento();
		if(segnatura!=null)
			segnatura=segnatura.replaceAll(prefRespDocSignature, "");
		
		LOGGER.debug(" segnatura documento interno: " + segnatura);
		//==========================================================	  
		PaleoRegistryAdapter toSaveObj=new PaleoRegistryAdapter();
		toSaveObj.setId(null);
		toSaveObj.setFk_registroDocummenti(registroDocumento.getId());
		toSaveObj.setIdPaleoDoc(new Integer(iDocNumber));
		
		//toSaveObj.setPaleoSegnaturaProtocollo(segnatura); --> Proviamo a salvare il docNumber
		String docNumber = String.valueOf(respObj.getDocNumber());
		toSaveObj.setPaleoSegnaturaProtocollo(docNumber);
		toSaveObj.setIdPaleoNumeroDoc(new Integer(iDocNumber));
		toSaveObj.setPaleoOggetto(respObj.getOggetto());
		LOGGER.debug(" obj number "+respObj.getDocNumber());
		//NON c'è data protocollo; ma la data è la data del documento!!!
		toSaveObj.setPaleoProtocolloData(PaleoAdapterService.getStringFromCalendar(respObj.getDataDocumento()));
		toSaveObj=paleoRegistryAdapterRepository.save(toSaveObj);
		return toSaveObj;
	}
	
	protected PaleoRegistryAdapter saveResponseToRegitryAdapter(RegistroDocumento registroDocumento,RespDocumento respObj) {
		int iDocNumber=respObj.getDocNumber();
		//==========================================================
		//[ATTENZIONE] - Il documento interno ha signature: ma NON ha valore legale !!!!!!!!!!!!!!
		//==========================================================
		String segnatura = respObj.getSegnaturaDocumento();
		if(segnatura!=null)
			segnatura=segnatura.replaceAll(prefRespDocSignature, "");
		
		LOGGER.debug(" segnatura documento interno: " + segnatura);
		//==========================================================	  
		PaleoRegistryAdapter toSaveObj=new PaleoRegistryAdapter();
		toSaveObj.setId(null);
		toSaveObj.setFk_registroDocummenti(registroDocumento.getId());
		toSaveObj.setIdPaleoDoc(new Integer(iDocNumber));
		
		//toSaveObj.setPaleoSegnaturaProtocollo(segnatura); --> Proviamo a salvare il docNumber
		String docNumber = String.valueOf(respObj.getDocNumber());
		toSaveObj.setPaleoSegnaturaProtocollo(docNumber);
		toSaveObj.setIdPaleoNumeroDoc(new Integer(iDocNumber));
		toSaveObj.setPaleoOggetto(respObj.getOggetto());
		LOGGER.debug(" obj number "+respObj.getDocNumber());
		//NON c'è data protocollo; ma la data è la data del documento!!!
		toSaveObj.setPaleoProtocolloData(PaleoAdapterService.getStringFromCalendar(respObj.getDataDocumento()));
		toSaveObj=paleoRegistryAdapterRepository.save(toSaveObj);
		return toSaveObj;
	}	
	
	@Override
	public RispostaJson submitDocToProtocol(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO,
			Object object, String idTipoEvento) {
		PaleoDocumentAdapterDTO protocollo=null;
		ResponseEntity<PaleoDocumentAdapterDTO> response = null;
		RispostaJson jsonREsponse=new RispostaJson();
		
		boolean isError=false;
		String error = "";
		Protocollo protocolloSaved =null;
		
		Integer idTipoProtocollo = DbConst.TIPO_PROTOCOLLO_PALEO;
		Integer idStatoProtocollo = DbConst.STATO_PROTOCOLLO_PROTOCOLLATA;
		try {
			//response conterra il protocollo
			//Aggiungo idTipoEvento
			response = doUploadCustomProtocolSingleFile(registroDocumento,integDTO, idTipoEvento);
			if (!response.getStatusCode().equals(HttpStatus.OK)) {
				idStatoProtocollo = DbConst.STATO_PROTOCOLLO_IN_ERRORE;
				isError=true;
				error = "Upload file fallito";
			}
		} 
		catch (Exception e) {
			LOGGER.debug("@paleo submitDocToProtocol["+registroDocumento.getId()+"]  - Exception: " + e.getMessage());
			e.printStackTrace();
			
			idStatoProtocollo = DbConst.STATO_PROTOCOLLO_IN_ERRORE;
			isError = true;
			error = e.getMessage();
		}
		
		// Inizio modifica ticket CDST-93
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		int codice = -1;
		try {
			// S.D. - Integrazione del 13/07/2020
			codice = Integer.decode(conf.getTipologiaConferenzaSpecializzazione().getCodice());
		} catch (Exception e) {
			LOGGER.debug("Attenzione, il codice tipologia conferenza specializzazione non è numerico, vale : " + conf.getTipologiaConferenzaSpecializzazione().getCodice());
		}
		boolean isUSR = codice == DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS;
		boolean isAmbiente = codice ==  DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_AIA || codice == DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_VIA;
		
		if (((""+DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA).equals(idTipoEvento) ||
				(""+DbConst.TIPOLOGIA_EVENTO_MODIFICA_DATA).equals(idTipoEvento))
				&& (isUSR || isAmbiente)) {	// Integrazione modifica
			skipMeetpadMailDispatch = false;
			LOGGER.debug("Imposto il flag skipMeetpadMailDispatch a false");
		}
		// fine modifica
			
		// SEND MAIL AFTER PALEO PROTOCOL IS DONE
		if(!isError && !skipMeetpadMailDispatch) {
			
			Documento documento = documentoService.findById(registroDocumento.getDocumento().getIdDocumento());

			try {
				if (documento.getTipologiaDocumento() != null && 
						documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)) {
					documentoService.notificaMailIndizione(documento.getConferenza(), documento);
				}
				else {
					List<Evento> eventi = eventoService.findByDocumento(documento);
					Boolean mailEventoModifcaData = false;
					for(Evento evento : eventi) {
						EventoFileDTO eventoFileDTO = documentoBuilder.buildEventoFileDTO(documento, 
								evento.getTipoEvento().getCodice(),
								evento.getVisibilitaEventoPartecipanti(),
								true);
						
						List<ModificaData> md = null;
						if (evento.getTipoEvento().getCodice().equalsIgnoreCase(String.valueOf(DbConst.TIPOLOGIA_EVENTO_MODIFICA_DATA))) {
							if (!mailEventoModifcaData) {
								md = modificaDataRepo.findModificaDataByIdDocumento(evento.getDocumento().getIdDocumento());
								eventoService.notificaMailFromScheduler(evento, documento, eventoFileDTO.getListaDestinatari(),md);
								mailEventoModifcaData = true;
							}
						} else {
							eventoService.notificaMailFromScheduler(evento, documento, eventoFileDTO.getListaDestinatari(),md);
						}
					}
				}
			} catch (Exception e) {
				LOGGER.debug("@paleo submitDocToProtocol - sendmail Exception: " + e.getMessage());
				e.printStackTrace();
				isError = true;
				error = e.getMessage();
			}
		}
		
		if(response!=null){
			protocollo = response.getBody();
		}
		
		if(!isError)
			protocolloSaved = protocolloService.saveProtocollo(registroDocumento,protocollo.getPaleoSegnaturaProtocollo(), idTipoProtocollo,
					idStatoProtocollo);
		else	
			if (protocollo != null &&
			protocollo.getPaleoError() != null) {
				error = protocollo.getPaleoError() ;
			}
			protocolloSaved = protocolloService.saveProtocolloError(registroDocumento,error, idTipoProtocollo,
					idStatoProtocollo);
		
		
		saveInfoProtocolloDocumento(registroDocumento.getDocumento(), protocolloSaved);
		
		if (protocollo == null) {
			jsonREsponse.setCode(null);
			jsonREsponse.setData(null);
			jsonREsponse.setCode(null);
			jsonREsponse.setData(null);
			jsonREsponse.setMessage(error);
			jsonREsponse.setStatus("error");
		} else {
			jsonREsponse.setCode(protocollo.getPaleoSegnaturaProtocollo());
			jsonREsponse.setData(protocollo.getDataProtocollo());
			if(!skipMeetpadMailDispatch) {
				jsonREsponse.setMessage(protocollo.getPaleoOggetto());
			}	
			else 
			{
				jsonREsponse.setMessage(protocollo.getPaleoError());
			}
			jsonREsponse.setStatus("protocollato");
		}
		
		
		return jsonREsponse;
	}

	private void saveInfoProtocolloDocumento(Documento documento, Protocollo protocolloSaved) {
		documento.setDataProtocollo(protocolloSaved.getDataProtocollo());
		documento.setNumeroProtocollo(protocolloSaved.getNumeroProtocollo());
		documentoRepository.save(documento);
	}

	@Override
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol() {
			return  null;
	}
	
	
	/**
	 * Gestione delle liste da gestire dal meccanismo di Listner
	 * Per gli eventi associati al tipo :
	 * SCRITTURA-PROTOCOLLO-PALEO-USCITA 
	 * @return
	 */
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolInIngressoUscita() {
		String par1="SCRITTURA-PROTOCOLLO-PALEO-INOUT";
		return  super.protocolloService.findAllDocToSubmitToProtocol(par1);

	}
	
	/**
	 * Gestione delle liste da gestire dal meccanismo di Listner
	 * Per gli eventi associati al tipo :
	 * SCRITTURA-PROTOCOLLO-PALEO-USCITA 
	 * @return
	 */
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolInUscita() {
		String par1="SCRITTURA-PROTOCOLLO-PALEO-USCITA";
		return  super.protocolloService.findAllDocToSubmitToProtocol(par1);

	}

	/**
	 * Gestione delle liste da gestire dal meccanismo di Listner
	 * Per gli eventi associati al tipo :
	 * SCRITTURA-PROTOCOLLO-PALEO-USCITA 
	 * @return
	 */
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolInUscitaDOMUS(String par1) {
		return  super.protocolloService.findAllDocToSubmitToProtocolAndSpecializzazione(par1);
	}

	/**
	 * Gestione delle liste da gestire dal meccanismo di Listner
	 * Per gli eventi associati al tipo :
	 * CRITTURA-PROTOCOLLO-PALEO-INGRESSO
	 * @return
	 */
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocolInIngresso() {
		String par1="SCRITTURA-PROTOCOLLO-PALEO-INGRESSO";
		return  super.protocolloService.findAllDocToSubmitToProtocolGlobal(par1);

	}
	
	/**
	 * Gestione delle liste da gestire dal meccanismo di Listner
	 * Per gli eventi associati al tipo :
	 * SCRITTURA-DOCUMENT-INTRA-PALEO
	 * @return
	 */
	public List<ObserverRegistryListener> findAllDocToSubmitToIntraDocument() {
		String par1="SCRITTURA-DOCUMENT-INTRA-PALEO";
		return  super.protocolloService.findAllDocToSubmitToProtocol(par1);

	}

	
	
	@Override
	public void submitProtocol() {
		List<ObserverRegistryListener> listed = findAllDocToSubmitToProtocol();
		
		LOGGER.debug("@paleo submitDocToProtoco list["+this.getClass().getSimpleName()+"]: " + listed.size());
		for (ObserverRegistryListener item : listed) {
			RegistroDocumento registroDocumento = registroDocumentoRepository.findById(item.getId_registro())
					.orElse(null);
			if (registroDocumento != null) {
				IntegProtocolloDTO  integDTO=IntegProtocolloDTO.fillIntegProtocolloDTO(item);
				integDTO = observerRegistryListenerService.getUtenteProtocollante(item, integDTO);				
				//TODO reistrare il protocollo
				
				//Aggiungo l'id_tipo_evento per aggiungerlo all'oggetto
				String idTipoEvento = item.getId_tipo_evento();
				LOGGER.debug("L'idTipoEvento che mi serve per l'oggetto è: " + idTipoEvento);
				RispostaJson protocollo=submitDocToProtocol(registroDocumento,integDTO,null, idTipoEvento);
				//Commento il vecchio metodo
				//RispostaJson protocollo=submitDocToProtocol(registroDocumento,integDTO,null);
				//Fine modifica mia
				
				LOGGER.debug("submitProtocol: "+protocollo.getMessage());

				if(protocollo.getStatus().equals("protocollato")) {
					createEventoProtocollo(item.getProtocol_event_type(), protocollo.getCode(), registroDocumento.getDocumento());
					ObserverRegistryAudit auditId = saveObserverRegistryAudit(item, protocollo);
				}
				
			}
		}
	}	

	private void createEventoProtocollo(String id_tipo_evento, String codeProtocollo, Documento documento) {
		Evento evento = new Evento();
		Conferenza conferenza = documento.getConferenza();
		evento.setConferenza(conferenza);
		evento.setData(new Date());
		evento.setDocumento(documento);
		
		for (Partecipante partecipante : conferenza.getPartecipantes()) {
			if (partecipante.isAmministrazioneProcendete()) {
				evento.setMittente(partecipante);
			}
		}
		
		TipoEvento tipoEvento = tipoEventoRepository.findById(id_tipo_evento).orElse(null);
		evento.setTipoEvento(tipoEvento);
		evento.setOggettoEvento(tipoEvento.getOggettoEvento());
		evento.setProtocollo(codeProtocollo);
		eventoRepository.save(evento);
	}

	@Override
	public void doAsincronousTask() {
		this.submitProtocol(); 		
	}	
	
	/**
	 * Se Esiste un filtro di tipo: CODICE_SCRITTURA_DOCUMENTI_INTERNI_PALEO per un tipo conferenza
	 * allora ritrona true..ed
	 * 1 - la protocollazione viene evitata 
	 * 2 - il docum,ento viene storicizzato come documento interno Paleo..
	 * 
	 * di default ritrona smpre false.. 
	 * 
	 * @param idRegistro
	 * @return
	 */
	public boolean skipIfItIsDocumentoInterno(Integer idRegistro) {
		
		String fkTipoConferenza = paleoRegistryAdapterRepository.getTipologiaConferenzaByIdRegistro(idRegistro);
		PaleoRegistryFilter filter=paleoRegistryFilterRepository.getPaleoRegistryFilterForDocInterniByTipoConferenza(Integer.valueOf(fkTipoConferenza) );
		if(filter==null)
			return false;
		
		if(CODICE_SCRITTURA_DOCUMENTI_INTERNI_PALEO.equals(filter.getCodiceriferimento()))
			return true;
			
		return false;
	}

	
	protected List<Mailer> getMailerList(RegistroDocumento registroDocumento) {
		// get PEC receivers list
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		return mailerRepository.findAllMailerByConferenceForPaleo(conf.getIdConferenza());
	}
	
	protected String getOggettoForConference(Conferenza conf, String idTipoEvento) {
		// CDST-80: Nome e cognome intestatario pratica (se non presente ci si mette nome e cognome richiedente) – Ord. N.___/____);
		// Il valore dell'ordinanza può essere preso dal campo "Azione"
		
		String oggetto = conf.getRiferimentoIstanza();
		String determinazione = "";
		try { determinazione = conf.getOggettoDeterminazione().substring(0, determinazione.indexOf(' ')).replaceAll("[^0-9]*",  " "); } catch (Exception skip) { }
		LOGGER.debug("Determinazione vale" + determinazione);
		String ordinanza = "";
		//try { ordinanza = conf.getAzione().getAttivita().getDescrizione().replaceAll("[^0-9]*",  " "); } catch (Exception skip) { }
		try {
			ordinanza = (conf.getAzione().getDescrizione().toLowerCase().indexOf("ordinanza") > -1) ? conf.getAzione().getDescrizione() : "";
			if (ordinanza.trim().length() == 0)
				ordinanza = "Ord. N. ";
		} catch (Exception skip) {
			LOGGER.debug("Errore nel reperire l'ordinanza");
			if (conf.getAzione() != null)
				LOGGER.debug("conf.getAzione().getDescrizione() vale : " +conf.getAzione().getDescrizione());
			else
				LOGGER.debug("fk_azione nella tabella conferenza è null");
		}
		LOGGER.debug("L'ordinanza vale: " + ordinanza);
		LOGGER.debug("La conferenza è di tipo: " + conf.getTipologiaConferenzaSpecializzazione().getDescrizione());
		LOGGER.debug("idTipoEvento vale " + idTipoEvento);
		TipoEvento tipoEvento = tipoEventoRepository.findById(idTipoEvento).orElse(null);
		String nomeEvento = tipoEvento.getDescrizione();
		String oggettoPerAmbiente = nomeEvento + " - "  + oggetto;
		LOGGER.debug("Il nomeEvento vale: " + nomeEvento);
		if(conf.getTipologiaConferenzaSpecializzazione().getDescrizione().equals("tipologiaConferenzaSpec.domus")) {
			if (nomeEvento.replaceAll(" ","").toLowerCase().equalsIgnoreCase("convocazioneconferenza"))
				nomeEvento += " Regionale";
			oggetto = nomeEvento + " - " + conf.getNomeRichiedente() + " " +conf.getCognomeRichiedente() + " - " + ordinanza;
			LOGGER.debug("La conferenza è di tipo usr e l'oggetto vale: " + oggetto);
			return oggetto;
		}
		LOGGER.debug("La conferenza non è usr e l'oggetto vale: " + oggettoPerAmbiente);
		return oggettoPerAmbiente;
	}
	
	protected String getOggettoForConferenceUSR(Conferenza conf, String idTipoEvento) throws ServiceException {
		
		String result = "";
		
		if(conf.getTipologiaConferenzaSpecializzazione().getCodice().equals(Integer.toString(DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS))) {
			TipoEvento tipoEvento = tipoEventoRepository.findById(idTipoEvento).orElse(null);
			if (tipoEvento != null) {
				String nomeEvento = tipoEvento.getDescrizione();
				if (nomeEvento != null && !nomeEvento.isEmpty())
				{
					if (tipoEvento.getCodice().
						equals(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA))) {
						nomeEvento += " Regionale";
					}
					result += nomeEvento;
				}
			} else {
				throw new ServiceException ("IdTipoEvento non trovato " + idTipoEvento);
			}
			
			if (conf.getAzione() != null &&
				conf.getAzione().getDescrizione_lunga() != null &&
				!conf.getAzione().getDescrizione_lunga().isEmpty() &&
				conf.getAzione().getDescrizione_lunga().startsWith("Ordinanza")) {
				result += " - " + conf.getAzione().getDescrizione_lunga();
			}
			
			if (conf.getNomeRichiedente() != null &&
			    !conf.getNomeRichiedente().isEmpty()) {
				result += " - " + conf.getNomeRichiedente();
				if (conf.getCognomeRichiedente() != null &&
					!conf.getCognomeRichiedente().isEmpty()) {
					result += " " + conf.getCognomeRichiedente();
				}
			}  else {
				if (conf.getCognomeRichiedente() != null &&
						!conf.getCognomeRichiedente().isEmpty()) {
						result += " - " + conf.getCognomeRichiedente();
				}
			}
			
			
			 
			if (conf.getLocalizzazioneComune() != null &&
			    conf.getLocalizzazioneComune().getDescrizione()	!= null &&
			    !conf.getLocalizzazioneComune().getDescrizione().isEmpty()) {
				result += " - " + conf.getLocalizzazioneComune().getDescrizione();
			}
					
			LOGGER.debug("La conferenza è di tipo usr e l'oggetto vale: " + result);
		}
		
		return result;

		/*
		String oggetto = conf.getRiferimentoIstanza();
		String determinazione = "";
		try { determinazione = conf.getOggettoDeterminazione().substring(0, determinazione.indexOf(' ')).replaceAll("[^0-9]*",  " "); } catch (Exception skip) { }
		LOGGER.debug("Determinazione vale" + determinazione);
		String ordinanza = "";
		//try { ordinanza = conf.getAzione().getAttivita().getDescrizione().replaceAll("[^0-9]*",  " "); } catch (Exception skip) { }
		try {
			ordinanza = (conf.getAzione().getDescrizione().toLowerCase().indexOf("ordinanza") > -1) ? conf.getAzione().getDescrizione() : "";
			if (ordinanza.trim().length() == 0)
				ordinanza = "Ord. N. ";
		} catch (Exception skip) {
			LOGGER.debug("Errore nel reperire l'ordinanza");
			if (conf.getAzione() != null)
				LOGGER.debug("conf.getAzione().getDescrizione() vale : " +conf.getAzione().getDescrizione());
			else
				LOGGER.debug("fk_azione nella tabella conferenza è null");
		}
		LOGGER.debug("L'ordinanza vale: " + ordinanza);
		LOGGER.debug("La conferenza è di tipo: " + conf.getTipologiaConferenzaSpecializzazione().getDescrizione());
		LOGGER.debug("idTipoEvento vale " + idTipoEvento);
		TipoEvento tipoEvento = tipoEventoRepository.findById(idTipoEvento).orElse(null);
		String nomeEvento = tipoEvento.getDescrizione();
		String oggettoPerAmbiente = nomeEvento + " - "  + oggetto;
		LOGGER.debug("Il nomeEvento vale: " + nomeEvento);
		if(conf.getTipologiaConferenzaSpecializzazione().getDescrizione().equals("tipologiaConferenzaSpec.domus")) {
			if (nomeEvento.replaceAll(" ","").toLowerCase().equalsIgnoreCase("convocazioneconferenza"))
				nomeEvento += " Regionale";
			oggetto = nomeEvento + " - " + conf.getNomeRichiedente() + " " +conf.getCognomeRichiedente() + " - " + ordinanza;
			LOGGER.debug("La conferenza è di tipo usr e l'oggetto vale: " + oggetto);
			return oggetto;
		}
		LOGGER.debug("La conferenza non è usr e l'oggetto vale: " + oggettoPerAmbiente);
		return oggettoPerAmbiente;
		*/
	}
	
	protected paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] getListCorrispondetiPaleoGiunta(RegistroDocumento registroDocumento, boolean isMailRequired) {
		// get PEC receivers list
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		List<Mailer> mailerList = mailerRepository.findAllMailerByConferenceForPaleo(conf.getIdConferenza());
		
		return getListCorrispondetiPaleoGiunta(mailerList, isMailRequired);
	}
		
	protected paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] getListCorrispondetiPaleoGiunta(List<Mailer> mailerList, boolean isMailRequired) {
		List<paleoGiunta.it.marche.regione.paleo.services.Corrispondente> pecList = new ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente>();
		
		String escludiMail = paleoConfigurator.getPaleoEscludiMail();
		String[] listMail2Esclude = escludiMail.split("[;]");
		Boolean mailAmmProc;
		
		for(Mailer mail : mailerList) {
			mailAmmProc = false;
			
			for(int i=0; i<listMail2Esclude.length; i++) {
				if(listMail2Esclude[i].equalsIgnoreCase(mail.getEmail())) {
					mailAmmProc = true;
					LOGGER.debug("Ho trovato la mail dell'amministrazione procedente: " + mail.getEmail());
				}
			}
			
			if(isMailRequired && (mail.getEmail() == null 
					|| "".equalsIgnoreCase(mail.getEmail())
					|| mailAmmProc))
				continue;

			// xmf: added check to skip all non pecs
			if(!isPec(mail.getEmail())) continue;
			
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente corrispondente = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente();
			LOGGER.debug("Sono qui perché la mail da inviare non è dell'amministrazione procedente: " + mail.getEmail());
			String cognome = "";
			if(mail.getCognome() != null)
				cognome = mail.getCognome();
			else {
				//pecList.add(corrispondente);
				//corrispondente.setCodiceRubrica(integDTO.getCodiceRubricaDestinatario());
				
				cognome = mail.getDescrizione_ente().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
				if(cognome.length() > 100)
					cognome = cognome.substring(0, 100); 
			}
				
			pecList.add(corrispondente);
			
			paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente daticorrispondente = new paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			if(isMailRequired) {
				String recipient = mail.getEmail();
				
				try {
					if(mailSenderConfigurator.isFakeRecipient())
						recipient = mailSenderConfigurator.getFakeRecipientAddress();
				} catch (Exception skip) { }
				
				daticorrispondente.setEmail(recipient);
			}
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
		}
		// S.D. - Inizio modifica - preparo una nuova lista di Corrispondente senza eventuali duplicati
		ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente> newPecList = new ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente>();
		for (paleoGiunta.it.marche.regione.paleo.services.Corrispondente corr : pecList) {
			if (corrispondentePresentePaleoGiunta(newPecList, corr.getCorrispondenteOccasionale().getEmail()) == false)
				newPecList.add(corr);
			else
				LOGGER.debug("(getListCorrispondeti) - Duplicato rimosso: " + corr.getCorrispondenteOccasionale().getEmail());
		}
		LOGGER.debug("(getListCorrispondeti) ----------- lista email inizio -----------");
		paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondeti = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente[newPecList.size()];
		for(int i=0; i<newPecList.size(); i++) {
			LOGGER.debug("@paleo corr: " + newPecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + newPecList.get(i).getCorrispondenteOccasionale().getEmail());
			listaCorrispondeti[i] = newPecList.get(i);
		}
		LOGGER.debug("(getListCorrispondeti) ----------- lista email fine -----------");
		// S.D. - fine modifica
		
		/* S.D. - Da rimuovere dopo verificato che funziona correttamente la creazione della nuova lista senza duplicati
		Corrispondente[] listaCorrispondeti = new Corrispondente[pecList.size()];
		for(int i=0; i<pecList.size(); i++) {
			LOGGER.debug("@paleo corr: " + pecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + pecList.get(i).getCorrispondenteOccasionale().getEmail());
			listaCorrispondeti[i] = pecList.get(i);
		}
		*/
		return listaCorrispondeti;
	}
	
	protected Corrispondente[] getListCorrispondeti(RegistroDocumento registroDocumento, boolean isMailRequired) {
		// get PEC receivers list
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		List<Mailer> mailerList = mailerRepository.findAllMailerByConferenceForPaleo(conf.getIdConferenza());
		
		return getListCorrispondeti(mailerList, isMailRequired);
	}
		
	protected Corrispondente[] getListCorrispondeti(List<Mailer> mailerList, boolean isMailRequired) {
		List<Corrispondente> pecList = new ArrayList<Corrispondente>();
		
		String escludiMail = paleoConfigurator.getPaleoEscludiMail();
		String[] listMail2Esclude = escludiMail.split("[;]");
		Boolean mailAmmProc;
		
		for(Mailer mail : mailerList) {
			mailAmmProc = false;
			
			for(int i=0; i<listMail2Esclude.length; i++) {
				if(listMail2Esclude[i].equalsIgnoreCase(mail.getEmail())) {
					mailAmmProc = true;
					LOGGER.debug("Ho trovato la mail dell'amministrazione procedente: " + mail.getEmail());
				}
			}
			
			if(isMailRequired && (mail.getEmail() == null 
					|| "".equalsIgnoreCase(mail.getEmail())
					|| mailAmmProc))
				continue;

			// xmf: added check to skip all non pecs
			if(!isPec(mail.getEmail())) continue;
			
			Corrispondente corrispondente = new Corrispondente();
			LOGGER.debug("Sono qui perché la mail da inviare non è dell'amministrazione procedente: " + mail.getEmail());
			String cognome = "";
			if(mail.getCognome() != null)
				cognome = mail.getCognome();
			else {
				//pecList.add(corrispondente);
				//corrispondente.setCodiceRubrica(integDTO.getCodiceRubricaDestinatario());
				
				cognome = mail.getDescrizione_ente().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
				if(cognome.length() > 100)
					cognome = cognome.substring(0, 100); 
			}
				
			pecList.add(corrispondente);
			
			DatiCorrispondente daticorrispondente = new DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			if(isMailRequired) {
				String recipient = mail.getEmail();
				
				try {
					if(mailSenderConfigurator.isFakeRecipient())
						recipient = mailSenderConfigurator.getFakeRecipientAddress();
				} catch (Exception skip) { }
				
				daticorrispondente.setEmail(recipient);
			}
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
		}
		// S.D. - Inizio modifica - preparo una nuova lista di Corrispondente senza eventuali duplicati
		ArrayList<Corrispondente> newPecList = new ArrayList<Corrispondente>();
		for (Corrispondente corr : pecList) {
			if (corrispondentePresente(newPecList, corr.getCorrispondenteOccasionale().getEmail()) == false)
				newPecList.add(corr);
			else
				LOGGER.debug("(getListCorrispondeti) - Duplicato rimosso: " + corr.getCorrispondenteOccasionale().getEmail());
		}
		LOGGER.debug("(getListCorrispondeti) ----------- lista email inizio -----------");
		Corrispondente[] listaCorrispondeti = new Corrispondente[newPecList.size()];
		for(int i=0; i<newPecList.size(); i++) {
			LOGGER.debug("@paleo corr: " + newPecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + newPecList.get(i).getCorrispondenteOccasionale().getEmail());
			listaCorrispondeti[i] = newPecList.get(i);
		}
		LOGGER.debug("(getListCorrispondeti) ----------- lista email fine -----------");
		// S.D. - fine modifica
		
		/* S.D. - Da rimuovere dopo verificato che funziona correttamente la creazione della nuova lista senza duplicati
		Corrispondente[] listaCorrispondeti = new Corrispondente[pecList.size()];
		for(int i=0; i<pecList.size(); i++) {
			LOGGER.debug("@paleo corr: " + pecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + pecList.get(i).getCorrispondenteOccasionale().getEmail());
			listaCorrispondeti[i] = pecList.get(i);
		}
		*/
		return listaCorrispondeti;
	}
	
	protected Corrispondente getCorrispondeteResponsabile(List<Mailer> mailerList) {
		for(Mailer mail : mailerList) {
			if(!"1".equals(mail.getCodiceRuolo()))
				continue;
			
			Corrispondente corrispondente = new Corrispondente();
			
			String cognome = "";
			if(mail.getCognome() != null)
				cognome = mail.getCognome();
			else {
				cognome = mail.getDescrizione_ente().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
				if(cognome.length() > 100)
					cognome = cognome.substring(0, 100); 
			}
				
			DatiCorrispondente daticorrispondente = new DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			daticorrispondente.setEmail(mail.getEmail());
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
			
			return corrispondente;
		}
		
		return null;
	}
	
	protected paleoGiunta.it.marche.regione.paleo.services.Corrispondente getCorrispondeteResponsabileGiunta(List<Mailer> mailerList) {
		for(Mailer mail : mailerList) {
			if(!"1".equals(mail.getCodiceRuolo()))
				continue;
			
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente corrispondente = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente();
			
			String cognome = "";
			if(mail.getCognome() != null)
				cognome = mail.getCognome();
			else {
				cognome = mail.getDescrizione_ente().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
				if(cognome.length() > 100)
					cognome = cognome.substring(0, 100); 
			}
				
			paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente daticorrispondente = new paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			daticorrispondente.setEmail(mail.getEmail());
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
			
			return corrispondente;
		}
		
		return null;
	}
	
	
	protected Corrispondente getMittenteResponsabile(Partecipante mittente) {

			Corrispondente corrispondente = new Corrispondente();
			
			String cognome = "";
			String email = "";
			if(mittente != null) {
				cognome = mittente.getDescEnteCompetente();
				email = mittente.getPecEnteCompetente();
			}
				
				
			DatiCorrispondente daticorrispondente = new DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			daticorrispondente.setEmail(email);
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
			
			return corrispondente;

	}
	
	protected paleoGiunta.it.marche.regione.paleo.services.Corrispondente getMittenteResponsabileGiunta(Partecipante mittente) {

		paleoGiunta.it.marche.regione.paleo.services.Corrispondente corrispondente = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente();
		
		String cognome = "";
		String email = "";
		if(mittente != null) {
			cognome = mittente.getDescEnteCompetente();
			email = mittente.getPecEnteCompetente();
		}
			
			
		paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente daticorrispondente = new paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente();
		
		daticorrispondente.setCognome(cognome);
		daticorrispondente.setEmail(email);
		
		corrispondente.setCorrispondenteOccasionale(daticorrispondente);
		
		return corrispondente;

}
	
	/*
	protected List<String> generateAttachDocments(RegistroDocumento registroDocumento, List<Mailer> mailerList) {
		List<String> attachPaths = new ArrayList<String>();

		Documento documento = documentoService.findById(registroDocumento.getDocumento().getIdDocumento());
		boolean isIndizione = documento.getTipologiaDocumento() != null && documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE);
		List<Evento> eventi = eventoService.findByDocumento(documento);
		try {
			String messageBody = null;

			if (isIndizione) {
				for (Mailer mail : mailerList) {
					MailMetadata metadati = emailRepositoryService.formatTestoEmailIndizione(mail, null, null, null, null);
					// TockenConference tocken = mailerRepository.getTocken("guido.deluca@eng.it", new Integer(1),  new Integer(1), new Integer(idconferenza), null, "Indizione");
					// String urlTocken = "http://127.0.0.1:8082/conferences/autoaccreditamento/" + tocken.getTKN1() + "/" + tocken.getTKN2() + "/";
					metadati.setFaseConcerenza("INDIZIONE");
					metadati.setCodiceTipoEvento(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA));
					
					messageBody = mailbuilder.build_indizione(metadati);
				}
			}
			else {
				for(Evento evento : eventi) {
					EventoFileDTO eventoFileDTO = documentoBuilder.buildEventoFileDTO(documento, 
							evento.getTipoEvento().getCodice(),
							evento.getVisibilitaEventoPartecipanti());
					if (evento.getTipoEvento().getFlagInvioEmail()) {
						MailMetadata metadati = emailRepositoryService.sendEmailEvento(evento, null, null, documento, null, false);
						metadati.setFaseConcerenza(evento.getTipoEvento().getDescrizione());
						metadati.setCodiceTipoEvento(evento.getTipoEvento().getCodice());
						metadati.setCorpoEvento(evento.getCorpo());
						messageBody = mailbuilder.buildTemplateEmail(metadati);
					}
				}
			}
			
			if(messageBody != null) {
				// TODO: save massage to file and add path to list 
			}
				
		} catch (Exception e) {
			LOGGER.debug("@paleo submitDocToProtocol - sendmail Exception: " + e.getMessage());
			e.printStackTrace();
		}

		return attachPaths;
	}
	*/
	
	public boolean isPec(String mail) {
		if (mail != null) {
			EmailPec emailPec = emailPecRepository.findByEmail(mail).orElse(null);

			if (emailPec != null)
				return emailPec.getPec();
		}
		return mailSenderConfigurator.isDefaultFormatPec();
	}
	
	private boolean corrispondentePresente(List<Corrispondente> corrispondenti, String email) {
		boolean presente = false;
		if (corrispondenti != null && corrispondenti.size() > 0) {
			for (Corrispondente corr : corrispondenti) {
				if (corr.getCorrispondenteOccasionale() != null && corr.getCorrispondenteOccasionale().getEmail().equalsIgnoreCase(email))
					return true;
			}
		}
		return presente;
	}
	
	private boolean corrispondentePresentePaleoGiunta(List<paleoGiunta.it.marche.regione.paleo.services.Corrispondente> corrispondenti, String email) {
		boolean presente = false;
		if (corrispondenti != null && corrispondenti.size() > 0) {
			for (paleoGiunta.it.marche.regione.paleo.services.Corrispondente corr : corrispondenti) {
				if (corr.getCorrispondenteOccasionale() != null && corr.getCorrispondenteOccasionale().getEmail().equalsIgnoreCase(email))
					return true;
			}
		}
		return presente;
	}
	
	protected Corrispondente[] getListCorrispondetiDue(List<Partecipante> partecipanteList, boolean isMailRequired) {
		
		List<Corrispondente> pecList = new ArrayList<Corrispondente>();
		
		String escludiMail = paleoConfigurator.getPaleoEscludiMail();
		String[] listMail2Esclude = escludiMail.split("[;]");
		Boolean mailAmmProc;
		
		for (Partecipante partecipante : partecipanteList) {
			mailAmmProc = false;
			
			for(int i=0; i<listMail2Esclude.length; i++) {
				if(listMail2Esclude[i].equalsIgnoreCase(partecipante.getPecEnteCompetente())) {
					mailAmmProc = true;
					LOGGER.debug("Ho trovato la mail dell'amministrazione procedente: " + partecipante.getPecEnteCompetente());
				}
			}
			
			if(isMailRequired && (partecipante.getPecEnteCompetente() == null 
					|| "".equalsIgnoreCase(partecipante.getPecEnteCompetente())
					|| mailAmmProc))
				continue;

			// xmf: added check to skip all mails marked as non  PEC
			if(!isPec(partecipante.getPecEnteCompetente())) continue;
			
			Corrispondente corrispondente = new Corrispondente();
			LOGGER.debug("Sono qui perché la mail da inviare non è dell'amministrazione procedente: " + partecipante.getPecEnteCompetente());
			
			String cognome = partecipante.getDescEnteCompetente().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
			if (partecipante.getRuoloPartecipante() != null &&
				partecipante.getRuoloPartecipante().getCodice() != null &&
				!partecipante.getRuoloPartecipante().getCodice().isEmpty() &&
				partecipante.getRuoloPartecipante().getCodice().equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)) &&
				(cognome == null || cognome.isEmpty() || cognome.length() == 0)) {
				cognome = "Richiedente";
			} 
			
			if(cognome.length() == 0) continue;
			
			if(cognome.length() > 100)
				cognome = cognome.substring(0, 100); 
			
			pecList.add(corrispondente);
			
			DatiCorrispondente daticorrispondente = new DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			if(isMailRequired) {
				String recipient = partecipante.getPecEnteCompetente();
				
				try {
					if(mailSenderConfigurator.isFakeRecipient())
						recipient = mailSenderConfigurator.getFakeRecipientAddress();
				} catch (Exception skip) { }
				
				daticorrispondente.setEmail(recipient);
			}
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
		}
		
		// S.D. - Inizio modifica - preparo una nuova lista di Corrispondente senza eventuali duplicati
		ArrayList<Corrispondente> newPecList = new ArrayList<Corrispondente>();
		for (Corrispondente corr : pecList) {
			if (corrispondentePresente(newPecList, corr.getCorrispondenteOccasionale().getEmail()) == false)
				newPecList.add(corr);
			else
				LOGGER.debug("(getListCorrispondetiDue) - Duplicato rimosso: " + corr.getCorrispondenteOccasionale().getEmail());
		}
		LOGGER.debug("(getListCorrispondetiDue) ----------- lista email inizio -----------");
		Corrispondente[] listaCorrispondeti = new Corrispondente[newPecList.size()];
		for(int i=0; i<newPecList.size(); i++) {
			LOGGER.debug("@paleo corr: " + newPecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + newPecList.get(i).getCorrispondenteOccasionale().getEmail());
			listaCorrispondeti[i] = newPecList.get(i);
		}
		LOGGER.debug("(getListCorrispondetiDue) ----------- lista email fine -----------");
		// S.D. - fine modifica
		return listaCorrispondeti;
	}

	protected paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] getListCorrispondetiDuePaleoGiunta(List<Partecipante> partecipanteList, boolean isMailRequired) {
		
		List<paleoGiunta.it.marche.regione.paleo.services.Corrispondente> pecList = new ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente>();
		
		String escludiMail = paleoConfigurator.getPaleoEscludiMail();
		String[] listMail2Esclude = escludiMail.split("[;]");
		Boolean mailAmmProc;
		
		for (Partecipante partecipante : partecipanteList) {
			mailAmmProc = false;
			
			for(int i=0; i<listMail2Esclude.length; i++) {
				if(listMail2Esclude[i].equalsIgnoreCase(partecipante.getPecEnteCompetente())) {
					mailAmmProc = true;
					LOGGER.debug("Ho trovato la mail dell'amministrazione procedente: " + partecipante.getPecEnteCompetente());
				}
			}
			
			if(isMailRequired && (partecipante.getPecEnteCompetente() == null 
					|| "".equalsIgnoreCase(partecipante.getPecEnteCompetente())
					|| mailAmmProc))
				continue;

			// xmf: added check to skip all non pecs
			if(!isPec(partecipante.getPecEnteCompetente())) continue;
			
			paleoGiunta.it.marche.regione.paleo.services.Corrispondente corrispondente = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente();
			LOGGER.debug("Sono qui perché la mail da inviare non è dell'amministrazione procedente: " + partecipante.getPecEnteCompetente());
			
			String cognome = partecipante.getDescEnteCompetente().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
			if (partecipante.getRuoloPartecipante() != null &&
				partecipante.getRuoloPartecipante().getCodice() != null &&
				!partecipante.getRuoloPartecipante().getCodice().isEmpty() &&
				partecipante.getRuoloPartecipante().getCodice().equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE)) &&
				(cognome == null || cognome.isEmpty() || cognome.length() == 0)) {
				cognome = "Richiedente";
			} 
			
			if(cognome.length() == 0) continue;
			
			if(cognome.length() > 100)
				cognome = cognome.substring(0, 100); 
			
			pecList.add(corrispondente);
			
			paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente daticorrispondente = new paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			if(isMailRequired) {
				String recipient = partecipante.getPecEnteCompetente();
				
				try {
					if(mailSenderConfigurator.isFakeRecipient())
						recipient = mailSenderConfigurator.getFakeRecipientAddress();
				} catch (Exception skip) { }
				
				daticorrispondente.setEmail(recipient);
			}
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
		}
		
		// S.D. - Inizio modifica - preparo una nuova lista di Corrispondente senza eventuali duplicati
		ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente> newPecList = new ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente>();
		for (paleoGiunta.it.marche.regione.paleo.services.Corrispondente corr : pecList) {
			if (corrispondentePresentePaleoGiunta(newPecList, corr.getCorrispondenteOccasionale().getEmail()) == false)
				newPecList.add(corr);
			else
				LOGGER.debug("(getListCorrispondetiDue) - Duplicato rimosso: " + corr.getCorrispondenteOccasionale().getEmail());
		}
		LOGGER.debug("(getListCorrispondetiDue) ----------- lista email inizio -----------");
		paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondeti = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente[newPecList.size()];
		for(int i=0; i<newPecList.size(); i++) {
			LOGGER.debug("@paleo corr: " + newPecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + newPecList.get(i).getCorrispondenteOccasionale().getEmail());
			listaCorrispondeti[i] = newPecList.get(i);
		}
		LOGGER.debug("(getListCorrispondetiDue) ----------- lista email fine -----------");
		// S.D. - fine modifica
		return listaCorrispondeti;
	}
/*
	protected Corrispondente getCorrispondeteResponsabileDue(List<Partecipante> partecipanteList) {
		
		for (Partecipante partecipante : partecipanteList) {
			if(!"1".equals(partecipante.getRuoloPartecipante()))
				continue;
			
			Corrispondente corrispondente = new Corrispondente();
			
			String cognome = partecipante.getDescEnteCompetente().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
			if(cognome.length() > 100)
				cognome = cognome.substring(0, 100); 
				
			DatiCorrispondente daticorrispondente = new DatiCorrispondente();
			
			daticorrispondente.setCognome(cognome);
			daticorrispondente.setEmail(partecipante.getPecEnteCompetente());
			
			corrispondente.setCorrispondenteOccasionale(daticorrispondente);
			
			return corrispondente;
		}
		
		return null;
	}*/
	
	protected Corrispondente[] getListCorrispondetiAccreditati(List<AccreditamentoDTO> accreditamentoList, boolean isMailRequired) {
        
        List<Corrispondente> pecList = new ArrayList<Corrispondente>();
        
        for (AccreditamentoDTO accreditamentoDTO : accreditamentoList) {


            if(isMailRequired && (accreditamentoDTO.getEmail() == null || "".equalsIgnoreCase(accreditamentoDTO.getEmail().trim())))
                continue;
            
            Corrispondente corrispondente = new Corrispondente();
            LOGGER.debug("Sono qui perché la mail da inviare è un indirizzo presumibilmente valido");
            
            String cognome = accreditamentoDTO.getCognome().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
            
            if(cognome.length() > 100)
                cognome = cognome.substring(0, 100); 
            
            pecList.add(corrispondente);
            
            DatiCorrispondente daticorrispondente = new DatiCorrispondente();
            
            daticorrispondente.setCognome(cognome);
            if(isMailRequired) {
                String recipient = accreditamentoDTO.getEmail();
                
                try {
                    if(mailSenderConfigurator.isFakeRecipient())
                        recipient = mailSenderConfigurator.getFakeRecipientAddress();
                } catch (Exception skip) { }
                
                daticorrispondente.setEmail(recipient);
                LOGGER.debug("Indirizzo aggiunto: " + recipient);
            }
            corrispondente.setCorrispondenteOccasionale(daticorrispondente);
        }
        
        // S.D. - Inizio modifica - preparo una nuova lista di Corrispondente senza eventuali duplicati
 		ArrayList<Corrispondente> newPecList = new ArrayList<Corrispondente>();
 		for (Corrispondente corr : pecList) {
 			if (corrispondentePresente(newPecList, corr.getCorrispondenteOccasionale().getEmail()) == false)
 				newPecList.add(corr);
 			else
 				LOGGER.debug("(getListCorrispondetiAccreditati) - Duplicato rimosso: " + corr.getCorrispondenteOccasionale().getEmail());
 		}
 		LOGGER.debug("(getListCorrispondetiAccreditati) ----------- lista email inizio -----------");
 		Corrispondente[] listaCorrispondeti = new Corrispondente[newPecList.size()];
 		for(int i=0; i<newPecList.size(); i++) {
 			LOGGER.debug("@paleo corr: " + newPecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + newPecList.get(i).getCorrispondenteOccasionale().getEmail());
 			listaCorrispondeti[i] = newPecList.get(i);
 		}
 		LOGGER.debug("(getListCorrispondetiAccreditati) ----------- lista email fine -----------");
 		// S.D. - fine modifica

     	/*
        Corrispondente[] listaCorrispondeti = new Corrispondente[pecList.size()];
        for(int i=0; i<pecList.size(); i++) {
            LOGGER.debug("@paleo corr: " + pecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + pecList.get(i).getCorrispondenteOccasionale().getEmail());
            listaCorrispondeti[i] = pecList.get(i);
        }
		*/

        return listaCorrispondeti;
    }
	
protected paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] getListCorrispondetiAccreditatiPaleoGiunta(List<AccreditamentoDTO> accreditamentoList, boolean isMailRequired) {
        
        List<paleoGiunta.it.marche.regione.paleo.services.Corrispondente> pecList = new ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente>();
        
        for (AccreditamentoDTO accreditamentoDTO : accreditamentoList) {


            if(isMailRequired && (accreditamentoDTO.getEmail() == null || "".equalsIgnoreCase(accreditamentoDTO.getEmail().trim())))
                continue;
            
            paleoGiunta.it.marche.regione.paleo.services.Corrispondente corrispondente = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente();
            LOGGER.debug("Sono qui perché la mail da inviare è un indirizzo presumibilmente valido");
            
            String cognome = accreditamentoDTO.getCognome().replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
            
            if(cognome.length() > 100)
                cognome = cognome.substring(0, 100); 
            
            pecList.add(corrispondente);
            
            paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente daticorrispondente = new paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente();
            
            daticorrispondente.setCognome(cognome);
            if(isMailRequired) {
                String recipient = accreditamentoDTO.getEmail();
                
                try {
                    if(mailSenderConfigurator.isFakeRecipient())
                        recipient = mailSenderConfigurator.getFakeRecipientAddress();
                } catch (Exception skip) { }
                
                daticorrispondente.setEmail(recipient);
                LOGGER.debug("Indirizzo aggiunto: " + recipient);
            }
            corrispondente.setCorrispondenteOccasionale(daticorrispondente);
        }
        
        // S.D. - Inizio modifica - preparo una nuova lista di Corrispondente senza eventuali duplicati
 		ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente> newPecList = new ArrayList<paleoGiunta.it.marche.regione.paleo.services.Corrispondente>();
 		for (paleoGiunta.it.marche.regione.paleo.services.Corrispondente corr : pecList) {
 			if (corrispondentePresentePaleoGiunta(newPecList, corr.getCorrispondenteOccasionale().getEmail()) == false)
 				newPecList.add(corr);
 			else
 				LOGGER.debug("(getListCorrispondetiAccreditati) - Duplicato rimosso: " + corr.getCorrispondenteOccasionale().getEmail());
 		}
 		LOGGER.debug("(getListCorrispondetiAccreditati) ----------- lista email inizio -----------");
 		paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listaCorrispondeti = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente[newPecList.size()];
 		for(int i=0; i<newPecList.size(); i++) {
 			LOGGER.debug("@paleo corr: " + newPecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + newPecList.get(i).getCorrispondenteOccasionale().getEmail());
 			listaCorrispondeti[i] = newPecList.get(i);
 		}
 		LOGGER.debug("(getListCorrispondetiAccreditati) ----------- lista email fine -----------");
 		// S.D. - fine modifica

     	/*
        Corrispondente[] listaCorrispondeti = new Corrispondente[pecList.size()];
        for(int i=0; i<pecList.size(); i++) {
            LOGGER.debug("@paleo corr: " + pecList.get(i).getCorrispondenteOccasionale().getCognome() + " - " + pecList.get(i).getCorrispondenteOccasionale().getEmail());
            listaCorrispondeti[i] = pecList.get(i);
        }
		*/

        return listaCorrispondeti;
    }
 
}
