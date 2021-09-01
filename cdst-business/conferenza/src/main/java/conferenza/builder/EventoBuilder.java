package conferenza.builder;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import conferenza.DTO.EventoCompletoDTO;
import conferenza.DTO.EventoDTO;
import conferenza.DTO.EventoFileDTO;
import conferenza.DTO.ModificaDataDTO;
import conferenza.DTO.PecDTO;
import conferenza.DTO.RicercaPecDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.model.Conferenza;
import conferenza.model.CruscottoPec;
import conferenza.model.Documento;
import conferenza.model.Evento;
import conferenza.model.ModificaData;
import conferenza.model.Partecipante;
import conferenza.model.TipoData;
import conferenza.model.TipoEvento;
import conferenza.model.TipoParere;
import conferenza.repository.EsitoChiusuraConferenzaRepository;
import conferenza.repository.ModificaDataRepository;
import conferenza.repository.OggettoEventoRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.TipoDataRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.repository.TipoParereRepository;
import conferenza.service.DocumentoFirmaMultiplaService;
import conferenza.service.EventoService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;

@Component
public class EventoBuilder extends _BaseBuilder {

	@Autowired
	TipoEventoRepository tipoEventoRepo;

	@Autowired
	TipoDataRepository tipoDataRepo;
	
	@Autowired
	OggettoEventoRepository oggettoEventoRepo;

	@Autowired
	PartecipanteRepository partecipanteRepo;

	@Autowired
	PartecipanteBuilder partecipanteBuilder;

	@Autowired
	DocumentoBuilder documentoBuilder;

	@Autowired
	ConferenzaBuilder conferenzaBuilder;

	@Autowired
	EventoService eventoService;

	@Autowired
	UtenteService utenteService;
	
	@Autowired
	EsitoChiusuraConferenzaRepository esitoChiusuraConfRepo;
	
	@Autowired
	TipoParereRepository tipoParereRepo;

	@Autowired
	ModificaDataRepository modificaDaaRepo;
	
	@Autowired
	DocumentoFirmaMultiplaService documentoFirmaMultiplaService;

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
			// se è un documento in firma multipla recupero lo stato in cui si trova
			String statoFirma = this.documentoFirmaMultiplaService.getStatoDocumento(evento.getDocumento());
			if(statoFirma != null)
				eventoDTO.setStatoFirma(statoFirma);	
			eventoDTO.setId(evento.getId());
			return eventoDTO;
		}
		return null;
	}

	public Evento buildEvento(EventoFileDTO eventoFileDTO, Conferenza conferenza, Documento documento, ModificaData modificaData) {
		Evento evento = new Evento();
		
		evento.setConferenza(conferenza);
		evento.setDocumento(documento);
		evento.setModificaData(modificaData);
		evento.setCorpo(eventoFileDTO.getCorpo());
		evento.setNote(eventoFileDTO.getNote());
		Partecipante partecipante = this.utenteService.getAccreditamento(conferenza, true).getPartecipante();
		if (eventoFileDTO.getTipoParere() != null) {
			TipoParere tipoParere = tipoParereRepo.findById(eventoFileDTO.getTipoParere()).orElse(null);
			evento.setTipoParere(tipoParere);
		}
		if (eventoFileDTO.getData() == null) {
			evento.setData(new Date());
		} else {
			evento.setData(stringToDate(eventoFileDTO.getData()));
		}
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

	public ModificaData buildModificaData(EventoFileDTO eventoFileDTO, Conferenza conferenza, Date valoreNew, int ordine) {		
		ModificaData modificaData = null;
		TipoData tipoData = tipoDataRepo.findById(eventoFileDTO.getModificaData().getCodice()).get();
		//modificaData = modificaDaaRepo.findOneByConferenzaAndTipoData(conferenza, tipoData);
		if(modificaData == null) {
			modificaData = new ModificaData();
			modificaData.setTipoData(tipoData);		
			modificaData.setConferenza(conferenza);
		}
		getMapper().map(eventoFileDTO.getModificaData(), modificaData);	
		modificaData.setDataNew(valoreNew);
		modificaData.setDataModifica(new Date());
		modificaData.setOrdine(ordine);

		return modificaData;
	}
	
	public ModificaData mapModifcaData(ModificaDataDTO modificaDataDTO, Conferenza conferenza, String tipoData, int ordine)  {
		ModificaData modificaData = new ModificaData();
		TipoData tipoDataDB = tipoDataRepo.findById(modificaDataDTO.getCodice()).get();
		modificaData.setTipoData(tipoDataDB);	
		modificaData.setConferenza(conferenza);
		modificaData.setOrdine(ordine);
		getMapper().map(modificaDataDTO, modificaData); 
		return 	modificaData;	
	}
	
	public String ConvertDateToString (Date valore) {
		return dateToString(valore);
	}
	
	public Date ConvertStringToDate (String valore) {
		return stringToDate(valore);
	}
		
	public EventoCompletoDTO buildEventoCompletoDTO(Evento evento) {
		EventoDTO eventoDTO = buildEventoDTO(evento);
		if (eventoDTO != null) {
			EventoCompletoDTO eventoCompletoDTO = getMapper().map(eventoDTO, EventoCompletoDTO.class);
			eventoCompletoDTO.setNumeroProtocollo(evento.getProtocollo());
			eventoCompletoDTO.setCorpo(evento.getCorpo()==null?"":evento.getCorpo()); // xmf: prevent "null" string in the dialogbox
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
			
			if(evento.getModificaData() != null) {
				eventoCompletoDTO.setModificaData(conferenzaBuilder.buildModificaDataDTO(evento.getModificaData()));				
			}
			
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
