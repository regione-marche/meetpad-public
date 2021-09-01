package conferenza.controller;

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
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaVotazioneDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.DTO.RispostaListaVotazioneDTO;
import conferenza.DTO.RispostaVotazioneDTO;
import conferenza.DTO.VotazioneDTO;
import conferenza.DTO.VotoDTO;
import conferenza.DTO.bean.RispostaNoData;
import conferenza.service.VotazioneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = { "Votings API" })
public class VotazioneController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VotazioneController.class);

	
	@Autowired
	VotazioneService votazioneService;

	/**
	 * 
	 * @param id     idConferenza
	 * @param locale
	 * @return
	 */
	@GetMapping("/conferences/{id}/votings")
	@ApiOperation(value = "get votings from conference")
	public RispostaListaVotazioneDTO findVotazioni(@PathVariable Integer id) {
		LOGGER.debug("Class: VotazioneController - Method: getListaVotazioni");
		ListaVotazioneDTO lista = this.votazioneService.findVotazioniConferenza(id);
		RispostaListaVotazioneDTO risposta = new RispostaListaVotazioneDTO();
		risposta.setData(lista);
		return risposta;
	}


	/**
	 * 
	 * @return
	 */	
	@PostMapping("/conferences/{conferenceId}/votings")
	@ApiOperation(value = "insert voting")
	public RispostaVotazioneDTO creaVotazione(@PathVariable Integer conferenceId, @RequestBody VotazioneDTO votazione) {
		LOGGER.debug("Class: VotazioneController - Method: postVotazione");
		VotazioneDTO votazioneDTO = this.votazioneService.creaVotazione(conferenceId, votazione);
		RispostaVotazioneDTO risposta = new RispostaVotazioneDTO();
		risposta.setData(votazioneDTO);
		return risposta;
	}

	@PostMapping("/conferences/{conferenceId}/votings/{votingId}/vote")
	@ApiOperation(value = "insert new vote")
	public RispostaVotazioneDTO insertNewVote(@PathVariable Integer conferenceId, @PathVariable Integer votingId, @RequestBody VotoDTO votoDto) {
		LOGGER.debug("Class: VotazioneController - Method: addNewVote");
		VotazioneDTO votazioneDTO = this.votazioneService.addVoto(conferenceId, votingId, votoDto);
		RispostaVotazioneDTO risposta = new RispostaVotazioneDTO();
		risposta.setData(votazioneDTO);
		return risposta;
	}
	
	/**
	 * 
	 * @param conference
	 * @param id
	 * @param idVotings
	 * @return
	 */
	@PutMapping("/conferences/{id}/votings/{idVotings}")
	@ApiOperation(value = "update voting")
	public RispostaVotazioneDTO modificaVotazione(@RequestBody VotazioneDTO votazioneDTO, @PathVariable Integer id, @PathVariable Integer idVotings) {
		LOGGER.debug("Class ConferenceController - Method: putConferenza");
		votazioneDTO.setId(idVotings);
		votazioneDTO = this.votazioneService.editVotazione(id, votazioneDTO);
		RispostaVotazioneDTO risposta = new RispostaVotazioneDTO();
		risposta.setData(votazioneDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@PatchMapping("/conferences/{id}/votings/{idVotings}")
	@ApiOperation(value = "modify only some information of voting") // <-- ???
	public RispostaIdentifiableDTO patchVotazione(@RequestBody VotazioneDTO votazioneDTO,
			@PathVariable Integer id, @PathVariable Integer idVotings) {
		LOGGER.debug("Class: VotazioneController - Method: patchVotazione");
		IdentifiableDTO identifiableDTO = null;// = this.votazioneService.patchVotazione(id, idVotings, votazioneDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}

	@DeleteMapping("/conferences/{id}/votings/{idVotings}")
	@ApiOperation(value = "delete voting")
	public RispostaNoData eliminaVotazione(@PathVariable Integer id, @PathVariable Integer idVotings) {
		LOGGER.debug("Class: VotazioneController - Method: deleteVotazione");
		String esito = this.votazioneService.deleteVotazione(idVotings);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}

}
