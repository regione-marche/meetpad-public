package conferenza.adapder.integrazioni.paleo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import conferenza.DTO.ListaAccreditamentoDTO;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoDocumentAdapterDTO;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoClientConfiguration;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;
import conferenza.model.Conferenza;
import conferenza.model.Evento;
import conferenza.model.Mailer;
import conferenza.model.Partecipante;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.model.ObserverRegistryListener;
import conferenza.service.AccreditamentoService;
import conferenza.util.DbConst;
import it.marche.regione.paleo.services.Corrispondente;
import it.marche.regione.paleo.services.DatiCorrispondente;
import it.marche.regione.paleo.services.RespProtocolloArrivo;
import it.marche.regione.paleo.services.RespProtocolloPartenza;





@Component("PaleoObserverIncomingListnerInterface")
public class PaleoIncomingProtocolService extends PaleoIntegProtocolService{

	private static final Logger LOGGER = LoggerFactory.getLogger(PaleoIncomingProtocolService.class);
	
	@Autowired
	PaleoDocumentaleIntraProtocolService paleoDocumentaleService;

	@Autowired 
	PaleoClientConfiguration clientConfiguration;

	@Autowired
	AccreditamentoService accreditamentoService;

	@Override
	public List<ObserverRegistryListener> findAllDocToSubmitToProtocol() {
		return  super.findAllDocToSubmitToProtocolInIngresso();
	}

	@Override
	public void doAsincronousTask() {
		super.submitProtocol(); 		
	}		
	
	/**
	 * [ATTENZIONE]
	 * Se Esiste un filtro che associa una tipologia conferenza a PaleoDocumentale..
	 * invece della protocollazione viene eseguita la storicizzazione come documento interno
	 */
	@Override
	protected ResponseEntity<PaleoDocumentAdapterDTO> doUploadCustomProtocolSingleFile(RegistroDocumento registroDocumento, IntegProtocolloDTO integDTO, String idTipoEvento) 
			throws ServiceException, IOException, SOAPException {
		
		RespProtocolloArrivo out=null;
		//gestione dei focumenti interni paleo
		boolean isDocumentoInterno=skipIfItIsDocumentoInterno(registroDocumento.getId());
		if(isDocumentoInterno==false) {
			Conferenza conf = registroDocumento.getDocumento().getConferenza();
			//Cerchiamo i partecipanti, proviamo a trovare un match con quello che ha creato l'evento e lo passiamo come utente occasionale. Punto 2 ticket 114

			List<Evento> eventi = new ArrayList<Evento>();
			//partecipanti = conf.getPartecipantes();

			eventi = conf.getEventi();
			Evento evento = null;
			Partecipante mittente = null;
			LOGGER.debug("PaleoIncomingProtocolService: l'idTipoEvento è: " + idTipoEvento);
			for(int i = 0; i<eventi.size(); i++) {
				evento = eventi.get(i);
				LOGGER.debug("PaleoIncomingProtocolService: l'evento è: " + evento.getTipoEvento().getCodice());
				if(evento.getTipoEvento().getCodice().equals(idTipoEvento)) {
					mittente = evento.getMittente();
					LOGGER.debug("PaleoIncomingProtocolService: Il mittente dentro il for è: " + mittente);
					break;
				}
			}
			
			LOGGER.debug("PaleoIncomingProtocolService: Il mittente fuori dal for è: " + mittente);
			
			String cfResp = conf.getCodiceFiscaleResponsabileConferenza();
			LOGGER.debug("@paleo doUploadExitProtocolSingleFile[cfResp]: " + cfResp);
			LOGGER.debug("@paleo doUploadExitProtocolSingleFile[PaleoDomusObserverInOutListnerInterface]: " + conf.getIdConferenza());
			
			boolean isUSR = conf.getTipologiaConferenzaSpecializzazione().getCodice().equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS);
			
			List<Mailer> listMail = getMailerList(registroDocumento);
			
			String oggetto =null;
			
			
			
			if (isUSR) {
				oggetto = getOggettoForConferenceUSR(conf, idTipoEvento);
			
				// inizio modifica S.D.
				if (idTipoEvento.equals(clientConfiguration.getRichiestaIntegrazioni())) {
					LOGGER.debug(
							"@paleo doUploadCustomProtocolSingleFile - Richiesta Integrazioni - ricerco tutti gli enti/utenti accreditati");
					ListaAccreditamentoDTO listaAccreditamento = accreditamentoService
							.findAccreditamentiConferenza(conf.getIdConferenza());
					LOGGER.debug(
							"@paleo doUploadCustomProtocolSingleFile - Richiesta Integrazioni - lista enti/utenti accreditati recuperata");
					out = paleoAdapterClient.doSubmitFileToIncomingProtocolService(registroDocumento, integDTO,
							getMittenteResponsabile(mittente),
							getListCorrispondetiAccreditati(listaAccreditamento.getList(), true), oggetto, isUSR,
							cfResp);
				} else if (idTipoEvento.equals(clientConfiguration.getEspressionePareri())) {
					LOGGER.debug("@paleo doUploadCustomProtocolSingleFile - Espressione Pareri - notifica solo USR");
					Corrispondente corrispondenteUsr = new Corrispondente();
					DatiCorrispondente datiCorrispondente = new DatiCorrispondente();
					String cognome = conf.getAmministrazioneProcedente().getDescrizioneEnte()
							.replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
					if (cognome.length() > 100)
						cognome = cognome.substring(0, 100);
					datiCorrispondente.setCognome(cognome);
					datiCorrispondente.setEmail(conf.getAmministrazioneProcedente().getPecEnte());
					LOGGER.debug("Indirizzo mail/pec amministrazione procedente: "
							+ conf.getAmministrazioneProcedente().getPecEnte());
					corrispondenteUsr.setCorrispondenteOccasionale(datiCorrispondente);
					Corrispondente[] listMailUsr = new Corrispondente[] { corrispondenteUsr };
					out = paleoAdapterClient.doSubmitFileToIncomingProtocolService(registroDocumento, integDTO,
							getMittenteResponsabile(mittente), listMailUsr, oggetto, isUSR, cfResp);
				} else {
					out = paleoAdapterClient.doSubmitFileToIncomingProtocolService(registroDocumento, integDTO,
							getMittenteResponsabile(mittente), getListCorrispondeti(listMail, true), oggetto, isUSR,
							cfResp);
				}
				
				
				String messaggio=out.getMessaggioRisultato().getDescrizione();
				String tipo=out.getMessaggioRisultato().getTipoRisultato().getValue();		
				
				if("Error".equals(tipo)){			
					LOGGER.debug("@paleo [ERRORE] : doUploadCustomProtocolSingleFile: " +messaggio+" - "+tipo);		
					
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
				

			//FINE MODIFICA

			//DA RIPRISTINARE IL PRIMO OUT SE NON FUNZIONA LA SOLUZIONE DI SANDRO E COMMENTARE IL CODICE COMPRESO TRA INIZIO MODIFICA SD-FINE MODIFICA
			//out = paleoAdapterClient.doSubmitFileToIncomingProtocolService( registroDocumento,  integDTO, getMittenteResponsabile(mittente), getListCorrispondeti(listMail, true), null, isUSR, cfResp);
			//out = paleoAdapterClient.doSubmitFileToIncomingProtocolService( registroDocumento,  integDTO, getCorrispondeteResponsabile(listMail), getListCorrispondeti(listMail, true), null, isUSR, cfResp);
			//out = paleoAdapterClient.doSubmitFileToIncomingProtocolService( registroDocumento,  integDTO); // xmf: REPLACED TO FIX "GRMA paleo rubrica" issue  
			} else {
				paleoGiunta.it.marche.regione.paleo.services.RespProtocolloArrivo outPaleoGiunta = null;
				if (idTipoEvento.equals(clientConfiguration.getRichiestaIntegrazioni())) {
					LOGGER.debug(
							"@paleo doUploadCustomProtocolSingleFile - Richiesta Integrazioni - ricerco tutti gli enti/utenti accreditati");
					ListaAccreditamentoDTO listaAccreditamento = accreditamentoService
							.findAccreditamentiConferenza(conf.getIdConferenza());
					LOGGER.debug(
							"@paleo doUploadCustomProtocolSingleFile - Richiesta Integrazioni - lista enti/utenti accreditati recuperata");
					outPaleoGiunta = paleoAdapterClient.doSubmitFileToIncomingProtocolServiceGiunta(registroDocumento, integDTO,
							getMittenteResponsabileGiunta(mittente),
							getListCorrispondetiAccreditatiPaleoGiunta(listaAccreditamento.getList(), true), oggetto,
							cfResp);
				} else if (idTipoEvento.equals(clientConfiguration.getEspressionePareri())) {
					LOGGER.debug("@paleo doUploadCustomProtocolSingleFile - Espressione Pareri - notifica solo USR");
					paleoGiunta.it.marche.regione.paleo.services.Corrispondente corrispondenteUsr = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente();
					paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente datiCorrispondente = new paleoGiunta.it.marche.regione.paleo.services.DatiCorrispondente();
					String cognome = conf.getAmministrazioneProcedente().getDescrizioneEnte()
							.replaceAll("[/\\.@#$%*'-]", "").replaceAll("[ ]+", " ");
					if (cognome.length() > 100)
						cognome = cognome.substring(0, 100);
					datiCorrispondente.setCognome(cognome);
					datiCorrispondente.setEmail(conf.getAmministrazioneProcedente().getPecEnte());
					LOGGER.debug("Indirizzo mail/pec amministrazione procedente: "
							+ conf.getAmministrazioneProcedente().getPecEnte());
					corrispondenteUsr.setCorrispondenteOccasionale(datiCorrispondente);
					paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] listMailUsr = new paleoGiunta.it.marche.regione.paleo.services.Corrispondente[] { corrispondenteUsr };
					outPaleoGiunta = paleoAdapterClient.doSubmitFileToIncomingProtocolServiceGiunta(registroDocumento, integDTO,
							getMittenteResponsabileGiunta(mittente), listMailUsr, oggetto,  cfResp);
				} else {
					outPaleoGiunta = paleoAdapterClient.doSubmitFileToIncomingProtocolServiceGiunta(registroDocumento, integDTO,
							getMittenteResponsabileGiunta(mittente), getListCorrispondetiPaleoGiunta(listMail, true), oggetto,
							cfResp);
				}
				
				String messaggio=outPaleoGiunta.getMessaggioRisultato().getDescrizione();
				String tipo=outPaleoGiunta.getMessaggioRisultato().getTipoRisultato().getValue();		
				
				if("Error".equals(tipo)){			
					LOGGER.debug("@paleo [ERRORE] : doUploadCustomProtocolSingleFileGiunta: " +messaggio+" - "+tipo);		
					
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
		else  {
			return paleoDocumentaleService.doUploadCustomProtocolSingleFile(registroDocumento,integDTO, idTipoEvento);
		}
			
		
						
		

	}	
}
