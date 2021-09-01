package conferenza.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.DocumentazioneDTO;
import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.DocumentoFirmaDTO;
import conferenza.DTO.EventoFileDTO;
import conferenza.DTO.FonteFile;
import conferenza.DTO.ListaDocumentoFirmaDTO;
import conferenza.DTO.ModalitaSalvataggioFile;
import conferenza.DTO.RicercaDocumentoDTO;
import conferenza.DTO.bean.Errore;
import conferenza.DTO.bean.LabelValue;
import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.builder.ConferenzaBuilder;
import conferenza.builder.DocumentoBuilder;
import conferenza.builder.DocumentoFirmaMultiplaBuilder;
import conferenza.builder.EventoBuilder;
import conferenza.exception.InvalidFieldEx;
import conferenza.facade.STATO_DOCUMENTO;
import conferenza.file.WriteDocService;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.adapter.FirmaAdapter;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.repository.RegistroFirmaAdapterRepository;
import conferenza.firma.service.FirmaSemplificataService;
import conferenza.firma.service.FirmaService;
import conferenza.mail.EmailRepositoryService;
import conferenza.mail.JavaMailSenderConfigurator;
import conferenza.model.Accreditamento;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.DocumentoFirmaMultipla;
import conferenza.model.Evento;
import conferenza.model.Modello;
import conferenza.model.Partecipante;
import conferenza.model.RegistroDocumento;
import conferenza.model.Template;
import conferenza.model.TokenRegistroDocumento;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.CategoriaDocumentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.DocumentoSpecificationsBuilder;
import conferenza.repository.EnteAppoggioCSVRepository;
import conferenza.repository.EnteRepository;
import conferenza.repository.EnteUfficiAppoggioCSVRepository;
import conferenza.repository.EventoRepository;
import conferenza.repository.MailerRepository;
import conferenza.repository.ModelloRepository;
import conferenza.repository.PartecipanteRepository;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.repository.SearchCriteria;
import conferenza.repository.StatoRepository;
import conferenza.repository.TemplateRepository;
import conferenza.repository.TipoEventoRepository;
import conferenza.repository.TipologiaConferenzaSpecializzazioneRepository;
import conferenza.repository.TipologiaDocumentoRepository;
import conferenza.repository.TokenRegistroDocumentoRepository;
import conferenza.repository.UtenteRepository;
import conferenza.util.DbConst;
import conferenza.util.JsonUtil;
import conferenza.validator.DocumentoValidator;

@Transactional
@Service
public class DocumentoService extends _BaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoService.class);

	@Autowired
	DocumentAdapterService dcumentAdapterService;
	
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
	TokenRegistroDocumentoRepository tokenRegistroDocumentoRepository;
	
	@Autowired
	@Lazy
	EmailRepositoryService emailRepositoryService;
	
	@Autowired
	FirmaSemplificataService firmaSemplificataService;

	@Autowired
	FirmaAdapter firmaAdapterService;
	
	@Autowired
	RegistroFirmaAdapterRepository registroFirmaAdapterRepository;

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
	UtenteRepository utenteRepository;

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
	TipologiaConferenzaSpecializzazioneRepository tipologiaConferenzaSpecializzazioneRepo;
	
	@Autowired
	WriteDocService writeDocService;
	
	@Autowired
	EnteAppoggioCSVRepository enteAppoRepo;
	
	@Autowired
	EnteRepository enteRepo;
	
	@Autowired
	EnteUfficiAppoggioCSVRepository enteUfficiAppoRepo;
	
	@Autowired
	CategoriaDocumentoRepository categoriaDocRepo;

	@Autowired
	EventoBuilder eventoBuilder;
	
	@Autowired
	EventoRepository eventoRepository;
	
	@Autowired
	ModelloRepository modelloRepo;
	
	@Autowired
	FirmaService firmaService;
	
	@Autowired
	DocumentoFirmaMultiplaBuilder documentoFirmaMultiplaBuilder;
	
	@Autowired
	DocumentoFirmaMultiplaService documentoFirmaMultiplaService;
	
	public Documento creaDocumento(DocumentoFileDTO documentoFileDTO, Integer idConferenza) {
		Documento documento = creaDocumentoMultipartFile(documentoFileDTO, idConferenza, null, false);
		if (documento != null && isIndizione(documentoFileDTO)) {
			doIndizione(documento.getConferenza(), documento);
			/*
			 * creazione evento convocazione conferenza
			 */
			this.eventoService.creaEventoConvocazioneConferenza(documentoFileDTO, documento, idConferenza);
		}
		
		return documento;
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
	public DocumentoDTO creaDocumentoRiferimento(DocumentoDTO documentoDTO, String riferimentoEsterno, ModalitaSalvataggioFile modalita, FonteFile fonte, Integer idConferenza, Integer idAccreditamento) {
		Conferenza conferenza = conferenzaRepo.findByIdConferenza(idConferenza);
		Accreditamento owner = accreditamentoRepository.findById(idAccreditamento).orElse(null);
		if (conferenza != null) {
			Documento documentoSaved = documentoRepository.save(documentoBuilder.buildDocumento(documentoDTO, conferenza, owner));
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
		return creaDocumentoMultipartFile(documentoFileDTO, idConferenza, null, false);
	}
	
	public Documento creaDocumentoMultipartFile(DocumentoFileDTO documentoFileDTO, Integer idConferenza, String calamaioRemoteDocumentId, boolean isJustAttachment) {
		if (isJustAttachment || validateConferenza(documentoFileDTO, idConferenza)) {
			Conferenza conferenza = conferenzaRepo.findByIdConferenza(idConferenza);
			if(isJustAttachment || validate(documentoFileDTO)) {
				DocumentoDTO documentoDTO = getDocumentoDTO(documentoFileDTO);
				
				Documento documentoSaved;
				if(calamaioRemoteDocumentId == null) {
					//if(isAdditionalSign) {

					Documento doc = documentoBuilder.buildDocumento(documentoDTO, conferenza, utenteService.getAccreditamento(conferenza, true));
					documentoSaved = documentoRepository.save(doc);
					creaVisibilitaPartecipanti(documentoDTO, documentoSaved);
					creaFirmatari(documentoDTO, documentoSaved, documentoFileDTO.getFirmatari());
					creaRegistroDocumento(documentoFileDTO, documentoSaved);
					if(isDocumentoFirmato(documentoFileDTO)) {
						inizializzaSessioneDiFirma(documentoFileDTO, documentoSaved);
					}				
					
					return documentoSaved;
				}
				else
					creaRegistroDocumento(documentoFileDTO, documentoRepository.findById(Integer.parseInt(calamaioRemoteDocumentId)).get());
			}
		}
		return null;
	}
	
	public DocumentoDTO getDocumentoDTO(DocumentoFileDTO documentoFileDTO) {
		return documentoBuilder.buildDocumentoDTO(documentoFileDTO);
	}

	private void caricaDocumentoFirmato(DocumentoFileDTO documentoFileDTO, Documento documentoSaved) {
		
		try {
			
			//TODO valida utente
			
			RegistroFirmaAdapter registroFirmaAdapter = registroFirmaAdapterRepository.lastRegistroFirmaAdapterByidDoc(documentoSaved.getIdDocumento());
						
			FirmaDTO firmaDTO = new FirmaDTO();
			firmaDTO.setCrc(registroFirmaAdapter.getCrc());
			firmaDTO.setIdDocumento(documentoSaved.getIdDocumento());
			firmaDTO.setFile(documentoFileDTO.getFile());
			firmaDTO.setFileName(documentoFileDTO.getNomeFile());
			firmaDTO.setIdConferenza(documentoSaved.getConferenza().getIdConferenza());
			firmaDTO.setResource( new InputStreamResource(documentoFileDTO.getFile().getInputStream()) );
			
			Integer iTipoFirma=firmaSemplificataService.getTipoFirma(FirmaSemplificataService.TIPOFIRMA_FSSEMPLIFICATA);		
			firmaDTO.setFk_tipo_firma(iTipoFirma);		
					
			firmaSemplificataService.doActionUnlck(firmaDTO);
								
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Errore nell'inizializzazione della firma del documento ");
			//TODO se l'utente non è responsabile viene creato il documento ma fallisce l'inizializzazione della sessione di firma
			throw new InternalError();
		}
		
	}

	private void inizializzaSessioneDiFirma(DocumentoFileDTO documentoFileDTO, Documento documentoSaved) {
		
		try {
			
			FirmaDTO firmaDTO = new FirmaDTO();
			firmaDTO.setIdDocumento(documentoSaved.getIdDocumento());
			firmaDTO.setFile(documentoFileDTO.getFile());
			firmaDTO.setFileName(documentoFileDTO.getNomeFile());
			firmaDTO.setIdConferenza(documentoSaved.getConferenza().getIdConferenza());
			firmaDTO.setResource( new InputStreamResource(documentoFileDTO.getFile().getInputStream()) );
			
			//il primo lock è sempre di tipo firmasemplificata
			Integer iTipoFirma=firmaSemplificataService.getTipoFirma(FirmaSemplificataService.TIPOFIRMA_FSSEMPLIFICATA);		
			firmaDTO.setFk_tipo_firma(iTipoFirma);	
						
			//se il file caricato è firmato esegui l'unlock
			if(documentoFileDTO.getFirmato()) {	
				this.documentoValidator.validateSignedFile(documentoFileDTO, messageSource);
				firmaService.inizializzaSessioneDiFirma(firmaDTO);
				firmaSemplificataService.doActionUnlck(firmaDTO);
			}else {
				firmaService.inizializzaSessioneDiFirma(firmaDTO);
			}
					
			//inserimento dei firmatari nella sessione di firma
			firmaService.addPartecipanteInSigningSessionByAccreditamento(firmaDTO, documentoSaved.getFirmatari());	
			
		}catch (InvalidFieldEx e) {
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Errore nell'inizializzazione della firma del documento");
			throw new InternalError();
		}
		
	}
	
	private void creaFirmatari(DocumentoDTO documentoDTO, Documento documentoSaved, String firmatari) {
		if (firmatari != null) {
			documentoDTO
			.setFirmatari(JsonUtil.jsonToListLabelValue(firmatari));
		}
		documentoSaved.getFirmatari().clear();
		for (LabelValue labelValue : documentoDTO.getFirmatari()) {
			Optional<Accreditamento> accreditamento = accreditamentoRepository.findById(new Integer(labelValue.getKey()));
			if (accreditamento.isPresent()) {
				documentoSaved.getFirmatari().add(accreditamento.get());
			}
		}
		documentoRepository.save(documentoSaved);
	}

	private void doIndizione(Conferenza conferenza, Documento documento) {
		/*
		 * imposto la categoria del documento indizione
		 */
		documento.setCategoriaDocumento(categoriaDocRepo
				.findById(Integer.toString(DbConst.CATEGORIA_DOCUMENTO_DOCUMENTO_INDIZIONE)).orElse(null));

		/*
		 * aggiornamento dello stato
		 */
		conferenza.setStato(statoRepo.findById(new Integer(DbConst.STATO_VALUTAZIONE).toString()).get());
		conferenzaRepo.save(conferenza);
	}

	public void notificaMailIndizione(Conferenza conferenza, Documento documento) {
		String subject = "[Conferenza] Indizione conferenza";
		String from = mailer.getFrom();
		
		String textmessage = "Messaggio di Prova \n procedere all'autoaccreditamento  ";

		if (mailer.isSendEnabled()) {
			emailRepositoryService.sendMailIndizioneForConference(conferenza.getIdConferenza(), from, textmessage,
					subject, null, documento);
		}
	}

	public boolean isIndizione(DocumentoFileDTO documentoFileDTO) {
		return documentoFileDTO.getTipoDocumento() != null
				&& documentoFileDTO.getTipoDocumento().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE);
	}
	
	public boolean isDocumentoFirmato(DocumentoFileDTO documentoFileDTO) {
		return documentoFileDTO.getTipoDocumento() != null
				&& documentoFileDTO.getTipoDocumento().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_FIRMATO);
	}

	public boolean validate(DocumentoFileDTO documentoDTO) {
		if(documentoDTO.getFile() !=null) {
		List<Errore> errors = documentoValidator.validateCreaDocumento(documentoDTO, messageSource);
			if (errors.size() != 0) {
				throw new InvalidFieldEx(errors);
			}
		}
		return true;
	}

	private boolean validateConferenza(DocumentoFileDTO documentoFileDTO, Integer idConferenza) {
		if(documentoFileDTO != null) {
		List<Errore> errors = documentoValidator.validateConferenza(idConferenza, documentoFileDTO,
				messageSource);
			if (errors.size() != 0) {
				throw new InvalidFieldEx(errors);
			}
		}
		return true;
	}

	public void creaVisibilitaPartecipanti(DocumentoDTO documentoDTO, Documento documentoSaved) {
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
	 * 
	 * salvataggio file nella modalità indicata e salvataggio riga di registro
	 * 
	 * @param documentoDTO
	 * @param documentoSaved
	 * @param modalita
	 * @param fonte
	 * @return
	 */
	public RegistroDocumento creaRegistroDocumento(DocumentoFileDTO documentoFileDTO, Documento documentoSaved) {
		return creaRegistroDocumento(documentoFileDTO, documentoSaved, true);
	}
	
	public RegistroDocumento creaRegistroDocumento(DocumentoFileDTO documentoFileDTO, Documento documentoSaved, boolean storeregistro) {
		return creaRegistroDocumento(documentoFileDTO, documentoSaved, true, null);
	}
	
	public RegistroDocumento creaRegistroDocumento(DocumentoFileDTO documentoFileDTO, Documento documentoSaved, boolean storeregistro, Resource risorsa) {
		// TODO: rendere la modalità di salvataggio file parametrizzabile
		ModalitaSalvataggioFile modalita = modalitaSalvataggio(documentoFileDTO.getFile(), documentoFileDTO.getUrl());
		FonteFile fonte = FonteFile.conferenza;
		String riferimentoEsterno = null;
		LOGGER.debug((documentoFileDTO.getModello()==null) + " "+ documentoFileDTO.getUrl() );
		if (documentoFileDTO.getModello() == null || documentoFileDTO.getModello().isEmpty()) {
			LOGGER.debug("modello null o vuoto");
			riferimentoEsterno = storeFile(documentoFileDTO, documentoSaved, modalita, risorsa);
		} else {
			LOGGER.debug("modello");
			riferimentoEsterno = fileSystemService.storeFileDaModello(documentoFileDTO, documentoSaved);
		}
		
		if(!storeregistro)
			return null;

		return saveRegistro(riferimentoEsterno, documentoSaved, modalita, fonte);
	}

	/**
	 * 
	 * DEFAULT: per retrocompatibilità
	 * ModalitaSalvataggioFile.Filesystem
	 * FonteFile.conferenza
	 * 
	 * @param file
	 * @param documentoSaved
	 * @param modalita
	 * @return
	 */
	public RegistroDocumento creaRegistroDocumento(MultipartFile file, Documento documentoSaved,
			ModalitaSalvataggioFile modalita, FonteFile fonte) {
		// TODO: rendere la modalità di salvataggio file parametrizzabile
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		if (modalita == null)
			modalita = ModalitaSalvataggioFile.Filesystem;
		if (fonte == null)
			fonte = FonteFile.conferenza;
		String riferimentoEsterno = storeFile(documentoFileDTO, documentoSaved, modalita);
		return saveRegistro(riferimentoEsterno, documentoSaved, modalita, fonte);
	}
	
	
	private String storeFile(DocumentoFileDTO documentoFileDTO, Documento documento, ModalitaSalvataggioFile modalitaSalvataggioFile) {
		return storeFile(documentoFileDTO, documento, modalitaSalvataggioFile, null);
	}
	
	/**
	 * salvataggio file nella modalità indicata (filesystem, alfresco, ...)
	 * 
	 * @param modalita
	 * @param file
	 * @param documentoSaved
	 * @return
	 */
	private String storeFile(DocumentoFileDTO documentoFileDTO, Documento documento, ModalitaSalvataggioFile modalitaSalvataggioFile, Resource risorsa) {
		if (modalitaSalvataggioFile.equals(ModalitaSalvataggioFile.Filesystem)) {
			if (risorsa == null) {
				return fileSystemService.storeFile(documentoFileDTO.getFile(), documento);
			} else {
				return fileSystemService.storeFile(risorsa, documento);
			}
			
		} else if (modalitaSalvataggioFile.equals(ModalitaSalvataggioFile.Alfresco)) {
			// TODO: implementare upload file su Alfresco
		} else if (modalitaSalvataggioFile.equals(ModalitaSalvataggioFile.Url)) {
			return documentoFileDTO.getUrl();
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
		return DocumentAdapterService.getCodiceTipoDocumentazione(modalita);
	}

	private String toCodiceFonte(FonteFile fonte) {
		return DocumentAdapterService.getCodiceFonte(fonte);
	}

	public DocumentoDTO modificaDocumento(DocumentoFileDTO documentoFileDTO, Integer id) {
		// TODO Numero e data protocollo non sono modificabili per ora
		if (validateModificaDocumento(documentoFileDTO, id)) {
			DocumentoDTO documentoDTO = documentoBuilder.buildDocumentoDTO(documentoFileDTO);
			documentoDTO.setId(id);
			Documento documentoSaved = documentoRepository
					.save(documentoBuilder.buildDocumento(documentoDTO, null, null));
			creaVisibilitaPartecipanti(documentoDTO, documentoSaved);
						
			if(isDocumentoFirmato(documentoFileDTO)) {
				creaRegistroDocumento(documentoFileDTO, documentoSaved);
				caricaDocumentoFirmato(documentoFileDTO, documentoSaved);
			}
			
			return this.documentoBuilder.buildDocumentoDTO(documentoSaved);
		}

		return null;
	}
	
	public DocumentoDTO modificaNomeDocumento(Documento doc, DocumentoFileDTO documentoFileDTO, Resource resource) {

		Documento documentoSaved = documentoRepository.save(doc);

		creaRegistroDocumento(documentoFileDTO, documentoSaved,true,resource);

		return this.documentoBuilder.buildDocumentoDTO(documentoSaved);
	}

	private boolean validateModificaDocumento(DocumentoFileDTO documentoDTO, Integer id) {
		List<Errore> errors = documentoValidator.validateModificaDocumento(documentoDTO, messageSource, id);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		return true;
	}

	public String deleteDocumentList(List<Integer> documentIdList) {

		documentIdList.forEach( (id) -> {
			this.eliminaDocumento(id);
		});
		
		return "ok";
	}
	
	public String eliminaDocumento(Integer id) {
		Documento documento = this.documentoRepository.findById(id).orElse(null);
		documentoValidator.validateEliminaDocumento(documento);
		
		Boolean isResponsabile =  utenteService.getAuthenticatedUserAsResponsibleOfConference(documento.getConferenza()).getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_RESPONSABILE_CONFERENZA));
		LOGGER.debug("isResponsabile = " + isResponsabile);
		if(isResponsabile && documento.getTipologiaDocumento().equals(DbConst.CATEGORIA_DOCUMENTO_DOCUMENTO_FIRMATO))
		{
			LOGGER.debug("dentro l'if");
			RegistroFirmaAdapter registroFirmaAdapter = registroFirmaAdapterRepository.lastRegistroFirmaAdapterByidDoc(documento.getIdDocumento());
		
			FirmaDTO firmaDTO = new FirmaDTO();
			firmaDTO.setSessioneFirma(registroFirmaAdapter.getToken());
			firmaDTO.setIdDocumento(documento.getIdDocumento());
			LOGGER.debug("eseguo manageSignDocumentToDelete");
			manageSignDocumentToDelete(firmaDTO);
			LOGGER.debug("manageSignDocumentToDelete esguito");
		}
		
		documento.setDataFine(new Date());
		this.documentoRepository.save(documento);
		LOGGER.debug("documento eliminato");
		return "ok";
	}
	
	/*
	 * verifica se il documento che si sta scaricando è di tipo firma
	 * se il docuemnto è unlocked e il download viene eseguito da un firmatario
	 * viene eseguito il lock del file
	 * 
	 * 1. i controlli sui permessi vengono eseguiti da validateTokenDocumento
	 * 2. se i permessi sono validi bisogna verificare se l'utente può scaricare 
	 * 		il file firmato senza eseguire il lock, questo avviene nel caso in cui lo stato sia completed
	 * 3. se lo stato è diverso da completed esegui il lock del file 
	 */
	public void manageSignDocumentDownload(String tokenFile) {
		
		TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(tokenFile);	
		Documento documento = tokenRegistroDocumento.getRegistroDocumento().getDocumento();
		
		if(!documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_FIRMATO)) {
			return;
		}
		
		try {			
			
			RegistroFirmaAdapter registroFirmaAdapter = registroFirmaAdapterRepository.lastRegistroFirmaAdapterByidDoc(documento.getIdDocumento());
			
			FirmaDTO firmaDTO = new FirmaDTO();
			firmaDTO.setSessioneFirma(registroFirmaAdapter.getToken());
			firmaDTO.setIdDocumento(documento.getIdDocumento());
			firmaDTO.setIdConferenza(documento.getConferenza().getIdConferenza());

			Integer iTipoFirma=firmaSemplificataService.getTipoFirma(FirmaSemplificataService.TIPOFIRMA_FSSEMPLIFICATA);		
			firmaDTO.setFk_tipo_firma(iTipoFirma);		
			
			//se lo stato è closed allora viene eseguito lo scaricamento sena il lock del file
			if(firmaSemplificataService.isSigningSessionClosed(firmaDTO)) {
				return;
			}
							
			firmaSemplificataService.doActionLock(firmaDTO);
			
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Errore nel lock del documento di tipo firma");
			throw new InternalError();
		}
		
		
	}
	
	public void manageSignDocumentToDelete(FirmaDTO firmaDTO) {
		try {	
			firmaService.doActionCancelSigningSession(firmaDTO);
		}catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public Resource loadFileAsResource(String tokenFile) {
		if (validate(tokenFile)) {
			return registroDocumentoService.loadFileAsResource(tokenFile);
		}
		return null;
	}
	
	public String getFileNameDocumento(String tokenFile) {
		if (validate(tokenFile)) {
			return registroDocumentoService.getFileNameDocumento(tokenFile);
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
	 * Ad ogni documento sono associate 1 o più righe di registro. 
	 * Il registro che deve essere restituito è l'ultimo inserito in ordine di data. 
	 * @param documento
	 * @return
	 */
	public RegistroDocumento getRegistroDocumento(Documento documento) {
		return documentoBuilder.getRegistroDocumento(documento);
	}
	
	public DocumentazioneDTO syncronizeDocuments(Integer idConference, Boolean allDocuments) throws Exception {
		
		List<Errore> errors = documentoValidator.validateSyncDocuments(idConference, messageSource);
		if(errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		
		Boolean result = dcumentAdapterService.syncronize(idConference);
		if(result == false) {
			LOGGER.error("Impossibile eseguire la sincronizzazione");
			throw new Exception("Impossibile eseguire la sincronizzazione");
		}
		return this.findAllDocuments(idConference, allDocuments);
	}

	public DocumentazioneDTO findAllDocuments(Integer idConference, Boolean allDocuments) {
		Conferenza conferenza = this.confService.findConferenzaByIdFiltrata(idConference);
		Boolean isAdminProc = utenteService.getAuthenticatedUserAsResponsibleOfConference(conferenza).getProfilo().getTipoProfilo().getCodice()
				.equals(Integer.toString(DbConst.TIPO_PROFILO_ADMIN_AMMINISTRAZIONE_PROCEDENTE));
		Boolean isAdminAmministrazioni = utenteService.isAdminAmministrazioni();
		List<Documento> listaDocumentiAll = new ArrayList<>();
		if (isAdminAmministrazioni || isAdminProc) {
			listaDocumentiAll = documentoRepository.findByConferenza(conferenza);
			return this.documentoBuilder.buildDocumentazioneDTO(listaDocumentiAll, conferenza);
		} else {
			
			Accreditamento accreditamento = this.utenteService.getAccreditamento(conferenza, true);
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
				//aggiunge alla lista tutti i file con visibilità ristratta non visibili a questo utente
				
			}
			
			List<Documento> listaDocumentiConFirmatario = listaDocumentiConFirmatario(listaDocumenti, conferenza,
					accreditamento);
			listaDocumentiConFirmatario.stream().forEach(d -> listaDocumenti.add(d));

			return this.documentoBuilder.buildDocumentazioneDTO(listaDocumenti, conferenza);
		}
		
	}

	private List<Documento> listaDocumentiConFirmatario(List<Documento> listaDocumenti, Conferenza conferenza,
			Accreditamento accreditamento) {
		List<Documento> listaDocAll = documentoRepository.findByConferenza(conferenza);
		List<Documento> listaDocumentiConFirmatario = new ArrayList<>();
		for (Documento doc: listaDocAll) {
			List<Accreditamento> listaFirmatari = doc.getFirmatari();
			for (Accreditamento acc: listaFirmatari) {
				if (acc.equals(accreditamento) && !listaDocumenti.stream().anyMatch(d -> d.equals(doc))) {
					listaDocumentiConFirmatario.add(doc);
				}
			}
		}
		return listaDocumentiConFirmatario;
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

	public String creaTemplatePerDownload(Integer idConferenza, String tipoEvento) throws Exception {
		//Template template = templateRepo.findByTipoEvento(tipoEventoRepo.findById(tipoEvento).get()).get(0);
		Conferenza conferenza = confService.findConferenzaByIdFiltrata(idConferenza);
		Template template;
		if((""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS).equals(conferenza.getTipologiaConferenzaSpecializzazione().getCodice())) {
				LOGGER.info("DocumentoService - creaTemplatePerDownload: è una conferenza usr");
				template = templateRepo.findByTipologiaConferenzaSpecializzazioneAndTipoEventoAndAzione(conferenza.getTipologiaConferenzaSpecializzazione(), tipoEventoRepo.findById(tipoEvento).get(), conferenza.getAzione()).get();
				LOGGER.info("Template vale: " + template.getNomeTemplateDownload());
		}
		else
				template = templateRepo.findByTipologiaConferenzaSpecializzazioneAndTipoEvento(conferenza.getTipologiaConferenzaSpecializzazione(), tipoEventoRepo.findById(tipoEvento).get()).get();
		
		String esito = null;
		if (template.getNomeTemplateDownload() == null) {
			esito = "La tipologia di conferenza inserita non comprende un template";
		} else {
			
			String fileName = template.getNomeTemplateDownload() + ".docx";
			LOGGER.info("SONO ENTRATO QUA E FILENAME VALE: " + fileName);
			writeDocService.writeDocxFile(fileName, conferenza);
		}		
		return esito;
	}

	public ModalitaSalvataggioFile modalitaSalvataggio(MultipartFile file, String url) {
		if (url != null && !url.isEmpty()) {
			return ModalitaSalvataggioFile.Url;
		} else {
			return ModalitaSalvataggioFile.Filesystem;
		}
	}

	/**
	 * <pre>
	 * Alcune tipologie di documento richiedono l'invio di mail di notifica. I destinatari dipendono dalla tipologia di documento:
	 * - documenti condivisi -> i destinatari sono i partecipanti che hanno visibilità del documento
	 * - documenti in firma -> i destinatari sono gli accreditati selezionati nel momento di caricamento del documento
	 * </pre>
	 * @param documento
	 */
	public void invioEmailDocumentoPerTipologia(Documento documento) {
		String tipoEvento = null;
		List<Partecipante> listaPartecipanti = new ArrayList<>();
		if (mailer.isSendEnabled()) {
			if (documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTI_CONDIVISI)) {
				tipoEvento = Integer.toString(DbConst.TIPOLOGIA_EVENTO_INSERIMENTO_DOCUMENTAZIONE_CONDIVISA);
				listaPartecipanti = documento.getVisibilitaPartecipanti();
			}
			if (documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_FIRMATO)) {
				tipoEvento = Integer.toString(DbConst.TIPOLOGIA_EVENTO_CARICAMENTO_DOCUMENTO_FIRMA);
				/*
				 * i destinatari in questo caso sono i firmatari e si trovano dentro il
				 * documento. Il metodo di invio mail prenderà la lista dal documento 
				 */
			}
			if (tipoEvento != null) {
				EventoFileDTO eventoFileDTO = documentoBuilder.buildEventoFileDTO(documento, tipoEvento,
						listaPartecipanti);
				Evento saved = this.eventoRepository.save(eventoBuilder.buildEvento(eventoFileDTO, documento.getConferenza(), documento, null));
				try {
					if (!documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTI_CONDIVISI)) {
						eventoService.notificaMail(saved, saved.getDocumento(), eventoFileDTO.getListaDestinatari(),null);
						LOGGER.debug("L'invio della mail non avviene nel caso di documenti condivisi");
					}
						
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	public String getCategoriaDocDinamica(String category, String type) {
		if (category == null) {
			if (type != null && type.equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTI_CONDIVISI)) {
				category = Integer.toString(DbConst.CATEGORIA_DOCUMENTO_DOCUMENTO_CONDIVISO);
			}
		}
		return category;
	}

	public String getModelloDinamico(String model, String category) {
		if ((model == null || model.isEmpty()) && (category != null && !category.isEmpty())
				&& category.equals(Integer.toString(DbConst.CATEGORIA_DOCUMENTO_APPUNTI))) {

			List<Modello> listamodelli = modelloRepo.findByDescrizione("appunti");
			if (listamodelli.size() != 0) {
				model = listamodelli.get(0).getCodice();
			}
		}
		return model;
	}

	public DocumentoDTO sbloccaSessioneFirma(Integer idDocumento) {
		
		List<Errore> errors = documentoValidator.validateUnlockSigningDocument(idDocumento, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		
		firmaAdapterService.adminUnlock(idDocumento);			
		Documento documento = documentoRepository.getOne(idDocumento);	
		return this.documentoBuilder.buildDocumentoDTO(documento);

	}
	
	public DocumentoDTO sbloccaSessioneFirmaConCallback(Integer idDocumento, FirmaDTO firma) {
		
		List<Errore> errors = documentoValidator.validateUnlockSigningDocumentWithCallback(idDocumento, firma, messageSource);
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		
		try {
			
			Documento document = documentoRepository.getOne(idDocumento);
			
			firma.setIdDocumento(idDocumento);
			firma.setFileName(document.getNome());
			
			
			if (firma.getCalamaioRemota() != null && firma.getCalamaioRemota().equals("calamaio")) {
				if (firma.getPadesCades() != null &&
						!firma.getPadesCades()) {
					document.setNome(document.getNome() + "." + FirmaService.SIGNED_FILE_EXTENSION);	
				}
				DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
				
				
				Resource resource = firmaService.getResourceFirma(firma);
				
				modificaNomeDocumento(document, documentoFileDTO,resource);
			}
			
			firma=firmaService.doActionUnlck(firma);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalError();
		}
				
		Documento documento = documentoRepository.getOne(idDocumento);	
		return this.documentoBuilder.buildDocumentoDTO(documento);

	}

	public boolean isAlreadyPresentDocument(Integer conferenceId, String type, String externalReference) {
		
		RegistroDocumento registroDocumento = registroDocumentoRepository.findByIdConferenceAndTypeAndRifEsterno(conferenceId, type, externalReference);
		return registroDocumento != null && registroDocumento.getDocumento() != null;
	
	}
	
	public Documento findById(Integer id) {
		return documentoRepository.findById(id).get();
	}

	public MultipartFile getDocumentoMultipart(Integer iddoc) throws Exception {
		InputStream in = null;
		try {
			Documento doc = findById(iddoc);
			
			Resource resource = registroDocumentoService.loadDocumentoAsResource(iddoc);
			//File file = new File(path.toString());
			File file = resource.getFile();
			Path path = Paths.get(file.getAbsolutePath());

			byte[] filearray = new byte[(int)file.length()];
	
			in = new FileInputStream(file);
			in.read(filearray);

			return new MockMultipartFile(
					doc.getNome(),
					doc.getNome(),
					Files.probeContentType(path),
					filearray);
		} catch (Exception e) {
			LOGGER.error("@calamaio exception: impossible find the file to be signed: " + e.getMessage(), e);
			throw new Exception("Impossible find the file to be signed!");
		}
		finally {
			try {  if(in != null) in.close(); } catch (Exception e) { }
		}
	}

	//Questo metodo deve implementare il caricamento di un file nel sistema. 
	//Da scoprire come viene fatto negli altri punti
	public void uploadFile(MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

	public Documento creaDocumentoFirmaMultipla(DocumentoFileDTO documentoFileDTO, Integer idConferenza, Integer idResponsabileFirma) {
		Documento documento = creaDocumentoMultipartFile(documentoFileDTO, idConferenza, null, false);
		if (documento != null && isIndizione(documentoFileDTO)) {
			documento.setCategoriaDocumento(categoriaDocRepo
					.findById(Integer.toString(DbConst.CATEGORIA_DOCUMENTO_DOCUMENTO_INDIZIONE)).orElse(null));
			/*
			 * creazione evento convocazione conferenza
			 */
			this.eventoService.creaEventoConvocazioneConferenza(documentoFileDTO, documento, idConferenza);
		}
		// devo aggiungere il documento come da firmare con firma multipla
		DocumentoFirmaMultipla documentoInFirma = documentoFirmaMultiplaService.aggiornaFirmaMultipla(documento, idResponsabileFirma, STATO_DOCUMENTO.IN_BOZZA.getStatus(), true);
		
		// invio mail all'utente firmatario
		if(documentoInFirma != null)
			notificaMailAFirmatario(documento, idResponsabileFirma);
		
		return documento;
	}

	public Optional<Documento> getDocumentoById(Integer idDocumento) {
		return documentoRepository.findById(idDocumento);
	}

	private Specification<Documento> filtroDocumentoByIdResponsabileAndStato(RicercaDocumentoDTO documento, Integer totalNumber) {
		// devo recuperare i documenti per cui l'utente loggato risulti essere il responsabile di firma
		Integer idUtente = utenteService.getAuthenticatedUser().getIdUtente();
		
		List<Documento> listaDocumenti = documentoRepository.findDocumentoByResposabileFirmaAndStato(documento.getStato(), idUtente);
		totalNumber = listaDocumenti.size();
		List<SearchCriteria> parametriIdDocumento = new ArrayList<>();
		listaDocumenti.stream().forEach(item-> parametriIdDocumento.add(new SearchCriteria("idDocumento", "=",item.getIdDocumento())));
		
		DocumentoSpecificationsBuilder builder = new DocumentoSpecificationsBuilder();
		if(parametriIdDocumento != null && !parametriIdDocumento.isEmpty())
			return builder.buildAllOr(parametriIdDocumento);
		else
			return null;
	}
	/**
	 * Questo metodo è da implementare correttamente dopo che l'entity Documento è stata modificata con il field stato ed idResponsabileFirmatario.
	 * Per ora ritorna sempre la stessa lista di documenti
	 * @param valueOf
	 * @param statoDocumento
	 * @return
	 */
	public ListaDocumentoFirmaDTO getDocumentoByIdResponsabileAndStato(RicercaDocumentoDTO ricerca, STATO_DOCUMENTO stato) {
		PageRequest page = PageRequest.of(ricerca .getPagVisualizzata() - 1, ricerca.getNumRecordPag(),
				Direction.fromString(ricerca.getDirOrdinamento()), documentoBuilder.mapColonnaOrdinamentoDocumentoRicerca(ricerca.getColonnaOrdinamento()));

		// devo recuperare i documenti per cui l'utente loggato risulti essere il responsabile di firma
		Integer idUtente = utenteService.getAuthenticatedUser().getIdUtente();
		
		List<Documento> listaDocumenti = documentoRepository.findDocumentoByResposabileFirmaAndStato(ricerca.getStato(), idUtente);
		Integer totalNumber = listaDocumenti.size();
		List<SearchCriteria> parametriIdDocumento = new ArrayList<>();
		listaDocumenti.stream().forEach(item-> parametriIdDocumento.add(new SearchCriteria("idDocumento", "=",item.getIdDocumento())));
		
		DocumentoSpecificationsBuilder builder = new DocumentoSpecificationsBuilder();
		Specification<Documento> filter = null;
		if(parametriIdDocumento != null && !parametriIdDocumento.isEmpty())
			filter = builder.buildAllOr(parametriIdDocumento);
		ListaDocumentoFirmaDTO listaDTO = new ListaDocumentoFirmaDTO();

		if(filter !=null ) {
			Iterable<Documento> documenti = this.documentoRepository.findAll(filter, page);
			
			for(Documento documento : documenti) {
				documento.getConferenza();
				DocumentoFirmaDTO documentBuilder = documentoFirmaMultiplaBuilder.buildDocumentoFirmaDTO(documento,stato.getStatus());
				documentBuilder.setStato(stato.name());
				listaDTO.getList().add(documentBuilder);
			}
		}
	
		listaDTO.setTotalNumber(new Long(totalNumber));
		return listaDTO;
		
	}
	
	public List<Documento> getListaDocumento(Iterable<Integer> ids){
		return documentoRepository.findAllById(ids);
	}
	
	public void notificaMailAFirmatario( Documento documento, Integer idUtente) {

		if (mailer.isSendEnabled()) {
			emailRepositoryService.sendEmailAFirmatario(documento.getIdDocumento(), idUtente);			
		}		
	}
	
}
