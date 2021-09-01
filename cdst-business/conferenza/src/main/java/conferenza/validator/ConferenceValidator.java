package conferenza.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.DefinizioneDTO;
import conferenza.DTO.IstanzaDTO;
import conferenza.DTO.LocalizzazioneDTO;
import conferenza.DTO.PartecipanteDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.PraticaDTO;
import conferenza.DTO.RichiedenteDTO;
import conferenza.DTO.bean.Errore;
import conferenza.exception.NotEditableException;
import conferenza.exception.NotFoundEx;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.EventoCalendarioDataObbligatoria;
import conferenza.model.Partecipante;
import conferenza.model.TipologiaConferenza;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EventoCalendarioDataObbligatoriaRepository;
import conferenza.repository.TipologiaConferenzaRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.util.DbConst;

@Component
public class ConferenceValidator {

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	ConferenzaRepository confRepo;
	
	@Autowired
	EventoCalendarioDataObbligatoriaRepository dataObblRepo;
	
	@Autowired
	TipologiaConferenzaRepository tipoConfRepo;
	
	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipConfSpecRepo;

	/**
	 * validazione per la creazione di una conferenza
	 * 
	 * @param conferenza
	 * @param messageSource
	 * @param locale
	 * @return
	 */
	public List<Errore> validateCreaConferenza(ConferenzaDTO conferenza, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		validateConferenzaDTO(conferenza, messageSource, errors, true);
		return errors;
	}

	/**
	 * validazione per la modifica di una conferenza
	 * 
	 * @param conferenza
	 * @param messageSource
	 * @param locale
	 * @return
	 */
	public List<Errore> validateModificaConferenza(ConferenzaDTO conferenza, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		validateConferenzaDTO(conferenza, messageSource, errors, false);
		return errors;
	}

	/**
	 * Valida l'oggetto ConferenzaDTO in input rispetto a campi obbligatori
	 * 
	 * @author vindeluca
	 * @param conferenza
	 * @param create
	 * @param locale
	 * @return
	 */
	public void validateConferenzaDTO(ConferenzaDTO conferenza, MessageSource messageSource, List<Errore> errors,
			boolean create) {

		validatePraticaDTO(conferenza.getPratica(), messageSource, errors);

		if (!create && conferenza.getStep() > 0) {
			validateDefinizioneDTO(conferenza.getDefinizione(), messageSource, errors);
			validatePartecipantiDTO(conferenza.getPartecipanti(), messageSource, errors);
		}
	}

	private void validatePraticaDTO(PraticaDTO pratica, MessageSource messageSource, List<Errore> errors) {
		if (pratica != null) {
			if (pratica.getRichiedente() != null) {
				// Validazione Richiedente
				RichiedenteDTO richiedente = pratica.getRichiedente();
				if (richiedente.getAttivita() == null || (
						richiedente.getAttivita() !=null &&
						!richiedente.getAttivita().getKey().equals(DbConst.ATTIVITA_RICOSTRUZIONE_PUBBLICA))) {
					if (richiedente.getNomeRichiedente() == null) {
						String msg = messageSource.getMessage("nome.richiedente.null", null, request.getLocale());
						errors.add(Errore.getErrorSingleField("procedure.applicant.name", msg));
					}
				} else {
					if (richiedente.getAttivita() !=null &&
						richiedente.getAttivita().getKey().equals(DbConst.ATTIVITA_RICOSTRUZIONE_PUBBLICA)) {
						if (richiedente.getNomeRichiedente() == null) {
							richiedente.setNomeRichiedente(" ");
						}
					}
				}
				
				if (richiedente.getCognomeRichiedente() == null) {
					String msg = messageSource.getMessage("cognome.richiedente.null", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("procedure.applicant.surname", msg));
				}
				if (richiedente.getRiferimentoIstanza() == null) {
					String msg = messageSource.getMessage("riferimento.istanza.null", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("procedure.applicant.requestReference", msg));
				}
				if (richiedente.getCodiceFiscaleRichiedente() == null) {
					String msg = messageSource.getMessage("cf.richiedente.null", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("procedure.applicant.fiscalCode", msg));
				}
				if (richiedente.getDataAvvio() == null) {
					String msg = messageSource.getMessage("data.avvio.null", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("procedure.applicant.startDate", msg));
				}
				if (richiedente.getPec() == null) {
					String msg = messageSource.getMessage("pec.null", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("procedure.applicant.pec", msg));
				}
			} else {
				String msg = messageSource.getMessage("pratica.richiedente.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("procedure.applicant", msg));
			}
			if (pratica.getLocalizzazione() != null) {
				// Validazione localizzazione
				LocalizzazioneDTO localizzazione = pratica.getLocalizzazione();
				if (localizzazione.getProvincia() == null) {
					String msg = messageSource.getMessage("localizzazione.provincia.null", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("procedure.locale.province", msg));
				}
				if (localizzazione.getComune() == null) {
					String msg = messageSource.getMessage("localizzazione.comune.null", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("procedure.locale.city", msg));
				}
			} else {
				String msg = messageSource.getMessage("pratica.localizzazione.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("procedure.locale", msg));
			}
		} else {
			String msg = messageSource.getMessage("pratica.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("procedure", msg));
		}
	}

	private void validateDefinizioneDTO(DefinizioneDTO definizione, MessageSource messageSource, List<Errore> errors) {
		if (definizione != null) {
			if (definizione.getIstanza() != null) {
				// Validazione istanza
				IstanzaDTO istanza = definizione.getIstanza();
				if (istanza.getTipologiaConferenza() != null) {
					
					TipologiaConferenza tipoConferenza = null;
					Optional<TipologiaConferenzaSpecializzazione> tipoConfSpecializzata = this.tipConfSpecRepo.findById(istanza.getTipologiaConferenza().getKey());
					if(tipoConfSpecializzata.isPresent()) {
						tipoConferenza = tipoConfSpecializzata.get().getTipologiaConferenza();
					}else {
						tipoConferenza = this.tipoConfRepo.findById(istanza.getTipologiaConferenza().getKey()).get();
					}
					List<EventoCalendarioDataObbligatoria> listaDateObbligatorie = this.dataObblRepo.findByTipologiaConferenza(tipoConferenza);
					
					if (istanza.getTermineEspressionePareri() == null) {
						EventoCalendarioDataObbligatoria dataObbl = listaDateObbligatorie.stream()
								.filter(d -> d.getCampoErrore().equals(DbConst.CAMPO_ERRORE_ESPRESSIONE_PARERI))
								.collect(Collectors.toList()).get(0);
						if (dataObbl.getFlagObbligatorio()) {
							String msg = messageSource.getMessage(dataObbl.getMessaggioErrore(), null,
									request.getLocale());
							errors.add(Errore.getErrorSingleField(dataObbl.getCampoErrore(), msg));
						}
					}
					
					if (istanza.getTermineRichiestaIntegrazioniConferenza() == null) {
						EventoCalendarioDataObbligatoria dataObbl = listaDateObbligatorie.stream()
								.filter(d -> d.getCampoErrore().equals(DbConst.CAMPO_ERRORE_TERMINE_RICHIESTA_INTEGRAZIONI))
								.collect(Collectors.toList()).get(0);
						if (dataObbl.getFlagObbligatorio()) {
							String msg = messageSource.getMessage(dataObbl.getMessaggioErrore(), null,
									request.getLocale());
							errors.add(Errore.getErrorSingleField(dataObbl.getCampoErrore(), msg));
						}
					}
					
					if (istanza.getPrimaSessioneSimultanea() == null) {
						EventoCalendarioDataObbligatoria dataObbl = listaDateObbligatorie.stream()
								.filter(d -> d.getCampoErrore().equals(DbConst.CAMPO_ERRORE_SESSIONE_SIMULTANEA))
								.collect(Collectors.toList()).get(0);
						if (dataObbl.getFlagObbligatorio()) {
							String msg = messageSource.getMessage(dataObbl.getMessaggioErrore(), null,
									request.getLocale());
							errors.add(Errore.getErrorSingleField(dataObbl.getCampoErrore(), msg));
						}
					}
					
					if (istanza.getDataTermine() == null) {
						EventoCalendarioDataObbligatoria dataObbl = listaDateObbligatorie.stream()
								.filter(d -> d.getCampoErrore().equals(DbConst.CAMPO_ERRORE_DATA_TERMINE))
								.collect(Collectors.toList()).get(0);
						if (dataObbl.getFlagObbligatorio()) {
							String msg = messageSource.getMessage(dataObbl.getMessaggioErrore(), null,
									request.getLocale());
							errors.add(Errore.getErrorSingleField(dataObbl.getCampoErrore(), msg));
						}
					}
				}
			} else {
				String msg = messageSource.getMessage("definizione.istanza.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("definition.instance", msg));
			}
		} else {
			String msg = messageSource.getMessage("definizione.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("definition", msg));
		}

	}

	private void validatePartecipantiDTO(List<PartecipanteDTO> partecipanti, MessageSource messageSource,
			List<Errore> errors) {
		if (partecipanti != null) {
			// Validazione Ente
			for (PartecipanteDTO partecipante : partecipanti) {
				validatePartecipanteDTO(partecipante, messageSource, errors);
			}
		}

	}

	public void validatePartecipanteDTO(PartecipanteDTO partecipanteDTO, MessageSource messageSource,
			List<Errore> errors) {
		if (partecipanteDTO.getEnte() == null) {
			String msg = messageSource.getMessage("partecipante.attore.ente.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.actor.company", msg));
		}
		if (partecipanteDTO.getRuolo() == null) {
			String msg = messageSource.getMessage("partecipante.attore.ruolo.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.actor.role", msg));
		}
		if (partecipanteDTO.getDescEnte() == null) {
			String msg = messageSource.getMessage("partecipante.attore.descEnte.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.actor.companyDescription", msg));
		}
		if (partecipanteDTO.getPec() == null) {
			String msg = messageSource.getMessage("partecipante.attore.pec.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.actor.pec", msg));
		}
		if (partecipanteDTO.getCf() == null) {
			String msg = messageSource.getMessage("partecipante.attore.cf.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.actor.fiscalCode", msg));
		}
	}

	public List<Errore> validateCreaPartecipante(PartecipanteDTO partecipanteDTO, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		validatePartecipanteDTO(partecipanteDTO, messageSource, errors);
		return errors;
	}

	public void validateStatoPartecipante(Conferenza conferenza) {
		int stato = Integer.parseInt(conferenza.getStato().getCodice());
		if (stato == DbConst.STATO_VALUTAZIONE || stato == DbConst.STATO_CHIUSA || stato == DbConst.STATO_ARCHIVIATA) {
			throw new NotEditableException("The Participant can't be edited");
		}
	}

	public void validateStatoConferenza(Conferenza conferenza) {
		int stato = Integer.parseInt(conferenza.getStato().getCodice());
		if (stato != DbConst.STATO_COMPILAZIONE && stato != DbConst.STATO_BOZZA) {
			throw new NotEditableException("The conference can't be edited");
		}
	}

	public List<Errore> validatePersonaRuoloConferenzaDTO(PersonaRuoloConferenzaDTO personaRuoloDTO,
			MessageSource messageSource, List<Errore> errors) {
		if (personaRuoloDTO.getNome() == null) {
			String msg = messageSource.getMessage("partecipante.ruoloPersona.nome.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.user.name", msg));
		}
		if (personaRuoloDTO.getCognome() == null) {
			String msg = messageSource.getMessage("partecipante.ruoloPersona.nome.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.user.surname", msg));
		}
		if (personaRuoloDTO.getCodiceFiscale() == null) {
			String msg = messageSource.getMessage("partecipante.ruoloPersona.codiceFiscale.null", null,
					request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.user.fiscalCode", msg));
		}
		if (personaRuoloDTO.getProfilo() == null) {
			String msg = messageSource.getMessage("partecipante.ruoloPersona.profilo.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("participant.user.profilo", msg));
		}

		return errors;
	}
	
	public Conferenza ConfEsistenza (Integer id) {
		Conferenza conferenza = this.confRepo.findByIdConferenza(id);
		if (conferenza == null) {
			throw new NotFoundEx("la conferenza non e' stata trovata");
		}
		return conferenza;
	}

	public void validateTypeConferenceSpecialization(String type) {
		if (type.isEmpty() || type == null) {
			throw new NotFoundEx("La tipologia conferenza specializzazione è obbligatoria");
		}	
		TipologiaConferenzaSpecializzazione tipoConfSpec = tipConfSpecRepo.findById(type).orElse(null);
		if (tipoConfSpec == null) {
			throw new NotFoundEx("la tipologia conferenza non esiste");
		}
	}

	public void validateCreaAccreditamentoUnico(Partecipante partecipante, String codiceFiscale) {
		for (Accreditamento accreditamento: partecipante.getAccreditati()) {
			if (accreditamento.getPersona().getCodiceFiscale().equals(codiceFiscale)) {
				throw new NotEditableException("Utente gia' associato a questo partecipante");
			}
		}
	}

}
