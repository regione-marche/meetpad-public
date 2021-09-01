package cdst_be_marche.builder;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cdst_be_marche.DTO.EventoCompletoDTO;
import cdst_be_marche.DTO.EventoDTO;
import cdst_be_marche.DTO.EventoFileDTO;
import cdst_be_marche.DTO.PecDTO;
import cdst_be_marche.DTO.RicercaPecDTO;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.CruscottoPec;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Evento;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.TipoEvento;
import cdst_be_marche.repository.EsitoChiusuraConferenzaRepository;
import cdst_be_marche.repository.OggettoEventoRepository;
import cdst_be_marche.repository.PartecipanteRepository;
import cdst_be_marche.repository.TipoEventoRepository;
import cdst_be_marche.repository.TipoParereRepository;
import cdst_be_marche.service.EventoService;
import cdst_be_marche.service.UtenteService;

@Component
public class EventoBuilder extends _BaseBuilder {

	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	OggettoEventoRepository oggettoEventoRepo;

	@Autowired
	PartecipanteRepository partecipanteRepo;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	DocumentoBuilder documentoBuilder;

	@Autowired
	EventoService eventoService;

	@Autowired
	UtenteService utenteService;
	
	@Autowired
	EsitoChiusuraConferenzaRepository esitoChiusuraConfRepo;
	
	@Autowired
	TipoParereRepository tipoParereRepo;

	public EventoDTO buildEventoDTO(Evento evento) {
		EventoDTO eventoDTO = new EventoDTO();
		if (evento != null) {
			eventoDTO.setData(dateToString(evento.getData()));
			if (evento.getMittente() != null) {
				eventoDTO.setMittente(evento.getMittente().getDescrizione());
			}
			if (evento.getOggettoEvento() != null) {
				eventoDTO.setOggettoRichiesta(createNotNullLabelValue(
						this.oggettoEventoRepo.findById(evento.getOggettoEvento().getCodice()).orElse(null)));
			}
			eventoDTO.setTipoEvento(createNotNullLabelValue(
					this.tipoEventoRepo.findById(evento.getTipoEvento().getCodice()).orElse(null)));
			eventoDTO.setId(evento.getId());
			return eventoDTO;
		}
		return null;
	}

	public Evento buildEvento(EventoFileDTO eventoFileDTO, Conferenza conferenza, Documento documento) {
		Evento evento = new Evento();
		evento.setConferenza(conferenza);
		evento.setDocumento(documento);
		evento.setCorpo(eventoFileDTO.getCorpo());
		evento.setNote(eventoFileDTO.getNote());
		if (eventoFileDTO.getTipoParere() != null) {
			evento.setTipoParere(tipoParereRepo.findById(eventoFileDTO.getTipoParere()).orElse(null));
		}
		if (eventoFileDTO.getData() == null) {
			evento.setData(new Date());
		} else {
			evento.setData(stringToDate(eventoFileDTO.getData()));
		}
		Partecipante partecipante = this.utenteService.getAccreditamento(conferenza).getPartecipante();
		evento.setMittente(partecipante);
		TipoEvento tipoEvento = this.tipoEventoRepo.findById(eventoFileDTO.getTipoEvento()).orElse(null);
		evento.setTipoEvento(tipoEvento);
		evento.setOggettoEvento(tipoEvento.getOggettoEvento());
		evento.setProtocollo(eventoFileDTO.getProtocollo());
		if (eventoFileDTO.getResult() != null && !eventoFileDTO.getResult().isEmpty())
			evento.setEsitoChiusuraConferenza(this.esitoChiusuraConfRepo.findById(eventoFileDTO.getResult()).orElse(null));
		List<Partecipante> listaVisibilita = evento.getVisibilitaEventoPartecipanti();
		eventoFileDTO.getListaDestinatari().stream()
				.map(l -> this.partecipanteRepo.findById(Integer.parseInt(l.getKey())).orElse(null))
				.forEach(p -> listaVisibilita.add(p));
		return evento;
	}

	public EventoCompletoDTO buildEventoCompletoDTO(Evento evento) {
		EventoDTO eventoDTO = buildEventoDTO(evento);
		if (eventoDTO != null) {
			EventoCompletoDTO eventoCompletoDTO = getMapper().map(eventoDTO, EventoCompletoDTO.class);
			eventoCompletoDTO.setNumeroProtocollo(evento.getProtocollo());
			eventoCompletoDTO.setCorpo(evento.getCorpo());
			eventoCompletoDTO.setIdConferenza(evento.getConferenza().getIdConferenza());
			eventoCompletoDTO.setAmministrazione(eventoDTO.getMittente());
			eventoCompletoDTO.setNote(evento.getNote());
			if (evento.getDocumento() != null) {
				eventoCompletoDTO.setDocumentoDTO(this.documentoBuilder.buildDocumentoDTO(evento.getDocumento()));
			}
			if (evento.getEsitoChiusuraConferenza() != null) {
				eventoCompletoDTO.setResult(createNotNullLabelValue(evento.getEsitoChiusuraConferenza()));
			}
			if (evento.getTipoParere() != null) {
				eventoCompletoDTO.setTipoParere(createNotNullLabelValue(evento.getTipoParere()));
			}
			List<LabelValue> listaDestinatariDTO = eventoCompletoDTO.getListaDestinatari();
			evento.getVisibilitaEventoPartecipanti().stream()
					.forEach(p -> listaDestinatariDTO.add(createNotNullLabelValue(p)));
			return eventoCompletoDTO;
		}
		return null;
	}

	public CruscottoPec buildCruscottoRicerca(Integer id, RicercaPecDTO ricerca) {
		CruscottoPec cruscotto = new CruscottoPec();
		cruscotto.setIdConferenza(id);
		cruscotto.setMittente(ricerca.getMittente());
		cruscotto.setDestinatario(ricerca.getDestinatario());
		cruscotto.setCodiceStatoPec(ricerca.getEmailStatus());
		cruscotto.setCodiceTipoEvento(ricerca.getIdEvento());
		return cruscotto;
	}

	public PecDTO buildPecDTO(CruscottoPec cruscotto) {
		PecDTO pecDTO = new PecDTO();
		pecDTO.setMittente(cruscotto.getMittente());
		pecDTO.setDestinatario(cruscotto.getDestinatario());
		pecDTO.setEmailDestinatario(cruscotto.getEmailDestinatario());
		pecDTO.setDataInvio(this.dateToString(cruscotto.getDataInvio()));
		pecDTO.setMessaggioStato(cruscotto.getStatusMessage());
		pecDTO.setEmailStatus(new LabelValue(cruscotto.getCodiceStatoPec(),
				messageSource.getMessage(cruscotto.getDescrStatoPec(), null, request.getLocale())));
		pecDTO.setIdEvento(new LabelValue(cruscotto.getCodiceTipoEvento(), cruscotto.getDescrTipoEvento()));
		return pecDTO;
	}

	public String mapColonnePec(String colonnaOrdinamento) {
		if (colonnaOrdinamento.equals("emailStatus")) {
			colonnaOrdinamento = "codiceStatoPec";
		}
		if (colonnaOrdinamento.equals("idEvento")) {
			colonnaOrdinamento = "codiceTipoEvento";
		}
		if (colonnaOrdinamento.equals("messaggioStato")) {
			colonnaOrdinamento = "statusMessage";
		}
		return colonnaOrdinamento;
	}

}
