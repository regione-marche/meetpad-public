package cdst_be_marche.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdst_be_marche.DTO.DocumentoFileDTO;
import cdst_be_marche.DTO.EventoCompletoDTO;
import cdst_be_marche.DTO.EventoFileDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaEventoDTO;
import cdst_be_marche.DTO.ListaPecDTO;
import cdst_be_marche.DTO.PageableDTO;
import cdst_be_marche.DTO.PecDTO;
import cdst_be_marche.DTO.RicercaPecDTO;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.builder.EventoBuilder;
import cdst_be_marche.exception.NotFoundEx;
import cdst_be_marche.mail.EmailRepositoryService;
import cdst_be_marche.mail.JavaMailSenderConfigurator;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.CruscottoPec;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Evento;
import cdst_be_marche.model.EventoPartecipante;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.RuoloPartecipante;
import cdst_be_marche.model.Stato;
import cdst_be_marche.model.TipoEvento;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.CruscottoPecRepository;
import cdst_be_marche.repository.DocumentoRepository;
import cdst_be_marche.repository.EventoPartecipanteRepository;
import cdst_be_marche.repository.EventoRepository;
import cdst_be_marche.repository.EventoSpecificationsBuilder;
import cdst_be_marche.repository.OggettoEventoRepository;
import cdst_be_marche.repository.PartecipanteRepository;
import cdst_be_marche.repository.SearchCriteria;
import cdst_be_marche.repository.StatoRepository;
import cdst_be_marche.repository.TipoEventoRepository;
import cdst_be_marche.util.DbConst;
import cdst_be_marche.validator.ConferenceValidator;

@Transactional
@Service
public class EventoService extends _BaseService {

	@Autowired
	ConferenzaRepository confRepo;

	@Autowired
	EventoRepository eventoRepo;

	@Autowired
	EventoBuilder eventoBuilder;

	@Autowired
	DocumentoRepository documentoRepo;

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
	
	private Specification<Evento> filtroEventoPerVisibilita(Integer idEvento, Conferenza conferenza) {
		List<Partecipante> listaPartecipanti = this.utenteService.findAllPartecipantiPerUtente(conferenza);
		List<SearchCriteria> criteriaConferenza = new ArrayList<>();
		criteriaConferenza.add(new SearchCriteria("conferenza", "=", conferenza));
		List<SearchCriteria> criteriaMittente = creaCriteriaMittente(listaPartecipanti);
		List<SearchCriteria> criteriaVisibilitaExtra = creaCriteriaVisibilitaExtra(listaPartecipanti);
		List<SearchCriteria> criteriaDestinatari = creaCriteriaDestinatari(listaPartecipanti);
		List<SearchCriteria> criteriaIdEvento = new ArrayList<>();
		if (idEvento != null) {
			criteriaIdEvento.add(new SearchCriteria("id", "=", idEvento));
		}
		EventoSpecificationsBuilder builder = new EventoSpecificationsBuilder();
		return builder.buildEventoSpec(criteriaConferenza, criteriaMittente, criteriaVisibilitaExtra, criteriaDestinatari, criteriaIdEvento,
				this.utenteService.isAdminAmministrazioni(), this.utenteService.getAuthenticatedUser().isCreatore());
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

	private void doEvento(Evento saved, Documento documento, List<LabelValue> listaDestinatari) throws MessagingException {
		if (mailer.isSendEnabled() && saved.getTipoEvento().getFlagInvioEmail()) {
			emailRepositoryService.sendMailEvento(saved, documento, listaDestinatari);
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

	public void creaEventoConvocazioneConferenza(Documento documentoSaved) {
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
	}

	public void creaEventoCreazioneConferenza(Conferenza saved) {
		Evento evento = new Evento();
		evento.setConferenza(saved);
		evento.setData(new Date());
		if (this.utenteService.getAccreditamento(saved) != null) {
			evento.setMittente(this.utenteService.getAccreditamento(saved).getPartecipante());
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
		Example<CruscottoPec> example = Example.of(cruscottoExample);
		Iterable<CruscottoPec> it = this.cruscottoRepo.findAll(example, page);
		ListaPecDTO listaPec = new ListaPecDTO();
		for (CruscottoPec cruscotto : it) {
			listaPec.getList().add(this.eventoBuilder.buildPecDTO(cruscotto));
		}
		listaPec.setTotalNumber(this.cruscottoRepo.count(example));
		return listaPec;
	}
	
	public IdentifiableDTO creaEvento (EventoFileDTO eventoFileDTO, Integer id, DocumentoFileDTO documentoFileDTO) {
		Conferenza conferenza = this.confRepo.findByIdConferenza(id);
		if (conferenza == null) {
			throw new NotFoundEx("la conferenza non e' stata trovata");
		}
		TipoEvento tipoEvento = this.tipoEventoRepo.findById(eventoFileDTO.getTipoEvento()).orElse(null);
		Documento documento = null;
		if (tipoEvento != null && documentoFileDTO.getFile() != null) {
			documentoFileDTO.setTipoDocumento(tipoEvento.getTipologiaDocumento().getCodice());
			documento = documentoService.creaDocumentoMultipartFile(documentoFileDTO, id);
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
			this.documentoRepo.save(documento);
		}
		Evento saved = this.eventoRepo.save(this.eventoBuilder.buildEvento(eventoFileDTO, conferenza, documento));
		
		try {
			doEvento(saved, documento, eventoFileDTO.getListaDestinatari());
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (saved.getOggettoEvento().getCodice().equals(Integer.toString(DbConst.OGGETTO_EVENTO_CHIUSURA_CONFERENZA))) {
			Stato stato = this.statoRepo.findById(Integer.toString(DbConst.STATO_CHIUSA)).get();
			conferenza.setStato(stato);
			this.confRepo.save(conferenza);
		}
		IdentifiableDTO identifiableDTO = new IdentifiableDTO();
		identifiableDTO.setId(saved.getId());
		return identifiableDTO;
	}	

}
