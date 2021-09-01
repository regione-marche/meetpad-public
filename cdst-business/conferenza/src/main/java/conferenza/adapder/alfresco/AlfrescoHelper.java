package conferenza.adapder.alfresco;


import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import conferenza.DTO.AlfrescoDocumentAdapterDTO;
import conferenza.adapder.documentale.service.DocumentAdapterService;
import conferenza.exception.AlfrescoGenericException;
import conferenza.model.AlfrescoDocumentAdapter;
import conferenza.model.BaseRestEntity;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class AlfrescoHelper {

    private static final Logger logger = LogManager.getLogger(AlfrescoService.class.getName());

    @Autowired
    private AlfrescoAdapter alfrescoAdapter;

    @Autowired
    private AlfrescoService alfrescoService;
    
    @Autowired
    private DocumentAdapterService adapterService;
    
    
    public Document getAlfrescoDocumentById(String id) throws IOException {
    	Document documentById=null;
       	if(id==null)
    		throw new IOException();
    	
    	String alfrescodormattedId=null;    	
    	if(id.lastIndexOf("\";1.0\"")>=0)
    		alfrescodormattedId=id;
    	else
    		alfrescodormattedId=id + ";1.0";
        /**
         * get Document By Id
         */
    	documentById = alfrescoAdapter.getDocumentById(alfrescodormattedId);
        return documentById;
    }
    
    public void downloadDocumentById(HttpServletResponse response, String id) throws IOException {
    	
    	if(id==null)
    		throw new IOException();
    	
    	String alfrescodormattedId=null;    	
    	if(id.lastIndexOf("\";1.0\"")>=0)
    		alfrescodormattedId=id;
    	else
    		alfrescodormattedId=id + ";1.0";
        Document documentById = alfrescoAdapter.getDocumentById(alfrescodormattedId);

        if (documentById != null) {
            ContentStream contentStream = documentById.getContentStream();
            if (contentStream != null) {
                InputStream stream = contentStream.getStream();
                String mimeType = contentStream.getMimeType();

                logger.debug("mimetype : " + mimeType);

                response.setContentType(mimeType);

                String filename = DocumentUtils.getFilename(contentStream.getFileName());

                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));

                response.setContentLength((int) contentStream.getLength());

                // closes both streams.
                FileCopyUtils.copy(stream, response.getOutputStream());
            }
        }
    }
    
    public String[] getAlfrescoFileMetadataById(String id) throws IOException {
    	    String[] retVal=new String[3];
    	    String filename =null;
    	    String mimeType = null;
    	    String len =null;
        	if(id==null)
        		throw new IOException();        	
        	String alfrescodormattedId=null;    	
        	if(id.lastIndexOf("\";1.0\"")>=0)
        		alfrescodormattedId=id;
        	else
        		alfrescodormattedId=id + ";1.0";
            Document documentById = alfrescoAdapter.getDocumentById(alfrescodormattedId);
            if (documentById != null) {
                ContentStream contentStream = documentById.getContentStream();
                if (contentStream != null) {
                    InputStream stream = contentStream.getStream();
                    mimeType = contentStream.getMimeType();
                    filename = DocumentUtils.getFilename(contentStream.getFileName());
                    long fileLen=contentStream.getLength();
                    logger.debug("filename : " + filename);
                    logger.debug("mimetype : " + mimeType);
                    len=String.valueOf(contentStream.getLength());
                }
            }
            if(filename==null || len==null || mimeType==null) {
            	return null;            	
            }else {
            	retVal[0]=filename;
            	retVal[1]=mimeType;
            	retVal[2]=len;
            	return retVal;
            }            
    }
    	
    	
    
    
    
    /**
     * DLG
     * 
     * @param folder
     * @param searchAllVersions
     * @return
     */
    public List<CmisObject> getAllDocumentsThatAreInFolderName(String folder,boolean searchAllVersions){
    	String absolute=folder;
    	logger.debug(absolute);    		
    	return alfrescoAdapter.getAllDocumentsThatAreInFolderName(absolute, searchAllVersions);	    	
    }
    
    
    public List<CmisObject>  getAllDocumentsByFolderId(String folderId,boolean searchAllVersions){
    	return alfrescoAdapter.getAllDocumentsByFolderId(folderId, searchAllVersions);    	
    }
    
    /**
     * DLG
     * 
     * @param queryString
     * @param searchAllVersions
     * @return
     */
    public List<CmisObject> getAllDocumentsByQuery(String queryString, boolean searchAllVersions){
    	return alfrescoAdapter.getAllDocumentsByQuery( queryString,searchAllVersions);
    }
    
   
    /**
     * DLG
     * @param relativePath
     * @return
     */
    public List<String[]>  getSubfolderIdName(String relativePath){
    	/*
    	List<String[]>  listRet = null;
    	String initDir=null;
    	String folderId=alfrescoAdapter.getFolderId(relativePath);
    	if(folderId==null | "".equals(folderId))
    		initDir=DocumentUtils.SUAP_FOLDER_NAME;
    	else
    		initDir= relativePath;   	     	
    	*/
    	return alfrescoService.findSubfolderThree(relativePath) ;  	
    } 
    
    
    
    public BaseRestEntity fileUpload(MultipartFile file, HttpServletResponse httpResponse, String folderPath) throws IOException {
    	BaseRestEntity response;
    	String documentId=null;
        response = BaseRestEntity.getSuccessResponse();
        AlfrescoDocumentAdapterDTO document=null;

        if (file != null && StringUtils.isNotBlank(file.getOriginalFilename())) {
            try {
                if(StringUtils.isBlank(folderPath)) {
                	documentId = alfrescoService.uploadTempDocument(file);
                }
                else{
                    String originalFilename = file.getOriginalFilename();
                    String alfrescoId = alfrescoService.saveFileInRootFolder(file, folderPath);
                    String documentoId = originalFilename + DocumentUtils.DOCUMENT_SEPARATOR + alfrescoId;

                    document = new AlfrescoDocumentAdapterDTO();
                    document.setName(originalFilename);
                    document.setAlfrescoId(alfrescoId);
                    document.setDocumentId(documentoId);        
                    document.setAlfrescoPath(folderPath);
                    
                    //Persistenza del file Caricato su Alfresco
                    AlfrescoDocumentAdapter adapter=adapterService.storeAlfrescoDocument(document);
                    document.setId(adapter.getId());
     
                }
                response.setData(document);
            } catch (Exception e) {
                logger.error(e);
                httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        BaseRestEntity.ErrorsEnum.GENERIC.name());
                response = BaseRestEntity.getErrorResponse();
                response.setMessage(BaseRestEntity.ErrorsEnum.GENERIC.name());
            }
        } else {
            response.setMessage("file not found");
        }
        
        return response;
    	
    	
    }
    /*
    public BaseRestEntity fileUpload(MultipartFile file, HttpServletResponse httpResponse, Utente loggedUser, String folderPath) throws IOException {
        BaseRestEntity response;
        DocumentDTO document = null;
        if (loggedUser != null) {
            response = BaseRestEntity.getSuccessResponse();

            if (file != null && StringUtils.isNotBlank(file.getOriginalFilename())) {

                try {
                    if(StringUtils.isBlank(folderPath)) {
                        document = alfrescoService.uploadTempDocument(file);
                    }
                    else{
                        String originalFilename = file.getOriginalFilename();
                        String alfrescoId = alfrescoService.saveFileInFolder(file, folderPath);
                        String documentoId = originalFilename + DocumentUtils.DOCUMENT_SEPARATOR + alfrescoId;

                        document = new DocumentDTO();
                        document.setName(originalFilename);
                        document.setAlfrescoId(alfrescoId);
                        document.setDocumentId(documentoId);
                    }
                    response.setData(document);
                } catch (Exception e) {
                    logger.error(e);
                    httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            BaseRestEntity.ErrorsEnum.GENERIC.name());
                    response = BaseRestEntity.getErrorResponse();
                    response.setMessage(BaseRestEntity.ErrorsEnum.GENERIC.name());
                }
            } else {
                response.setMessage("file not found");
            }

        } else {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, BaseRestEntity.ErrorsEnum.UNAUTHORIZED.name());
            response = BaseRestEntity.getErrorResponse();
            response.setMessage(BaseRestEntity.ErrorsEnum.UNAUTHORIZED.name());
        }
        return response;
    }
    */


    public Boolean deleteDocumentById(String Id){
        return alfrescoAdapter.deleteDocumentById(Id);
    }

    public Document getDocumentById(String documentId) throws AlfrescoGenericException {
        return alfrescoAdapter.getDocumentById(documentId);
    }

	public Resource loadFileAsResource(String adapterKey) throws IOException {
		if (adapterKey == null)
			throw new IOException();

		String alfrescodormattedId = null;
		if (adapterKey.lastIndexOf("\";1.0\"") >= 0)
			alfrescodormattedId = adapterKey;
		else
			alfrescodormattedId = adapterKey + ";1.0";
		Document documentById = alfrescoAdapter.getDocumentById(alfrescodormattedId);

		if (documentById != null) {
			ContentStream contentStream = documentById.getContentStream();
			if (contentStream != null) {
				byte[] bytes = IOUtils.toByteArray(contentStream.getStream());
				return new ByteArrayResource(bytes) {
					@Override
					public String getFilename() {
						return contentStream.getFileName();
					}
				};
			}
		}
		return null;
	}
}
