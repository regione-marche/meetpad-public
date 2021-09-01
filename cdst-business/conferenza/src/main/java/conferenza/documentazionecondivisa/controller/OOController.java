package conferenza.documentazionecondivisa.controller;



import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.commons.impl.json.parser.JSONParseException;
import org.apache.chemistry.opencmis.commons.impl.json.parser.JSONParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.util.FileUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;

import conferenza.DTO.bean.Errore;
import conferenza.DTO.bean.RispostaNoData;
import conferenza.documentazionecondivisa.DTO.OOCreateUserResponse;
import conferenza.documentazionecondivisa.DTO.OODocumentDTO;
import conferenza.documentazionecondivisa.DTO.OODocumentResponseDTO;
import conferenza.documentazionecondivisa.DTO.OOLoginDTO;
import conferenza.documentazionecondivisa.DTO.OOLoginResponseDTO;
import conferenza.documentazionecondivisa.DTO.OOUploadFileReesponseDTO;
import conferenza.documentazionecondivisa.DTO.bean.OOFileResponseODT;
import conferenza.documentazionecondivisa.model.OOAdapter;
import conferenza.documentazionecondivisa.service.OOService;
import conferenza.service.UtenteService;
import conferenza.util.JsonUtil;



@RestController
@Lazy
@RequestMapping({"/docucondivisa"})
public class OOController {

	 private static final Logger logger = LoggerFactory.getLogger(OOService.class.getName());
	
	  @Autowired
	  OOService ooService;

	  @Autowired
	  UtenteService utenteService;
	  
	  /*************************************************************************************************************
	   * http://localhost:8082/docucondivisa/ooeditdoc/3EBmuod1OYcnsUJA
	   * 
	   * 
	   ************************************************************************************************************* 
	   * @param token
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"ooeditdoc/{token}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public OODocumentResponseDTO ooeditdoc(
			  @PathVariable String token, 
			  HttpServletRequest request){
		
		logger.debug("token: "+token);	    
		OODocumentResponseDTO responseDTO=new OODocumentResponseDTO();
		OODocumentDTO jsnoOBJ=null;
	    try{
	    	//==================================================
	    	//JWT dell'utente chiamante
			//==================================================
	    	String jwt=ooService.getJWT(request);
	    	//==================================================
	    	//VERIFICA DEL LOCK DEL FILE
	    	//==================================================
	    	//TODO
	    	
	    	//==================================================
	    	//inizializzazione del documenit 
			//==================================================	    	
		    String newtoken=ooService.initializeEditOODocument(token,jwt);
	    	//==================================================
	    	//
			//==================================================
		    jsnoOBJ=ooService.getOOJsonDocument(newtoken,jwt);
		    logger.debug("OOController - riga 130 - jsnoOBJ: " + jsnoOBJ);
		    responseDTO.setMsg("ok");
		    responseDTO.setData(jsnoOBJ);
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	      logger.error(e.getMessage());
	      responseDTO.setCode("403");
	      responseDTO.setMsg("Risorsa Non Disponibile");
	      List<Errore> errors=new ArrayList<Errore> ();
	      Errore err=new Errore("403", e.getLocalizedMessage());
	      errors.add(err);
	      responseDTO.setErrors(errors);
	    }
	    return responseDTO;
	  }	
	  
	  /*
	  * 
	  */
	  @RequestMapping(value={"oogeturl/{token}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String oogeturl(
			  @PathVariable String token, 
			  HttpServletRequest request){
		   //TODO: fare il metofo che generi l'url del file a partire dall'id doc {idcoc}	
		   String lsUrl=ooService.getOODocumentUrl(token);		 
		   return lsUrl;
	  }
	  
	  /**
	   * 
	   * @param idcoc
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"oogeteditor/{token}/{jwt}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public OODocumentDTO oogeteditor(
			  @PathVariable String token, 
			  @PathVariable String jwt, 
			  HttpServletRequest request)
	  {
		  
		  OODocumentDTO retjsonVal=ooService.getOOJsonDocument(token,jwt) ;		  
		  return retjsonVal;
		  
	  }	  
	  
	  
	  @RequestMapping(value={"ooinitialize/{token}/{jwt}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public RispostaNoData initializeEditOODocument(
			  @PathVariable String token, 
			  @PathVariable String jwt, 
			  HttpServletRequest request){

		  String textmessage = "inizializzato";
		  try {
			  ooService.initializeEditOODocument(token,jwt);
		  }catch (Exception e) {
			  textmessage = "NON inizializzato"; 
			  e.printStackTrace();
		  }	  
		RispostaNoData risposta = new RispostaNoData();
		risposta.setMsg(textmessage);
		return risposta;		  		  
	  }	  
	  
//======================================================
//TEST!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	  
//======================================================
	  /**
	   * http://localhost:8082/docucondivisa/ootest/3EBmuod1OYcnsUJA/eyJ4NXQiOiJNalF4TmpnM01EQTJNV1ZpWXpjM1ltWTNaak14TjJJeU9ERXpNamxrTURneE9HWmlOMll5TVEiLCJraWQiOiJNalF4TmpnM01EQTJNV1ZpWXpjM1ltWTNaak14TjJJeU9ERXpNamxrTURneE9HWmlOMll5TVEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJETENHRFU3MUQxNkM2MzJTIiwiYXVkIjoia1BNbHNyZmVTVlJXUlg1MnFjcU1NRDJfOXMwYSIsImF6cCI6ImtQTWxzcmZlU1ZSV1JYNTJxY3FNTUQyXzlzMGEiLCJzY29wZSI6Im9wZW5pZCIsImlzcyI6Imh0dHBzOlwvXC93c28yLm1lZXRwYWQtZGV2LmVuZzo5NDQ3XC9vYXV0aDJcL3Rva2VuIiwiZXhwIjoxNTUyNDcwMTY4LCJpYXQiOjE1NTI0NjY1NjgsImp0aSI6ImQ2ZmJkYWRjLTliMGItNGJkNS05MzNjLTVjMWVhZGFmZDBiMiJ9.AeL8k99A03NjrZmRFGr8CYp_fQtWGkGYI3XkxR5qTl7AmDXHe43TfkSw6leFPTEUmiCsuvv1bkaIfwP-vkDCLAHCBJ2oi_0gw-nuoPFRoZ-sXQhEnkDd3rPCK_l4NawdxJfWx-92xXnW304kuCOQKO3jSjjI0w7INdTfUCEl6ZXtqq-U7t6MAqGU8eOO0GH7-FM3yW3aQrwu2U_ykw8AzVXp6_T2ri5AzoNkI38EMBoV_Bl9tt9XhnP6vQSmjxdX7401LuH4gIWZ5Xj2OL-WlPiSspLEMvBP6VVXhORXJBlhmRz_jtaXKSnCqrZgxPCBRuIdurjbkmr4irJuMT0lNQ
	   * @param idcoc
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"ootest/{token}/{jwt}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String test(
			  @PathVariable String token, 
			  @PathVariable String jwt, 
			  HttpServletRequest request){
		  
		  String tokennew=null;
		  //==================================================
		  try {
			  tokennew=ooService.initializeEditOODocument(token,jwt);
		  }catch (Exception e) {
			  //TODO 
			  e.printStackTrace();
		  }
		  //==================================================		  
		  OODocumentDTO retjsonVal=ooService.getOOJsonDocument(tokennew,jwt) ;				  
		  String html="<head>\r\n" + 
		  		"<base href=\"/\" />\r\n" + 
		  		"<script type=\"text/javascript\" src=\""+ooService.getOODOAPIUrl()+"\"></script>\r\n" + 
		  		"</head>\r\n" + 
		  		"<br />\r\n" + 
		  		"<div id=\"placeholder\"></div>\r\n" + 
		  		//"<p>Test</p>\r\n" + 
		  		//"<button onclick=\"open_to_edit('nomedocumento', 'text')\">Edit nomedocumento</button>\r\n" + 
		  		"<script>\r\n" + 
		  		//"function open_to_edit(filename, documentType) {\r\n" + 
		  		"if (this.docEditor) {\r\n" + 
		  		"	this.docEditor.destroyEditor()\r\n" + 
		  		"}\r\n" + 
		  		"this.docEditor = new DocsAPI.DocEditor(\"placeholder\", \r\n" + 
		  		""+JsonUtil.jsonToString(retjsonVal)+"\r\n" + 
		  		");\r\n" + 
		  		//"}\r\n" + 
		  		"</script>";		  
		  return html;
		  
	  }	  	  

	  @RequestMapping(value={"ootest2/{doc}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String test(
			  @PathVariable String doc, 
			  HttpServletRequest request){
		  

		  byte[] bytedecodedjwt = Base64.getDecoder().decode(doc);
		  String jsnDoc = new String(bytedecodedjwt);		  
		  //==================================================		  			  
		  String html="<head>\r\n" + 
		  		"<base href=\"/\" />\r\n" + 
		  		"<script type=\"text/javascript\" src=\""+ooService.getOODOAPIUrl()+"\"></script>\r\n" + 
		  		"</head>\r\n" + 
		  		"<br />\r\n" + 
		  		"<div id=\"placeholder\"></div>\r\n" + 
		  		//"<p>Test</p>\r\n" + 
		  		//"<button onclick=\"open_to_edit('nomedocumento', 'text')\">Edit nomedocumento</button>\r\n" + 
		  		"<script>\r\n" + 
		  		//"function open_to_edit(filename, documentType) {\r\n" + 
		  		"if (this.docEditor) {\r\n" + 
		  		"	this.docEditor.destroyEditor()\r\n" + 
		  		"}\r\n" + 
		  		"this.docEditor = new DocsAPI.DocEditor(\"placeholder\", \r\n" + 
		  		""+jsnDoc+"\r\n" + 
		  		");\r\n" + 
		  		//"}\r\n" + 
		  		"</script>";		  
		  return html;
		  
	  }	  	  

	  
//*********************************************************************************	  
	  @RequestMapping(value={"oogetdocumentUrl/{token}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String getOODocumentUrl(
			  @PathVariable String token, 
			  HttpServletRequest request)
	  {
	    String response = ooService.getOODocumentUrl(token);
	    return response;
	  }	
	  
	  
	  @RequestMapping(value={"oogettoken/{codicefiscale}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String gettoken(
			  @PathVariable String codicefiscale, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    try{
	    	OOLoginResponseDTO responseDTO=ooService.doAdminGetToken();
	    	String ooAccessToken=responseDTO.getResponse().getToken();
	    	logger.debug("ooAccessToken : "+ooAccessToken);	    	
	    	response = ooAccessToken;
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }	
	  
	  
	  /**
	   * ritorna l'id dell'utente creato..
	   * @param codicefiscale
	   * @param request
	   * @return
	   */
	  @RequestMapping(value={"oocreateuser/{codicefiscale}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String oocreateuser(
			  @PathVariable String codicefiscale, 
			  HttpServletRequest request)
	  {
	    String response = null;
	    try{
	    	//OOCreateUserResponse createOOUserByRequest()
	    	OOCreateUserResponse responseDTO=ooService. createOOUserByRequest(codicefiscale);
	    	logger.debug("OOCreateUserResponse : "+responseDTO.getResponse().getId());	    	
	    	
	    	response = responseDTO.getResponse().getId();
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return response;
	  }		  
	  
	  
	  @RequestMapping(value={"oouploadfile/{iddocument}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public OOUploadFileReesponseDTO oouploadfile(
			  @PathVariable String iddocument, 
			  HttpServletRequest request){
		
		logger.debug("iddocument to upload: "+iddocument);	    
		OOUploadFileReesponseDTO responseDTO=null;
	    try{
	    	responseDTO=ooService.uploadTest();
	    	logger.debug("oouploadfile : OK ");	    		    		    
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return responseDTO;
	  }		  
	  
	  @RequestMapping(value={"oopublish/{idregistro}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public OOAdapter oopublish(
			  @PathVariable Integer idregistro, 
			  HttpServletRequest request){
		
		logger.debug("idregistro to publish: "+idregistro);	    
		OOAdapter responseDTO=null;
	    try{
	    	responseDTO=ooService.publishOODocument(idregistro);
	    	logger.debug("oopublish : OK ");	    		    		    
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return responseDTO;
	  }		  
	  

	  
	  
	  
	  
	  @RequestMapping(value={"ooedit/{idregistro}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public String ooedit(
			  @PathVariable Integer idregistro, 
			  HttpServletRequest request){
		
		logger.debug("idregistro to ooedit: "+idregistro);	    
		String responseDTO=null;
	    try{
	    	responseDTO=ooService.getEditOODocumentURL(idregistro);
	    	logger.debug("ooedit : OK ");	    		    		    
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return responseDTO;
	  }		  
	  
	  
	  
	  
	  @RequestMapping(value={"oouploadfile/{iddocument}/{token}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
	  public OOUploadFileReesponseDTO ootest(
			  @PathVariable String iddocument, 
			  @PathVariable String token, 
			  HttpServletRequest request){
		
		logger.debug("iddocument to upload: "+iddocument);	    
		OOUploadFileReesponseDTO responseDTO=null;
	    try{
	    	responseDTO=ooService.uploadTest();
	    	logger.debug("oouploadfile : OK ");	    		    		    
	    	
	    }catch (Exception e){
	      e.printStackTrace();
	    }
	    return responseDTO;
	  }			  
	  
	  /**
	   * http://localhost:8082/docucondivisa/calback
	   * 
0 - no document with the key identifier could be found,
1 - document is being edited,
2 - document is ready for saving,
3 - document saving error has occurred,
4 - document is closed with no changes,
6 - document is being edited, but the current document state is saved,
7 - error has occurred while force saving the document.

	   * @param request
	   * @param response
	   * @throws Exception
	   */
	  @RequestMapping(value={"callback/{idadapter}/{cf}/{jwt}"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  public void calback(
			  @PathVariable String idadapter, 
			  @PathVariable String cf, 
			  @PathVariable String jwt, 
			  HttpServletRequest request,
			  HttpServletResponse response
			  ) throws Exception{
		
		byte[] bytedecodedjwt = Base64.getDecoder().decode(jwt);
		String decodedjwt = new String(bytedecodedjwt);
		  
		logger.debug("calback ");	    
		PrintWriter writer = response.getWriter();
		
		Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
		String body = scanner.hasNext() ? scanner.next() : "";
		logger.debug("JSONObject - body "+body);	
		
		JSONObject jsonObj = (JSONObject) new JSONParser().parse(body);
        		
		java.math.BigInteger status=(BigInteger) jsonObj.get("status");
		if(status!=null)
			logger.debug("status :"+status.doubleValue());
		else
			logger.debug("is null");
		
		BigInteger COMPAREVAL=new java.math.BigInteger("2");
        if(status.compareTo(COMPAREVAL)==0){
            String downloadUri = (String) jsonObj.get("url");
            String key = (String) jsonObj.get("key");
            String pathForSave = key+"_"+ooService.getNomeDocumentByToken(key);
            URL url = new URL(downloadUri);
            //open connection
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();            
            //------------------------------------------------------
            //store saved file in Community Server
            InputStream stream = connection.getInputStream();
            File savedFile = new File(pathForSave);

            FileOutputStream out = null;
            try {
            	out = new FileOutputStream(savedFile);
            	int read;
                final byte[] bytes = new byte[1024];
                while ((read = stream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
            }catch (Exception e) {
            	e.printStackTrace();
			}            

            ooService.saveOODocumentVersione(key,idadapter,cf,decodedjwt,savedFile);
            
            /*
            OOUploadFileReesponseDTO responseDTO=null;
            Resource res=null;
            File tmpFile = File.createTempFile("file", "temp");
            FileUtils.copyInputStreamToFile(stream, tmpFile); // FileUtils from apache-io
            try {
            	res=new FileSystemResource(tmpFile);
            	responseDTO=ooService.uploadSingleFileASincronous(res);
            	Integer id=responseDTO.getResponse().getId();
            	
            }catch (Exception e) {
            	e.printStackTrace();
            } finally {
                tmpFile.delete();
            }                        
            */
            
            //------------------------------------------------------
            //close connection
            connection.disconnect();            
            //------------------------------------------------------
        }
        
	    try{
	    	logger.debug("oouploadfile : OK ");	    		    		    
	    		    }catch (Exception e){
	      e.printStackTrace();
	    }
	    
	    writer.write("{\"error\":0}");
	  }		
	  
	  /**
	 * @throws IOException 
	   * 
	   */
	  @RequestMapping(value = "geturl/**")
	    public String redirect(
	    		@RequestBody(required = false) String body,
	    		 HttpMethod method,
	    		final HttpServletResponse response,
	    		final HttpServletRequest request) throws IOException {
		  
		    String token = ooService.getAdminAuthorizationToken() ;
		    logger.debug("token " +token);
		    HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", token);
	        //headers.setContentType(MediaType.APPLICATION_JSON);
	        String address="http://192.168.157.130:8082/products/files/httphandlers/filehandler.ashx?action=download&fileid=51";
	        logger.debug("request.getPathInfo() " +request.getPathInfo());
	        String url=address;//+ request.getPathInfo();
	        RestTemplate restTemplate = new RestTemplate();	        
	        ResponseEntity<String> responseEntity=restTemplate.exchange( 
					url, 
					method, 
					new HttpEntity<String>(body, headers) , 
					String.class);
	        return responseEntity.getBody();
	 }	  
    //============================================================================
	//DOWNLOAD BY COMMUNITY SERVER  
	//============================================================================
	/**
	 * http://localhost:8082/docucondivisa/downloadoo/6kppeB8A3RAL7SLN
	 * 
	 * intercetta il file dal community server..quando sia stato caricato:
	 * 1 - dall amministratore
	 * 2 - nella sezione common del community server..
	 * 
	 * TODO:
	 * gestire l'accesso ristretto ai file:
	 * a - i file devono essere associati ad un utente 
	 * b - iu file devono essere scharati ad dei gruppi di utenti..   
	 *   
	 * @param filetoken
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/downloadoo/{filetoken}", method = RequestMethod.GET)	  
	public ResponseEntity<T>   downloadoo(
				  @PathVariable String filetoken, 
				  HttpServletRequest request							
    ) throws IOException{			
			String ootoken=null;
			Integer idOOFile=ooService.getIdOOFile(filetoken);
			String codiceFiscale=utenteService.getAuthenticatedUser().getCodiceFiscale();	
			//TODO: per il momento solo l'utente che carica un file..può scaricarlo..
			//occorre implementare i metofi di SHAREdel file ..
			//nel frattempo si fà tutto con l'amministratore..
			//lato back end!!!
			ootoken=ooService.getAdminAuthorizationToken();
			//ootoken=ooService.getUserToken(codiceFiscale) ;			
			return ooService.getOOCommunityFile(idOOFile,ootoken) ;	
    }
	/**
	 * http://localhost:8082/docucondivisa/download/aHR0cHM6Ly9vb2NvbXNlcnZlci10ZXN0LnJlZ2lvbmUubWFyY2hlLml0L3Byb2R1Y3RzL2ZpbGVzL2h0dHBoYW5kbGVycy9maWxlaGFuZGxlci5hc2h4P2FjdGlvbj1kb3dubG9hZCZmaWxlaWQ9Mw==  
	 * @param url
	 * @param reques
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/testdownload/{url}", method = RequestMethod.GET)	  
	public ResponseEntity<byte[]>   testdownload(
			  @PathVariable String url, 
			  HttpServletRequest request			
			) throws IOException {

		    //====================================================================================
	        String token = ooService.getAdminAuthorizationToken() ;	    
	        //====================================================================================		
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");
	        //====================================================================================
			byte[] bytedecodedjwt = Base64.getDecoder().decode(url);
			String decodedurl = new String(bytedecodedjwt);
			logger.debug("decodedurl " +decodedurl);
            //====================================================================================
	        String address="https://oocomserver-test.regione.marche.it/products/files/httphandlers/filehandler.ashx?action=download&fileid=12";	        
	        if(decodedurl!=null)
	        	address=decodedurl;
            //====================================================================================	        
	        ResponseEntity<byte[]> restresponse=ooService.ooGetFile(address,token); 
	        //====================================================================================
	        HttpHeaders responseheaders=restresponse.getHeaders();
	        List<String> contentdisposition =responseheaders.get("content-disposition");
	        String filename=null;
	        for(String item : contentdisposition) {
	        	//attachment; filename=tempVGiJ74Juc7sbGZWhSampleDLG.docx
	        	logger.debug("contentdisposition " +item);
	        	if(item!=null && !"".equals(item)) {
	        		filename=item.replace("attachment; filename=", "");
	        		filename=filename.replaceAll("\"", "");
	        		filename=filename.replaceAll(" ", "");
	        	}	
	        }	        
	        //====================================================================================
	        byte[] risposta=restresponse.getBody();
        	int read;
            File targetFile = new File(filename);
            OutputStream outStream = new FileOutputStream(targetFile);                    
            byte[] buffer=risposta;//.getBytes();
            int bytesRead=-1;
            try {                
            	int j=0;
                int c;
                for(byte i: buffer) {
                	c=i;               
                	outStream.write(c);
                }
             }finally {
                if (outStream != null) {                	
                	outStream.flush();
                	outStream.close();
                }
             }  

            //====================================================================================            
            //ByteArrayOutputStream bufferout = new ByteArrayOutputStream();
            //InputStream inStream = new FileInputStream(targetFile);
            //int nRead;
            //byte[] data = new byte[16384];
            //while ((nRead = inStream.read(data, 0, data.length)) != -1) {
            //	bufferout.write(data, 0, nRead);
            //}
            //====================================================================================
            //String ssb=sb.toString();
            //Resource resource= ooService.loadFileAsResourceFromFileSystem(targetFile);
            return  new ResponseEntity(buffer, responseheaders,HttpStatus.OK);
            //====================================================================================                        	        
            //ResponseEntity respEntity = null;            
            //respEntity = new ResponseEntity(restresponse.getBody(), responseheaders,HttpStatus.OK);
            //====================================================================================
            //ResponseEntity respEntity = null;            
            //respEntity = new ResponseEntity(copy, responseheaders,HttpStatus.OK);
          //====================================================================================
            
            //return restresponse;	       
    }	  
	

    public Resource getResource(File file) {
        return new FileSystemResource(file);
    }
}
