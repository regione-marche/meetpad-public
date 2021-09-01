package conferenza.adapder.integrazioni.suap.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import conferenza.adapder.integrazioni.suap.DTO.IntegSuapFrontieraDTO;
import conferenza.adapder.integrazioni.suap.persistance.IntegFrontieraConferenzaRepository;
import conferenza.adapder.integrazioni.suap.service.IntegProtocolService;
import conferenza.adapder.integrazioni.suap.service.IntegSuapFrontieraService;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.service.ConferenzaService;
import conferenza.service.UtenteService;

@RestController
@org.springframework.context.annotation.Lazy
@RequestMapping(value = "/integrazioni/suap")
public class IntegSuapFrontieraController {

	
	private static final Logger logger = LoggerFactory.getLogger(IntegSuapFrontieraController.class.getName());

	//@Autowired
	//private AlfrescoHelper alfrescoHelper;
	@Autowired
	private IntegSuapFrontieraService frontieraService;
	
	@Autowired
	private IntegProtocolService protocolService;

	@Autowired
	IntegFrontieraConferenzaRepository frontieraConfRepo;
	
	@Autowired
	ConferenzaService confService;
	
	@Autowired
	UtenteService utenteService;
	
	@RequestMapping(value = "submitpratica", method = RequestMethod.POST)
	public String createConference(@RequestBody IntegSuapFrontieraDTO frontieraDTO, HttpServletRequest request)
			throws IOException {
		Integer idConferenza = null;
		if (!this.frontieraConfRepo.findByIdPratica(frontieraDTO.getId_pratica()).isPresent()) {
			idConferenza = frontieraService.persistConference(frontieraDTO);
		} else {
			idConferenza = frontieraConfRepo.findByIdPratica(frontieraDTO.getId_pratica()).get().getConferenza().getIdConferenza();
		}

		return frontieraService.getUrlConferenza(idConferenza);
	}
	
	@RequestMapping(value = "protocollo/testupload/", method = RequestMethod.POST)
	  public String handleFileUpload(
	          @RequestParam("file") MultipartFile multipartFile) throws IOException {
	      String name = multipartFile.getOriginalFilename();
	      System.out.println("File name: "+name);
	      //todo save to a file via multipartFile.getInputStream()
	      byte[] bytes = multipartFile.getBytes();
	      	      
	      logger.debug("File uploaded content:\n" + bytes.length);
	      
	      return "PROTOCOLLO:"+bytes.length;
	  }
	
	/**
	 * 	
	 * @param suapPraticaId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "testrestclient/{suapPraticaId}", method = RequestMethod.GET)
	public ResponseEntity<String> testrestclient(
            @PathVariable Integer suapPraticaId, HttpServletRequest request
     ) {
		ResponseEntity<String>  response=null;
		try {
			IntegProtocolloDTO integDTO =new IntegProtocolloDTO();
			integDTO.setIdPratica(suapPraticaId);
			response=protocolService.testRestClient(integDTO);
		} catch (IOException e) {
			e.printStackTrace();
		}				
		return response;
	}
}