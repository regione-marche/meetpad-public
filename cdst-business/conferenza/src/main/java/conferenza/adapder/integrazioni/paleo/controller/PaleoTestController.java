package conferenza.adapder.integrazioni.paleo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import conferenza.DTO.IdentifiableDTO;
import conferenza.adapder.integrazioni.createconference.DTO.IntegFrontieraDTO;
import conferenza.adapder.integrazioni.paleo.DTO.PaleoConferenzaFascicoliDTO;
import conferenza.adapder.integrazioni.paleo.DTO.bean.PaleoDocumentiFascicoloDTO;
import conferenza.adapder.integrazioni.paleo.adapter.PaleoSoapClientAdapter;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAllegatiAdapter;
import conferenza.adapder.integrazioni.paleo.service.PaleoAdapterService;
import conferenza.adapder.integrazioni.paleo.service.PaleoDomusExitProtocolService;
import conferenza.adapder.integrazioni.paleo.service.PaleoDomusInOutProtocolService;
import conferenza.adapder.integrazioni.paleo.service.PaleoExitProtocolService;
import conferenza.adapder.integrazioni.paleo.service.PaleoIncomingProtocolService;
import conferenza.adapder.integrazioni.paleo.service.PaleoToFrontieraDTOService;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.service.ProtocolloService;
import conferenza.repository.RegistroDocumentoRepository;
import conferenza.util.DbConst;
import it.marche.regione.paleo.services.BEBaseOfFileZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfSerieArchivisticaZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfTitolarioInfoZA0HwLp5;
import it.marche.regione.paleo.services.BEListOfrespDocProtocolliInfoZA0HwLp5;
import it.marche.regione.paleo.services.RespDocumento;



@RestController
@Lazy
@RequestMapping({"/paleotest"})
public class PaleoTestController {
	
	 private static final Logger logger = LoggerFactory.getLogger(PaleoTestController.class.getName());
	 
	@Deprecated
	@Autowired
	PaleoSoapClientAdapter paleoClientAdapter;
	  
	@Autowired
	PaleoAdapterService paleoAdapterService;
	  
	@Autowired
	PaleoToFrontieraDTOService paleoToFrontieraDTOService;
  
	@Autowired
	ProtocolloService observerService;
	
	@Autowired
	private ApplicationContext appContext;	
	
	@Autowired
	PaleoExitProtocolService paleoExitProtocolService;
	
	@Autowired
	@Qualifier("PaleoObserverDomusExitListnerInterface")
	PaleoDomusExitProtocolService paleoDomusExitProtocolService; 
	
	@Autowired
	@Qualifier("PaleoDomusObserverInOutListnerInterface")
	PaleoDomusInOutProtocolService paleoDomusInOutProtocolService; 
	
	
	@Autowired
	PaleoIncomingProtocolService paleoInputProtocolService;
	
	@Autowired
	RegistroDocumentoRepository registroDocumentoRepository;	
	
	  @RequestMapping(value={"getpratica/{pratica}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public it.marche.regione.paleo.services.RegistroInfo[] getpratica(
			  @PathVariable String pratica, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    it.marche.regione.paleo.services.RegistroInfo[] risultato=null;
	    try{
	    	
	    	risultato=paleoClientAdapter.doCall_GetRegistri(false);
	    	
	    	logger.debug("pratica : "+pratica);	    	
	    	response = "pratica : "+pratica;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }	
	  
	  //BEListOfTitolarioInfoZA0HwLp5 getTitolarioClassificazione() 
	  @RequestMapping(value={"titolario/{pratica}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public BEListOfTitolarioInfoZA0HwLp5 titolarioClassificazione(
			  @PathVariable String pratica, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    BEListOfTitolarioInfoZA0HwLp5 risultato=null;
	    try{
	    	
	    	risultato=paleoClientAdapter.getTitolarioClassificazione(false);
	    	
	    	logger.debug("pratica : "+pratica);	    	
	    	response = "pratica : "+pratica;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }	
	  
	  //BEListOfTitolarioInfoZA0HwLp5 getTitolarioClassificazione() 
	  @RequestMapping(value={"seriearchivistiche/{pratica}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public BEListOfSerieArchivisticaZA0HwLp5 serieArchivisticheFascicoli(
			  @PathVariable String pratica, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    BEListOfSerieArchivisticaZA0HwLp5 risultato=null;
	    try{
	    	
	    	risultato=paleoClientAdapter.getSerieArchivisticheFascicoli(false);
	    	
	    	logger.debug("pratica : "+pratica);	    	
	    	response = "pratica : "+pratica;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }		  
	  
	  /**
	   * http://localhost:8082/paleo/documentiinfascicolo/MTUwLjMwLjEzMC8yMDExL0lORi81NQ==
	   * lista di tipo
	   * {"codiceRegistro":"GRM","corrispondenti":"Rossi Mario","dataCreazione":"2019-01-24T13:00:29.647+0000","dataProtocollo":"2019-01-24T00:00:00.000+0000","docNumber":4605904,"numeroProtocollo":3130,"oggetto":"Oggetto Prova Protocollo tramite WS","privato":false,"pubblico":false,"segnaturaProtocollo":"0003130|24/01/2019|R_MARCHE|GRM|INF|A|150.30.130/2011/INF/55","statoProtocollo":{"value":"Protocollato"},"tipoProtocollo":{"value":"Ingresso"}}
	   * 
	   * @param codiceFascicolo
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"documentiinfascicolo/{codiceFascicolo}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public BEListOfrespDocProtocolliInfoZA0HwLp5 documentiProtocolliInFascicolo(
			  @PathVariable String codiceFascicolo, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    BEListOfrespDocProtocolliInfoZA0HwLp5 risultato=null;
	    try{
	    	byte[] codiceFascicoloDecoded=Base64.getDecoder().decode(codiceFascicolo);
	    	
	    	String str = new String(codiceFascicoloDecoded, StandardCharsets.UTF_8);
	    	risultato=paleoClientAdapter.getGetDocumentiProtocolliInFascicolo(str, false);
	    	
	    	logger.debug("codiceFascicoloDecoded : "+str);	    	
	    	response = "codiceFascicoloDecoded : "+str;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }		
	  
	  /**
	   * l'id è ottenuto dalla split di
	   * "segnaturaProtocollo":"0012850|09/10/2018|R_MARCHE|GRM|INF|A|150.30.130/2011/INF/55"
	   * come segnaturaProtocollo.split("|")[0]
	   * 
		{"messaggioRisultato":{"descrizione":null,"tipoRisultato":{"value":"Info"}},
		"oggetto":{"estensione":"txt","idFile":15493,"impronta":"92A323D4984108000B160B0B00A031288B5F5D7A","mimeType":"text/plain",
		"nome":"@1bh01A.TIF",
		"stream":"UXVlc3RvIOggdW4gZG9jdW1lbnRvIGZpdHRpemlvIHBlciBpbCBzaXRvIGRpIFRFU1QgZGkgUGFsZW8gKFByb3RvY29sbG8gSW5mb3JtYXRpY28gUmVnaW9uZSBNYXJjaGUpLg0KDQo="}}
	   * @param idFilePaleo
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"getfile/{idFilePaleo}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public BEBaseOfFileZA0HwLp5 getFile(
			  @PathVariable Integer idFilePaleo, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    BEBaseOfFileZA0HwLp5 risultato=null;
	    try{
	    		    

	    	risultato=paleoClientAdapter.getFile(idFilePaleo, false);
	    	
	    	logger.debug("idFilePaleo : "+idFilePaleo);	    	
	    	response = "idFilePaleo : "+idFilePaleo;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }		  
	  
	  @RequestMapping(value={"getfileresource/{idConference}/{idFilePaleo}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Resource getFileResource(
			  @PathVariable Integer idConference,
			  @PathVariable Integer idFilePaleo, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    Resource risultato=null;
	    try{
	    		   	    	
	    	risultato= paleoAdapterService.getFileStreamAsResource( idFilePaleo, idConference, false); 
	    	
	    	logger.debug("idFilePaleo : "+idFilePaleo);	    	
	    	response = "idFilePaleo : "+idFilePaleo;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }	  
	  
 	  //managePaleoProtocolliInFascicolo(String codiceFascicolo,Integer idConference)
	  /*
	  @RequestMapping(value={"getfascicolo/{idConference}/{codiceFascicolo}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Resource managePaleoProtocolliInFascicolo(
			  @PathVariable Integer idConference,
			  @PathVariable String codiceFascicolo, 
			  HttpServletRequest request)
	  {
	    Resource risultato=null;
	    try{
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
	    	byte[] codiceFascicoloDecoded=Base64.getDecoder().decode(codiceFascicolo);	    	
	    	String strCodiceFascicoloDecoded = new String(codiceFascicoloDecoded, StandardCharsets.UTF_8);
	    	//il codice fascicolo è decodificato--in futuro passa per la tabella di frontiera..
	    	paleoAdapterService.managePaleoProtocolliInFascicolo(strCodiceFascicoloDecoded, idConference);
	    	
	    	logger.debug("idConference : "+idConference);	    	
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }	
	  */
	  
	  /**
	   * http://localhost:8082/paleo/getfrontieradto/MTUwLjMwLjEzMC8yMDExL0lORi81NQ==
	   * @param codiceFascicolo
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"getfrontieradto/{codiceFascicolo}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public IntegFrontieraDTO getfrontieradto(
			  @PathVariable String codiceFascicolo, 
			  HttpServletRequest request)
	  {
		  IntegFrontieraDTO risultato=null;
	    try{
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
	    	byte[] codiceFascicoloDecoded=Base64.getDecoder().decode(codiceFascicolo);	    	
	    	String strCodiceFascicoloDecoded = new String(codiceFascicoloDecoded, StandardCharsets.UTF_8);
	    	//il codice fascicolo è decodificato--in futuro passa per la tabella di frontiera..
	    	risultato=paleoToFrontieraDTOService.getFrontieraDTOFromIdFascicolo(strCodiceFascicoloDecoded,"4");	    	
	    	logger.debug("codiceFascicolo : "+codiceFascicolo);	    	
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }	

	  /**
	   * http://localhost:8082/paleo/createconference/MTUwLjMwLjEzMC8yMDExL0lORi81NQ==/3
	   * @param codiceFascicolo
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"createconference/{codiceFascicolo}/{tipoconferenza}/{codiceDocumento}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Integer createconference(
			  @PathVariable String codiceFascicolo, 
			  @PathVariable String tipoconferenza, 
			  @PathVariable String codiceDocumento,
			  HttpServletRequest request){
		//-------------------------------------------  
		Integer risultato=null;
	    try{
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
	    	byte[] codiceFascicoloDecoded=Base64.getDecoder().decode(codiceFascicolo);	    	
	    	String strCodiceFascicoloDecoded = new String(codiceFascicoloDecoded, StandardCharsets.UTF_8);
	    	
	    	byte[] bcodiceDocumentodecoded=Base64.getDecoder().decode(codiceDocumento);
	    	String codiceDocumentodecoded=new String(bcodiceDocumentodecoded, StandardCharsets.UTF_8);
	    	
	    	//il codice fascicolo è decodificato--in futuro passa per la tabella di frontiera..
	    	PaleoConferenzaFascicoliDTO paleoConferenzaFascicoloDTO=new PaleoConferenzaFascicoliDTO();
	    	paleoConferenzaFascicoloDTO.setCodiceFascicolo(strCodiceFascicoloDecoded);
	    	paleoConferenzaFascicoloDTO.setTipoConferenza(tipoconferenza);
	    	
	    	
	    	List<PaleoDocumentiFascicoloDTO> listaFascicoliPaleo =new ArrayList<PaleoDocumentiFascicoloDTO>();
	    	PaleoDocumentiFascicoloDTO fascicolo =new PaleoDocumentiFascicoloDTO();
	    
			fascicolo.setCodiceDocumento(codiceDocumentodecoded);
	    	fascicolo.setDescrizioneDocumento("TEST-"+codiceDocumentodecoded);
	    	
			paleoConferenzaFascicoloDTO.setListaFascicoliPaleo(listaFascicoliPaleo );
	    	
	    	IdentifiableDTO iDTO=paleoAdapterService.doActionCreateConference(paleoConferenzaFascicoloDTO);	    	
	    	risultato=iDTO.getId();
	    	logger.debug("codiceFascicolo : "+codiceFascicolo);
	    	logger.debug("tipoconferenza  : "+tipoconferenza);
	    	logger.debug("id conferenza   : "+risultato);
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }	
	  
	  @RequestMapping(value={"dojson/{codiceFascicolo}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public PaleoConferenzaFascicoliDTO doJson(
			  @PathVariable String codiceFascicolo, 
			  HttpServletRequest request){
		//-------------------------------------------  
		  PaleoConferenzaFascicoliDTO risultato=new PaleoConferenzaFascicoliDTO();
	    try{
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
	    	byte[] codiceFascicoloDecoded=Base64.getDecoder().decode(codiceFascicolo);	    	
	    	String strCodiceFascicoloDecoded = new String(codiceFascicoloDecoded, StandardCharsets.UTF_8);
	    	//il codice fascicolo è decodificato--in futuro passa per la tabella di frontiera..
	    	risultato.setCodiceFascicolo(strCodiceFascicoloDecoded);
	    	risultato.setTipoConferenza(new String("4"));
	    	risultato.setDescrizioneFascicolo("PROVIAMOLO");	    		    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }	
	  
	  
	  @RequestMapping(value={"asincronous"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Integer doprotocollazione( 
			  HttpServletRequest request){
		//-------------------------------------------  
		Integer risultato=null;
	    try{
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
			Map<String,String> alreadyProcessed = new HashMap<String,String>();
			List<Map<String,String>> taskAsincroni= observerService.getRegisterdAsincronousTask();
	        for(Map<String,String> map : taskAsincroni) {
	        	String item = map.get("class");
	        	
	        	if(alreadyProcessed.containsKey(item))
	        		continue;
	        	alreadyProcessed.put(item, "");
	        	
		        //DLG:TO VERIFY
		        String methodName = "doAsincronousTask"; // the method to be called
		        Object bean = appContext.getBean(item);//defined by the @Component params
		        Method method;
				try {
					logger.debug("[scheduleTaskWithFixedRate] : "+item+"." + methodName);
					method = bean.getClass().getMethod(methodName);
			        if (method != null) {
			            method.invoke(bean);//java reflection ..
			        }  			
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
	        }	    		    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return risultato;
	  }		  
	  
	  /**

http://localhost:8082/paleotest/gepaleodoc/MTUwLjMwLjEzMC8yMDExL0lORi81NQ==/MDAwMjEzMnwxOS8xMi8yMDEzfFJfTUFSQ0hFfEdSTXxJTkZ8QXwxNTAuMzAuMTMwLzIwMTEvSU5GLzU1
	   * @param fascicolo
	   * @param docpaleo
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"gepaleodoc/{fascicolo}/{tipoconferenza}/{docpaleo}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public List<PaleoRegistryAllegatiAdapter> gepaleodoc(
			  @PathVariable String fascicolo,
			  @PathVariable String tipoconferenza,
			  @PathVariable String docpaleo,
			  HttpServletRequest request)
	  {
	    String response = null;
	    PaleoRegistryAdapter risultato =null;
	    List<PaleoRegistryAllegatiAdapter> allegati=null;
	    
		boolean isUSR = false; try { isUSR = tipoconferenza.equals(""+DbConst.TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE_DOMUS); } catch (Exception takedefault) { }
	    
	    try{
	    	
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
	    	byte[] codiceFascicoloDecoded=Base64.getDecoder().decode(fascicolo);	    	
	    	String strCodiceFascicoloDecoded = new String(codiceFascicoloDecoded, StandardCharsets.UTF_8);
	    	
	    	//Il codice fascicolo arriva in base 64 al controller!!!!!!!	    		   	    	
	    	byte[] docpaleoDecoded=Base64.getDecoder().decode(docpaleo);	    	
	    	String strdocpaleoDecoded = new String(docpaleoDecoded, StandardCharsets.UTF_8);

	    	//
	    	risultato=paleoAdapterService.cercaDocumentoPaleo(strdocpaleoDecoded, strCodiceFascicoloDecoded,tipoconferenza);
	    	Integer idFC=paleoAdapterService.getIdFascioloTipoConferenza(strCodiceFascicoloDecoded,tipoconferenza);
	    	risultato=paleoAdapterService.storeDocumentoPaleo(risultato,idFC);
	    	allegati=paleoAdapterService.manageAllegati(risultato, isUSR);
	    	
	    	//
	    	logger.debug("pratica : "+fascicolo);
	    	logger.debug("docpaleo : "+docpaleo);
	    	
	    	response = "docpaleo : "+docpaleo;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return allegati;
	  }		  
	  
	  
	  /**
	   * http://localhost:8082/paleotest/doprotocolloexit
	   * per effettuare la protocollazione senza aspettare l'esecuzione del batch
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"doprotocolloexit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Integer doprotocolloExit( 
			  HttpServletRequest request){		  
		  paleoExitProtocolService.doAsincronousTask();
		  return 0;
	  }
	  
	  /**
	   * http://localhost:8082/paleotest/doprotocolloexit
	   * per effettuare la protocollazione senza aspettare l'esecuzione del batch
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"doprotocolloinput"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Integer doprotocolloInput( 
			  HttpServletRequest request){		  
		  paleoInputProtocolService.doAsincronousTask();
		  return 0;
	  }	  
	  
	  /**
	   * http://localhost:8082/paleotest/doprotocolloexit
	   * per effettuare la protocollazione senza aspettare l'esecuzione del batch
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"doprotocolloDomusExit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Integer doprotocolloDomusExit( 
			  HttpServletRequest request){		
		  
		  paleoDomusExitProtocolService.doAsincronousTask();
		  return 0;
	  }	
	  
	  /**
	   * http://localhost:8082/paleotest/doprotocolloexit
	   * per effettuare la protocollazione senza aspettare l'esecuzione del batch
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"doprotocolloDomusInOut"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public Integer doprotocolloDomusInOut( 
			  HttpServletRequest request){		
		  
		  paleoDomusInOutProtocolService.doAsincronousTask();
		  return 0;
	  }	
	  
	  /**
	   * archiviaprotocolloInterno:
	   * dati i seguenti 4 parametri - archivia il documento come Documento Interno in Paleo..
	   * 
	   * @param idRegistro
	   * @param idPratica
	   * @param idFascicolo
	   * @param codiceProtocollo
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"archiviaprotocolloInterno"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public RespDocumento archiviaProtocolloInterno( 
			  @RequestParam Integer idRegistro,
			  @RequestParam String idFascicolo,
			  HttpServletRequest request){		
		 
		RespDocumento respDoc=null;  
		//====================================================
		Optional<RegistroDocumento> oregistroDocumento = registroDocumentoRepository.findById(idRegistro);
		RegistroDocumento registroDocumento =oregistroDocumento.get();
		//====================================================
		IntegProtocolloDTO integDTO = new IntegProtocolloDTO();
		//====================================================
		if(idFascicolo!=null &&!"".equals(idFascicolo))
			integDTO.setCodiceProtocollo(idFascicolo);
		    
		try {
			//respDoc=paleoAdapterService.doSubmitFileToIntraDocumentalService(registroDocumento, integDTO);
			// Aggiungo dei campi fittizi per la trasmissione. Capire se viene usato
			String cfResp = "";
			respDoc=paleoAdapterService.doSubmitFileToExitProtocolService(registroDocumento, integDTO, false, cfResp);
		} catch (ServiceException | IOException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  return respDoc;
	  }
}
