package conferenza.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.EliminaListaDocumentiDTO;
import conferenza.DTO.EliminaListaPartecipantiDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaPartecipanteDTO;
import conferenza.DTO.ListaPersonaRuoloConferenzaDTO;
import conferenza.DTO.PartecipanteDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.DTO.RispostaListaPartecipanteDTO;
import conferenza.DTO.RispostaListaPersonaRuoloConferenzaDTO;
import conferenza.DTO.bean.RispostaNoData;
import conferenza.service.ParticipantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = "Participants API")
public class ParticipantController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	ParticipantService participantService;

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@GetMapping("/conferences/{id}/participants")
	@ApiOperation(value = "all participants from the conferences with id")
	public RispostaListaPartecipanteDTO findPartecipantiByIdConferenza(@PathVariable Integer id) {
		LOGGER.debug("Class: ParticipantController - Method: getPartecipante");
		ListaPartecipanteDTO lista = this.participantService.findPartecipantiByIdConferenza(id);
		RispostaListaPartecipanteDTO risposta = new RispostaListaPartecipanteDTO();
		risposta.setData(lista);
		return risposta;
	}

	/**
	 * 
	 * @param partecipanteDTO
	 * @param locale
	 * @param id
	 * @return
	 */
	@PostMapping("/conferences/{id}/participants")
	@ApiOperation(value = "adding participant")
	public RispostaIdentifiableDTO creaPartecipante(@RequestBody PartecipanteDTO partecipanteDTO,
			@PathVariable Integer id) {
		LOGGER.debug("Class: ParticipantController - Method: postPartecipante");
		IdentifiableDTO identifiableDTO = this.participantService.creaPartecipante(partecipanteDTO, id);
		// this.participantService.creaUtenzeAccreditamenti(id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	/**
	 * 
	 * @param partecipanteDTO
	 * @param id
	 * @param locale
	 * @return
	 */
	@PutMapping("/participants/{id}")
	@ApiOperation(value = "update participant")
	public RispostaIdentifiableDTO modificaPartecipante(@RequestBody PartecipanteDTO partecipanteDTO,
			@PathVariable Integer id) {
		LOGGER.debug("Class: ParticipantController - Method: putPartecipante");
		IdentifiableDTO identifiableDTO = this.participantService.modificaPartecipante(partecipanteDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@DeleteMapping("/participants/{id}")
	@ApiOperation(value = "delete participant")
	public RispostaNoData eliminaPartecipante(@PathVariable Integer id) {
		LOGGER.debug("Class: ParticipantController - Method: deletePartecipante");
		String esito = this.participantService.eliminaPartecipante(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}

	/**
	 * 
	 * @param id
	 * @param idPart
	 * @param locale
	 * @return
	 */
	@GetMapping("/conferences/{id}/participants/{idPart}/users")
	@ApiOperation(value = "all users from the participant with idPart")
	public RispostaListaPersonaRuoloConferenzaDTO findAccreditamentoByIdPartecipante(@PathVariable Integer id,
			@PathVariable Integer idPart) {
		LOGGER.debug("Class: ParticipantController - Method: getAccreditato");
		ListaPersonaRuoloConferenzaDTO lista = this.participantService.findAccreditamentiById(idPart);
		RispostaListaPersonaRuoloConferenzaDTO risposta = new RispostaListaPersonaRuoloConferenzaDTO();
		risposta.setData(lista);
		return risposta;
	}

	/**
	 * 
	 * @param personaRuoloConferenzaDTO
	 * @param locale
	 * @param id
	 * @param idPart
	 * @return
	 */
	@PostMapping("/conferences/{id}/participants/{idPart}/users")
	@ApiOperation(value = "adding user")
	public RispostaIdentifiableDTO creaAccreditamento(@RequestBody PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO,
			@PathVariable Integer id, @PathVariable Integer idPart) {
		LOGGER.debug("Class: ParticipantController - Method: postAccreditato");
		IdentifiableDTO identifiableDTO = this.participantService.creaAccreditamento(personaRuoloConferenzaDTO, id,
				idPart);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	/**
	 * 
	 * @param personaRuoloConferenzaDTO
	 * @param id
	 * @param locale
	 * @return
	 */
	@PutMapping("/conferences/participants/users/{id}")
	@ApiOperation(value = "update user")
	public RispostaIdentifiableDTO modificaAccreditamento(
			@RequestBody PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO, @PathVariable Integer id) {
		LOGGER.debug("Class: ParticipantController - Method: putAccreditato");
		IdentifiableDTO identifiableDTO = this.participantService.modificaAccreditamento(personaRuoloConferenzaDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@DeleteMapping("/conferences/participants/users/{id}")
	@ApiOperation(value = "delete user")
	public RispostaNoData eliminaAccreditamento(@PathVariable Integer id) {
		LOGGER.debug("Class: ParticipantController - Method: deleteAccreditato");
		String esito = this.participantService.eliminaAccreditamento(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	@PatchMapping(value = "/participants/deleteList")
	@ApiOperation(value = "delete participants list")
	public RispostaNoData eliminaListaPartecipanti(@RequestBody EliminaListaPartecipantiDTO eliminaListaPartecipantiDTO) {
		String result = participantService.deleteParticipantList(eliminaListaPartecipantiDTO.getListaIdPartecipanti());
		RispostaNoData response = new RispostaNoData();
		response.setMsg(result);
		return response;
	}

}
