package cdst_be_marche.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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

import cdst_be_marche.DTO.DocumentoFileDTO;
import cdst_be_marche.DTO.EventoCompletoDTO;
import cdst_be_marche.DTO.EventoFileDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaEventoDTO;
import cdst_be_marche.DTO.ListaPecDTO;
import cdst_be_marche.DTO.PageableDTO;
import cdst_be_marche.DTO.RicercaPecDTO;
import cdst_be_marche.DTO.RispostaEventoCompletoDTO;
import cdst_be_marche.DTO.RispostaIdentifiableDTO;
import cdst_be_marche.DTO.RispostaListaEventoDTO;
import cdst_be_marche.DTO.RispostaListaPecDTO;
import cdst_be_marche.service.EventoService;
import cdst_be_marche.util.JsonUtil;
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

	@GetMapping(value = "/conferences/{idConference}/events")
	@ApiOperation(value = "get events for conference identified by id")
	public RispostaListaEventoDTO findAll(@PathVariable Integer idConference, PageableDTO pageableDTO) {
		LOGGER.debug("Class: EventsController - Method: getEvents");
		ListaEventoDTO lista = this.eventoService.findAll(idConference, pageableDTO);
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

	@PostMapping(value = "/conferences/{idConference}/events")
	@ApiOperation(value = "insert event for conference identified by id")
	public RispostaIdentifiableDTO creaEvento(@PathVariable Integer idConference, @RequestParam String type,
			@RequestParam(required = false) String body, @RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String name, @RequestParam(required = false) String category,
			@RequestParam(required = false) String protocolNumber, @RequestParam(required = false) String protocolDate,
			@RequestParam(required = false) String result, @RequestParam(required = false) String notes,
			@RequestParam(required = false) String recipients, @RequestParam(required = false) String visibilities,
			@RequestParam(required = false) String determinationType)
			throws MessagingException {
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setCategoria(category);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setVisibilitaPartecipanti(visibilities);
		EventoFileDTO eventoFileDTO = new EventoFileDTO();
		eventoFileDTO.setCorpo(body);
		eventoFileDTO.setTipoEvento(type);
		eventoFileDTO.setProtocollo(protocolNumber);
		eventoFileDTO.setNote(notes);
		eventoFileDTO.setResult(result);
		eventoFileDTO.setTipoParere(determinationType);
		eventoFileDTO.setListaDestinatari(JsonUtil.jsonToListLabelValue(recipients));
		IdentifiableDTO identifiableDTO = this.eventoService.creaEvento(eventoFileDTO, idConference, documentoFileDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

}
