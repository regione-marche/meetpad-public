package cdst_be_marche.builder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cdst_be_marche.DTO.ConferenzaAnteprimaDTO;
import cdst_be_marche.DTO.ConferenzaCompletaDTO;
import cdst_be_marche.DTO.ConferenzaDTO;
import cdst_be_marche.DTO.ContattoSupportoDTO;
import cdst_be_marche.DTO.DefinizioneDTO;
import cdst_be_marche.DTO.DeterminazioneDTO;
import cdst_be_marche.DTO.DocumentazioneDTO;
import cdst_be_marche.DTO.DocumentoDTO;
import cdst_be_marche.DTO.ImpresaDTO;
import cdst_be_marche.DTO.IndirizzoSessioneSimultaneaDTO;
import cdst_be_marche.DTO.IstanzaDTO;
import cdst_be_marche.DTO.ListaPartecipanteDTO;
import cdst_be_marche.DTO.LocalizzazioneDTO;
import cdst_be_marche.DTO.PersonaDTO;
import cdst_be_marche.DTO.PersonaRuoloConferenzaDTO;
import cdst_be_marche.DTO.PraticaDTO;
import cdst_be_marche.DTO.RicercaAvanzataDTO;
import cdst_be_marche.DTO.RichiedenteDTO;
import cdst_be_marche.exception.NotFoundEx;
import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.ContattoSupporto;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Ente;
import cdst_be_marche.model.Evento;
import cdst_be_marche.model.Impresa;
import cdst_be_marche.model.RicercaConferenza;
import cdst_be_marche.model.Stato;
import cdst_be_marche.model.TipologiaConferenza;
import cdst_be_marche.repository.AttivitaRepository;
import cdst_be_marche.repository.AzioneRepository;
import cdst_be_marche.repository.ComuneRepository;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.EnteRepository;
import cdst_be_marche.repository.FormaGiuridicaRepository;
import cdst_be_marche.repository.ImpresaRepository;
import cdst_be_marche.repository.ModalitaRepository;
import cdst_be_marche.repository.PersonaRepository;
import cdst_be_marche.repository.ProvinciaRepository;
import cdst_be_marche.repository.RegioneRepository;
import cdst_be_marche.repository.StatoRepository;
import cdst_be_marche.repository.TipologiaConferenzaRepository;
import cdst_be_marche.repository.TipologiaPraticaRepository;
import cdst_be_marche.service.ParticipantService;
import cdst_be_marche.service.UtenteService;
import cdst_be_marche.util.DbConst;

@Component
public class ConferenzaBuilder extends _BaseBuilder {

	@Autowired
	TipologiaConferenzaRepository tipConfRepo;

	@Autowired
	TipologiaPraticaRepository tipPratRepo;

	@Autowired
	AttivitaRepository attivitaRepo;

	@Autowired
	AzioneRepository azioneRepo;

	@Autowired
	RegioneRepository regioneRepo;

	@Autowired
	ProvinciaRepository provRepo;

	@Autowired
	ComuneRepository comuneRepo;

	@Autowired
	StatoRepository statoRepo;

	@Autowired
	FormaGiuridicaRepository formaGiuridicaRepo;

	@Autowired
	ParticipantService participantService;

	@Autowired
	DocumentoBuilder documentoBuilder;

	@Autowired
	UtenteService utenteService;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	ConferenzaRepository confRepo;

	@Autowired
	PersonaRepository personaRepo;

	@Autowired
	ModalitaRepository modalitaRepo;

	@Autowired
	EventoBuilder eventoBuilder;

	@Autowired
	ImpresaRepository impresaRepo;
	
	@Autowired
	EnteRepository enteRepo;
	
	/**
	 * Crea un oggetto Conferenza a partire da un DTO
	 * 
	 * @author vindeluca
	 * @param conferenzaDTO
	 * @return
	 */
	public Conferenza buildConferenza(ConferenzaDTO conferenzaDTO, Integer idStato) {
		Conferenza conferenza;
		if (conferenzaDTO.getId() == null) {
			conferenza = new Conferenza();
			conferenza.setCodiceFiscaleCreatoreConferenza(utenteService.getCodiceFiscaleUtente().toUpperCase());
		} else {
			conferenza = confRepo.findByIdConferenza(conferenzaDTO.getId());
		}
		conferenza.setStep(conferenzaDTO.getStep());
		/*
		 * il responsabile della conferenza è impostato nella POST del partecipante
		 */
		// conferenza.setCodiceFiscaleResponsabileConferenza(utenteService.getCodiceFiscaleResponsabileConferenza(conferenza));

		if (idStato != null) {
			conferenza.setStato(statoRepo.findById(idStato.toString()).orElse(null));
		}
		if (conferenzaDTO.getPratica() != null) {
			// Richiedente
			RichiedenteDTO richiedenteDTO = conferenzaDTO.getPratica().getRichiedente();
			getMapper().map(richiedenteDTO, conferenza);
			if (richiedenteDTO.getTipologia() != null) {
				conferenza
						.setTipologiaPratica(tipPratRepo.findById(richiedenteDTO.getTipologia().getKey()).orElse(null));
			}
			if (richiedenteDTO.getAttivita() != null) {
				conferenza.setAttivita(attivitaRepo.findById(richiedenteDTO.getAttivita().getKey()).orElse(null));
			}
			if (richiedenteDTO.getAzione() != null) {
				conferenza.setAzione(azioneRepo.findById(richiedenteDTO.getAzione().getKey()).orElse(null));
			}
			// Localizzazione
			LocalizzazioneDTO localizzazioneDTO = conferenzaDTO.getPratica().getLocalizzazione();
			conferenza.setLocalizzazioneProvincia(
					provRepo.findById(localizzazioneDTO.getProvincia().getKey()).orElse(null));
			conferenza
					.setLocalizzazioneComune(comuneRepo.findById(localizzazioneDTO.getComune().getKey()).orElse(null));
			conferenza.setLocalizzazioneIndirizzo(localizzazioneDTO.getIndirizzo());
			ImpresaDTO impresaDTO = conferenzaDTO.getPratica().getCompagnia();
			if (impresaDTO != null) {
				conferenza.setImpresaDenominazione(impresaDTO.getDenominazione());
				conferenza.setImpresaCodiceFiscale(impresaDTO.getCf().toUpperCase());
				conferenza.setImpresaPartitaIva(impresaDTO.getPartitaIva());
				conferenza.setImpresaIndirizzo(impresaDTO.getIndirizzo());
				if (impresaDTO.getFormaGiuridica() != null) {
					conferenza.setImpresaFormaGiuridica(
							formaGiuridicaRepo.findById(impresaDTO.getFormaGiuridica().getKey()).orElse(null));
				}
				if (impresaDTO.getRegione() != null) {
					conferenza.setImpresaRegione(regioneRepo.findById(impresaDTO.getRegione().getKey()).orElse(null));
				}
				if (impresaDTO.getProvincia() != null) {
					conferenza.setImpresaProvincia(provRepo.findById(impresaDTO.getProvincia().getKey()).orElse(null));
				}
				if (impresaDTO.getComune() != null) {
					conferenza.setImpresaComune(comuneRepo.findById(impresaDTO.getComune().getKey()).orElse(null));
				}
			}
		}

		if (conferenzaDTO.getDefinizione() != null) {
			conferenza.setOggettoDeterminazione(
					conferenzaDTO.getDefinizione().getDeterminazione().getOggettoDeterminazione());
			// Istanza
			IstanzaDTO istanzaDTO = conferenzaDTO.getDefinizione().getIstanza();
			getMapper().map(istanzaDTO, conferenza);
			IndirizzoSessioneSimultaneaDTO indirizzoDTO = istanzaDTO.getIndirizzo_sessione_simultanea();
			if (indirizzoDTO != null) {
				conferenza.setIndirizzoSessioneSimultanea(indirizzoDTO.getRiferimento());
				if (indirizzoDTO.getModalita() != null) {
					conferenza.setModalita(modalitaRepo.findById(indirizzoDTO.getModalita().getKey()).orElse(null));
				}
				conferenza.setCapSessioneSimultanea(indirizzoDTO.getCap());
				if (indirizzoDTO.getComune() != null) {
					conferenza.setComuneSessioneSimultanea(
							comuneRepo.findById(indirizzoDTO.getComune().getKey()).orElse(null));
				}
				if (indirizzoDTO.getProvincia() != null) {
					conferenza.setProvinciaSessioneSimultanea(
							provRepo.findById(indirizzoDTO.getProvincia().getKey()).orElse(null));
				}
			}
			conferenza.setTipologiaConferenza(
					tipConfRepo.findById(istanzaDTO.getTipologiaConferenza().getKey()).orElse(null));
		}

		return conferenza;
	}

	/**
	 * Crea un oggetto conferenzaDTO a partire da un oggetto Conferenza
	 * 
	 * @param conferenza
	 * @return
	 * 
	 * @author arosina
	 */
	public ConferenzaDTO buildConferenzaDTO(Conferenza conferenza) {
		ConferenzaDTO conferenzaDTO = new ConferenzaDTO();

		conferenzaDTO.setId(conferenza.getIdConferenza());
		conferenzaDTO.setStep(conferenza.getStep());
		conferenzaDTO.setStato(createNotNullLabelValue(conferenza.getStato()));
		/*
		 * PraticaDTO
		 */
		PraticaDTO praticaDTO = new PraticaDTO();
		// ---- RichiedenteDTO
		RichiedenteDTO richiedenteDTO = new RichiedenteDTO();
		getMapper().map(conferenza, richiedenteDTO);
		richiedenteDTO.setTipologia(createNotNullLabelValue(conferenza.getTipologiaPratica()));
		richiedenteDTO.setAttivita(createNotNullLabelValue((conferenza.getAttivita())));
		richiedenteDTO.setAzione(createNotNullLabelValue(conferenza.getAzione()));
		praticaDTO.setRichiedente(richiedenteDTO);
		// ---- ImpresaDTO
		ImpresaDTO impresaDTO = new ImpresaDTO(); 
		impresaDTO.setDenominazione(conferenza.getImpresaDenominazione());
		impresaDTO.setCf(conferenza.getImpresaCodiceFiscale());
		impresaDTO.setPartitaIva(conferenza.getImpresaPartitaIva());
		impresaDTO.setIndirizzo(conferenza.getImpresaIndirizzo());
		impresaDTO.setFormaGiuridica(createNotNullLabelValue(conferenza.getImpresaFormaGiuridica()));
		impresaDTO.setRegione(createNotNullLabelValue(conferenza.getImpresaRegione()));
		impresaDTO.setProvincia(createNotNullLabelValue(conferenza.getImpresaProvincia()));
		impresaDTO.setComune(createNotNullLabelValue(conferenza.getImpresaComune()));
		praticaDTO.setCompagnia(impresaDTO);

		// ---- LocalizzazioneDTO
		LocalizzazioneDTO localizzazioneDTO = new LocalizzazioneDTO();
		localizzazioneDTO.setProvincia(createNotNullLabelValue(conferenza.getLocalizzazioneProvincia()));
		localizzazioneDTO.setComune(createNotNullLabelValue(conferenza.getLocalizzazioneComune()));
		localizzazioneDTO.setIndirizzo(conferenza.getLocalizzazioneIndirizzo());
		praticaDTO.setLocalizzazione(localizzazioneDTO);

		conferenzaDTO.setPratica(praticaDTO);

		/*
		 * ListaPartecipanteDTO
		 */
		/*ListaPartecipanteDTO listaPartecipanteDTO = new ListaPartecipanteDTO();
		listaPartecipanteDTO = this.participantService.findPartecipantiByIdConferenza(conferenza.getIdConferenza());
		conferenzaDTO.setPartecipanti(listaPartecipanteDTO.getList());*/

		/*
		 * DefinizioneDTO
		 */
		DefinizioneDTO definizioneDTO = new DefinizioneDTO();
		// ---- DeterminazioneDTO
		DeterminazioneDTO determinazioneDTO = new DeterminazioneDTO();
		determinazioneDTO.setOggettoDeterminazione(conferenza.getOggettoDeterminazione());
		definizioneDTO.setDeterminazione(determinazioneDTO);
		// ---- IstanzaDTO
		IstanzaDTO istanzaDTO = new IstanzaDTO();
		istanzaDTO.setRifIstanza(conferenza.getRiferimentoIstanza());
		getMapper().map(conferenza, istanzaDTO);
		istanzaDTO.setRifIstanza(conferenza.getRiferimentoIstanza());
		IndirizzoSessioneSimultaneaDTO indirizzo = new IndirizzoSessioneSimultaneaDTO();
		indirizzo.setRiferimento(conferenza.getIndirizzoSessioneSimultanea());
		indirizzo.setModalita(createNotNullLabelValue(conferenza.getModalita()));
		indirizzo.setCap(conferenza.getCapSessioneSimultanea());
		indirizzo.setComune(createNotNullLabelValue(conferenza.getComuneSessioneSimultanea()));
		indirizzo.setProvincia(createNotNullLabelValue(conferenza.getProvinciaSessioneSimultanea()));
		istanzaDTO.setIndirizzo_sessione_simultanea(indirizzo);
		istanzaDTO.setTipologiaConferenza(createNotNullLabelValue(conferenza.getTipologiaConferenza()));
		definizioneDTO.setIstanza(istanzaDTO);

		conferenzaDTO.setDefinizione(definizioneDTO);

		/*
		 * Documenti
		 */
		//conferenzaDTO.setDocumentazione(buildDocumentazioneDTO(conferenza, false));

		return conferenzaDTO;
	}

	public DocumentazioneDTO buildDocumentazioneDTO(Conferenza conferenza, Boolean allDocuments) {
		DocumentazioneDTO documentazioneDTO = new DocumentazioneDTO();
		if (conferenza != null) {
			for (Documento documento : conferenza.getDocumentiAggiuntivi()) {
				checkVisibilitaDocumenti(documentazioneDTO.getDocumentiAggiuntivi(), documento, allDocuments);
			}
			for (Documento documento : conferenza.getDocumentiInterazioni()) {
				checkVisibilitaDocumenti(documentazioneDTO.getDocumentiInterazione(), documento, allDocuments);
			}
			for (Documento documento : conferenza.getDocumentiIndizione()) {
				checkVisibilitaDocumenti(documentazioneDTO.getDocumentiIndizione(), documento, allDocuments);
			}
			for (Documento documento : conferenza.getDocumentiPreIstruttoria()) {
				checkVisibilitaDocumenti(documentazioneDTO.getDocumentiPreIstruttoria(), documento, allDocuments);
			}
			for (Documento documento : conferenza.getVerbaleRiunione()) {
				checkVisibilitaDocumenti(documentazioneDTO.getDocumentiInterazione(), documento, allDocuments);
			}
			for (Documento documento : conferenza.getDetermina()) {
				checkVisibilitaDocumenti(documentazioneDTO.getDocumentiInterazione(), documento, allDocuments);
			}
		}
		return documentazioneDTO;
	}
	
	public void checkVisibilitaDocumenti (List<DocumentoDTO> listaDocumenti, Documento documento, Boolean allDocuments) {
		if (allDocuments == null) {
			allDocuments = false;
		}
		Accreditamento accreditamento = this.utenteService.getAccreditamento(documento.getConferenza());
		if ((allDocuments && documento.getTipologiaDocumento().getCodice()
				.equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA)) || !documento.getVisibilitaRistretta()
				|| accreditamento.getPersona().getCodiceFiscale()
						.equals(documento.getConferenza().getCodiceFiscaleResponsabileConferenza())) {
			listaDocumenti.add(documentoBuilder.buildDocumentoDTO(documento));
		} else {
			Boolean visibilita = documento.getVisibilitaPartecipanti().stream()
					.anyMatch(p -> p.equals(accreditamento.getPartecipante()));
			if (visibilita)
				listaDocumenti.add(documentoBuilder.buildDocumentoDTO(documento));
		}

	}

	public ConferenzaCompletaDTO buildConferenzaCompletaDTO(Conferenza conferenza) {
		ConferenzaDTO conferenzaDTO = this.buildConferenzaDTO(conferenza);
		ConferenzaCompletaDTO conferenzaCompletaDTO = getMapper().map(conferenzaDTO, ConferenzaCompletaDTO.class);
		/*for (Evento evento : conferenza.getEventi()) {
			conferenzaCompletaDTO.getEventi().add(this.eventoBuilder.buildEventoCompletoDTO(evento));
		}*/
		return conferenzaCompletaDTO;
	}

	public Conferenza buildConferenzaFromRicerca(RicercaAvanzataDTO ricercaAvanzataDTO) {
		Conferenza conferenza = new Conferenza();
		if (ricercaAvanzataDTO.getStato() != null && !ricercaAvanzataDTO.getStato().isEmpty())
			conferenza.setStato(statoRepo.findById(ricercaAvanzataDTO.getStato()).orElse(null));
		if (ricercaAvanzataDTO.getTipologiaConferenza() != null
				&& !ricercaAvanzataDTO.getTipologiaConferenza().isEmpty())
			conferenza.setTipologiaConferenza(
					tipConfRepo.findById(ricercaAvanzataDTO.getTipologiaConferenza()).orElse(null));
		if (ricercaAvanzataDTO.getRiferimentoIstanza() != null && !ricercaAvanzataDTO.getRiferimentoIstanza().isEmpty())
			conferenza.setRiferimentoIstanza(ricercaAvanzataDTO.getRiferimentoIstanza());
		if (ricercaAvanzataDTO.getCfRichiedente() != null && !ricercaAvanzataDTO.getCfRichiedente().isEmpty())
			conferenza.setCodiceFiscaleRichiedente(ricercaAvanzataDTO.getCfRichiedente());
		if (ricercaAvanzataDTO.getProvincia() != null && !ricercaAvanzataDTO.getProvincia().isEmpty())
			conferenza.setLocalizzazioneProvincia(provRepo.findById(ricercaAvanzataDTO.getProvincia()).orElse(null));
		if (ricercaAvanzataDTO.getComune() != null && !ricercaAvanzataDTO.getComune().isEmpty())
			conferenza.setLocalizzazioneComune(comuneRepo.findById(ricercaAvanzataDTO.getComune()).orElse(null));
		return getMapper().map(conferenza, Conferenza.class);
	}

	public ConferenzaAnteprimaDTO buildConferenzaAnteprimaDTO(Conferenza conferenza) {
		ConferenzaAnteprimaDTO conferenzaAnteprimaDTO = new ConferenzaAnteprimaDTO();
		conferenzaAnteprimaDTO.setIdConferenza(conferenza.getIdConferenza());
		conferenzaAnteprimaDTO.setRiferimentoIstanza(conferenza.getRiferimentoIstanza());
		conferenzaAnteprimaDTO.setTipologiaConferenza(createNotNullLabelValue(conferenza.getTipologiaConferenza()));
		conferenzaAnteprimaDTO.setTermineProcedimento(dateToString(conferenza.getDataTermine()));
		conferenzaAnteprimaDTO.setStato(createNotNullLabelValue(conferenza.getStato()));
		PersonaRuoloConferenzaDTO ruoloDTO = this.utenteService.findPersonaRuoloConferenza(conferenza.getIdConferenza());
		if (ruoloDTO != null) {
			conferenzaAnteprimaDTO.setProfilo(ruoloDTO.getProfilo());
		}
		if (conferenza.getAmministrazioneProcedente() != null) {
			conferenzaAnteprimaDTO.setAmministrazioneProcedente(
					partecipanteBuilder.enteToEnteDTO(conferenza.getAmministrazioneProcedente()));
		}
		conferenzaAnteprimaDTO.setIndirizzoSessioneSimultanea(conferenza.getIndirizzoSessioneSimultanea());
		PersonaDTO personaDTO = new PersonaDTO();
		personaDTO.setNome(conferenza.getNomeRichiedente());
		personaDTO.setCognome(conferenza.getCognomeRichiedente());
		personaDTO.setCodiceFiscale(conferenza.getCodiceFiscaleRichiedente());
		conferenzaAnteprimaDTO.setRichiedente(personaDTO);
		conferenzaAnteprimaDTO
				.setTerminePerIntegrazione(dateToString(conferenza.getTermineRichiestaIntegrazioniConferenza()));
		conferenzaAnteprimaDTO.setTerminePerLaDetermina(dateToString(conferenza.getTermineEspressionePareri()));
		conferenzaAnteprimaDTO.setTermineProssimaSessione(dateToString(conferenza.getPrimaSessioneSimultanea()));
		return conferenzaAnteprimaDTO;
	}

	public Impresa buildImpresa(ImpresaDTO impresaDTO) {
		Impresa impresa;
		if (this.impresaRepo.findByCodiceFiscaleOrPartitaIvaIgnoreCase(impresaDTO.getCf(), impresaDTO.getPartitaIva())
				.orElse(null) != null) {
			impresa = this.impresaRepo
					.findByCodiceFiscaleOrPartitaIvaIgnoreCase(impresaDTO.getCf(), impresaDTO.getPartitaIva()).get();
		} else {
			impresa = new Impresa();
		}
		impresa.setDenominazione(impresaDTO.getDenominazione());
		impresa.setCodiceFiscale(impresaDTO.getCf().toUpperCase());
		impresa.setPartitaIva(impresaDTO.getPartitaIva());
		impresa.setIndirizzo(impresaDTO.getIndirizzo());
		if (impresaDTO.getFormaGiuridica() != null) {
			impresa.setFormaGiuridica(
					formaGiuridicaRepo.findById(impresaDTO.getFormaGiuridica().getKey()).orElse(null));
		} else {
			impresa.setFormaGiuridica(null);
		}
		if (impresaDTO.getRegione() != null) {
			impresa.setRegione(regioneRepo.findById(impresaDTO.getRegione().getKey()).orElse(null));
		} else {
			impresa.setRegione(null);
		}
		if (impresaDTO.getProvincia() != null) {
			impresa.setProvincia(provRepo.findById(impresaDTO.getProvincia().getKey()).orElse(null));
		} else {
			impresa.setProvincia(null);
		}
		if (impresaDTO.getComune() != null) {
			impresa.setComune(comuneRepo.findById(impresaDTO.getComune().getKey()).orElse(null));
		} else {
			impresa.setComune(null);
		}
		return impresa;
	}

	public ImpresaDTO buildImpresaDTO(Impresa impresa) {
		ImpresaDTO impresaDTO = new ImpresaDTO();
		if (impresa != null) {
			impresaDTO.setId(impresa.getId());
			impresaDTO.setDenominazione(impresa.getDenominazione());
			if (impresa.getCodiceFiscale() != null) {
				impresaDTO.setCf(impresa.getCodiceFiscale().toUpperCase());
			}
			impresaDTO.setPartitaIva(impresa.getPartitaIva());
			impresaDTO.setIndirizzo(impresa.getIndirizzo());
			impresaDTO.setFormaGiuridica(createNotNullLabelValue(impresa.getFormaGiuridica()));
			impresaDTO.setRegione(createNotNullLabelValue(impresa.getRegione()));
			impresaDTO.setProvincia(createNotNullLabelValue(impresa.getProvincia()));
			impresaDTO.setComune(createNotNullLabelValue(impresa.getComune()));
		}
		return impresaDTO;
	}

	public ContattoSupporto buildContattoSupporto(ContattoSupportoDTO supportoDTO, ContattoSupporto contatto,
			Integer idConferenza) {
		ContattoSupporto supporto;
		if (contatto == null) {
			supporto = new ContattoSupporto();
			supporto.setConferenza(this.confRepo.findById(idConferenza).orElse(null));
		} else {
			supporto = contatto;
			supporto.setIdContattoSupporto(contatto.getIdContattoSupporto());
		}
		supporto.setNome(supportoDTO.getNome());
		supporto.setCognome(supportoDTO.getCognome());
		supporto.setEmail(supportoDTO.getEmail());
		supporto.setTelefono(supportoDTO.getTelefono());	
		return supporto;
	}

	public ContattoSupportoDTO buildContattoSupportoDTO(ContattoSupporto contatto) {
		ContattoSupportoDTO contattoDTO = new ContattoSupportoDTO();
		contattoDTO.setId(contatto.getIdContattoSupporto());
		contattoDTO.setNome(contatto.getNome());
		contattoDTO.setCognome(contatto.getCognome());
		contattoDTO.setEmail(contatto.getEmail());
		contattoDTO.setTelefono(contatto.getTelefono());
		return contattoDTO;
	}
	
	public ConferenzaAnteprimaDTO buildRicercaUnifyToConferenzaAnteprimaDTO(RicercaConferenza ric) {
		ConferenzaAnteprimaDTO conferenzaAnteprimaDTO = new ConferenzaAnteprimaDTO();
		conferenzaAnteprimaDTO.setIdConferenza(ric.getIdConferenza());
		conferenzaAnteprimaDTO.setRiferimentoIstanza(ric.getRiferimentoIstanza());
		TipologiaConferenza tipoConf = this.tipConfRepo.findById(ric.getCodiceTipologiaConferenza())
				.orElse(null);
		if (tipoConf != null)
			conferenzaAnteprimaDTO.setTipologiaConferenza(createNotNullLabelValue(tipoConf));
		conferenzaAnteprimaDTO.setTermineProcedimento(dateToString(ric.getDataTermine()));
		Stato stato = this.statoRepo.findById(ric.getCodiceStato()).orElse(null);
		if (stato != null)
			conferenzaAnteprimaDTO.setStato(createNotNullLabelValue(stato));
		if (ric.getDescrizioneAmmProcedente() != null) {
			Ente amministrazioneProcedente = this.enteRepo.findById(ric.getIdEnte())
					.orElse(null);
			if (amministrazioneProcedente != null)
				conferenzaAnteprimaDTO
						.setAmministrazioneProcedente(partecipanteBuilder.enteToEnteDTO(amministrazioneProcedente));
		}
		conferenzaAnteprimaDTO.setIndirizzoSessioneSimultanea(ric.getIndirizzoSessioneSimultanea());
		PersonaDTO personaDTO = new PersonaDTO();
		personaDTO.setNome(ric.getNomeRichiedente());
		personaDTO.setCognome(ric.getCognomeRichiedente());
		personaDTO.setCodiceFiscale(ric.getCodiceFiscaleRichiedente());
		conferenzaAnteprimaDTO.setRichiedente(personaDTO);
		conferenzaAnteprimaDTO
				.setTerminePerIntegrazione(dateToString(ric.getTermineRichiestaIntegrazioniConferenza()));
		conferenzaAnteprimaDTO.setTerminePerLaDetermina(dateToString(ric.getTermineEspressionePareri()));
		conferenzaAnteprimaDTO.setTermineProssimaSessione(dateToString(ric.getPrimaSessioneSimultanea()));
		return conferenzaAnteprimaDTO;
	}
}
