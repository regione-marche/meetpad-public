package cdst_be_marche.adapder.alfresco;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cdst_be_marche.exception.AlfrescoGenericException;

//import it.eng.suap.acl.model.Utente;
//import it.eng.suap.model.BaseRestEntity;
//import it.eng.suap.model.document.DocumentDTO;

@Service
public class AlfrescoService {

	private static final Logger logger = LogManager.getLogger(AlfrescoService.class.getName());

	@Autowired
	private AlfrescoAdapter alfrescoAdapter;
	
	@Autowired
	AlfrescoConfigurator configuration;
	
	public ContentStream getContentStreamByDocumentId(String documentId) throws IOException {
		Document documentById = alfrescoAdapter.getDocumentById(documentId + ";1.0");
		ContentStream contentStream = null;
		if (documentById != null) {
			contentStream = documentById.getContentStream();
		}
		return contentStream;
	}

	public String saveFileInTempFolder(MultipartFile documento) {
		return saveFileInRootFolder(documento, DocumentUtils.TEMP_FOLDER_NAME);
	}
	
	
	/**
	 * Naviga l'albero cercando la cartella in questione..
	 * @param relativePath
	 * @return
	 */
	public List<String[]>  findSubfolderThree(String relativePath) {
		List<String[]>  listRet = new ArrayList<String[]>();
		String currentPath = relativePath;
		Folder rootFolder=null;
		
		List<CmisObject> folders = null;
		//if (relativePath.contains("|")){
		//	String[] splittedPath = relativePath.split("\\|");			
		//	folders=alfrescoAdapter.getAllFoldersWithName(splittedPath[0], false);
		//}	
		//------------------------------------------------------------------------
		if (CollectionUtils.isEmpty(folders)){
			folders=alfrescoAdapter.getAllFoldersWithName(configuration.getBaseRoot(), false);
		}
		//------------------------------------------------------------------------
		if (CollectionUtils.isNotEmpty(folders)){
			rootFolder = (Folder) folders.get(0);
		} else {
			logger.error("Non trovo la cartella root : " + configuration.getBaseRoot());
			return null;
		}		
				
		Folder parentFolder = rootFolder;
		Folder currentFolder = null;
		
		String parentFolderName=null;
		if (relativePath.contains("|")){
			String[] splittedPath = relativePath.split("\\|");			
			for(int i = 0; i<splittedPath.length; i++){
				currentPath = splittedPath[i];
				if(i==0)
				if(currentPath.equals(parentFolder.getName())) {
					continue;
				}
					
				currentFolder = alfrescoAdapter.getFolderByParentAndFolderName(parentFolder, currentPath);
				if (currentFolder == null){
					throw new AlfrescoGenericException("Folder not Found");
				};
				parentFolderName=parentFolder.getName();
				parentFolder = currentFolder;
			}			
		}
		String name=currentFolder.getName();
		String aId=currentFolder.getId();
		String[] val = new String[3];
		val[0]=name;
		val[1]=aId;
		val[2]=parentFolderName;
		listRet.add(val);			
		return listRet;
	}

	/**
	 * dato un id cartella cerca ricorsivamente la presenza si una cartella dal nodo dato..
	 * restituendone l'id
	 * @param relativePath
	 * @return
	 */
	public List<String[]>  findSubfolderById(String folderId,String folderNAme,List<String[]> listRet) {				
				if(listRet==null || listRet.isEmpty())
					listRet = new ArrayList();	
		        List<CmisObject> subfolderList=null;
				String alfrescoId = null;
				Folder rootFolder = null;
				List<CmisObject> folders = null;
				if(folderNAme!=null && !"".equals(folderNAme)) {
					folders = alfrescoAdapter.getChildrenFolderByParentId(folderId, false);
					for( CmisObject  cmisObject : folders ) {
						String name=null;
						String aId=null;							
						if (cmisObject instanceof Folder) {
						    Folder folder = (Folder) cmisObject;
						    name=folder.getName();	
						    aId=folder.getId();
						}
						String[] val = new String[2];
						val[0]=name;
						val[1]=aId;
						listRet.add(val);						
						if (cmisObject instanceof Folder) {
							if( !folderNAme.equals(name))
								findSubfolderById(aId,folderNAme,listRet);
							else {
							    listRet = new ArrayList();
								val[0]=name;
								val[1]=aId;
								listRet.add(val);
								logger.info(name+" - "+aId);
								return listRet;
							}
						}						
					}					
			    }			 				
				return listRet;
		}								
	
	
	/**
	 * salva un file su Alfresco, in uan path determinata; se la path non esiste la crea ricorsivamente
	 * relativePath è da specificare nella forma: "pratiche|120|notifiche|32" col sepatatore pipe.
	 * @param documento
	 * @param relativePath
	 * @return
	 */	
	public String saveFileInRootFolder(MultipartFile documento, String relativePath) {
		String alfrescoId = null;
		Folder rootFolder = null;
		
		List<CmisObject> folders = alfrescoAdapter.getAllFoldersWithName(configuration.getBaseRoot(), false);
		if (CollectionUtils.isNotEmpty(folders)){
			rootFolder = (Folder) folders.get(0);
		} else {
			logger.error("Non trovo la cartella root del SUAP: " + configuration.getBaseRoot());
			return null;
		}

		int counter = 1;

		while (alfrescoId == null || counter < 5) {

			logger.info("Attempt to upload a document number [" + counter + "]");

			Folder parentFolder = rootFolder;
			Folder currentFolder = null;
			
			String currentPath = relativePath;
			
			if (relativePath.contains("|")){
				String[] splittedPath = relativePath.split("\\|");
				
				for(int i = 0; i<splittedPath.length; i++){
					currentPath = splittedPath[i];
					currentFolder = alfrescoAdapter.getFolderByParentAndFolderName(parentFolder, currentPath);
					if (currentFolder == null){
						currentFolder = alfrescoAdapter.createFolder(parentFolder, currentPath);
					};
					parentFolder = currentFolder;
				}
				
			} else {
				currentFolder = alfrescoAdapter.getFolderByParentAndFolderName(parentFolder, currentPath);
				if (currentFolder == null){
					currentFolder = alfrescoAdapter.createFolder(parentFolder, currentPath);
				};
			}
			
			
			if (currentFolder != null) {
				String fileName = FilenameUtils.getName(documento.getOriginalFilename());
				Document createdDocument = alfrescoAdapter.createDocument(currentFolder, fileName
						+ DocumentUtils.DOCUMENT_SEPARATOR_ALFRESCO_DOCUMENT + Calendar.getInstance().getTimeInMillis(),
						getContentStream(documento));
				if (createdDocument != null) {
					alfrescoId = createdDocument.getId();
				}
			}

			counter++;
		}

		return alfrescoId;
	}	
	private ContentStream getContentStream(MultipartFile file) {
		ContentStream contentStream = null;
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
			byte[] buff = new byte[8000];
			int bytesRead = 0;

			String mimeType = "application/octet-stream";

			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			while ((bytesRead = inputStream.read(buff)) != -1) {
				bao.write(buff, 0, bytesRead);
			}

			byte[] buf = bao.toByteArray();
			ByteArrayInputStream input = new ByteArrayInputStream(buf);

			contentStream = alfrescoAdapter.getSession().getObjectFactory().createContentStream(file.getName(),
					buf.length, mimeType, input);
		} catch (IOException e) {
			logger.error("Unable to get content stream from file " + file.getName());
			// e.printStackTrace();
			logger.error(e);
		}
		return contentStream;

	}
	
	/*
	public DocumentDTO uploadTempDocument(MultipartFile file) throws InterruptedException {
		DocumentDTO document = null;
		String documentoId = null;
		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String alfrescoId = this.saveFileInTempFolder(file);
			alfrescoId = (alfrescoId.split(";"))[0];
			if (alfrescoId != null) {
				documentoId = originalFilename + SuapUtils.DOCUMENT_SEPARATOR + alfrescoId;
				document = new DocumentDTO();
				document.setName(originalFilename);
				document.setAlfrescoId(alfrescoId);
				document.setDocumentId(documentoId);
			}
		}
		return document;
	}
	*/	
	public String  uploadTempDocument(MultipartFile file) throws InterruptedException {
		
		String documentoId = null;
		if (file != null && !file.isEmpty()) {
			String originalFilename = file.getOriginalFilename();
			String alfrescoId = this.saveFileInTempFolder(file);
			alfrescoId = (alfrescoId.split(";"))[0];
			if (alfrescoId != null) {
				documentoId = originalFilename + DocumentUtils.DOCUMENT_SEPARATOR + alfrescoId;
			}
		}
		return documentoId;
	}
}
