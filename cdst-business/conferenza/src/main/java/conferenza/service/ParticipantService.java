package conferenza.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaPartecipanteDTO;
import conferenza.DTO.ListaPersonaRuoloConferenzaDTO;
import conferenza.DTO.PartecipanteDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.bean.Errore;
import conferenza.builder.PartecipanteBuilder;
import conferenza.builder._BaseBuilder;
import conferenza.exception.InvalidFieldEx;
import conferenza.exception.NotFoundEx;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.EmailPartecipante;
import conferenza.model.Evento;
import conferenza.model.Partecipante;
import conferenza.model.Persona;
import conferenza.model.RubricaImprese;
import conferenza.model.RubricaPreaccreditati;
import conferenza.model.RubricaRichiedenti;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EmailPartecipanteRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.RubricaImpreseRepository;
import conferenza.repository.RubricaPreaccreditatiRepository;
import conferenza.repository.RubricaRichiedentiRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.repository.RuoloPersonaRepository;
import conferenza.repository.UtenteRepository;
import conferenza.util.DbConst;
import conferenza.validator.ConferenceValidator;

@Transactional
@Service
public class ParticipantService extends _BaseBuilder {

	private static final Logger log = LoggerFactory.getLogger(ParticipantService.class.getName());
	
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
	
	@Autowired
	RubricaRichiedentiRepository rubricaRichRepo;
	
	@Autowired
	RubricaImpreseRepository rubricaImpRepo;
	
	@Autowired
	RubricaPreaccreditatiRepository rubricaPreAccreditamentoRepository;
	
	@Autowired
	EventoRepository eventoRepo;
	
	@Autowired
	ConferenceValidator confValidator;

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
				if (partecipante.getDataFine() == null) {
					PartecipanteDTO partecipanteDTO = partecipanteBuilder.buildPartecipanteDTO(partecipante);
					List<Evento> listaEventi = eventoRepo.findByMittente(partecipante);
					partecipanteDTO.setFlagParereEspresso(listaEventi.stream().anyMatch(e -> e.getTipoEvento()
							.getCodice().equals(Integer.toString(DbConst.TIPOLOGIA_EVENTO_ESPRESSIONE_PARERI))));
					Boolean isPresentIntegration = listaEventi.stream().anyMatch(e -> e.getTipoEvento().getCodice()
							.equals(Integer.toString(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_INTEGRAZIONE))) || 
							listaEventi.stream().anyMatch(e -> e.getTipoEvento().getCodice()
									.equals(Integer.toString(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_UNICA_INTEGRAZIONE)));
					partecipanteDTO.setFlagRichiestaIntegrazioneEffettuata(isPresentIntegration);
					listaDTO.getList().add(partecipanteDTO);
				}
			}
		}
		listaDTO.setTotalNumber(new Long(listaPartecipanti.size()));
		return listaDTO;
	}

	/**
	 * 
	 * @param persona
	 * @return
	 */
	public boolean existPesona(Persona persona){
		
		if(persona.getCodiceFiscale()==null)
			return false;
			
		Optional<Persona> oesistente=this.personaRepo.findByCodiceFiscaleIgnoreCase(persona.getCodiceFiscale());
		//
		if(oesistente.isPresent()==false)
			return false;
		Persona esistente=oesistente.get();
		if(esistente!=null && esistente.getCodiceFiscale()!=null && !"".equals(esistente.getCodiceFiscale()))
			return true;
		
		return false;
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
		
		// reupero la lista delle persone censite come pre accreditamento per il partecipante
		List<RubricaPreaccreditati> listaPreAccreditamento = new ArrayList<>();
		if(!saved.isAmministrazioneProcendete() ) {
			listaPreAccreditamento = rubricaPreAccreditamentoRepository.findByEnteAndTipologiaConferenzaSpecializzazione(saved.getEnte(),
					conferenza.getTipologiaConferenzaSpecializzazione());
		}
		//RubricaRichiedenti rubricaRich = null;
		//Persona richiedenteImpresa = null;
		for (PersonaRuoloConferenzaDTO personaRuolo : listaPersonaRuolo) {
			Iterator<RubricaPreaccreditati> it = listaPreAccreditamento.iterator();
			Persona persona = this.partecipanteBuilder.buildPersona(personaRuolo);
			
			if(persona==null)
				continue;
			
			Persona personaSaved =null;
			if(persona!=null && existPesona(persona)==false){
				try{
					personaSaved = this.personaRepo.save(persona);
				}catch (Exception e) {
					log.debug(e.getMessage());
				}
				
			}else
				personaSaved =persona;
			Accreditamento accreditamento = this.partecipanteBuilder.buildAccreditamento(personaRuolo, saved,
					personaSaved);
			try {
				this.accreditamentoRepo.save(accreditamento);	
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
			
			if (accreditamento.isResponsabileConferenza()) {
				conferenza.setCodiceFiscaleResponsabileConferenza(personaSaved.getCodiceFiscale().toUpperCase());
				conferenza.setAmministrazioneProcedente(saved.getEnte());
			}
			
			// elimino tutte le persone già presenti nella lista listaPersonaRuolo
			while (it.hasNext()) {
				RubricaPreaccreditati personaPreAccreditamento = it.next();
				if ( personaPreAccreditamento.getPersona().getIdPersona().equals(personaSaved.getIdPersona()) ) {
					it.remove();
				}
			}
			/*if (accreditamento.isRichiedente()) {
				rubricaRich = rubricaRichRepo
						.findByPersonaAndTipologiaConferenza(personaSaved, conferenza.getTipologiaConferenza())
						.orElse(null);
				richiedenteImpresa = personaSaved;
			}*/
		}
		/*if (rubricaRich != null) {
			fillAccRichiedentiImpresa(rubricaRich, saved, richiedenteImpresa);
		}*/
		

		for(RubricaPreaccreditati preaccreditamento: listaPreAccreditamento) {
			Persona persona = preaccreditamento.getPersona();
			PersonaRuoloConferenzaDTO personaRuolo = partecipanteBuilder.buildPersonaRuoloDTOFromPersona(persona,
					preaccreditamento.getRuoloPersona().getCodice());
			
			Accreditamento accreditamento = this.partecipanteBuilder.buildAccreditamento(personaRuolo, saved,
					persona);
			List<Accreditamento> accreditamenti = accreditamentoRepo.findByPartecipanteAndPersona(saved, persona);
			if(accreditamenti == null || accreditamenti.isEmpty()) {
				try {
					this.accreditamentoRepo.save(accreditamento);	
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
			}
		}
		
		conferenzaRepo.save(conferenza);
		try{
			creaUtentiAccreditati(this.findConferenzaById(idConferenza));
		}catch (Exception e) {
			log.debug(e.getMessage());
		}
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getIdPartecipante());
		return identifiableDTO;
	}

	public void fillAccRichiedentiImpresa(Partecipante partecipante, Persona persona) {
		RubricaImprese rubricaImprese = rubricaImpRepo
				.findByTipologiaConferenzaSpecializzazioneAndImpresa(partecipante.getConferenza().getTipologiaConferenzaSpecializzazione(),
						partecipante.getConferenza().getImpresa())
				.orElse(null);
		if (rubricaImprese != null) {
			List<RubricaRichiedenti> listaRubricaRichiedenti = rubricaRichRepo.findByImpresaAndTipologiaConferenzaSpecializzazione(
					rubricaImprese, partecipante.getConferenza().getTipologiaConferenzaSpecializzazione());
			listaRubricaRichiedenti.stream().filter(rr -> !rr.getPersona().equals(persona)).map(rr -> rr.getPersona())
					.forEach(p -> {
						PersonaRuoloConferenzaDTO personaRuolo = partecipanteBuilder.buildPersonaRuoloDTOFromPersona(p,
								Integer.toString(DbConst.RUOLO_PERSONA_RICHIEDENTE));
						accreditamentoRepo.save(partecipanteBuilder.buildAccreditamento(personaRuolo, partecipante, p));
					});
		}
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
		
		//elimina gli accreditamenti vecchi
		saved.getAccreditati().forEach( accreditato -> {
			this.accreditamentoRepo.delete(accreditato);
		});
		
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
		confValidator.validateCreaAccreditamentoUnico(partecipante, personaRuoloConferenzaDTO.getCodiceFiscale());
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
		if (saved.isRichiedente()) {
			fillAccRichiedentiImpresa(partecipante, personaSaved);
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

	public Partecipante findAmministrazioneProcedente(Conferenza conferenza) {
		Partecipante ammProcedente = null;
		for (Partecipante part: conferenza.getPartecipantes()) {
			if(part.isAmministrazioneProcendete()) {
				ammProcedente = part;
			}
		}
		return ammProcedente;
	}
	
	public void scambioPuntamentiPartecipante(Integer idNuovoPartecipante, Integer idVecchioPartecipante) {
		Partecipante nuovoPart = partecipanteRepo.findById(idNuovoPartecipante).orElse(null);
		Partecipante vecchioPart = partecipanteRepo.findById(idVecchioPartecipante).orElse(null);
		eventoRepo.findByMittente(vecchioPart).stream().forEach(e -> {
			e.setMittente(nuovoPart);
			eventoRepo.save(e);
		});
		vecchioPart.getAltreEmail().clear();
		//TODO ciclare le liste
		vecchioPart.getListaCompAutoPerPartecipante().stream()
				.forEach(ca -> nuovoPart.getListaCompAutoPerPartecipante().add(ca));
		vecchioPart.getVisibilitaPartecipanteEventi().stream()
				.forEach(v -> nuovoPart.getVisibilitaPartecipanteEventi().add(v));
		vecchioPart.getVisibilitaDocumenti().stream().forEach(vd -> nuovoPart.getVisibilitaDocumenti().add(vd));
		vecchioPart.getListaCompAutoPerPartecipante().clear();
		vecchioPart.getVisibilitaPartecipanteEventi().clear();
		vecchioPart.getVisibilitaDocumenti().clear();
		partecipanteRepo.save(vecchioPart);
		partecipanteRepo.save(nuovoPart);
	}
	
	public String deleteParticipantList(List<Integer> participantIdList) {

		participantIdList.forEach( (id) -> {
			this.eliminaPartecipante(id);
		});
		
		return "ok";
	}

}
