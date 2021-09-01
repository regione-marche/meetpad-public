package conferenza.builder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.ConferenzaAnteprimaDTO;
import conferenza.DTO.ConferenzaCompletaDTO;
import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.ConferenzaModificaRichiedenteDTO;
import conferenza.DTO.ContattoSupportoDTO;
import conferenza.DTO.DefinizioneDTO;
import conferenza.DTO.DeterminazioneDTO;
import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.ImpresaDTO;
import conferenza.DTO.IndirizzoSessioneSimultaneaDTO;
import conferenza.DTO.IstanzaDTO;
import conferenza.DTO.LocalizzazioneDTO;
import conferenza.DTO.ModificaDataDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.PersonaRuoloConferenzaDTO;
import conferenza.DTO.PraticaDTO;
import conferenza.DTO.RicercaAvanzataDTO;
import conferenza.DTO.RichiedenteDTO;
import conferenza.model.Accreditamento;
import conferenza.model.Comune;
import conferenza.model.Conferenza;
import conferenza.model.ContattoSupporto;
import conferenza.model.Documento;
import conferenza.model.Ente;
import conferenza.model.FormaGiuridica;
import conferenza.model.Impresa;
import conferenza.model.ModificaData;
import conferenza.model.Provincia;
import conferenza.model.Regione;
import conferenza.model.RicercaConferenza;
import conferenza.model.RubricaImprese;
import conferenza.model.Stato;
import conferenza.model.TipologiaConferenzaSpecializzazione;
import conferenza.repository.AttivitaRepository;
import conferenza.repository.AzioneRepository;
import conferenza.repository.ComuneRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.FormaGiuridicaRepository;
import conferenza.repository.ImpresaRepository;
import conferenza.repository.ModalitaRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.ProvinciaRepository;
import conferenza.repository.RegioneRepository;
import conferenza.repository.RubricaImpreseRepository;
import conferenza.repository.StatoRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.repository.TipologiaPraticaRepository;
import conferenza.service.ParticipantService;
import conferenza.service.PermessoService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;

@Component
public class ConferenzaBuilder extends _BaseBuilder {

	@Autowired
	TipologiaConferenzaSpecializzazioneRepository tipConfSpecRepo;

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
	PermessoService permessoService;

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
	
	@Autowired
	RubricaImpreseRepository rubricaImprRepo;
	
	@Autowired
	EnteBuilder enteBuilder;
	
	/**
	 * Nei processi di creazione della conferenza asincroni il CF
	 * non arriva dalla richiesta!
	 * @param conferenzaDTO
	 * @param idStato
	 * @return
	 */
	public Conferenza buildConferenza(ConferenzaDTO conferenzaDTO, Integer idStato) {		
		String codiceFiscaleCreatore =null;
		if(conferenzaDTO.getCodiceFiscaleCreatore()!=null)
			codiceFiscaleCreatore = conferenzaDTO.getCodiceFiscaleCreatore();
		else
			codiceFiscaleCreatore = utenteService.getCodiceFiscaleUtente().toUpperCase();		
		Conferenza conferenza=buildConferenza(conferenzaDTO, idStato,codiceFiscaleCreatore);
		return conferenza;
	}
	/**
	 * Crea un oggetto Conferenza a partire da un DTO
	 * 
	 * @author vindeluca
	 * @param conferenzaDTO
	 * @param idStato
	 * @param codiceFiscaleCreatore
	 * @return
	 */
	private Conferenza buildConferenza(ConferenzaDTO conferenzaDTO, Integer idStato,String codiceFiscaleCreatore) {
		Conferenza conferenza;
		if (conferenzaDTO.getId() == null) {
			conferenza = new Conferenza();
			conferenza.setCodiceFiscaleCreatoreConferenza(codiceFiscaleCreatore);
		} else {
			conferenza = confRepo.findByIdConferenza(conferenzaDTO.getId());
		}
		conferenza.setStep(conferenzaDTO.getStep());
		conferenza.setGeomapGuid(conferenzaDTO.getGeomapGuid());
		if (conferenzaDTO.getFlagAbilitaModificaRichiedente() != null) {
			conferenza.setFlagAbilitaModificaRichiedente(conferenzaDTO.getFlagAbilitaModificaRichiedente());
		}
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
			
			if(localizzazioneDTO.getProvincia() != null)
				conferenza.setLocalizzazioneProvincia(provRepo.findById(localizzazioneDTO.getProvincia().getKey()).orElse(null));
			
			if(localizzazioneDTO.getComune() != null)
				conferenza.setLocalizzazioneComune(comuneRepo.findById(localizzazioneDTO.getComune().getKey()).orElse(null));
			
			conferenza.setLocalizzazioneIndirizzo(localizzazioneDTO.getIndirizzo());
			ImpresaDTO impresaDTO = conferenzaDTO.getPratica().getCompagnia();
			if (impresaDTO != null) {
				Impresa impresa = buildImpresa(impresaDTO);
				conferenza.setImpresaDenominazione(impresaDTO.getDenominazione());
				if (impresaDTO.getCf() != null && !impresaDTO.getCf().isEmpty()) {
					conferenza.setImpresaCodiceFiscale(impresaDTO.getCf().toUpperCase());					
				}
				conferenza.setImpresaPartitaIva(impresaDTO.getPartitaIva());
				conferenza.setImpresaIndirizzo(impresaDTO.getIndirizzo());
				if (impresaDTO.getFormaGiuridica() != null) {
					FormaGiuridica formaGiuridica = formaGiuridicaRepo.findById(impresaDTO.getFormaGiuridica().getKey()).orElse(null);
					conferenza.setImpresaFormaGiuridica(formaGiuridica);					
				}
				if (impresaDTO.getRegione() != null) {
					Regione regione = regioneRepo.findById(impresaDTO.getRegione().getKey()).orElse(null);
					conferenza.setImpresaRegione(regione);					
				}
				if (impresaDTO.getProvincia() != null) {
					Provincia provincia = provRepo.findById(impresaDTO.getProvincia().getKey()).orElse(null);
					conferenza.setImpresaProvincia(provincia);					
				}
				if (impresaDTO.getComune() != null) {
					Comune comune = comuneRepo.findById(impresaDTO.getComune().getKey()).orElse(null);
					conferenza.setImpresaComune(comune);
				}
				Impresa saved = impresaRepo.save(impresa);
				conferenza.setImpresa(saved);
			}
		}

		if (conferenzaDTO.getDefinizione() != null) {
			if (conferenzaDTO.getDefinizione().getDeterminazione() != null) {
				conferenza.setOggettoDeterminazione(
						conferenzaDTO.getDefinizione().getDeterminazione().getOggettoDeterminazione());
			}
			// Istanza
			IstanzaDTO istanzaDTO = conferenzaDTO.getDefinizione().getIstanza();
			if (istanzaDTO.getRifIstanza() != null && !istanzaDTO.getRifIstanza().isEmpty()) {
				conferenza.setRiferimentoIstanza(istanzaDTO.getRifIstanza());
			}
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
			conferenza.setTipologiaConferenzaSpecializzazione(
					tipConfSpecRepo.findById(istanzaDTO.getTipologiaConferenza().getKey()).orElse(null));
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
		conferenzaDTO.setGeomapGuid(conferenza.getGeomapGuid());
		conferenzaDTO.setFoglioMappale(conferenza.getFoglioMappale());
		conferenzaDTO.setStato(createNotNullLabelValue(conferenza.getStato()));
		conferenzaDTO.setFlagAbilitaModificaRichiedente(conferenza.getFlagAbilitaModificaRichiedente());
		if(conferenza.getConferenzaPadre() != null)
			conferenzaDTO.setClonedFromId(""+conferenza.getConferenzaPadre().getIdConferenza());

		String cfUtente = utenteService.getAuthenticatedUser(/*xmf: do not use getAuthenticatedUserAsResponsibleOfConference here!*/).getCodiceFiscale();
		boolean isImpersonatedAdmin = !conferenza.getCodiceFiscaleCreatoreConferenza().equals(cfUtente);
		conferenzaDTO.setImpersonatedAdmin(false);

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
		istanzaDTO.setTipologiaConferenza(createNotNullLabelValue(conferenza.getTipologiaConferenzaSpecializzazione()));
		
		/*
		if(conferenza.getModificaData() != null && conferenza.getModificaData().size() > 0) {
			
			conferenza.getModificaData().forEach( modificaData -> {
				istanzaDTO.addListaModificaData( this.buildModificaDataDTO(modificaData) );
			});
		}
		*/
		
		definizioneDTO.setIstanza(istanzaDTO);

		conferenzaDTO.setDefinizione(definizioneDTO);

		/*
		 * Documenti
		 */
		//conferenzaDTO.setDocumentazione(buildDocumentazioneDTO(conferenza, false));

		return conferenzaDTO;
	}
	
	public ModificaDataDTO buildModificaDataDTO(ModificaData modificaData) {
		ModificaDataDTO modificaDataDTO = new ModificaDataDTO();	
		getMapper().map(modificaData, modificaDataDTO);
		modificaDataDTO.setCodice(modificaData.getTipoData().getCodice());
		modificaDataDTO.setDescrizone(modificaData.getTipoData().getDescrizione());
			
		return modificaDataDTO;
	}
	
	public void checkVisibilitaDocumenti (List<DocumentoDTO> listaDocumenti, Documento documento, Boolean allDocuments) {
		if (allDocuments == null) {
			allDocuments = false;
		}
		Accreditamento accreditamento = this.utenteService.getAccreditamento(documento.getConferenza(), true);
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
			conferenza.setTipologiaConferenzaSpecializzazione(
					tipConfSpecRepo.findById(ricercaAvanzataDTO.getTipologiaConferenza()).orElse(null));
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
		conferenzaAnteprimaDTO.setTipologiaConferenza(createNotNullLabelValue(conferenza.getTipologiaConferenzaSpecializzazione()));
		conferenzaAnteprimaDTO.setTermineProcedimento(dateToString(conferenza.getDataTermine()));
		conferenzaAnteprimaDTO.setStato(createNotNullLabelValue(conferenza.getStato()));
		if(conferenza.getConferenzaPadre() != null && conferenza.getConferenzaPadre().getIdConferenza() != null)
			conferenzaAnteprimaDTO.setIdParent(""+conferenza.getConferenzaPadre().getIdConferenza());
		PersonaRuoloConferenzaDTO ruoloDTO = this.utenteService.findPersonaRuoloConferenza(conferenza.getIdConferenza());
		if (ruoloDTO != null) {
			conferenzaAnteprimaDTO.setProfilo(ruoloDTO.getProfilo());
		}
		if (conferenza.getAmministrazioneProcedente() != null) {
			conferenzaAnteprimaDTO.setAmministrazioneProcedente(
					enteBuilder.enteToEnteDTO(conferenza.getAmministrazioneProcedente()));
		}
		if (conferenza.getImpresaDenominazione() != null ) {
			Integer tipoConferenza = Integer.parseInt(conferenza.getTipologiaConferenzaSpecializzazione().getTipologiaConferenza().getCodice());
			if(tipoConferenza != DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_BUL_DECISORIA && tipoConferenza != DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_BUL_PREISTRUTTORIA)
				conferenzaAnteprimaDTO.setDenominazione(conferenza.getImpresaDenominazione());
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
		
		conferenzaAnteprimaDTO.setCanEdit(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_EDIT, conferenza.getIdConferenza()));
		conferenzaAnteprimaDTO.setCanDelete(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_DELETE, conferenza.getIdConferenza()));
		conferenzaAnteprimaDTO.setCanClone(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_CLONE, conferenza.getIdConferenza()));
		conferenzaAnteprimaDTO.setCanSkype(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_SKYPE, conferenza.getIdConferenza()));

		return conferenzaAnteprimaDTO;
	}

	public Impresa buildImpresa(ImpresaDTO impresaDTO) {
		Impresa impresa = null;
		if (impresaDTO.getPartitaIva() != null && !impresaDTO.getPartitaIva().isEmpty()) {
			impresa = impresaRepo.findByPartitaIvaIgnoreCase(impresaDTO.getPartitaIva()).orElse(null);
		} else {
			if (impresaDTO.getCf() != null && !impresaDTO.getCf().isEmpty()) {
				String codiceFiscale = impresaDTO.getPartitaIva();
				if(codiceFiscale == null)
					codiceFiscale = impresaDTO.getCf();
				
				impresa = impresaRepo.findByCodiceFiscaleIgnoreCase(codiceFiscale).orElse(null);
			}
		}
		if (impresa == null) {
			impresa = new Impresa();
			impresa.setDenominazione(impresaDTO.getDenominazione());
			if (impresaDTO.getCf() != null) {
				impresa.setCodiceFiscale(impresaDTO.getCf().toUpperCase());
			}
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
		TipologiaConferenzaSpecializzazione tipoConfSpec = this.tipConfSpecRepo.findById(ric.getCodiceTipologiaConferenza())
				.orElse(null);
		if (tipoConfSpec != null)
			conferenzaAnteprimaDTO.setTipologiaConferenza(createNotNullLabelValue(tipoConfSpec));
		conferenzaAnteprimaDTO.setTermineProcedimento(dateToString(ric.getDataTermine()));
		Stato stato = this.statoRepo.findById(ric.getCodiceStato()).orElse(null);
		if (stato != null)
			conferenzaAnteprimaDTO.setStato(createNotNullLabelValue(stato));
		if (ric.getDescrizioneAmmProcedente() != null) {
			Ente amministrazioneProcedente = this.enteRepo.findById(ric.getIdEnte())
					.orElse(null);
			if (amministrazioneProcedente != null)
				conferenzaAnteprimaDTO
						.setAmministrazioneProcedente(enteBuilder.enteToEnteDTO(amministrazioneProcedente));
		}
		PersonaRuoloConferenzaDTO ruoloDTO = this.utenteService.findPersonaRuoloConferenza(ric.getIdConferenza());
		if (ruoloDTO != null) {
			conferenzaAnteprimaDTO.setProfilo(ruoloDTO.getProfilo());
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
		
		conferenzaAnteprimaDTO.setCanEdit(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_EDIT, ric.getIdConferenza()));
		conferenzaAnteprimaDTO.setCanDelete(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_DELETE, ric.getIdConferenza()));
		conferenzaAnteprimaDTO.setCanClone(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_CLONE, ric.getIdConferenza()));
		conferenzaAnteprimaDTO.setCanSkype(permessoService.isAbilitato(DbConst.PERMESSO_AZIONE_CONFERENZA_SKYPE, ric.getIdConferenza()));
		
		return conferenzaAnteprimaDTO;
	}

	public ConferenzaModificaRichiedenteDTO buildConferenzaModificaRichiedenteDTO(Conferenza conferenza) {
		ConferenzaModificaRichiedenteDTO conferenzaModificaRichiedenteDTO = new ConferenzaModificaRichiedenteDTO();
		Boolean modificaAbilitata = false;
		Boolean stepModificabiliPerTipoEImpresa = false;
		if (conferenza.getImpresa() != null) {
			RubricaImprese rubricaImprese = rubricaImprRepo
					.findByTipologiaConferenzaSpecializzazioneAndImpresa(conferenza.getTipologiaConferenzaSpecializzazione(), conferenza.getImpresa()).orElse(null);
			if (rubricaImprese != null) {
				boolean conferenzaAbilitataModifiche = conferenza.getFlagAbilitaModificaRichiedente() != null && conferenza.getFlagAbilitaModificaRichiedente();
				Accreditamento accreditamento = utenteService.getAccreditamento(conferenza, true);
				boolean richiedente = accreditamento != null && accreditamento.isRichiedente();
				stepModificabiliPerTipoEImpresa = rubricaImprese.getStepConferenzaModificabili();
				
				modificaAbilitata = richiedente && stepModificabiliPerTipoEImpresa && conferenzaAbilitataModifiche;
												
				if (rubricaImprese.getListaStepConferenzaModificabili() != null) {
					String[] steps = rubricaImprese.getListaStepConferenzaModificabili().split(",");
					for (String step : steps) {
						conferenzaModificaRichiedenteDTO.getStepAttivi().add(Integer.parseInt(step));
					}
				}
			}
		}
		conferenzaModificaRichiedenteDTO.setConferenzaEditabile(stepModificabiliPerTipoEImpresa);
		conferenzaModificaRichiedenteDTO.setModificaAbilitata(modificaAbilitata);
		
		return conferenzaModificaRichiedenteDTO;
	}
	
	public static void main(String[] args) {
		String[] steps = "1,3".split(",");
		for (String step : steps) {
			System.out.println(Integer.parseInt(step));
		}
	}
}
