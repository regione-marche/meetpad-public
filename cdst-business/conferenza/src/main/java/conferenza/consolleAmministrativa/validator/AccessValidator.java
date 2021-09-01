package conferenza.consolleAmministrativa.validator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EnteDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.DTO.bean.Errore;
import conferenza.consolleAmministrativa.DTO.PreaccreditatoDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoImpresaCompletaDTO;
import conferenza.consolleAmministrativa.DTO.PrecaricamentoPartecipanteDTO;
import conferenza.exception.InvalidFieldEx;
import conferenza.exception.NotAuthorizedUser;
import conferenza.exception.NotEditableException;
import conferenza.exception.NotFoundEx;
import conferenza.model.Conferenza;
import conferenza.model.Ente;
import conferenza.model.Impresa;
import conferenza.model.Persona;
import conferenza.model.ResponsabileConferenza;
import conferenza.model.RubricaDelegati;
import conferenza.model.RubricaImprese;
import conferenza.model.RubricaPartecipanti;
import conferenza.model.RubricaPreaccreditati;
import conferenza.model.RubricaRichiedenti;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.model.TipologiaDocumento;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.ImpresaRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProfiloRepository;
import conferenza.repository.ResponsabileConferenzaRepository;
import conferenza.repository.RubricaDelegatiRepository;
import conferenza.repository.RubricaImpreseRepository;
import conferenza.repository.RubricaPartecipantiRepository;
import conferenza.repository.RubricaPreaccreditatiRepository;
import conferenza.repository.RubricaRichiedentiRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.repository.UtenteRepository;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;

@Component
public class AccessValidator {
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	ResponsabileConferenzaRepository respConfRepo;
	
	@Autowired
	ConferenzaRepository confRepo;
	
	@Autowired
	UtenteRepository utenteRepo;
	
	@Autowired
	AccreditamentoRepository accreditamentoRepo;
	
	@Autowired
	PersonaRepository personaRepo;
	
	@Autowired
	RubricaRichiedentiRepository rubricaRichRepo;
	
	@Autowired
	RubricaPartecipantiRepository rubricaPartRepo;
	
	@Autowired
	RubricaImpreseRepository rubricaImprRepo;
	
	@Autowired
	RubricaDelegatiRepository rubricaDelRepo;
	
	@Autowired
	RubricaPreaccreditatiRepository rubricaPreaccrRepo;
	
	@Autowired
	ImpresaRepository impresaRepo;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ProfiloRepository profiloRepo;
	
	@Autowired
	EnteRepository enteRepo;
	
	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipoConfSpecRepo;
	
	@Autowired
	TipologiaDocumentoRepository tipoDocRepo;
	
	/**
	 * Controllo se l'utente può accedere alla consolle
	 */
	public void consolleAdminAccesso() {
		if(!utenteService.isAdminSistema()) {
			throw new NotAuthorizedUser("L'utente non e' autorizzato");
		}
	}
	
	public void validateParameter(String param) {
		if (param == null || param.isEmpty()) {
			throw new InvalidFieldEx("value can not be null");
		}		
	}

	public Utente validateAccreditamentoUtente(Integer id) {
		Utente utente = utenteRepo.findById(id).orElse(null);
		if(utente == null) {
			throw new NotFoundEx("utente not found");
		}
		Persona persona = personaRepo.findByCodiceFiscaleIgnoreCase(utente.getCodiceFiscale()).orElse(null);
		if(persona != null && accreditamentoRepo.findByPersona(persona).size() != 0) {
			throw new NotEditableException("l'utente ha presenziato a delle conferenze");
		}
		return utente;
	}
	
	public RubricaRichiedenti validateRubricaRichiedenti(Integer id) {
		RubricaRichiedenti rubricaRichiedenti = rubricaRichRepo.findById(id).orElse(null);
		if (rubricaRichiedenti == null) {
			throw new NotFoundEx("preemptive applicant not found");
		}
		return rubricaRichiedenti;
	}
	
	public RubricaPartecipanti validateRubricaPartecipanti(Integer id) {
		RubricaPartecipanti rubricaPartecipanti = rubricaPartRepo.findById(id).orElse(null);
		if (rubricaPartecipanti == null) {
			throw new NotFoundEx("preemptive participant not found");
		}
		return rubricaPartecipanti;
	}

	public void validateEsistenzaRubricaRichiedenti(TipologiaConferenzaSpecializzazione tipoConf, Persona persona) {
		RubricaRichiedenti rubricaRich = null;
		esistenzaTipologiaConferenzaSpec(tipoConf.getCodice());
		if (tipoConf != null && persona != null) {
			rubricaRich = rubricaRichRepo
					.findByPersonaAndTipologiaConferenzaSpecializzazione(persona, tipoConf).orElse(null);
		}
		if (rubricaRich != null) {
			throw new NotEditableException("richiedente da precaricare gia' inserito");
		}

	}

	public void validateRubricaImpreseTipoConf(TipologiaConferenzaSpecializzazione tipoConf, TipologiaConferenzaSpecializzazione tipologiaConferenza) {
		if (!tipoConf.equals(tipologiaConferenza)) {
			throw new NotEditableException("impresa collegata ad un'altra tipologia di conferenza");
		}
	}

	public void validateRubricaPartecipantiEsistenza(RubricaPartecipanti rubricaPart) {
		if (rubricaPartRepo.findByTipologiaConferenzaSpecializzazioneAndEnte(rubricaPart.getTipologiaConferenzaSpecializzazione(), rubricaPart.getEnte()).isPresent()) {
			throw new NotEditableException("partecipante da precaricare gia' inserito");
		}
	}

	public RubricaImprese validateRubricaImprese(Integer id) {
		RubricaImprese rubricaImprese = rubricaImprRepo.findById(id).orElse(null);
		if (rubricaImprese == null) {
			throw new NotFoundEx("preemptive company not found");
		}
		return rubricaImprese;
	}

	public Impresa validateDeleteImpresa(Integer id) {
		Impresa impresa = validateImpresa(id);
		if (confRepo.findByImpresa(impresa).size() != 0) {
			throw new NotEditableException("impresa collegata ad una conferenza");
		}
		return impresa;
	}

	public Impresa validateImpresa(Integer id) {
		Impresa impresa = impresaRepo.findById(id).orElse(null);
		if (impresa == null) {
			throw new NotFoundEx("company not found");
		}
		return impresa;
	}

	public RubricaDelegati validateRubricaDelegati(Integer id) {
		RubricaDelegati rubricaDelegati = rubricaDelRepo.findById(id).orElse(null);
		if (rubricaDelegati == null) {
			throw new NotFoundEx("preemptive delegate not found");
		}
		return rubricaDelegati;
	}
	
	public void validateEsistenzaRubricaDelegati(TipologiaConferenzaSpecializzazione tipoConf, Persona persona) {
		RubricaDelegati rubricaDelegati = null;
		esistenzaTipologiaConferenzaSpec(tipoConf.getCodice());
		if (tipoConf != null && persona != null) {
			rubricaDelegati = rubricaDelRepo
					.findByPersonaAndTipologiaConferenzaSpecializzazione(persona, tipoConf).orElse(null);
		}
		if (rubricaDelegati != null) {
			throw new NotEditableException("delegato da precaricare gia' inserito");
		}
	}
	
	public void validateDuplicatoRubricaDel(RubricaDelegati rubricaDel) {
		RubricaDelegati rubricaDelDb = rubricaDelRepo.findByPersonaAndTipologiaConferenzaSpecializzazione(rubricaDel.getPersona(), rubricaDel.getTipologiaConferenzaSpecializzazione()).orElse(null);
		if (rubricaDelDb != null) {
			throw new NotEditableException("Esiste gia' un richiedente con queste caratteristiche");
		}
	}
	
	public Utente validateCambioProfiloUtente(Integer id) {
		Utente utente = validateAccreditamentoUtente(id);
		Boolean isCreatore = (confRepo.findByCodiceFiscaleCreatoreConferenza(utente.getCodiceFiscale()).size() != 0);
		Boolean isResponsabile = (confRepo.findByCodiceFiscaleResponsabileConferenza(utente.getCodiceFiscale())
				.size() != 0);
		if (isCreatore) {
			throw new NotEditableException("l'utente ha creato una conferenza");
		}
		if (isResponsabile) {
			throw new NotEditableException("l'utente e' il responsabile di una conferenza");
		}
		return utente;
	}

	public void validateDuplicatoRubricaRich(RubricaRichiedenti rubricaRich) {
		RubricaRichiedenti rubricaRichDb = rubricaRichRepo.findByPersonaAndTipologiaConferenzaSpecializzazione(rubricaRich.getPersona(), rubricaRich.getTipologiaConferenzaSpecializzazione()).orElse(null);
		if (rubricaRichDb != null) {
			throw new NotEditableException("Esiste gia' un richiedente con queste caratteristiche");
		}
	}

	public void datiObbligatoriUtenteDTO(UtenteDTO utenteDTO) {
		List<Errore> errors = new ArrayList<>();
		if (utenteDTO.getNome() == null || utenteDTO.getNome().isEmpty()) {
			String msg = messageSource.getMessage("nome.utente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("user.name", msg));
		}
		if (utenteDTO.getCognome() == null || utenteDTO.getCognome().isEmpty()) {
			String msg = messageSource.getMessage("cognome.utente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("user.lastname", msg));
		}
		if (utenteDTO.getCodiceFiscale() == null || utenteDTO.getCodiceFiscale().isEmpty()) {
			String msg = messageSource.getMessage("codiceFiscale.utente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("user.fiscalCode", msg));
		}
		if (utenteDTO.getEmail() == null || utenteDTO.getEmail().isEmpty()) {
			String msg = messageSource.getMessage("email.utente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("user.email", msg));
		}
		if (utenteDTO.getProfilo() == null || utenteDTO.getProfilo().getKey() == null || utenteDTO.getProfilo().getKey().isEmpty()) {
			String msg = messageSource.getMessage("profilo.utente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("user.profile", msg));
		}
		if (!profiloRepo.findById(Integer.parseInt(utenteDTO.getProfilo().getKey())).isPresent()) {
			String msg = messageSource.getMessage("profilo.utente.notFound", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("user.profile", msg));
		}
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
	}

	public void datiObbligatoriPersonaDTO(PersonaDTO personaDTO) {
		List<Errore> errors = new ArrayList<>();
		if (personaDTO.getNome() == null || personaDTO.getNome().isEmpty()) {
			String msg = messageSource.getMessage("nome.persona.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("person.name", msg));
		}
		if (personaDTO.getCognome() == null || personaDTO.getCognome().isEmpty()) {
			String msg = messageSource.getMessage("cognome.persona.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("person.lastname", msg));
		}
		if (personaDTO.getCodiceFiscale() == null || personaDTO.getCodiceFiscale().isEmpty()) {
			String msg = messageSource.getMessage("codiceFiscale.persona.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("person.fiscalCode", msg));
		}
		if (personaDTO.getEmail() == null || personaDTO.getEmail().isEmpty()) {
			String msg = messageSource.getMessage("email.persona.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("person.email", msg));
		}
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
	}

	public void esistenzaEnte(Integer id) {
		Ente ente = enteRepo.findById(id).orElse(null);
		if (ente == null) {
			throw new NotFoundEx("authority not found");
		}
	}

	public void esistenzaResponsabileConferenza(Integer id) {
		ResponsabileConferenza responsabile = respConfRepo.findById(id).orElse(null);
		if (responsabile == null) {
			throw new NotFoundEx("manager not found");
		}
	}

	public void esistenzaUtente(Integer id) {
		Utente utente = utenteRepo.findById(id).orElse(null);
		if (utente == null) {
			throw new NotFoundEx("user not found");
		}
	}

	public Conferenza esistenzaConferenza(Integer id) {
		Conferenza conferenza = confRepo.findById(id).orElse(null);
		if (conferenza == null) {
			throw new NotFoundEx("conference not found");
		}
		return conferenza;
	}

	public void datiObbligatoriPrecaricamentoPartecipanteDTO(PrecaricamentoPartecipanteDTO partecipantDTO) {
		List<Errore> errors = new ArrayList<>();
		if (partecipantDTO.getEnte() == null) {
			String msg = messageSource.getMessage("rubricaPartecipante.ente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preemptiveParticipant.authority", msg));
		}
		if (partecipantDTO.getTipologiaConferenza() == null) {
			String msg = messageSource.getMessage("rubricaPartecipante.tipologiaConferenza.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preemptiveParticipant.conferenceType", msg));
		}
		if (partecipantDTO.getRuoloPartecipante() == null) {
			String msg = messageSource.getMessage("rubricaPartecipante.ruoloPartecipante.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preemptiveParticipant.participantRole", msg));
		}
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
	}

	public void esistenzaRubricaImpresa(Impresa impresa, TipologiaConferenzaSpecializzazione tipoConfSpec) {
		if (impresa != null && tipoConfSpec != null) {
			if (rubricaImprRepo
				.findByTipologiaConferenzaSpecializzazioneAndImpresa(tipoConfSpec, impresa).isPresent()) {
				throw new NotEditableException("impresa gia' associata a questa tipologia di conferenza");
			}
		}
	}

	public void datiObbligatoriPrecaricamentoImpresaCompletaDTO(PrecaricamentoImpresaCompletaDTO impresaDTO, Boolean associaImpresa) {
		List<Errore> errors = new ArrayList<>();
		if (impresaDTO.getPartitaIva() == null || impresaDTO.getPartitaIva().isEmpty()) {
			String msg = messageSource.getMessage("rubricaImpresa.partitaIva.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preemptiveCompany.vatNumber", msg));
		}
		if (impresaDTO.getNome() == null || impresaDTO.getNome().isEmpty()) {
			String msg = messageSource.getMessage("rubricaImpresa.nome.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preemptiveCompany.name", msg));
		}
		if (impresaDTO.getTipologiaConferenza() == null && associaImpresa) {
			String msg = messageSource.getMessage("rubricaImpresa.tipologiaConferenza.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preemptiveCompany.conferenceType", msg));
		}
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
	}

	public RubricaImprese validateRubricaImpresa(Integer id) {
		RubricaImprese rubricaImprese = rubricaImprRepo.findById(id).orElse(null);
		if (rubricaImprese == null) {
			throw new NotFoundEx("preemptive company not found");
		}
		return rubricaImprese;
	}

	public Impresa esistenzaImpresa(Integer id) {
		Impresa impresa = impresaRepo.findById(id).orElse(null);
		if (impresa == null) {
			throw new NotFoundEx("company not found");
		}
		return impresa;
	}

	public TipologiaConferenzaSpecializzazione esistenzaTipologiaConferenzaSpec(String keyTipoConfSpec) {
		TipologiaConferenzaSpecializzazione tipoConfSpec = tipoConfSpecRepo
				.findById(keyTipoConfSpec).orElse(null);
		if (tipoConfSpec == null) {
			throw new NotFoundEx("conference type not found");
		}
		return tipoConfSpec;
	}
	
	public Persona esistenzaPersona(Integer id) {
		Persona persona = personaRepo.findById(id).orElse(null);
		if (persona == null) {
			throw new NotFoundEx("person not found");
		}
		return persona;
	}

	public void validateDeletePersona(Persona persona) {
		Utente utente = utenteRepo.findByCodiceFiscaleIgnoreCase(persona.getCodiceFiscale()).orElse(null);
		if (utente != null) {
			validateCambioProfiloUtente(utente.getIdUtente());
		}
	}

	public void esistenzaEnteByCodiceFiscaleAndCodiceUfficio(String codiceFiscale, String codiceUfficio) {
		Ente ente = enteRepo.findByCodiceFiscaleEnteAndCodiceUfficio(codiceFiscale, codiceUfficio).orElse(null);
		if (ente != null) {
			throw new NotEditableException("l'ente e' gia' presente");
		}
	}

	public void datiObbligatoriEnteDTO(EnteDTO enteDTO) {
		List<Errore> errors = new ArrayList<>();
		if (enteDTO.getCodiceFiscale() == null || enteDTO.getCodiceFiscale().isEmpty()) {
			String msg = messageSource.getMessage("ente.codiceFiscale.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.fiscalCode", msg));
		}
		if (enteDTO.getNome() == null || enteDTO.getNome().isEmpty()) {
			String msg = messageSource.getMessage("ente.nome.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.name", msg));
		}
		if (enteDTO.getPec() == null || enteDTO.getPec().isEmpty()) {
			String msg = messageSource.getMessage("ente.pec.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.pec", msg));
		}
		if (enteDTO.getRegione() == null) {
			String msg = messageSource.getMessage("ente.regione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.area", msg));
		}
		if (enteDTO.getProvincia() == null) {
			String msg = messageSource.getMessage("ente.provincia.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.province", msg));
		}
		if (enteDTO.getComune() == null) {
			String msg = messageSource.getMessage("ente.comune.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("authority.city", msg));
		}
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
	}

	public void datiObbligatoriCaricamentoDocumenti(DocumentoFileDTO documentoFileDTO) {
		List<Errore> errors = new ArrayList<>();
		if (documentoFileDTO.getCategoria() != null && !documentoFileDTO.getCategoria().isEmpty() && documentoFileDTO
				.getCategoria().equalsIgnoreCase(Integer.toString(DbConst.CATEGORIA_DOCUMENTO_VIDEO_RIUNIONE))) {
			if (documentoFileDTO.getDataRiunione() == null) {
				String msg = messageSource.getMessage("documento.dataRiunione.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("document.meetingDate", msg));
			}
		}
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		
	}

	public void controlloTipologieDocConsolle(DocumentoFileDTO documentoFileDTO) {
		List<TipologiaDocumento> listaTipoDoc = tipoDocRepo.findByFlagUploadConsolle(Boolean.TRUE);
		TipologiaDocumento tipoDoc = tipoDocRepo.findById(documentoFileDTO.getTipoDocumento()).orElse(null);
		if (tipoDoc == null) {
			throw new NotFoundEx("document type not found");
		}
		Boolean isPresent = listaTipoDoc.stream().anyMatch(tipo -> tipo.equals(tipoDoc));
		if (!isPresent) {
			throw new NotEditableException("tipologia documento non utilizzabile");
		}
	}

	public void datiCongruenti(EnteDTO enteDTO) {
		if (enteDTO.getFlagAmmProc()!= null && enteDTO.getCodiceUfficio() != null) {
			throw new NotEditableException("This is an office. It must be not a proceeding administration");
		}
	}

	public RubricaPreaccreditati validateRubricaPreaccreditati(Integer id) {
		RubricaPreaccreditati rubricaPreaccreditati = rubricaPreaccrRepo.findById(id).orElse(null);
		if (rubricaPreaccreditati == null) {
			throw new NotFoundEx("preaccreditation not found");
		}
		return rubricaPreaccreditati;
	}
	
	public void datiObbligatoriPreaccreditatiDTO(PreaccreditatoDTO preaccreditatoDTO) {
		List<Errore> errors = new ArrayList<>();
		if (preaccreditatoDTO.getEnte() == null) {
			String msg = messageSource.getMessage("rubricaPartecipante.ente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preaccreditation.authority", msg));
		}
		if (preaccreditatoDTO.getTipoConf() == null) {
			String msg = messageSource.getMessage("rubricaPreaccreditati.tipologiaConferenza.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preaccreditation.conferenceType", msg));
		}
		if (preaccreditatoDTO.getTipoProfilo() == null) {
			String msg = messageSource.getMessage("rubricaPartecipante.ruoloPartecipante.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("preaccreditation.profileType", msg));
		}
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
	}
	
	public void validateRubricaPreaccreditatoEsistenza(RubricaPreaccreditati rubricaPreaccr) {
		if (rubricaPreaccrRepo.findByPersonaAndEnteAndTipologiaConferenzaSpecializzazione(rubricaPreaccr.getPersona(), rubricaPreaccr.getEnte(), rubricaPreaccr.getTipologiaConferenzaSpecializzazione()).isPresent()) {
			throw new NotEditableException("preaccreditato per ente gia' inserito");
		}
	}
}
