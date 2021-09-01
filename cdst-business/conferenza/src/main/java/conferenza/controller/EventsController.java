package conferenza.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.bcel.classfile.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EventoCompletoDTO;
import conferenza.DTO.EventoFileDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaEventoDTO;
import conferenza.DTO.ListaPecDTO;
import conferenza.DTO.ModificaDataDTO;
import conferenza.DTO.PageableDTO;
import conferenza.DTO.RicercaEventoDTO;
import conferenza.DTO.RicercaPecDTO;
import conferenza.DTO.RispostaEventoCompletoDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.DTO.RispostaListaEventoDTO;
import conferenza.DTO.RispostaListaLabelValueDTO;
import conferenza.DTO.RispostaListaPecDTO;
import conferenza.exception.NotFoundEx;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.Evento;
import conferenza.model.ModificaData;
import conferenza.model.TipoEvento;
import conferenza.protocollo.service.ProtocolloService;
import conferenza.service.CaricaComboService;
import conferenza.service.EventoService;
import conferenza.util.DbConst;
import conferenza.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = { "Events API" })
public class EventsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	EventoService eventoService;

	@Autowired
	CaricaComboService comboService;
	
	@Autowired
	ProtocolloService protocolloService;
	
	@GetMapping(value = "/conferences/{idConference}/events")
	@ApiOperation(value = "get events for conference identified by id")
	public RispostaListaEventoDTO findAll(@PathVariable Integer idConference, PageableDTO pageableDTO) {
		LOGGER.debug("Class: EventsController - Method: getEvents");
		ListaEventoDTO lista = this.eventoService.findAll(idConference, pageableDTO);
		RispostaListaEventoDTO risposta = new RispostaListaEventoDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	/**
	 * Restituisce una lista di tutti gli eventi in una data conferenza filtrati in base ai parametri di ricerca; 
	 * se non presenti restituisce tutti gli eventi della conferenza
	 * @param ricerca
	 * @param idConference
	 * @return
	 */
	@GetMapping(value = "/conferences/{idConference}/eventResearches")
	@ApiOperation(value = "list of events meet the search parameters in query string")
	public RispostaListaEventoDTO findEventsByFilter(RicercaEventoDTO ricerca, @PathVariable Integer idConference) {
		LOGGER.debug("Class: EventsController - Method: getEventsByFilter");
		ListaEventoDTO lista = this.eventoService.findEventsByFilter(idConference, ricerca);
		RispostaListaEventoDTO risposta = new RispostaListaEventoDTO();
		risposta.setData(lista);
		return risposta;
	}

	@GetMapping(value = "/conferences/{idConference}/events/{idEvent}")
	@ApiOperation(value = "get event detail with idEvent for conference identified by id")
	public RispostaEventoCompletoDTO findEventDetail(@PathVariable Integer idConference,
			@PathVariable Integer idEvent) {
		LOGGER.debug("Class: EventsController - Method: getEventDetail");
		EventoCompletoDTO eventoCompletoDTO = this.eventoService.findEvent(idEvent);
		RispostaEventoCompletoDTO risposta = new RispostaEventoCompletoDTO();
		risposta.setData(eventoCompletoDTO);
		return risposta;
	}

	@GetMapping(value = "/conferences/{idConference}/pec")
	@ApiOperation(value = "get pec for conference identified by id")
	public RispostaListaPecDTO findPec(@PathVariable Integer idConference, RicercaPecDTO ricerca) {
		LOGGER.debug("Class: EventsController - Method: getPec");
		ListaPecDTO lista = this.eventoService.findPec(idConference, ricerca);
		RispostaListaPecDTO risposta = new RispostaListaPecDTO();
		risposta.setData(lista);
		return risposta;
	}

	@GetMapping(value = "/conferences/{idConference}/eventTypes")
	@ApiOperation(value = "get eventTypes allowed for idConference and user role")
	public RispostaListaLabelValueDTO findConferenceEventTypes(@PathVariable Integer idConference) {
		LOGGER.debug("Class: EventsController - Method: findConferenceEventTypes");
		return comboService.findConferenceEventTypes(idConference);
	}

	@PostMapping(value = "/conferences/{idConference}/events")
	@ApiOperation(value = "insert event for conference identified by id")
	public RispostaIdentifiableDTO creaEvento(@PathVariable Integer idConference, @RequestParam String type,
			@RequestParam(required = false) String body, @RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String name, @RequestParam(required = false) String category,
			@RequestParam(required = false) String protocolNumber, @RequestParam(required = false) String protocolDate,
			@RequestParam(required = false) String result, @RequestParam(required = false) String notes,
			@RequestParam(required = false) String recipients, @RequestParam(required = false) String visibilities,
			@RequestParam(required = false) String determinationType, @RequestParam(required = false) String cityReference,
			//@RequestParam(required = false) String dateType, @RequestParam(required = false) String newDate,
			@RequestParam(required = false) MultipartFile[] attachments,
			@RequestParam(required = false) String newDateTermine,
			@RequestParam(required = false) String newDateTermineEsprPareri,
			@RequestParam(required = false) String newDateTermineRichInteg,
			@RequestParam(required = false) String newDatePrimaSessioneSimultanea)
			throws MessagingException {
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setAttachments(attachments);
		documentoFileDTO.setCategoria(category);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setVisibilitaPartecipanti(visibilities);
		documentoFileDTO.setCompetenzaTerritoriale(cityReference);
		LOGGER.debug("@docs: " + documentoFileDTO.toString());
		EventoFileDTO eventoFileDTO = new EventoFileDTO();
		eventoFileDTO.setCorpo(body);
		eventoFileDTO.setTipoEvento(type);
		eventoFileDTO.setProtocollo(protocolNumber);
		eventoFileDTO.setNote(notes);
		eventoFileDTO.setResult(result);
		eventoFileDTO.setTipoParere(determinationType);
		eventoFileDTO.setListaDestinatari(JsonUtil.jsonToListLabelValue(recipients));
		
		
		//L'evento di tipo data può portare alla creazione di più eventi e pertanto si utilizza un metodo a parte
		Conferenza conferenza = this.eventoService.getConferenza(idConference);
		if (conferenza == null) {
			throw new NotFoundEx("la conferenza non e' stata trovata");
		}
		
		TipoEvento tipoEvento = this.eventoService.getTipoEvento(eventoFileDTO);
		LOGGER.debug("@docs event type: " + tipoEvento.getDescrizione());

		Evento evento = null;
		List<ModificaData> modificheDate = new ArrayList<ModificaData>();
		//se il tipo di evento è modifica data, va salvato sul database la nova data della conferenza e si
		//possono creare più eventi
		if(tipoEvento.getCodice().equalsIgnoreCase(String.valueOf(DbConst.TIPOLOGIA_EVENTO_MODIFICA_DATA))) {	
			int ordine = this.eventoService.getMaxOrdineModifcataData(idConference);
			ordine += 1;
			
			Documento docEsistente = null;
			if (newDateTermine != null && !this.eventoService.ConvertStringToDate(newDateTermine).equals(conferenza.getDataTermine())) {
				evento = this.eventoService.creaEventoDate(eventoFileDTO, conferenza, 
						documentoFileDTO, tipoEvento,newDateTermine,DbConst.DATA_TERMINE,ordine,docEsistente);
				if (evento != null && evento.getModificaData() != null) {
					modificheDate.add(evento.getModificaData());
				}
			}
			if (newDateTermineEsprPareri != null && !this.eventoService.ConvertStringToDate(newDateTermineEsprPareri).equals(conferenza.getTermineEspressionePareri())) {
				
				if (evento != null && evento.getDocumento() != null) {
					docEsistente = evento.getDocumento();
				}
				evento = this.eventoService.creaEventoDate(eventoFileDTO, conferenza, 
						documentoFileDTO, tipoEvento,newDateTermineEsprPareri,DbConst.DATA_TERMINE_ESPRESSIONE_PARERI,ordine,docEsistente);
				if (evento != null && evento.getModificaData() != null) {
					modificheDate.add(evento.getModificaData());
				}
			}
			if (newDateTermineRichInteg != null && !this.eventoService.ConvertStringToDate(newDateTermineRichInteg).equals(conferenza.getTermineRichiestaIntegrazioniConferenza())) {
				if (evento != null && evento.getDocumento() != null) {
					docEsistente = evento.getDocumento();
				}
				evento = this.eventoService.creaEventoDate(eventoFileDTO, conferenza, 
						documentoFileDTO, tipoEvento,newDateTermineRichInteg,DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA,ordine,docEsistente);
				if (evento != null && evento.getModificaData() != null) {
					modificheDate.add(evento.getModificaData());
				}
			}
			if (newDatePrimaSessioneSimultanea != null && !this.eventoService.ConvertStringToDate(newDatePrimaSessioneSimultanea).equals(conferenza.getPrimaSessioneSimultanea())) {
				if (evento != null && evento.getDocumento() != null) {
					docEsistente = evento.getDocumento();
				}
				evento = this.eventoService.creaEventoDate(eventoFileDTO, conferenza, 
						documentoFileDTO, tipoEvento,newDatePrimaSessioneSimultanea,DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA,ordine,docEsistente);
				if (evento != null && evento.getModificaData() != null) {
					modificheDate.add(evento.getModificaData());
				}
			}
			
		} else {
			evento = this.eventoService.creaEvento(eventoFileDTO, conferenza, 
					documentoFileDTO, tipoEvento);
			LOGGER.debug("@docs eventId: " + evento.getId());
		}
		
		
		/*
		 * l'invio delle mail (essendo asincrono) deve essere chiamato solo dopo la
		 * conclusione della transazione di creazione dell'evento e quindi nel
		 * controller
		 */
		try {
			// xmf: if there is a document for which is pending a Paleo protocol request then 
			// don't send the email and send it after the protocol is generated 
			if(tipoEvento.getCodice().equalsIgnoreCase(String.valueOf(DbConst.TIPOLOGIA_EVENTO_MODIFICA_DATA))) {
				/*
				if(evento.getDocumento() != null || 
						(evento.getDocumento() != null && protocolloService.findAllDocuments(evento.getDocumento().getIdDocumento()).size() > 0)) {
					eventoService.notificaMail(evento, evento.getDocumento(), eventoFileDTO.getListaDestinatari(),modificheDate);				
				}
				*/
			} else {
				if(evento.getDocumento() == null || protocolloService.findAllDocuments(evento.getDocumento().getIdDocumento()).size() == 0) {
					eventoService.notificaMail(evento, evento.getDocumento(), eventoFileDTO.getListaDestinatari(),null);				
				}	
			}
			
		} catch (MessagingException e) {
			LOGGER.debug("@docs exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
		}

		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(evento.getId());
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		
		LOGGER.debug("@docs res ok");
		return risposta;
	}
	@PostMapping(value = "/conferences/{idConference}/eventsSendToSign")
	@ApiOperation(value = "insert event for conference identified by id")
	public RispostaIdentifiableDTO creaEventoInviaAllaFirma(@PathVariable Integer idConference, @RequestParam String type,
			@RequestParam(required = false) String body, @RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String name, @RequestParam(required = false) String category,
			@RequestParam(required = false) String protocolNumber, @RequestParam(required = false) String protocolDate,
			@RequestParam(required = false) String result, @RequestParam(required = false) String notes,
			@RequestParam(required = false) String recipients, @RequestParam(required = false) String visibilities,
			@RequestParam(required = false) String determinationType, @RequestParam(required = false) String cityReference,
			//@RequestParam(required = false) String dateType, @RequestParam(required = false) String newDate,
			@RequestParam(required = false) MultipartFile[] attachments,
			@RequestParam(required = false) Integer managerCST,
			@RequestParam(required = false) String newDateTermine,
			@RequestParam(required = false) String newDateTermineEsprPareri,
			@RequestParam(required = false) String newDateTermineRichInteg,
			@RequestParam(required = false) String newDatePrimaSessioneSimultanea)
			throws MessagingException {
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setAttachments(attachments);
		documentoFileDTO.setCategoria(category);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setVisibilitaPartecipanti(visibilities);
		documentoFileDTO.setCompetenzaTerritoriale(cityReference);
		LOGGER.debug("@docs: " + documentoFileDTO.toString());
		EventoFileDTO eventoFileDTO = new EventoFileDTO();
		eventoFileDTO.setCorpo(body);
		eventoFileDTO.setTipoEvento(type);
		eventoFileDTO.setProtocollo(protocolNumber);
		eventoFileDTO.setNote(notes);
		eventoFileDTO.setResult(result);
		eventoFileDTO.setTipoParere(determinationType);
		eventoFileDTO.setListaDestinatari(JsonUtil.jsonToListLabelValue(recipients));
		
		
		//L'evento di tipo data può portare alla creazione di più eventi e pertanto si utilizza un metodo a parte
		Conferenza conferenza = this.eventoService.getConferenza(idConference);
		if (conferenza == null) {
			throw new NotFoundEx("la conferenza non e' stata trovata");
		}
				
		TipoEvento tipoEvento = this.eventoService.getTipoEvento(eventoFileDTO);
		LOGGER.debug("@docs event type: " + tipoEvento.getDescrizione());
		
		Evento evento = null;
		//se il tipo di evento è modifica data, va salvato sul database la nova data della conferenza e si
		//possono creare più eventi
		if(tipoEvento.getCodice().equalsIgnoreCase(String.valueOf(DbConst.TIPOLOGIA_EVENTO_MODIFICA_DATA))) {
			int ordine = this.eventoService.getMaxOrdineModifcataData(idConference);
			ordine += 1;
			
			Documento docEsistente = null;
			if (newDateTermine != null && !this.eventoService.ConvertStringToDate(newDateTermine).equals(conferenza.getDataTermine())) {
				evento = this.eventoService.creaEventoMultiFirmaDate(eventoFileDTO, conferenza, 
						documentoFileDTO, managerCST, tipoEvento, newDateTermine,DbConst.DATA_TERMINE,ordine,docEsistente);
			}
			if (newDateTermineEsprPareri != null && !this.eventoService.ConvertStringToDate(newDateTermineEsprPareri).equals(conferenza.getTermineEspressionePareri())) {
				if (evento != null && evento.getDocumento() != null) {
					docEsistente = evento.getDocumento();
				}
				evento = this.eventoService.creaEventoMultiFirmaDate(eventoFileDTO, conferenza, 
						documentoFileDTO, managerCST,tipoEvento, newDateTermineEsprPareri,DbConst.DATA_TERMINE_ESPRESSIONE_PARERI,ordine,docEsistente);
			}
			if (newDateTermineRichInteg != null && !this.eventoService.ConvertStringToDate(newDateTermineRichInteg).equals(conferenza.getTermineRichiestaIntegrazioniConferenza())) {
				if (evento != null && evento.getDocumento() != null) {
					docEsistente = evento.getDocumento();
				}
				evento = this.eventoService.creaEventoMultiFirmaDate(eventoFileDTO, conferenza, 
						documentoFileDTO, managerCST, tipoEvento, newDateTermineRichInteg,DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA,ordine,docEsistente);
			} 
			if (newDatePrimaSessioneSimultanea != null && !this.eventoService.ConvertStringToDate(newDatePrimaSessioneSimultanea).equals(conferenza.getPrimaSessioneSimultanea())) {
				if (evento != null && evento.getDocumento() != null) {
					docEsistente = evento.getDocumento();
				}
				evento = this.eventoService.creaEventoMultiFirmaDate(eventoFileDTO, conferenza, 
						documentoFileDTO, managerCST,tipoEvento,newDatePrimaSessioneSimultanea,DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA,ordine,docEsistente);
			}
			if (evento != null && evento.getDocumento() != null) {
				this.eventoService.notificaMailMultiFirmaDate(conferenza, tipoEvento, managerCST, evento.getDocumento());
			}
		} else {
			evento = this.eventoService.creaEventoMultiFirma(eventoFileDTO, idConference, documentoFileDTO, managerCST);
			LOGGER.debug("@docs eventId: " + evento.getId());
		}
		
		// non deve esser fatto qui, dovrebbe farla già paleo dopo la protocollazione
		//	eventoService.notificaMail(evento, evento.getDocumento(), eventoFileDTO.getListaDestinatari());	
		
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(evento.getId());
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		
		LOGGER.debug("@docs res ok");
		return risposta;
	}
}
