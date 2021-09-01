package conferenza.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.EnteRicercaGeograficaDTO;
import conferenza.DTO.EnteRicercaTipologicaDTO;
import conferenza.DTO.ListaEnteDTO;
import conferenza.DTO.RispostaListaEnteDTO;
import conferenza.service.EnteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/authorities")
@Api(tags = "Authorities API")
public class EnteController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnteController.class);
	
	@Autowired
	EnteService enteService;
	
	@GetMapping("/geographicalSearch")
	@ApiOperation(value = "authorities from a geographical search")
	public RispostaListaEnteDTO findEnteByRicercaGeografica(EnteRicercaGeograficaDTO ricerca) {
		LOGGER.debug("Class: EnteController - Method: getGeographicalSearch");
		ListaEnteDTO lista = enteService.findEnteByRicercaGeografica(ricerca);
		RispostaListaEnteDTO risposta = new RispostaListaEnteDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	@GetMapping("/typologicalSearch")
	@ApiOperation(value = "authorities from a geographical search")
	public RispostaListaEnteDTO findEnteByRicercaTipologica(EnteRicercaTipologicaDTO ricerca) {
		LOGGER.debug("Class: EnteController - Method: getTypologicalSearch");
		ListaEnteDTO lista = enteService.findEnteByRicercaTipologica(ricerca);
		RispostaListaEnteDTO risposta = new RispostaListaEnteDTO();
		risposta.setData(lista);
		return risposta;
	}

}
