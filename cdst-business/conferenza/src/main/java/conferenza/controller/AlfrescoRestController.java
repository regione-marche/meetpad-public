package conferenza.controller;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.AlfrescoDocumentAdapterDTO;
import conferenza.adapder.alfresco.AlfrescoHelper;
import conferenza.adapder.alfresco.DocumentUtils;
import conferenza.model.BaseRestEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

@RestController
@org.springframework.context.annotation.Lazy
@RequestMapping(value = "/alfresco")
public class AlfrescoRestController {

	private static final Logger logger = LoggerFactory.getLogger(AlfrescoRestController.class.getName());

	@Autowired
	private AlfrescoHelper alfrescoHelper;
	
	
	/**
	 * 
	 * @param alfrescoadapter
	 * @param request
	 * @param httpResponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/finddir", method = RequestMethod.POST, headers = {
            "Accept=application/json", "Content-type=application/json"})
	public List<String[]>  findPath(
			@RequestBody AlfrescoDocumentAdapterDTO alfrescoadapter,	
			HttpServletRequest request, 
			HttpServletResponse httpResponse
	) throws IOException {
		List<String[]> subfolderList= alfrescoHelper.getSubfolderIdName( alfrescoadapter.getAlfrescoPath());
		return subfolderList;
	}	
	
	

	/**
	 * 
	 * @param response
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String[] getDocById(HttpServletResponse response, @PathVariable("id") String id) throws IOException {
		String[] retVal=new String[4];
		Document doc=null;
		doc=alfrescoHelper.getAlfrescoDocumentById(id) ;
		logger.debug(" Name: " + doc.getName()); ;
		logger.debug(" Url: " +doc.getContentUrl());
		logger.debug(" Stream Mime Type: " + doc.getContentStreamMimeType()); ;
		logger.debug(" Len: " +doc.getContentStreamLength());
		retVal[0]=doc.getName();
		retVal[1]=doc.getContentUrl();
		retVal[2]=doc.getContentStreamMimeType();
		retVal[3]=String.valueOf(doc.getContentStreamLength());
		return retVal;
	}	
	
	/**
	 * 
	 * @param alfrescoadapter
	 * @param request
	 * @param httpResponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/find", method = RequestMethod.POST, headers = {
            "Accept=application/json", "Content-type=application/json"})
	public List<String[]>  find(
			@RequestBody AlfrescoDocumentAdapterDTO alfrescoadapter,	
			HttpServletRequest request, 
			HttpServletResponse httpResponse
	) throws IOException {
		List<String[]>  retList = new ArrayList<String[]>();
		List<CmisObject>  listCmisObject=null;
		List<String[]> subfolderList= alfrescoHelper.getSubfolderIdName( alfrescoadapter.getAlfrescoPath());
		for( String[]  listaFolder : subfolderList ) {
			listCmisObject = alfrescoHelper.getAllDocumentsByFolderId(listaFolder[1],false);
			for( CmisObject  cmisObject : listCmisObject ) {
				String[] val=new String[3];
				String name=null;
				String alfrescoId=null;
				String tipo=null; 
				if (cmisObject instanceof Document) {
				    Document document = (Document) cmisObject;
				    name=document.getName();
				    alfrescoId=document.getId();
				    tipo="Document";
				} else if (cmisObject instanceof Folder) {
				    Folder folder = (Folder) cmisObject;
				    name=folder.getName();	
				    alfrescoId=folder.getId();
				    tipo="Folder";
				}
				val[0]=name;
				val[1]=alfrescoId;
				val[2]=tipo;
				retList.add(val);
			}
		}
		return retList;
	}	
	

	/**
	 * 
	 * @param alfrescoadapter
	 * @param request
	 * @param httpResponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/doquery", method = RequestMethod.POST, headers = {
            "Accept=application/json", "Content-type=application/json"})
	public List<String[]>  doquery(
			@RequestBody AlfrescoDocumentAdapterDTO alfrescoadapter,	
			HttpServletRequest request, 
			HttpServletResponse httpResponse
	) throws IOException {
		List<String[]>  retList = new ArrayList<String[]>();
		List<CmisObject>  listCmisObject=null;
		listCmisObject = alfrescoHelper.getAllDocumentsByQuery(alfrescoadapter.getNote(),false);
		for( CmisObject  cmisObject : listCmisObject ) {
			String name=null;
			String alfrescoId=null;
			
			if (cmisObject instanceof Document) {
			    Document document = (Document) cmisObject;
			    name=document.getName();
			    alfrescoId=document.getId();
			} else if (cmisObject instanceof Folder) {
			    Folder folder = (Folder) cmisObject;
			    name=folder.getName();	
			    alfrescoId=folder.getId();
			}
			String[] val = new String[2];
			val[0]=name;
			val[1]=alfrescoId;
			retList.add(val);
		}		
		return retList;
	}	
	
	
	/* UPLOAD */
	@RequestMapping(value = "/upload/{alfrescouploadparh}", method = RequestMethod.POST)
	public BaseRestEntity handleFileUpload(
			@RequestParam("file") MultipartFile file, 	
			@PathVariable String alfrescouploadparh,
			HttpServletRequest request, 
			HttpServletResponse httpResponse
	) throws IOException {
		BaseRestEntity response = null;
		byte[] decoded = Base64.getDecoder().decode(alfrescouploadparh);
		alfrescouploadparh = new String(decoded, StandardCharsets.UTF_8);
		logger.debug(alfrescouploadparh);
		response = alfrescoHelper.fileUpload(file, httpResponse, alfrescouploadparh);
		return response;
	}
	
	@PostMapping(value = "/delete/{alfrescoId}")
	public BaseRestEntity unlinkFile(
            @PathVariable String alfrescoId, HttpServletRequest request
     ) {

		BaseRestEntity response = BaseRestEntity.getSuccessResponse();
		Boolean deleted = alfrescoHelper.deleteDocumentById(alfrescoId);

		if(!deleted) {
			response = BaseRestEntity.getErrorResponse(BaseRestEntity.ErrorsEnum.GENERIC);
		}
		return response;
	}	

	@Lazy
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public void download(HttpServletResponse response, @PathVariable("id") String id) throws IOException {
		alfrescoHelper.downloadDocumentById(response, id);
	}	
	
	public static void main(String[] args) {
		String s = Base64.getEncoder().encodeToString("CONFERENZE".getBytes());
		System.out.println(s);
	}
}
