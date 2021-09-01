package conferenza.mediateca.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.PageableDTO;
import conferenza.mediateca.DTO.MediatecaDTO;
import conferenza.mediateca.DTO.bean.ListMediatecaDTO;
import conferenza.mediateca.DTO.bean.RispostaListMediatecaDTO;
import conferenza.mediateca.model.Mediateca;
import conferenza.mediateca.service.MediatecaService;
import conferenza.report.DTO.ListaReportDTO;
import conferenza.report.DTO.ReportDTO;
import conferenza.report.DTO.RispostaListaReportDTO;
import conferenza.report.controller.ReportTestController;
import conferenza.report.model.Report;
import conferenza.report.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Lazy
@CrossOrigin
@RestController
@Api(tags = "Mediateca Controller API")
@RequestMapping(value = "/media-library")
public class MediatecaController {

	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MediatecaController.class);
	
	@Autowired
	MediatecaService mediatecaService;
	
	/**
	 * Lista dei report publici
	 * @param request
	 * @return
	 */
	@GetMapping()
	@ApiOperation(value = "Lista Multimedia in Mediateca")
	public RispostaListMediatecaDTO listamediateca(PageableDTO page,
			// @RequestParam String filtro1,
			String search, HttpServletRequest request) {
		ListMediatecaDTO lista = mediatecaService.getListRecordMediateca(page, search);
		RispostaListMediatecaDTO risposta = new RispostaListMediatecaDTO();
		risposta.setData(lista);
		return risposta;
	}
	
}
