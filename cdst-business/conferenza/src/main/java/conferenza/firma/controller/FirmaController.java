package conferenza.firma.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.DTO.bean.RispostaFirmaDTO;
import conferenza.firma.model.bean.Firma;
import conferenza.firma.service.FirmaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = { "Firma API" })
@RequestMapping(value = "/firma")
public class FirmaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirmaController.class);
	
	@Autowired
	FirmaService firmaService;
	
		
	/**
	 * Attività propedeutiche alla firma:
	 * 1) Download del file per effettuare la firma...
	 * 2) Lock del file..
	 * @param firma
	 * @return
	 */
	@PostMapping("/lock")
	@ApiOperation(value = "Recupera il file per spedirlo al sistema di firma..e lo locka contestualmente")
	public ResponseEntity<?> getfiletosign(@RequestBody FirmaDTO firma, HttpServletRequest request) {
		
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		Resource resource = null;
		
		if(firma.getDownloadToken()!=null)
			firma.setIdDocumento(firmaService.getDocIdByDownloadToken(firma.getDownloadToken()));
				
		try {
			firma=firmaService.doActionLock(firma);
		} catch (IOException e) {
			e.printStackTrace();
			
			risposta.setMsg("File lock");
			risposta.setCode("200");
			risposta.setMsg(e.getMessage());
				
			return ResponseEntity.status(HttpStatus.OK).body(risposta );
		}
		
		resource = firma.getResource();

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

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	
	/**
	 * 
	 * @param firma
	 * @param request
	 * @return
	 */
	@PostMapping("/lockcallback")
	@ApiOperation(value = "Esegue la callback per ottenere i dati da mandare al sistema esterno di firma")
	public RispostaFirmaDTO getcallback(@RequestBody FirmaDTO firma, HttpServletRequest request) {		
		RispostaFirmaDTO risposta = new RispostaFirmaDTO();
		try {
			firma=firmaService.doActionLock(firma);
			firma.setResource(null);
			risposta.setData(firma);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return risposta;
	}
	
	@PostMapping("/unlockcallback")
	@ApiOperation(value = "Esegue la callback per storicizzare il file ritornato dal sistema esterno di firma")
	public RispostaFirmaDTO unlockcallback(@RequestBody FirmaDTO firma, HttpServletRequest request) {		
		RispostaFirmaDTO risposta = new RispostaFirmaDTO();
		try {
			firma=firmaService.doActionUnlck(firma);
			firma.setResource(null);
			risposta.setData(firma);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return risposta;
	}	
	
	

	
	
	/**
	 * 
	 * @param file
	 * @param downloadToken
	 * @param fk_tipo_firma
	 * @param crc
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/unlockdt", method = RequestMethod.POST)
	public RispostaIdentifiableDTO unlockdt(
			@RequestParam MultipartFile file, 	
			@RequestParam String downloadToken,
			@RequestParam Integer fk_tipo_firma,
			@RequestParam String crc
	) throws IOException {
		
		Integer idDocumento=firmaService.getDocIdByDownloadToken(downloadToken);
		//-------------------------------------------------------------------
		//..da qui unlockdt è uguale a unlock
		//-------------------------------------------------------------------
		FirmaDTO firma =new FirmaDTO();		
		firma.setIdDocumento(idDocumento);
		firma.setFk_tipo_firma(fk_tipo_firma);
		firma.setCrc(crc);
		//@TODO: intercettare l'id user dal token di autorizzazione
		firma.setIdUser(new Integer(1));
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		//-------------------------------------------------------------------
        InputStreamResource isresource = new InputStreamResource(file.getInputStream());      
		try {

			firma.setResource(isresource);
			firma.setFileName(file.getOriginalFilename());
			
			firma=firmaService.doActionUnlck(firma);			
			risposta.setMsg("File caricato:"+firma.getShot());
			risposta.setCode("200");
			
		} catch (Exception e) {
			e.printStackTrace();
			risposta.setMsg(e.getMessage());
		}
		//-------------------------------------------------------------------		
		return risposta;		
		
	}
	

	
	
	
	/**
	 * Attività post firma:
	 * 1 - salvataggio del file firmato
	 * 2 - unlock del file
	 * 
	 * @param file
	 * @param idDocumento
	 * @param fk_tipo_firma
	 * @param crc
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/unlock", method = RequestMethod.POST)
	public RispostaIdentifiableDTO unlock(
			@RequestParam MultipartFile file, 	
			@RequestParam Integer idDocumento,
			@RequestParam Integer fk_tipo_firma,
			@RequestParam String crc,
			@RequestParam Integer idConferenza
	) throws IOException {
		
		FirmaDTO firma =new FirmaDTO();
		firma.setIdDocumento(idDocumento);
		firma.setFk_tipo_firma(fk_tipo_firma);
		firma.setCrc(crc);
		//@TODO: intercettare l'id user dal token di autorizzazione
		firma.setIdUser(new Integer(1));
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		//-------------------------------------------------------------------
        InputStreamResource isresource = new InputStreamResource(file.getInputStream());      
		try {

			firma.setResource(isresource);
			firma.setFileName(file.getOriginalFilename());
			
			firma=firmaService.doActionUnlck(firma);			
			risposta.setMsg("File caricato:"+firma.getShot());
			risposta.setCode("200");
			
		} catch (Exception e) {
			e.printStackTrace();
			risposta.setMsg(e.getMessage());
		}
		//-------------------------------------------------------------------		
		return risposta;	
	}
	
	/**
	 * Resituisce per l'utente autorizzato il CRC frl  SUO ultimo file in stato LOCKED 
	 * Restituisce null se non ci sono file lockati dall'utente!
	 * @param idDocumento
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/crc/{idDocumento}", method = RequestMethod.GET)
	public String getCRC( 	
			@PathVariable Integer idDocumento
	) throws IOException {
		String crc=firmaService.getLastCRCForUserDoc(idDocumento);
		return crc;	
	}
	
	/**
	 * 
	 * @param idConferenza
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/lista/{idConferenza}", method = RequestMethod.GET)
	public List<Firma> lista( 	
			@PathVariable Integer idConferenza
	) throws IOException {		
		List<Firma> listFirma= firmaService.getListFirmaByConference(idConferenza);
		return listFirma;	
	}
	
	
	/**
	 * Cancellazione della "Sessioen di Firma"..
	 * @param firma
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public RispostaIdentifiableDTO cancel(@RequestBody FirmaDTO firma) {
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		try {			
			
			firmaService.doActionCancelSigningSession(firma);
			
			risposta.setMsg("Firma Cancellata");
			risposta.setCode("200");			
		} catch (Exception e) {
			e.printStackTrace();
			risposta.setMsg(e.getMessage());
		}
		return risposta;
	}	
	
	
	
	
	
}
