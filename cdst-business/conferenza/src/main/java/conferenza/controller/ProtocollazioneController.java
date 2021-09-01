package conferenza.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.ListaProtocolloDTO;
import conferenza.DTO.ProtocolloDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.DTO.RispostaListaProtocolloDTO;
import conferenza.service.ProtocollazioneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = { "Protocollings API" })
public class ProtocollazioneController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProtocollazioneController.class);
	
	@Autowired
	ProtocollazioneService protocollazioneService;
	
	/**
	 * 
	 * @param id     idConferenza
	 * @param locale
	 * @return
	 */
	@GetMapping("/conferences/{id}/Protocols")
	@ApiOperation(value = "get protocols from conference")
	public RispostaListaProtocolloDTO findProtocolli(@PathVariable Integer id) {
		LOGGER.debug("Class: ProtocollazioneController - Method: getListaProtocolli");
		ListaProtocolloDTO lista = this.protocollazioneService.findProtocolliConferenza(id);
		RispostaListaProtocolloDTO risposta = new RispostaListaProtocolloDTO();
		risposta.setData(lista);
		return risposta;
	}
	
	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@PatchMapping("/conferences/Protocols/{protocolId}")
	@ApiOperation(value = "modify only state of protocol")
	public RispostaIdentifiableDTO patchProtocollo(@PathVariable Integer protocolId) {
		LOGGER.debug("Class: ProtocollazioneController - Method: patchProtocollo");
		IdentifiableDTO identifiableDTO = this.protocollazioneService.patchProtocollo(protocolId);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}



}
