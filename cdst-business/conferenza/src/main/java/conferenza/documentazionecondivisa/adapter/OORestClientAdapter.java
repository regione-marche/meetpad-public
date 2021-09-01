package conferenza.documentazionecondivisa.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import conferenza.documentazionecondivisa.OOConfiguration;
import conferenza.documentazionecondivisa.DTO.OOCreateUserResponse;
import conferenza.documentazionecondivisa.DTO.OOLoginDTO;
import conferenza.documentazionecondivisa.DTO.OOLoginResponseDTO;
import conferenza.documentazionecondivisa.DTO.OOUploadFileReesponseDTO;
import conferenza.documentazionecondivisa.DTO.OOUserDTO;
import conferenza.documentazionecondivisa.DTO.bean.OOFileResponseODT;
import conferenza.documentazionecondivisa.DTO.bean.OOUserListResponse;




@Service
public class OORestClientAdapter {

	private static final Logger logger = LogManager.getLogger(OORestClientAdapter.class.getName());
	@Autowired
	OOConfiguration configuration;

	private String URL_SET_PAASSWORD="/people/IDUSEROO/password.json";
	private String URL_USER_LIST="/people/filter.json";
	
	 public OOLoginResponseDTO ooUserGetToken(String serverUrl,  OOLoginDTO login)
			    throws IOException
			  {
		 
		 
		 		ObjectMapper mapper = new ObjectMapper();
		 		String jsonInString = mapper.writeValueAsString(login);
		 		logger.debug("jsonInString " +jsonInString);
		 		
			    ResponseEntity<String> response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Accept", "application/json");
			    headers.add("Content-Type", "application/json; charset=UTF-8");
			    headers.add("DNT", "1");
			    			    			    
			    HttpEntity<String> entity = new HttpEntity<>(jsonInString, headers);
			    
			    RestTemplate restTemplate = new RestTemplate();
			    response = restTemplate.postForEntity(serverUrl, entity, String.class, new Object[0]);
			    
			    OOLoginResponseDTO sLoginREsponse=null;
			    try {
					//json = (JSONObject) parser.parse(response.getBody());					
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					String sr=response.getBody();
					sLoginREsponse =mapper.readValue (sr, OOLoginResponseDTO.class);
			    }catch (Exception e) {

				}	
			      
			    return sLoginREsponse;
	 }
	 
	 public OOCreateUserResponse ooCreateUser(String serverUrl,  OOUserDTO user,String onlyOfficeAccessTockem)
			    throws IOException
			  {
		 
		 
		 		ObjectMapper mapper = new ObjectMapper();
		 		String jsonInString = mapper.writeValueAsString(user);
		 		logger.debug("jsonInString " +jsonInString);
		 		
			    ResponseEntity<String> response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Accept", "application/json");
			    headers.add("Content-Type", "application/json; charset=UTF-8");
			    headers.add("DNT", "1");
			    headers.add("Authorization", onlyOfficeAccessTockem);
			    
			    HttpEntity<String> entity = new HttpEntity<>(jsonInString, headers);
			    
			    RestTemplate restTemplate = new RestTemplate();
			    response = restTemplate.postForEntity(serverUrl, entity, String.class, new Object[0]);
			    
			    OOCreateUserResponse sLoginREsponse=null;
			    try {
					//json = (JSONObject) parser.parse(response.getBody());					
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					String sr=response.getBody();
					sLoginREsponse =mapper.readValue (sr, OOCreateUserResponse.class);
			    }catch (Exception e) {

				}	
			      
			    return sLoginREsponse;
	 }	 
	 

	 public ResponseEntity<byte[]>  ooGetFile(String serverUrl,String onlyOfficeAccessTockem)
			    throws IOException{
		 				 	
		 		logger.debug("URL: "+serverUrl);
		 		logger.debug("onlyOfficeAccessTockem: "+onlyOfficeAccessTockem);
		 		
		 		ResponseEntity<byte[]>  response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Accept", "application/json");
			    headers.add("Content-Type", "application/json; charset=UTF-8");
			    headers.add("DNT", "1");
			    headers.add("Access-Control-Allow-Origin", "*");
			    
			    
			    headers.add("Authorization", onlyOfficeAccessTockem);
			    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			    
			    RestTemplate restTemplate = new RestTemplate();
			    response = restTemplate.exchange(serverUrl, HttpMethod.GET,entity, byte[].class);			    
			    					      
			    return response;
	 }	 	 
	 
	 /**
	  * 
	  * @param serverUrl
	  * @param fileToSubmit
	  * @param onlyOfficeAccessTockem
	  * @return
	 * @throws Exception 
	  */
	  public OOUploadFileReesponseDTO uploadSingleFileASincronous(String serverUrl, Resource fileToSubmit,String onlyOfficeAccessTockem)
			    throws Exception
			  {
		  
		  		logger.debug("uploadSingleFileASincronous!");	
			    
		  		ResponseEntity<String> response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Authorization", onlyOfficeAccessTockem);
			    
			    String filename=null;
			    try {
			    	filename=fileToSubmit.getFilename();
			    }catch (Exception e) {
			    	filename="test.docx";
			    }
			    
			    logger.debug("filename: "+filename);			    			   
			    
			    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			    try {								
			    	headers.add("Content-Disposition","form-data; name=\""+fileToSubmit.getFile().getName()+"\"; filename=\""+URLEncoder.encode(filename, "UTF-8")+"\"");
			    }catch (Exception e) {
			    	headers.add("Content-Disposition","form-data; name=\"test\"; filename=\"test\"");
				}			    			    

			    
			    HttpEntity<Resource> entity = new HttpEntity<>(fileToSubmit, headers);
			    			    
			    RestTemplate restTemplate = new RestTemplate();
			    logger.debug("serverUrl: "+serverUrl);
			    logger.debug("entity: "+entity.toString());
			    response = restTemplate.exchange(serverUrl, HttpMethod.POST, entity,String.class);
			    logger.debug("restTemplate.exchange eseguito");
			    
			    
			    ObjectMapper mapper = new ObjectMapper();
			    OOUploadFileReesponseDTO sFileREsponse=null;	
			    try {									
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					String sr=response.getBody();
					sFileREsponse =mapper.readValue (sr, OOUploadFileReesponseDTO.class);
			    }catch (Exception e) {
			    	 logger.debug("Errore inizializzazione OOUploadFileReesponseDTO: " + e.getMessage());
			    	e.printStackTrace();	
				}			    			    
			    return sFileREsponse;
	   }
	 
	  
	  
	public String getURL_SETUSERPASSWORD(String IDUSEROO){
		return configuration.getUrlRoot() +URL_SET_PAASSWORD.replaceAll("IDUSEROO", IDUSEROO);		
	}
	
	 public OOCreateUserResponse ooSetUserPassword(String IDUSEROO,OOLoginDTO user,String onlyOfficeAccessTockem)
			    throws IOException
			  {
		 		 
		 		String serverUrl = getURL_SETUSERPASSWORD(IDUSEROO);
		 		logger.debug("serverUrl " +serverUrl);
		 		
		 		ObjectMapper mapper = new ObjectMapper();
		 		String jsonInString = mapper.writeValueAsString(user);
		 		logger.debug("jsonInString " +jsonInString);
		 		
			    ResponseEntity<String> response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Accept", "application/json");
			    headers.add("Content-Type", "application/json; charset=UTF-8");
			    headers.add("DNT", "1");
			    headers.add("Authorization", onlyOfficeAccessTockem);
			    
			    HttpEntity<String> entity = new HttpEntity<>(jsonInString, headers);
			    
			    RestTemplate restTemplate = new RestTemplate();
			    
			    //PUT!!!!
			    response = restTemplate.exchange(serverUrl, HttpMethod.PUT, entity,String.class);
			    
			    OOCreateUserResponse sLoginREsponse=null;
			    try {
					//json = (JSONObject) parser.parse(response.getBody());					
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					String sr=response.getBody();
					sLoginREsponse =mapper.readValue (sr, OOCreateUserResponse.class);
			    }catch (Exception e) {
			    	e.printStackTrace();
				}	
			      
			    return sLoginREsponse;
	 }		
		  
	 private String getUserListURL() {
		 
		 return configuration.getUrlRoot()+this.URL_USER_LIST;
	 }
	 
	 public OOUserListResponse ooGetUserList(String onlyOfficeAccessTockem)
			    throws IOException
			  {
		 
		 		String serverUrl=getUserListURL() ;
		 
		 		ObjectMapper mapper = new ObjectMapper();
		 		
			    ResponseEntity<String> response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.add("Accept", "application/json");
			    headers.add("Content-Type", "application/json; charset=UTF-8");
			    headers.add("DNT", "1");
			    headers.add("Authorization", onlyOfficeAccessTockem);
			    
			    
			    RestTemplate restTemplate = new RestTemplate();
			    response = restTemplate.getForEntity(serverUrl, String.class);			    
			    
			    OOUserListResponse sUserListResponse=null;
			    try {
					//json = (JSONObject) parser.parse(response.getBody());					
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					String sr=response.getBody();
					sUserListResponse =mapper.readValue (sr, OOUserListResponse.class);
			    }catch (Exception e) {

				}	
			      
			    return sUserListResponse;
	 }	 
	 
	 
	 
	 
	 
    /**
	     * TODO Get File 
	     * @return
	     * @throws IOException
	     */
	 public static Resource getDocumentFile() throws IOException {
	        //Path testFile = Files.createTempFile("test-file", ".txt");
		    //System.out.println("Creating and Uploading Test File: " + testFile);
		    //Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
	        //return new FileSystemResource(testFile.toFile());	        
	        File f=new File("C:/Users/guideluc/git/testonlyoffice/src/test1.docx");
	        return new FileSystemResource(f);
	 }
		
}
