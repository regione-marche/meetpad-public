package conferenza.report.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.ConferenzaCompletaDTO;
import conferenza.DTO.RispostaConferenzaCompletaDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.report.DTO.ListaReportDTO;
import conferenza.report.DTO.ReportDTO;
import conferenza.report.DTO.RispostaListaReportDTO;
import conferenza.report.model.Report;
import conferenza.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Lazy
@CrossOrigin
@RestController
@Api(tags = "Report Controller API")
@RequestMapping(value = "/report")
public class ReportController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTestController.class);
	
	@Autowired
	ReportService reportService;

	
	/**
	 *  Creare Report con  filtri
	 *  per gestire le diverse tipologie di conferenza
	 *  
		tipologia_conferenza_specializzazione

		codice,descrizione
		"1"	"tipologiaConferenzaSpec.semplificata"
		"2"	"tipologiaConferenzaSpec.simultanea"
		"3"	"tipologiaConferenzaSpec.regionale"
		"4"	"tipologiaConferenzaSpec.BULDecisoriaSimultanea"
		"5"	"tipologiaConferenzaSpec.BUListruttoriaSimultanea"

	 *  
	 *  
	 *  
		select cc.* from conferenza cc 
		inner join tipologia_conferenza_specializzazione tt on tt.codice=cc.fk_tipologia_conferenza_specializzazione
		where 1=1
		and tt.descrizione='tipologiaConferenzaSpec.semplificata'
		and cc.fk_tipologia_conferenza_specializzazione='4'
	 */
	
	
	/**
	 * 
	 * @param codice del report
	 * @return
	 */
	@RequestMapping(value = "/{codice}", method = RequestMethod.GET)
	public ResponseEntity<?>  downloadReport(
			@PathVariable String codice,
			HttpServletRequest request
	) {

		String filename=null;
		InputStreamResource responseResource=null;
		
		try {
			Report report=reportService.getReportBycodice(codice);			
			reportService.doConferenceByTipoReport(report);
			responseResource = reportService.getStreamResource(reportService.getName(),report.getTiporeport());
			filename=reportService.getOutName();
				
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("Could not retirve file.");
		}
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(responseResource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.debug("Could not determine file type.");
		}
		
		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(responseResource);
	}		

	/**
	 * Lista dei report
	 * @param request
	 * @return
	 */
	@GetMapping("/")
	@ApiOperation(value = "Lista Report publici")
	public RispostaListaReportDTO getReportList(HttpServletRequest request) {		
		List<ReportDTO> listaDTO=new ArrayList<ReportDTO>();
		RispostaListaReportDTO risposta =new RispostaListaReportDTO();
		List<Report> lista= reportService.getPublicReports();
		listaDTO= ReportDTO.fillListReportDTO(lista) ;
		ListaReportDTO liatareportdto= ListaReportDTO.fillListaReportDTO(listaDTO);
		
		risposta.setData(liatareportdto);			
		return risposta;
	}
	
}
