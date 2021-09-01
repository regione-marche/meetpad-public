package cdst_be_marche.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.iap.Response;

import cdst_be_marche.DTO.ConferenzaDTO;
import cdst_be_marche.DTO.DocumentazioneDTO;
import cdst_be_marche.DTO.DocumentoDTO;
import cdst_be_marche.DTO.DocumentoFileDTO;
import cdst_be_marche.DTO.FonteFile;
import cdst_be_marche.DTO.ModalitaSalvataggioFile;
import cdst_be_marche.DTO.RispostaDocumentazioneDTO;
import cdst_be_marche.DTO.RispostaDocumentoDTO;
import cdst_be_marche.DTO.bean.RispostaNoData;
import cdst_be_marche.service.DocumentoService;
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
	HttpServletResponse response;
	
	@GetMapping(value = "/conferences/{idConference}/documents")
	@ApiOperation(value = "get documents for conference identified by id")
	public RispostaDocumentazioneDTO getAllDocuments(@PathVariable Integer idConference, Boolean key) {
		DocumentazioneDTO documentazione = this.documentoService.findAllDocuments(idConference, key);
		RispostaDocumentazioneDTO risposta = new RispostaDocumentazioneDTO();
		risposta.setData(documentazione);
		return risposta;
	}

	@PostMapping(value = "/conferences/{idConference}/documents")
	@ApiOperation(value = "insert document for conference identified by id")
	public RispostaDocumentoDTO creaDocumento(@RequestParam MultipartFile file, @RequestParam String name,
			@RequestParam String type, @RequestParam(required = false) String category, @RequestParam String visibility,
			@PathVariable Integer idConference, @RequestParam(required = false) String protocolNumber,
			@RequestParam(required = false) String protocolDate, @RequestParam(required = false) String inventoryNumber) {
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setCategoria(category);
		documentoFileDTO.setTipoDocumento(type);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setVisibilitaPartecipanti(visibility);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setNumeroInventario(inventoryNumber);
		DocumentoDTO documentoDTO = documentoService.creaDocumento(documentoFileDTO, idConference);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
		return risposta;
	}

	@PutMapping(value = "/documents/{id}")
	@ApiOperation(value = "update document identified by id")
	public RispostaDocumentoDTO modificaDocumento(@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String name, @RequestParam String type,
			@RequestParam(required = false) String category, @RequestParam String visibility, @PathVariable Integer id,
			@RequestParam(required = false) String protocolNumber, @RequestParam(required = false) String protocolDate,
			@RequestParam(required = false) String inventoryNumber) {
		DocumentoFileDTO documentoFileDTO = new DocumentoFileDTO();
		documentoFileDTO.setFile(file);
		documentoFileDTO.setCategoria(category);
		documentoFileDTO.setTipoDocumento(type);
		documentoFileDTO.setNomeFile(name);
		documentoFileDTO.setVisibilitaPartecipanti(visibility);
		documentoFileDTO.setNumeroProtocollo(protocolNumber);
		documentoFileDTO.setDataProtocollo(protocolDate);
		documentoFileDTO.setNumeroInventario(inventoryNumber);
		DocumentoDTO documentoDTO = documentoService.modificaDocumento(documentoFileDTO, id);
		RispostaDocumentoDTO risposta = new RispostaDocumentoDTO();
		risposta.setData(documentoDTO);
		return risposta;
	}

	@DeleteMapping(value = "/documents/{id}")
	@ApiOperation(value = "delete document identified by id")
	public RispostaNoData eliminaDocumento(@PathVariable Integer id) {
		String esito = documentoService.eliminaDocumento(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}

	@GetMapping("/downloadFile/{token}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String token, HttpServletRequest request) {
		Resource resource = documentoService.loadFileAsResource(token);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@GetMapping("/conferences/{idConference}/downloadFile/template")
	@ApiOperation(value = "service to download template for the conference with (idConference)")
	public RispostaNoData downloadTemplateIndizione(@PathVariable Integer idConference, String eventType) throws IOException {
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
}
