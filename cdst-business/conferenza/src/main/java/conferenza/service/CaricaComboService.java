package conferenza.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.DTO.ConferenceTemplateDTO;
import conferenza.DTO.ConferenceTemplateDefinitionDTO;
import conferenza.DTO.ConferenceTemplateInstanceAddressDTO;
import conferenza.DTO.ConferenceTemplateInstanceConferenceTimeDTO;
import conferenza.DTO.ConferenceTemplateInstanceDTO;
import conferenza.DTO.ConferenceTemplateInstanceDateDTO;
import conferenza.DTO.ConferenceTemplateListDTO;
import conferenza.DTO.ConferenceTemplateParticipantDTO;
import conferenza.DTO.ConferenceTemplateProcedureDTO;
import conferenza.DTO.DataIstanza;
import conferenza.DTO.EnteDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaResponsabiliConferenza;
import conferenza.DTO.ListaRichiedenteDTO;
import conferenza.DTO.ResponsabileConferenzaDTO;
import conferenza.DTO.RichiedenteDTO;
import conferenza.DTO.RispostaBoolean;
import conferenza.DTO.RispostaConferenceTemplateDTO;
import conferenza.DTO.RispostaConferenceTemplateListDTO;
import conferenza.DTO.RispostaEnteDTO;
import conferenza.DTO.RispostaLabelValueDTO;
import conferenza.DTO.RispostaListaLabelValueDTO;
import conferenza.DTO.RispostaListaResponsabiliConferenzaDTO;
import conferenza.DTO.RispostaListaRichiedenteDTO;
import conferenza.DTO.RubricaImpresaDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.DTO.bean.ListaLabelValue;
import conferenza.builder.ConferenzaBuilder;
import conferenza.builder.EnteBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.builder.UtenteBuilder;
import conferenza.exception.NotFoundEx;
import conferenza.model.SelezioneAmministrazioneProcedente;
import conferenza.model.SelezioneTipologiaConferenza;
import conferenza.model.Attivita;
import conferenza.model.CategoriaDocumento;
import conferenza.model.CompetenzaAutorizzativa;
import conferenza.model.Comune;
import conferenza.model.EmailStatus;
import conferenza.model.Ente;
import conferenza.model.EnteTipologiaAmministrativa;
import conferenza.model.EnteTipologiaIstat;
import conferenza.model.EsitoChiusuraConferenza;
import conferenza.model.FormaGiuridica;
import conferenza.model.Modalita;
import conferenza.model.OggettoEvento;
import conferenza.model.Persona;
import conferenza.model.Provincia;
import conferenza.model.Regione;
import conferenza.model.ResponsabileConferenza;
import conferenza.model.RubricaImprese;
import conferenza.model.RubricaRichiedenti;
import conferenza.model.RuoloPartecipante;
import conferenza.model.RuoloPersona;
import conferenza.model.Stato;
import conferenza.model.TipoData;
import conferenza.model.TipoEvento;
import conferenza.model.TipoParere;
import conferenza.model.TipologiaConferenza;
import conferenza.model.TipologiaConferenzaDataDefinizione;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.model.TipologiaPratica;
import conferenza.model.Utente;
import conferenza.model.bean._Typological;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.SelezioneAmministrazioneProcedenteRepository;
import conferenza.repository.SelezioneTipologiaConferenzaRepository;
import conferenza.repository.AttivitaRepository;
import conferenza.repository.AzioneRepository;
import conferenza.repository.CategoriaDocumentoRepository;
import conferenza.repository.ComuneRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EmailStatusRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EnteTipologiaAmministrativaRepository;
import conferenza.repository.EnteTipologiaIstatRepository;
import conferenza.repository.EsitoChiusuraConferenzaRepository;
import conferenza.repository.FormaGiuridicaRepository;
import conferenza.repository.ModalitaRepository;
import conferenza.repository.ModelloRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProfiloRepository;
import conferenza.repository.ProvinciaRepository;
import conferenza.repository.RegioneRepository;
import conferenza.repository.ResponsabileConferenzaRepository;
import conferenza.repository.RubricaImpreseRepository;
import conferenza.repository.RubricaPartecipantiRepository;
import conferenza.repository.RubricaRichiedentiRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.repository.RuoloPersonaRepository;
import conferenza.repository.SezioneDocumentazioneRepository;
import conferenza.repository.StatoRepository;
import conferenza.repository.TipoDataRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.repository.TipoParereRepository;
import conferenza.repository.TipoProfiloRepository;
import conferenza.repository.TipologiaConferenzaDataDefinizioneRepository;
import conferenza.repository.TipologiaConferenzaRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.repository.TipologiaPraticaRepository;
import conferenza.repository.UtenteRepository;
import conferenza.util.DbConst;
import conferenza.validator.ConferenceValidator;
import javassist.NotFoundException;

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
	UtenteRepository utenteRepo;

	@Autowired
	TipologiaConferenzaRepository tipConfRepo;

	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipConfSpecRepo;

	@Autowired
	TipologiaConferenzaDataDefinizioneRepository tipConfDataDefinizioneRepo;

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
	TipologiaConferenzaSpecializzazioneRepository tipoConfSpecializzazioneRepo;

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

	@Autowired
	TipologiaDocumentoRepository tipologiaDocRepo;
	
	@Autowired
	RuoloPartecipanteRepository ruoloPartRepo;
	
	@Autowired
	EnteTipologiaAmministrativaRepository enteTipoAmmRepo;
	
	@Autowired
	EnteTipologiaIstatRepository enteTipoIstatRepo;
	
	@Autowired
	EnteBuilder enteBuilder;
	
	@Autowired
	ProfiloRepository profiloRepo;
	
	@Autowired
	UtenteBuilder utenteBuilder;
	
	@Autowired
	RubricaPartecipantiRepository rubricaPartRepo;
	
	@Autowired
	PermessoService permessoService;
	
	@Autowired
	ModelloRepository modelloRepo;
	
	@Autowired
	SezioneDocumentazioneRepository sezioneDocRepo;

	@Autowired
	TipoDataRepository tipoDataRepo;
	
	@Autowired
	SelezioneAmministrazioneProcedenteRepository amministrazioneProcedenteRepo;
	
	@Autowired
	SelezioneTipologiaConferenzaRepository selezioneTipologiaConferenzaRepo;

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

	public RispostaListaLabelValueDTO findTipologieConferenzeSpecializzazioneAll() {
		List<TipologiaConferenzaSpecializzazione> listaTipologie = this.tipConfSpecRepo.findAll();
		return findTypologicalAll(listaTipologie);
	}

	public RispostaListaLabelValueDTO findTipologieConferenzeSpecializzazioneAll(Utente utente) {
		List<SelezioneTipologiaConferenza> lista = this.selezioneTipologiaConferenzaRepo.findByUtente(utente);

		List<TipologiaConferenzaSpecializzazione> confLista = lista.stream()
			    .map(e -> e.getTipologiaConferenzaSpecializzazione())
			    .collect(Collectors.toList());

		if(confLista.size() == 0)
			return findTipologieConferenzeSpecializzazioneAll();

		return findTypologicalAll(confLista);
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
		return findTypologicalAll(ruoloPartecipante.getListaRuoloPersonaPerRuoloPart());
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

	public EnteDTO findEnte(Integer id) {
		Ente ente = enteRepo.findById(id).get();
		return this.enteBuilder.enteToEnteDTO(ente);
	}

	public RispostaListaLabelValueDTO findCategorieDocumentiAll(String tipologiaConferenza, String tipoEvento) {
		return findTypologicalAll(findCategoriaDocByTipologiaConfAndTipoEvento(tipologiaConferenza, tipoEvento));
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

	/*
	 * Utilizzato per la l'inizializzazione della conferenza, 
	 * viene cablato come ruolo quello di amministrazione procedente
	 */
	public RispostaEnteDTO findAuthority(Integer idAuthority) {
		RispostaEnteDTO risposta = new RispostaEnteDTO();
		if (idAuthority == null) {
			EnteDTO enteDto = utenteService.findAmministrazioneProcedente();
			risposta.setData(enteDto);
			return risposta;
		} else {
			EnteDTO enteDto = findEnte(idAuthority);
			enteDto.setRuolo(createNotNullLabelValue(this.ruoloPartecipanteRepo
					.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get()));
			risposta.setData(enteDto);
			return risposta;
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

	public RispostaListaLabelValueDTO findFilteredAuthority(Utente utente) {
		List<SelezioneAmministrazioneProcedente> lista = this.amministrazioneProcedenteRepo.findByUtente(utente);

		List<Ente> enteLista = lista.stream()
				.filter(e -> e.getEnte().getFlagAmministrazioneProcedente())
			    .map(e -> e.getEnte())
			    .collect(Collectors.toList());
		
		if(enteLista.size() == 0)
			return findAllAuthority();

		return findTypologicalAll(enteLista);
	}
	

	public RispostaListaLabelValueDTO findAllConfernzaCreators(Utente utente) {
		List<Utente> utenti = utenteRepo.getAllConferenzaCreatorUsers(utente.getIdUtente());
//				.stream()
//			    .map(e -> e.getEnte())
//			    .collect(Collectors.toList());
		
		return findTypologicalAll(utenti);
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

	/**
	 * <pre>
	 * La risposta è del tipo:
     *    {
     *    	...
     *        "data": {
     *            "procedure": {
     *                "applicant": {}, //solo applicant non collegati a nessuna impresa (il primo della lista se più di uno)
     *                "companySection": [ //può essere un elenco vuoto
     *                    {
     *                        "company": {...},
     *                        "applicant": {...} //eventuale applicant (principale) associato alla company
     *                    }
     *                ]
     *            },
     *            "definition": {
     *                "instance": {
     *                    "endIntegrationDate": {
     *                        "offsetBusinessDay": "integer",
     *                        "offsetDay": "integer",
     *                        "baseDate": "creationDate|endOpinionDate|endIntegrationDate|firstSessionDate",
     *                        "required": "boolean"
     *                    },
     *                    "endOpinionDate": {
     *                        "offsetBusinessDay": "integer",
     *                        "offsetDay": "integer",
     *                        "baseDate": "creationDate|endOpinionDate|endIntegrationDate|firstSessionDate",
     *                        "required": "boolean"
     *                    },
     *                    "firstSessionDate": {
     *                        "offsetBusinessDay": "integer",
     *                        "offsetDay": "integer",
     *                        "baseDate": "creationDate|endOpinionDate|endIntegrationDate|firstSessionDate",
     *                        "required": "boolean"
     *                    },
     *                    "expirationDate": {
     *                        "offsetBusinessDay": "integer",
     *                        "offsetDay": "integer",
     *                        "baseDate": "creationDate|endOpinionDate|endIntegrationDate|firstSessionDate",
     *                        "required": "boolean"
     *                    },
     *                    "conferenceTime": {
     *                        "visible": "boolean"
     *                    },
     *                    "address": {
     *                        "type": "1|2", // 1= online, 2= fisica,
     *                        "disabled": "boolean"
     *                    }
     *                }
     *            },
     *            "participant": {
     *                "competence": [ // se lista vuota => input di tipo testuale
     *                    {
     *                        "key": "number",
     *                        "value": "string"
     *                    }
     *                ]
     *            }
     *        }
     *    }
	 * </pre>
	 * @param type
	 * @return
	 */
	public RispostaConferenceTemplateDTO findConferenceTemplateDaPrecaricare(String type) {
		this.confValidator.validateTypeConferenceSpecialization(type);
		
		TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione = tipConfSpecRepo.findById(type).get();	
		
		ConferenceTemplateDTO conferenceTemplateDTO = new ConferenceTemplateDTO();
		
		/*
		 * procedure ||||||||||||||||||||||||||||
		 */
		ConferenceTemplateProcedureDTO conferenceTemplateProcedureDTO = new ConferenceTemplateProcedureDTO();
		
		/*
		 * --- applicant
		 */
		RichiedenteDTO richiedenteDTO = null;
		List<RubricaRichiedenti> richiedenti = rubricaRichRepo.findByTipologiaConferenzaSpecializzazione(tipologiaConferenzaSpecializzazione);
		for (RubricaRichiedenti richiedente : richiedenti) {
			if (richiedente.getImpresa() == null) {
				richiedenteDTO = partecipanteBuilder.buildRichiedenteDTO(richiedente.getPersona());
			}
		}
		conferenceTemplateProcedureDTO.setRichiedenteDTO(richiedenteDTO );
		
		/*
		 * --- companySection
		 */
		List<RubricaImprese> imprese = rubricaImprRepo.findByTipologiaConferenzaSpecializzazione(tipologiaConferenzaSpecializzazione);
		for (RubricaImprese impresa : imprese) {
			RubricaImpresaDTO rubricaImpresaDTO = new RubricaImpresaDTO();
			rubricaImpresaDTO.setImpresaDTO(confBuilder.buildImpresaDTO(impresa.getImpresa()));
			List<RubricaRichiedenti> richiedentiPrincipali = rubricaRichRepo.findByImpresaAndPrincipale(impresa, true);
			if (!richiedentiPrincipali.isEmpty()) {
				RubricaRichiedenti richiedentePrincipale = richiedentiPrincipali.get(0);
				rubricaImpresaDTO.setRichiedenteDTO(partecipanteBuilder.buildRichiedenteDTO(richiedentePrincipale.getPersona()));
			}
			
			conferenceTemplateProcedureDTO.getRubricaImprese().add(rubricaImpresaDTO);
		}
		
		conferenceTemplateDTO.setConferenceTemplateProcedureDTO(conferenceTemplateProcedureDTO);		
		
		/*
		 * definition ||||||||||||||||||||||||||||
		 */
		TipologiaConferenza tipologiaConferenza = tipologiaConferenzaSpecializzazione.getTipologiaConferenza();
		ConferenceTemplateDefinitionDTO conferenceTemplateDefinitionDTO = new ConferenceTemplateDefinitionDTO();
		ConferenceTemplateInstanceDTO conferenceTemplateInstanceDTO = new ConferenceTemplateInstanceDTO();
		conferenceTemplateInstanceDTO.setEndIntegrationDate(getConferenceTemplateInstanceDate(DataIstanza.endIntegrationDate, tipologiaConferenza));
		conferenceTemplateInstanceDTO.setEndOpinionDate(getConferenceTemplateInstanceDate(DataIstanza.endOpinionDate, tipologiaConferenza));
		conferenceTemplateInstanceDTO.setFirstSessionDate(getConferenceTemplateInstanceDate(DataIstanza.firstSessionDate, tipologiaConferenza));
		conferenceTemplateInstanceDTO.setExpirationDate(getConferenceTemplateInstanceDate(DataIstanza.expirationDate, tipologiaConferenza));
		ConferenceTemplateInstanceConferenceTimeDTO conferenceTime = new ConferenceTemplateInstanceConferenceTimeDTO();
		conferenceTime.setVisible(tipologiaConferenza.getFlagOrarioVisibile());
		conferenceTemplateInstanceDTO.setConferenceTime(conferenceTime);
		ConferenceTemplateInstanceAddressDTO address = new ConferenceTemplateInstanceAddressDTO();
		address.setDisabled(!tipologiaConferenza.getFlagModalitaIncontroModificabile());
		if (tipologiaConferenza.getModalita() != null) {
			address.setType(tipologiaConferenza.getModalita().getCodice());
		}
		conferenceTemplateInstanceDTO.setAddress(address);
		conferenceTemplateDefinitionDTO.setConferenceTemplateInstanceDTO(conferenceTemplateInstanceDTO );
		conferenceTemplateDTO.setConferenceTemplateDefinitionDTO(conferenceTemplateDefinitionDTO);
		
		/*
		 * participant ||||||||||||||||||||||||||||
		 */
		ConferenceTemplateParticipantDTO conferenceTemplateParticipantDTO = new ConferenceTemplateParticipantDTO();
		if (!tipologiaConferenza.getListaCompAutoPerTipologiaConf().isEmpty()) {
			for (CompetenzaAutorizzativa competenza : tipologiaConferenza.getListaCompAutoPerTipologiaConf()) {
				conferenceTemplateParticipantDTO.getCompetence().add(createNotNullLabelValue(competenza));
			}
		}
		conferenceTemplateDTO.setConferenceTemplateParticipantDTO(conferenceTemplateParticipantDTO);
		
		RispostaConferenceTemplateDTO risposta = new RispostaConferenceTemplateDTO();
		risposta.setData(conferenceTemplateDTO);
		return risposta;
	}

	private ConferenceTemplateInstanceDateDTO getConferenceTemplateInstanceDate(DataIstanza data,
			TipologiaConferenza tipologiaConferenza) {
		ConferenceTemplateInstanceDateDTO conferenceTemplateInstanceDateDTO = new ConferenceTemplateInstanceDateDTO();
		TipologiaConferenzaDataDefinizione tipologiaConferenzaDataDefinizione = tipConfDataDefinizioneRepo
				.findByTipologiaConferenzaAndCampoDataCalcolato(tipologiaConferenza, data.name()).orElse(null);
		if (tipologiaConferenzaDataDefinizione != null) {
			conferenceTemplateInstanceDateDTO.setBaseDate(tipologiaConferenzaDataDefinizione.getCampoDataBase());
			conferenceTemplateInstanceDateDTO.setOffsetBusinessDay(tipologiaConferenzaDataDefinizione.getOffsetGiorniLavorativi());
			conferenceTemplateInstanceDateDTO.setOffsetDay(tipologiaConferenzaDataDefinizione.getOffsetGiorniSolari());
			conferenceTemplateInstanceDateDTO.setRequired(tipologiaConferenzaDataDefinizione.getFlagObbligatorio());
			conferenceTemplateInstanceDateDTO.setOrder(tipologiaConferenzaDataDefinizione.getOrder());
		}
		return conferenceTemplateInstanceDateDTO;
	}

	public RispostaListaRichiedenteDTO findListaRichiedentiDaPrecaricare(String type) {
		this.confValidator.validateTypeConferenceSpecialization(type);
		List<RubricaRichiedenti> rubricaRichLista = this.rubricaRichRepo
				.findByTipologiaConferenzaSpecializzazione(this.tipConfSpecRepo.findById(type).get());
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
		List<TipoEvento> listaTipoEvento = this.tipoEventoRepo.findAllTypeNotIn1419();
		return findTypologicalAll(listaTipoEvento);
	}
	
	public RispostaListaLabelValueDTO findDateTypes() {
		List<TipoData> listaTipoData = this.tipoDataRepo.findAll();
		return findTypologicalAll(listaTipoData);
	}

	public RispostaListaLabelValueDTO findEmailStatus() {
		List<EmailStatus> listaEmailStatus = this.emailStatusRepo.findAll();
		return findTypologicalAll(listaEmailStatus);
	}

	public RispostaConferenceTemplateListDTO findConferenceTemplateListDaPrecaricare(String type) {
		this.confValidator.validateTypeConferenceSpecialization(type);
		ConferenceTemplateListDTO confTemplateList = new ConferenceTemplateListDTO();
		List<RubricaRichiedenti> rubricaRichList = this.rubricaRichRepo
				.findByTipologiaConferenzaSpecializzazione(this.tipConfSpecRepo.findById(type).get());
		for (RubricaRichiedenti rubricaRichiedenti : rubricaRichList)
			confTemplateList.getListaRichiedenteDTO()
					.add(this.partecipanteBuilder.buildRichiedenteDTO(rubricaRichiedenti.getPersona()));
		List<RubricaImprese> rubricaImpreseList = this.rubricaImprRepo
				.findByTipologiaConferenzaSpecializzazione(this.tipConfSpecRepo.findById(type).get());
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
		
		Optional<Ente> ente = enteRepo.findByCodiceFiscaleEnteAndDescrizioneEnte(enteDTO.getCodiceFiscale(),enteDTO.getDescrizione());
		//Verifica inizialmente se è presente l'ente per df e descrizione del ente
		if (!ente.isPresent()){
			ente = enteRepo.findByCodiceFiscaleEnteAndDescrizioneEnteAndCodiceUfficio(enteDTO.getCodiceFiscale(),enteDTO.getDescrizione(),enteDTO.getCodiceUfficio());
		}
		if (!ente.isPresent()) {
			enteDTO.setFlagAmmPrincipale(Boolean.FALSE);
			saved = this.enteRepo.save(this.enteBuilder.enteDTOToEnte(enteDTO));
		} else {
			saved = ente.get();
		}
		
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdEnte());
		return identifiableDTO;
	}

	public RispostaListaLabelValueDTO findDeterminationType() {
		List<TipoParere> listaTipoParere = this.tipoParereRepository.findAll();
		return findTypologicalAll(listaTipoParere);
	}
	
	/**
	 * Metodo utilizzato per popolare la combo delle categorie sia per l'inserimento
	 * di un evento (e relativo documento), sia per l'inserimento di un documento
	 * senza evento. Nel caso di evento il filtro è applicato direttamente sul tipo
	 * evento passato in input. Nel caso di documento diretto si applica il filtro standard
	 * per tipologia conferenza. Se entrambi i valori in input sono null allora sono
	 * restituite tutte le categirie non associate ad eventi
	 * 
	 * @param tipologiaConferenzaStringa
	 * @param tipoEventoStringa
	 * @return
	 */
	public List<CategoriaDocumento> findCategoriaDocByTipologiaConfAndTipoEvento(String tipologiaConferenzaStringa, String tipoEventoStringa) {
		List<CategoriaDocumento> listaCategoriaDoc = new ArrayList<>();
		if (tipoEventoStringa == null || tipoEventoStringa.isEmpty()) {
			if (tipologiaConferenzaStringa == null || tipologiaConferenzaStringa.isEmpty()) {
				listaCategoriaDoc = categoriaDocRepo.findByTipoEventoIsNull();
			} else {
				
				Optional<TipologiaConferenzaSpecializzazione> tipoConferenzaSpecializzata = tipConfSpecRepo.findById(tipologiaConferenzaStringa);
				if(tipoConferenzaSpecializzata.isPresent()) {
					TipologiaConferenza tipologiaConferenza = tipoConferenzaSpecializzata.get().getTipologiaConferenza();
					listaCategoriaDoc = tipologiaConferenza.getListaCategoriaDocPerTipologiaConf();
				}else {
					listaCategoriaDoc = categoriaDocRepo.findByTipoEventoIsNull();
				}
				
			}
		} else {
			TipoEvento tipoEvento = tipoEventoRepo.findById(tipoEventoStringa).get();
			listaCategoriaDoc = categoriaDocRepo.findByTipoEvento(tipoEvento);
		}
		return listaCategoriaDoc;
	}

	public RispostaListaLabelValueDTO findCompetenzaAutorizzativaAll(String tipologiaConferenza) {
		List<CompetenzaAutorizzativa> listaCompAutoPerTipologiaConf = new ArrayList<>();
		if (tipologiaConferenza != null && !tipologiaConferenza.isEmpty()) {
			Optional<TipologiaConferenzaSpecializzazione> tipologiaConfSpecializzazione = tipoConfSpecializzazioneRepo.findById(tipologiaConferenza);
			listaCompAutoPerTipologiaConf = tipologiaConfSpecializzazione.get().getTipologiaConferenza().getListaCompAutoPerTipologiaConf();
		}
		return findTypologicalAll(listaCompAutoPerTipologiaConf);
	}
	
	public RispostaBoolean findObbligoEspressioneParere(String codiceRuolo) {
		Boolean parere = Boolean.FALSE;
		RuoloPartecipante ruolo = ruoloPartecipanteRepo.findById(codiceRuolo).orElse(null);
		if (ruolo != null) {
			parere = ruolo.getObbligoEspressioneParere();
		}
		RispostaBoolean risposta = new RispostaBoolean();
		risposta.setData(parere);
		return risposta;
	}

	public RispostaListaLabelValueDTO findIstatTypesAuthority() {
		List<EnteTipologiaIstat> listaTipologiaIstat = enteTipoIstatRepo.findAll();
		return findTypologicalAll(listaTipologiaIstat);
	}

	public RispostaListaLabelValueDTO findAdministrativeTypesAuthority() {
		List<EnteTipologiaAmministrativa> listaTipologiaAmm = enteTipoAmmRepo.findAll();
		return findTypologicalAll(listaTipologiaAmm);
	}	
	
	public RispostaListaLabelValueDTO findConferenceEventTypes(Integer idConferenza) {
		List<TipoEvento> listaTipoEvento = permessoService.findConferenceEventTypes(idConferenza);
		return findTypologicalAll(listaTipoEvento);
	}

	public RispostaListaLabelValueDTO findCategorieDocumentiPerTipoDocumento(String conferenceType,
			String documentType) {
		TipologiaConferenzaSpecializzazione tipoConfSpec = tipConfSpecRepo.findById(conferenceType).orElse(null);
		if (tipoConfSpec == null) {
			throw new NotFoundEx("conference type not found");
		}
		TipologiaConferenza tipoConf = tipoConfSpec.getTipologiaConferenza();
		List<CategoriaDocumento> lista = tipoConf.getListaCategoriaDocPerTipologiaConf();
		List<CategoriaDocumento> listaFiltrata = lista.stream()
				.filter(cat -> cat.getTipologiaDocumento().getCodice().equals(documentType))
				.collect(Collectors.toList());
		return findTypologicalAll(listaFiltrata);
	}

	public RispostaListaLabelValueDTO findCategorieDocumentiPerTipoEvento(String eventType) {
		return findTypologicalAll(categoriaDocRepo.findByTipoEvento(tipoEventoRepo.findById(eventType).orElse(null)));
	}

	public RispostaListaLabelValueDTO findModelliDocumentiPerTipologiaConferenza(String conferenceType) {
		return findTypologicalAll(modelloRepo
				.findByTipologiaConferenzaSpecializzazione(tipConfSpecRepo.findById(conferenceType).orElse(null)));
	}

	public RispostaListaLabelValueDTO findTipoDocumentoPerSezioneDoc(String documentArea) {
		return findTypologicalAll(
				tipologiaDocRepo.findBySezioneDocumentazione(sezioneDocRepo.findById(documentArea).orElse(null)));
	}

	public RispostaListaLabelValueDTO findConferenceStatus() {
		List<Stato> stati = new ArrayList<>();
		int[] statiConferenzaRicerca = { DbConst.STATO_CHIUSA,
				DbConst.STATO_ARCHIVIATA };
		for (int i = 0; i < statiConferenzaRicerca.length; i++) {
			stati.add(statoRepo.findById(Integer.toString(statiConferenzaRicerca[i])).get());
		}
		return findTypologicalAll(stati);
	}
}
