package conferenza.adapder.integrazioni.indicepa.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.adapder.integrazioni.indicepa.DTO.IndicePaSearchDTO;
import conferenza.adapder.integrazioni.indicepa.DTO.IndicePaSelectDTO;
import conferenza.adapder.integrazioni.indicepa.DTO.RispostaIndicePaSearchDTO;
import conferenza.adapder.integrazioni.indicepa.DTO.RispostaIndicePaSelectDTO;
import conferenza.adapder.integrazioni.indicepa.service.IndicePaService;
import io.swagger.annotations.ApiOperation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@org.springframework.context.annotation.Lazy
@RequestMapping(value = "/integrazioni/indicepa")
public class IndicePaController {

	private static final Logger logger = LoggerFactory.getLogger(IndicePaController.class.getName());
	
	@Autowired
	IndicePaService ipaService;
	
	
	
	@PostMapping(value = "/find")
	@ApiOperation(value = "find indice pa by desc")
	public RispostaIndicePaSearchDTO finiindie(@RequestParam String descrizione)
			throws MessagingException {

		IndicePaSearchDTO ipaDTO=new IndicePaSearchDTO();
		RispostaIndicePaSearchDTO risposta = new RispostaIndicePaSearchDTO();
		try {
			
			String result=ipaService.sendPOSTFindInIndicePA(descrizione);
			if(result !=null){
				ObjectMapper mapper = new ObjectMapper();
				ipaDTO = mapper.readValue(result, new TypeReference<IndicePaSearchDTO>() {
				});
				
			}
						
			if(ipaDTO!=null && ipaDTO.getResult()!= null && ipaDTO.getResult().getNumItems()>0){					
				risposta.setData(ipaDTO);
				
			}else{
				logger.debug("No data Foud for: "+descrizione);
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
			risposta.setData(null);	
		}
				
		return risposta;
	}

		
	/**
	 * 
	 * @param amm
	 * @return
	 * @throws MessagingException
	 */
	@PostMapping(value = "/select")
	@ApiOperation(value = "find indice pa by desc")
	public RispostaIndicePaSelectDTO selectipa(@RequestParam String amm)
			throws MessagingException {

		IndicePaSelectDTO ipaDTO=new IndicePaSelectDTO();
		RispostaIndicePaSelectDTO risposta = new RispostaIndicePaSelectDTO();
		try {
			
			String result=ipaService.sendPOSTSelectAmmInIndicePA(amm);
			if(result !=null){
				ObjectMapper mapper = new ObjectMapper();
				ipaDTO = mapper.readValue(result, new TypeReference<IndicePaSelectDTO>() {
				});
				
			}
						
			if(ipaDTO!=null && ipaDTO.getResult()!= null && ipaDTO.getResult().getNumItems()>0){					
				risposta.setData(ipaDTO);
				
			}else{
				logger.debug("No data Foud for: "+amm);
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
			risposta.setData(null);	
		}
				
		return risposta;
	}	
}
