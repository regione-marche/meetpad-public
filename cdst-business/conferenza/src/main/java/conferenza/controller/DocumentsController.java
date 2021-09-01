package conferenza.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.AcroFields.Item;

import conferenza.DTO.DocumentazioneDTO;
import conferenza.DTO.DocumentoDTO;
import conferenza.DTO.DocumentoFileDTO;
import conferenza.DTO.EliminaListaDocumentiDTO;
import conferenza.DTO.FonteFile;
import conferenza.DTO.ListaDocumentoFirmaDTO;
import conferenza.DTO.ModalitaSalvataggioFile;
import conferenza.DTO.RicercaDocumentoDTO;
import conferenza.DTO.RispostaDocumentazioneDTO;
import conferenza.DTO.RispostaDocumentoDTO;
import conferenza.DTO.RispostaListaDocumentoDTO;
import conferenza.DTO.RispostaListaDocumentoFirmaDTO;
import conferenza.DTO.bean.Errore;
import conferenza.DTO.bean.RispostaNoData;
import conferenza.adapder.integrazioni.firma.calamaio.service.CalamaioService;
import conferenza.builder.DocumentoBuilder;
import conferenza.exception.DocumentoException;
import conferenza.exception.InvalidFieldEx;
import conferenza.facade.DocumentFacade;
import conferenza.facade.STATO_DOCUMENTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.service.FirmaSemplificataService;
import conferenza.firma.service.FirmaService;
import conferenza.model.Documento;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.service.ProtocolloService;
import conferenza.service.DocumentoService;
import conferenza.service.RegistroDocumentoService;
import conferenza.util.P7MUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = { "Documents API" })
public class DocumentsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentsController.class);

	@Autowired
	HttpServletRequest request;

	@Autowired
	DocumentoService documentoService;
	
	@Autowired
	RegistroDocumentoService registroDocumentoService;
	
	@Autowired
	FirmaSemplificataService firmaSemplificataService;
	
	@Autowired
	DocumentoBuilder documentoBuilder;
	
	@Autowired
	HttpServletResponse response;
	
	@Autowired
	ProtocolloService protocolloService;
	
	@Autowired
	CalamaioService calamaioService;
	
	@Autowired
	private DocumentFacade documentFacade;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentsController.class.getName());
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   
	@ExceptionHandler(value= {DocumentoException.class})
	public  ResponseEntity<String> handleRestError(RuntimeException e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@GetMapping(value = "/conferences/{idConference}/documents")
	@ApiOperation(value = "get documents for conference identified by id")
	public RispostaDocumentazioneDTO getAllDocuments(@PathVariable Integer idConference, Boolean key) {
		DocumentazioneDTO documentazione = this.documentoService.findAllDocuments(idConference, key);
		RispostaDocumentazioneDTO risposta = new RispostaDocumentazioneDTO();
		risposta.setData(documentazione);
		return risposta;
	}

	/**
	 * 
	 * @param conference
	 * @param locale
	 * @return
	 * @throws Exception 
	 */
	@PatchMapping("/conferences/{idConference}/syncronizeDocuments")
	@ApiOperation(value = "syncronize documents of conference")
	public RispostaDocumentazioneDTO syncronizeDocuments(@PathVariable Integer idConference, Boolean key) throws Exception {
		LOGGER.debug("Class: ConferenceController - Method: syncronizeDocuments");
		RispostaDocumentazioneDTO risposta = new RispostaDocumentazioneDTO();
		try {
			DocumentazioneDTO documentazione = this.documentoService.syncronizeDocuments(idConference, key);
			risposta.setData(documentazione);
		}catch (Exception e) {
			risposta.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		
		return risposta;
	}
	
	@PostMapping(value = "/conferences/{idConference}/documents")
	@ApiOperation(value = "insert document for conference identified by id")
	public RispostaNoData creaDocumento(
			@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String url, 
			@RequestParam(required = false)  String name, 
			@RequestParam(required = false)  String type,
			@RequestParam(required = false) String category, 
			@RequestParam(required = false)  String visibility,
			@PathVariable Integer idConference, 
			@RequestParam(required = false) String protocolNumber,
			@RequestParam(required = false) String protocolDate, 
			@RequestParam(required = false) String inventoryNumber,
			@RequestParam(required = false) String cityReference, 
			@RequestParam(required = false) String model,
			@RequestParam(required = false, defaultValue = "false") Boolean remoteSignature,
			@RequestParam(required = false) String calamaioRemoteUsername,
			@RequestParam(required = false) String calamaioRemotePassword,
			@RequestParam(required = false) String calamaioRemoteOTP,
			@RequestParam(required = false) String calamaioRemoteDomain,
			@RequestParam(required = false) String calamaioRemoteDocumentId,
			@RequestParam(required = false) String signers, 
			@RequestParam(required = false, defaultValue = "false") Boolean signed,
			@RequestParam(required = false, defaultValue = "false") Boolean fileComplient,
			@RequestParam(required = false) MultipartFile[] attachments,
			@RequestParam(required = false, defaultValue = "false") Boolean padesCades, 
	        @RequestParam(required = false, defaultValue = "false") Boolean calamaioSignature){
		
		boolean isAdditionalSign = false;
		RispostaNoData errRisposta = new RispostaNoData();
		if(calamaioRemoteDocumentId != null && "undefined".equalsIgnoreCase(calamaioRemoteDocumentId))
			calamaioRemoteDocumentId = null;
		
		LOGGER.debug("@calamaio CALL: " + name + " - " + remoteSignature + " - calUID" + calamaioRemoteUsername + " - calDom" + calamaioRemoteDomain + " - calId" + calamaioRemoteDocumentId + " - attch: " + attachments);
		if(remoteSignature) {
			String errorMsg = null;

			try {
				isAdditionalSign = calamaioRemoteDocumentId != null && calamaioRemoteDocumentId.length() > 0;
				if(isAdditionalSign) {
					file = documentoService.getDocumentoMultipart(Integer.parseInt(calamaioRemoteDocumentId));
					name = file.getName();
				}
				else {
					if (!padesCades) {
						name = name + "." + FirmaService.SIGNED_FILE_EXTENSION;
					}  else {
						name = name + "." + FirmaService.SIGNED_PADES_FILE_EXTENSION;
					}
				}
					
				String tipoFirma = "aruba.remote.cades";
				if (padesCades) {
					tipoFirma = "aruba.remote.pades";
				}
				file = calamaioService.checkHcmSignatureCalamaio(file,
						calamaioRemoteUsername,
						calamaioRemotePassword,
						calamaioRemoteOTP,
						calamaioRemoteDomain,
						isAdditionalSign,
						tipoFirma);
			} catch (Exception e) {
				LOGGER.debug("@calamaio error: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
				errorMsg = e.getMessage();
			}
			
			LOGGER.debug("@calamaio REMOTE CALL OUT: " + name + " - " + errorMsg);
			if(errorMsg != null || file == null) {
				errRisposta.getErrors().add(new Errore("001", errorMsg == null?"Impossibile firmare digitalmente il con il servizio di firma remota.":errorMsg));
				errRisposta.setCode("200");
				errRisposta.setMsg("errore generico");
				return errRisposta;
			}
		}
		
		
		try {
			DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
			documentoFileDTO.setFile(file);
			documentoFileDTO.setUrl(url);
			documentoFileDTO.setCategoria(documentoService.getCategoriaDocDinamica(category, type));
			documentoFileDTO.setTipoDocumento(type);
			documentoFileDTO.setNomeFile(name);
			documentoFileDTO.setVisibilitaPartecipanti(visibility);
			documentoFileDTO.setNumeroProtocollo(protocolNumber);
			documentoFileDTO.setDataProtocollo(protocolDate);
			documentoFileDTO.setNumeroInventario(inventoryNumber);
			documentoFileDTO.setCompetenzaTerritoriale(cityReference);
			documentoFileDTO.setModello(documentoService.getModelloDinamico(model, category));
			documentoFileDTO.setFirmatari(signers);
			documentoFileDTO.setFirmato(signed);
			documentoFileDTO.setFileConforme(fileComplient);
			documentoFileDTO.setAttachments(attachments);
			
			if (calamaioSignature) {
				isAdditionalSign = calamaioRemoteDocumentId != null && calamaioRemoteDocumentId.length() > 0;
				if(isAdditionalSign) {
					file = documentoService.getDocumentoMultipart(Integer.parseInt(calamaioRemoteDocumentId));
					documentoFileDTO.setNomeFilePostCalamaio(file.getName()); 
				} else {
					if (padesCades) {
						documentoFileDTO.setNomeFilePostCalamaio(name + "." + FirmaService.SIGNED_PADES_FILE_EXTENSION);
					} else {
						documentoFileDTO.setNomeFilePostCalamaio(name + "." + FirmaService.SIGNED_FILE_EXTENSION);
					}
				}
				
			}
			
			LOGGER.debug("@calamaio params: " + documentoFileDTO.toString());
		
			if(isAdditionalSign) {
				DocumentoDTO documentoDTO = documentoService.modificaDocumento(documentoFileDTO, Integer.parseInt(calamaioRemoteDocumentId));
				RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
				risposta.setData(documentoDTO);
				return risposta;
			}
				
			Documento documento = documentoService.creaDocumento(documentoFileDTO, idConference);
			
			DocumentoDTO documentoDTO = documentoBuilder.buildDocumentoDTO(documento);
			RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
			risposta.setData(documentoDTO);
		
			documentoService.invioEmailDocumentoPerTipologia(documento);
			
			/*
			 * l'invio delle mail (essendo asincrono) deve essere chiamato solo dopo la
			 * conclusione della transazione di creazione del documento e quindi nel
			 * controller
			 */
			if (documento != null && documentoService.isIndizione(documentoFileDTO)) {
				
				// xmf: if there is a document for which is pending a Paleo protocol request then 
				// don't send the email and send it after the protocol is generated 
				if(protocolloService.findAllDocuments(documento.getIdDocumento()).size() == 0)
					documentoService.notificaMailIndizione(documento.getConferenza(), documento);
			}

			return risposta;
		} catch (InvalidFieldEx e) {
			LOGGER.debug("@calamaio error InvalidFieldEx: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
			errRisposta.setErrors(e.getErrors());
		} catch(Throwable t) {
			LOGGER.debug("@calamaio error Throwable: " + t.getMessage() + " - " + Arrays.toString(t.getStackTrace()));
			errRisposta.getErrors().add(new Errore("002", "Impossibile registrare il file."));
		}

		return errRisposta;
			
	}	
	
	@PutMapping(value = "/documents/{id}")
	@ApiOperation(value = "update document identified by id")
	public RispostaDocumentoDTO modificaDocumento(@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String name, @RequestParam String type,
			@RequestParam(required = false) String category, @RequestParam String visibility, @PathVariable Integer id,
			@RequestParam(required = false) String protocolNumber, @RequestParam(required = false) String protocolDate,
			@RequestParam(required = false) String inventoryNumber, @RequestParam(required = false) String cityReference) {
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setCategoria(category);
		documentoFileDTO.setTipoDocumento(type);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setVisibilitaPartecipanti(visibility);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setNumeroInventario(inventoryNumber);
		documentoFileDTO.setCompetenzaTerritoriale(cityReference);
		DocumentoDTO documentoDTO = documentoService.modificaDocumento(documentoFileDTO, id);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
		return risposta;
	}

	@DeleteMapping(value = "/documents/{id}")
	@ApiOperation(value = "delete document identified by id")
	public RispostaNoData eliminaDocumento(@PathVariable Integer id) {
		LOGGER.debug("Richiesta di eliminazione documento id " + id);
		String esito = documentoService.eliminaDocumento(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		LOGGER.debug("Esito Richiesta di eliminazione documento id " + id + " : " + esito);
		return risposta;
	}
	
	@PatchMapping(value = "/documents/deleteList")
	@ApiOperation(value = "delete document list")
	public RispostaNoData eliminaListaDocumenti(@RequestBody EliminaListaDocumentiDTO eliminaListaDocumentiDTO) {
		String result = documentoService.deleteDocumentList(eliminaListaDocumentiDTO.getListaIdDocumenti());
		RispostaNoData response = new RispostaNoData();
		response.setMsg(result);
		return response;
	}

	public ResponseEntity<Resource> downloadFile(@PathVariable String token
            , HttpServletRequest request) {
		return this.downloadFile(token, null, request);
	}

	@GetMapping("/downloadFile/{token}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String token
            , @RequestParam(name = "pdffile", required = false) String pdffile
            , HttpServletRequest request) {

		Resource resource = documentoService.loadFileAsResource(token);
		
		if (pdffile != null && pdffile.equals("1") && resource.getFilename().toLowerCase().endsWith("p7m")) {
			// estraggo il file dal p7m
			try {
				String filename = resource.getFilename().substring(0, resource.getFilename().indexOf(".p7m"));
				byte[] fileStream = P7MUtil.fromInputStreamToByteArray(resource.getInputStream());
				byte[] stream = P7MUtil.extractFileFromP7M(fileStream);
				resource = new ByteArrayResource(stream) {
					@SuppressWarnings("unused")
					@Override
					public String getFilename() {
						return filename;
					}
				};
			}
			catch (Exception e) {
				LOGGER.debug("Errore nella estrazione del file da " + resource.getFilename() + " : " + e.getMessage());
			}
		}
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.debug("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		String fileName = documentoService.getFileNameDocumento(token);
		if(fileName == null)
			fileName = resource.getFilename();
		
		String contentLength = "1";
		Long length = null;
		if (resource != null) {
			try {
				if (resource.contentLength() != 0) {
					length = resource.contentLength();
					contentLength = Long.toString(resource.contentLength());
				}
			} catch (Exception ex) {

			}
		}

		LOGGER.debug("Content Length file download: " + contentLength);
		
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
	    responseHeaders.add("Access-Control-Expose-Headers", "*");
	    responseHeaders.add("FILE_LENGTH", contentLength);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).contentLength(length).
				headers(responseHeaders).body(resource);
	}

	@GetMapping("/downloadDocumento/{idDocumento}")
	public ResponseEntity<Resource> downloadDocumento(@PathVariable Integer idDocumento, HttpServletRequest request) {
		Resource resource = registroDocumentoService.loadDocumentoAsResource(idDocumento);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.debug("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		/*
		 * commentare la seguente riga solo per test e sviluppo (questo REST non è sicuro in ambiente di produzione)
		 */
		resource = null;
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/downloadZipFile")
	public ResponseEntity<Resource> downloadZipFile(@RequestParam(name = "protocol", required = true) String protocol
            , @RequestParam(name = "docIDs", required = true) String docIDs
            , HttpServletRequest request) throws Exception {
		// Restituisce un file zip con tutti i documenti di un determinato protocollo Domus
		
		LOGGER.debug("Invoco il metodo registroDocumentoService.loadZipFileAsResourceByDocIDs per ottenere il file zip");
		LOGGER.debug("protocol = " + protocol);
		
		Resource resource = registroDocumentoService.loadZipFileAsResourceByDocIDs(docIDs);
		
		if (resource != null && resource.exists())
			LOGGER.debug("Resource exists");
		// Try to determine file's content type
		String contentType = null;
		String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.debug("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.contentLength(resource.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);
	}
	
	@GetMapping("/conferences/{idConference}/downloadFile/template")
	@ApiOperation(value = "service to download template for the conference with (idConference)")
	public RispostaNoData downloadTemplateIndizione(@PathVariable Integer idConference, String eventType) throws Exception {
		String esito = documentoService.creaTemplatePerDownload(idConference, eventType);
		RispostaNoData risposta = null;
		if (esito != null) {
			risposta = new RispostaNoData();
			risposta.setMsg(esito);
		}
		return risposta;
	}
	
	
	/**
	 * Inserimento di un documento alla conferenza {idConference}
	 * rappresentante il documento già presente su Alfresco con id {idAlfresco}
	 * 
	 * @param document
	 * @param idConference
	 * @param idAlfresco
	 * @return
	 */
	@PostMapping(value = "/conferences/{idConference}/documents/Alfresco/{idAlfresco}/{idAccreditamento}")
	@ApiOperation(value = "link Alfresco document (idAlfresco) to conference (idConference)")
	public RispostaDocumentoDTO collegaDocumentoAlfrescoToConferenza(@RequestBody DocumentoDTO document,
			@PathVariable Integer idConference, @PathVariable Integer idAccreditamento, @PathVariable String idAlfresco) {
		DocumentoDTO documentoDTO = documentoService.creaDocumentoRiferimento(document, idAlfresco,
				ModalitaSalvataggioFile.Alfresco, FonteFile.suap, idConference, idAccreditamento);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
		return risposta;
	}
	
	/*
	 * METODI PER FIRMA ---------------------------------------------------------------------------
	 */
	
	/**
	 * Sblocca la sessione di firma, bloccata da un altro utente (ruolo responsabile/creatore) 
	 * @return
	 */
	@PatchMapping("/documents/{id}/unlock")
	@ApiOperation(value = "unlock document, lock by another user for signing, identified by id")
	public RispostaDocumentoDTO sbloccaSessioneFirma(@PathVariable Integer id, @RequestBody FirmaDTO firma) {
		DocumentoDTO documentoDTO = documentoService.sbloccaSessioneFirma(id);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
		return risposta;
	}

	@PatchMapping("/documents/{id}/unlockcallback")
	@ApiOperation(value = "Esegue la callback per storicizzare il file ritornato dal sistema esterno di firma")
	public RispostaDocumentoDTO unlockcallback(@PathVariable Integer id, @RequestBody FirmaDTO firma) {		
		DocumentoDTO documentoDTO = documentoService.sbloccaSessioneFirmaConCallback(id, firma);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
		return risposta;
	}	
	
	@GetMapping("/lockAndDownload/{token}")
	@ApiOperation(value = "Esegue il lock del file e successivamente esegue il download")
	public ResponseEntity<Resource> lockAndDownload(@PathVariable String token, HttpServletRequest request, Boolean lock) {	
				
		ResponseEntity<Resource> response = this.downloadFile(token, request);
		if (lock == null || !lock) {
			documentoService.manageSignDocumentDownload(token);
		}
		return response;
	}
	
	@GetMapping(value = "/sign/documentsindraft")
	public RispostaListaDocumentoFirmaDTO getDocumentiInBozza(RicercaDocumentoDTO documento) {
		LOGGER.debug("Class: DocumentsController - Method: getDocumentiInBozza");
		RispostaListaDocumentoFirmaDTO risposta = new RispostaListaDocumentoFirmaDTO();
		ListaDocumentoFirmaDTO lista = documentFacade.getDocumentiInBozza(documento);
		risposta.setData(lista);
		return risposta;
		
	}
	
	@GetMapping(value = "/sign/documentssigned")
	public RispostaListaDocumentoFirmaDTO getDocumentiFirmati(RicercaDocumentoDTO documento) {
		LOGGER.debug("Class: DocumentsController - Method: getDocumentiFirmati");
		RispostaListaDocumentoFirmaDTO risposta = new RispostaListaDocumentoFirmaDTO();
		ListaDocumentoFirmaDTO lista = documentFacade.getDocumentiFirmati(documento);
		risposta.setData(lista);
		return risposta;
	}
	
	@GetMapping(value = "/sign/documentsrejected")
	public RispostaListaDocumentoFirmaDTO getDocumentiRifiutati(RicercaDocumentoDTO documento) {
		LOGGER.debug("Class: DocumentsController - Method: getDocumentiRifiutati");
		RispostaListaDocumentoFirmaDTO risposta = new RispostaListaDocumentoFirmaDTO();
		ListaDocumentoFirmaDTO lista = documentFacade.getDocumentiRifiutati(documento);
		risposta.setData(lista);
		return risposta;
	}
	
	@PatchMapping(value = "/sign/documents/signdocuments")
	public RispostaNoData firmaMultiplaDocumenti(@RequestBody ListaDocumentoFirmaDTO listaDocumenti,//@RequestParam(required = false) MultipartFile[] files,
			@RequestParam(required = false) String calamaioRemoteUsername,
			@RequestParam(required = false) String calamaioRemotePassword,
			@RequestParam(required = false) String calamaioRemoteOTP,
			@RequestParam(required = false) String calamaioRemoteDomain,
			@RequestParam(required = false) String calamaioRemoteDocumentId,
			@RequestParam(required = false, defaultValue = "false") Boolean padesCades,
			@RequestParam(required = false) String signers) {
		LOGGER.debug("Class: DocumentsController - Method: firmaMultiplaDocumenti");
		RispostaNoData errRisposta = new RispostaNoData();
		RispostaListaDocumentoFirmaDTO risposta = new RispostaListaDocumentoFirmaDTO();
		String errorMsg = null;
		boolean isError = false;
		try {
			documentFacade.firmaDocumenti(listaDocumenti.getIdResponsabileCST(), listaDocumenti.getListaIdDocumenti(), 
					listaDocumenti.getCalamaioRemoteUsername(), listaDocumenti.getCalamaioRemotePassword(), listaDocumenti.getCalamaioRemoteOTP(), 
					listaDocumenti.getCalamaioRemoteDomain(), null, listaDocumenti.getPadesCades());
		}catch (InternalError e) {
			errorMsg = e.getMessage();
			isError = true;
		}
		if(isError || listaDocumenti == null ) {
			errRisposta.getErrors().add(new Errore("001", errorMsg == null?"Impossibile firmare digitalmente il con il servizio di firma remota.":errorMsg));
			errRisposta.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			errRisposta.setMsg("errore generico");
			return errRisposta;
		}
		
		return risposta;
	}
	
	@PatchMapping(value = "/sign/documents/reject")
	@ApiOperation(value = "update status document_list in rejected")
	public RispostaNoData rifiutaDocumenti(@RequestBody ListaDocumentoFirmaDTO rifiutaDocumenti) {
		LOGGER.debug("Class: DocumentsController - Method: rifiutaDocumenti");
		String errorMsg = null;
		boolean isError = false;
		RispostaNoData errRisposta = new RispostaNoData();
		
		try {
			documentFacade.rifiutaDocumenti(rifiutaDocumenti.getIdResponsabileCST(), rifiutaDocumenti.getListaIdDocumenti());
		}catch (InternalError e) {
			errorMsg = e.getMessage();
			isError = true;
		}
		
		if(isError ) {
			errRisposta.getErrors().add(new Errore("001", errorMsg == null?"Errore in fase di rifiuta documenti.":errorMsg));
			errRisposta.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			errRisposta.setMsg("errore generico");
			return errRisposta;
		}
		String esito = "ok";
		LOGGER.debug("I documenti sono stati rifiutati con esito: " + esito);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
		
	}

	@PostMapping(value = "/conferences/{idConference}/marktosigndocuments")
	@ApiOperation(value = "insert document for conference identified by id like DRAFT")
	public RispostaNoData creaDocumentoPerFirmaMultipla(
			@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String url, 
			@RequestParam(required = false)  String name, 
			@RequestParam(required = false)  String type,
			@RequestParam(required = false) String category, 
			@RequestParam(required = false)  String visibility,
			@PathVariable Integer idConference,
			@RequestParam(required = false) String protocolNumber,
			@RequestParam(required = false) String protocolDate, 
			@RequestParam(required = false) String inventoryNumber,
			@RequestParam(required = false) String cityReference, 
			@RequestParam(required = false) String model,
			@RequestParam(required = false, defaultValue = "false") Boolean remoteSignature,
			@RequestParam(required = false) String signers, 
			@RequestParam(required = false, defaultValue = "false") Boolean signed,
			@RequestParam(required = false, defaultValue = "false") Boolean fileComplient,
			@RequestParam(required = false) MultipartFile[] attachments,
			@RequestParam(required = false) Integer managerCST
			) {
		
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setUrl(url);
		documentoFileDTO.setCategoria(documentoService.getCategoriaDocDinamica(category, type));
		documentoFileDTO.setTipoDocumento(type);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setVisibilitaPartecipanti(visibility);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setNumeroInventario(inventoryNumber);
		documentoFileDTO.setCompetenzaTerritoriale(cityReference);
		documentoFileDTO.setModello(documentoService.getModelloDinamico(model, category));
		documentoFileDTO.setFirmatari(signers);
		documentoFileDTO.setFirmato(signed);
		documentoFileDTO.setFileConforme(fileComplient);
		documentoFileDTO.setAttachments(attachments);
			
		Documento documento = documentoService.creaDocumentoFirmaMultipla(documentoFileDTO, idConference, managerCST);
		
		DocumentoDTO documentoDTO = documentoBuilder.buildDocumentoDTO(documento);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
	
		return risposta;
			
	}
}
