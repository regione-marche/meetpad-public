package conferenza.builder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import conferenza.DTO.AccreditamentoFileDTO;
import conferenza.DTO.DocumentazioneDTO;
import conferenza.DTO.DocumentoCartellaDTO;
import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EnteCsvDTO;
import conferenza.DTO.EnteUfficiCsvDTO;
import conferenza.DTO.EventoFileDTO;
import conferenza.DTO.PersonaDTO;
import conferenza.DTO.bean.LabelValue;
import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.exception.ParseDateException;
import conferenza.facade.STATO_DOCUMENTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.adapter.FirmaAdapter;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.repository.RegistroFirmaAdapterRepository;
import conferenza.firma.service.FirmaService;
import conferenza.firma.service.RegistroFirmaSessionSignerService;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.EnteAppoggioCsv;
import conferenza.model.EnteUfficiAppoggioCsv;
import conferenza.model.Partecipante;
import conferenza.model.RegistroDocumento;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.CategoriaDocumentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.RegistroDocumentoFonteRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.RegistroDocumentoTipoRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.service.AccreditamentoService;
import conferenza.service.DocumentoFirmaMultiplaService;
import conferenza.service.RegistroDocumentoService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;
import conferenza.util.JsonUtil;
import conferenza.util.SignUtil;

@Component
public class DocumentoBuilder extends _BaseBuilder {

	private SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoBuilder.class);

	@Autowired
	CategoriaDocumentoRepository categoriaDocumentoRepository;

	@Autowired
	TipologiaDocumentoRepository tipologiaDocumentoRepository;
	
	@Autowired
	RegistroDocumentoFonteRepository registroDocumentoFonteRepository;
	
	@Autowired
	RegistroDocumentoTipoRepository registroDocumentoTipoRepository;

	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;

	@Autowired
	ConferenzaRepository conferenzaRepository;

	@Autowired
	private RegistroDocumentoService registroDocumentoService;

	@Autowired
	DocumentoRepository documentoRepository;

	@Autowired
	AccreditamentoRepository accreditamentoRepo;
	
    @Autowired
    private DocumentAdapterService adapterService;
    
    @Autowired
	AccreditamentoService accrService;
	
	@Autowired 
	RegistroFirmaAdapterRepository registroFirmaAdapterRepository;
	
	@Autowired
	FirmaAdapter firmaAdapter;
	
	@Autowired 
	RegistroFirmaSessionSignerService registroFirmaSessionSignerService;
	
	@Autowired
	FirmaService firmaService;

	@Autowired
	UtenteService utenteService;
	
	@Autowired 
	DocumentoFirmaMultiplaService documentoFirmaMultiplaService;
	
	@Autowired 
	PersonaRepository personaRepository;
	
	@Autowired
	TipoEventoRepository tipoEventoRepo;
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Value("${descrizioneAmmProcedente.descrizione}")
	private String descrizione;

	public DocumentoDTO buildDocumentoDTO(DocumentoFileDTO documentoFileDTO) {
		DocumentoDTO documentoDTO = new DocumentoDTO();
		if (documentoFileDTO.getNomeFilePostCalamaio() != null &&
				!documentoFileDTO.getNomeFilePostCalamaio().equals("")) {
			documentoDTO.setNomeFile(documentoFileDTO.getNomeFilePostCalamaio());
		} else {
			documentoDTO.setNomeFile(documentoFileDTO.getNomeFile());
		}
		
		if (documentoFileDTO.getCategoria() != null) {
			documentoDTO.setCategoria(createNotNullLabelValue(
					categoriaDocumentoRepository.findById(documentoFileDTO.getCategoria()).orElse(null)));
		}
		documentoDTO.setTipoDocumento(createNotNullLabelValue(
				tipologiaDocumentoRepository.findById(documentoFileDTO.getTipoDocumento()).orElse(null)));
		if (documentoFileDTO.getVisibilitaPartecipanti() != null) {
			documentoDTO
			.setVisibilitaPartecipanti(JsonUtil.jsonToListLabelValue(documentoFileDTO.getVisibilitaPartecipanti()));
		}	
		documentoDTO.setNumeroProtocollo(documentoFileDTO.getNumeroProtocollo());
		documentoDTO.setDataProtocollo(documentoFileDTO.getDataProtocollo());
		documentoDTO.setNumeroInventario(documentoFileDTO.getNumeroInventario());
		documentoDTO.setCompetenzaTerritoriale(documentoFileDTO.getCompetenzaTerritoriale());
		documentoDTO.setDataRiunione(documentoFileDTO.getDataRiunione());
		documentoDTO.setFileConforme(documentoFileDTO.getFileConforme());
		documentoDTO.setMd5(getMD5(documentoFileDTO));
		
		return documentoDTO;
	}

	/**
	 * build di un Documento sia per la fase di creazione che per la fase di
	 * modifica.
	 * 
	 * @param documentoDTO
	 * @param conferenza     passare null per mantenere il valore attuale inalterato
	 *                       in fase di modifica
	 * @param accreditamento passare null per mantenere il valore attuale inalterato
	 *                       in fase di modifica
	 * @return
	 */
	public Documento buildDocumento(DocumentoDTO documentoDTO, Conferenza conferenza, Accreditamento accreditamento) {
		Documento documento;
		if (documentoDTO.getId() == null) {
			documento = new Documento();
		} else {
			documento = this.documentoRepository.findById(documentoDTO.getId()).get();
		}
		documento.setNome(documentoDTO.getNomeFile());
		documento.setCompetenzaTerritoriale(documentoDTO.getCompetenzaTerritoriale());
		if (documentoDTO.getCategoria() != null)
			documento.setCategoriaDocumento(
					categoriaDocumentoRepository.findById(documentoDTO.getCategoria().getKey()).orElse(null));
		if (documentoDTO.getTipoDocumento() != null)
			documento.setTipologiaDocumento(
					tipologiaDocumentoRepository.findById(documentoDTO.getTipoDocumento().getKey()).orElse(null));
		documento.setVisibilitaRistretta(!documentoDTO.getVisibilitaPartecipanti().isEmpty());
		if (documentoDTO.getNumeroProtocollo() != null) {
			documento.setNumeroProtocollo(documentoDTO.getNumeroProtocollo());
		}
		if (documentoDTO.getDataProtocollo() != null) {
			documento.setDataProtocollo(fromStringToDate(documentoDTO.getDataProtocollo()));
		}
		if (documentoDTO.getNumeroInventario() != null) {
			documento.setNumeroInventario(documentoDTO.getNumeroInventario());
		}
		if (documentoDTO.getDataRiunione() != null) {
			documento.setDataRiunione(fromStringToDate(documentoDTO.getDataRiunione()));
		}
		if (accreditamento != null) {
			documento.setOwner(accreditamento);
		} 
		if (conferenza != null) {
			documento.setConferenza(conferenza);
			if (accreditamento == null) {
				Accreditamento accrResp = accrService.findAccreditamentoByConfAndCodFiscale(documento.getConferenza(),
						documento.getConferenza().getCodiceFiscaleResponsabileConferenza());
				documento.setOwner(accrResp);
			}
		}
		
		documento.setMd5(documentoDTO.getMd5());
		documento.setFileConforme(documentoDTO.getFileConforme());
		return documento;
	}

	public RegistroDocumento buildRegistroDocumento(String riferimentoEsterno, Documento documentoSaved, String codiceFonte, String codiceTipo) {
		RegistroDocumento registroDocumento = new RegistroDocumento();
		registroDocumento.setData(new Date());
		registroDocumento.setRiferimentoEsterno(riferimentoEsterno);
		registroDocumento.setFonte(registroDocumentoFonteRepository.findById(codiceFonte).orElse(null));
		registroDocumento.setTipo(registroDocumentoTipoRepository.findById(codiceTipo).orElse(null));
		registroDocumento.setDocumento(documentoSaved);
		return registroDocumento;
	}

	public DocumentoDTO buildDocumentoDTO(Documento documento) {
		if (documento != null) {
			DocumentoDTO documentoDTO = new DocumentoDTO();
			documentoDTO.setId(documento.getIdDocumento());
			documentoDTO.setNomeFile(documento.getNome());
			documentoDTO.setCategoria(createNotNullLabelValue(documento.getCategoriaDocumento()));
			documentoDTO.setTipoDocumento(createNotNullLabelValue(documento.getTipologiaDocumento()));
			documentoDTO.setNumeroProtocollo(documento.getNumeroProtocollo());
			documentoDTO.setDataProtocollo(fromDateToString(documento.getDataProtocollo()));
			documentoDTO.setNumeroInventario(documento.getNumeroInventario());
			documentoDTO.setCompetenzaTerritoriale(documento.getCompetenzaTerritoriale());
			documentoDTO.setDataRiunione(fromDateToString(documento.getDataRiunione()));
			documentoDTO.setCartella(adapterService.getRaggruppamentoByIdDocument(getRegistroDocumento(documento)));
			for (Partecipante partecipante : documento.getVisibilitaPartecipanti().stream().distinct()
					.collect(Collectors.toList())) {
				documentoDTO.getVisibilitaPartecipanti().add(createNotNullLabelValue(partecipante));
			}
			for (Accreditamento accreditamento : documento.getFirmatari().stream().distinct()
					.collect(Collectors.toList())) {
				documentoDTO.getFirmatari().add(new LabelValue(Integer.toString(accreditamento.getId()),
						accreditamento.getPersona().getCognome() + " " + accreditamento.getPersona().getNome()));
			}
			documentoDTO.setUrl(registroDocumentoService.resolveFileDownloadUri(getRegistroDocumento(documento)));
			
			//aggiungi stato e proprietario del file nel caso sia un documento firmatario
			if(documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_FIRMATO)) {
				DocumentoDTO statusDocumentDTO = getStatusAndOwner(documento);
				documentoDTO.setStato(statusDocumentDTO.getStato());
				documentoDTO.setProprietario(statusDocumentDTO.getProprietario());
				documentoDTO.setFileSignedFromUser(statusDocumentDTO.getFileSignedFromUser());
			}
			
			// se è un documento in firma multipla recupero lo stato in cui si trova
			if(documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)) {
				String statoFirma = this.documentoFirmaMultiplaService.getStatoDocumento(documento);
				if(statoFirma != null)
					documentoDTO.setStatoFirma(statoFirma);
			}
			return documentoDTO;
		}
		return null;
	}

	/**
	 * Ad ogni documento sono associate 1 o più righe di registro. 
	 * Il registro che deve essere restituito è l'ultimo inserito in ordine di data. 
	 * @param documento
	 * @return
	 */
	public RegistroDocumento getRegistroDocumento(Documento documento) {
		for (RegistroDocumento registroDocumento : registroDocumentoRepository.findByDocumentoOrderByDataDesc(documento)) {
			return registroDocumento;
		}
		return null;
	}

	public Documento buildDocumentoAccreditamento(AccreditamentoFileDTO fileDTO, Partecipante partecipante,
			Accreditamento accreditamento) {
		Documento documento = new Documento();
		documento.setNome("Accreditamento" + fileDTO.getNome() + fileDTO.getCognome());
		documento.setConferenza(
				this.conferenzaRepository.findByIdConferenza(partecipante.getConferenza().getIdConferenza()));
		documento.setTipologiaDocumento(tipologiaDocumentoRepository
				.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_ACCREDITAMENTO).orElse(null));
		documento.getVisibilitaPartecipanti().add(partecipante);
		documento.setOwner(this.accreditamentoRepo.findById(accreditamento.getId()).orElse(null));
		return documento;
	}

	private Date fromStringToDate(String str) {
		if (str != null && !str.isEmpty()) {
			try {
				return simpleDate.parse(str);
			} catch (ParseException e) {
				throw new ParseDateException(e.getMessage());

			}
		} else
			return null;

	}
	
	private String fromDateToString (Date data) {
		if (data != null) {
			return simpleDate.format(data);
		}
		return null;
	}

	public DocumentazioneDTO buildDocumentazioneDTO(List<Documento> listaDocumentiOwner, Conferenza conferenza) {
		DocumentazioneDTO documentazioneDTO = new DocumentazioneDTO();
		List<DocumentoDTO> documentiAggiuntivi = new ArrayList<>();
		List<DocumentoDTO> documentiInterazione = new ArrayList<>();
		List<DocumentoDTO> documentiIndizione = new ArrayList<>();
		List<DocumentoDTO> documentiPreIstruttoria = new ArrayList<>();
		List<DocumentoDTO> documentiCondivisi = new ArrayList<>();
		List<DocumentoDTO> documentiFirma = new ArrayList<>();
		for (Documento documento : listaDocumentiOwner) {
			if (documento.getTipologiaDocumento().getSezioneDocumentazione() != null) {
				if (documento.getTipologiaDocumento().getSezioneDocumentazione().getCodice()
						.equals(DbConst.SEZIONE_DOCUMENTAZIONE_DOCUMENTI_AGGIUNTIVI)) {
					documentiAggiuntivi.add(buildDocumentoDTO(documento));
				}
				if (documento.getTipologiaDocumento().getSezioneDocumentazione().getCodice()
						.equals(DbConst.SEZIONE_DOCUMENTAZIONE_DOCUMENTI_INTERAZIONE)) {
					documentiInterazione.add(buildDocumentoDTO(documento));
				}
				if (documento.getTipologiaDocumento().getSezioneDocumentazione().getCodice()
						.equals(DbConst.SEZIONE_DOCUMENTAZIONE_DOCUMENTI_INDIZIONE)) {
					DocumentoDTO documentoDTO = buildDocumentoDTO(documento);
					if(documentoDTO.getStatoFirma() == null ||
							(documentoDTO.getStatoFirma() != null && !documentoDTO.getStatoFirma().equalsIgnoreCase(STATO_DOCUMENTO.RIFIUTATO.getStatus())))
						documentiIndizione.add(documentoDTO);
				}
				if (documento.getTipologiaDocumento().getSezioneDocumentazione().getCodice()
						.equals(DbConst.SEZIONE_DOCUMENTAZIONE_DOCUMENTI_PREISTRUTTORIA)) {
					documentiPreIstruttoria.add(buildDocumentoDTO(documento));
				}
				if (documento.getTipologiaDocumento().getSezioneDocumentazione().getCodice()
						.equals(DbConst.SEZIONE_DOCUMENTAZIONE_DOCUMENTI_CONDIVISI)) {
					documentiCondivisi.add(buildDocumentoDTO(documento));
				}
				if (documento.getTipologiaDocumento().getSezioneDocumentazione().getCodice()
						.equals(DbConst.SEZIONE_DOCUMENTAZIONE_DOCUMENTI_FIRMA)) {
					documentiFirma.add(buildDocumentoDTO(documento));
				}
			}
		}
		
		Boolean isToOrder = false;
		if(
				(  (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_AIA).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())
				|| (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_VIA).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())
				|| (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())
				)
			) {
			isToOrder = true;
		}
		LOGGER.info("isToOrder vale: " + isToOrder);
		if(isToOrder) {
			//Queste sono le sezioni
			List<DocumentoCartellaDTO> listaDocumentiDaOrdinare = buildDocumentoCartellaDTO(documentiAggiuntivi);
			
			//Ordinarla
			if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())) {
				
				List<DocumentoCartellaDTO> listaDocumentiOrdinati = ordinaDocumentiAggiuntiviUSR(listaDocumentiDaOrdinare);
				//Fare il set passando un List<DocumentoCartellaDTO> ordinata
				documentazioneDTO.setDocumentiAggiuntivi(listaDocumentiOrdinati);
				} 
			else if ((  (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_AIA).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())
				|| (""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_AMBIENTE_DECISORIA_VIA).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice()))) {
				List<DocumentoCartellaDTO> listaDocumentiOrdinati = ordinaDocumentiAggiuntiviAmbiente(listaDocumentiDaOrdinare);
				//Fare il set passando un List<DocumentoCartellaDTO> ordinata
				documentazioneDTO.setDocumentiAggiuntivi(listaDocumentiOrdinati);
			}
			
			
		}else {		
			documentazioneDTO.setDocumentiAggiuntivi(buildDocumentoCartellaDTO(documentiAggiuntivi));
		}
		documentazioneDTO.setDocumentiInterazione(buildDocumentoCartellaDTO(documentiInterazione));
		documentazioneDTO.setDocumentiIndizione(documentiIndizione);
		documentazioneDTO.setDocumentiPreIstruttoria(buildDocumentoCartellaDTO(documentiPreIstruttoria));
		documentazioneDTO.setDocumentiCondivisi(buildDocumentoCartellaDTO(documentiCondivisi));
		documentazioneDTO.setDocumentiFirma(buildDocumentoCartellaDTO(documentiFirma));
		return documentazioneDTO;
	}
	
	/**
	 * Raggruppamento dei documenti per campo cartella
	 * @param documentiDTO
	 * @return
	 */
	private List<DocumentoCartellaDTO> buildDocumentoCartellaDTO(List<DocumentoDTO> documentiDTO) {
		List<DocumentoCartellaDTO> cartellaDTO = new ArrayList<>();
		
		Map<String, List<DocumentoDTO>> map = new HashMap<String, List<DocumentoDTO>>();
		for (DocumentoDTO documentoDTO : documentiDTO) {
			String cartella = documentoDTO.getCartella() != null ? documentoDTO.getCartella() : "default";
			
			LOGGER.info(" cartella vale: " + cartella);
			if (map.get(cartella) == null)
				map.put(cartella, new ArrayList<>());
			map.get(cartella).add(documentoDTO);
		}
		
		for (String key: map.keySet()) {
			DocumentoCartellaDTO documentoCartellaDTO = new DocumentoCartellaDTO();
			documentoCartellaDTO.setNomeCartella(key);
			documentoCartellaDTO.setDocumenti(map.get(key));
			cartellaDTO.add(documentoCartellaDTO);
			
			LOGGER.info("nome cartella vale: " + documentoCartellaDTO.getNomeCartella());
		}

		return cartellaDTO;
	}

	public EnteAppoggioCsv buildEnteCSV(EnteCsvDTO enteDTO) {
		return getMapper().map(enteDTO, EnteAppoggioCsv.class);
	}
	
	public EnteUfficiAppoggioCsv buildEnteUfficiCSV(EnteUfficiCsvDTO enteDTO) {
		return getMapper().map(enteDTO, EnteUfficiAppoggioCsv.class);
	}

	public EventoFileDTO buildEventoFileDTO(Documento documento, String tipoEvento,
			List<Partecipante> listaPartecipanti) {
		EventoFileDTO evento = new EventoFileDTO();
		evento.setData(LocalDate.now().toString());
		List<LabelValue> listaDestinatari = listaPartecipanti.stream()
				.map(p -> new LabelValue(Integer.toString(p.getIdPartecipante()), p.getDescEnteCompetente()))
				.collect(Collectors.toList());
		evento.setListaDestinatari(listaDestinatari);
		evento.setTipoEvento(tipoEvento);
		evento.setProtocollo(documento.getNumeroProtocollo());
		return evento;
	}
	
	public EventoFileDTO buildEventoFileDTO(Documento documento, String tipoEvento,
			List<Partecipante> listaPartecipanti, boolean ammProc) {
		EventoFileDTO evento = new EventoFileDTO();
		evento.setData(LocalDate.now().toString());
		List<Partecipante> partecipantiSenzaAmmProc = new ArrayList<Partecipante>(); 
		
		String descRuoloPartecipante = "";
		
		for(Partecipante part : listaPartecipanti) {
			descRuoloPartecipante = part.getRuoloPartecipante().getDescrizione();
			LOGGER.debug("[CDST - 114 - DocumentoBuilder - buildEventoFileDTO] descRuoloPartecipante: " + descRuoloPartecipante);
			if(!descRuoloPartecipante.equals(descrizione)) {
				partecipantiSenzaAmmProc.add(part);
			} else
				LOGGER.debug("[CDST - 114 - DocumentoBuilder - buildEventoFileDTO] Ho trovato l'amministrazione procedente:" + part.getDescEnteCompetente());
		}
		
		if(listaPartecipanti != null)
			LOGGER.debug("[CDST - 114 - DocumentoBuilder - buildEventoFileDTO] num partecipanti iniziale: " + listaPartecipanti.size());
		if(partecipantiSenzaAmmProc != null)
			LOGGER.debug("[CDST - 114 - DocumentoBuilder - buildEventoFileDTO] num partecipanti senza amministrazione procedente: " + partecipantiSenzaAmmProc.size());
		
		List<LabelValue> listaDestinatari = partecipantiSenzaAmmProc.stream()
				.map(p -> new LabelValue(Integer.toString(p.getIdPartecipante()), p.getDescEnteCompetente()))
				.collect(Collectors.toList());
		evento.setListaDestinatari(listaDestinatari);
		evento.setTipoEvento(tipoEvento);
		evento.setProtocollo(documento.getNumeroProtocollo());
		return evento;
	}
	
	
	/*
	 *  calcolo dello stato e del proprietario
	 */
	
	public DocumentoDTO getStatusAndOwner(Documento documento){
		
		DocumentoDTO documentoDTO = new DocumentoDTO();
		//Trovo il proprietario
		
		PersonaDTO proprietario = firmaAdapter.getOwnerDoc(documento.getIdDocumento());	
		
		//Calcolo lo stato
		String statoCalcolato = "LOCKED";
		RegistroFirmaAdapter rfa = registroFirmaAdapterRepository.lastRegistroFirmaAdapterByidDoc(documento.getIdDocumento());
	
		if(rfa!=null){
			String status = rfa.getStato();
			
			FirmaDTO firma = new FirmaDTO();
			
			firma.setIdDocumento(documento.getIdDocumento());
			String sessioneFirma = firmaService.getLastSigningSession(firma);
			firma.setSessioneFirma(sessioneFirma);
			
			String cfProprietario = proprietario.getCodiceFiscale();
			
			String cfUtenteRichiedente = utenteService.getAuthenticatedUser().getCodiceFiscale();
			
			Boolean isCreatoreCds = utenteService.getAuthenticatedUser().getProfilo().getTipoProfilo().getCodice()
					.equals(Integer.toString(DbConst.TIPO_PROFILO_CREATORE_CDS));
			Boolean isResponsabile =  utenteService.getAuthenticatedUserAsResponsibleOfConference(documento.getConferenza()).getProfilo().getTipoProfilo().getCodice()
					.equals(Integer.toString(DbConst.TIPO_PROFILO_RESPONSABILE_CONFERENZA));
			
			Boolean richiedenteIsFirmatario = SignUtil.isFirmatario(documento, cfUtenteRichiedente);
			
			Utente utente = utenteService.findByCodiceFiscale(cfUtenteRichiedente);
			RegistroFirmaAdapter registroFirmaAdapterSigned = registroFirmaAdapterRepository.getRegistroFirmaAdapterByTokenAndUserId(firma.getSessioneFirma(), utente.getIdUtente());
			documentoDTO.setFileSignedFromUser(registroFirmaAdapterSigned != null);

			if(isCreatoreCds || isResponsabile) {
				
				statoCalcolato = (registroFirmaSessionSignerService.isSessionClosed(firma))? DbConst.COMPLETED : status;
				
			}else if(richiedenteIsFirmatario) {
				
				
				Boolean hasSigned = (registroFirmaAdapterSigned != null);
						
				if(registroFirmaSessionSignerService.isSessionClosed(firma)) {
					statoCalcolato = DbConst.COMPLETED;
				}else if(hasSigned) {
					statoCalcolato = DbConst.SIGNED;
				}else if(cfProprietario.equals(cfUtenteRichiedente)){
					statoCalcolato = (status.equals(DbConst.LOCKED))? DbConst.SIGNING: DbConst.UNLOCKED;
				}else {
					statoCalcolato = (status.equals(DbConst.LOCKED))? DbConst.LOCKED: DbConst.UNLOCKED;
				}
				
			}else {
				statoCalcolato = "LOCKED";
			}			
			
		}
		

		documentoDTO.setStato(statoCalcolato);
		documentoDTO.setProprietario(proprietario);	
		
		return documentoDTO;
		
	}
	
	
	private String getMD5(DocumentoFileDTO documentoFileDTO) {
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(documentoFileDTO.getFile().getBytes());
					
		    return org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
		}
		catch(Throwable skip) {
			System.out.println("getMD5 exception: " + skip.getMessage() + " "  + Arrays.toString(skip.getStackTrace()));
		}
		finally {
			try { is.close(); } catch (Exception ignore) { }
		}
		
		return null;
	}

	
	/**
	 * Ordina i documenti aggiuntivi usr
	 * @param listaDocumenti
	 * @return
	 */
	private List<DocumentoCartellaDTO> ordinaDocumentiAggiuntiviUSR(List<DocumentoCartellaDTO> listaDocumenti){
		List<DocumentoCartellaDTO> listaDocumentiOrdinati = new ArrayList<>();
		List<DocumentoCartellaDTO> listaDocumentiToOrder = new ArrayList<>();
		//List<DocumentoCartellaDTO> listaDocumentiToNoOrder = new ArrayList<>();
		for (DocumentoCartellaDTO documento : listaDocumenti) {
			if(!documento.getNomeCartella().equals("default"))
				listaDocumentiToOrder.add(documento);
			else
				listaDocumentiOrdinati.add(documento);

		}
		
		for(int i=0; i<listaDocumentiToOrder.size(); i++) {

			LOGGER.info("la lista da ordinare è questa: " +listaDocumentiToOrder.get(i).getNomeCartella());
		}
		Collections.sort(listaDocumentiToOrder, new DocumentoComparator());
		//listaDocumentiOrdinati.addAll(listaDocumentiToNoOrder);
		listaDocumentiOrdinati.addAll(listaDocumentiToOrder);
		

		
		for(int j=0; j<listaDocumentiOrdinati.size(); j++) {

			LOGGER.info("la lista ordinata è questa: " +listaDocumentiOrdinati.get(j).getNomeCartella());
		}
		return listaDocumentiOrdinati;
	}
	
	/**
	 * Ordina per data dalla maggiore alla inferiore, i null in fondo
	 */
	private static class DocumentoComparator implements Comparator<DocumentoCartellaDTO> {

		@Override
		public int compare(DocumentoCartellaDTO o1, DocumentoCartellaDTO o2) {
			
			
			//Devo estrarre la data
				String[] campiNomeSezione1 = o1.getNomeCartella().split("[|]");
				String[] campiNomeSezione2 = o2.getNomeCartella().split("[|]");
				String data1;
				String data2;

					data1 = campiNomeSezione1[1];
					Date dateConverted1 = null;
					try {
						dateConverted1 = new SimpleDateFormat("dd/MM/yyyy").parse(data1);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

					data2 = campiNomeSezione2[1];	
					Date dateConverted2 = null;
					try {
						dateConverted2 = new SimpleDateFormat("dd/MM/yyyy").parse(data2);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			return 	(null != dateConverted1 &&  null !=  dateConverted2) 	?
								(dateConverted1.after(dateConverted2) ? -1 : 1)	 :
					(null == dateConverted1 && null !=  dateConverted2) 	?  1 :
					(null != dateConverted1 && null ==  dateConverted2)		? -1 : 0;
		}
	}
	
	
	
	/**
	 * Ordina i documenti aggiuntivi usr
	 * @param listaDocumenti
	 * @return
	 */
	private List<DocumentoCartellaDTO> ordinaDocumentiAggiuntiviAmbiente(List<DocumentoCartellaDTO> listaDocumenti){
		List<DocumentoCartellaDTO> listaDocumentiOrdinati = new ArrayList<>();
		List<DocumentoCartellaDTO> listaDocumentiToOrder = new ArrayList<>();
		//List<DocumentoCartellaDTO> listaDocumentiToNoOrder = new ArrayList<>();
		for (DocumentoCartellaDTO documento : listaDocumenti) {
			if(!documento.getNomeCartella().equals("default"))
				listaDocumentiToOrder.add(documento);

		}
		
		for(int i=0; i<listaDocumentiOrdinati.size(); i++) {

			LOGGER.info("la lista da ordinare è questa: " +listaDocumentiToOrder.get(i).getNomeCartella());
		}
		Collections.sort(listaDocumentiToOrder, new DocumentoComparatorAmbiente());
		//listaDocumentiOrdinati.addAll(listaDocumentiToNoOrder);
		listaDocumentiOrdinati.addAll(listaDocumentiToOrder);
		

		
		for(int j=0; j<listaDocumentiOrdinati.size(); j++) {

			LOGGER.info("la lista ordinata è questa: " +listaDocumentiOrdinati.get(j).getNomeCartella());
		}
		return listaDocumentiOrdinati;
	}
	
	/**
	 * Ordina per data dalla maggiore alla inferiore, i null in fondo
	 */
	private static class DocumentoComparatorAmbiente implements Comparator<DocumentoCartellaDTO> {

		@Override
		public int compare(DocumentoCartellaDTO o1, DocumentoCartellaDTO o2) {
			
			
			//Devo estrarre la data
				String[] campiNomeSezione1 = o1.getNomeCartella().split("[|]");
				String[] campiNomeSezione2 = o2.getNomeCartella().split("[|]");
				String dataSplittata1;
				String dataSplittata2;
				String data1;
				String data2;

					dataSplittata1 = campiNomeSezione1[2].trim();
					data1 = dataSplittata1.replace("-", "/");
					Date dateConverted1 = null;
					try {
						dateConverted1 = new SimpleDateFormat("yyyy/MM/dd").parse(data1);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 

					dataSplittata2 = campiNomeSezione2[2].trim();	
					data2 = dataSplittata2.replace("-", "/");
					Date dateConverted2 = null;
					try {
						dateConverted2 = new SimpleDateFormat("yyyy/MM/dd").parse(data2);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			return 	(null != dateConverted1 &&  null !=  dateConverted2) 	?
								(dateConverted1.after(dateConverted2) ? -1 : 1)	 :
					(null == dateConverted1 && null !=  dateConverted2) 	?  1 :
					(null != dateConverted1 && null ==  dateConverted2)		? -1 : 0;
		}
	}
	
	public String mapColonnaOrdinamentoDocumentoRicerca(String colonna) {
		if(colonna.equals("name")) {
			colonna = "nome";
		}
		return colonna;
	}
}
