package cdst_be_marche.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cdst_be_marche.DTO.DocumentazioneDTO;
import cdst_be_marche.DTO.DocumentoDTO;
import cdst_be_marche.DTO.DocumentoFileDTO;
import cdst_be_marche.DTO.FonteFile;
import cdst_be_marche.DTO.ModalitaSalvataggioFile;
import cdst_be_marche.DTO.bean.Errore;
import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.builder.ConferenzaBuilder;
import cdst_be_marche.builder.DocumentoBuilder;
import cdst_be_marche.exception.InvalidFieldEx;
import cdst_be_marche.exception.NotEditableException;
import cdst_be_marche.file.WriteDocService;
import cdst_be_marche.mail.EmailRepositoryService;
import cdst_be_marche.mail.JavaMailSenderConfigurator;
import cdst_be_marche.model.Accreditamento;
import cdst_be_marche.model.Conferenza;
import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Mailer;
import cdst_be_marche.model.Partecipante;
import cdst_be_marche.model.RegistroDocumento;
import cdst_be_marche.model.Template;
import cdst_be_marche.repository.AccreditamentoRepository;
import cdst_be_marche.repository.ConferenzaRepository;
import cdst_be_marche.repository.DocumentoRepository;
import cdst_be_marche.repository.DocumentoSpecificationsBuilder;
import cdst_be_marche.repository.MailerRepository;
import cdst_be_marche.repository.PartecipanteRepository;
import cdst_be_marche.repository.RegistroDocumentoRepository;
import cdst_be_marche.repository.SearchCriteria;
import cdst_be_marche.repository.StatoRepository;
import cdst_be_marche.repository.TemplateRepository;
import cdst_be_marche.repository.TipoEventoRepository;
import cdst_be_marche.repository.TipologiaDocumentoRepository;
import cdst_be_marche.util.DbConst;
import cdst_be_marche.validator.DocumentoValidator;

@Transactional
@Service
public class DocumentoService extends _BaseService {

	@Autowired
	DocumentoBuilder documentoBuilder;

	@Autowired
	DocumentoValidator documentoValidator;

	@Autowired
	DocumentoRepository documentoRepository;

	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;

	@Autowired
	private FileSystemService fileSystemService;

	@Autowired
	private RegistroDocumentoService registroDocumentoService;

	@Autowired
	EmailRepositoryService emailRepositoryService;

	@Autowired
	PartecipanteRepository partecipanteRepo;

	@Autowired
	ConferenzaRepository conferenzaRepo;

	@Autowired
	StatoRepository statoRepo;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	JavaMailSenderConfigurator mailer;

	@Autowired
	UtenteService utenteService;

	@Autowired
	EventoService eventoService;

	@Autowired
	ConferenzaBuilder conferenzaBuilder;

	@Autowired
	ConferenzaService confService;

	@Autowired
	TipologiaDocumentoRepository tipologiaDocRepo;

	@Autowired
	AccreditamentoRepository accreditamentoRepository;
	
	@Autowired
	MailerRepository mailerRepo;
	
	@Autowired
	TemplateRepository templateRepo;
	
	@Autowired
	TipoEventoRepository tipoEventoRepo;
	
	@Autowired
	WriteDocService writeDocService;

	public DocumentoDTO creaDocumento(DocumentoFileDTO documentoFileDTO, Integer idConferenza) {
		Documento documento = creaDocumentoMultipartFile(documentoFileDTO, idConferenza);
		if (documento != null && isIndizione(documentoFileDTO)) {
			doIndizione(documento.getConferenza(), documento);
			/*
			 * creazione evento convocazione conferenza
			 */
			this.eventoService.creaEventoConvocazioneConferenza(documento);
		}
		return documentoBuilder.buildDocumentoDTO(documento);
	}

	/**
	 * <pre>
	 * Creazione di un documento e del relativo registro a partire da un riferimento esistente:
	 * - il riferimento di un file già presente su Alfresco
	 * - il path di un file già presente nel filesystem
	 * - ...
	 * </pre>
	 * 
	 * @param documentoDTO
	 * @param idAccreditamento
	 * @return
	 */
	public DocumentoDTO creaDocumentoRiferimento(DocumentoDTO documentoDTO, String riferimentoEsterno,
			ModalitaSalvataggioFile modalita, FonteFile fonte, Integer idConferenza, Integer idAccreditamento) {
		Conferenza conferenza = conferenzaRepo.findByIdConferenza(idConferenza);
		Accreditamento owner = accreditamentoRepository.findById(idAccreditamento).orElse(null);
		if (conferenza != null) {
			Documento documentoSaved = documentoRepository
					.save(documentoBuilder.buildDocumento(documentoDTO, conferenza, owner));
			saveRegistro(riferimentoEsterno, documentoSaved, modalita, fonte);
			return documentoBuilder.buildDocumentoDTO(documentoSaved);
		}
		return null;
	}

	/**
	 * Creazione Documento a partire da un File (MultipartFile). Il metodo si occupa
	 * anche di registrare il file nella modalità di salvataggio configurata.
	 * 
	 * @param documentoFileDTO
	 * @param idConferenza
	 * @return
	 */
	public Documento creaDocumentoMultipartFile(DocumentoFileDTO documentoFileDTO, Integer idConferenza) {
		if (validateConferenza(documentoFileDTO, idConferenza)) {
			Conferenza conferenza = conferenzaRepo.findByIdConferenza(idConferenza);
			if (validate(documentoFileDTO)) {
				DocumentoDTO documentoDTO = documentoBuilder.buildDocumentoDTO(documentoFileDTO);
				Documento documentoSaved = documentoRepository.save(documentoBuilder.buildDocumento(documentoDTO,
						conferenza, utenteService.getAccreditamento(conferenza)));
				creaVisibilitaPartecipanti(documentoDTO, documentoSaved);
				creaRegistroDocumento(documentoFileDTO.getFile(), documentoSaved);
				return documentoSaved;
			}
		}
		return null;
	}

	private void doIndizione(Conferenza conferenza, Documento documento) {
		String subject = "[MeetPAD] Indizione conferenza";
		// String from = "MeetPAD@eng.it";
		String from = mailer.getFrom();
		String textmessage = "Messaggio di Prova \n procedere all'autoaccreditamento  ";
		if (mailer.isSendEnabled()) {
			emailRepositoryService.sendMailIndizioneForConference(conferenza.getIdConferenza(), from, textmessage,
					subject, null, documento);
		}

		/*
		 * aggiornamento dello stato
		 */
		conferenza.setStato(statoRepo.findById(new Integer(DbConst.STATO_VALUTAZIONE).toString()).get());
		conferenzaRepo.save(conferenza);

	}

	private boolean isIndizione(DocumentoFileDTO documentoFileDTO) {
		return documentoFileDTO.getTipoDocumento() != null
				&& documentoFileDTO.getTipoDocumento().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE);
	}

	private boolean validate(DocumentoFileDTO documentoDTO) {
		List<Errore> errors = documentoValidator.validateCreaDocumento(documentoDTO, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		return true;
	}

	private boolean validateConferenza(DocumentoFileDTO documentoFileDTO, Integer idConferenza) {
		List<Errore> errors = documentoValidator.validateConferenza(idConferenza, isIndizione(documentoFileDTO),
				messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		return true;
	}

	private void creaVisibilitaPartecipanti(DocumentoDTO documentoDTO, Documento documentoSaved) {
		documentoSaved.getVisibilitaPartecipanti().clear();
		for (LabelValue labelValue : documentoDTO.getVisibilitaPartecipanti()) {
			Optional<Partecipante> partecipante = partecipanteRepo.findById(new Integer(labelValue.getKey()));
			if (partecipante.isPresent()) {
				documentoSaved.getVisibilitaPartecipanti().add(partecipante.get());
			}
		}
		documentoRepository.save(documentoSaved);
	}

	/**
	 * salvataggio file nella modalità indicata e salvataggio riga di registro
	 * 
	 * @param documentoDTO
	 * @param documentoSaved
	 * @param modalita
	 * @param fonte
	 * @return
	 */
	public RegistroDocumento creaRegistroDocumento(MultipartFile file, Documento documentoSaved) {
		// TODO: rendere la modalità di salvataggio file parametrizzabile
		ModalitaSalvataggioFile modalita = ModalitaSalvataggioFile.Filesystem;
		FonteFile fonte = FonteFile.meetpad;
		String riferimentoEsterno = storeFile(modalita, file, documentoSaved);
		return saveRegistro(riferimentoEsterno, documentoSaved, modalita, fonte);
	}

	/**
	 * salvataggio file nella modalità indicata (filesystem, alfresco, ...)
	 * 
	 * @param modalita
	 * @param file
	 * @param documentoSaved
	 * @return
	 */
	private String storeFile(ModalitaSalvataggioFile modalita, MultipartFile file, Documento documento) {
		if (modalita.equals(ModalitaSalvataggioFile.Filesystem)) {
			return fileSystemService.storeFile(file, documento);
		} else if (modalita.equals(ModalitaSalvataggioFile.Alfresco)) {
			// TODO: implementare upload file su Alfresco
		}
		return null;
	}

	/**
	 * salvataggio della riga di registro
	 * 
	 * @param riferimentoEsterno
	 * @param documentoSaved
	 * @param modalita
	 * @param fonte
	 * @return
	 */
	private RegistroDocumento saveRegistro(String riferimentoEsterno, Documento documentoSaved,
			ModalitaSalvataggioFile modalita, FonteFile fonte) {
		RegistroDocumento registroDocumento = registroDocumentoRepository.save(documentoBuilder.buildRegistroDocumento(
				riferimentoEsterno, documentoSaved, toCodiceFonte(fonte), toCodiceTipo(modalita)));
		return registroDocumento;
	}

	private String toCodiceTipo(ModalitaSalvataggioFile modalita) {
		if (modalita != null) {
			if (modalita.equals(ModalitaSalvataggioFile.Filesystem)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_FILESYSTEM;
			} else if (modalita.equals(ModalitaSalvataggioFile.Alfresco)) {
				return DbConst.REGISTRO_DOCUMENTO_TIPO_ALFRESCO;
			}
		}
		return null;
	}

	private String toCodiceFonte(FonteFile fonte) {
		if (fonte != null) {
			if (fonte.equals(FonteFile.meetpad)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_MEETPAD;
			} else if (fonte.equals(FonteFile.suap)) {
				return DbConst.REGISTRO_DOCUMENTO_FONTE_SUAP;
			}
		}
		return null;
	}

	public DocumentoDTO modificaDocumento(DocumentoFileDTO documentoFileDTO, Integer id) {
		// TODO Numero e data protocollo non sono modificabili per ora
		if (validateModificaDocumento(documentoFileDTO, id)) {
			DocumentoDTO documentoDTO = documentoBuilder.buildDocumentoDTO(documentoFileDTO);
			documentoDTO.setId(id);
			Documento documentoSaved = documentoRepository
					.save(documentoBuilder.buildDocumento(documentoDTO, null, null));
			creaVisibilitaPartecipanti(documentoDTO, documentoSaved);
			return this.documentoBuilder.buildDocumentoDTO(documentoSaved);
		}

		return null;
	}

	private boolean validateModificaDocumento(DocumentoFileDTO documentoDTO, Integer id) {
		List<Errore> errors = documentoValidator.validateModificaDocumento(documentoDTO, messageSource, id);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		return true;
	}

	public String eliminaDocumento(Integer id) {
		Documento documento = this.documentoRepository.findById(id).orElse(null);
		if (documento != null) {
			if (documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)) {
				throw new NotEditableException("Il file di indizione non può essere rimosso");
			}
			documento.setDataFine(new Date());
			this.documentoRepository.save(documento);
		}
		return "ok";
	}

	public Resource loadFileAsResource(String tokenFile) {
		if (validate(tokenFile)) {
			return registroDocumentoService.loadFileAsResource(tokenFile);
		}
		return null;
	}

	private boolean validate(String tokenFile) {
		List<Errore> errors = documentoValidator.validateTokenDocumento(tokenFile, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		return true;
	}

	/**
	 * Restituisce il registro documento associato al documento in questione. TODO:
	 * quando saranno previste più modalità di conservazione del documento saranno
	 * presenti più righe di registro documento per ogni documento in tal caso
	 * andrebbe implementata la logica per decidere quale riga di registro
	 * utilizzare. Attualmente viene restituita l'unica riga presente
	 * 
	 * @param documento
	 * @return
	 */
	public RegistroDocumento getRegistroDocumento(Documento documento) {
		return documentoBuilder.getRegistroDocumento(documento);
	}

	public DocumentazioneDTO findAllDocuments(Integer idConference, Boolean allDocuments) {
		Conferenza conferenza = this.confService.findConferenzaByIdFiltrata(idConference);
		Accreditamento accreditamento = this.utenteService.getAccreditamento(conferenza);
		List<Documento> listaDocumentiOwner = this.documentoRepository
				.findAll(filtroDocumentiOwner(conferenza, accreditamento));
		List<Documento> listaDocumenti = listaDocumentiOwner;
		List<Documento> listaDocumentiVisibiliSempre = this.documentoRepository
				.findAll(filtroDocumentiVisibiliSempre(conferenza, accreditamento));
		listaDocumentiVisibiliSempre.stream().forEach(d -> listaDocumenti.add(d));
		List<Documento> listaDocumentiVisibilitaRistretta = this.documentoRepository
				.findAll(filtroDocumentiVisibilitaRistretta(conferenza, accreditamento));
		listaDocumentiVisibilitaRistretta.stream().forEach(d -> listaDocumenti.add(d));
		if (allDocuments == Boolean.TRUE) {
			List<Documento> listaDocumentiAggiuntivi = this.documentoRepository
					.findAll(filtroDocumentiAggiuntivi(conferenza, accreditamento));
			listaDocumentiAggiuntivi.stream().forEach(d -> listaDocumenti.add(d));
		}
		return this.documentoBuilder.buildDocumentazioneDTO(listaDocumenti);
	}

	private Specification<Documento> filtroDocumentiOwner(Conferenza conferenza, Accreditamento accreditamento) {
		List<SearchCriteria> parametri = new ArrayList<>();
		parametri.add(new SearchCriteria("conferenza", "=", conferenza));
		parametri.add(new SearchCriteria("owner", "=", accreditamento));
		DocumentoSpecificationsBuilder builder = new DocumentoSpecificationsBuilder();
		return builder.buildAnd(parametri);
	}

	private Specification<Documento> filtroDocumentiVisibiliSempre(Conferenza conferenza,
			Accreditamento accreditamento) {
		List<SearchCriteria> parametri = new ArrayList<>();
		parametri.add(new SearchCriteria("conferenza", "=", conferenza));
		parametri.add(new SearchCriteria("owner", "!=", accreditamento));
		parametri.add(new SearchCriteria("visibilitaRistretta", "=", Boolean.FALSE));
		DocumentoSpecificationsBuilder builder = new DocumentoSpecificationsBuilder();
		return builder.buildAnd(parametri);
	}

	private Specification<Documento> filtroDocumentiVisibilitaRistretta(Conferenza conferenza,
			Accreditamento accreditamento) {
		List<SearchCriteria> parametri = new ArrayList<>();
		parametri.add(new SearchCriteria("conferenza", "=", conferenza));
		parametri.add(new SearchCriteria("owner", "!=", accreditamento));
		parametri.add(new SearchCriteria("visibilitaRistretta", "=", Boolean.TRUE));
		List<SearchCriteria> parametriIdDocumento = new ArrayList<>();
		accreditamento.getPartecipante().getVisibilitaDocumenti().stream()
				.forEach(d -> parametriIdDocumento.add(new SearchCriteria("idDocumento", "=", d.getIdDocumento())));
		DocumentoSpecificationsBuilder builder = new DocumentoSpecificationsBuilder();
		return builder.buildAnd(parametri).and(builder.buildOr(parametriIdDocumento));
	}

	private Specification<Documento> filtroDocumentiAggiuntivi(Conferenza conferenza, Accreditamento accreditamento) {
		List<SearchCriteria> parametri = new ArrayList<>();
		parametri.add(new SearchCriteria("conferenza", "=", conferenza));
		parametri.add(new SearchCriteria("owner", "!=", accreditamento));
		parametri.add(new SearchCriteria("visibilitaRistretta", "=", Boolean.TRUE));
		List<SearchCriteria> parametriIdDocumento = new ArrayList<>();
		List<SearchCriteria> parametriTipologiaDoc = new ArrayList<>();
		accreditamento.getPartecipante().getVisibilitaDocumenti().stream()
				.forEach(d -> parametriIdDocumento.add(new SearchCriteria("idDocumento", "!=", d.getIdDocumento())));
		parametriTipologiaDoc.add(new SearchCriteria("tipologiaDocumento", "=",
				this.tipologiaDocRepo.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA).get()));
		parametriTipologiaDoc.add(new SearchCriteria("tipologiaDocumento", "=",
				this.tipologiaDocRepo.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE).get()));
		parametriTipologiaDoc.add(new SearchCriteria("tipologiaDocumento", "=",
				this.tipologiaDocRepo.findById(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_PRE_ISTRUTTORIA).get()));
		DocumentoSpecificationsBuilder builder = new DocumentoSpecificationsBuilder();
		return builder.buildAnd(parametri).and(builder.buildAnd(parametriIdDocumento))
				.and(builder.buildOr(parametriTipologiaDoc));
	}

	public String creaTemplatePerDownload(Integer idConferenza, String tipoEvento) throws IOException {
		Template template = templateRepo.findByTipoEvento(tipoEventoRepo.findById(tipoEvento).get()).get(0);
		String esito = null;
		if (template.getNomeTemplateDownload() == null) {
			esito = "La tipologia di conferenza inserita non comprende un template";
		} else {
			String fileName = template.getNomeTemplateDownload() + ".docx";
			writeDocService.writeDocxFile(fileName, confService.findConferenzaByIdFiltrata(idConferenza));
		}		
		return esito;
	}

}
