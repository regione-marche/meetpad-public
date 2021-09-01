package conferenza.adapder.integrazioni.paleo.service;

import java.io.IOException;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAdapterDTO;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;
import conferenza.model.Conferenza;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.util.DbConst;
import it.marche.regione.paleo.services.RespProtocolloPartenza;

@Component("PaleoObserverExitListnerInterface")
public class PaleoExitProtocolService extends PaleoIntegProtocolService{

	@Autowired
	PaleoDocumentaleIntraProtocolService paleoDocumentaleService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PaleoExitProtocolService.class);
	@Override
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol() {
		return  super.findAllDocToSubmitToProtocolInUscita();
	}

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
		
		Conferenza conf = registroDocumento.getDocumento().getConferenza(); // QUA PRENDO IL RESPONSABILE
		String cfResp = conf.getCodiceFiscaleResponsabileConferenza();
		LOGGER.debug("@paleo doUploadExitProtocolSingleFile[cfResp]: " + cfResp);
		LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoObserverExitListnerInterface]: " + conf.getIdConferenza());
		
		boolean isUSR = conf.getTipologiaConferenzaSpecializzazione().getCodice().equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS);
		
		String oggetto = null;
		if (isUSR) {
			oggetto = getOggettoForConferenceUSR(conf, idTipoEvento);
		} else {
			oggetto = getOggettoForConference(conf, idTipoEvento);
		}
		
		//gestione dei focumenti interni paleo
		boolean isDocumentoInterno=skipIfItIsDocumentoInterno(registroDocumento.getId());
		if(isDocumentoInterno==false) {			
			if (isUSR) {
				out=paleoAdapterClient.doSubmitFileToExitProtocolService( registroDocumento,  integDTO, getListCorrispondeti(registroDocumento, false), oggetto, isUSR, cfResp); //AGGIUNGERE QUA IL RESPONSABILE
			
				String messaggio=out.getMessaggioRisultato().getDescrizione();
				String tipo=out.getMessaggioRisultato().getTipoRisultato().getValue();	
				
				if("Error".equals(tipo)){			
					LOGGER.debug("[ERRORE] : doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);		
					PaleoDocumentAdapterDTO dto = new PaleoDocumentAdapterDTO();
					dto.setPaleoError(messaggio);
					return new ResponseEntity(dto,HttpStatus.EXPECTATION_FAILED);
				}
				
				LOGGER.debug("doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);
				//il registro documento non contiene ancora i riferimenti al protocollo esterno..
				PaleoRegistryAdapter adapter=saveResponseToRegitryAdapter( registroDocumento,out);

				PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
				ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto, HttpStatus.OK);		
				return risposta;
			} else {
				paleoGiunta.it.marche.regione.paleo.services.RespProtocolloPartenza outPaleoGiunta=null;
				outPaleoGiunta=paleoAdapterClient.doSubmitFileToExitProtocolServicePaleoGiunta( registroDocumento,  integDTO, 
						getListCorrispondetiPaleoGiunta(registroDocumento, false), oggetto,  cfResp); //AGGIUNGERE QUA IL RESPONSABILE
				
				String messaggio=outPaleoGiunta.getMessaggioRisultato().getDescrizione();
				String tipo=outPaleoGiunta.getMessaggioRisultato().getTipoRisultato().getValue();	
				
				if("Error".equals(tipo)){			
					LOGGER.debug("[ERRORE] : doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);		
					PaleoDocumentAdapterDTO dto = new PaleoDocumentAdapterDTO();
					dto.setPaleoError(messaggio);
					return new ResponseEntity(dto,HttpStatus.EXPECTATION_FAILED);
				}
				
				LOGGER.debug("doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);
				//il registro documento non contiene ancora i riferimenti al protocollo esterno..
				PaleoRegistryAdapter adapter=saveResponseToRegitryAdapter( registroDocumento,outPaleoGiunta);

				PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
				ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto, HttpStatus.OK);		
				return risposta;
			}
			
			
		} else {
			return paleoDocumentaleService.doUploadCustomProtocolSingleFile(registroDocumento,integDTO, idTipoEvento);
		}			
		
	}
		
}
