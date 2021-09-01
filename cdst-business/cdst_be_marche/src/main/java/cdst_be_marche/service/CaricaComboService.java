package cdst_be_marche.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdst_be_marche.DTO.ConferenceTemplateDTO;
import cdst_be_marche.DTO.ConferenceTemplateListDTO;
import cdst_be_marche.DTO.EnteDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ImpresaDTO;
import cdst_be_marche.DTO.ListaResponsabiliConferenza;
import cdst_be_marche.DTO.ListaRichiedenteDTO;
import cdst_be_marche.DTO.ResponsabileConferenzaDTO;
import cdst_be_marche.DTO.RichiedenteDTO;
import cdst_be_marche.DTO.RispostaConferenceTemplateDTO;
import cdst_be_marche.DTO.RispostaConferenceTemplateListDTO;
import cdst_be_marche.DTO.RispostaEnteDTO;
import cdst_be_marche.DTO.RispostaLabelValueDTO;
import cdst_be_marche.DTO.RispostaListaLabelValueDTO;
import cdst_be_marche.DTO.RispostaListaResponsabiliConferenzaDTO;
import cdst_be_marche.DTO.RispostaListaRichiedenteDTO;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.DTO.bean.ListaLabelValue;
import cdst_be_marche.builder.ConferenzaBuilder;
import cdst_be_marche.builder.PartecipanteBuilder;
import cdst_be_marche.model.Attivita;
import cdst_be_marche.model.CategoriaDocumento;
import cdst_be_marche.model.Comune;
import cdst_be_marche.model.EmailStatus;
import cdst_be_marche.model.Ente;
import cdst_be_marche.model.EsitoChiusuraConferenza;
import cdst_be_marche.model.FormaGiuridica;
import cdst_be_marche.model.Modalita;
import cdst_be_marche.model.OggettoEvento;
import cdst_be_marche.model.Persona;
import cdst_be_marche.model.Provincia;
import cdst_be_marche.model.Regione;
import cdst_be_marche.model.ResponsabileConferenza;
import cdst_be_marche.model.RubricaImprese;
import cdst_be_marche.model.RubricaRichiedenti;
import cdst_be_marche.model.RuoloPartecipante;
import cdst_be_marche.model.RuoloPersona;
import cdst_be_marche.model.Stato;
import cdst_be_marche.model.TipoEvento;
import cdst_be_marche.model.TipoParere;
import cdst_be_marche.model.TipologiaConferenza;
import cdst_be_marche.model.TipologiaPratica;
import cdst_be_marche.model.bean._Typological;
import cdst_be_marche.repository.AccreditamentoRepository;
import cdst_be_marche.repository.AttivitaRepository;
import cdst_be_marche.repository.AzioneRepository;
import cdst_be_marche.repository.CategoriaDocumentoRepository;
import cdst_be_marche.repository.ComuneRepository;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.EmailStatusRepository;
import cdst_be_marche.repository.EnteRepository;
import cdst_be_marche.repository.EsitoChiusuraConferenzaRepository;
import cdst_be_marche.repository.FormaGiuridicaRepository;
import cdst_be_marche.repository.ModalitaRepository;
import cdst_be_marche.repository.PersonaRepository;
import cdst_be_marche.repository.ProvinciaRepository;
import cdst_be_marche.repository.RegioneRepository;
import cdst_be_marche.repository.ResponsabileConferenzaRepository;
import cdst_be_marche.repository.RubricaImpreseRepository;
import cdst_be_marche.repository.RubricaRichiedentiRepository;
import cdst_be_marche.repository.RuoloPartecipanteRepository;
import cdst_be_marche.repository.RuoloPersonaRepository;
import cdst_be_marche.repository.StatoRepository;
import cdst_be_marche.repository.TipoEventoRepository;
import cdst_be_marche.repository.TipoParereRepository;
import cdst_be_marche.repository.TipoProfiloRepository;
import cdst_be_marche.repository.TipologiaConferenzaRepository;
import cdst_be_marche.repository.TipologiaPraticaRepository;
import cdst_be_marche.util.DbConst;
import cdst_be_marche.validator.ConferenceValidator;

@Transactional
@Service
public class CaricaComboService extends _BaseService {

	@Autowired
	RegioneRepository regRepo;

	@Autowired
	ProvinciaRepository provRepo;

	@Autowired
	ComuneRepository comuRepo;

	@Autowired
	TipologiaConferenzaRepository tipConfRepo;

	@Autowired
	AccreditamentoRepository accRepo;

	@Autowired
	PersonaRepository personaRepo;

	@Autowired
	MessageSource messageSource;

	@Autowired
	TipologiaPraticaRepository tipPraticaRepo;

	@Autowired
	AttivitaRepository attivitaRepo;

	@Autowired
	AzioneRepository azioneRepo;

	@Autowired
	HttpServletRequest request;

	@Autowired
	FormaGiuridicaRepository formaGiuridicaRepo;

	@Autowired
	RuoloPartecipanteRepository ruoloPartecipanteRepo;

	@Autowired
	RuoloPersonaRepository ruoloPersonaRepo;

	@Autowired
	ResponsabileConferenzaRepository responsabileConferenzaRepo;

	@Autowired
	EnteRepository enteRepo;

	@Autowired
	CategoriaDocumentoRepository categoriaDocRepo;

	@Autowired
	StatoRepository statoRepo;

	@Autowired
	TipoProfiloRepository tipoProfiloRepo;

	@Autowired
	UtenteService utenteService;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	ModalitaRepository modalitaRepo;

	@Autowired
	ConferenzaRepository conferenzaRepo;

	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	RubricaRichiedentiRepository rubricaRichRepo;

	@Autowired
	TipologiaConferenzaRepository tipoConfRepo;

	@Autowired
	EmailStatusRepository emailStatusRepo;

	@Autowired
	ConferenceValidator confValidator;

	@Autowired
	RubricaImpreseRepository rubricaImprRepo;

	@Autowired
	ConferenzaBuilder confBuilder;
	
	@Autowired
	EsitoChiusuraConferenzaRepository esitoChiusuraConfRepo;
	
	@Autowired
	TipoParereRepository tipoParereRepository;

	/**
	 * Restituisce l'elenco di tutte le regioni
	 * 
	 * @param search
	 * 
	 * @return
	 */
	public RispostaListaLabelValueDTO findRegioniAll(String search) {
		List<Regione> listaRegioni = new ArrayList<>();
		Sort sort = new Sort(Direction.ASC, "descrizione");
		if (search == null || search.isEmpty()) {
			listaRegioni = this.regRepo.findAll(sort);
		} else {
			listaRegioni = this.regRepo.findByDescrizioneContainingIgnoreCase(search, sort);
		}
		ListaLabelValue lista = new ListaLabelValue();
		for (Regione regione : listaRegioni) {
			lista.getList().add(new LabelValue(regione.getCodice(), regione.getDescrizione()));
		}
		lista.setTotalNumber(new Long(listaRegioni.size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	/**
	 * restituisce le province relative alla regione con codice 'idRegione'
	 * 
	 * @param idRegione
	 * @param search
	 * @return
	 */
	public RispostaListaLabelValueDTO findProvinceByRegione(String idRegione, String search) {
		List<Provincia> listaProvince = new ArrayList<>();
		Sort sort = new Sort(Direction.ASC, "descrizione");
		Regione regione = null;
		if (idRegione != null && !idRegione.isEmpty()) {
			regione = this.regRepo.findByCodice(idRegione).orElse(null);
		}
		if (search != null && !search.isEmpty()) {
			if (regione == null) {
				listaProvince = this.provRepo.findByDescrizioneContainingIgnoreCase(search, sort);
			} else {
				listaProvince = this.provRepo.findByRegioneAndDescrizioneContainingIgnoreCase(regione, search, sort);
			}
		}
		if ((search == null || search.isEmpty()) && regione != null) {
			listaProvince = this.provRepo.findByRegione(regione, sort);
		}
		ListaLabelValue lista = new ListaLabelValue();
		for (Provincia provincia : listaProvince) {
			lista.getList().add(new LabelValue(provincia.getCodice(), provincia.getDescrizione()));
		}
		lista.setTotalNumber(new Long(listaProvince.size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	/**
	 * restituisce i comuni relativi alla provincia con codice 'idProv'
	 * 
	 * @param idProv
	 * @param search
	 * @return
	 */
	public RispostaListaLabelValueDTO findComuneByProvincia(String idProv, String search) {
		List<Comune> listaComune = new ArrayList<>();
		Sort sort = new Sort(Direction.ASC, "descrizione");
		Provincia prov = null;
		if (idProv != null && !idProv.isEmpty()) {
			prov = this.provRepo.findByCodice(idProv).orElse(null);
		}
		if (search != null && !search.isEmpty()) {
			if (prov == null) {
				listaComune = this.comuRepo.findByDescrizioneContainingIgnoreCase(search, sort);
			} else {
				listaComune = this.comuRepo.findByProvinciaAndDescrizioneContainingIgnoreCase(prov, search, sort);
			}
		}
		if ((search == null || search.isEmpty()) && prov != null) {
			listaComune = this.comuRepo.findByProvincia(prov, sort);
		}
		ListaLabelValue lista = new ListaLabelValue();
		for (Comune comune : listaComune) {
			lista.getList().add(new LabelValue(comune.getCodice(), comune.getDescrizione()));
		}
		lista.setTotalNumber(new Long(listaComune.size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaListaLabelValueDTO findPersonaByString(String str) {
		Sort sort = new Sort(Direction.ASC, "cognome").and(new Sort(Direction.ASC, "nome"));
		List<Persona> persone = this.personaRepo.findByNomeContainingOrCognomeContainingAllIgnoreCase(str, str, sort);
		return findTypologicalAll(persone);
	}

	public RispostaListaLabelValueDTO findTipologieConferenzeAll() {
		List<TipologiaConferenza> listaTipologie = this.tipConfRepo.findAll();
		return findTypologicalAll(listaTipologie);
	}

	private RispostaListaLabelValueDTO findTypologicalAll(List<? extends _Typological> listaTypological) {
		ListaLabelValue lista = new ListaLabelValue();
		for (_Typological typological : listaTypological) {
			lista.getList()
					.add(new LabelValue(typological.getCodice(), messageSource.getMessage(typological.getDescrizione(),
							null, typological.getDescrizione(), request.getLocale())));
		}
		lista.setTotalNumber(new Long(listaTypological.size()));
		RispostaListaLabelValueDTO risposta = new RispostaListaLabelValueDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaListaLabelValueDTO findTipologiePraticheAll() {
		List<TipologiaPratica> listaTipologiePratiche = this.tipPraticaRepo.findAll();
		return findTypologicalAll(listaTipologiePratiche);
	}

	public RispostaListaLabelValueDTO findAttivitaByTipologiaPratica(String codicePratica) {
		Sort sort = new Sort(Direction.ASC, "descrizione");
		TipologiaPratica tipologiaPratica = this.tipPraticaRepo.findByCodice(codicePratica);
		return findTypologicalAll(this.attivitaRepo.findByTipologiaPratica(tipologiaPratica, sort));
	}

	public RispostaListaLabelValueDTO findAzioniByAttivita(String codiceAttivita) {
		Sort sort = new Sort(Direction.ASC, "descrizione");
		Attivita attivita = this.attivitaRepo.findByCodice(codiceAttivita);
		return findTypologicalAll(this.azioneRepo.findByAttivita(attivita, sort));
	}

	public RispostaListaLabelValueDTO findFormaGiuridicaAll() {
		List<FormaGiuridica> listaFormaGiuridica = this.formaGiuridicaRepo.findAll();
		return findTypologicalAll(listaFormaGiuridica);
	}

	public RispostaListaLabelValueDTO findRuoliPartecipantiAll() {
		List<RuoloPartecipante> listaRuoloPartecipante = ruoloPartecipanteRepo.findAll();
		return findTypologicalAll(listaRuoloPartecipante);
	}

	public RispostaListaLabelValueDTO findRuoliPersoneByRuoloPartecipante(String codiceRuoloPartecipante) {
		RuoloPartecipante ruoloPartecipante = ruoloPartecipanteRepo.findById(codiceRuoloPartecipante).get();
		return findTypologicalAll(ruoloPartecipante.getRuoliPersona());
	}

	public RispostaListaLabelValueDTO findRuoliPersoneAccreditation() {
		List<RuoloPersona> ruoli = new ArrayList<>();
		int[] ruoliPerAccreditamento = { DbConst.RUOLO_PERSONA_RESPONSABILE_PROCEDIMENTO,
				DbConst.RUOLO_PERSONA_RESPONSABILE_UNICO, DbConst.RUOLO_PERSONA_SOGGETTO_ACCREDIDATO };
		for (int i = 0; i < ruoliPerAccreditamento.length; i++) {
			ruoli.add(ruoloPersonaRepo.findById(Integer.toString(ruoliPerAccreditamento[i])).get());
		}
		return findTypologicalAll(ruoli);
	}

	public RispostaListaResponsabiliConferenzaDTO findResponsabiliConferenzaAll(String key) {
		Ente ente = new Ente();
		if (key == null || key.isEmpty()) {
			ente = utenteService.getAmministrazioneProcedente();
		} else {
			ente = enteRepo.findById(new Integer(key)).orElse(null);
		}
		List<ResponsabileConferenza> listaResponsabiliConferenza = responsabileConferenzaRepo.findAllByEnte(ente);
		return findResponsabiliConferenzaAll(listaResponsabiliConferenza);
	}

	public RispostaListaLabelValueDTO findEntiByString(String search) {
		Sort sort = new Sort(Direction.ASC, "descrizioneEnte");
		List<Ente> listaEnti = null;
		if (search == null || search.isEmpty()) {
			listaEnti = enteRepo.findAll(sort);
		} else {
			listaEnti = enteRepo.findByDescrizioneEnteContainingIgnoreCase(search, sort);
		}
		return findTypologicalAll(listaEnti);
	}

	public RispostaEnteDTO findEnte(Integer id) {
		Ente ente = enteRepo.findById(id).get();
		EnteDTO enteDTO = this.partecipanteBuilder.enteToEnteDTO(ente);
		RispostaEnteDTO risposta = new RispostaEnteDTO();
		risposta.setData(enteDTO);
		return risposta;
	}

	public RispostaListaLabelValueDTO findCategorieDocumentiAll() {
		List<CategoriaDocumento> listaCategorieDocumenti = categoriaDocRepo.findAll();
		return findTypologicalAll(listaCategorieDocumenti);
	}

	public RispostaListaLabelValueDTO findStatiAll() {
		List<Stato> listaStati = statoRepo.findAll();
		return findTypologicalAll(listaStati);
	}

	private RispostaListaResponsabiliConferenzaDTO findResponsabiliConferenzaAll(
			List<ResponsabileConferenza> listaResp) {
		ListaResponsabiliConferenza lista = new ListaResponsabiliConferenza();
		for (ResponsabileConferenza responsabile : listaResp) {
			ResponsabileConferenzaDTO responsabileConferenzaDTO = new ResponsabileConferenzaDTO(
					responsabile.getCodice(), messageSource.getMessage(responsabile.getDescrizione(), null,
							responsabile.getDescrizione(), request.getLocale()));
			responsabileConferenzaDTO.setNome(responsabile.getPersona().getNome());
			responsabileConferenzaDTO.setCognome(responsabile.getPersona().getCognome());
			responsabileConferenzaDTO.setCodiceFiscale(responsabile.getPersona().getCodiceFiscale());
			responsabileConferenzaDTO.setEmail(responsabile.getPersona().getEmail());
			responsabileConferenzaDTO.setProfilo(createNotNullLabelValue(this.tipoProfiloRepo
					.findByCodice(Integer.toString(DbConst.TIPO_PROFILO_RESPONSABILE_CONFERENZA)).get()));
			lista.getList().add(responsabileConferenzaDTO);

		}
		lista.setTotalNumber(new Long(listaResp.size()));
		RispostaListaResponsabiliConferenzaDTO risposta = new RispostaListaResponsabiliConferenzaDTO();
		risposta.setData(lista);
		return risposta;
	}

	public RispostaEnteDTO findAuthority(Integer idAuthority) {
		RispostaEnteDTO risposta = new RispostaEnteDTO();
		if (idAuthority == null) {
			EnteDTO enteDto = utenteService.findAmministrazioneProcedente();
			risposta.setData(enteDto);
			return risposta;
		} else {
			return findEnte(idAuthority);
		}
	}

	public RispostaListaLabelValueDTO findModalitaIndirizzoSessioneSimAll() {
		List<Modalita> listaModalita = modalitaRepo.findAll();
		return findTypologicalAll(listaModalita);
	}

	public RispostaListaLabelValueDTO findAllAuthority() {
		List<Ente> lista = this.enteRepo.findByFlagAmministrazioneProcedente(Boolean.TRUE);
		return findTypologicalAll(lista);
	}

	public RispostaLabelValueDTO findOggettoEvento(LabelValue tipoEventoDTO) {
		TipoEvento tipoEvento = this.tipoEventoRepo.findById(tipoEventoDTO.getKey()).orElse(null);
		OggettoEvento oggettoEvento = tipoEvento.getOggettoEvento();
		RispostaLabelValueDTO risposta = new RispostaLabelValueDTO();
		if (oggettoEvento != null) {
			risposta.setData(new LabelValue(oggettoEvento.getCodice(), oggettoEvento.getDescrizione()));
		} else {
			risposta.setData(null);
		}
		return risposta;
	}

	public RispostaConferenceTemplateDTO findConferenceTemplateDaPrecaricare(String type) {
		this.confValidator.validateTypeConference(type);
		List<RubricaRichiedenti> rubricaRichList = this.rubricaRichRepo
				.findByTipologiaConferenza(this.tipoConfRepo.findById(type).get());
		RichiedenteDTO richiedenteDTO;
		if (rubricaRichList.size() != 0) {
			richiedenteDTO = this.partecipanteBuilder.buildRichiedenteDTO(rubricaRichList.get(0).getPersona());
		} else {
			richiedenteDTO = null;
		}
		List<RubricaImprese> rubricaImpreseList = this.rubricaImprRepo
				.findByTipologiaConferenza(this.tipoConfRepo.findById(type).get());
		ImpresaDTO impresaDTO;
		if (rubricaImpreseList.size() != 0) {
			impresaDTO = this.confBuilder.buildImpresaDTO(rubricaImpreseList.get(0).getImpresa());
		} else {
			impresaDTO = null;
		}
		ConferenceTemplateDTO confTemplateDTO = new ConferenceTemplateDTO();
		confTemplateDTO.setImpresaDTO(impresaDTO);
		confTemplateDTO.setRichiedenteDTO(richiedenteDTO);
		RispostaConferenceTemplateDTO risposta = new RispostaConferenceTemplateDTO();
		risposta.setData(confTemplateDTO);
		return risposta;
	}

	public RispostaListaRichiedenteDTO findListaRichiedentiDaPrecaricare(String type) {
		this.confValidator.validateTypeConference(type);
		List<RubricaRichiedenti> rubricaRichLista = this.rubricaRichRepo
				.findByTipologiaConferenza(this.tipoConfRepo.findById(type).get());
		ListaRichiedenteDTO listaRichiedenteDTO = new ListaRichiedenteDTO();
		for (RubricaRichiedenti rubricaRich : rubricaRichLista) {
			RichiedenteDTO richiedenteDTO = this.partecipanteBuilder.buildRichiedenteDTO(rubricaRich.getPersona());
			listaRichiedenteDTO.getList().add(richiedenteDTO);
		}
		RispostaListaRichiedenteDTO risposta = new RispostaListaRichiedenteDTO();
		risposta.setData(listaRichiedenteDTO);
		return risposta;
	}

	public RispostaListaLabelValueDTO findEventType() {
		List<TipoEvento> listaTipoEvento = this.tipoEventoRepo.findAll();
		return findTypologicalAll(listaTipoEvento);
	}

	public RispostaListaLabelValueDTO findEmailStatus() {
		List<EmailStatus> listaEmailStatus = this.emailStatusRepo.findAll();
		return findTypologicalAll(listaEmailStatus);
	}

	public RispostaConferenceTemplateListDTO findConferenceTemplateListDaPrecaricare(String type) {
		this.confValidator.validateTypeConference(type);
		ConferenceTemplateListDTO confTemplateList = new ConferenceTemplateListDTO();
		List<RubricaRichiedenti> rubricaRichList = this.rubricaRichRepo
				.findByTipologiaConferenza(this.tipoConfRepo.findById(type).get());
		for (RubricaRichiedenti rubricaRichiedenti : rubricaRichList)
			confTemplateList.getListaRichiedenteDTO()
					.add(this.partecipanteBuilder.buildRichiedenteDTO(rubricaRichiedenti.getPersona()));
		List<RubricaImprese> rubricaImpreseList = this.rubricaImprRepo
				.findByTipologiaConferenza(this.tipoConfRepo.findById(type).get());
		for (RubricaImprese rubricaImprese: rubricaImpreseList)
			confTemplateList.getListaImpresaDTO().add(this.confBuilder.buildImpresaDTO(rubricaImprese.getImpresa()));
		RispostaConferenceTemplateListDTO risposta = new RispostaConferenceTemplateListDTO();
		risposta.setData(confTemplateList);
		return risposta;
	}

	public RispostaListaLabelValueDTO findConferencesResults() {
		List<EsitoChiusuraConferenza> listaEsiti = this.esitoChiusuraConfRepo.findAll();
		return findTypologicalAll(listaEsiti);
	}

	public IdentifiableDTO creaEnte(EnteDTO enteDTO) {
		Ente saved;
		if (!this.enteRepo.findByCodiceFiscaleEnte(enteDTO.getCodiceFiscale()).isPresent()) {
			saved = this.enteRepo.save(this.confBuilder.enteDTOToEnte(enteDTO));
		} else {
			saved = this.enteRepo.findByCodiceFiscaleEnte(enteDTO.getCodiceFiscale()).get();
		}
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdEnte());
		return identifiableDTO;
	}

	public RispostaListaLabelValueDTO findDeterminationType() {
		List<TipoParere> listaTipoParere = this.tipoParereRepository.findAll();
		return findTypologicalAll(listaTipoParere);
	}

}
