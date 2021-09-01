package cdst_be_marche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cdst_be_marche.DTO.PersonaRuoloConferenzaDTO;
import cdst_be_marche.DTO.RispostaLabelValueDTO;
import cdst_be_marche.DTO.RispostaPersonaRuoloConferenzaDTO;
import cdst_be_marche.DTO.RispostaUtenteDTO;
import cdst_be_marche.DTO.UtenteDTO;
import cdst_be_marche.service.UtenteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = "Users API")
public class UsersController {

	@Autowired
	UtenteService utenteService;

	@GetMapping("/users/profile")
	@ApiOperation(value = "user and profile information")
	public RispostaUtenteDTO findUtente() {
		UtenteDTO utente = utenteService.findUtente();
		RispostaUtenteDTO risposta = new RispostaUtenteDTO();
		risposta.setData(utente);
		return risposta;
	}

	@GetMapping("/users/conferenceProfile")
	@ApiOperation(value = "user role in conference identified by 'idConference'")
	public RispostaPersonaRuoloConferenzaDTO findPersonaRuoloConferenza(Integer idConference) {
		PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO = utenteService.findPersonaRuoloConferenza(idConference);
		RispostaPersonaRuoloConferenzaDTO risposta = new RispostaPersonaRuoloConferenzaDTO();
		risposta.setData(personaRuoloConferenzaDTO);
		return risposta;
	}

	@GetMapping("/users/conferenceParticipant")
	@ApiOperation(value = "addresser")
	public RispostaLabelValueDTO findPartecipanteUtenteLogged(Integer idConference) {
		return this.utenteService.findPartecipanteUtenteLogged(idConference);
	}

}
