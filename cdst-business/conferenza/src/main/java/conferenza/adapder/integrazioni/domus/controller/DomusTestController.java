package conferenza.adapder.integrazioni.domus.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.datacontract.schemas._2004._07.DomusSismaServices_Tipi.Pratica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDocumentDTO;
import conferenza.adapder.integrazioni.domus.adapter.DomusClientAdapterService;
import conferenza.adapder.integrazioni.domus.service.DomusService;

@RestController
@Lazy
@RequestMapping({"/domustest"})
public class DomusTestController {
	
	private static final Logger logger = LoggerFactory.getLogger(DomusTestController.class.getName());
	
	@Autowired
	DomusClientAdapterService domusAdapterService;
	
	@Autowired
	DomusService domusService;
	
	  @RequestMapping(value={"test"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Pratica[]  getpratica( 
			  HttpServletRequest request){
		Pratica[] pratiche = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{
	    	
	    	//domusAdapterService.test();
	    	String comune="043007";
	    	pratiche = domusService.getPraticheMIS(comune);	    		    	
	    	logger.debug("comune : "+comune);	    	
	    	
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return pratiche;
	  }	

	  /**
 			Pratica[]  getPraticheDomus(String comune)
 			 
	   */
	  @RequestMapping(value={"pratiche/{comune}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Pratica[] getPraticheDomus( 
			  @PathVariable("comune") String comune,
			  HttpServletRequest request){
		  Pratica[] response = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{	    	
	    	//domusAdapterService.test();	    	
	    	response=domusService.getPraticheMIS(comune);	    		    	
	    	logger.debug("test : ");	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }		  
	  
	  /**
	   * Crea conferenze di tipo 3
	   * @param comune
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"createconference"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String createconference( 			  
			  HttpServletRequest request)
	  {
	    String response = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{
	    	
	    	//domusAdapterService.test();
	    	String comune="043007";
	    	domusService.doActionCreateDomusConferences(comune,"3",null); 
	    	
	    	logger.debug("doActionCreateDomusConferences ");	    	
	    	response = "doActionCreateDomusConferences";
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }	
	  
	  
	   /**
		public Resource getFileStreamAsResourceByIdRegistro(Integer idRegistro) 
	    * @param response
	    * @param id
	    * @throws IOException
	    */
		@Lazy
		@RequestMapping(value = "/registro/{id}", method = RequestMethod.GET)
		public void getFileStreamAsResourceByIdRegistro(HttpServletResponse response, @PathVariable("id") Integer id) throws IOException {
			try {
				Resource resource= domusService.getFileStreamAsResourceByIdRegistro(id) ;
			} catch (ServiceException | SOAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
				
		@Lazy
		@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
		public void download(HttpServletResponse response, @PathVariable("id") String id) throws IOException {
			try {
				
				
				
				domusService.downloadDocumentById(response,id);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		
	  
}
