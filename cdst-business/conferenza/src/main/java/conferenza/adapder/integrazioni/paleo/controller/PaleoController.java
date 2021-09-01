package conferenza.adapder.integrazioni.paleo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.ConferenzaDTO;
import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoConferenzaFascicoliDTO;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoSoapClientAdapter;
import conferenza.adapder.integrazioni.paleo.service.PaleoAdapterService;
import conferenza.adapder.integrazioni.paleo.service.PaleoToFrontieraDTOService;
import io.swagger.annotations.ApiOperation;
import it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5;
import it.marche.regione.paleo.services.MessaggioRisultato;


@RestController
@Lazy
@RequestMapping({"/paleo"})
public class PaleoController {
	
	 private static final Logger logger = LoggerFactory.getLogger(PaleoController.class.getName());
	 
	  @Deprecated
	  @Autowired
	  PaleoSoapClientAdapter paleoClientAdapter;
	  
	  @Autowired
	  PaleoAdapterService paleoAdapterService;
	  
	  @Autowired
	  PaleoToFrontieraDTOService paleoToFrontieraDTOService;


		/**
		 * 
		 * @param conference
		 * @param locale
		 * @return
		 * @throws Exception 
		 */
		@PostMapping("/createconference")
		public RispostaIdentifiableDTO creaConferenza(
				@RequestBody PaleoConferenzaFascicoliDTO paleoConferenzaFascicoliDTO 
        ) throws Exception {
			logger.debug("@paleo: PaleoController - creaConferenza: " + paleoConferenzaFascicoliDTO.getCodiceFascicolo() + " + " + paleoConferenzaFascicoliDTO.getTipoConferenza());
			RispostaIdentifiableDTO risposta = null;
			try {
				IdentifiableDTO identifiableDTO = paleoAdapterService.doActionCreateConference(paleoConferenzaFascicoliDTO);
				logger.debug("@paleo: PaleoController - creaConferenza result: " + identifiableDTO.getId());
				risposta = new RispostaIdentifiableDTO();
				risposta.setData(identifiableDTO);
			} catch (IOException | ServiceException | SOAPException e) {
				logger.debug("@paleo PaleoController - creaConferenza exception: " + e.getMessage() + " - " + Arrays.toString(e.getStackTrace()));
				throw e;
			}
			
			return risposta;
		}	  
	  
	  
	  @RequestMapping(value={"createconference/{codiceFascicolo}/{tipoconferenza}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public Integer createconference(
			  @PathVariable String codiceFascicolo, 
			  @PathVariable String tipoconferenza, 
			  HttpServletRequest request){
		//-------------------------------------------  
		Integer risultato=null;
	    try{
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
	    	byte[] codiceFascicoloDecoded=Base64.getDecoder().decode(codiceFascicolo);	    	
	    	String strCodiceFascicoloDecoded = new String(codiceFascicoloDecoded, StandardCharsets.UTF_8);
	    	//il codice fascicolo è decodificato--in futuro passa per la tabella di frontiera..
	    	PaleoConferenzaFascicoliDTO paleoConferenzaFascicoloDTO=new PaleoConferenzaFascicoliDTO();
	    	paleoConferenzaFascicoloDTO.setCodiceFascicolo(strCodiceFascicoloDecoded);
	    	paleoConferenzaFascicoloDTO.setTipoConferenza(tipoconferenza);
	    	IdentifiableDTO iDTO=paleoAdapterService.doActionCreateConference(paleoConferenzaFascicoloDTO);	    	
	    	risultato=iDTO.getId();
	    	logger.debug("codiceFascicolo : "+codiceFascicolo);	    		    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }		  
	  
}
