package conferenza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.ListaAccreditamentoDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.RispostaBoolean;
import conferenza.DTO.RispostaLabelValueDTO;
import conferenza.DTO.RispostaListaAccreditamentoDTO;
import conferenza.DTO.RispostaPersonaRuoloConferenzaDTO;
import conferenza.DTO.RispostaUtenteDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.model.Conferenza;
import conferenza.model.Utente;
import conferenza.repository.SearchCriteria;
import conferenza.service.UtenteService;
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
	public RispostaUtenteDTO findUtenteInfo() {
		UtenteDTO utente = utenteService.findUtenteAccesso();
		RispostaUtenteDTO risposta = new RispostaUtenteDTO();
		risposta.setData(utente);
		return risposta;
	}

	/**
	 * Verifica esistenza utente nell'applicativo
	 * @return
	 */
	@GetMapping("/users/exist")
	@ApiOperation(value = "verify user existence")
	public RispostaBoolean utenteCensito() {
		return utenteService.isUtenteCensito();
	}

	@GetMapping("/users/pendingAccreditations")
	@ApiOperation(value = "user pending accreditations")
	public RispostaListaAccreditamentoDTO findAccreditamentiPendenti() {
		ListaAccreditamentoDTO listaAccreditamentoDTO = utenteService.findAccreditamentiPendenti();
		RispostaListaAccreditamentoDTO risposta = new RispostaListaAccreditamentoDTO();
		risposta.setData(listaAccreditamentoDTO);
		return risposta;
	}
	
	@GetMapping("/users/conferenceProfile")
	@ApiOperation(value = "user role in conference identified by 'idConference'")
	public RispostaPersonaRuoloConferenzaDTO findPersonaRuoloConferenza(Integer idConference) {
		RispostaPersonaRuoloConferenzaDTO risposta = new RispostaPersonaRuoloConferenzaDTO();
		PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO = utenteService.findPersonaRuoloConferenza(idConference);
		
		risposta.setData(personaRuoloConferenzaDTO);
		return risposta;
	}

	@GetMapping("/users/conferenceParticipant")
	@ApiOperation(value = "addresser")
	public RispostaLabelValueDTO findPartecipanteUtenteLogged(Integer idConference) {
		return this.utenteService.findPartecipanteUtenteLogged(idConference);
	}

	@GetMapping("/users/conferenceProfileDelegate")
	@ApiOperation(value = "user role delegate in conference identified by 'idConference'")
	public RispostaBoolean findPersonaRuoloDelegatoConferenza(Integer idConference) {
		return this.utenteService.isUtenteDelegatoConferenza(idConference);
	}
}
