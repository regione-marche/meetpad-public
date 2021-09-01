package conferenza.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import conferenza.DTO.EnteDTO;
import conferenza.DTO.ListaAccreditamentoDTO;
import conferenza.DTO.ListaUtenteDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.RispostaBoolean;
import conferenza.DTO.RispostaLabelValueDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.builder.EnteBuilder;
import conferenza.builder.PartecipanteBuilder;
import conferenza.builder.UtenteBuilder;
import conferenza.exception.NotAuthorizedUser;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Ente;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.model.RegistroAccesso;
import conferenza.model.RubricaDelegati;
import conferenza.model.Utente;
import conferenza.properties.AutenticazioneProperties;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProfiloRepository;
import conferenza.repository.RegistroAccessoRepository;
import conferenza.repository.RubricaDelegatiRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.repository.TipoProfiloRepository;
import conferenza.repository.UtenteRepository;
import conferenza.util.DbConst;

@Service
public class UtenteService extends _BaseService {
	private static final Logger logger = LoggerFactory.getLogger(ConferenzaService.class.getName());

	@Autowired
	UtenteRepository utenteRepo;

	@Autowired
	EnteRepository enteRepo;

	@Autowired
	RuoloPartecipanteRepository ruoloPartecipanteRepo;

	@Autowired
	UtenteBuilder utenteBuilder;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	JWTsService jWTsService;

	@Autowired
	AutenticazioneProperties autenticazioneProperties;

	@Autowired
	ConferenzaRepository conferenzaRepo;
	
	@Autowired
	RegistroAccessoRepository registroAccessoRepo;
		
	@Autowired
	EnteBuilder enteBuilder;
	
	@Autowired
	ProfiloRepository profiloRepo;
	
	@Autowired
	TipoProfiloRepository tipoProfiloRepo;
	
	@Autowired
	PartecipanteBuilder partBuilder;
	
	@Autowired
	PersonaRepository personaRepo;
	
	@Autowired
	AccreditamentoRepository accreditamentoRepo;
	
	@Autowired
	RubricaDelegatiRepository rubricaDelRepo;
	
	private @Autowired HttpServletRequest request;

	public UtenteDTO findUtente() {
		Utente utente = getAuthenticatedUser();
		if (utente != null)
			return utenteBuilder.buildUtenteDTO(getAuthenticatedUser());
		else
			throw new NotAuthorizedUser("Utente non dispone di autorizzazioni per l'applicazione");
	}
	
	public UtenteDTO findUtenteAccesso() {
		String cf = jWTsService.getCodiceFiscaleFromToken(request);
		RegistroAccesso registroAccesso = new RegistroAccesso();
		RegistroAccesso saved = new RegistroAccesso();
		if (cf != null) {
			registroAccesso.setCodiceFiscale(cf);
			registroAccesso.setDataAccesso(new Date());
			registroAccesso.setFlagUtenteEsistente(Boolean.FALSE);
			saved = registroAccessoRepo.save(registroAccesso);
		}
		Utente utente = getAuthenticatedUser();
		if (utente != null) {
			saved.setDescrizioneTipoProfilo(utente.getProfilo().getTipoProfilo().getDescrizione());
			saved.setFlagUtenteEsistente(Boolean.TRUE);
			registroAccessoRepo.save(saved);
			return utenteBuilder.buildUtenteDTO(getAuthenticatedUser());
		}
		else
			throw new NotAuthorizedUser("Utente non dispone di autorizzazioni per l'applicazione");
	}
	
	public RispostaBoolean isUtenteCensito() {
		RispostaBoolean risposta = new RispostaBoolean();
		risposta.setData(getAuthenticatedUser() != null);
		return risposta;
	}

	//xmfSE:  Le Cds USR devono poter essere visionate e modificate da tutti gli utenti dell’USR abilitati (e così per tutte le tipologie) per necessità di intervenire sulle cds “a più mani”
	// the role is overrided with the conference responsible one to give the same permissions as the responsible
	public Utente getAuthenticatedUserAsResponsibleOfConference(Conferenza conferenza) {
		Utente utente = getAuthenticatedUser();
		
		//xmfSE: enable this when the multiple function is to be enbled in PROD
		if(utente != null && conferenza != null) {
			for (Utente responsible : utente.getGruppoUtenti()) {
				if(conferenza.getCodiceFiscaleResponsabileConferenza().equalsIgnoreCase(responsible.getCodiceFiscale())) {
					return responsible;
				}
			}
		}
		
		return utente;
	}

	
	// TODO: implementare la verifica dell'autenticazione
	public Utente getAuthenticatedUser() {
		String cf = jWTsService.getCodiceFiscaleFromToken(request);
		if (cf != null) {
			return utenteRepo.findByCodiceFiscaleIgnoreCase(cf).orElse(null);
			
		} else if (autenticazioneProperties.getUtenteFittizio()) {
			return utenteRepo.findByCodiceFiscaleIgnoreCase(autenticazioneProperties.getCfUtenteFittizio()).get();
		}
		return null;
	}

	public String getCodiceFiscaleUtente() {
		String cf = jWTsService.getCodiceFiscaleFromToken(request);
		if (cf != null) {
			return cf;
		} else if (autenticazioneProperties.getUtenteFittizio()) {
			return autenticazioneProperties.getCfUtenteFittizio();
		}
		return null;
	}

	public PersonaRuoloConferenzaDTO findPersonaRuoloConferenza(Integer idConference) {
		Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(idConference);
		Accreditamento accreditamento = getAccreditamento(conferenza, false);
		return this.partecipanteBuilder.buildPersonaRuoloDTO(accreditamento);
	}

	/**
	 * restituisce l'amministrazione procedente in base all'utente collegato
	 * 
	 * @return
	 */
	public EnteDTO findAmministrazioneProcedente() {
		Ente amministrazioneProcedente = getAmministrazioneProcedente();
		EnteDTO enteDTO = this.enteBuilder.enteToEnteDTO(amministrazioneProcedente);
		enteDTO.setRuolo(createNotNullLabelValue(this.ruoloPartecipanteRepo
				.findByCodice(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)).get()));
		return enteDTO;
	}

	public Ente getAmministrazioneProcedente() {
		return getAuthenticatedUser().getProfilo().getAmministrazioneProcedente();
	}

	/**
	 * recupero dell'accreditamento nella conferenza relativo all'utente autenticato
	 * 
	 * @param conferenza
	 * @return null se non esiste accreditamento nella conferenza per l'utente
	 *         collegato
	 */
	public Accreditamento getAccreditamento(Conferenza conferenza, boolean creatoreAsResponsabile) {
		Utente utente = getAuthenticatedUserAsResponsibleOfConference(conferenza);
		if (utente != null) {
			String codiceFiscale = creatoreAsResponsabile && isCreatoreConferenza(conferenza) ? conferenza.getCodiceFiscaleResponsabileConferenza()
					: utente.getCodiceFiscale();
			Accreditamento accreditamento  = getAccreditamento(conferenza, codiceFiscale);
			
//			if (accreditamento == null) {
//System.out.println(conferenza.getIdConferenza());
//				Utente authUser = getAuthenticatedUser();
//				if (authUser != null) {
//					for (Utente responsible : authUser.getGruppoUtenti()) {
//						if(conferenza.getCodiceFiscaleResponsabileConferenza().equalsIgnoreCase(responsible.getCodiceFiscale())) {
//							//xmfSE:  Le Cds USR devono poter essere visionate e modificate da tutti gli utenti dell’USR abilitati (e così per tutte le tipologie) per necessità di intervenire sulle cds “a più mani”
//							// the role is overrided with the conference responsible one to give the same permissions as the responsible
//							accreditamento = getAccreditamento(conferenza, conferenza.getCodiceFiscaleResponsabileConferenza());
//						}
//					}
//				}
//			}
			
			return accreditamento;	
		}
		return null;
	}
	
	// xmf: changed to support scheduler send mail job!
	public Accreditamento getAccreditamentoFromCreator(Conferenza conferenza) {
		String codiceFiscale = conferenza.getCodiceFiscaleCreatoreConferenza();
		Utente utente = utenteRepo.findByCodiceFiscaleIgnoreCase(codiceFiscale).get();
		
		if (utente != null) {
			return getAccreditamento(conferenza, codiceFiscale);
		}
		return null;
	}
	
	/**
	 * recupero dell'accreditamento nella conferenza relativo all'utente corrispondente al codiceFiscale
	 * 
	 * @param conferenza
	 * @return null se non esiste accreditamento nella conferenza per l'utente
	 *         corrispondente al codiceFiscale
	 */
	public Accreditamento getAccreditamento(Conferenza conferenza, String codiceFiscale) {
		Partecipante ammProc = null;
		for (Partecipante partecipante : conferenza.getPartecipantes()) {
			
			//xmfSE: in case it's not found, then try to see if the logged user is 
			if(Integer.parseInt(partecipante.getRuoloPartecipante().getCodice()) == DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE)
				ammProc = partecipante;
			
			for (Accreditamento accreditamento : partecipante.getAccreditati()) {
				if (accreditamento.getPersona().getCodiceFiscale().equals(codiceFiscale)) {
					return accreditamento;
				}
			}
		}
		
		//xmfSE: in case it's not found, then try to see if the logged user is 
		//Le Cds USR devono poter essere visionate e modificate da tutti gli utenti dell’USR abilitati (e così per tutte le tipologie) per necessità di intervenire sulle cds “a più mani”
		if(ammProc != null)
			try {
				Utente authUser = getAuthenticatedUserAsResponsibleOfConference(conferenza);
				
				if(authUser.getProfilo().getAmministrazioneProcedente().getIdEnte() == ammProc.getEnte().getIdEnte()) {
					ammProc.getAccreditati();
				}
			} catch (Throwable skip) { }
		
		return null;
	}

	public String getCodiceFiscaleResponsabileConferenza(Conferenza conferenza) {
		for (Partecipante partecipante : conferenza.getPartecipantes()) {
			for (Accreditamento accreditamento : partecipante.getAccreditati()) {
				if (accreditamento.isResponsabileConferenza()) {
					return accreditamento.getPersona().getCodiceFiscale();
				}
			}
		}
		return null;
	}

	public RispostaLabelValueDTO findPartecipanteUtenteLogged(Integer idConference) {
		RispostaLabelValueDTO risposta = new RispostaLabelValueDTO();
		Accreditamento accreditamento = getAccreditamento(this.conferenzaRepo.findByIdConferenza(idConference), true);
		if(accreditamento != null) {
			Partecipante partecipante =	accreditamento.getPartecipante();
			risposta.setData(new LabelValue(Integer.toString(partecipante.getIdPartecipante()), partecipante.getDescrizione()));
		}			
		return risposta;
	}
	
	public List<Partecipante> findAllPartecipantiPerUtente(Conferenza conferenza) {
		List<Partecipante> listaPartecipanti = new ArrayList<>();
		List<Conferenza> listaConferenza = new ArrayList<>();
		if (conferenza == null) {
			listaConferenza = this.conferenzaRepo.findAll();
		} else {
			listaConferenza.add(conferenza);
		}
		
		try {
			for (Conferenza conf: listaConferenza) {
				for (Partecipante part: conf.getPartecipantes()) {
					if(part.getAccreditati() != null) {
						if (part.getAccreditati().stream().anyMatch(
								acc -> acc.getPersona().getCodiceFiscale().equals(getAuthenticatedUserAsResponsibleOfConference(conferenza).getCodiceFiscale()))) {
							listaPartecipanti.add(part);
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaPartecipanti;
	}
	
	public Boolean isAdminAmministrazioni() {
		return getAuthenticatedUser().getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_AMMINISTRAZIONI));
	}

	public Boolean isAdminAmministrazioneProcedente() {
		return getAuthenticatedUser().getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_AMMINISTRAZIONE_PROCEDENTE));
	}
	
	public Boolean isAdminSistema() {
		return getAuthenticatedUser().getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_SISTEMA));
	}
	
	public boolean isCreatoreConferenza(Conferenza conferenza) {
		Utente utente = getAuthenticatedUserAsResponsibleOfConference(conferenza);
		if (utente != null) {
			return getCodiceFiscaleUtente().equals(conferenza.getCodiceFiscaleCreatoreConferenza());
		}
		return false;
	}
	
	public boolean isResponsabileConferenza(Conferenza conferenza) {
		Utente utente = getAuthenticatedUserAsResponsibleOfConference(conferenza);
		if (utente != null) {
			return getCodiceFiscaleUtente().equals(conferenza.getCodiceFiscaleResponsabileConferenza());
		}
		return false;
	}
		
	/**
	 * Ritorna elenco degli eventuali accreditamenti pendenti (con flagAccreditamento = false) dell'utente collegato
	 * @return
	 */
	public ListaAccreditamentoDTO findAccreditamentiPendenti() {
		String cf = getCodiceFiscaleUtente();
		ListaAccreditamentoDTO lista = new ListaAccreditamentoDTO();
		
		Persona persona = this.personaRepo.findByCodiceFiscaleIgnoreCase(cf).orElse(null);
		if (persona != null) {
			List<Accreditamento> listaAccreditamento = accreditamentoRepo.findByPersona(persona);
			for (Accreditamento accreditamento : listaAccreditamento) {
				if (!accreditamento.getFlagAccreditato()) {
					lista.getList().add(this.partecipanteBuilder.buildAccrToAccrDTO(accreditamento));
				}
			}
		}		
		lista.setTotalNumber(new Long(lista.getList().size()));

		return lista;
	}
	
	/*
	 * Restituisce l'utente sulla base dell'id
	 */
	public Utente findById(Integer idUtente){
		Optional<Utente> utenteOpt = utenteRepo.findById(idUtente);
		return utenteOpt.get();
	}

	/*
	 * Restituisce l'utente sulla base del codice fiscale
	*/
	public Utente findByCodiceFiscale(String codiceFiscale){
		Optional<Utente> utenteOpt = utenteRepo.findByCodiceFiscaleIgnoreCase(codiceFiscale);
		return utenteOpt.get();
	}
	
	public ListaUtenteDTO getAllFirmatariConferenza(Integer idConferenza,  boolean flagFirmatario){
		ListaUtenteDTO listaDTO = new ListaUtenteDTO();
		List<Utente> listaUtentiFirmatari = utenteRepo.getAllFirmatariConferenza(idConferenza, flagFirmatario);
		for(Utente utente: listaUtentiFirmatari) {
			listaDTO.getList().add(utenteBuilder.buildUtenteDTO(utente));
		}
		listaDTO.setTotalNumber(new Long(listaDTO.getList().size()));
		return listaDTO;
	}
	
	public RispostaBoolean isPartecipanteAbilitaFirmaMultipla(Integer idConference) {
		boolean isAmministratoreProcedente = false;
		Accreditamento accreditamento = getAccreditamento(this.conferenzaRepo.findByIdConferenza(idConference), true);
		if(accreditamento != null) {
			Partecipante partecipante =	accreditamento.getPartecipante();
			isAmministratoreProcedente = partecipante.isAmministrazioneProcendete();
		}	
		
		RispostaBoolean risposta = new RispostaBoolean();
		risposta.setData(isAmministratoreProcedente);
		return risposta;
	}
	
	public RispostaBoolean isUtenteDelegatoConferenza(Integer idConference) {
		boolean isUtenteDelegato = false;		
		UtenteDTO utente = findUtente();
		Persona persona = this.personaRepo.findByCodiceFiscaleIgnoreCase(utente.getCodiceFiscale()).orElse(null);
		Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(idConference);
		logger.debug("tipo_conferenza : " + conferenza.getTipologiaConferenzaSpecializzazione().getCodice());
		if(persona != null) {
			RubricaDelegati rubricaDelegati = rubricaDelRepo.findByPersonaAndTipologiaConferenzaSpecializzazione(persona, conferenza.getTipologiaConferenzaSpecializzazione())
					.orElse(null);
			if (rubricaDelegati != null) {
				isUtenteDelegato = true;
			}
		}
		RispostaBoolean risposta = new RispostaBoolean();
		risposta.setData(isUtenteDelegato);
		return risposta;
	}
}
