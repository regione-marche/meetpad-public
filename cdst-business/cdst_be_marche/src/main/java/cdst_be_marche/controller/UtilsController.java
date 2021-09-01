package cdst_be_marche.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cdst_be_marche.DTO.EnteDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaScadenzaDTO;
import cdst_be_marche.DTO.RispostaConferenceTemplateDTO;
import cdst_be_marche.DTO.RispostaConferenceTemplateListDTO;
import cdst_be_marche.DTO.RispostaEnteDTO;
import cdst_be_marche.DTO.RispostaIdentifiableDTO;
import cdst_be_marche.DTO.RispostaLabelValueDTO;
import cdst_be_marche.DTO.RispostaListaImpresaDTO;
import cdst_be_marche.DTO.RispostaListaLabelValueDTO;
import cdst_be_marche.DTO.RispostaListaPersonaDTO;
import cdst_be_marche.DTO.RispostaListaResponsabiliConferenzaDTO;
import cdst_be_marche.DTO.RispostaListaRichiedenteDTO;
import cdst_be_marche.DTO.RispostaListaScadenzaDTO;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.service.CaricaComboService;
import cdst_be_marche.service.ConferenzaService;
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

	@GetMapping("/utils/areas")
	@ApiOperation(value = "all areas containing the string 'search'")
	public RispostaListaLabelValueDTO findRegioniAll(String search) {
		return this.comboService.findRegioniAll(search);
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
		return this.comboService.findEnte(id);
	}

	@GetMapping("/utils/documentCategory")
	@ApiOperation(value = "all categories for documents")
	public RispostaListaLabelValueDTO findCategorieDocumentiAll() {
		return this.comboService.findCategorieDocumentiAll();
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
		return this.comboService.findAllAuthority();
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

}
