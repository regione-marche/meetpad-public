package conferenza.adapder.integrazioni.domus.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.RispostaIdentifiableDTO;
import conferenza.adapder.integrazioni.domus.adapter.DomusClientAdapterService;
import conferenza.adapder.integrazioni.domus.service.DomusListnerService;
import conferenza.adapder.integrazioni.domus.service.DomusService;
import conferenza.util.DbConst;

@RestController
@Lazy
@RequestMapping({"/domus"})
public class DomusController {

	private static final Logger logger = LoggerFactory.getLogger(DomusTestController.class.getName());
	
	@Autowired
	DomusClientAdapterService domusAdapterService;
	
	@Autowired
	DomusService domusService;
	
	@Autowired
	DomusListnerService domusServiceTask;
	
	@PostMapping("/createconference/{comune}")
	public RispostaIdentifiableDTO createconferenceByIdPratica(
			@PathVariable String comune, HttpServletRequest request) throws Exception {
		comune = comune.replace("-", "#");
		logger.debug("Class: DomusController - Method: postConferenza: " + comune);
		RispostaIdentifiableDTO risposta = null;
		try {
	    	domusService.doActionCreateDomusConferences(comune,String.valueOf(DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS),null); 	    	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw e;
		}
		
		return risposta;
	  }	
	
		
	  /**
	   * 
	   * @param comune
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"createconference/{comune}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String createconference( 
			  @PathVariable String comune,
			  HttpServletRequest request)
	  {
	    String response = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{
	    	
	    	//domusAdapterService.test();
	    	//comune="043007";
	    	domusService.doActionCreateDomusConferences(comune,String.valueOf(DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS),null); 	    	
	    	logger.debug("doActionCreateDomusConferences ");	    	
	    	response = "doActionCreateDomusConferences";
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }	

	  @RequestMapping(value={"createconferenceall"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String createconferenceall( 
			  HttpServletRequest request){
	    String response = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{
	    	//---------------------------------------------
	    	domusService.doActionCreateConferenceAllComuni(String.valueOf(DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS),null); 	    	
	    	logger.debug("doActionCreateDomusConferences ");	    	
	    	response = "doActionCreateDomusConferences";	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }		  
	  
	  @RequestMapping(value={"createconferencetask"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String doTask( 
			  HttpServletRequest request){
	    String response = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{
	    	//---------------------------------------------
	    	domusServiceTask.doAsincronousTaskToCreateDomusConference(false); 	    	
	    	logger.debug("createconferencetask ");	    	
	    	response = "createconferencetask";	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }		  
	  
	  /**
	   * 
	   * @param comune
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"conferencebypratica/{comune}/{idpratica}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String createconferencepratica( 
			  @PathVariable String comune,
			  @PathVariable Integer idpratica,
			  HttpServletRequest request){
	    String response = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{
	    	
	    	//domusAdapterService.test();
	    	//comune="043007";
	    	domusService.doActionCreateDomusConferencesByComunepratica(comune,idpratica,String.valueOf(DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS),null); 	    	
	    	logger.debug("doActionCreateDomusConferences ");	    	
	    	response = "doActionCreateDomusConferences";
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }		  
}
