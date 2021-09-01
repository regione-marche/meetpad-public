package conferenza.adapder.integrazioni.paleo.service;

import java.io.IOException;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.marche.regione.paleo.services.RespDocumento;
import it.marche.regione.paleo.services.RespProtocolloPartenza;

/**
 * Gestione della documentazione interna..
 * 
	 * Paleo Vine usato come Documentale:
	 * do Submit File To Archivia Documento Interno
	 * Le altre possibilità sono per la Protocollazione.
	 * Quindi si hanno due alternative per PAleo: aut Sistema protocollante, aut documentale
	 * 
	 * L'uso come Documentale è un Parametro
	 * La configurazione si fà tramite la tabella 
	 * 			paleo_registry_filter
	 * vengono specificati:
	 * 1) la tipologia di conferenza (specializzazione)
	 * 2) il tipo di filtro
	 * 3) il codice di riferimento sull'observer registry per individuare la classe che deve farne il marshaler
	 * 4) la tipologia di documento
 * 
 * @author guideluc
 *
 */
@Component("PaleoObserverDocumentaleIntraListnerInterface")
public class PaleoDocumentaleIntraProtocolService  extends PaleoIntegProtocolService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PaleoExitProtocolService.class);
	@Override
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol() {
		return  super.findAllDocToSubmitToIntraDocument();
	}

	@Override
	public void doAsincronousTask() {
		super.submitProtocol(); 		
	}
	
	protected ResponseEntity<PaleoDocumentAdapterDTO> doUploadCustomProtocolSingleFile(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, String idTipoEvento) throws ServiceException, IOException, SOAPException {
		
		Conferenza conf = registroDocumento.getDocumento().getConferenza();
		String cfResp = conf.getCodiceFiscaleResponsabileConferenza();
		LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoObserverDomusExitListnerInterface]: " + conf.getIdConferenza());
		boolean isUSR = conf.getTipologiaConferenzaSpecializzazione().getCodice().equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS);
		
		if (isUSR) {
			RespDocumento out=paleoAdapterClient.doSubmitFileToIntraDocumentalService( registroDocumento,  integDTO, isUSR, cfResp);				
			String messaggio=out.getMessaggioRisultato().getDescrizione();
			String tipo=out.getMessaggioRisultato().getTipoRisultato().getValue();		
			LOGGER.debug("doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);

			if("Error".equals(tipo)){			
				LOGGER.debug("@paleo [ERRORE] : doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);		
				
				PaleoDocumentAdapterDTO dto = new PaleoDocumentAdapterDTO();
				dto.setPaleoError(messaggio);
				return new ResponseEntity(dto, HttpStatus.EXPECTATION_FAILED);
			}
			else {
				//il registro documento non contiene ancora i riferimenti al protocollo esterno..
				PaleoRegistryAdapter adapter=saveResponseToRegitryAdapter( registroDocumento,out);
				PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
				ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto, HttpStatus.OK);		
				return risposta;
			}
		} else {
			paleoGiunta.it.marche.regione.paleo.services.RespDocumento outPaleoGiunta= paleoAdapterClient.doSubmitFileToIntraDocumentalServicePaleoGiunta( registroDocumento,  integDTO, cfResp);
			String messaggio=outPaleoGiunta.getMessaggioRisultato().getDescrizione();
			String tipo=outPaleoGiunta.getMessaggioRisultato().getTipoRisultato().getValue();		
			LOGGER.debug("doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);

			if("Error".equals(tipo)){			
				LOGGER.debug("@paleo [ERRORE] : doUploadExitProtocolSingleFile: " +messaggio+" - "+tipo);		
				
				PaleoDocumentAdapterDTO dto = new PaleoDocumentAdapterDTO();
				dto.setPaleoError(messaggio);
				return new ResponseEntity(dto, HttpStatus.EXPECTATION_FAILED);
			}
			else {
				//il registro documento non contiene ancora i riferimenti al protocollo esterno..
				PaleoRegistryAdapter adapter=saveResponseToRegitryAdapter( registroDocumento,outPaleoGiunta);
				PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
				ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto, HttpStatus.OK);		
				return risposta;
			}
		}
		
		
	}
}
