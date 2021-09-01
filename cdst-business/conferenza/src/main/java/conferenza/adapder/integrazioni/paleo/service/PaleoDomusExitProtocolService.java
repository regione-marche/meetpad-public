package conferenza.adapder.integrazioni.paleo.service;

import java.io.IOException;

import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAdapterDTO;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoClientConfiguration;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;
import conferenza.mail.EmailRepositoryService;
import conferenza.mail.bean.MailMetadata;
import conferenza.model.Conferenza;
import conferenza.model.Mailer;
import conferenza.model.Partecipante;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.util.DbConst;
import it.marche.regione.paleo.services.RespProtocolloPartenza;

@Component("PaleoObserverDomusExitListnerInterface")
public class PaleoDomusExitProtocolService extends PaleoIntegProtocolService{

	@Autowired
	PaleoDocumentaleIntraProtocolService paleoDocumentaleService;
	
	@Autowired
	EmailRepositoryService emailRepositoryService;
	
	@Value("${idTipoEventoCaricamentoVerbale.id}")
	private String idTipoEvento;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaleoDomusExitProtocolService.class);
	@Override
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol() {
		return  super.findAllDocToSubmitToProtocolInUscitaDOMUS("SCRITTURA-PROTOCOLLO-DOMUS-PALEO-USCITA");
	}

	@Autowired 
	PaleoClientConfiguration clientConfiguration;

	@Override
	public void doAsincronousTask() {
		super.submitProtocol(); 		
	}
	
	/**
	 * [ATTENZIONE]
	 * Se Esiste un filtro che associa una tipologia conferenza a PaleoDocumentale..
	 * invece della protocollazione viene eseguita la storicizzazione come documento interno
	 * 
	 */
	@Override
	protected ResponseEntity<PaleoDocumentAdapterDTO> doUploadCustomProtocolSingleFile(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, String idTipoEvento) throws ServiceException, IOException, SOAPException {
		RespProtocolloPartenza out=null;
		//gestione dei focumenti interni paleo
		
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		String cfResp = conf.getCodiceFiscaleResponsabileConferenza();
		LOGGER.debug("@paleo doUploadExitProtocolSingleFile[cfResp]: " + cfResp);
		LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoObserverDomusExitListnerInterface]: " + conf.getIdConferenza());
		boolean isUSR = conf.getTipologiaConferenzaSpecializzazione().getCodice().equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS);
		boolean isDocumentoInterno=skipIfItIsDocumentoInterno(registroDocumento.getId());
		
		String oggetto = "";
		
			
		
		if (isUSR) {
			oggetto = getOggettoForConferenceUSR(conf, idTipoEvento);
			//if(isDocumentoInterno==false || idTipoEvento.equals("9"))
			if(!isDocumentoInterno || idTipoEvento.equals("idTipoEvento")) {
				if (idTipoEvento.equals(clientConfiguration.getComunicazioneGenerica())) {
					LOGGER.debug("@paleo doUploadCustomProtocolSingleFile - Comunicazione Generica utilizzo la lista partecipanti");
					List<Mailer> listMail = getMailerList(registroDocumento);
					
					List<Partecipante> listPartecipante = registroDocumento.getDocumento().getVisibilitaPartecipanti();
				    out = paleoAdapterClient.doSubmitFileToExitProtocolServiceUSR(registroDocumento, integDTO, getListCorrispondetiDue(listPartecipante, true), oggetto, isUSR, cfResp);
				} else
					out = paleoAdapterClient.doSubmitFileToExitProtocolServiceUSR(registroDocumento, integDTO, getListCorrispondeti(registroDocumento, true), oggetto, isUSR, cfResp);
			}			
			else
				return paleoDocumentaleService.doUploadCustomProtocolSingleFile(registroDocumento,integDTO, idTipoEvento);
			
			String messaggio=out.getMessaggioRisultato().getDescrizione();
			String tipo=out.getMessaggioRisultato().getTipoRisultato().getValue();	
			
			if("Error".equals(tipo)){			
				LOGGER.debug("@paleo [ERRORE] : doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);		
				
				PaleoDocumentAdapterDTO dto = new PaleoDocumentAdapterDTO();
				dto.setPaleoError(messaggio);
				return new ResponseEntity(dto,HttpStatus.EXPECTATION_FAILED);
			}
			else {
				LOGGER.debug("@paleo doUploadExitProtocolSingleFile sending PEC: " +messaggio+" - "+tipo);		
				
				// xmf: sending PEC through Paleo
				skipMeetpadMailDispatch = true;
				
				MailMetadata metadati = new MailMetadata("");
				
				metadati.setIdConferenza(conf.getIdConferenza());
				metadati.setSubject("Mail inviata da PALEO");
				metadati.setMessage("Mail inviata da PALEO");
				//metadati.setMessageId(messageId); // TODO: generate a GUID
				
				if(!paleoAdapterClient.doProtocolDispatchService(out.getSegnatura(), isUSR)) {
					LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoObserverDomusExitListnerInterface]: PEC dispatch error " +messaggio+" - "+tipo+" - confId: "+conf.getIdConferenza());
					emailRepositoryService.registerInitialMessag(metadati, DbConst.EMAIL_STATUS_ERRORETRASMISSIONE, "Errore Trasmissione");
				}
				else {
					emailRepositoryService.registerInitialMessag(metadati, DbConst.EMAIL_STATUS_CONSEGNA, "Trasmissione");
					LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoObserverDomusExitListnerInterface]: PEC dispatch OK" +messaggio+" - "+tipo+" - confId: "+conf.getIdConferenza());
				}
			}
			
			LOGGER.debug("@paleo doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);
			//il registro documento non contiene ancora i riferimenti al protocollo esterno..
			PaleoRegistryAdapter adapter=saveResponseToRegitryAdapter( registroDocumento,out);

			PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
			ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto,HttpStatus.OK);		
			return risposta;
		} else {
			oggetto = getOggettoForConference(conf, idTipoEvento);
			paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza outPaleoGiunta = null;
			if(!isDocumentoInterno || idTipoEvento.equals("idTipoEvento")) {
				if (idTipoEvento.equals(clientConfiguration.getComunicazioneGenerica())) {
					LOGGER.debug("@paleo doUploadCustomProtocolSingleFile - Comunicazione Generica utilizzo la lista partecipanti");
					List<Mailer> listMail = getMailerList(registroDocumento);
					
					List<Partecipante> listPartecipante = registroDocumento.getDocumento().getVisibilitaPartecipanti();
					
					outPaleoGiunta = paleoAdapterClient.doSubmitFileToExitProtocolServicePaleoGiunta(registroDocumento, integDTO, getListCorrispondetiDuePaleoGiunta(listPartecipante, true), oggetto, cfResp);
					
				} else {
					outPaleoGiunta = paleoAdapterClient.doSubmitFileToExitProtocolServicePaleoGiunta(registroDocumento, integDTO, getListCorrispondetiPaleoGiunta(registroDocumento, true), oggetto, cfResp);
				}					
			} else {
				return paleoDocumentaleService.doUploadCustomProtocolSingleFile(registroDocumento,integDTO, idTipoEvento);
			}
			
			String messaggio=outPaleoGiunta.getMessaggioRisultato().getDescrizione();
			String tipo=outPaleoGiunta.getMessaggioRisultato().getTipoRisultato().getValue();	
			
			if("Error".equals(tipo)){			
				LOGGER.debug("@paleo [ERRORE] : doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);		
				
				PaleoDocumentAdapterDTO dto = new PaleoDocumentAdapterDTO();
				dto.setPaleoError(messaggio);
				return new ResponseEntity(dto,HttpStatus.EXPECTATION_FAILED);
			}
			else {
				LOGGER.debug("@paleo doUploadExitProtocolSingleFile sending PEC: " +messaggio+" - "+tipo);		
				
				// xmf: sending PEC through Paleo
				skipMeetpadMailDispatch = true;
				
				MailMetadata metadati = new MailMetadata("");
				
				metadati.setIdConferenza(conf.getIdConferenza());
				metadati.setSubject("Mail inviata da PALEO");
				metadati.setMessage("Mail inviata da PALEO");
				//metadati.setMessageId(messageId); // TODO: generate a GUID
				
				if(!paleoAdapterClient.doProtocolDispatchService(outPaleoGiunta.getSegnatura(), isUSR)) {
					LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoObserverDomusExitListnerInterface]: PEC dispatch error " +messaggio+" - "+tipo+" - confId: "+conf.getIdConferenza());
					emailRepositoryService.registerInitialMessag(metadati, DbConst.EMAIL_STATUS_ERRORETRASMISSIONE, "Errore Trasmissione");
				}
				else {
					emailRepositoryService.registerInitialMessag(metadati, DbConst.EMAIL_STATUS_CONSEGNA, "Trasmissione");
					LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoObserverDomusExitListnerInterface]: PEC dispatch OK" +messaggio+" - "+tipo+" - confId: "+conf.getIdConferenza());
				}
			}
			
			LOGGER.debug("@paleo doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);
			//il registro documento non contiene ancora i riferimenti al protocollo esterno..
			PaleoRegistryAdapter adapter=saveResponseToRegitryAdapterPaleoGiunta(registroDocumento,outPaleoGiunta);

			PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
			ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto,HttpStatus.OK);		
			return risposta;
			
		}
		
		
		
	}
		
}
