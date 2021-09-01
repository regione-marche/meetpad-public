package conferenza.builder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.AccreditamentoDTO;
import conferenza.DTO.AccreditamentoFileDTO;
import conferenza.DTO.EnteDTO;
import conferenza.DTO.PartecipanteDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.RichiedenteDTO;
import conferenza.DTO.UtenteDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.model.Accreditamento;
import conferenza.model.CompetenzaAutorizzativa;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.EmailPartecipante;
import conferenza.model.Ente;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.model.Profilo;
import conferenza.model.ResponsabileConferenza;
import conferenza.model.RubricaPartecipanti;
import conferenza.model.RuoloPersona;
import conferenza.model.TipoProfilo;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.CompetenzaAutorizzativaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProfiloRepository;
import conferenza.repository.ResponsabileConferenzaRepository;
import conferenza.repository.RubricaImpreseRepository;
import conferenza.repository.RubricaPartecipantiRepository;
import conferenza.repository.RubricaRichiedentiRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.repository.RuoloPersonaRepository;
import conferenza.repository.TipoProfiloRepository;
import conferenza.repository.TipologiaConferenzaRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.repository.UtenteRepository;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;

@Component
public class PartecipanteBuilder extends _BaseBuilder {

	@Autowired
	EnteRepository enteRepo;

	@Autowired
	RuoloPartecipanteRepository ruoloPartecipanteRepo;

	@Autowired
	RuoloPersonaRepository ruoloPersonaRepo;

	@Autowired
	PartecipanteRepository partecipanteRepo;

	@Autowired
	PersonaRepository personaRepository;

	@Autowired
	AccreditamentoRepository accreditamentoRepo;

	@Autowired
	UtenteRepository utenteRepo;

	@Autowired
	TipoProfiloRepository tipoProfiloRepo;

	@Autowired
	ProfiloRepository profiloRepo;

	@Autowired
	UtenteService utenteService;

	@Autowired
	private RegistroDocumentoService registroDocumentoService;

	@Autowired
	DocumentoBuilder documentoBuilder;

	@Autowired
	DocumentoRepository documentoRepo;

	@Autowired
	TipologiaDocumentoRepository tipoDocumentoRepo;
	
	@Autowired
	ResponsabileConferenzaRepository responsabileConfRepo;
	
	@Autowired
	CompetenzaAutorizzativaRepository compAutoRepo;
	
	@Autowired
	RubricaRichiedentiRepository rubricaRichRepo;
	
	@Autowired
	RubricaImpreseRepository rubricaImpRepo;
	
	@Autowired
	EventoRepository eventoRepo;
	
	@Autowired
	RubricaPartecipantiRepository rubricaPartRepo;
	
	@Autowired
	TipologiaConferenzaRepository tipoConfRepo;

	public PartecipanteDTO buildPartecipanteDTO(Partecipante partecipante) {
		PartecipanteDTO partecipanteDTO = new PartecipanteDTO();
		partecipanteDTO.setId(partecipante.getIdPartecipante());
		partecipanteDTO.setDescEnte(partecipante.getDescEnteCompetente());
		partecipanteDTO.setPec(partecipante.getPecEnteCompetente());
		partecipanteDTO.setCf(partecipante.getCfEnteCompetente());
		partecipanteDTO.setEnte(createNotNullLabelValue(partecipante.getEnte()));
		partecipanteDTO.setRuolo(createNotNullLabelValue(partecipante.getRuoloPartecipante()));
		partecipanteDTO.setEsprimeParere(partecipante.getEsprimeDeterminazione());
		partecipanteDTO.setCompetenza(partecipante.getCompetenza());
		if (partecipante.getAltreEmail().size() != 0) {
			for (EmailPartecipante email : partecipante.getAltreEmail()) {
				partecipanteDTO.getAltreEmail().add(email.getEmail());
			}
		}
		if (partecipante.getListaCompAutoPerPartecipante().size() != 0) {
			for (CompetenzaAutorizzativa competenza : partecipante.getListaCompAutoPerPartecipante()) {
				partecipanteDTO.getCompetenzaAutorizzativa().add(createNotNullLabelValue(competenza));
			}
		}
		
		// PersonaDTO
		/*List<Accreditamento> listaAccreditati = partecipante.getAccreditati();
		for (Accreditamento accreditamento : listaAccreditati) {
			PersonaRuoloConferenzaDTO personaRuoloDTO = new PersonaRuoloConferenzaDTO();
			if (accreditamento.getPersona() != null) {
				personaRuoloDTO.setId(accreditamento.getId());
				personaRuoloDTO.setNome(accreditamento.getPersona().getNome());
				personaRuoloDTO.setCognome(accreditamento.getPersona().getCognome());
				personaRuoloDTO.setCodiceFiscale(accreditamento.getPersona().getCodiceFiscale());
				personaRuoloDTO.setEmail(accreditamento.getPersona().getEmail());
				personaRuoloDTO.setProfilo(createNotNullLabelValue(accreditamento.getRuoloPersona()));
			}
			partecipanteDTO.getListaUtente().add(personaRuoloDTO);
		}*/
		return partecipanteDTO;
	}

	public Partecipante buildPartecipante(PartecipanteDTO partecipanteDTO, Conferenza conferenza) {
		Partecipante partecipante;
		if (partecipanteDTO.getId() == null) {
			partecipante = new Partecipante();
		} else {
			partecipante = this.partecipanteRepo.findById(partecipanteDTO.getId()).get();
		}
		partecipante.setEnte(enteRepo.findById(new Integer(partecipanteDTO.getEnte().getKey())).orElse(null));
		partecipante
				.setRuoloPartecipante(ruoloPartecipanteRepo.findById(partecipanteDTO.getRuolo().getKey()).orElse(null));
		partecipante.setPecEnteCompetente(partecipanteDTO.getPec());
		partecipante.setCfEnteCompetente(partecipanteDTO.getCf().toUpperCase());
		partecipante.setDescEnteCompetente(partecipanteDTO.getDescEnte());
		partecipante.setCompetenza(partecipanteDTO.getCompetenza());
		partecipante.setEsprimeDeterminazione(partecipanteDTO.isEsprimeParere());
		List<CompetenzaAutorizzativa> listaCompetenza = partecipanteDTO.getCompetenzaAutorizzativa().stream()
				.map(lab -> compAutoRepo.findById(lab.getKey()).get()).collect(Collectors.toList());
		partecipante.setListaCompAutoPerPartecipante(listaCompetenza);
		partecipante.setConferenza(conferenza);
		return partecipante;
	}

	public EmailPartecipante buildEmailPartecipante(String email, Partecipante partecipante) {
		EmailPartecipante emailPartecipante = new EmailPartecipante();
		emailPartecipante.setEmail(email);
		emailPartecipante.setPartecipante(partecipante);
		return emailPartecipante;
	}

	public Persona buildPersona(PersonaDTO personaDTO) {
		Persona persona;
		if (this.personaRepository.findByCodiceFiscaleIgnoreCase(personaDTO.getCodiceFiscale()).isPresent()) {
			persona = this.personaRepository.findByCodiceFiscaleIgnoreCase(personaDTO.getCodiceFiscale()).get();
		} else {
			persona = new Persona();
		}
		if (personaDTO.getCognome() != null && !personaDTO.getCognome().isEmpty()) {
			persona.setCognome(personaDTO.getCognome());
		}
		if (personaDTO.getNome() != null && !personaDTO.getNome().isEmpty()) {
			persona.setNome(personaDTO.getNome());
		}
		if (personaDTO.getCodiceFiscale() != null && !personaDTO.getCodiceFiscale().isEmpty()) {
			persona.setCodiceFiscale(personaDTO.getCodiceFiscale().toUpperCase());
		}
		/*
		 * aggiorno la mail solo se arriva valorizzata altrimenti lascio quella presente
		 */
		if (personaDTO.getEmail() != null && !personaDTO.getEmail().isEmpty()) {
			persona.setEmail(personaDTO.getEmail());
		}
		return persona;
	}

	public RichiedenteDTO buildRichiedenteDTO(Persona persona) {
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		richiedenteDTO.setCognomeRichiedente(persona.getCognome());
		richiedenteDTO.setNomeRichiedente(persona.getNome());
		richiedenteDTO.setCodiceFiscaleRichiedente(persona.getCodiceFiscale().toUpperCase());
		richiedenteDTO.setPec(persona.getEmail());
		return richiedenteDTO;
	}

	public PersonaRuoloConferenzaDTO buildPersonaRuoloDTO(Accreditamento accreditamento) {
		PersonaRuoloConferenzaDTO personaRuoloDTO = new PersonaRuoloConferenzaDTO();
		
		if (accreditamento != null) {
			personaRuoloDTO.setProfilo(createNotNullLabelValue(accreditamento.getRuoloPersona()));
			Persona persona = accreditamento.getPersona();
			personaRuoloDTO.setCognome(persona.getCognome());
			personaRuoloDTO.setNome(persona.getNome());
			personaRuoloDTO.setCodiceFiscale(persona.getCodiceFiscale().toUpperCase());
			personaRuoloDTO.setEmail(persona.getEmail());
			return personaRuoloDTO;
		}
		return  null;
	}

	public Accreditamento buildAccreditamento(PersonaRuoloConferenzaDTO personaRuoloDTO, Partecipante partecipante,
			Persona persona) {
		Accreditamento accreditamento;
		if (personaRuoloDTO.getId() == null) {
			accreditamento = new Accreditamento();
		} else {
			accreditamento = this.accreditamentoRepo.findById(personaRuoloDTO.getId()).get();
		}
		accreditamento.setPersona(persona);
		accreditamento.setPartecipante(partecipante);
		accreditamento
				.setRuoloPersona(this.ruoloPersonaRepo.findById(personaRuoloDTO.getProfilo().getKey()).orElse(null));
		accreditamento.setFlagInvitato(Boolean.TRUE);
		if (personaRuoloDTO.getProfilo().getKey().equals(Integer.toString(DbConst.RUOLO_PERSONA_RICHIEDENTE))
				|| personaRuoloDTO.getProfilo().getKey()
						.equals(Integer.toString(DbConst.RUOLO_PERSONA_RESPONSABILE_CONFERENZA))) {
			accreditamento.setFlagAccreditato(Boolean.TRUE);
		} else {
			accreditamento.setFlagAccreditato(Boolean.FALSE);
		}
		
		//in ogni caso imposto il flag a true se non è richiesto l'accreditamento
		if (tipologiaNonRichiedeAccreditamento(partecipante.getConferenza())) {
			accreditamento.setFlagAccreditato(Boolean.TRUE);
		}
		
		return accreditamento;
	}

	public boolean tipologiaNonRichiedeAccreditamento(Conferenza conferenza) {
		return conferenza.getTipologiaConferenzaSpecializzazione() != null && 
				conferenza.getTipologiaConferenzaSpecializzazione().getFlagAutoaccreditamento();
	}

	public Utente buildUtente(Accreditamento accreditamento) {
		if (accreditamento.getFlagAccreditato()) {
			if (!this.utenteRepo.findByCodiceFiscaleIgnoreCase(accreditamento.getPersona().getCodiceFiscale())
					.isPresent()) {
				Utente utente = new Utente();
				utente.setNome(accreditamento.getPersona().getNome());
				utente.setCognome(accreditamento.getPersona().getCognome());
				utente.setCodiceFiscale(accreditamento.getPersona().getCodiceFiscale());
				utente.setEmail(accreditamento.getPersona().getEmail());
				utente.setDelegaCreazioneAltreAmministrazioni(Boolean.FALSE);
				Ente ente = accreditamento.getPartecipante().getConferenza().getAmministrazioneProcedente();
				TipoProfilo tipoProfilo;
				Profilo profilo;
				if (accreditamento.isResponsabileConferenza()) {
					tipoProfilo = this.tipoProfiloRepo
							.findByCodice(Integer.toString(DbConst.TIPO_PROFILO_RESPONSABILE_CONFERENZA)).get();
					if (!responsabileConfRepo.findByEnteAndPersona(ente, accreditamento.getPersona()).isPresent()) {
						ResponsabileConferenza responsabileConf = new ResponsabileConferenza();
						responsabileConf.setEnte(ente);
						responsabileConf.setPersona(accreditamento.getPersona());
						responsabileConfRepo.save(responsabileConf);
					}
				} else {
					tipoProfilo = this.tipoProfiloRepo.findByCodice(Integer.toString(DbConst.TIPO_PROFILO_PARTECIPANTE))
							.get();
				}
				profilo = this.profiloRepo.findByTipoProfiloAndAmministrazioneProcedente(tipoProfilo, ente)
						.orElse(null);
				if (profilo == null) {
					profilo = new Profilo();
					profilo.setAmministrazioneProcedente(ente);
					profilo.setTipoProfilo(tipoProfilo);
					profiloRepo.save(profilo);
				}
				utente.setProfilo(profilo);
				return utente;
			}
		}
		return null;

	}

	public PersonaRuoloConferenzaDTO buildAccreditamentoToPersonaRuoloDTO(Accreditamento accreditamento) {
		PersonaRuoloConferenzaDTO personaRuoloDTO = new PersonaRuoloConferenzaDTO();
		personaRuoloDTO.setNome(accreditamento.getPersona().getNome());
		personaRuoloDTO.setCognome(accreditamento.getPersona().getCognome());
		personaRuoloDTO.setCodiceFiscale(accreditamento.getPersona().getCodiceFiscale());
		personaRuoloDTO.setEmail(accreditamento.getPersona().getEmail());
		personaRuoloDTO.setId(accreditamento.getId());
		RuoloPersona ruolo = this.ruoloPersonaRepo.findById(accreditamento.getRuoloPersona().getCodice()).get();
		personaRuoloDTO.setProfilo(createNotNullLabelValue(ruolo));
		if(accreditamento.getPersona().getCodiceFiscale() != null) {
			personaRuoloDTO.setFlagRichPrincipale(accreditamento.getPersona().getCodiceFiscale().equals(accreditamento.getPartecipante().getConferenza().getCodiceFiscaleRichiedente()));
		}else {
			personaRuoloDTO.setFlagRichPrincipale(false);
		}
		return personaRuoloDTO;
	}

	public AccreditamentoDTO buildAccrToAccrDTO(Accreditamento accreditamento) {
		AccreditamentoDTO accreditamentoDTO = new AccreditamentoDTO();
		if (accreditamento != null) {
			accreditamentoDTO.setNome(accreditamento.getPersona().getNome());
			accreditamentoDTO.setCognome(accreditamento.getPersona().getCognome());
			accreditamentoDTO.setCodiceFiscale(accreditamento.getPersona().getCodiceFiscale());
			accreditamentoDTO.setEmail(accreditamento.getPersona().getEmail());
			accreditamentoDTO.setProfilo(createNotNullLabelValue(
					this.ruoloPersonaRepo.findById(accreditamento.getRuoloPersona().getCodice()).get()));
			accreditamentoDTO.setId(accreditamento.getId());
			accreditamentoDTO.setFlagInvitato(accreditamento.getFlagInvitato());
			accreditamentoDTO.setFlagAccreditato(accreditamento.getFlagAccreditato());
			accreditamentoDTO.setFlagRifiuto(accreditamento.getFlagRifiuto());
			accreditamentoDTO.setDescrizioneRifiuto(accreditamento.getDescrizioneRifiuto());
			accreditamentoDTO.setIdConferenza(accreditamento.getPartecipante().getConferenza().getIdConferenza());
			accreditamentoDTO.setIdPartecipante(accreditamento.getPartecipante().getIdPartecipante());
			accreditamentoDTO.setDescrizionePartecipante(accreditamento.getPartecipante().getDescEnteCompetente());
			accreditamentoDTO.setCreationDate(dateToString(accreditamento.getCreatedDate()));
			Documento documento = documentoAccreditamento(accreditamento);
			if (documento != null) {
				accreditamentoDTO.setUrl(
						registroDocumentoService.resolveFileDownloadUri(this.documentoBuilder.getRegistroDocumento(documento)));
			}
			return accreditamentoDTO;
		} else {
			return null;
		}

	}

	public Documento documentoAccreditamento(Accreditamento accreditamento) {
		return documentoRepo.findByTipologiaDocumentoAndOwner(
				tipoDocumentoRepo.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_ACCREDITAMENTO).orElse(null),
				accreditamento).orElse(null);
	}

	public Accreditamento buildAccreditamentoFromFile(AccreditamentoFileDTO fileDTO, Partecipante partecipante,
			Accreditamento accreditamento) {
		boolean inserimento = accreditamento == null;
		RuoloPersona nuovoRuolo = this.ruoloPersonaRepo.findById(fileDTO.getRuoloPersona()).get();
		Persona persona = personaRepository.save(buildPersona(buildAccrFileDTOToPersonaDTO(fileDTO)));

		if (inserimento) {
			accreditamento = new Accreditamento();
			accreditamento.setFlagAccreditato(Boolean.FALSE);
			accreditamento.setFlagInvitato(Boolean.FALSE);
			accreditamento.setFlagRifiuto(Boolean.FALSE);
			accreditamento.setPartecipante(partecipante);
		} else {
			if ((accreditamento.getRuoloPersona().equals(nuovoRuolo)) && accreditamento.getFlagInvitato()) {
				accreditamento.setFlagAccreditato(Boolean.TRUE);
			}
		}
		
		//in ogni caso imposto il flag a true se non è richiesto l'accreditamento
		if (tipologiaNonRichiedeAccreditamento(partecipante.getConferenza())) {
			accreditamento.setFlagAccreditato(Boolean.TRUE);
		}

		accreditamento.setPersona(persona);
		accreditamento.setRuoloPersona(nuovoRuolo);

		return accreditamento;
	}

	public PersonaDTO buildAccrFileDTOToPersonaDTO(AccreditamentoFileDTO fileDTO) {
		PersonaDTO personaDTO = new PersonaDTO();
		personaDTO.setNome(fileDTO.getNome());
		personaDTO.setCodiceFiscale(fileDTO.getCodiceFiscale());
		personaDTO.setCognome(fileDTO.getCognome());
		personaDTO.setEmail(fileDTO.getEmail());
		return personaDTO;
	}

	public PartecipanteDTO buildRubricaPartecipanteToPartecipanteDTO(RubricaPartecipanti rubricaPart) {
		Partecipante partecipante = new Partecipante();
		partecipante.setEnte(rubricaPart.getEnte());
		partecipante.setCfEnteCompetente(rubricaPart.getEnte().getCodiceFiscaleEnte());
		partecipante.setDescEnteCompetente(rubricaPart.getEnte().getDescrizioneEnte());
		partecipante.setPecEnteCompetente(rubricaPart.getEnte().getPecEnte());
		partecipante.setRuoloPartecipante(rubricaPart.getRuoloPartecipante());
		partecipante.setListaCompAutoPerPartecipante(rubricaPart.getListaCompAutoPerRbricaPartecipante());
		PartecipanteDTO partecipanteDTO = this.buildPartecipanteDTO(partecipante);
		return partecipanteDTO;
	}
	
	public PartecipanteDTO buildEnteDTOToPartecipanteDTO(EnteDTO ente) {
		PartecipanteDTO partecipante = new PartecipanteDTO();
		partecipante.setEnte(createNotNullLabelValue(this.enteRepo.findById(ente.getId()).get()));
		partecipante.setDescEnte(ente.getDescrizione());
		partecipante.setCf(ente.getCodiceFiscale());
		partecipante.setPec(ente.getPec());
		partecipante.setRuolo(ente.getRuolo());
		return partecipante;
	}

	public PersonaRuoloConferenzaDTO buildPersonaRuoloDTOFromPersona(Persona persona, String ruoloPersona) {
		PersonaRuoloConferenzaDTO personaRuoloConferenzaDTO = new PersonaRuoloConferenzaDTO();
		personaRuoloConferenzaDTO.setNome(persona.getNome());
		personaRuoloConferenzaDTO.setCognome(persona.getCognome());
		personaRuoloConferenzaDTO.setCodiceFiscale(persona.getCodiceFiscale());
		personaRuoloConferenzaDTO.setEmail(persona.getEmail());
		personaRuoloConferenzaDTO.setProfilo(new LabelValue(ruoloPersona, "value"));
		return personaRuoloConferenzaDTO;
	}

	public PersonaDTO buildPersonaDTO(Persona persona) {
		PersonaDTO personaDTO = new PersonaDTO();
		if (persona == null) {
			personaDTO = null;
		} else {
			personaDTO.setNome(persona.getNome());
			personaDTO.setCognome(persona.getCognome());
			personaDTO.setCodiceFiscale(persona.getCodiceFiscale());
			personaDTO.setEmail(persona.getEmail());
			personaDTO.setId(persona.getIdPersona());
		}
		return personaDTO;
	}

	public PersonaDTO buildUtenteDTOToPersonaDTO(UtenteDTO utenteDTO) {
		PersonaDTO personaDTO = new PersonaDTO();
		personaDTO.setNome(utenteDTO.getNome());
		personaDTO.setCognome(utenteDTO.getCognome());
		personaDTO.setCodiceFiscale(utenteDTO.getCodiceFiscale());
		personaDTO.setEmail(utenteDTO.getEmail());
		return personaDTO;
	}
}
