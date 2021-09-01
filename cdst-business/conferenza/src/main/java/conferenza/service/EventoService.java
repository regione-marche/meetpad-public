package conferenza.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EventoCompletoDTO;
import conferenza.DTO.EventoFileDTO;
import conferenza.DTO.ListaEventoDTO;
import conferenza.DTO.ListaPecDTO;
import conferenza.DTO.ModificaDataDTO;
import conferenza.DTO.PageableDTO;
import conferenza.DTO.PecDTO;
import conferenza.DTO.RicercaEventoDTO;
import conferenza.DTO.RicercaPecDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.builder.DocumentoFirmaMultiplaBuilder;
import conferenza.builder.EventoBuilder;
import conferenza.controller.ConferenceController;
import conferenza.exception.NotFoundEx;
import conferenza.facade.STATO_DOCUMENTO;
import conferenza.mail.EmailRepositoryService;
import conferenza.mail.JavaMailSenderConfigurator;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.CruscottoPec;
import conferenza.model.Documento;
import conferenza.model.DocumentoAttachment;
import conferenza.model.DocumentoFirmaMultipla;
import conferenza.model.Ente;
import conferenza.model.Evento;
import conferenza.model.EventoPartecipante;
import conferenza.model.ModificaData;
import conferenza.model.Partecipante;
import conferenza.model.RuoloPartecipante;
import conferenza.model.Stato;
import conferenza.model.TipoData;
import conferenza.model.TipoEvento;
import conferenza.model.Utente;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.CruscottoPecRepository;
import conferenza.repository.DocumentoAttachRepository;
import conferenza.repository.DocumentoFirmaMultiplaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EventoPartecipanteRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.EventoSpecificationsBuilder;
import conferenza.repository.ModificaDataRepository;
import conferenza.repository.OggettoEventoRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.RuoloPartecipanteRepository;
import conferenza.repository.SearchCriteria;
import conferenza.repository.StatoRepository;
import conferenza.repository.TipoDataRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.util.DbConst;
import conferenza.validator.ConferenceValidator;

@Transactional
@Service
public class EventoService extends _BaseService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConferenceController.class);

	@Autowired
	ConferenzaRepository confRepo;

	@Autowired
	EventoRepository eventoRepo;

	@Autowired
	ModificaDataRepository modificaDataRepo;

	@Autowired
	EventoBuilder eventoBuilder;

	@Autowired
	DocumentoRepository documentoRepo;

	@Autowired
	DocumentoAttachRepository documentoAttachRepo;

	@Autowired
	DocumentoService documentoService;

	@Autowired
	OggettoEventoRepository oggettoEventoRepo;

	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	UtenteService utenteService;

	@Autowired
	JavaMailSenderConfigurator mailer;

	@Autowired
	@Lazy
	EmailRepositoryService emailRepositoryService;

	@Autowired
	ConferenceValidator confValidator;

	@Autowired
	CruscottoPecRepository cruscottoRepo;
	
	@Autowired
	StatoRepository statoRepo;
	
	@Autowired
	PartecipanteRepository partRepo;
	
	@Autowired
	EventoPartecipanteRepository eventoPartRepo;
	
	@Autowired
	ConferenzaService confService;
	
	@Autowired
	EnteRepository enteRepo;
	
	@Autowired
	RuoloPartecipanteRepository ruoloPartRepo;
	
	@Autowired
	DocumentoFirmaMultiplaBuilder documentoFirmaMultiplaBuilder;

	@Autowired
	DocumentoFirmaMultiplaService documentoFirmaMultiplaService;
	
	
	@Transactional(readOnly = true)
	public ListaEventoDTO findAll(Integer id, PageableDTO pageableDTO) {
		Conferenza conferenza = this.confValidator.ConfEsistenza(id);
		PageRequest page = PageRequest.of(pageableDTO.getPagVisualizzata() - 1, pageableDTO.getNumRecordPag(),
				Direction.fromString(pageableDTO.getDirOrdinamento()), pageableDTO.getColonnaOrdinamento());
		Iterable<Evento> it = this.eventoRepo.findAll(filtroEventoPerVisibilita(null, conferenza), page);
		ListaEventoDTO lista = new ListaEventoDTO();
		for (Evento evento : it) {
			lista.getList().add(this.eventoBuilder.buildEventoDTO(evento));
		}
		lista.setTotalNumber(this.eventoRepo.count(filtroEventoPerVisibilita(null, conferenza)));
		return lista;

	}
	
	public ListaEventoDTO findEventsByFilter(Integer idConference, RicercaEventoDTO ricerca) {
		Conferenza conferenza = this.confService.findConferenzaByIdFiltrata(idConference);
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()), ricerca.getColonnaOrdinamento());
		Iterable<Evento> it = this.eventoRepo.findAll(filtroEvento(conferenza, ricerca), page);
		ListaEventoDTO lista = new ListaEventoDTO();
		for (Evento evento : it) {
			lista.getList().add(this.eventoBuilder.buildEventoDTO(evento));
		}
		lista.setTotalNumber(this.eventoRepo.count(filtroEvento(conferenza, ricerca)));
		return lista;
	}
	
	private Specification<Evento> filtroEvento(Conferenza conferenza, RicercaEventoDTO ricerca) {
		//Uguale al metodo "filtroEventoPerVisibilita"
		List<Partecipante> listaPartecipanti = this.utenteService.findAllPartecipantiPerUtente(conferenza);
		List<SearchCriteria> criteriaConferenza = new ArrayList<>();
		criteriaConferenza.add(new SearchCriteria("conferenza", "=", conferenza));
		List<SearchCriteria> criteriaMittente = creaCriteriaMittente(listaPartecipanti);
		List<SearchCriteria> criteriaVisibilitaExtra = creaCriteriaVisibilitaExtra(listaPartecipanti);
		List<SearchCriteria> criteriaDestinatari = creaCriteriaDestinatari(listaPartecipanti);
		List<SearchCriteria> criteriaIdEvento = new ArrayList<>();
		//xmfSE todo: to see all events the user must be replaced with the responsible
		Boolean isAdminProc = utenteService.getAuthenticatedUserAsResponsibleOfConference(conferenza).getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_AMMINISTRAZIONE_PROCEDENTE));
		EventoSpecificationsBuilder builder = new EventoSpecificationsBuilder();
		///////////////////////////////////////////////////////
		List<SearchCriteria> criteriaRicerca = creaCriteriaRicerca(ricerca, conferenza);
		List<SearchCriteria> criteriaRuoloPart = creaCriteriaRuoloPart(ricerca.getRuoloPartecipante(), conferenza);
		//filtro per amministrazione per pareri interni
		List<SearchCriteria> criteriaPerPareriInterni = creaCriteriaPerPareriInterni(conferenza);
		
		List<SearchCriteria> criteriaAccreditamentoEModificaRichiedente = creaCriteriaAccreditamentoEModificaRichiedente(conferenza);
		
		return builder.buildEventoSpecAll(criteriaConferenza, criteriaMittente, criteriaVisibilitaExtra,
				criteriaDestinatari, criteriaIdEvento, this.utenteService.isAdminAmministrazioni(),
				this.utenteService.getAuthenticatedUser().isCreatore(), isAdminProc, criteriaRicerca, criteriaRuoloPart,
				criteriaPerPareriInterni, criteriaAccreditamentoEModificaRichiedente);
	}

	private List<SearchCriteria> creaCriteriaRuoloPart(String ruoloPartecipante, Conferenza conferenza) {
		List<SearchCriteria> criteriaList = new ArrayList<>();
		if (ruoloPartecipante != null && !ruoloPartecipante.isEmpty()) {
			RuoloPartecipante ruolo = ruoloPartRepo.findById(ruoloPartecipante).orElse(null);
			List<Partecipante> listaPartecipanti = conferenza.getPartecipantes().stream()
					.filter(p -> p.getRuoloPartecipante().equals(ruolo)).collect(Collectors.toList());
			for(Partecipante part: listaPartecipanti) {
				criteriaList.add(new SearchCriteria("mittente", "=", part));
			}
			if(listaPartecipanti.size() == 0) {
				criteriaList = null;
			}
		}
		return criteriaList;
	}

	private List<SearchCriteria> creaCriteriaPerPareriInterni(Conferenza conferenza) {
		List<SearchCriteria> criteriaList = new ArrayList<>();
		Partecipante utentePartecipante = utenteService.getAccreditamento(conferenza, true).getPartecipante();
		//Boolean isAmministrazionePerPareriInterni = utentePartecipante.isAmministrazionePerPareriInterni();
		//if (!isAmministrazionePerPareriInterni) {
			List<Partecipante> listaMittenti = conferenza.getPartecipantes().stream()
					.filter(p -> p.getRuoloPartecipante().getCodice()
							.equals(Integer.toString(DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PER_PARERI_INTERNI)))
					.filter(p -> !p.getEnte().getCodiceFiscaleEnte().equals(utentePartecipante.getEnte().getCodiceFiscaleEnte()))
					.collect(Collectors.toList());
			listaMittenti.stream().forEach(m -> criteriaList.add(new SearchCriteria("mittente", "!=", m)));
		//}
		return criteriaList;
	}

	private List<SearchCriteria> creaCriteriaRicerca(RicercaEventoDTO ricerca, Conferenza conferenza) {
		List<SearchCriteria> criteriaList = new ArrayList<>();
		if (ricerca.getTipoEvento() != null && !ricerca.getTipoEvento().isEmpty()) {
			TipoEvento tipoEvento = tipoEventoRepo.findById(ricerca.getTipoEvento()).orElse(null);
			criteriaList.add(new SearchCriteria("tipoEvento", "=", tipoEvento));
		}
		if (ricerca.getEnte() != null && !ricerca.getEnte().isEmpty()) {
			Ente ente = enteRepo.findById(Integer.parseInt(ricerca.getEnte())).orElse(null);
			List<Partecipante> listaMittente = conferenza.getPartecipantes().stream().filter(p -> p.getEnte().equals(ente))
					.collect(Collectors.toList());
			if (listaMittente.size() != 0) {
				criteriaList.add(new SearchCriteria("mittente", "=", listaMittente.get(0)));
			} else {
				criteriaList.add(new SearchCriteria(null, "false", null));
			}
		}
		return criteriaList;
	}

	private Specification<Evento> filtroEventoPerVisibilita(Integer idEvento, Conferenza conferenza) {
		List<Partecipante> listaPartecipanti = this.utenteService.findAllPartecipantiPerUtente(conferenza);
		List<SearchCriteria> criteriaConferenza = new ArrayList<>();
		criteriaConferenza.add(new SearchCriteria("conferenza", "=", conferenza));
		List<SearchCriteria> criteriaMittente = creaCriteriaMittente(listaPartecipanti);
		List<SearchCriteria> criteriaVisibilitaExtra = creaCriteriaVisibilitaExtra(listaPartecipanti);
		List<SearchCriteria> criteriaDestinatari = creaCriteriaDestinatari(listaPartecipanti);
		
		List<SearchCriteria> criteriaAccreditamentoEModificaRichiedente = creaCriteriaAccreditamentoEModificaRichiedente(conferenza);
		
		List<SearchCriteria> criteriaIdEvento = new ArrayList<>();
		if (idEvento != null) {
			criteriaIdEvento.add(new SearchCriteria("id", "=", idEvento));
		}
		Boolean isAdminProc = utenteService.getAuthenticatedUser().getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_AMMINISTRAZIONE_PROCEDENTE));
		EventoSpecificationsBuilder builder = new EventoSpecificationsBuilder();
		return builder.buildEventoSpec(criteriaConferenza, criteriaMittente, criteriaVisibilitaExtra, criteriaDestinatari, criteriaIdEvento,
				this.utenteService.isAdminAmministrazioni(), this.utenteService.getAuthenticatedUser().isCreatore(), isAdminProc, criteriaAccreditamentoEModificaRichiedente);
	}

	private List<SearchCriteria> creaCriteriaAccreditamentoEModificaRichiedente(Conferenza conferenza) {
		List<SearchCriteria> criteriaList = new ArrayList<>();
		criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_ACCREDITAMENTO)).orElse(null)));
		criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONFERMA_ACCREDITAMENTO)).orElse(null)));
		criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_RIFIUTA_ACCREDITAMENTO)).orElse(null)));
		criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_INSERIMENTO_DOCUMENTAZIONE_CONDIVISA)).orElse(null)));
		criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CARICAMENTO_DOCUMENTO_FIRMA)).orElse(null)));
		Boolean isCreatoreOResponsabile = utenteService.getCodiceFiscaleUtente().equals(conferenza.getCodiceFiscaleCreatoreConferenza())
				|| utenteService.getCodiceFiscaleUtente().equals(conferenza.getCodiceFiscaleResponsabileConferenza());
		if (!isCreatoreOResponsabile) {
			criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo
					.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_ABILITA_MODIFICA_RICHIEDENTE)).orElse(null)));
			criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo
					.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_TERMINA_MODIFICA_RICHIEDENTE)).orElse(null)));
			criteriaList.add(new SearchCriteria("tipoEvento", "!=", tipoEventoRepo
					.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_REVOCA_MODIFICA_RICHIEDENTE)).orElse(null)));
		}
		return criteriaList;
	}

	private List<SearchCriteria> creaCriteriaMittente(List<Partecipante> listaPartecipanti) {
		List<SearchCriteria> criteriaList = new ArrayList<>();
		listaPartecipanti.stream().forEach(p -> criteriaList.add(new SearchCriteria("mittente", "=", p)));
		;
		return criteriaList;
	}

	private List<SearchCriteria> creaCriteriaDestinatari(List<Partecipante> listaPartecipanti) {
		List<SearchCriteria> criteriaList = new ArrayList<>();
		List<RuoloPartecipante> listaRuoliPartecipante = listaPartecipanti.stream().map(p -> p.getRuoloPartecipante())
				.collect(Collectors.toList());
		for (RuoloPartecipante ruolo : listaRuoliPartecipante) {
			List<EventoPartecipante> listaEventoPartecipante = this.eventoPartRepo.findByRuoloPartecipante(ruolo);
			listaEventoPartecipante.stream().map(ep -> ep.getTipoEvento())
					.forEach(te -> criteriaList.add(new SearchCriteria("tipoEvento", "=", te)));
		}
		return criteriaList;
	}

	private List<SearchCriteria> creaCriteriaVisibilitaExtra(List<Partecipante> listaPartecipanti) {
		List<SearchCriteria> criteriaList = new ArrayList<>();
		for (Partecipante partecipante : listaPartecipanti) {
			partecipante.getVisibilitaPartecipanteEventi().stream().map(e -> e.getId())
					.forEach(id -> criteriaList.add(new SearchCriteria("id", "=", id)));
		}
		return criteriaList;
	}

	public void notificaMail(Evento saved, Documento documento, List<LabelValue> listaDestinatari,List<ModificaData> modificheDate) throws MessagingException {
		if (mailer.isSendEnabled() && saved.getTipoEvento().getFlagInvioEmail()) {
			TipoEvento tipoEvento = this.tipoEventoRepo.findById(saved.getTipoEvento().getCodice()).get();
			List<EventoPartecipante> lista = this.eventoPartRepo.findByTipoEvento(tipoEvento);
			List<Partecipante> listaPartecipanti = emailRepositoryService.creaDestinatariPerRuoloPart(saved, lista);
			Utente utente = utenteService.getAuthenticatedUserAsResponsibleOfConference(documento.getConferenza());
			emailRepositoryService.sendMailEvento(saved.getId(), documento.getIdDocumento(), listaDestinatari, listaPartecipanti, utente.getIdUtente(),modificheDate);			
		}		
	}

	// xmf: changed to support scheduler send mail job!
	public void notificaMailFromScheduler(Evento saved, Documento documento, List<LabelValue> listaDestinatari, List<ModificaData> md) throws MessagingException {
		if (mailer.isSendEnabled() && saved.getTipoEvento().getFlagInvioEmail()) {
			TipoEvento tipoEvento = this.tipoEventoRepo.findById(saved.getTipoEvento().getCodice()).get();
			List<EventoPartecipante> lista = this.eventoPartRepo.findByTipoEvento(tipoEvento);
			List<Partecipante> listaPartecipanti = emailRepositoryService.creaDestinatariPerRuoloPartFromScheduler(saved, lista);
			Utente utente = utenteService.findByCodiceFiscale(saved.getConferenza().getCodiceFiscaleCreatoreConferenza());
			emailRepositoryService.sendMailEvento(saved.getId(), documento.getIdDocumento(), listaDestinatari, listaPartecipanti, utente.getIdUtente(),md);
		}
	}

	public EventoCompletoDTO findEvent(Integer id) {
		List<Evento> listaEventi = this.eventoRepo
				.findAll(filtroEventoPerVisibilita(id, this.eventoRepo.findById(id).get().getConferenza()));
		EventoCompletoDTO eventoCompletoDTO = null;
		if (listaEventi.size() != 0) {
			eventoCompletoDTO = this.eventoBuilder.buildEventoCompletoDTO(listaEventi.get(0));
		}
		return eventoCompletoDTO;
	}

	public void deleteEvent(Integer id) {
		Evento evento = this.eventoRepo.findById(id).orElse(null);
		evento.setDataFine(new Date());
		this.eventoRepo.save(evento);
	}

	public void creaEventoCreazioneConferenza(Conferenza saved) {
		Evento evento = new Evento();
		evento.setConferenza(saved);
		evento.setData(new Date());
		Accreditamento acc = this.utenteService.getAccreditamento(saved, true);
		if (acc != null) {
			evento.setMittente(acc.getPartecipante());
		}
		evento.setTipoEvento(this.tipoEventoRepo
				.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CREAZIONE_CONFERENZA)).orElse(null));
		this.eventoRepo.save(evento);
	}

	public void creaEventoClonazioneConferenza(Conferenza conferenzaOriginale, Conferenza conferenzaClonata) {
		Evento evento = new Evento();
		evento.setConferenza(conferenzaClonata);
		evento.setData(new Date());
		evento.setTipoEvento(this.tipoEventoRepo
				.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_ASSOCIAZIONE_CONFERENZA)).orElse(null));
		evento.setCorpo("Conferenza associata alla conferenza con id: " + conferenzaOriginale.getIdConferenza());
		this.eventoRepo.save(evento);
	}

	public ListaPecDTO findPec(Integer id, RicercaPecDTO ricerca) {
		this.confValidator.ConfEsistenza(id);
		PageRequest page = PageRequest.of(ricerca.getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()),
				this.eventoBuilder.mapColonnePec(PecDTO.getColonnaOrdinamento(ricerca.getColonnaOrdinamento())));
		CruscottoPec cruscottoExample = this.eventoBuilder.buildCruscottoRicerca(id, ricerca);
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("mittente", match -> match.contains().ignoreCase());
		Example<CruscottoPec> example = Example.of(cruscottoExample, matcher);
		Iterable<CruscottoPec> it = this.cruscottoRepo.findAll(example, page);
		ListaPecDTO listaPec = new ListaPecDTO();
		for (CruscottoPec cruscotto : it) {
			listaPec.getList().add(this.eventoBuilder.buildPecDTO(cruscotto));
		}
		listaPec.setTotalNumber(this.cruscottoRepo.count(example));
		return listaPec;
	}
	
	//public Evento creaEvento (EventoFileDTO eventoFileDTO, Integer idConf, DocumentoFileDTO documentoFileDTO) {
	public Evento creaEvento (EventoFileDTO eventoFileDTO, Conferenza conferenza, DocumentoFileDTO documentoFileDTO,
			TipoEvento tipoEvento) {
		
		Documento mainDocument = null;
		if (tipoEvento != null && documentoFileDTO.getFile() != null) {
			// xmfAddedAttaches
			for(int j=-1; j==-1 || (documentoFileDTO.getAttachments() != null && j < documentoFileDTO.getAttachments().length); j++) {
				
				Documento documento;
				if(j == -1) {
					documentoFileDTO.setTipoDocumento(tipoEvento.getTipologiaDocumento().getCodice());
					mainDocument = documento = documentoService.creaDocumentoMultipartFile(documentoFileDTO, conferenza.getIdConferenza());					
				}
				else {
					// SAVE EVENT ATTACHMENTS
					MultipartFile attachment = documentoFileDTO.getAttachments()[j];
					documento = saveAttachment(documentoFileDTO, attachment, mainDocument, conferenza.getIdConferenza());
				}
				
				setDocVisibility(conferenza, documento, eventoFileDTO);
			}
		}
		
		
		Evento saved = this.eventoRepo.save(this.eventoBuilder.buildEvento(eventoFileDTO, conferenza, mainDocument, null));
		LOGGER.debug("@docs eventId: " + saved.getId());
		
		if (saved.getOggettoEvento().getCodice().equals(Integer.toString(DbConst.OGGETTO_EVENTO_CHIUSURA_CONFERENZA))) {
			Stato stato = this.statoRepo.findById(Integer.toString(DbConst.STATO_CHIUSA)).get();
			conferenza.setStato(stato);
			this.confRepo.save(conferenza);
		}
		
		return saved;
	}
	
	public Evento creaEventoModificaRichiedente(Conferenza saved, String stringaTipoEvento) {
		TipoEvento tipoEvento = tipoEventoRepo.findById(stringaTipoEvento).orElse(null);
		Evento evento = new Evento();
		evento.setConferenza(saved);
		evento.setData(new Date());
		Accreditamento acc = this.utenteService.getAccreditamento(saved, true);
		if (acc != null) {
			evento.setMittente(acc.getPartecipante());
		} else {
			evento.setMittente(saved.getPartecipantes().stream()
					.filter(p -> p.getRuoloPartecipante().getCodice()
							.equals(Integer.toString(DbConst.RUOLO_PERSONA_RESPONSABILE_CONFERENZA)))
					.collect(Collectors.toList()).get(0));
		}
		evento.setTipoEvento(tipoEvento);
		evento.setOggettoEvento(tipoEvento.getOggettoEvento());
		Evento eventoSaved = this.eventoRepo.save(evento);
		return eventoSaved;
		
	}

	public void doEventoModificaRichiedente(Evento evento) {
		if (mailer.isSendEnabled() && evento.getTipoEvento().getFlagInvioEmail()) {
			Utente utente = utenteService.getAuthenticatedUserAsResponsibleOfConference(evento.getConferenza());
			emailRepositoryService.sendMailEventoModificaRichiedente(evento.getId(), utente.getIdUtente(), utente.isCreatore());
		}
	}

	public void creaEventoAccreditamento(Accreditamento accreditamento, String stringaTipoEvento, Boolean isResponsabile) {
		TipoEvento tipoEvento = tipoEventoRepo.findById(stringaTipoEvento).orElse(null);
		Evento evento = new Evento();
		evento.setConferenza(accreditamento.getPartecipante().getConferenza());
		evento.setData(new Date());
		evento.setTipoEvento(tipoEvento);
		evento.setOggettoEvento(tipoEvento.getOggettoEvento());
		Accreditamento acc = utenteService.getAccreditamento(accreditamento.getPartecipante().getConferenza(), true);
		if (acc != null) {
			evento.setMittente(acc.getPartecipante());
		}
		Evento eventoSaved = this.eventoRepo.save(evento);
		doEventoAccreditamento(eventoSaved, accreditamento, isResponsabile);
	}

	public void doEventoAccreditamento(Evento evento, Accreditamento accreditamento, Boolean isResponsabile) {
		if (mailer.isSendEnabled() && evento.getTipoEvento().getFlagInvioEmail()) {
			Utente utente = utenteService.getAuthenticatedUserAsResponsibleOfConference(evento.getConferenza());
			emailRepositoryService.sendMailEventoAccreditamento(evento.getId(), accreditamento.getId(), isResponsabile, utente.getIdUtente());
		}
	}
	
	public List<Evento> findByDocumento(Documento documento) {
		return eventoRepo.findByDocumento(documento);
	}
	
	public void creaEventoConvocazioneConferenza(DocumentoFileDTO documentoFileDTO, Documento documentoSaved, Integer idConferenza) {
		Evento evento = new Evento();
		evento.setConferenza((documentoSaved.getConferenza()));
		evento.setDocumento(documentoSaved);
		evento.setData(new Date());
		if (documentoSaved.getOwner() != null) {
			evento.setMittente(documentoSaved.getOwner().getPartecipante());
		}
		evento.setTipoEvento(this.tipoEventoRepo
				.findById(Integer.toString(DbConst.TIPOLOGIA_EVENTO_CONVOCAZIONE_CONFERENZA)).orElse(null));
		this.eventoRepo.save(evento);
		
		MultipartFile[] attachments = documentoFileDTO.getAttachments(); 
		for(int j=0; attachments != null && j<attachments.length;j++) {
			Documento attachSaved = saveAttachment(documentoFileDTO, attachments[j], documentoSaved, idConferenza);
			
			DocumentoDTO documentoDTO = documentoService.getDocumentoDTO(documentoFileDTO);
			documentoService.creaVisibilitaPartecipanti(documentoDTO, attachSaved);

			//setDocVisibility(doc.getConferenza(), doc, eventoFileDTO);
		}
	}

	private Documento saveAttachment(DocumentoFileDTO documentoFileDTO, MultipartFile attachment, Documento mainDocument, Integer idConf) {
		documentoFileDTO.setFile(attachment);
		documentoFileDTO.setNomeFile(attachment.getOriginalFilename());
		documentoService.creaRegistroDocumento(documentoFileDTO, mainDocument, false);

		DocumentoAttachment document = new DocumentoAttachment();
		document.setIdDocumento(mainDocument.getIdDocumento());
		document.setNome(attachment.getOriginalFilename());
		document.setTipologiaDocumento(mainDocument.getTipologiaDocumento());
		document.setCategoriaDocumento(mainDocument.getCategoriaDocumento());
		//document.setVisibilitaRistretta(mainDocument.getVisibilitaRistretta());
		document.setNumeroProtocollo(mainDocument.getNumeroProtocollo());
		document.setDataProtocollo(mainDocument.getDataProtocollo());
		document.setNumeroInventario(mainDocument.getNumeroInventario());
		//document.setCompetenzaTerritoriale(mainDocument.getCompetenzaTerritoriale());
		document.setDataRiunione(mainDocument.getDataRiunione());
		//document.setOwner(mainDocument.getOwner());
		document.setConferenza(mainDocument.getConferenza());
		//document.setVisibilitaPartecipanti(mainDocument.getVisibilitaPartecipanti());
		//document.setFirmatari(mainDocument.getFirmatari());
		document.setFileConforme(mainDocument.getFileConforme());
		//document.setMd5(mainDocument.getMd5());
		document.setFkDocumento(mainDocument.getIdDocumento());
		
		this.documentoAttachRepo.save(document);
		LOGGER.debug("@docs attach doc["+attachment.getOriginalFilename()+"] ID: " + document.getIdDocumento());
		
		// SAVE DOCUMENTS
		documentoFileDTO.setTipoDocumento(DbConst.TIPOLOGIA_DOCUMENTO_INTERAZIONI);
		documentoFileDTO.setFile(attachment);
		documentoFileDTO.setNomeFile(attachment.getOriginalFilename());
		Documento documento = documentoService.creaDocumentoMultipartFile(documentoFileDTO, idConf, null, true);
		documento.setFkDocParent(mainDocument.getIdDocumento());
		
		return documento;
	}

	private void setDocVisibility(Conferenza conferenza, Documento documento, EventoFileDTO eventoFileDTO) {
		List<Partecipante> listaVisibilita = documento.getVisibilitaPartecipanti();
		eventoFileDTO.getListaDestinatari().stream()
				.map(l -> this.partRepo.findById(Integer.parseInt(l.getKey())).orElse(null))
				.forEach(p -> listaVisibilita.add(p));
		List<RuoloPartecipante> listaRuoliDestinatari = this.eventoPartRepo
				.findByTipoEvento(this.tipoEventoRepo.findById(eventoFileDTO.getTipoEvento()).orElse(null)).stream()
				.map(ep -> ep.getRuoloPartecipante()).collect(Collectors.toList());
		for (RuoloPartecipante ruolo : listaRuoliDestinatari) {
			conferenza.getPartecipantes().stream().filter(p -> p.getRuoloPartecipante().equals(ruolo))
					.forEach(p -> listaVisibilita.add(p));
		}
		documento.setVisibilitaPartecipanti(listaVisibilita.stream().distinct().collect(Collectors.toList()));
		
		this.documentoRepo.save(documento);
		LOGGER.debug("@docs new doc["+documento.getNome()+"] ID: " + documento.getIdDocumento());
	}
	
	public Evento creaEventoMultiFirma (EventoFileDTO eventoFileDTO, Integer idConf, DocumentoFileDTO documentoFileDTO, Integer idResponsabileFirma) {
		Conferenza conferenza = this.confRepo.findByIdConferenza(idConf);
		if (conferenza == null)
			throw new NotFoundEx("la conferenza non e' stata trovata");

		TipoEvento tipoEvento = this.tipoEventoRepo.findById(eventoFileDTO.getTipoEvento()).orElse(null);
		LOGGER.debug("@docs event type: " + tipoEvento.getDescrizione());
		
		Documento mainDocument = null;
		if (tipoEvento != null && documentoFileDTO.getFile() != null) {
			// xmfAddedAttaches
			for(int j=-1; j==-1 || (documentoFileDTO.getAttachments() != null && j < documentoFileDTO.getAttachments().length); j++) {
				
				Documento documento;
				if(j == -1) {
					documentoFileDTO.setTipoDocumento(tipoEvento.getTipologiaDocumento().getCodice());
					mainDocument = documento = documentoService.creaDocumentoMultipartFile(documentoFileDTO, idConf);					
				}
				else {
					// SAVE EVENT ATTACHMENTS
					MultipartFile attachment = documentoFileDTO.getAttachments()[j];
					documento = saveAttachment(documentoFileDTO, attachment, mainDocument, idConf);
				}
				
				setDocVisibility(conferenza, documento, eventoFileDTO);
			}
		}
		
	
		
		Evento saved = this.eventoRepo.save(this.eventoBuilder.buildEvento(eventoFileDTO, conferenza, mainDocument,null));
		LOGGER.debug("@docs eventId: " + saved.getId());
		
		// devo aggiungere il documento come da firmare con firma multipla
		DocumentoFirmaMultipla documentoFirmaMultiSaved = documentoFirmaMultiplaService.aggiornaFirmaMultipla(mainDocument, idResponsabileFirma, STATO_DOCUMENTO.IN_BOZZA.getStatus(), true);

		// mando la mail di notifica
		if(documentoFirmaMultiSaved != null)
			notificaMailAFirmatario(mainDocument, idResponsabileFirma);
		LOGGER.debug("@docs eventId: " + documentoFirmaMultiSaved.getId());
		// non devo cambiare lo stato
		return saved;
	}
	
	public void notificaMailAFirmatario( Documento documento, Integer idUtente) {
		if (mailer.isSendEnabled()) {
			emailRepositoryService.sendEmailAFirmatario(documento.getIdDocumento(), idUtente);			
		}		
	}
	
	public Conferenza getConferenza(int idConf) {
		return  this.confRepo.findByIdConferenza(idConf);
	}
	
	public TipoEvento getTipoEvento(EventoFileDTO eventoFileDTO) {
		return  this.tipoEventoRepo.findById(eventoFileDTO.getTipoEvento()).orElse(null);
	}
	
	public Evento creaEventoDate (EventoFileDTO eventoFileDTO, Conferenza conferenza, DocumentoFileDTO documentoFileDTO,
			TipoEvento tipoEvento, String newDate, String dateType, int ordine, Documento docEsistente) {

		Documento mainDocument = null;
		if (docEsistente != null) {
			mainDocument = docEsistente;
		} else {
			if (tipoEvento != null && documentoFileDTO.getFile() != null) {
				// xmfAddedAttaches
				for(int j=-1; j==-1 || (documentoFileDTO.getAttachments() != null && j < documentoFileDTO.getAttachments().length); j++) {
					
					Documento documento;
					if(j == -1) {
						documentoFileDTO.setTipoDocumento(tipoEvento.getTipologiaDocumento().getCodice());
						mainDocument = documento = documentoService.creaDocumentoMultipartFile(documentoFileDTO, conferenza.getIdConferenza());					
					}
					else {
						// SAVE EVENT ATTACHMENTS
						MultipartFile attachment = documentoFileDTO.getAttachments()[j];
						documento = saveAttachment(documentoFileDTO, attachment, mainDocument, conferenza.getIdConferenza());
					}
					
					setDocVisibility(conferenza, documento, eventoFileDTO);
				}
			}
		}
		
		
		//se il tipo di evento è modifica data, va salvato sul database la nova data della conferenza
		ModificaData modificaData = null;
		
		ModificaDataDTO modificaDataDTO = new ModificaDataDTO();
		modificaDataDTO.setCodice(dateType);
		modificaDataDTO.setData(newDate);		
		eventoFileDTO.setModificaData( modificaDataDTO);
			
		String tipoData = eventoFileDTO.getModificaData().getCodice();
		String backupDateString = null;
		ModificaData modData = this.eventoBuilder.mapModifcaData(eventoFileDTO.getModificaData(), conferenza, tipoData,ordine);
		switch (tipoData) {
		case DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA:
			backupDateString = this.eventoBuilder.ConvertDateToString(conferenza.getPrimaSessioneSimultanea());
			conferenza.setPrimaSessioneSimultanea(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		case DbConst.DATA_TERMINE:
			backupDateString = this.eventoBuilder.ConvertDateToString(conferenza.getDataTermine());
			conferenza.setDataTermine(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		case DbConst.DATA_TERMINE_ESPRESSIONE_PARERI:
			backupDateString = this.eventoBuilder.ConvertDateToString(conferenza.getTermineEspressionePareri());
			conferenza.setTermineEspressionePareri(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		case DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA:
			backupDateString = this.eventoBuilder
					.ConvertDateToString(conferenza.getTermineRichiestaIntegrazioniConferenza());

			conferenza.setTermineRichiestaIntegrazioniConferenza(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		}

		modificaData = modificaDataRepo
				.save(this.eventoBuilder.buildModificaData(eventoFileDTO, conferenza, modData.getData(), ordine));
		
		
		Evento saved = this.eventoRepo.save(this.eventoBuilder.buildEvento(eventoFileDTO, conferenza, mainDocument, modificaData));
		LOGGER.debug("@docs eventId: " + saved.getId());
		
		if (saved.getOggettoEvento().getCodice().equals(Integer.toString(DbConst.OGGETTO_EVENTO_CHIUSURA_CONFERENZA))) {
			Stato stato = this.statoRepo.findById(Integer.toString(DbConst.STATO_CHIUSA)).get();
			conferenza.setStato(stato);
			this.confRepo.save(conferenza);
		}
		
		return saved;
	}
	
	public String ConvertDateToString (Date valore) {
		return this.eventoBuilder.ConvertDateToString(valore);
	}
	
	public Date ConvertStringToDate (String valore) {
		return this.eventoBuilder.ConvertStringToDate(valore);
	}
	
	public Evento creaEventoMultiFirmaDate (EventoFileDTO eventoFileDTO, Conferenza conferenza, DocumentoFileDTO documentoFileDTO, Integer idResponsabileFirma,
			TipoEvento tipoEvento, String newDate, String dateType, int ordine, Documento docEsistente) {
		Documento mainDocument = null;
		if (docEsistente != null) {
			mainDocument = docEsistente;
		} else {
			if (tipoEvento != null && documentoFileDTO.getFile() != null) {
				// xmfAddedAttaches
				for(int j=-1; j==-1 || (documentoFileDTO.getAttachments() != null && j < documentoFileDTO.getAttachments().length); j++) {
					
					Documento documento;
					if(j == -1) {
						documentoFileDTO.setTipoDocumento(tipoEvento.getTipologiaDocumento().getCodice());
						mainDocument = documento = documentoService.creaDocumentoMultipartFile(documentoFileDTO, conferenza.getIdConferenza());					
					}
					else {
						// SAVE EVENT ATTACHMENTS
						MultipartFile attachment = documentoFileDTO.getAttachments()[j];
						documento = saveAttachment(documentoFileDTO, attachment, mainDocument, conferenza.getIdConferenza());
					}
					
					setDocVisibility(conferenza, documento, eventoFileDTO);
				}
			}
		}
		
		//se il tipo di evento è modifica data, va salvato sul database la nova data della conferenza
		ModificaData modificaData = null;
				
		ModificaDataDTO modificaDataDTO = new ModificaDataDTO();
		modificaDataDTO.setCodice(dateType);
		modificaDataDTO.setData(newDate);		
		eventoFileDTO.setModificaData( modificaDataDTO);
					
		String tipoData = eventoFileDTO.getModificaData().getCodice();
		String backupDateString = null;

		ModificaData modData = this.eventoBuilder.mapModifcaData(eventoFileDTO.getModificaData(), conferenza, tipoData, ordine);
		switch (tipoData) {
		case DbConst.DATA_PRIMA_SESSIONE_SIMULTANEA:
			backupDateString = this.eventoBuilder.ConvertDateToString(conferenza.getPrimaSessioneSimultanea());
			conferenza.setPrimaSessioneSimultanea(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		case DbConst.DATA_TERMINE:
			backupDateString = this.eventoBuilder.ConvertDateToString(conferenza.getDataTermine());
			conferenza.setDataTermine(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		case DbConst.DATA_TERMINE_ESPRESSIONE_PARERI:
			backupDateString = this.eventoBuilder.ConvertDateToString(conferenza.getTermineEspressionePareri());
			conferenza.setTermineEspressionePareri(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		case DbConst.DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA:
			backupDateString = this.eventoBuilder
					.ConvertDateToString(conferenza.getTermineRichiestaIntegrazioniConferenza());
			conferenza.setTermineRichiestaIntegrazioniConferenza(modData.getData());
			eventoFileDTO.getModificaData().setData(backupDateString);
			break;
		}

		modificaData = modificaDataRepo
				.save(this.eventoBuilder.buildModificaData(eventoFileDTO, conferenza, modData.getData(),ordine));
		
		Evento saved = this.eventoRepo.save(this.eventoBuilder.buildEvento(eventoFileDTO, conferenza, mainDocument, modificaData));
		LOGGER.debug("@docs eventId: " + saved.getId());
		
		
		return saved;
	}
	
	public void notificaMailMultiFirmaDate (Conferenza conferenza, TipoEvento tipoEvento,
			Integer idResponsabileFirma, Documento documento) {

		// devo aggiungere il documento come da firmare con firma multipla
		DocumentoFirmaMultipla documentoFirmaMultiSaved = documentoFirmaMultiplaService.aggiornaFirmaMultipla(documento,
				idResponsabileFirma, STATO_DOCUMENTO.IN_BOZZA.getStatus(), true);

		// mando la mail di notifica
		if (documentoFirmaMultiSaved != null)
			notificaMailAFirmatario(documento, idResponsabileFirma);
		LOGGER.debug("@docs eventId: " + documentoFirmaMultiSaved.getId());
		// non devo cambiare lo stato
	}
	
	public int getMaxOrdineModifcataData(int idConf) {
		return  this.modificaDataRepo.findMaxOrdineModificaData(idConf);
	}
}
