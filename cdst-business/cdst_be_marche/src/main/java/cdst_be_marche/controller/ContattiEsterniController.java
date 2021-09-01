package cdst_be_marche.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cdst_be_marche.DTO.ContattoSupportoDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaContattoSupportoDTO;
import cdst_be_marche.DTO.RispostaIdentifiableDTO;
import cdst_be_marche.DTO.RispostaListaContattoSupportoDTO;
import cdst_be_marche.DTO.bean.RispostaNoData;
import cdst_be_marche.service.ContattiEsterniService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = "Contacts API")
public class ContattiEsterniController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContattiEsterniController.class);
	
	@Autowired
	ContattiEsterniService contattiService;
	
	@GetMapping("/conferences/{id}/supportContacts")
	@ApiOperation(value = "all support contacts")
	public RispostaListaContattoSupportoDTO findAllSupportContacts (@PathVariable Integer id) {
		LOGGER.debug("Class: ContattiEsterniController - Method: getSupportContacts");
		ListaContattoSupportoDTO listaDTO = this.contattiService.findAllSupportContacts(id);
		RispostaListaContattoSupportoDTO risposta = new RispostaListaContattoSupportoDTO();
		risposta.setData(listaDTO);
		return risposta;
	}
	
	@PostMapping("/conferences/{id}/supportContacts")
	@ApiOperation(value = "insert a support contact")
	public RispostaIdentifiableDTO creaSupportoContatto(@PathVariable Integer id,
			@RequestBody ContattoSupportoDTO contattoDTO) {
		LOGGER.debug("Class: ContattiEsterniController - Method: postSupportContacts");
		IdentifiableDTO identifiableDTO = this.contattiService.creaSupportoContatto(contattoDTO, id, null);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}
	
	@PutMapping("/supportContacts/{id}")
	@ApiOperation(value = "update support contact")
	public RispostaIdentifiableDTO modificaSupportoContatto(@PathVariable Integer id,
			@RequestBody ContattoSupportoDTO contattoDTO) {
		LOGGER.debug("Class: ContattiEsterniController - Method: putSupportContacts");
		IdentifiableDTO identifiableDTO = this.contattiService.creaSupportoContatto(contattoDTO, null, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}
	
	@DeleteMapping("/supportContacts/{id}")
	@ApiOperation(value = "delete support contact")
	public RispostaNoData eliminaContattoSupporto(@PathVariable Integer id) {
		LOGGER.debug("Class: ContattiEsterniController - Method: deleteContattoSupporto");
		String esito = this.contattiService.eliminaContattoSupporto(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}

}
