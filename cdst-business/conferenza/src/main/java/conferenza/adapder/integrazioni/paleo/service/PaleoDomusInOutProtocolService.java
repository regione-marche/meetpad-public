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
import conferenza.adapder.integrazioni.paleo.adapter.PaleoClientConfiguration;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.Mailer;
import conferenza.model.Partecipante;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.util.DbConst;
import it.marche.regione.paleo.services.Corrispondente;
import it.marche.regione.paleo.services.RespProtocollo;

@Component("PaleoDomusObserverInOutListnerInterface")
public class PaleoDomusInOutProtocolService extends PaleoDomusExitProtocolService {

	@Autowired
	PaleoDocumentaleIntraProtocolService paleoDocumentaleService;
	
	@Autowired 
	PaleoClientConfiguration clientConfiguration;

	private static final Logger LOGGER = LoggerFactory.getLogger(PaleoDomusInOutProtocolService.class);
	@Override
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol() {
		return  super.findAllDocToSubmitToProtocolInUscitaDOMUS("SCRITTURA-PROTOCOLLO-DOMUS-PALEO-INOUT");
	}

	@Override
	public void doAsincronousTask() {
		super.submitProtocol(); 		
	}
	
	private boolean isDocumentOwnerAmministrazioneProcedente(Documento documento) {

		return documento.getOwner().getPartecipante().getEnte().getIdEnte().equals( 		
				documento.getConferenza().getAmministrazioneProcedente().getIdEnte()
			);
	}
	
	/**
	 * [ATTENZIONE]
	 * Se Esiste un filtro che associa una tipologia conferenza a PaleoDocumentale..
	 * invece della protocollazione viene eseguita la storicizzazione come documento interno
	 * 
	 */
	@Override
	protected ResponseEntity<PaleoDocumentAdapterDTO> doUploadCustomProtocolSingleFile(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, String idTipoEvento) throws ServiceException, IOException, SOAPException {
		RespProtocollo out=null;
		
		//gestione dei focumenti interni paleo
		boolean isDocumentoInterno=skipIfItIsDocumentoInterno(registroDocumento.getId());
		if(isDocumentoInterno==false) {
			Conferenza conf = registroDocumento.getDocumento().getConferenza();
			if(isDocumentOwnerAmministrazioneProcedente(registroDocumento.getDocumento())) {
				LOGGER.debug("@paleo doUploadCustomProtocolSingleFile - richiamo la protocollazione in uscita per la conferenza: " + + conf.getIdConferenza());
				return super.doUploadCustomProtocolSingleFile(registroDocumento, integDTO, idTipoEvento);
			}else {
				
				String cfResp = conf.getCodiceFiscaleResponsabileConferenza();
				LOGGER.debug("@paleo doUploadCustomProtocolSingleFile[cfResp]: " + cfResp);
				LOGGER.debug("@paleo doUploadCustomProtocolSingleFile[PaleoDomusObserverInOutListnerInterface]: " + conf.getIdConferenza());
				
				boolean isUSR = conf.getTipologiaConferenzaSpecializzazione().getCodice().equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS);
				
				List<Mailer> listMail = getMailerList(registroDocumento);
				
				String oggetto = null;
				if (isUSR) {
					oggetto = getOggettoForConferenceUSR(conf, idTipoEvento);

					// inizio modifica S.D.
					if (idTipoEvento.equals(clientConfiguration.getComunicazioneGenerica())) {
						LOGGER.debug(
								"@paleo doUploadCustomProtocolSingleFile - Comunicazione Generica utilizzo la lista partecipanti");
						List<Partecipante> listPartecipante = registroDocumento.getDocumento()
								.getVisibilitaPartecipanti();
						out = paleoAdapterClient.doSubmitFileToIncomingProtocolService(registroDocumento, integDTO,
								getCorrispondeteResponsabile(listMail), getListCorrispondetiDue(listPartecipante, true),
								oggetto, isUSR, cfResp);
					} else {
						out = paleoAdapterClient.doSubmitFileToIncomingProtocolService(registroDocumento, integDTO,
								getCorrispondeteResponsabile(listMail), getListCorrispondeti(listMail, true), oggetto,
								isUSR, cfResp);
					}
						
					// fine modifica

					// out = paleoAdapterClient.doSubmitFileToIncomingProtocolService(
					// registroDocumento, integDTO); // xmf: REPLACED TO FIX "GRMA paleo rubrica"
					// issue
					
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
					PaleoRegistryAdapter adapter=saveResponseToRegitryAdapter( registroDocumento, out);			

					PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
					ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto, HttpStatus.OK);		
					return risposta;
				} else {
					paleoGiunta.it.marche.regione.paleo.services.RespProtocollo outPaleoGiunta= null;
					if (idTipoEvento.equals(clientConfiguration.getComunicazioneGenerica())) {
						LOGGER.debug(
								"@paleo doUploadCustomProtocolSingleFileGiunta - Comunicazione Generica utilizzo la lista partecipanti");
						List<Partecipante> listPartecipante = registroDocumento.getDocumento()
								.getVisibilitaPartecipanti();
						outPaleoGiunta = paleoAdapterClient.doSubmitFileToIncomingProtocolServiceGiunta(registroDocumento, integDTO,
								getCorrispondeteResponsabileGiunta(listMail), getListCorrispondetiDuePaleoGiunta(listPartecipante, true),
								oggetto, cfResp);
					} else {
						outPaleoGiunta = paleoAdapterClient.doSubmitFileToIncomingProtocolServiceGiunta(registroDocumento, integDTO,
								getCorrispondeteResponsabileGiunta(listMail), getListCorrispondetiPaleoGiunta(listMail, true), oggetto,
								cfResp);
					}
					
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
					PaleoRegistryAdapter adapter=saveResponseToRegitryAdapter( registroDocumento, outPaleoGiunta);			

					PaleoDocumentAdapterDTO dto=PaleoDocumentAdapterDTO.fillPaleoDocumentAdapterDTO(adapter);
					ResponseEntity<PaleoDocumentAdapterDTO> risposta=new ResponseEntity(dto, HttpStatus.OK);		
					return risposta;
				}
			}
			
		}else {
			return paleoDocumentaleService.doUploadCustomProtocolSingleFile(registroDocumento,integDTO, idTipoEvento);
		}
			
		
		
	}
		
}
