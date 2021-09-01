package conferenza.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.bean.Errore;
import conferenza.documentazionecondivisa.service.OOService;
import conferenza.exception.InvalidFieldEx;
import conferenza.exception.NotAuthorizedUser;
import conferenza.exception.NotEditableException;
import conferenza.exception.NotFoundEx;
import conferenza.facade.STATO_DOCUMENTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.adapter.FirmaAdapter;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.model.RegistroFirmaSessionSigner;
import conferenza.firma.repository.RegistroFirmaAdapterRepository;
import conferenza.firma.service.DSSValidationService;
import conferenza.firma.service.FirmaSemplificataService;
import conferenza.firma.service.FirmaService;
import conferenza.firma.service.RegistroFirmaSessionSignerService;
import conferenza.model.Accreditamento;
import conferenza.model.CategoriaDocumento;
import conferenza.model.Conferenza;
import conferenza.model.Documento;
import conferenza.model.DocumentoFirmaMultipla;
import conferenza.model.RegistroDocumento;
import conferenza.model.TokenRegistroDocumento;
import conferenza.model.Utente;
import conferenza.repository.AccreditamentoRepository;
import conferenza.repository.CategoriaDocumentoRepository;
import conferenza.repository.ConferenzaRepository;
import conferenza.repository.DocumentoRepository;
import conferenza.repository.PersonaRepository;
import conferenza.repository.TokenRegistroDocumentoRepository;
import conferenza.service.DocumentoFirmaMultiplaService;
import conferenza.service.UtenteService;
import conferenza.util.DbConst;
import conferenza.util.JsonUtil;

@Component
public class DocumentoValidator {

	@Autowired
	TokenRegistroDocumentoRepository tokenRegistroDocumentoRepository;

	@Autowired
	ConferenzaRepository confRepo;

	@Autowired
	DocumentoRepository documentoRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	CategoriaDocumentoRepository catDocRepo;

	@Autowired
	AccreditamentoRepository accreditamentoRepository;

	@Autowired
	PersonaRepository personaRepository;
	
	@Autowired
	UtenteService utenteService;

	@Autowired
	FirmaSemplificataService firmaSemplificataService;

	@Autowired
	RegistroFirmaAdapterRepository registroFirmaDocumentoRepository;

	@Autowired
	RegistroFirmaSessionSignerService registroFirmaSessionSignerService;
	
	@Autowired
	DocumentoFirmaMultiplaService documentoFirmaMultiplaService;

	@Value("${spring.profiles.active:PROD}")
	String __env = ""; 
	
	@Autowired
	DSSValidationService dssValidationService;
	private static final Logger logger = LoggerFactory.getLogger(OOService.class.getName());
	public List<Errore> validateUnlockSigningDocument(Integer documentId, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		
		if(documentId == null){
			String msg = messageSource.getMessage("documento.file.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
			return errors;
		}

		Documento documento = this.documentoRepository.findById(documentId).orElse(null);
		if(documento == null || documento.getConferenza() == null){
			String msg = messageSource.getMessage("documento.file.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
			return errors;
		}

		Accreditamento accreditamento = utenteService.getAccreditamento(documento.getConferenza(), true);
		if ( accreditamento == null 
			|| (accreditamento == null && !utenteService.isResponsabileConferenza(documento.getConferenza()) 
					&& !utenteService.isCreatoreConferenza(documento.getConferenza())
				)
			) {
				String msg = messageSource.getMessage("documento.firma.utenteNonValido", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("user", msg));
				return errors;
		}
		
		//TODO check if user is a singer of this document

		RegistroFirmaAdapter registroFirmaAdapter = registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(documentId);	
		if(registroFirmaAdapter == null || !registroFirmaAdapter.getStato().equals(FirmaAdapter.STATUS_LOCK)){
			String msg = messageSource.getMessage("documento.firma.statoNonValido", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("status", msg));
			return errors;
		}
		
		return errors;
	}

	public List<Errore> validateUnlockSigningDocumentWithCallback(Integer documentId, FirmaDTO firmaDTO, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		
		if(documentId == null){
			String msg = messageSource.getMessage("documento.file.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
			return errors;
		}

		Documento documento = this.documentoRepository.findById(documentId).orElse(null);
		if(documento == null || documento.getConferenza() == null){
			String msg = messageSource.getMessage("documento.file.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
			return errors;
		}

		if(firmaDTO.getCallbackbody() == null) {
			String msg = messageSource.getMessage("documento.file.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
			return errors;
		}
		
		Accreditamento accreditamento = utenteService.getAccreditamento(documento.getConferenza(), true);
		if ( accreditamento == null) {
				String msg = messageSource.getMessage("documento.firma.utenteNonValido", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("user", msg));
				return errors;
		}

		RegistroFirmaAdapter registroFirmaAdapter = registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(documentId);	
		if(registroFirmaAdapter == null || !registroFirmaAdapter.getStato().equals(FirmaAdapter.STATUS_LOCK)){
			String msg = messageSource.getMessage("documento.firma.statoNonValido", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("status", msg));
			return errors;
		}
				
		return errors;
	}
	
	public List<Errore> validateCreaDocumento(DocumentoFileDTO documentoDTO, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		if (documentoDTO.getFile() == null &&  (documentoDTO.getUrl() == null ||documentoDTO.getUrl().isEmpty())){
			if( (documentoDTO.getModello() == null || documentoDTO.getModello().isEmpty())
					&& !Integer.toString(DbConst.CATEGORIA_DOCUMENTO_APPUNTI).equals(documentoDTO.getCategoria())) {
				
				String msg = messageSource.getMessage("documento.file.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("file", msg));
			}
			
		}else if(FirmaService.SIGNED_FILE_EXTENSION.equals(FilenameUtils.getExtension(documentoDTO.getFile().getOriginalFilename()))){								
		
			// xmfDIS digital sign disabled!
			if(true || !"local".equals(__env)) {
				
				//VERIFICA CONTENUTO DEL FILE
				boolean isSigned = Boolean.FALSE;
				MultipartFile file=documentoDTO.getFile();
				byte[] bytesArray=null;
				try {
					//byte array del multipart file!
					bytesArray = file.getBytes();
					//verifica che il file sia firmato digitalmente!
					isSigned = dssValidationService.validateBytesArray(bytesArray);

					//Se il file NON è firmato rilancia l'eccezione!
					if(isSigned==Boolean.FALSE){
						String msg = messageSource.getMessage("documento.firma.assenzasezionefirma", null, request.getLocale());
						errors.add(Errore.getErrorSingleField("file", msg));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
	
					String msg = messageSource.getMessage("documento.firma.assenzasezionefirma", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("file", msg));
				}		  
			}
		}			

		
		if (documentoDTO.getNomeFile() == null || documentoDTO.getNomeFile().isEmpty()) {
			String msg = messageSource.getMessage("documento.nomeFile.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("document.fileName", msg));
		}
		
		if (documentoDTO.getTipoDocumento().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)
				&& !"01-01-1970".equals(documentoDTO.getDataProtocollo())) {
			if (documentoDTO.getNumeroProtocollo() == null || documentoDTO.getFile().isEmpty()) {
				String msg = messageSource.getMessage("documento.protocolNumber.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("documento.protocolNumber", msg));
			}
			if (documentoDTO.getDataProtocollo() == null || documentoDTO.getDataProtocollo().isEmpty()) {
				String msg = messageSource.getMessage("documento.protocolDate.null", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("documento.protocolDate", msg));
			}
		}
		
		errors = validateFieldDocument(errors, documentoDTO, messageSource);
		return errors;
	}
	
	public boolean validateSignedFile(DocumentoFileDTO documentoFileDTO, MessageSource messageSource) {
		
		List<Errore> errors = new ArrayList<>();
		if(!FirmaService.SIGNED_FILE_EXTENSION.equals(FilenameUtils.getExtension(documentoFileDTO.getFile().getOriginalFilename())) &&
				!FirmaService.SIGNED_PADES_FILE_EXTENSION.equals(FilenameUtils.getExtension(documentoFileDTO.getFile().getOriginalFilename()))	){
			String msg = messageSource.getMessage("documento.firma.estensioneNonValida", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
		}
				
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		
		/*
		 * viene già verificato per ogni tipo di file
		//VERIFICA CONTENUTO DEL FILE
		boolean isSigned = Boolean.FALSE;
		MultipartFile file=documentoFileDTO.getFile();
		byte[] bytesArray=null;
		try {
			//byte array del multipart file!
			bytesArray = file.getBytes();
			//verifica che il file sia firmato digitalmente!
			isSigned = dssValidationService.validateBytesArray(bytesArray);
			//Se il file NON è firmato rilancia l'eccezione!
			if(isSigned==Boolean.FALSE){
				String msg = messageSource.getMessage("documento.firma.assenzasezionefirma", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("file", msg));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			String msg = messageSource.getMessage("documento.firma.assenzasezionefirma", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("file", msg));
		}
		  
		if (errors.size() != 0) {
			throw new InvalidFieldEx(errors);
		}
		*/
		return true;
	}

	public List<Errore> validateSyncDocuments(Integer conferenceId, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		Conferenza conferenza = confRepo.findById(conferenceId).orElse(null);
		
		if(conferenza == null) {
			String msg = messageSource.getMessage("documento.sync.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("conference", msg));
		}else if(!utenteService.isCreatoreConferenza(conferenza) && !utenteService.isResponsabileConferenza(conferenza)) {
			String msg = messageSource.getMessage("documento.sync.userNonValido", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("user", msg));
		}
		
		return errors;
	}
	
	public List<Errore> validateTokenDocumento(String tokenFile, MessageSource messageSource) {
		
		logger.debug("DocumentoValidator - riga 285 - tokenFile: " + tokenFile + " messageSource: " + messageSource);
		List<Errore> errors = new ArrayList<>();
		if (tokenFile == null || tokenFile.isEmpty()) {
			String msg = messageSource.getMessage("documento.token.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("token", msg));
		} else {
			if (validateVisibilitaDocumento(tokenFile, messageSource)) {
				TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(tokenFile);
				if (tokenRegistroDocumento == null) {
					String msg = messageSource.getMessage("documento.token.notValid", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("token", msg));
				} else {
					Date scadenza = tokenRegistroDocumento.getScadenza();
					if (scadenza.compareTo(new Date()) < 0) {
						String msg = messageSource.getMessage("documento.token.expired", null,
								request.getLocale());
						errors.add(Errore.getErrorSingleField("token", msg));
						
					}
				}
			}
			
			
		}
		return errors;
	}
		
	/*
	 * verifica se l'utente che richiede il download del documento ha i permessi per poterlo scaricare
	 * 
	 * 1. il docuemnto è in uno stato COMPLETED|UNLOCKED
	 * 2. l'utente richiedente è un firmatario o il responsabile del documento
	 */
	private boolean validateSignatureDocumentPermission(String tokenFile, MessageSource messageSource) {

		TokenRegistroDocumento tokenRegistroDocumento = tokenRegistroDocumentoRepository.findByToken(tokenFile);
		Documento documento = tokenRegistroDocumento.getRegistroDocumento().getDocumento();
		Conferenza conferenza = documento.getConferenza();
	
		if(!documento.getTipologiaDocumento().getCodice().equalsIgnoreCase(DbConst.SEZIONE_DOCUMENTAZIONE_DOCUMENTI_FIRMA)) {
			return true;
		}
		
		RegistroFirmaAdapter lastRegistroFirmaAdapter = registroFirmaDocumentoRepository.lastRegistroFirmaAdapterByidDoc(documento.getIdDocumento());
		//nel caso ci sia un'inconsistenza nel database e non esista un registroFirma adapter per il token
		if(lastRegistroFirmaAdapter == null){
			return false;
		}

		FirmaDTO firmaDTO = new FirmaDTO();
		firmaDTO.setSessioneFirma(lastRegistroFirmaAdapter.getToken());
		boolean isSigningSessionClosed = registroFirmaSessionSignerService.isSessionClosed(firmaDTO);
		boolean idSessionUnlocked = lastRegistroFirmaAdapter.getStato().equalsIgnoreCase(FirmaAdapter.STATUS_UNLOCK);

		//1. verifica che l'utente sia creatore/responsabile e lo stato del RegistroFirmaAdapter sia chiuso
		Accreditamento accreditamento = utenteService.getAccreditamento(conferenza, true);
		if ( accreditamento != null 
			&& conferenza.getCodiceFiscaleResponsabileConferenza().equalsIgnoreCase(accreditamento.getPersona().getCodiceFiscale())
			&& isSigningSessionClosed
			) {
				return true;
		}

		Utente authenticatedUser = utenteService.getAuthenticatedUserAsResponsibleOfConference(conferenza);
		List<RegistroFirmaSessionSigner> signerWaitingForList = registroFirmaSessionSignerService.getListSignerWaitingFor(firmaDTO);
		Boolean isFirmatarioWaitingForSigning = false;
		for (RegistroFirmaSessionSigner sessionSigner : signerWaitingForList){
			if(sessionSigner.getSigner() == authenticatedUser.getIdUtente()){
				isFirmatarioWaitingForSigning = true;
			}
		}
		
		//2. la sessione è sbloccata e che il firmatario non ha ancora firmato
		if(!isSigningSessionClosed && idSessionUnlocked && isFirmatarioWaitingForSigning){
			 return true;
		}

		Boolean isUserFirmatario = false;
		Utente utenteAutenticato = utenteService.findByCodiceFiscale(authenticatedUser.getCodiceFiscale());
		for(Accreditamento firmatario  :documento.getFirmatari()){
			Utente utenteFirmatario = utenteService.findByCodiceFiscale(firmatario.getPersona().getCodiceFiscale());
			if(utenteFirmatario != null && utenteFirmatario.getIdUtente() == utenteAutenticato.getIdUtente()){
				isUserFirmatario = true;
			}
		}

		//3. la sessione è chiusa e l'utente è un firmatario valido
		if( isSigningSessionClosed && isUserFirmatario){
			 return true;
		}

		return false;
	}
	
	private boolean validateVisibilitaDocumento(String tokenFile, MessageSource messageSource) {
		// TODO implementare la visibilita in base all'utente collegato
		return true;
	}

	public List<Errore> validateModificaDocumento(DocumentoFileDTO documentoDTO, MessageSource messageSource, int id) {
		Documento documento = this.documentoRepository.findById(id).orElse(null);
		if (documento == null) {
			throw new NotFoundEx("document not found");
		}
				
		List<Errore> errors = new ArrayList<>();
		errors = validateFieldDocument(errors, documentoDTO, messageSource);
		return errors;
	}

	public List<Errore> validateFieldDocument(List<Errore> errors, DocumentoFileDTO documentoDTO,
			MessageSource messageSource) {
		if (documentoDTO.getVisibilitaPartecipanti() != null && !documentoDTO.getVisibilitaPartecipanti().isEmpty()) {
			if (!JsonUtil.validateListLabelValue(documentoDTO.getVisibilitaPartecipanti())) {
				String msg = messageSource.getMessage("documento.visibility.jsonNonValido", null,
						request.getLocale());
				errors.add(Errore.getErrorSingleField("visibility", msg));
			}
		}
		if (documentoDTO.getTipoDocumento() == null && !documentoDTO.getTipoDocumento().isEmpty()) {
			String msg = messageSource.getMessage("documento.tipoDocumento.null", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("document.type", msg));
		}
		if (documentoDTO.getCategoria() != null && !documentoDTO.getCategoria().isEmpty()) {
			CategoriaDocumento cat = catDocRepo.findById(documentoDTO.getCategoria()).orElse(null);
			if (cat != null) {
				if (!cat.getTipologiaDocumento().getCodice().equals(documentoDTO.getTipoDocumento())) {
					String msg = messageSource.getMessage("documento.categoriaDocumento.mismatch", null, request.getLocale());
					errors.add(Errore.getErrorSingleField("document.category", msg));
				}
			}
		}
		return errors;
	}

	public List<Errore> validateConferenza(Integer idConferenza, DocumentoFileDTO documentoFileDTO, MessageSource messageSource) {
		List<Errore> errors = new ArrayList<>();
		Optional<Conferenza> conferenza = confRepo.findById(idConferenza);
		if (!conferenza.isPresent()) {
			String msg = messageSource.getMessage("documento.conferenza.nonPresente", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("idConference", msg));
		} else if (documentoFileDTO.getTipoDocumento().equalsIgnoreCase(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)
				&& !conferenza.get().getStato().getCodice().equals(new Integer(DbConst.STATO_BOZZA).toString())) {
			String msg = messageSource.getMessage("documento.indizione.statoNonValido", null, request.getLocale());
			errors.add(Errore.getErrorSingleField("document.type", msg));
		} else if(conferenza.isPresent() && documentoFileDTO.getTipoDocumento().equalsIgnoreCase(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_FIRMATO)){

			//verifica che l'utente richiedente sia il responsabile o creatore della conferenza
			Accreditamento accreditamento = utenteService.getAccreditamento(conferenza.get(), true);
			if ( accreditamento == null || (accreditamento == null && !conferenza.get().getCodiceFiscaleResponsabileConferenza().equalsIgnoreCase(accreditamento.getPersona().getCodiceFiscale()))) {
				String msg = messageSource.getMessage("documento.firma.utenteNonValido", null, request.getLocale());
				errors.add(Errore.getErrorSingleField("user.type", msg));
			}
		
		}
		return errors;
	}

	public void validateEliminaDocumento(Documento documento) {
		if (documento != null) {
			if (documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE)) {
				// devo verificare se è un documento con firma_multipla ancora in bozza, in tal caso posso eliminare il documento
				if(!documentoFirmaMultiplaService.isDocumentoRegistroInBozza(documento))
					throw new NotEditableException("Il file di indizione non puo' essere rimosso");
			}
			Accreditamento accreditamento = utenteService.getAccreditamento(documento.getConferenza(), true);
			if (documento.getTipologiaDocumento().getCodice().equals(DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_FIRMATO)
					&& (accreditamento != null)
					&& !(accreditamento.equals(documento.getOwner()) || accreditamento.getPersona().getCodiceFiscale()
							.equalsIgnoreCase(documento.getConferenza().getCodiceFiscaleResponsabileConferenza()))) {
				throw new NotAuthorizedUser("Il file non puo' essere rimosso");
			}
		} else {
			throw new NotFoundEx("Document not found");
		}
	}

}
