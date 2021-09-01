package conferenza.protocollo.adapter;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import conferenza.protocollo.DTO.IntegProtocolloDTO;

@Service
public class IntegRestClientAdapter {

	private static final Logger logger = LogManager.getLogger(IntegRestClientAdapter.class.getName());
	
	 public ResponseEntity<String> uploadSingleFileSincronous(String serverUrl, Resource fileToSubmit, IntegProtocolloDTO protocolloDTO, String access_token)
			    throws IOException
			  {
			    ResponseEntity<String> response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			    if ((access_token != null) && (!"".equals(access_token))) {
			      headers.add("Authorization", "Bearer " + access_token);
			    }
			    MultiValueMap<String, Object> body = new LinkedMultiValueMap();
			    body.add("file", fileToSubmit);
			    body.add("protocolloDTO", protocolloDTO);
			    
			    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(body, headers);
			    
			    RestTemplate restTemplate = new RestTemplate();
			    response = restTemplate.postForEntity(serverUrl, requestEntity, String.class, new Object[0]);
			    return response;
			  }
    
	 
	  public ResponseEntity<String> uploadSingleFileASincronous(String serverUrl, Resource fileToSubmit, IntegProtocolloDTO protocolloDTO)
			    throws IOException
			  {
			    ResponseEntity<String> response = null;
			    HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			    
			    MultiValueMap<String, Object> body = new LinkedMultiValueMap();
			    body.add("file", fileToSubmit);
			    body.add("protocolloDTO", protocolloDTO);
			    
			    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(body, headers);
			    
			    RestTemplate restTemplate = new RestTemplate();
			    response = restTemplate.postForEntity(serverUrl, requestEntity, String.class, new Object[0]);
			    return response;
			  }
	  
    public static Resource getResourceFromPath(String path) {
    	Resource file=new ClassPathResource(path);
    	return file;
    }
    
    public static Resource getResourceFromFile(File fileIn) {
    	Resource file=new FileSystemResource(fileIn);
    	return file;
    }
    
}
