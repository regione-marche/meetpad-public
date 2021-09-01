package cdst_be_marche.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdst_be_marche.DTO.EnteDTO;
import cdst_be_marche.DTO.PersonaRuoloConferenzaDTO;
import cdst_be_marche.DTO.RispostaLabelValueDTO;
import cdst_be_marche.DTO.UtenteDTO;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.builder.PartecipanteBuilder;
import cdst_be_marche.builder.UtenteBuilder;
import cdst_be_marche.exception.NotAuthorizedUser;
import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Ente;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.TipoProfilo;
import cdst_be_marche.model.Utente;
import cdst_be_marche.properties.AutenticazioneProperties;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.EnteRepository;
import cdst_be_marche.repository.RuoloPartecipanteRepository;
import cdst_be_marche.repository.UtenteRepository;
import cdst_be_marche.util.DbConst;

@Service
public class UtenteService extends _BaseService {

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

	private @Autowired HttpServletRequest request;

	public UtenteDTO findUtente() {
		Utente utente = getAuthenticatedUser();
		if (utente != null)
			return utenteBuilder.buildUtenteDTO(getAuthenticatedUser());
		else
			throw new NotAuthorizedUser("Utente non dispone di autorizzazioni per l'applicazione");
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
		Accreditamento accreditamento = getAdminAccreditamento(conferenza);
		return this.partecipanteBuilder.buildPersonaRuoloDTO(accreditamento);
	}

	/**
	 * restituisce l'amministrazione procedente in base all'utente collegato
	 * 
	 * @return
	 */
	public EnteDTO findAmministrazioneProcedente() {
		Ente amministrazioneProcedente = getAmministrazioneProcedente();
		EnteDTO enteDTO = this.partecipanteBuilder.enteToEnteDTO(amministrazioneProcedente);
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
	public Accreditamento getAccreditamento(Conferenza conferenza) {
		Utente utente = getAuthenticatedUser();
		if (utente != null) {
			String codiceFiscale = utente.isCreatore() ? conferenza.getCodiceFiscaleResponsabileConferenza()
					: utente.getCodiceFiscale();
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
		for (Partecipante partecipante : conferenza.getPartecipantes()) {
			for (Accreditamento accreditamento : partecipante.getAccreditati()) {
				if (accreditamento.getPersona().getCodiceFiscale().equals(codiceFiscale)) {
					return accreditamento;
				}
			}
		}
		return null;
	}

	public Accreditamento getAdminAccreditamento(Conferenza conferenza) {
		Utente utente = getAuthenticatedUser();
		if (utente != null) {
			String codiceFiscale = utente.getCodiceFiscale();
			return getAccreditamento(conferenza, codiceFiscale);
		}
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
		Partecipante partecipante = getAccreditamento(this.conferenzaRepo.findByIdConferenza(idConference))
				.getPartecipante();
		RispostaLabelValueDTO risposta = new RispostaLabelValueDTO();
		risposta.setData(
				new LabelValue(Integer.toString(partecipante.getIdPartecipante()), partecipante.getDescrizione()));
		return risposta;
	}
	
	public Boolean isAdminAmministrazioni() {
		return getAuthenticatedUser().getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_AMMINISTRAZIONI));
	}
	
	public List<Partecipante> findAllPartecipantiPerUtente(Conferenza conferenza) {
		List<Partecipante> listaPartecipanti = new ArrayList<>();
		List<Conferenza> listaConferenza = new ArrayList<>();
		if (conferenza == null) {
			listaConferenza = this.conferenzaRepo.findAll();
		} else {
			listaConferenza.add(conferenza);
		}
		for (Conferenza conf: listaConferenza) {
			for (Partecipante part: conf.getPartecipantes()) {
				if (part.getAccreditati().stream().anyMatch(
						acc -> acc.getPersona().getCodiceFiscale().equals(getAuthenticatedUser().getCodiceFiscale()))) {
					listaPartecipanti.add(part);
				}
			}
		}
		return listaPartecipanti;
	}

}
