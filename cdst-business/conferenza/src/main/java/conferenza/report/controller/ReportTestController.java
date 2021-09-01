package conferenza.report.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.report.service.ReportService;
import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Lazy
@CrossOrigin
@RestController
@Api(tags = "Report Test API")
@RequestMapping(value = "/reporttest")
public class ReportTestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTestController.class);
	
	@Autowired
	ReportService reportService;
	
	/**
	 * 
	 * @param idconferenza
	 * @return
	 */
	@RequestMapping(value = "/test1/{token}", method = RequestMethod.GET)
	public ResponseEntity<?>  test1(
			@PathVariable String token,
			HttpServletRequest request
	) {

		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		Resource resource = null;
		
		String textmessage = "Report 1 ";
		reportService.doTest();

		InputStreamResource responsePDF=null;
		try {
			responsePDF = reportService.getStreamResource(reportService.getName(),"REPORT");		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(responsePDF.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.debug("Could not determine file type.");
		}
		
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + responsePDF.getFilename() + "\"")
				.body(responsePDF);
	}		
	
	
}
