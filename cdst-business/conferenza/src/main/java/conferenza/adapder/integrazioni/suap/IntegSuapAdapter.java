package conferenza.adapder.integrazioni.suap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.model.RegistroDocumento;
import conferenza.protocollo.DTO.IntegProtocolloDTO;
import conferenza.protocollo.adapter.IntegRestClientAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Lazy
@Service
public class IntegSuapAdapter {
	
	
	
	private static final Logger logger = LogManager.getLogger(IntegSuapAdapter.class.getName());
	

	
	@Autowired
	IntegRestClientAdapter restClientAdapter;
	
	@Autowired
	IntegSuapFrontieraConfigurator integConfigurator;
	
	@Autowired
	DocumentAdapterService documentAdapterService;
	
	
	
	/**
	 * TODO: USARE COME TEST!!!!
	 * @return
	 * @throws IOException
	 */
    public  ResponseEntity<String>  uploadSingleFileSincronous(IntegProtocolloDTO integDTO,String access_token) throws IOException {
    	ResponseEntity<String> response =null;
        String serverUrl = integConfigurator.getUrlProtocollo();
        response=restClientAdapter.uploadSingleFileSincronous(serverUrl, getDocumentFile(),integDTO,access_token);        
        logger.debug("Response code: " + response.getStatusCode());        
        return response;
    }

    /**
     * Submit in asincronous mode
     * @param registroDocumento
     * @param integDTO
     * @return
     * @throws IOException
     */
    public  ResponseEntity<String>  uploadSingleFile(RegistroDocumento registroDocumento,IntegProtocolloDTO integDTO) throws IOException {
    	ResponseEntity<String> response =null;
        String serverUrl = integConfigurator.getUrlProtocollo();
        response=restClientAdapter.uploadSingleFileASincronous(serverUrl, getDocumentFile(registroDocumento),integDTO);        
        logger.debug("Response code: " + response.getStatusCode());        
        return response;
    }
    
    /**
     * @param registroDocumento
     * @return
     * @throws IOException
     */
    public Resource getDocumentFile(RegistroDocumento registroDocumento) throws IOException {
		return documentAdapterService.loadFileAsResource(registroDocumento);
    }
    
    /*
    private static void uploadMultipleFile() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("files", getTestFile());
        body.add("files", getTestFile());
        body.add("files", getTestFile());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String serverUrl = "http://localhost:8082/spring-rest/fileserver/multiplefileupload/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
        System.out.println("Response code: " + response.getStatusCode());
    }
    */

    /**
     * TODO Get File 
     * @return
     * @throws IOException
     */
    public static Resource getDocumentFile() throws IOException {
        Path testFile = Files.createTempFile("test-file", ".txt");
        System.out.println("Creating and Uploading Test File: " + testFile);
        Files.write(testFile, "Hello World !!, This is a test file.".getBytes());
        return new FileSystemResource(testFile.toFile());
    }
	
	
}
