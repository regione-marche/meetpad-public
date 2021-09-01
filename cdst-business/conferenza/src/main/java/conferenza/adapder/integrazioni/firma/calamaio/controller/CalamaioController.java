package conferenza.adapder.integrazioni.firma.calamaio.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import conferenza.adapder.integrazioni.firma.calamaio.service.CalamaioService;
import conferenza.firma.DTO.FirmaDTO;
import conferenza.firma.DTO.bean.RispostaFirmaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@CrossOrigin
@RestController
@Api(tags = { "Firma Calamaio API" })
@RequestMapping(value = "/calamaio")
public class CalamaioController {

	@Autowired
	CalamaioService calamaioService;

	@Autowired
    ResourceLoader resourceLoader;
	

	@Value("${calamaio.applicationPath}") 
	private String applicationPath;
	
	@PostMapping("/fillxml")
	@ApiOperation(value = "Popola  la callback per storicizzare il file ritornato dal sistema esterno di firma Calamio")	
	public RispostaFirmaDTO fillCalamaioXMLRequest(@RequestBody FirmaDTO firma) {
		RispostaFirmaDTO risposta = new RispostaFirmaDTO();
		try {
			firma=calamaioService.fillCalamaioXMLRequest(firma);
			firma.setResource(null);
			risposta.setData(firma);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return risposta;		
		
	}
	
	@GetMapping("/download")
	@ApiOperation(value = "Scarica l'applicazione Calamaio")	
	@ResponseBody
	public ResponseEntity<Resource> download(HttpServletRequest request) throws IOException {

		Resource resource = resourceLoader.getResource(applicationPath);
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
		 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFile().getName() + "\"")
		 .body(resource);
		
	}
	
	
}
