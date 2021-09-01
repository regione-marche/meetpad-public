package conferenza.consolleAmministrativa.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EnteDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.RicercaProtocollo;
import conferenza.DTO.RicercaUnifyDTO;
import conferenza.DTO.RicercaUtente;
import conferenza.DTO.RispostaDocumentoDTO;
import conferenza.DTO.RispostaEnteDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.DTO.RispostaListaConferenzaAnteprimaDTO;
import conferenza.DTO.RispostaListaLabelValueDTO;
import conferenza.DTO.RispostaListaProfiloDTO;
import conferenza.DTO.RispostaPersonaDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.DTO.bean.RispostaNoData;
import conferenza.consolleAmministrativa.DTO.EnteRicerca;
import conferenza.consolleAmministrativa.DTO.ImpresaRicerca;
import conferenza.consolleAmministrativa.DTO.InvioMailIndizioneDTO;
import conferenza.consolleAmministrativa.DTO.PreaccreditatoDTO;
import conferenza.consolleAmministrativa.DTO.PreaccreditatoRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoDelegatoDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoDelegatoRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaCompletaDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipanteDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipanteRicerca;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoRichiedenteDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoRichiedenteRicerca;
import conferenza.consolleAmministrativa.DTO.ResponsabileConfCompletoDTO;
import conferenza.consolleAmministrativa.DTO.RicercaResponsabileConferenzaDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaEntePreviewDTO;
import conferenza.consolleAmministrativa.DTO.lista.ListaProtocolloPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaConferenzaConsolleDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaImpresaDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaEntePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPreaccreditatiPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoDelegatoPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoImpresaPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoPartecipantePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaPrecaricamentoRichiedentePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaProtocolloPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaResponsabileConfPreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaListaUtentePreviewDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPreaccreditatoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoDelegatoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoImpresaDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoPartecipanteCompletoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaPrecaricamentoRichiedenteDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaResponsabileConfCompletoDTO;
import conferenza.consolleAmministrativa.DTO.risposta.RispostaUtenteCompletoDTO;
import conferenza.consolleAmministrativa.service.ConsolleAmministrativaService;
import conferenza.mail.EmailRepositoryService;
import conferenza.model.RubricaDelegati;
import conferenza.service.DocumentoService;
import conferenza.service.ProtocollazioneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/adminConsolle")
@Api(tags = { "AdminConsolle API" })
public class ConsolleAmministrativaController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsolleAmministrativaController.class);
	
	@Autowired
	ConsolleAmministrativaService consolleAmmService;
	
	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	EmailRepositoryService emailRepositoryService;
	
	@Autowired
	ProtocollazioneService protService;
	
	/**
	 * Restituisce tutti gli enti in base ai filtri
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/authorities")
	@ApiOperation(value = "select all authorities")
	public RispostaListaEntePreviewDTO findEnteAll(EnteRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getEnteAll");
		ListaEntePreviewDTO lista = consolleAmmService.findEnteAll(ricerca);
		RispostaListaEntePreviewDTO risposta = new RispostaListaEntePreviewDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	/**
	 * Restituisce l'ente con id
	 * @param id
	 * @return
	 */
	@GetMapping("/authorities/{id}")
	@ApiOperation(value = "select authority with selected id")
	public RispostaEnteDTO findEnteSingolo(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getEnteSingolo");
		return consolleAmmService.findEnteSingolo(id);
	}
	
	/**
	 * Inserisce enti e uffici da file .csv presi dal sito indiciPA
	 * @param file
	 * @param type
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/authoritiesCsv")
	@ApiOperation(value = "insert document for authorities and offices")
	public RispostaNoData caricaDocumentoEnti(@RequestParam MultipartFile file, @RequestParam String type) throws IOException {
		consolleAmmService.caricaDocumentoEnti(file, type);
		String esito = consolleAmmService.updateEnti(type);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	/**
	 * Inserisce un nuovo ente od un nuovo ufficio
	 * @param enteDTO
	 * @return
	 */
	@PostMapping(value = "/authorities")
	@ApiOperation(value = "insert a new authority or office")
	public RispostaIdentifiableDTO creaEntiDaConsolle(@RequestBody EnteDTO enteDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: postEnti");
		IdentifiableDTO identifiableDTO = consolleAmmService.creaEnte(enteDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Aggiorna le informazioni dell'ente tranne per il flag amministrazione procedente
	 * @param enteDTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/authorities/{id}")
	@ApiOperation(value = "update authority with selected id")
	public RispostaIdentifiableDTO modificaEntiDaConsolle(@RequestBody EnteDTO enteDTO, @PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: putEnte");
		IdentifiableDTO identifiableDTO = consolleAmmService.modificaEntiDaConsolle(enteDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Aggiorna le informazioni dell'ente come flagAmmProcedente o email/pec
	 * @param enteDTO
	 * @param id
	 * @return
	 */
	@PatchMapping("/authorities/{id}")
	@ApiOperation(value = "update authority to prosecuting administration with selected id")
	public RispostaIdentifiableDTO modificaEntiAdAmministrazioneProcedente(@RequestBody EnteDTO enteDTO, @PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patchEnte");
		IdentifiableDTO identifiableDTO = consolleAmmService.patchEnte(enteDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Restituisce tutti i responsabili in base ai filtri inseriti
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/conferenceManagers")
	@ApiOperation(value = "select all conference managers")
	public RispostaListaResponsabileConfPreviewDTO findResponsabiliConferenzaAll(RicercaResponsabileConferenzaDTO ricerca) {
		return consolleAmmService.findResponsabiliConferenzaAll(ricerca);
	}
	
	/**
	 * Restituisce il responsabile con l'id selezionata
	 * @param id
	 * @return
	 */
	@GetMapping("/conferenceManagers/{id}")
	@ApiOperation(value = "select conference manager with selected id")
	public RispostaResponsabileConfCompletoDTO findResponsabiliConferenzaSingolo(@PathVariable Integer id) {
		return consolleAmmService.findResponsabiliConferenzaSingolo(id);
	}
	
	/**
	 * Restituisce tutte le persone che contengono 'key' nel nome o nel cognome
	 * @param key
	 * @return
	 */
	@GetMapping("/people")
	@ApiOperation(value = "select all people containing the string 'key' in name or lastname")
	public RispostaListaLabelValueDTO findPersoneAll(String key) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getPersona");
		return consolleAmmService.findPersoneAll(key);
	}
	
	/**
	 * Restituisce la persona con l'id selezionata
	 * @param id
	 * @return
	 */
	@GetMapping("/people/{id}")
	@ApiOperation(value = "select person with selected id")
	public RispostaPersonaDTO findPersonaSingola(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getPersona/id");
		return consolleAmmService.findPersonaSingola(id);
	}
	
	/**
	 * Modifica nome, cognome ed email di una persona se è presente
	 * @param personaDTO
	 * @return
	 */
	@PatchMapping("/people/{id}")
	@ApiOperation(value = "update person with selected id")
	public RispostaIdentifiableDTO updatePersona(@RequestBody PersonaDTO personaDTO, @PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patchPersona");
		IdentifiableDTO identifiableDTO = consolleAmmService.patchPersona(personaDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Inserisce un responsabile di conferenza associando amministrazione procendente, persona ed utente
	 * @param authority
	 * @param person
	 * @return
	 */
	@PostMapping("/conferenceManagers")
	@ApiOperation(value = "insert conference manager")
	public RispostaIdentifiableDTO creaResponsabileConferenza(@RequestBody ResponsabileConfCompletoDTO respConferenza) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: postResponsabileConferenza");
		IdentifiableDTO identifiableDTO = consolleAmmService.creaResponsabileConferenza(respConferenza);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Modifica il responsabile inserito in una conferenza
	 * @param idManager
	 * @return
	 */
	@PatchMapping("/conferences/{id}/conferenceManagerUpdate")
	@ApiOperation(value = "update administration proceeding in conference with selected id")
	public RispostaIdentifiableDTO updateconferenceManager(@RequestParam Integer idManager, @PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: Patch updateconferenceManager");
		IdentifiableDTO identifiableDTO = consolleAmmService.updateconferenceManager(id, idManager);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Restituisce tutti gli utenti filtrati in base ai parametri di ricerca; 
	 * se non ci sono, restituisce tutti gli utenti
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/users")
	@ApiOperation(value = "select all users")
	public RispostaListaUtentePreviewDTO findUtenti(RicercaUtente ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: Get users");
		return consolleAmmService.findUtenti(ricerca);
	}
	
	/**
	 * Restituisce il singolo utente con 'id'
	 * @param id
	 * @return
	 */
	@GetMapping("/users/{id}")
	@ApiOperation(value = "select user with selected id")
	public RispostaUtenteCompletoDTO findUtenteSingolo(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: Get users/id");
		return consolleAmmService.findUtenteSingolo(id);
	}
	
	/**
	 * Restituisce una lista di ProfiloDTO passandogli l'id dell'amministrazione procedente scelta
	 * @param administrationProceeding
	 * @return
	 */
	@GetMapping("/profiles")
	@ApiOperation(value = "select all profiles for an administration proceeding")
	public RispostaListaProfiloDTO findProfileByAmministrazioneProcedente(Integer idAdministrationProceeding) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: Get profiles");
		return consolleAmmService.findProfileByAmministrazioneProcedente(idAdministrationProceeding);
	}
	
	/**
	 * Inserisce un utente se non esiste
	 * @param utenteDTO
	 * @return
	 */
	@PostMapping("/users")
	@ApiOperation(value = "insert user")
	public RispostaIdentifiableDTO creaUtente(@RequestBody UtenteDTO utenteDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: postUtente");
		IdentifiableDTO identifiableDTO = consolleAmmService.creaUtenteEPersona(utenteDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Modifica l'email o il profilo di un utente
	 * @param utenteDTO
	 * @param id
	 * @return
	 */
	@PatchMapping("/users/{id}")
	@ApiOperation(value = "update user with selected id")
	public RispostaIdentifiableDTO patchUtente(@RequestBody UtenteDTO utenteDTO, @PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patchUtente");
		IdentifiableDTO identifiableDTO = consolleAmmService.patchUtente(utenteDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Elimina un utente non collegato a nessuna conferenza
	 * @param id
	 * @return
	 */
	@DeleteMapping("/users/{id}")
	@ApiOperation(value = "delete user with selected id")
	public RispostaNoData eliminaUtente(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: delete users/id");
		String esito = consolleAmmService.eliminaUtente(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	/**
	 * Restituisce tutti i richiedenti da precaricare
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/preemptiveApplicants")
	@ApiOperation(value = "select all preemptiveApplicants")
	public RispostaListaPrecaricamentoRichiedentePreviewDTO findRichiedentePrecaricatoAll(PrecaricamentoRichiedenteRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveApplicants");
		return consolleAmmService.findRichiedentePrecaricatoAll(ricerca);
	}
	
	/**
	 * Restituisce il singolo richiedente da precaricare
	 * @param id
	 * @return
	 */
	@GetMapping("/preemptiveApplicants/{id}")
	@ApiOperation(value = "select a preemptive applicant with selected id")
	public RispostaPrecaricamentoRichiedenteDTO findRichiedentePrecaricatoSingolo(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveApplicants/id");
		return consolleAmmService.findRichiedentePrecaricatoSingolo(id);
	}
	
	/**
	 * Inserisce un nuovo richiedente da precaricare
	 * @param richiedenteDTO
	 * @return
	 */
	@PostMapping("/preemptiveApplicants")
	@ApiOperation(value = "insert a preemptive applicant")
	public RispostaIdentifiableDTO creaRichiedentePrecaricato(@RequestBody PrecaricamentoRichiedenteDTO richiedenteDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: post preemptiveApplicants");
		IdentifiableDTO identifiableDTO = consolleAmmService.creaRichiedentePrecaricato(richiedenteDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Aggiorna un richiedente da precaricare già esistente
	 * @param id
	 * @param richiedenteDTO
	 * @return
	 */
	@PatchMapping("/preemptiveApplicants/{id}")
	@ApiOperation(value = "update preemptive applicant with selected id")
	public RispostaIdentifiableDTO aggiornaRichiedentePrecaricato(@PathVariable Integer id, @RequestBody PrecaricamentoRichiedenteDTO richiedenteDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patch preemptiveApplicants/id");
		IdentifiableDTO identifiableDTO = consolleAmmService.aggiornaRichiedentePrecaricato(id, richiedenteDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Elimina il richiedente da precaricare con id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/preemptiveApplicants/{id}")
	@ApiOperation(value = "delete preemptive applicants with selected id")
	public RispostaNoData eliminaRichiedentePrecaricato(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: delete preemptiveApplicants/id");
		String esito = consolleAmmService.eliminaRichiedentePrecaricato(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	/**
	 * Restituisce tutti i partecipanti da precaricare
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/preemptiveParticipants")
	@ApiOperation(value = "select all preemptiveParticipants")
	public RispostaListaPrecaricamentoPartecipantePreviewDTO findPartecipantePrecaricatoAll(PrecaricamentoPartecipanteRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveParticipants");
		return consolleAmmService.findPartecipantePrecaricatoAll(ricerca);
	}
	
	/**
	 * Restituisce il partecipanti da precaricare con id
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/preemptiveParticipants/{id}")
	@ApiOperation(value = "select preemptiveParticipant with selected id")
	public RispostaPrecaricamentoPartecipanteCompletoDTO findPartecipantePrecaricatoSingolo(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveParticipants/id");
		return consolleAmmService.findPartecipantePrecaricatoSingolo(id);
	}
	
	/**
	 * Inserisce un nuovo partecipante da precaricare
	 * @param partecipantDTO
	 * @return
	 */
	@PostMapping("/preemptiveParticipants")
	@ApiOperation(value = "insert a new preemptive participant")
	public RispostaIdentifiableDTO creaPartecipantePrecaricato(@RequestBody PrecaricamentoPartecipanteDTO partecipantDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: post preemptiveParticipants");
		IdentifiableDTO identifiableDTO = consolleAmmService.creaPartecipantePrecaricato(partecipantDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Aggiorna il partecipante da precaricare con id
	 * @param id
	 * @param partecipantDTO
	 * @return
	 */
	@PatchMapping("/preemptiveParticipants/{id}")
	@ApiOperation(value = "update preemptive participant with selected id")
	public RispostaIdentifiableDTO aggiornaPartecipantePrecaricato(@PathVariable Integer id, @RequestBody PrecaricamentoPartecipanteDTO partecipantDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patch preemptiveParticipants/id");
		IdentifiableDTO identifiableDTO = consolleAmmService.aggiornaPartecipantePrecaricato(id, partecipantDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Elimina il partecipante da precaricare con id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/preemptiveParticipants/{id}")
	@ApiOperation(value = "delete preemptive participant with selected id")
	public RispostaNoData eliminaPartecipantePrecaricato(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: delete preemptiveParticipants/id");
		String esito = consolleAmmService.eliminaPartecipantePrecaricato(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	/**
	 * Restituisce tutte le imprese da precaricare
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/preemptiveCompanies")
	@ApiOperation(value = "select all paginated preemptiveCompanies")
	public RispostaListaPrecaricamentoImpresaPreviewDTO findImpresaPrecaricataPaginataAll(PrecaricamentoImpresaRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveCompanies");
		return consolleAmmService.findImpresaPrecaricataPaginataAll(ricerca);
	}
	
	/**
	 * Restituisce tutte le imprese precaricate non paginate
	 * @param key
	 * @return
	 */
	@GetMapping("/noPaginatedpreemptiveCompanies")
	@ApiOperation(value = "select all preemptiveCompanies")
	public RispostaListaLabelValueDTO findImpresaPrecaricataAll(PrecaricamentoImpresaRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get noPaginatedpreemptiveCompanies");
		return consolleAmmService.findImpresaPrecaricataAll(ricerca);
	}
	
	/**
	 * Restituisce l'impresa da precaricare con id
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/preemptiveCompanies/{id}")
	@ApiOperation(value = "select preemptiveCompany with selected id")
	public RispostaPrecaricamentoImpresaDTO findImpresaPrecaricataSingola(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveCompanies/id");
		return consolleAmmService.findImpresaPrecaricataSingola(id);
	}
	
	/**
	 * Inserisce una nuova impresa da precaricare
	 * @param partecipantDTO
	 * @return
	 */
	@PostMapping("/preemptiveCompanies")
	@ApiOperation(value = "insert a new preemptiveCompany")
	public RispostaIdentifiableDTO associaImpresaInRubrica(@RequestBody PrecaricamentoImpresaCompletaDTO impresaDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: post preemptiveCompanies");
		IdentifiableDTO identifiableDTO = consolleAmmService.associaImpresaInRubrica(impresaDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Aggiornare una nuova impresa da precaricare
	 * @param partecipantDTO
	 * @return
	 */
	@PutMapping("/preemptiveCompanies/{id}")
	@ApiOperation(value = "update preemptive company with selected id")
	public RispostaIdentifiableDTO modificaRubricaImpresa(@RequestBody PrecaricamentoImpresaCompletaDTO impresaDTO, @PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: put preemptiveCompanies");
		IdentifiableDTO identifiableDTO = consolleAmmService.modificaRubricaImpresa(impresaDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Elimina l'impresa da precaricare con id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/preemptiveCompanies/{id}")
	@ApiOperation(value = "delete preemptive company with selected id")
	public RispostaNoData separaImpresaInRubrica(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: delete preemptiveCompanies/id");
		String esito = consolleAmmService.separaImpresaInRubrica(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	/**
	 * Restituisce tutte le imprese paginate
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/companies")
	@ApiOperation(value = "select all paginated companies")
	public RispostaListaImpresaPreviewDTO findImpresaPaginataAll(ImpresaRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get companies");
		return consolleAmmService.findImpresaPaginataAll(ricerca);
	}
	
	/**
	 * Restituisce tutte le imprese non paginate
	 * @param key
	 * @return
	 */
	@GetMapping("/noPaginatedCompanies")
	@ApiOperation(value = "select all companies")
	public RispostaListaLabelValueDTO findImpresaAll(String key) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get noPaginatedcompanies");
		return consolleAmmService.findImpresaAll(key);
	}
	
	/**
	 * Restituisce l'impresa con id
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/companies/{id}")
	@ApiOperation(value = "select company with selected id")
	public RispostaImpresaDTO findImpresaPaginataAll(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get companies/id");
		return consolleAmmService.findImpresaSingola(id);
	}
	
	/**
	 * Elimina l'impresa con id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/companies/{id}")
	@ApiOperation(value = "delete company with selected id")
	public RispostaNoData eliminaImpresa(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: delete companies/id");
		String esito = consolleAmmService.eliminaImpresa(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	/**
	 * Invia una singola email di indizione per la conferenza con idConference all'ente partecipante con idEnte, 
	 * passando l'id del documento e l'email di destinazione
	 * @param idConference
	 * @param mailDTO
	 * @return
	 */
	@PostMapping("/sendIndictionMail/{idConference}")
	@ApiOperation(value = "send indiction emails for conference with selected id")
	public RispostaNoData invioEmailIndizione (@PathVariable Integer idConference, @RequestBody InvioMailIndizioneDTO mailDTO,
			@RequestParam(required = false, defaultValue = "false") Boolean sendnonpec) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: post sendIndictionMail/idConf[sendnonpec:" + sendnonpec+"]");

		try {
			emailRepositoryService.upsertEmailPec(mailDTO.getEmail(), !sendnonpec);	
		} catch (Exception e) {
			LOGGER.error("Class: ConsolleAmministrativaController - Method: invioEmailIndizione: impossible set email_pec", e);
		}
		
		String esito = consolleAmmService.invioEmailIndizione(idConference, mailDTO, sendnonpec);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	/**
	 * Restituisce una lista di anteprima conferenze
	 * @param ricerca
	 * @return
	 */
	@GetMapping(value = "/conferences")
	@ApiOperation(value = "list of conferences meet the only one search parameter in query string")
	public RispostaListaConferenzaAnteprimaDTO findConferenzaAll(RicercaUnifyDTO ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getConferenzaAvanzata");
		return consolleAmmService.findConferenzaAll(ricerca);
	}
	
	/**
	 * Restituisce il dettaglio contente amministrazione procedente e responsabile della conferenza con l'id selezionato 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/conferences/{id}")
	@ApiOperation(value = "detail of conference with selected id")
	public RispostaConferenzaConsolleDTO findConferenzaSingola(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getConferenzaSingola");
		return consolleAmmService.findConferenzaSingola(id);
	}
	
	/**
	 * Restituisce una lista degli enti partecipanti alla conferenza con l'id selezionato
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/conferences/{id}/participants")
	@ApiOperation(value = "list of participants of conference with selected id")
	public RispostaListaLabelValueDTO findEntiPartecipantiConferenza(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getEntiPartecipantiConferenza");
		return consolleAmmService.findEntiPartecipantiConferenza(id);
	}
	
	/**
	 * Restituisce una lista delle tipologie di documento che sono associate alla consolle amministrativa
	 * @return
	 */
	@GetMapping(value = "/documentTypesConsolle")
	@ApiOperation(value = "list of document types for admin consolle")
	public RispostaListaLabelValueDTO findTipologiaDocumentoPerConsolle() {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: getTipologiaDocumento");
		return consolleAmmService.findTipologiaDocumentoPerConsolle();
	}
	
	@PostMapping(value = "/conferences/{idConference}/documents")
	@ApiOperation(value = "insert document for conference identified by id")
	public RispostaDocumentoDTO creaDocumento(@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String url, @RequestParam String name, @RequestParam String type,
			@RequestParam(required = false) String category, @RequestParam String visibility,
			@PathVariable Integer idConference, @RequestParam(required = false) String protocolNumber,
			@RequestParam(required = false) String protocolDate, @RequestParam(required = false) String inventoryNumber,
			@RequestParam(required = false) String cityReference, @RequestParam(required = false) String model,
			@RequestParam(required = false) String meetingDate) {
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setUrl(url);
		documentoFileDTO.setCategoria(category);
		documentoFileDTO.setTipoDocumento(type);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setVisibilitaPartecipanti(visibility);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setNumeroInventario(inventoryNumber);
		documentoFileDTO.setCompetenzaTerritoriale(cityReference);
		documentoFileDTO.setModello(model);
		documentoFileDTO.setDataRiunione(meetingDate);
		LOGGER.debug(documentoFileDTO.toString());
		return consolleAmmService.creaDocumento(documentoFileDTO, idConference);
	}
	
	/**
	 * Restituisce tutti gli utenti filtrati in base ai parametri di ricerca; 
	 * se non ci sono, restituisce tutti gli utenti
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/protocols")
	@ApiOperation(value = "select all protocol with error")
	public RispostaListaProtocolloPreviewDTO findProtocolli (RicercaProtocollo ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: Get protocols");
		
		return consolleAmmService.findProtocolli(ricerca);
	}
	
	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@PatchMapping("/Protocols/{protocolId}")
	@ApiOperation(value = "modify only state of protocol")
	public RispostaIdentifiableDTO patchProtocollo(@PathVariable Integer protocolId) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patchProtocollo");
		IdentifiableDTO identifiableDTO = this.consolleAmmService.patchProtocollo(protocolId);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}

	/**
	 * Restituisce tutti i delegato da precaricare
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/preemptiveDelegates")
	@ApiOperation(value = "select all preemptive delegates")
	public RispostaListaPrecaricamentoDelegatoPreviewDTO findDelegatiPrecaricatoAll(PrecaricamentoDelegatoRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveApplicants");
		return consolleAmmService.findDelegatoPrecaricatoAll(ricerca);
	}
	
	/**
	 * Restituisce il singolo delegato da precaricare
	 * @param id
	 * @return
	 */
	@GetMapping("/preemptiveDelegates/{id}")
	@ApiOperation(value = "select a preemptive delegate with selected id")
	public RispostaPrecaricamentoDelegatoDTO findDelegatoPrecaricatoSingolo(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preemptiveApplicants/id");
		return consolleAmmService.findDelegatoPrecaricatoSingolo(id);
	}
	
	/**
	 * Aggiorna un richiedente da precaricare già esistente
	 * @param id
	 * @param file
	 * @param name
	 * @param surname
	 * @param fiscalCode
	 * @param email
	 * @param documentName
	 * @param conferenceType
	 * @return
	 */
	@PatchMapping("/preemptiveDelegates/{id}")
	@ApiOperation(value = "update preemptive delegate with selected id")
	public RispostaIdentifiableDTO aggiornaDelegatoPrecaricato(@PathVariable Integer id,
			@RequestParam(required = false) 	MultipartFile file, 
			@RequestParam(required = false)  	String name, 
			@RequestParam(required = false)  	String surname,
			@RequestParam(required = false)		String fiscalCode, 
			@RequestParam(required = false)  	String email,
			@RequestParam(required = false)  	String documentName,
			@RequestParam(required = false)  	String conferenceType) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patch preemptiveApplicants/id");
		PrecaricamentoDelegatoDTO delegatoDTO = new PrecaricamentoDelegatoDTO();
		delegatoDTO.setNome(name);
		delegatoDTO.setCognome(surname);
		delegatoDTO.setCodiceFiscale(fiscalCode);
		delegatoDTO.setEmail(email);
		delegatoDTO.setTipoConf(new LabelValue(conferenceType, conferenceType));
		IdentifiableDTO identifiableDTO = consolleAmmService.aggiornaDelegatoPrecaricato(id, delegatoDTO );
		if(identifiableDTO != null && file != null) {
			// nel caso in cui c'è un file allegato, salvo il documento
			identifiableDTO = consolleAmmService.aggiornaDocumentoDelega(identifiableDTO.getId(), file);
		}
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Elimina il delegato da precaricare con id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/preemptiveDelegates/{id}")
	@ApiOperation(value = "delete preemptive delegates with selected id")
	public RispostaNoData eliminaDelegatoPrecaricato(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: delete preemptiveApplicants/id");
		String esito = consolleAmmService.eliminaDelegatoPrecaricato(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
	
	@PostMapping(value = "/saveDelegateFile")
	@ApiOperation(value = "insert event for conference identified by id")
	public RispostaIdentifiableDTO creaDocumentoDelegatoPrecaricato(
			@RequestParam(required = false) 	MultipartFile file, 
			@RequestParam(required = false)  	String name, 
			@RequestParam(required = false)  	String surname,
			@RequestParam(required = false)		String fiscalCode, 
			@RequestParam(required = false)  	String email,
			@RequestParam(required = false)  	String documentName,
			@RequestParam(required = false)  	String conferenceType) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: saveDelegateFile" + fiscalCode);
		PrecaricamentoDelegatoDTO richiedenteDTO = new PrecaricamentoDelegatoDTO();
		richiedenteDTO.setNome(name);
		richiedenteDTO.setCognome(surname);
		richiedenteDTO.setCodiceFiscale(fiscalCode);
		richiedenteDTO.setEmail(email);
		richiedenteDTO.setTipoConf(new LabelValue(conferenceType, conferenceType));
		// creo il delegato
		IdentifiableDTO identifiableDTO = consolleAmmService.creaDelegatoPrecaricato(richiedenteDTO );
		if(identifiableDTO != null && file != null) {
			// nel caso in cui c'è un file allegato, salvo il documento
			identifiableDTO = consolleAmmService.aggiornaDocumentoDelega(identifiableDTO.getId(), file);
		}
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	@PostMapping(value = "/deleteDelegateFile/{id}")
	@ApiOperation(value = "insert event for conference identified by id")
	public RispostaIdentifiableDTO cancellaDocumentoDelegatoPrecaricato(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patch deleteDelegateFile/id" );
		IdentifiableDTO identifiableDTO = consolleAmmService.cancellaDocumentoDelega(id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Delete Document complete");
		risposta.setCode("200");
		return risposta;
	}

	@GetMapping(value = "/downloadDelegateFile/{id}")
	@ApiOperation(value = "download delegate's document identified by id")
	public  ResponseEntity<Resource> downloadDocumentoDelegatoPrecaricato(@PathVariable Integer id,
		HttpServletRequest request) throws Exception {
		
		String fileName = null;
		RubricaDelegati rubricaDel = consolleAmmService.getRubricaDelegato(id);
		if(rubricaDel.getNomeDocumento() != null)
			fileName = rubricaDel.getNomeDocumento();
		
		Resource resource = consolleAmmService.downloadDocumentoDelega(rubricaDel);
		if (resource != null && resource.exists()) {
			LOGGER.debug("Resource exists");
		}
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.debug("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		if(fileName == null)
			fileName = resource.getFilename();
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.body(resource);
	}
	
	/**
	 * Restituisce tutti gli accreditamenti per ente
	 * @param ricerca
	 * @return
	 */
	@GetMapping("/preAccreditations")
	@ApiOperation(value = "select all preemptive accreditations")
	public RispostaListaPreaccreditatiPreviewDTO findPreAccreditatiAll(PreaccreditatoRicerca ricerca) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preAccreditations");
		return consolleAmmService.findPreaccreditatiAll(ricerca);
	}
	
	/**
	 * Restituisce il singolo delegato da precaricare
	 * @param id
	 * @return
	 */
	@GetMapping("/preAccreditations/{id}")
	@ApiOperation(value = "select a accreditation with selected id")
	public RispostaPreaccreditatoDTO findPreaccreditatiSingolo(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: get preAccreditations/id");
		return consolleAmmService.findPreaccreditatoSingolo(id);
	}
	
	@PostMapping("/preAccreditations")
	@ApiOperation(value = "insert a new preaccreditation")
	public RispostaIdentifiableDTO creaPreaccreditamentoPerEnte(@RequestBody PreaccreditatoDTO preaccreditatoDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: post creaPreaccreditamentoPerEnte");
		IdentifiableDTO identifiableDTO = consolleAmmService.creaPreaccreditato(preaccreditatoDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Insert complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Aggiorna il preaccreditato con id
	 * @param id
	 * @param preaccreditatoDTO
	 * @return
	 */
	@PatchMapping("/preAccreditations/{id}")
	@ApiOperation(value = "update preemptive participant with selected id")
	public RispostaIdentifiableDTO aggiornaPreaccreditato(@PathVariable Integer id, @RequestBody PreaccreditatoDTO preaccreditatoDTO) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: patch aggiornaPreaccreditato");
		IdentifiableDTO identifiableDTO = consolleAmmService.creaAggiornaPreaccreditatoPerEnte(id, preaccreditatoDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Update complete");
		risposta.setCode("200");
		return risposta;
	}
	
	/**
	 * Elimina  il preaccreditato con id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/preAccreditations/{id}")
	@ApiOperation(value = "delete preemptive participant with selected id")
	public RispostaNoData eliminaPreaccreditato(@PathVariable Integer id) {
		LOGGER.debug("Class: ConsolleAmministrativaController - Method: eliminaPreaccreditato");
		String esito = consolleAmmService.eliminaPreaccreditato(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}
}
