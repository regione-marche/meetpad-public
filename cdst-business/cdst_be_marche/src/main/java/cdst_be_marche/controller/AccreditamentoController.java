package cdst_be_marche.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cdst_be_marche.DTO.AccreditamentoDTO;
import cdst_be_marche.DTO.AccreditamentoFileDTO;
import cdst_be_marche.DTO.AccreditamentoInfoDTO;
import cdst_be_marche.DTO.IdentifiableDTO;
import cdst_be_marche.DTO.ListaAccreditamentoDTO;
import cdst_be_marche.DTO.RispostaAccreditamentoInfoDTO;
import cdst_be_marche.DTO.RispostaIdentifiableDTO;
import cdst_be_marche.DTO.RispostaListaAccreditamentoDTO;
import cdst_be_marche.DTO.bean.RispostaNoData;
import cdst_be_marche.service.AccreditamentoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api(tags = "Accreditations API")
public class AccreditamentoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccreditamentoController.class);

	@Autowired
	AccreditamentoService accreditamentoService;

	/**
	 * 
	 * @param token1
	 * @param token2
	 * @param locale
	 * @return
	 */
	@GetMapping("/accreditations")
	@ApiOperation(value = "accreditation identified by token1, token2 and user logged in")
	public RispostaAccreditamentoInfoDTO findAccreditato(String token1, String token2) {
		LOGGER.debug("Class: AccreditamentoController - Method: getAccreditato");
		AccreditamentoInfoDTO accreditamentoInfoDTO = this.accreditamentoService.findAccreditamento(token1, token2);
		RispostaAccreditamentoInfoDTO risposta = new RispostaAccreditamentoInfoDTO();
		risposta.setData(accreditamentoInfoDTO);
		return risposta;

	}

	/**
	 * 
	 * @param id     idConferenza
	 * @param locale
	 * @return
	 */
	@GetMapping("/conferences/{id}/accreditations")
	@ApiOperation(value = "accreditations to validate from conference")
	public RispostaListaAccreditamentoDTO findAccreditamentiConferenza(@PathVariable Integer id) {
		LOGGER.debug("Class: AccreditamentoController - Method: getAccreditamentoDaValidare");
		ListaAccreditamentoDTO lista = this.accreditamentoService.findAccreditamentiConferenza(id);
		RispostaListaAccreditamentoDTO risposta = new RispostaListaAccreditamentoDTO();
		risposta.setData(lista);
		return risposta;
	}

	/**
	 * 
	 * @param ruolo
	 * @param file
	 * @param idPart
	 * @param nome
	 * @param cognome
	 * @param codiceFiscale
	 * @param email
	 * @param locale
	 * @return
	 */
	@PostMapping("/accreditations")
	@ApiOperation(value = "insert accreditation")
	public RispostaIdentifiableDTO createAccreditamento(@RequestParam(required = false) MultipartFile file,
			@RequestParam String token1, @RequestParam String token2,
			@RequestParam String role, @RequestParam String name,
			@RequestParam String surname, @RequestParam String fiscalCode, @RequestParam String email) {
		LOGGER.debug("Class: AccreditamentoController - Method: postAccreditamento");
		AccreditamentoFileDTO fileDTO = new AccreditamentoFileDTO();
		fileDTO.setToken1(token1);
		fileDTO.setToken2(token2);
		fileDTO.setRuoloPersona(role);
		fileDTO.setNome(name);
		fileDTO.setCognome(surname);
		fileDTO.setCodiceFiscale(fiscalCode);
		fileDTO.setEmail(email);
		fileDTO.setFile(file);
		IdentifiableDTO identifiableDTO = this.accreditamentoService.createAccreditamento(fileDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	@PutMapping("/accreditations/{id}")
	@ApiOperation(value = "update accreditation")
	public RispostaIdentifiableDTO modificaAccreditamento(@RequestParam(required = false) MultipartFile file,
			@RequestParam(required = false) String role, @PathVariable Integer id,
			@RequestParam(required = false) String name, @RequestParam(required = false) String surname,
			@RequestParam(required = false) String fiscalCode, @RequestParam(required = false) String email) {
		LOGGER.debug("Class: AccreditamentoController - Method: putAccreditamento");
		AccreditamentoFileDTO fileDTO = new AccreditamentoFileDTO();
		fileDTO.setRuoloPersona(role);
		fileDTO.setNome(name);
		fileDTO.setCognome(surname);
		fileDTO.setCodiceFiscale(fiscalCode);
		fileDTO.setEmail(email);
		fileDTO.setFile(file);
		IdentifiableDTO identifiableDTO = this.accreditamentoService.updateAccreditamento(fileDTO, id);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		return risposta;
	}

	/**
	 * 
	 * @param id
	 * @param locale
	 * @return
	 */
	@PatchMapping("/conferences/{id}/accreditations/{idAcc}")
	@ApiOperation(value = "modify only flagAccreditation of accreditation")
	public RispostaIdentifiableDTO patchConferenza(@RequestBody AccreditamentoDTO accreditamentoDTO,
			@PathVariable Integer id, @PathVariable Integer idAcc) {
		LOGGER.debug("Class: AccreditamentoController - Method: patchAccreditamento");
		IdentifiableDTO identifiableDTO = this.accreditamentoService.patchAccreditamento(id, idAcc, accreditamentoDTO);
		RispostaIdentifiableDTO risposta = new RispostaIdentifiableDTO();
		risposta.setData(identifiableDTO);
		risposta.setMsg("Patch complete");
		risposta.setCode("200");
		return risposta;
	}

	@DeleteMapping("/accreditations/{id}")
	@ApiOperation(value = "delete accreditation")
	public RispostaNoData eliminaAccreditamento(@PathVariable Integer id) {
		LOGGER.debug("Class: AccreditamentoController - Method: deleteAccreditamento");
		String esito = this.accreditamentoService.deleteAccreditamento(id);
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(esito);
		return risposta;
	}

}
