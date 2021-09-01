package cdst_be_marche.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaPartecipanteDTO;
import cdst_be_marche.DTO.ListaPersonaRuoloConferenzaDTO;
import cdst_be_marche.DTO.PartecipanteDTO;
import cdst_be_marche.DTO.PersonaRuoloConferenzaDTO;
import cdst_be_marche.DTO.bean.Errore;
import cdst_be_marche.builder.PartecipanteBuilder;
import cdst_be_marche.builder._BaseBuilder;
import cdst_be_marche.exception.InvalidFieldEx;
import cdst_be_marche.exception.NotFoundEx;
import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.EmailPartecipante;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.Persona;
import cdst_be_marche.model.RubricaRichiedenti;
import cdst_be_marche.model.Utente;
import cdst_be_marche.repository.AccreditamentoRepository;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.EmailPartecipanteRepository;
import cdst_be_marche.repository.EnteRepository;
import cdst_be_marche.repository.PartecipanteRepository;
import cdst_be_marche.repository.PersonaRepository;
import cdst_be_marche.repository.RuoloPartecipanteRepository;
import cdst_be_marche.repository.RuoloPersonaRepository;
import cdst_be_marche.repository.UtenteRepository;
import cdst_be_marche.util.DbConst;
import cdst_be_marche.validator.ConferenceValidator;

@Transactional
@Service
public class ParticipantService extends _BaseBuilder {

	@Autowired
	ConferenceValidator validator;
	
	@Autowired
	ConferenzaRepository conferenzaRepo;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	PartecipanteRepository partecipanteRepo;

	@Autowired
	EmailPartecipanteRepository emailPartecipanteRepo;

	@Autowired
	PersonaRepository personaRepo;

	@Autowired
	AccreditamentoRepository accreditamentoRepo;

	@Autowired
	UtenteRepository utenteRepo;

	@Autowired
	EnteRepository enteRepo;

	@Autowired
	RuoloPersonaRepository ruoloPersonaRepo;

	@Autowired
	RuoloPartecipanteRepository ruoloPartecipanteRepo;

	public Conferenza findConferenzaById(Integer id) {
		Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(id);
		if (conferenza == null) {
			throw new NotFoundEx("the record doesn't exist");
		}
		return conferenza;
	}

	public Partecipante findPartecipanteById(Integer id) {
		Partecipante partecipante = this.partecipanteRepo.findById(id).orElse(null);
		if (partecipante == null) {
			throw new NotFoundEx("the record doesn't exist");
		}
		return partecipante;
	}

	public Accreditamento findAccreditamentoById(Integer id) {
		Accreditamento accreditamento = this.accreditamentoRepo.findById(id).get();
		if (accreditamento == null) {
			throw new NotFoundEx("the record doesn't exist");
		}
		return accreditamento;
	}

	public ListaPartecipanteDTO findPartecipantiByIdConferenza(Integer id) {
		Conferenza conferenza = findConferenzaById(id);
		List<Partecipante> listaPartecipanti = new ArrayList<Partecipante>();
		ListaPartecipanteDTO listaDTO = new ListaPartecipanteDTO();
		if (conferenza.getPartecipantes().size() != 0) {
			listaPartecipanti = conferenza.getPartecipantes();
			for (Partecipante partecipante : listaPartecipanti) {
				if (partecipante.getDataFine() == null)
					listaDTO.getList().add(this.partecipanteBuilder.buildPartecipanteDTO(partecipante));
			}
		}
		listaDTO.setTotalNumber(new Long(listaPartecipanti.size()));
		return listaDTO;
	}

	public IdentifiableDTO creaPartecipante(PartecipanteDTO partecipanteDTO, Integer idConferenza) {
		List<Errore> errors = new ArrayList<>();
		errors = validator.validateCreaPartecipante(partecipanteDTO, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		List<PersonaRuoloConferenzaDTO> listaPersonaRuolo = new ArrayList<>();
		if (partecipanteDTO.getListaUtente() != null) {
			listaPersonaRuolo = partecipanteDTO.getListaUtente();
		}
		for (PersonaRuoloConferenzaDTO personaRuolo : listaPersonaRuolo) {
			errors = validator.validatePersonaRuoloConferenzaDTO(personaRuolo, messageSource, errors);
			if (errors.size() != 0) {
				throw new InvalidFieldEx(errors);
			}
		}
		Conferenza conferenza = findConferenzaById(idConferenza);
		Partecipante saved = this.partecipanteRepo
				.save(partecipanteBuilder.buildPartecipante(partecipanteDTO, conferenza));
		List<String> listaEmail = partecipanteDTO.getAltreEmail();
		for (String email : listaEmail) {
			EmailPartecipante emailPartecipante = this.partecipanteBuilder.buildEmailPartecipante(email, saved);
			this.emailPartecipanteRepo.save(emailPartecipante);
		}
		for (PersonaRuoloConferenzaDTO personaRuolo : listaPersonaRuolo) {
			Persona persona = this.partecipanteBuilder.buildPersona(personaRuolo);
			Persona personaSaved = this.personaRepo.save(persona);
			Accreditamento accreditamento = this.partecipanteBuilder.buildAccreditamento(personaRuolo, saved,
					personaSaved);
			this.accreditamentoRepo.save(accreditamento);
			if (accreditamento.isResponsabileConferenza()) {
				conferenza.setCodiceFiscaleResponsabileConferenza(personaSaved.getCodiceFiscale().toUpperCase());
				conferenza.setAmministrazioneProcedente(saved.getEnte());
			}
		}
		conferenzaRepo.save(conferenza);
		creaUtentiAccreditati(this.findConferenzaById(idConferenza));
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdPartecipante());
		return identifiableDTO;
	}

	public IdentifiableDTO modificaPartecipante(PartecipanteDTO partecipanteDTO, Integer id) {
		Partecipante partecipante = findPartecipanteById(id);
		List<Errore> errors = validator.validateCreaPartecipante(partecipanteDTO, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		validator.validateStatoPartecipante(partecipante.getConferenza());
		Partecipante saved = this.partecipanteRepo
				.save(partecipanteBuilder.buildPartecipante(partecipanteDTO, partecipante.getConferenza()));
		List<EmailPartecipante> listaEmailPartecipanti = saved.getAltreEmail();
		List<String> listaEmail = partecipanteDTO.getAltreEmail();
		for (EmailPartecipante emailPartecipante : listaEmailPartecipanti) {
			this.emailPartecipanteRepo.delete(emailPartecipante);
		}
		for (String email : listaEmail) {
			EmailPartecipante emailPartecipante = this.partecipanteBuilder.buildEmailPartecipante(email, saved);
			this.emailPartecipanteRepo.save(emailPartecipante);
		}
		List<PersonaRuoloConferenzaDTO> listaUtenti = new ArrayList<>();
		if (partecipanteDTO.getListaUtente() != null) {
			listaUtenti = partecipanteDTO.getListaUtente();
		}
		for (PersonaRuoloConferenzaDTO utente : listaUtenti) {
			Persona persona = this.partecipanteBuilder.buildPersona(utente);
			Persona personaSaved = this.personaRepo.save(persona);
			Accreditamento accreditamento = this.partecipanteBuilder.buildAccreditamento(utente, saved, personaSaved);
			this.accreditamentoRepo.save(accreditamento);
		}
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdPartecipante());
		return identifiableDTO;
	}

	public String eliminaPartecipante(Integer id) {
		Partecipante partecipante = this.partecipanteRepo.findById(id).orElse(null);
		partecipante.setDataFine(new Date());
		this.partecipanteRepo.save(partecipante);
		List<Accreditamento> listaAccreditati = partecipante.getAccreditati();
		for (Accreditamento accreditamento : listaAccreditati) {
			accreditamento.setDataFine(new Date());
			this.accreditamentoRepo.save(accreditamento);
		}
		List<EmailPartecipante> listaEmailPartecipanti = partecipante.getAltreEmail();
		for (EmailPartecipante emailPartecipante : listaEmailPartecipanti) {
			this.emailPartecipanteRepo.delete(emailPartecipante);
		}
		return "ok";
	}

	/**
	 * creazione delle utenze, se non presenti, per accreditamenti con
	 * flagAccreditato a true
	 * 
	 * @param idConferenza
	 */
	public void creaUtentiAccreditati(Conferenza conferenza) {
		List<Partecipante> listaPartecipanti = this.partecipanteRepo.findByConferenza(conferenza);
		for (Partecipante partecipante : listaPartecipanti) {
			creaUtentiAccreditati(partecipante);
		}

	}

	private void creaUtentiAccreditati(Partecipante partecipante) {
		if (partecipante.getConferenza().getAmministrazioneProcedente() != null) {
			for (Accreditamento accreditamento : this.accreditamentoRepo.findByPartecipante(partecipante)) {
				Utente utente = this.partecipanteBuilder.buildUtente(accreditamento);
				if (utente != null) {
					this.utenteRepo.save(utente);
				}
			}
		}
	}

	public ListaPersonaRuoloConferenzaDTO findAccreditamentiById(Integer idPartecipante) {
		Partecipante partecipante = findPartecipanteById(idPartecipante);
		List<Accreditamento> listaAccreditati = new ArrayList<Accreditamento>();
		ListaPersonaRuoloConferenzaDTO listaDTO = new ListaPersonaRuoloConferenzaDTO();
		if (partecipante.getAccreditati().size() != 0) {
			listaAccreditati = partecipante.getAccreditati();
			for (Accreditamento accreditamento : listaAccreditati) {
				if (accreditamento.getDataFine() == null && accreditamento.getFlagInvitato())
					listaDTO.getList()
							.add(this.partecipanteBuilder.buildAccreditamentoToPersonaRuoloDTO(accreditamento));
			}
			listaDTO.setTotalNumber(new Long(listaDTO.getList().size()));
			return listaDTO;
		}
		return listaDTO;
	}

	public IdentifiableDTO creaAccreditamento(PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO, Integer idConference,
			Integer idPart) {
		List<Errore> errors = new ArrayList<>();
		errors = validator.validatePersonaRuoloConferenzaDTO(personaRuoloConferenzaDTO, messageSource, errors);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		Partecipante partecipante = findPartecipanteById(idPart);
		Persona persona = this.partecipanteBuilder.buildPersona(personaRuoloConferenzaDTO);
		Persona personaSaved = this.personaRepo.save(persona);
		Accreditamento saved = this.accreditamentoRepo
				.save(partecipanteBuilder.buildAccreditamento(personaRuoloConferenzaDTO, partecipante, personaSaved));
		Conferenza conferenza = this.conferenzaRepo.findByIdConferenza(idConference);
		if (saved.isResponsabileConferenza()) {
			conferenza.setCodiceFiscaleResponsabileConferenza(personaSaved.getCodiceFiscale());
			conferenza.setAmministrazioneProcedente(partecipante.getEnte());
			conferenzaRepo.save(conferenza);
		}

		creaUtentiAccreditati(conferenza);
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}

	public IdentifiableDTO modificaAccreditamento(PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO, Integer id) {
		Accreditamento accreditamento = findAccreditamentoById(id);
		List<Errore> errors = new ArrayList<>();
		errors = validator.validatePersonaRuoloConferenzaDTO(personaRuoloConferenzaDTO, messageSource, errors);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		validator.validateStatoPartecipante(accreditamento.getPartecipante().getConferenza());
		Partecipante partecipante = accreditamento.getPartecipante();
		Persona persona = this.partecipanteBuilder.buildPersona(personaRuoloConferenzaDTO);
		Persona personaSaved = this.personaRepo.save(persona);
		Accreditamento saved = this.accreditamentoRepo
				.save(partecipanteBuilder.buildAccreditamento(personaRuoloConferenzaDTO, partecipante, personaSaved));
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}

	public String eliminaAccreditamento(Integer id) {
		Accreditamento accreditamento = findAccreditamentoById(id);
		accreditamento.setDataFine(new Date());
		this.accreditamentoRepo.save(accreditamento);
		return "ok";
	}

}
