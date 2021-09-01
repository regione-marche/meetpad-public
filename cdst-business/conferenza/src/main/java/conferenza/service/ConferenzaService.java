package conferenza.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.DTO.ConferenzaAnteprimaDTO;
import conferenza.DTO.ConferenzaAutorizzazioniDTO;
import conferenza.DTO.ConferenzaCompletaDTO;
import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaConferenzaAnteprimaDTO;
import conferenza.DTO.ListaPartecipanteDTO;
import conferenza.DTO.ListaScadenzaDTO;
import conferenza.DTO.PartecipanteDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.RicercaAvanzataDTO;
import conferenza.DTO.RicercaSempliceDTO;
import conferenza.DTO.RicercaUnifyDTO;
import conferenza.DTO.ScadenzaDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.DTO.bean.Errore;
import conferenza.adapder.integrazioni.createconference.model.IntegFrontieraConferenza;
import conferenza.adapder.integrazioni.domus.model.DomusRegistryAdapterTesta;
import conferenza.adapder.integrazioni.domus.repository.DomusRegistryAdapterTestaRepository;
import conferenza.adapder.integrazioni.paleo.repository.IntegPaleoFrontieraConferenzaRepository;
import conferenza.builder.ConferenzaBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.exception.InvalidFieldEx;
import conferenza.exception.NotFoundEx;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.Ente;
import conferenza.model.Evento;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.model.RegistroDocumento;
import conferenza.model.RicercaConferenza;
import conferenza.model.RubricaPartecipanti;
import conferenza.model.TipologiaDocumento;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.AttivitaRepository;
import conferenza.repository.AzioneRepository;
import conferenza.repository.ComuneRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.ConferenzaSpecificationsBuilder;
import conferenza.repository.ContattoSupportoRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EventiCalendarioRepository;
import conferenza.repository.ImpresaRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProfiloRepository;
import conferenza.repository.ProvinciaRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.RicercaConferenzaRepository;
import conferenza.repository.RicercaConferenzaSpecificationsBuilder;
import conferenza.repository.RubricaPartecipantiRepository;
import conferenza.repository.RubricaRichiedentiRepository;
import conferenza.repository.SearchCriteria;
import conferenza.repository.StatoRepository;
import conferenza.repository.TipoProfiloRepository;
import conferenza.repository.TipologiaConferenzaRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.repository.TipologiaPraticaRepository;
import conferenza.repository.UtenteRepository;
import conferenza.util.DbConst;
import conferenza.validator.ConferenceValidator;

@Transactional
@Service
public class ConferenzaService extends _BaseService {

	private static final Logger logger = LoggerFactory.getLogger(ConferenzaService.class.getName());

	@Autowired
	ConferenzaRepository confRepo;

	@Autowired
	ConferenzaBuilder confBuilder;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ParticipantService participantService;

	@Autowired
	StatoRepository statoRepo;

	@Autowired
	EnteRepository enteRepo;

	@Autowired
	UtenteRepository utenteRepo;
	
	@Autowired
	PartecipanteRepository partecipanteRepo;

	@Autowired
	UtenteService utenteService;

	@Autowired
	AccreditamentoRepository accrRepo;

	@Autowired
	PersonaRepository personaRepo;

	@Autowired
	TipoProfiloRepository tipoProfiloRepo;

	@Autowired
	ProvinciaRepository provinciaRepo;

	@Autowired
	ComuneRepository comuneRepo;

	@Autowired
	TipologiaConferenzaRepository tipologiaConfRepo;

	@Autowired
	ProfiloRepository profiloRepo;

	@Autowired
	EventoService eventoService;

	@Autowired
	DocumentoRepository documentoRepo;

	@Autowired
	TipologiaDocumentoRepository tipoDocumentoRepo;

	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;

	@Autowired
	RubricaPartecipantiRepository rubricaPartRepo;

	@Autowired
	RubricaRichiedentiRepository rubricaRichRepo;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	TipologiaPraticaRepository tipPratRepo;

	@Autowired
	AttivitaRepository attivitaRepo;

	@Autowired
	AzioneRepository azioneRepo;

	@Autowired
	ImpresaRepository impresaRepo;

	@Autowired
	EventiCalendarioRepository eventiCalRepo;

	@Autowired
	ConferenceValidator validator;

	@Autowired
	ContattoSupportoRepository supportoRepo;

	@Autowired
	RicercaConferenzaRepository ricercaConfRepo;
	
	@Autowired
	IntegPaleoFrontieraConferenzaRepository integPaleoFrontieraConferenzaRepository;

	@Autowired
	DomusRegistryAdapterTestaRepository domusRegistryAdapterTestaRepository;
	
	public ConferenzaCompletaDTO findByIdVal(int id) {
		return confBuilder.buildConferenzaCompletaDTO(findConferenzaByIdFiltrata(id));
	}

	public Conferenza findConferenzaByIdFiltrata(Integer id) {
		RicercaSempliceDTO ricerca = new RicercaSempliceDTO();
		ricerca.setId(id);
		List<Conferenza> listaConferenze = this.confRepo.findAll(findConferenzaFiltrataOttimizzata(null, ricerca));
		if (listaConferenze.size() == 0)
			throw new NotFoundEx("la conferenza non e' stata trovata");
		else
			return listaConferenze.get(0);
	}

	@Transactional(readOnly = true)
	public ListaConferenzaAnteprimaDTO findByIdSemp(RicercaSempliceDTO ricerca) {
		if (this.confRepo.findByIdConferenza(ricerca.getId()) == null)
			return null;
		else {
			List<Conferenza> listaConferenze = this.confRepo.findAll(findConferenzaFiltrata(null, ricerca));
			if (listaConferenze.size() != 0) {
				ConferenzaAnteprimaDTO conferenza = this.confBuilder
						.buildConferenzaAnteprimaDTO(listaConferenze.get(0));
				ListaConferenzaAnteprimaDTO lista = new ListaConferenzaAnteprimaDTO();
				lista.getList().add(conferenza);
				lista.setTotalNumber(1l);
				return lista;
			} else
				return null;

		}
	}

	public ListaConferenzaAnteprimaDTO findAllAv(RicercaAvanzataDTO ricercaAvDTO) {
		PageRequest page = PageRequest.of(ricercaAvDTO.getPagVisualizzata() - 1, ricercaAvDTO.getNumRecordPag(),
				Direction.fromString(ricercaAvDTO.getDirOrdinamento()), mapColonneConferenza(
						ConferenzaAnteprimaDTO.getColonnaOrdinamento(ricercaAvDTO.getColonnaOrdinamento())));
		Utente utente = this.utenteService.getAuthenticatedUser();
		if (utente != null) {
			// Conferenza conferenza = confBuilder.buildConferenzaFromRicerca(ricercaAvDTO);
			// Example<Conferenza> example = Example.of(conferenza);
			// Iterable<Conferenza> itConf = this.confRepo.findAll(example, page);
			Iterable<Conferenza> itConf = this.confRepo.findAll(findConferenzaFiltrata(ricercaAvDTO, null), page);

			ListaConferenzaAnteprimaDTO listeAnteprime = new ListaConferenzaAnteprimaDTO();
			for (Conferenza conferenzaI : itConf) {
				ConferenzaAnteprimaDTO confAnteprimaDTO = this.confBuilder.buildConferenzaAnteprimaDTO(conferenzaI);
				listeAnteprime.getList().add(confAnteprimaDTO);
			}
			listeAnteprime.setTotalNumber(this.confRepo.count(findConferenzaFiltrata(ricercaAvDTO, null)));
			// filterPartecipante(listeAnteprime);

			return listeAnteprime;
		} else
			return new ListaConferenzaAnteprimaDTO();
	}
	
	public IdentifiableDTO creaConferenza(ConferenzaDTO conferenza) {
		return this.creaConferenza(conferenza, false);
	}
	
	public IdentifiableDTO creaConferenza(ConferenzaDTO conferenza, Boolean skipValidation) {
		List<Errore> errors = validator.validateCreaConferenza(conferenza, messageSource);
		if (errors.size() != 0 && !skipValidation) {
			throw new InvalidFieldEx(errors);
		}
		// Impresa
		conferenza.setFlagAbilitaModificaRichiedente(Boolean.FALSE);
		Conferenza saved = this.confRepo.save(confBuilder.buildConferenza(conferenza, DbConst.STATO_COMPILAZIONE));
		// Lista Partecipanti per tipologia conferenza
		List<RubricaPartecipanti> listaRubricaPartecipanti = this.rubricaPartRepo
				.findByTipologiaConferenzaSpecializzazione(saved.getTipologiaConferenzaSpecializzazione());
		for (RubricaPartecipanti rubricaPart : listaRubricaPartecipanti) {
			PartecipanteDTO partecipanteDTO = this.partecipanteBuilder
					.buildRubricaPartecipanteToPartecipanteDTO(rubricaPart);
			this.participantService.creaPartecipante(partecipanteDTO, saved.getIdConferenza());
		}

		/*
		 * precaricamento TIPOLOGIA-ATTIVITA-AZIONE non può essere fatto nel build
		 * perchè richiamato anche in fase di modifica
		 */
		// impostaValoriPredefiniti(saved);

		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdConferenza());
		return identifiableDTO;
	}

	public IdentifiableDTO modificaConferenza(ConferenzaDTO conferenza, Integer id) {
		if ((conferenza.getId() != null && !conferenza.getId().equals(id)) || conferenza.getId() == null) {
			throw new NotFoundEx("the id doesn't match with the conference");
		}
		Conferenza conferenzaDB = validateFindConference(conferenza.getId());
		List<Errore> errors = validator.validateModificaConferenza(conferenza, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		validator.validateStatoConferenza(conferenzaDB);
		conferenza.setFlagAbilitaModificaRichiedente(null);
		Conferenza saved = this.confRepo.save(confBuilder.buildConferenza(conferenza, null));
		
		//aggiorna il richiedente
		/*
		 * List<PartecipanteDTO> listaPartecipanteDTO = conferenza.getPartecipanti();
		 * List<Partecipante> listaPartecipante = new ArrayList<>(); for
		 * (PartecipanteDTO partecipanteDTO: listaPartecipanteDTO) {
		 * this.participantService.creaPartecipante(partecipanteDTO,
		 * saved.getIdConferenza(), locale); }
		 */
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdConferenza());
		return identifiableDTO;
	}
	
	public String eliminaConferenza(Integer id) {
		Conferenza conferenza = this.confRepo.findById(id).orElse(null);
		conferenza.setDataFine(new Date());
		List<Partecipante> listaPartecipanti = conferenza.getPartecipantes();
		for (Partecipante partecipante : listaPartecipanti) {
			this.participantService.eliminaPartecipante(partecipante.getIdPartecipante());
		}
		List<Evento> listaEventi = conferenza.getEventi();
		for (Evento evento : listaEventi) {
			this.eventoService.deleteEvent(evento.getId());
		}
		this.confRepo.save(conferenza);
		return "ok";
	}

	public Conferenza patchConferenza(ConferenzaDTO conferenzaDTO, Integer id) {
		Conferenza conferenza = validateFindConference(id);
		// validator.validateStatoConferenza(conferenza);
		if (conferenzaDTO.getStep() != null) {
			conferenza.setStep(conferenzaDTO.getStep());
		}
		if (conferenzaDTO.getStato() != null) {
			conferenza.setStato(this.statoRepo.findById(conferenzaDTO.getStato().getKey()).get());
		}
		Conferenza saved = this.confRepo.save(conferenza);

		/*
		 * Evento creazione conferenza
		 */
		int stato = Integer.parseInt(saved.getStato().getCodice());
		if (stato == DbConst.STATO_BOZZA && saved.getPartecipantes().size() != 0) {
			this.eventoService.creaEventoCreazioneConferenza(saved);
		}

		return saved;
	}

	public Evento patchFlagAbilitaModificaRichiedente(ConferenzaDTO conferenceDTO, Conferenza conferenza) {
		Boolean nuovoValore = conferenceDTO.getFlagAbilitaModificaRichiedente();
		/*
		 * se il flag che arriva è diverso da quello del db applico le modifiche
		 */
		if (nuovoValore != null && !nuovoValore.equals(conferenza.getFlagAbilitaModificaRichiedente())) {
			/*
			 * modifica flag
			 */
			conferenza.setFlagAbilitaModificaRichiedente(nuovoValore);
			Conferenza conferenzaSaved = confRepo.save(conferenza);

			/*
			 * creazione evento
			 */
			Boolean isCreatore = utenteService.getAuthenticatedUser().isCreatore();
			String tipoEvento = "";
			if (isCreatore && nuovoValore.equals(Boolean.TRUE)) {
				tipoEvento = Integer.toString(DbConst.TIPOLOGIA_EVENTO_ABILITA_MODIFICA_RICHIEDENTE);
			}
			if (isCreatore && nuovoValore.equals(Boolean.FALSE)) {
				tipoEvento = Integer.toString(DbConst.TIPOLOGIA_EVENTO_REVOCA_MODIFICA_RICHIEDENTE);
			}
			if (!isCreatore && nuovoValore.equals(Boolean.FALSE)) {
				tipoEvento = Integer.toString(DbConst.TIPOLOGIA_EVENTO_TERMINA_MODIFICA_RICHIEDENTE);
			}
			Evento evento = eventoService.creaEventoModificaRichiedente(conferenzaSaved, tipoEvento);
			return evento;
		}
		return null;
	}

	public Conferenza validateFindConference(Integer id) {
		Conferenza conferenzaDB = this.confRepo.findByIdConferenza(id);
		if (conferenzaDB == null) {
			throw new NotFoundEx("the record doesn't exist");
		}
		return conferenzaDB;
	}

	public List<Integer> filterPartecipante() {
		List<Integer> lista = new ArrayList<>();
		UtenteDTO utente = this.utenteService.findUtente();
		Persona persona = this.personaRepo.findByCodiceFiscaleIgnoreCase(utente.getCodiceFiscale()).orElse(null);
		List<Accreditamento> listaAccreditamento = this.accrRepo.findByPersona(persona);
		for (Accreditamento accreditamento : listaAccreditamento) {
			if (accreditamento.getFlagAccreditato()) {
				lista.add(accreditamento.getPartecipante().getConferenza().getIdConferenza());
			}
		}
		return lista;
	}
	
	public List<SearchCriteria> filterStatiAperti(){
		List<SearchCriteria> criteriaStato = new ArrayList<>();
		criteriaStato.add(new SearchCriteria("stato", "=",
				this.statoRepo.findById(new Integer(DbConst.STATO_BOZZA).toString()).orElse(null)));
		criteriaStato.add(new SearchCriteria("stato", "=",
				this.statoRepo.findById(new Integer(DbConst.STATO_COMPILAZIONE).toString()).orElse(null)));
		criteriaStato.add(new SearchCriteria("stato", "=",
				this.statoRepo.findById(new Integer(DbConst.STATO_VALUTAZIONE).toString()).orElse(null)));
		return criteriaStato;
	}

	public List<SearchCriteria> filterStatiApertiUnify(){
		List<SearchCriteria> criteriaStato = new ArrayList<>();
		criteriaStato.add(new SearchCriteria("codiceStato", "=",
				new Integer(DbConst.STATO_BOZZA).toString()));
		criteriaStato.add(new SearchCriteria("codiceStato", "=",
				new Integer(DbConst.STATO_COMPILAZIONE).toString()));
		criteriaStato.add(new SearchCriteria("codiceStato", "=",
				new Integer(DbConst.STATO_VALUTAZIONE).toString()));
		return criteriaStato;
	}
	
	public List<SearchCriteria> filterGruppiUtenti() {
		List<SearchCriteria> criteriaGruppo = new ArrayList<>();
		Utente authUser = utenteService.getAuthenticatedUser();
		if (authUser != null) {
			for (Utente user : authUser.getGruppoUtenti()) {
				criteriaGruppo.add(new SearchCriteria("codiceFiscaleCreatoreConferenza", "=", user.getCodiceFiscale()));
			}
		}
		return criteriaGruppo;
	}

	public List<SearchCriteria> filterSameEnteUtenti() {
		List<SearchCriteria> criteriaGruppo = new ArrayList<>();
		Utente authUser = utenteService.getAuthenticatedUser();
		if (authUser != null) {
			List<Utente> utenti = utenteRepo.getOtherUsersFromEnte(authUser.getProfilo().getAmministrazioneProcedente().getIdEnte());
			for (Utente user : utenti) {
				criteriaGruppo.add(new SearchCriteria("codiceFiscaleCreatoreConferenza", "=", user.getCodiceFiscale()));
			}
		}
		return criteriaGruppo;
	}

	public Specification<Conferenza> findConferenzaFiltrata(RicercaAvanzataDTO ricerca,
			RicercaSempliceDTO ricercaSemplice) {
		Boolean isUnify = Boolean.FALSE;
		List<SearchCriteria> parametriRicerca = fillParametriRicerca(ricercaSemplice, ricerca, null);
		List<SearchCriteria> criteriaPartecipanti = new ArrayList<>();
		List<SearchCriteria> criteriaProfili = fillCriteriaProfili(isUnify);
		
		// xmfSE: user groups: adds read only users
		List<SearchCriteria> criteriaGruppoUtenti = filterGruppiUtenti();
		// xmfSE:  adds all users from the same ente DISABLED IN ORDER TO FIX FE issues
		//List<SearchCriteria> criteriaGruppoUtenti = filterSameEnteUtenti();
		
		ConferenzaSpecificationsBuilder builder = new ConferenzaSpecificationsBuilder();
		/*
		 * visibilità in base agli accreditamenti
		 */
		List<Integer> idLista = filterPartecipante();
		criteriaPartecipanti = builder.buildIdConfCriteria(idLista);

		List<SearchCriteria> criteriaStato = filterStatiAperti();
		return builder.buildConferenzaSpec(parametriRicerca, criteriaPartecipanti, criteriaProfili, 
				criteriaGruppoUtenti, criteriaStato, this.utenteService.isAdminAmministrazioni(), false);
	}

	public Specification<Conferenza> findConferenzaFiltrataOttimizzata(RicercaAvanzataDTO ricerca,
			RicercaSempliceDTO ricercaSemplice) {
		Boolean isUnify = Boolean.FALSE;
		List<SearchCriteria> parametriRicerca = fillParametriRicerca(ricercaSemplice, ricerca, null);
		List<SearchCriteria> criteriaProfili = fillCriteriaProfili(isUnify);
		SearchCriteria criteriaPartecipanti = null;
		
		// xmfSE: user groups: adds read only users
		List<SearchCriteria> criteriaGruppoUtenti = filterGruppiUtenti();
		// xmfSE:  adds all users from the same ente DISABLED IN ORDER TO FIX FE issues
		//List<SearchCriteria> criteriaGruppoUtenti = filterSameEnteUtenti();
		
		ConferenzaSpecificationsBuilder builder = new ConferenzaSpecificationsBuilder();
		/*
		 * visibilità in base agli accreditamenti
		 */
		List<Integer> idLista = filterPartecipante();

		String listaPartecipanti = null;
		
		if(idLista != null && !idLista.isEmpty()) {
			for(Integer item : idLista){
				if(listaPartecipanti != null)
					listaPartecipanti = listaPartecipanti.concat(",").concat(item.toString());
				else
					listaPartecipanti = item.toString();
			}
		}
		
		if(listaPartecipanti != null)
			criteriaPartecipanti = new SearchCriteria("idConferenza", "in", listaPartecipanti);
				
		return builder.buildConferenzaSpecNew(parametriRicerca, criteriaPartecipanti, criteriaProfili, 
				criteriaGruppoUtenti, this.utenteService.isAdminAmministrazioni());
	}
	
	public List<SearchCriteria> fillParametriRicerca(RicercaSempliceDTO ricercaSemplice,
			RicercaAvanzataDTO ricercaAvanzata, RicercaUnifyDTO ricercaUnify) {
		List<SearchCriteria> parametriRicerca = new ArrayList<>();
		if (ricercaSemplice != null) {
			if (ricercaSemplice.getId() != null) {
				parametriRicerca.add(new SearchCriteria("idConferenza", "=", ricercaSemplice.getId()));
			}
		}
		if (ricercaAvanzata != null) {
			if (ricercaAvanzata.getStato() != null && !ricercaAvanzata.getStato().isEmpty()) {
				parametriRicerca.add(new SearchCriteria("stato", "=",
						this.statoRepo.findById(ricercaAvanzata.getStato()).orElse(null)));
			}
			if (ricercaAvanzata.getTipologiaConferenza() != null
					&& !ricercaAvanzata.getTipologiaConferenza().isEmpty()) {
				parametriRicerca.add(new SearchCriteria("tipologiaConferenza", "=",
						this.tipologiaConfRepo.findById(ricercaAvanzata.getTipologiaConferenza()).orElse(null)));
			}
			if (ricercaAvanzata.getRiferimentoIstanza() != null && !ricercaAvanzata.getRiferimentoIstanza().isEmpty()) {
				parametriRicerca
						.add(new SearchCriteria("riferimentoIstanza", "=", ricercaAvanzata.getRiferimentoIstanza()));
			}
			if (ricercaAvanzata.getCfRichiedente() != null && !ricercaAvanzata.getCfRichiedente().isEmpty()) {
				parametriRicerca
						.add(new SearchCriteria("codiceFiscaleRichiedente", "=", ricercaAvanzata.getCfRichiedente()));
			}
			if (ricercaAvanzata.getProvincia() != null && !ricercaAvanzata.getProvincia().isEmpty()) {
				parametriRicerca.add(new SearchCriteria("localizzazioneProvincia", "=",
						this.provinciaRepo.findByCodice(ricercaAvanzata.getProvincia()).orElse(null)));
			}
			if (ricercaAvanzata.getComune() != null && !ricercaAvanzata.getComune().isEmpty()) {
				parametriRicerca.add(new SearchCriteria("localizzazioneComune", "=",
						this.comuneRepo.findById(ricercaAvanzata.getComune()).orElse(null)));
			}
		}
		if (ricercaUnify != null && ricercaUnify.getValue() != null && !ricercaUnify.getValue().isEmpty()) {
			parametriRicerca.add(new SearchCriteria("riferimentoIstanza", "%", ricercaUnify.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneTipologiaConferenza", "%", ricercaUnify.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneStato", "%", ricercaUnify.getValue()));
			parametriRicerca.add(new SearchCriteria("nomeRichiedente", "%", ricercaUnify.getValue()));
			parametriRicerca.add(new SearchCriteria("cognomeRichiedente", "%", ricercaUnify.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneProvincia", "%", ricercaUnify.getValue()));
			parametriRicerca.add(new SearchCriteria("descrizioneComune", "%", ricercaUnify.getValue()));
			try {
				Integer numero = Integer.parseInt(ricercaUnify.getValue());
				parametriRicerca.add(new SearchCriteria("idConferenza", "=", numero));
			} catch (NumberFormatException e) {
				logger.debug("parametro di ricerca non numerico, non viene effettuata la ricerca su idConferenza");
			}
		}
		return parametriRicerca;
	}

	public List<SearchCriteria> fillCriteriaProfili(Boolean isUnify) {
		Utente utente = this.utenteService.getAuthenticatedUser();
		List<SearchCriteria> criteriaProfili = new ArrayList<>();
		if (utente.getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_AMMINISTRAZIONE_PROCEDENTE))) {
			Ente amministrazioneProcedente = this.utenteService.getAmministrazioneProcedente();
			if (!isUnify) {
				criteriaProfili.add(new SearchCriteria("amministrazioneProcedente", "=", amministrazioneProcedente));
			} else {
				criteriaProfili.add(new SearchCriteria("idEnte", "=", amministrazioneProcedente.getIdEnte()));
			}
		}
		if (utente.getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_CREATORE_CDS))) {
			criteriaProfili.add(new SearchCriteria("codiceFiscaleCreatoreConferenza", "=", utente.getCodiceFiscale()));
		}
		if (utente.getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_RESPONSABILE_CONFERENZA))) {
			criteriaProfili
					.add(new SearchCriteria("codiceFiscaleResponsabileConferenza", "=", utente.getCodiceFiscale()));
		}
		return criteriaProfili;
	}

	public IdentifiableDTO clonaConferenza(int idConferenza) {
		/*
		 * recupero di tutte le informazioni per creare la nuova conferenza
		 */
		ConferenzaCompletaDTO conferenzaOriginaleDTO = getDtoForClone(idConferenza);

		/*
		 * impostazioni iniziali per la nuova conferenza
		 */
		inizializzaDTOForClone(conferenzaOriginaleDTO);

		IdentifiableDTO identifiableDTO = creaConferenza(conferenzaOriginaleDTO);

		/*
		 * creazione dei partecipanti perchè non sono stati creati in creaConferenza
		 */
		for (PartecipanteDTO partecipanteDTO : conferenzaOriginaleDTO.getPartecipanti()) {
			participantService.creaPartecipante(partecipanteDTO, identifiableDTO.getId());
		}

		Conferenza conferenzaOriginale = confRepo.findByIdConferenza(idConferenza);
		Conferenza conferenzaClonata = confRepo.findByIdConferenza(identifiableDTO.getId());
		conferenzaClonata = setConferenzaPadre(conferenzaOriginale, conferenzaClonata);

		if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(conferenzaClonata.getTipologiaConferenzaSpecializzazione().getCodice())
				|| (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_AIA).equals(conferenzaClonata.getTipologiaConferenzaSpecializzazione().getCodice())
				|| (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_VIA).equals(conferenzaClonata.getTipologiaConferenzaSpecializzazione().getCodice())) {

			IntegFrontieraConferenza integ = integPaleoFrontieraConferenzaRepository.findByConferenza(conferenzaOriginale);
			logger.debug("inserimento record in integ_frontiera_conferenza per la cds clonata " + conferenzaClonata.getIdConferenza());
			IntegFrontieraConferenza newInteg = new IntegFrontieraConferenza();
			newInteg.setConferenza(conferenzaClonata);
			newInteg.setIdFascicolo(integ.getIdFascicolo());
			newInteg.setIdPratica(integ.getIdPratica());
			integPaleoFrontieraConferenzaRepository.save(newInteg);
			logger.debug("record inserito in integ_frontiera_conferenza per la cds clonata " + conferenzaClonata.getIdConferenza());
		}
		
		if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(conferenzaClonata.getTipologiaConferenzaSpecializzazione().getCodice())) {
			DomusRegistryAdapterTesta domusRegAdOrg = domusRegistryAdapterTestaRepository.findByIdConferenza(idConferenza);
			DomusRegistryAdapterTesta domusRegAdDst = new DomusRegistryAdapterTesta();
			
			domusRegAdDst.setDtIns(new Date());
			domusRegAdDst.setFk_CodiceComune(domusRegAdOrg.getFk_CodiceComune());
			domusRegAdDst.setIdPratica(domusRegAdOrg.getIdPratica());
			domusRegAdDst.setIdConferenza(conferenzaClonata.getIdConferenza());
			
			domusRegistryAdapterTestaRepository.save(domusRegAdDst);
		}
		
		/**
		 * <pre>
		 * clona documentazione 
		 * DOCUMENTO_ACCREDITAMENTO -> DOCUMENTO_ACCREDITAMENTO
		 * DOCUMENTAZIONE_AGGIUNTIVA -> DOCUMENTAZIONE_AGGIUNTIVA 
		 * DOCUMENTO_INDIZIONE -> DOCUMENTAZIONE_AGGIUNTIVA 
		 * INTERAZIONI -> DOCUMENTAZIONE_AGGIUNTIVA
		 * DOCUMENTO_PRE_ISTRUTTORIA -> DOCUMENTAZIONE_AGGIUNTIVA
		 * VERBALE_RIUNIONE -> DOCUMENTAZIONE_AGGIUNTIVA
		 * DETERMINA -> DOCUMENTAZIONE_AGGIUNTIVA
		 * </pre>
		 */
		for (TipologiaDocumento tipologiaDocumento : tipoDocumentoRepo.findAll()) {
			TipologiaDocumento tipologiaClonata = tipologiaDocumento;
			if (!DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_ACCREDITAMENTO.equals(tipologiaDocumento.getCodice())) {
				tipologiaClonata = tipoDocumentoRepo.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA)
						.get();
			}
			clonaDocumenti(conferenzaOriginale, tipologiaDocumento, conferenzaClonata, tipologiaClonata);
		}

		/*
		 * evento di clonazione
		 */
		eventoService.creaEventoClonazioneConferenza(conferenzaOriginale, conferenzaClonata);

		return identifiableDTO;
	}

	private Conferenza setConferenzaPadre(Conferenza conferenzaOriginale, Conferenza conferenzaClonata) {
		conferenzaClonata.setConferenzaPadre(conferenzaOriginale);
		return confRepo.save(conferenzaClonata);
	}

	/**
	 * La clonazione riguarda i documenti di tipo accreditamento
	 * 
	 * @param conferenzaSorgente
	 * @param conferenzaDestinazione
	 */
	private void clonaDocumenti(Conferenza conferenzaSorgente, TipologiaDocumento tipologiaSorgente,
			Conferenza conferenzaDestinazione, TipologiaDocumento tipologiaDestinazione) {
		List<Documento> documentiAccreditamento = documentoRepo.findByTipologiaDocumentoAndConferenza(tipologiaSorgente,
				conferenzaSorgente);
		for (Documento documentoSorgente : documentiAccreditamento) {
			Documento documentoDestinazione = new Documento();
			documentoDestinazione.setCategoriaDocumento(documentoSorgente.getCategoriaDocumento());
			documentoDestinazione.setConferenza(conferenzaDestinazione);
			documentoDestinazione.setDataProtocollo(documentoSorgente.getDataProtocollo());
			documentoDestinazione.setNome(documentoSorgente.getNome());
			documentoDestinazione.setNumeroInventario(documentoSorgente.getNumeroInventario());
			documentoDestinazione.setNumeroProtocollo(documentoSorgente.getNumeroProtocollo());

			/*
			 * si cerca l'accreditamento della conferenza destinazione che corrisponde
			 * all'accreditamento della conferenza sorgente. si presuppone che ad una
			 * persona corrisponde un solo accreditamento nella conferenza (non può essere
			 * invitato con ruoli diversi)
			 */
			List<Accreditamento> listaAccreditamenti = this.accrRepo
					.findByPersona(documentoSorgente.getOwner().getPersona());
			for (Accreditamento accreditamento : listaAccreditamenti) {
				if (accreditamento.getPartecipante().getConferenza().equals(conferenzaDestinazione)) {
					documentoDestinazione.setOwner(accreditamento);
				}
			}

			documentoDestinazione.setTipologiaDocumento(tipologiaDestinazione);

			/*
			 * un partecipante può essere identificato dall'ente per cui cerco nella
			 * conferenza destinazione il partecipante con lo stesso ente del partecipante
			 * sorgente
			 */
			List<Partecipante> visibilitaPartecipanti = new ArrayList<>();
			for (Partecipante partecipanteSorgente : documentoSorgente.getVisibilitaPartecipanti()) {
				for (Partecipante partecipanteDestinazione : conferenzaDestinazione.getPartecipantes()) {
					if (partecipanteDestinazione.getEnte().equals(partecipanteSorgente.getEnte())) {
						visibilitaPartecipanti.add(partecipanteDestinazione);
					}
				}
			}
			documentoDestinazione.setVisibilitaPartecipanti(visibilitaPartecipanti);

			documentoDestinazione.setVisibilitaRistretta(documentoSorgente.getVisibilitaRistretta());
			documentoRepo.save(documentoDestinazione);

			for (RegistroDocumento registroDocumentoSorgente : registroDocumentoRepository
					.findByDocumentoOrderByDataDesc(documentoSorgente)) {
				RegistroDocumento registroDocumentoDestinazione = new RegistroDocumento();
				registroDocumentoDestinazione.setData(registroDocumentoSorgente.getData());
				registroDocumentoDestinazione.setDataEsterno(registroDocumentoSorgente.getDataEsterno());
				registroDocumentoDestinazione.setDocumento(documentoDestinazione);
				registroDocumentoDestinazione.setEnte(registroDocumentoSorgente.getEnte());
				registroDocumentoDestinazione.setRiferimentoEsterno(registroDocumentoSorgente.getRiferimentoEsterno());
				registroDocumentoDestinazione.setTipo(registroDocumentoSorgente.getTipo());
				registroDocumentoDestinazione.setTipoEsterno(registroDocumentoSorgente.getTipoEsterno());
				registroDocumentoRepository.save(registroDocumentoDestinazione);
			}
		}
	}

	private ConferenzaCompletaDTO getDtoForClone(int id) {
		ConferenzaCompletaDTO conferenzaOriginaleDTO = findByIdVal(id);
		conferenzaOriginaleDTO.setClonedFromId(""+id);

		/*
		 * Imposto i partecipanti nel DTO perchè non più restituiti dalla findByIdVal
		 */
		ListaPartecipanteDTO listaPartecipanteDTO = new ListaPartecipanteDTO();
		listaPartecipanteDTO = this.participantService.findPartecipantiByIdConferenza(id);
		conferenzaOriginaleDTO.setPartecipanti(listaPartecipanteDTO.getList());

		for (PartecipanteDTO partecipanteDTO : conferenzaOriginaleDTO.getPartecipanti()) {
			List<PersonaRuoloConferenzaDTO> listaAccreditamentoDTO = participantService
					.findAccreditamentiById(partecipanteDTO.getId()).getList();
			partecipanteDTO.setListaUtente(listaAccreditamentoDTO);
		}
		return conferenzaOriginaleDTO;
	}

	private void inizializzaDTOForClone(ConferenzaCompletaDTO dto) {
		dto.setId(null);
		dto.setStep(3);
		dto.setFlagAbilitaModificaRichiedente(false);
		for (PartecipanteDTO partecipanteDTO : dto.getPartecipanti()) {
			for (PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO : partecipanteDTO.getListaUtente()) {
				personaRuoloConferenzaDTO.setId(null);
			}
			partecipanteDTO.setId(null);
		}
	}

	public ListaScadenzaDTO ricercaScadenze() {
		List<Conferenza> listaConferenza = this.confRepo.findAll(findConferenzaFiltrata(null, null));
		ListaScadenzaDTO listaScadenze = new ListaScadenzaDTO();
		for (Conferenza conferenza : listaConferenza) {
			if (conferenza.getDataTermine() != null) {
				listaScadenze.getList()
						.add(new ScadenzaDTO(conferenza.getIdConferenza(), conferenza.getRiferimentoIstanza(),
								new SimpleDateFormat("dd-MM-yyyy").format(conferenza.getDataTermine()),
								createNotNullLabelValue(this.eventiCalRepo.findById("1").orElse(null))));
			}
			if (conferenza.getTermineEspressionePareri() != null) {
				listaScadenze.getList()
						.add(new ScadenzaDTO(conferenza.getIdConferenza(), conferenza.getRiferimentoIstanza(),
								new SimpleDateFormat("dd-MM-yyyy").format(conferenza.getTermineEspressionePareri()),
								createNotNullLabelValue(this.eventiCalRepo.findById("2").orElse(null))));
			}
			if (conferenza.getTermineRichiestaIntegrazioniConferenza() != null) {
				listaScadenze.getList()
						.add(new ScadenzaDTO(conferenza.getIdConferenza(), conferenza.getRiferimentoIstanza(),
								new SimpleDateFormat("dd-MM-yyyy")
										.format(conferenza.getTermineRichiestaIntegrazioniConferenza()),
								createNotNullLabelValue(this.eventiCalRepo.findById("3").orElse(null))));
			}
			if (conferenza.getPrimaSessioneSimultanea() != null) {
				listaScadenze.getList()
						.add(new ScadenzaDTO(conferenza.getIdConferenza(), conferenza.getRiferimentoIstanza(),
								new SimpleDateFormat("dd-MM-yyyy").format(conferenza.getPrimaSessioneSimultanea()),
								createNotNullLabelValue(this.eventiCalRepo.findById("4").orElse(null))));
			}
		}
		listaScadenze.setTotalNumber(new Long(listaScadenze.getList().size()));
		return listaScadenze;

	}

	public ListaConferenzaAnteprimaDTO findUnifyResearch(RicercaUnifyDTO ricerca) {
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				mapColonneConferenza(ConferenzaAnteprimaDTO.getColonnaOrdinamento(ricerca.getColonnaOrdinamento())));
		Iterable<RicercaConferenza> itConf = this.ricercaConfRepo.findAll(findFiltroRicercaUnify(ricerca), page);

		ListaConferenzaAnteprimaDTO listeAnteprime = new ListaConferenzaAnteprimaDTO();
		for (RicercaConferenza ric : itConf) {
			listeAnteprime.getList().add(this.confBuilder.buildRicercaUnifyToConferenzaAnteprimaDTO(ric));
		}
		listeAnteprime.setTotalNumber(this.ricercaConfRepo.count(findFiltroRicercaUnify(ricerca)));

		return listeAnteprime;
	}

	public Specification<RicercaConferenza> findFiltroRicercaUnify(RicercaUnifyDTO ricerca) {
		Boolean isUnify = Boolean.TRUE;
		Boolean allState = isAllState(ricerca);
		
		List<SearchCriteria> criteriaPartecipanti = new ArrayList<>();
		List<SearchCriteria> parametriRicerca = fillParametriRicerca(null, null, ricerca);
		List<SearchCriteria> criteriaProfili = fillCriteriaProfili(isUnify);
		List<SearchCriteria> criteriaGruppoUtenti = filterGruppiUtenti();
		List<SearchCriteria> criteriaStato = filterCriteriaStato(ricerca);
		RicercaConferenzaSpecificationsBuilder builder = new RicercaConferenzaSpecificationsBuilder();
		List<Integer> idLista = filterPartecipante();
		criteriaPartecipanti = builder.buildIdConfCriteria(idLista);

		return builder.buildAll(this.utenteService.isAdminAmministrazioni()).or(builder.buildOr(criteriaProfili))
				.or(builder.buildOr(criteriaGruppoUtenti))
				.or(builder.buildOr(criteriaPartecipanti)).and(builder.buildAll(parametriRicerca.isEmpty()).or(builder.buildOr(parametriRicerca)))
				.and(builder.buildAll(allState).or(builder.buildOr(criteriaStato)));
		
	}

	public ConferenzaAutorizzazioniDTO getAutorizzazioniConferenza(Integer idConferenza) {
		Conferenza conferenza = findConferenzaByIdFiltrata(idConferenza);
		ConferenzaAutorizzazioniDTO conferenzaAutorizzazioniDTO = new ConferenzaAutorizzazioniDTO();
		conferenzaAutorizzazioniDTO
				.setModificaRichiedente(confBuilder.buildConferenzaModificaRichiedenteDTO(conferenza));
		return conferenzaAutorizzazioniDTO;
	}

	private List<SearchCriteria> filterCriteriaStato(RicercaUnifyDTO ricerca){
		List<SearchCriteria> criteriaStato = null;
		if(ricerca!= null && ricerca.getStato()!= null && ricerca.getStato()!="") {
			if(ricerca.getStato().equals(new Integer(DbConst.STATO_ARCHIVIATA).toString()) || ricerca.getStato().equals(new Integer(DbConst.STATO_CHIUSA).toString())) {
				criteriaStato = new ArrayList<>();
				criteriaStato.add(new SearchCriteria("codiceStato", "=",
						ricerca.getStato()));
			}else {
				criteriaStato = filterStatiApertiUnify();
			}
		}
		return criteriaStato;
	}
	private Boolean isAllState(RicercaUnifyDTO ricerca) {
		Boolean isAllState = Boolean.TRUE;
		if(ricerca!= null && ricerca.getStato()!= null && ricerca.getStato()!="")
			isAllState = Boolean.FALSE;
		return isAllState;
	}
}
