package conferenza.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.EnteDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaScadenzaDTO;
import conferenza.DTO.RispostaBoolean;
import conferenza.DTO.RispostaConferenceTemplateDTO;
import conferenza.DTO.RispostaConferenceTemplateListDTO;
import conferenza.DTO.RispostaEnteDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.DTO.RispostaLabelValueDTO;
import conferenza.DTO.RispostaListaLabelValueDTO;
import conferenza.DTO.RispostaListaResponsabiliConferenzaDTO;
import conferenza.DTO.RispostaListaRichiedenteDTO;
import conferenza.DTO.RispostaListaScadenzaDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.model.Utente;
import conferenza.service.CaricaComboService;
import conferenza.service.ConferenzaService;
import conferenza.service.UtenteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = "Utils API")
public class UtilsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilsController.class);

	@Autowired
	CaricaComboService comboService;

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ConferenzaService confService;

	@Autowired
	UtenteService utenteService;
	
	@GetMapping("/utils/areas")
	@ApiOperation(value = "all areas containing the string 'search'")
	public RispostaListaLabelValueDTO findRegioniAll(String search) {
		RispostaListaLabelValueDTO risp = comboService.findRegioniAll(search);
		return risp;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	@GetMapping("/utils/provinces")
	@ApiOperation(value = "all provinces of area identified by 'key' and containing the string 'search'")
	public RispostaListaLabelValueDTO findProvinceByRegione(String key, String search) {
		return this.comboService.findProvinceByRegione(key, search);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	@GetMapping("/utils/cities")
	@ApiOperation(value = "all cities of province identified by 'key' and containing the string 'search'")
	public RispostaListaLabelValueDTO findComuneByProvincia(String key, String search) {
		return this.comboService.findComuneByProvincia(key, search);
	}

	/**
	 * 
	 * @param search
	 * @return
	 */
	@GetMapping("/utils/applicant")
	@ApiOperation(value = "all applicants containing the string 'search' in name or lastname")
	public RispostaListaLabelValueDTO findPersonaByString(String search) {
		return this.comboService.findPersonaByString(search);
	}

	@GetMapping("/utils/conferenceTypes")
	@ApiOperation(value = "all conference types")
	public RispostaListaLabelValueDTO findTipologieConferenzeAll() {
		return this.comboService.findTipologieConferenzeAll();
	}

	@GetMapping("/utils/conferenceSpecializationTypes")
	@ApiOperation(value = "all conference specialization types filtered for userid")
	public RispostaListaLabelValueDTO findTipologieConferenzeSpecializzazioneAll() {
		return this.comboService.findTipologieConferenzeSpecializzazioneAll(this.utenteService.getAuthenticatedUser());
	}

	@GetMapping("/utils/practicesTypes")
	@ApiOperation(value = "all practice types")
	public RispostaListaLabelValueDTO findTipologiePraticheAll() {
		return this.comboService.findTipologiePraticheAll();
	}

	/**
	 * 
	 * @param key
	 * @param locale
	 * @return
	 */
	@GetMapping("/utils/activities")
	@ApiOperation(value = "all activities of practice type identified by 'key'")
	public RispostaListaLabelValueDTO findAttivitaByTipologiaPratica(String key) {
		return this.comboService.findAttivitaByTipologiaPratica(key);
	}

	/**
	 * 
	 * @param key
	 * @param locale
	 * @return
	 */
	@GetMapping("/utils/actions")
	@ApiOperation(value = "all actions of activity identified by 'key'")
	public RispostaListaLabelValueDTO findAzioniByAttivita(String key) {
		return this.comboService.findAzioniByAttivita(key);
	}

	@GetMapping("/utils/legalForms")
	@ApiOperation(value = "all legalForms")
	public RispostaListaLabelValueDTO findFormaGiuridicaAll() {
		return this.comboService.findFormaGiuridicaAll();
	}

	@GetMapping("/utils/participantRoles")
	@ApiOperation(value = "all conference roles for participant")
	public RispostaListaLabelValueDTO findRuoliPartecipantiAll() {
		return this.comboService.findRuoliPartecipantiAll();
	}

	/**
	 * 
	 * @param key
	 * @param locale
	 * @return
	 */
	@GetMapping("/utils/personRoles")
	@ApiOperation(value = "all person roles related to partecipant role identified by 'key'")
	public RispostaListaLabelValueDTO findRuoliPersoneAll(String key) {
		return this.comboService.findRuoliPersoneByRuoloPartecipante(key);
	}

	/**
	 * Tutti i ruoli persona selezionabili in fase di accreditamento
	 * 
	 * @param locale
	 * @return
	 */
	@GetMapping("/utils/accreditationRoles")
	@ApiOperation(value = "all person roles for accreditation")
	public RispostaListaLabelValueDTO findRuoliPersoneAccreditation() {
		return this.comboService.findRuoliPersoneAccreditation();
	}

	@GetMapping("/utils/conferenceManagers")
	@ApiOperation(value = "all possible conference managers for proceeding administration")
	public RispostaListaResponsabiliConferenzaDTO findResponsabiliConferenzaAll(String key) {
		return this.comboService.findResponsabiliConferenzaAll(key);
	}

	@GetMapping("/utils/company")
	@ApiOperation(value = "all companies containing the string 'search'")
	public RispostaListaLabelValueDTO findEntiByString(String search) {
		return this.comboService.findEntiByString(search);
	}

	/**
	 * 
	 * @param locale
	 * @param id
	 * @return
	 */
	@GetMapping("/utils/company/{id}")
	@ApiOperation(value = "companies with this id")
	public RispostaEnteDTO findEnte(@PathVariable Integer id) {
		RispostaEnteDTO risposta = new RispostaEnteDTO();
		risposta.setData(this.comboService.findEnte(id));
		return risposta;
	}
	
	@Deprecated
	@GetMapping("/utils/documentCategory")
	@ApiOperation(value = "all categories for documents")
	public RispostaListaLabelValueDTO findCategorieDocumentiAll() {
		String conferenceType = null;
		String eventType = null;
		return this.comboService.findCategorieDocumentiAll(conferenceType, eventType);
	}
	
	/**
	 * tipo evento obbligatorio
	 * @param conferenceType
	 * @param eventType
	 * @return
	 */
	@GetMapping("/utils/documentCategoriesForConferenceTypes")
	@ApiOperation(value = "all categories for documents")
	public RispostaListaLabelValueDTO findCategorieDocumentiPerTipologiaConf(String conferenceType, String eventType) {
		return this.comboService.findCategorieDocumentiAll(conferenceType, eventType);
	}
	
	/**
	 * tipologia documento obbligatoria; viene chiamato per popolare la combo nella post documento
	 * @param conferenceType
	 * @param documentType
	 * @param eventType
	 * @return
	 */
	@GetMapping("/utils/documentCategoriesForDocumentType")
	@ApiOperation(value = "all categories for documents filtered by conference type and document type")
	public RispostaListaLabelValueDTO findCategorieDocumentiPerTipoDocumento(String conferenceType, String documentType) {
		return comboService.findCategorieDocumentiPerTipoDocumento(conferenceType, documentType);
	}
	
	/**
	 * tipo evento obbligatorio; viene chiamato per popolare la combo nella post evento
	 * @param conferenceType
	 * @param documentType
	 * @param eventType
	 * @return
	 */
	@GetMapping("/utils/documentCategoriesForEventType")
	@ApiOperation(value = "all categories for documents filtered event type")
	public RispostaListaLabelValueDTO findCategorieDocumentiPerTipoEvento(String eventType) {
		return comboService.findCategorieDocumentiPerTipoEvento(eventType);
	}
	
	@GetMapping("/utils/competenceAuthorizationForConferenceTypes")
	@ApiOperation(value = "all competence authorization")
	public RispostaListaLabelValueDTO findCompetenzaAutorizzativaPerTipologiaConf(String conferenceType) {
		return this.comboService.findCompetenzaAutorizzativaAll(conferenceType);
	}

	@GetMapping("/utils/state")
	@ApiOperation(value = "all states")
	public RispostaListaLabelValueDTO findStatiAll() {
		return this.comboService.findStatiAll();
	}

	@GetMapping("/utils/authority")
	@ApiOperation(value = "authority for logget user or identified by 'idAuthority' if indicated in query string")
	public RispostaEnteDTO findAuthority(Integer idAuthority) {
		return this.comboService.findAuthority(idAuthority);
	}

	@GetMapping("/utils/addressTypes")
	@ApiOperation(value = "addressTypes")
	public RispostaListaLabelValueDTO findModalitaIndirizzoSessioneSimAll() {
		return this.comboService.findModalitaIndirizzoSessioneSimAll();
	}

	@GetMapping("/utils/authorities")
	@ApiOperation(value = "all possible proceeding administrations")
	public RispostaListaLabelValueDTO findAllAuthority() {
		return this.comboService.findFilteredAuthority(this.utenteService.getAuthenticatedUser());
	}

	@GetMapping("/utils/authorities/conferenzaCreators")
	@ApiOperation(value = "all possible proceeding administrations")
	public RispostaListaLabelValueDTO findAllConfernzaCreators() {
		return this.comboService.findAllConfernzaCreators(this.utenteService.getAuthenticatedUser());
	}

	@GetMapping("/utils/subjectsEvent")
	@ApiOperation(value = "SubjectsEvent from type event")
	public RispostaLabelValueDTO findOggettoEvento(LabelValue tipoEventoDTO) {
		return this.comboService.findOggettoEvento(tipoEventoDTO);
	}
	
	@GetMapping("/utils/conferenceTemplate")
	@ApiOperation(value = "Conference Template for this conference type")
	public RispostaConferenceTemplateDTO findConferenceTemplateDaPrecaricare (String type) {
		return this.comboService.findConferenceTemplateDaPrecaricare(type);
	}
	
	@GetMapping("/utils/conferenceTemplateList")
	@ApiOperation(value = "Conference Templates for this conference type")
	public RispostaConferenceTemplateListDTO findConferenceTemplateListDaPrecaricare (String type) {
		return this.comboService.findConferenceTemplateListDaPrecaricare(type);
	}
	
	@GetMapping("/utils/conferenceApplicants")
	@ApiOperation(value = "All applicants for this conference type")
	public RispostaListaRichiedenteDTO findListaRichiedentiDaPrecaricare (String type) {
		return this.comboService.findListaRichiedentiDaPrecaricare(type);
	}
	
	@GetMapping("/utils/eventTypes")
	@ApiOperation(value = "all event types")
	public RispostaListaLabelValueDTO findEventType() {
		return this.comboService.findEventType();
	}
	
	@GetMapping("/utils/dateTypes")
	@ApiOperation(value = "all type of date to edit")
	public RispostaListaLabelValueDTO findDateTypes() {
		return this.comboService.findDateTypes();
	}
	
	@GetMapping("/utils/pecStatus")
	@ApiOperation(value = "all pec status")
	public RispostaListaLabelValueDTO findEmailStatus() {
		return this.comboService.findEmailStatus();
	}
	
	@GetMapping("/utils/conferencesResults")
	@ApiOperation(value = "all possible results for the end of a conferences")
	public RispostaListaLabelValueDTO findConferencesResults () {
		return this.comboService.findConferencesResults();
	}
	
	@GetMapping(value = "/utils/calendaries")
	@ApiOperation(value = "list of deadlines")
	public RispostaListaScadenzaDTO ricercaScadenze() {
		ListaScadenzaDTO lista = this.confService.ricercaScadenze();
		RispostaListaScadenzaDTO risposta = new RispostaListaScadenzaDTO();
		risposta.setData(lista);
		return risposta;
		}
	
	@PostMapping("/utils/authorities")
	@ApiOperation(value = "insert authority")
	public RispostaIdentifiableDTO creaEnte (EnteDTO ente) {
		LOGGER.debug("Class: UtilsController - Method: postEnti");
		IdentifiableDTO identifiableDTO = this.comboService.creaEnte(ente);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}
	
	@GetMapping("/utils/determinationTypes")
	@ApiOperation(value = "all determination types")
	public RispostaListaLabelValueDTO findDeterminationType() {
		return this.comboService.findDeterminationType();
	}
	
	/**
	 * Restituisce il flag riferito all'obbligo di espressione parere rispetto ad un determinato 
	 * ruolo partecipante
	 * @param participantRole
	 * @return
	 */
	@GetMapping("/utils/determinations")
	@ApiOperation(value = "determination for a participant role")
	public RispostaBoolean findObbligoEspressioneParere(String participantRole) {
		return comboService.findObbligoEspressioneParere(participantRole);
	}
	
	@GetMapping("/utils/istatTypesAuthority")
	@ApiOperation(value = "all determination types")
	public RispostaListaLabelValueDTO findIstatTypesAuthority() {
		return this.comboService.findIstatTypesAuthority();
	}
	
	@GetMapping("/utils/administrativeTypesAuthority")
	@ApiOperation(value = "all determination types")
	public RispostaListaLabelValueDTO findAdministrativeTypesAuthority() {
		return this.comboService.findAdministrativeTypesAuthority();
	}
	
	@GetMapping("/utils/documentModelsForConferenceType")
	@ApiOperation(value = "all document models for conference type")
	public RispostaListaLabelValueDTO findModelliDocumentiPerTipologiaConferenza(String conferenceType) {
		return this.comboService.findModelliDocumentiPerTipologiaConferenza(conferenceType);
	}
	
	@GetMapping("/utils/documentTypesForDocumentArea")
	@ApiOperation(value = "all document types for document area")
	public RispostaListaLabelValueDTO findTipoDocumentoPerSezioneDoc(String documentArea) {
		return this.comboService.findTipoDocumentoPerSezioneDoc(documentArea);
	}
	
	@GetMapping("/utils/conferenceStatus")
	@ApiOperation(value = "all conference status")
	public RispostaListaLabelValueDTO findConferenceStatus() {
		return this.comboService.findConferenceStatus();
	}
}
