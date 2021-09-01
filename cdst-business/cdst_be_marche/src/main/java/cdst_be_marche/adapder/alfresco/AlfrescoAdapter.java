package cdst_be_marche.adapder.alfresco;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.AclCapabilities;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PermissionMapping;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.AclPropagation;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cdst_be_marche.exception.AlfrescoGenericException;

@org.springframework.context.annotation.Lazy
@Component
@Transactional
public class AlfrescoAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AlfrescoAdapter.class.getName());

	private static final String ORDER_BY_CMIS_CREATION_DATE = " ORDER BY cmis:creationDate ";

	private static final String CMIS_OBJECT_ID = "cmis:objectId";

	private static final String SELECT_FROM_CMIS_DOCUMENT = "select * from cmis:document ";

	private static final String ALFRESCO_QUERY = "Alfresco Query : ";

	private static final String WHERE = " where ";

	private static final String UNDER_FOLDER = " under folder : ";

	private static final String PARAMETER_MISSING = "Parameter missing";

	private static final String WAS_ADDED_TO = " was added to ";

	private static final String USER = "User: ";

	private static final String GROUPS = "groups/";

	private static final String GROUP = "Group: ";

	@Autowired
	AlfrescoConfigurator alfrescoConfigurator;

	@org.springframework.context.annotation.Lazy
	@Autowired
	AlfrescoHttpClient alfrescoHttpClient;

	private Session session = null;

	/**
	 * E' una sessione alfresco..
	 * @return
	 * @throws AlfrescoGenericException
	 */
	public Session getSession() throws AlfrescoGenericException {

		try {
			if (session == null) {
				// default factory implementation
				SessionFactory factory = SessionFactoryImpl.newInstance();
				Map<String, String> parameter = new HashMap<>();

				// user credentials
				parameter.put(SessionParameter.USER, alfrescoConfigurator.getAlfrescoSuperadminUser());
				parameter.put(SessionParameter.PASSWORD, alfrescoConfigurator.getAlfrescoSuperadminPassword());

				// connection settings
				// Atom Pub binding
				parameter.put(SessionParameter.ATOMPUB_URL,
						alfrescoConfigurator.getBaseUrl() + "alfresco/api/-default-/public/cmis/versions/1.1/atom");
				parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());

				List<Repository> repositories = factory.getRepositories(parameter);

				this.session = repositories.get(0).createSession();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			String errorMessage = "Error creating the sesssion.";
			throw new AlfrescoGenericException(errorMessage);
		}

		return this.session;
	}

	public List<CmisObject> getAllFoldersWithName(String folderName, boolean searchAllVersions)
			throws AlfrescoGenericException {

		List<CmisObject> documents = null;

		try {

			String queryString = "select * from cmis:folder ";
			if (StringUtils.isNotBlank(folderName)) {
				queryString += " where cmis:name = '" + folderName + "'";
			}

			queryString += " " + ORDER_BY_CMIS_CREATION_DATE;

			logger.info("Alfresco Query search folder : " + queryString);

			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			String errorMessage = "Error loading folder with name : " + folderName;
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}

	/**
	 * Gets the object ID for a folder of a specified name which is assumed to
	 * be unique across the entire repository.
	 *
	 * @return String
	 */
	public String getFolderId(String folderName) throws AlfrescoGenericException {
		String objectId = null;

		try {
			String queryString = "select cmis:objectId from cmis:folder where cmis:name = '" + folderName + "'";
			ItemIterable<QueryResult> results = getSession().query(queryString, false);

			if (results != null) {
				for (QueryResult qResult : results) {
					objectId = qResult.getPropertyValueByQueryName(CMIS_OBJECT_ID);
				}
			}

			logger.debug("Folder id found : " + objectId);
		} catch (Exception ex) {
			String errorMessage = "Error loading the folder : " + folderName;
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return objectId;
	}
	
	
	public List<CmisObject> getChildrenFolderByParentId(String folderId,boolean searchAllVersions) throws AlfrescoGenericException {
		String objectId = null;
		List<CmisObject> documents = null;
		try {
			String queryString = "select cmis:objectId from cmis:folder where in_folder('" + folderId + "')";
			logger.info("Alfresco Query search folder : " + queryString);

			documents = getQueryResults(queryString, searchAllVersions);

			logger.debug("Folder id found : " + objectId);
		} catch (Exception ex) {
			String errorMessage = "Error loading the folder : " + folderId;
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}
	

	/**
	 * Gets the Folder by id.
	 *
	 * @return Folder
	 */
	public Folder getFolderById(String id) throws AlfrescoGenericException {
		Folder folder = null;

		try {

			CmisObject object = getSession().getObject(id);
			if (object != null) {
				folder = (Folder) object;
				logger.debug("Folder found : " + folder.getName());
			}

		} catch (Exception ex) {
			String errorMessage = "Error loading the folder with id: " + id;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return folder;
	}

	/**
	 * Gets the object ID for a document of a specified name which is assumed to
	 * be unique across the entire repository.
	 *
	 * @return String
	 */
	public String getDocumentId(String documentName) throws AlfrescoGenericException {
		String objectId = null;

		try {
			String queryString = "select cmis:objectId from cmis:document where cmis:name = '" + documentName + "'";
			ItemIterable<QueryResult> results = getSession().query(queryString, false);

			if (results != null) {
				for (QueryResult qResult : results) {
					objectId = qResult.getPropertyValueByQueryName(CMIS_OBJECT_ID);
				}
			}

			logger.debug("Document id found : " + objectId);
		} catch (Exception ex) {
			String errorMessage = "Error loading the document : " + documentName;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return objectId;
	}

	public Document getDocumentById(String documentId) throws AlfrescoGenericException {
		Document doc = null;

		try {

			OperationContext operationContext = new OperationContextImpl();
			operationContext.setIncludeAcls(true);

			CmisObject obj = getSession().getObject(documentId, operationContext);

			if (obj != null && obj instanceof Document) {
				doc = (Document) obj;
			}

		} catch (Exception ex) {
			String errorMessage = "Error loading the document by id : " + documentId;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			// throw new AlfrescoGenericException(errorMessage);
			return null;
		}

		return doc;
	}

	public Folder getFolderByParentAndFolderName(Folder parent, String folderName) {

		Folder returnFolder = null;
		ItemIterable<CmisObject> children = parent.getChildren();
		for (CmisObject folder : children) {
			if (folder instanceof Folder) {
				Folder current = (Folder) folder;
				if (current.getName().equals(folderName)) {
					returnFolder = current;
					break;
				}
			}
		}

		return returnFolder;
	}

	public boolean doesFolderExist(Folder target, String folderName) {

		boolean folderExists = false;

		Folder targetFolder = getFolderById(target.getId());
		ItemIterable<CmisObject> children = targetFolder.getChildren();

		for (CmisObject folder : children) {
			if (folder instanceof Folder) {
				Folder current = (Folder) folder;
				if (current.getName().equals(folderName)) {
					folderExists = true;
					break;
				}
			}
		}

		return folderExists;
	}

	public Folder searchFolderWithQuery(String folderPath) throws AlfrescoGenericException {

		// Grab a reference to the folder by path
		// This assumes the object represented by that path is actually a folder
		Folder folder = null;
		try {
			folder = (Folder) getSession().getObjectByPath(folderPath);

			if (folder != null) {
				logger.debug("Folder found with id: " + folder.getId() + ", name : " + folder.getName()
						+ " and description " + folder.getDescription());
			} else {
				logger.warn("Folder not found with path " + folderPath);
			}
		} catch (Exception ex) {
			String errorMessage = "Error in search folder :" + folderPath + " by query.";
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return folder;
	}

	public List<CmisObject> getAllDocuments(boolean searchAllVersions) throws AlfrescoGenericException {

		List<CmisObject> documents = null;

		try {

			String queryString = SELECT_FROM_CMIS_DOCUMENT + ORDER_BY_CMIS_CREATION_DATE;
			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			String errorMessage = "Error loading all documents..";
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}

	public List<CmisObject> getAllDocumentsThatContainsWord(String word, boolean searchAllVersions)
			throws AlfrescoGenericException {

		List<CmisObject> documents = null;

		try {

			String queryString = SELECT_FROM_CMIS_DOCUMENT;
			if (StringUtils.isNotBlank(word)) {
				queryString += " where contains('" + word + "')";
			}

			queryString += " " + ORDER_BY_CMIS_CREATION_DATE;

			logger.info(ALFRESCO_QUERY + queryString);

			documents = getQueryResults(queryString, searchAllVersions);

		} catch (Exception ex) {
			String errorMessage = "Error loading documents that contains the word : " + word;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}

	public List<CmisObject> getAllDocumentsWithName(String fileName, boolean searchAllVersions)
			throws AlfrescoGenericException {

		List<CmisObject> documents = null;

		try {
			String queryString = SELECT_FROM_CMIS_DOCUMENT;
			if (StringUtils.isNotBlank(fileName)) {
				queryString += " where cmis:name LIKE '%" + fileName + "%'";
			}

			queryString += " " + ORDER_BY_CMIS_CREATION_DATE;

			logger.debug("Alfresco Query search document : " + queryString);

			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			String errorMessage = "Error loading all documents with name : " + fileName;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}

	public List<CmisObject> getAllDocumentsThatContainsWordAndAreInFolderName(String word, String folderName,
			boolean searchAllVersions) throws AlfrescoGenericException {

		List<CmisObject> documents = null;

		try {
			String queryString = SELECT_FROM_CMIS_DOCUMENT;

			if (StringUtils.isNotBlank(word) || StringUtils.isNotBlank(folderName)) {
				queryString += WHERE;
			}

			if (StringUtils.isNotBlank(word)) {
				queryString += " contains('" + word + "')";
			}

			if (StringUtils.isBlank(word) && StringUtils.isNotBlank(folderName)) {
				queryString += " in_folder('" + getFolderId(folderName) + "')";
			} else if (StringUtils.isNotBlank(word) && StringUtils.isNotBlank(folderName)) {
				queryString += " and in_folder('" + getFolderId(folderName) + "')";
			}

			queryString += " " + ORDER_BY_CMIS_CREATION_DATE;

			logger.debug(ALFRESCO_QUERY + queryString);

			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			String errorMessage = "Error loading documents that contains the word : " + word + " and are in folder : "
					+ folderName;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}

	public List<CmisObject> getAllDocumentsByQuery(String queryString, boolean searchAllVersions){
		List<CmisObject> documents = null;
		try {
			logger.info(queryString);
			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			String errorMessage = "Error in Query String " ;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}
		return documents;			
	}

	/**
	 * DLG
	 * 
	 * @param folderId
	 * @param searchAllVersions
	 * @return
	 * @throws AlfrescoGenericException
	 */
	public List<CmisObject> getAllDocumentsByFolderId(String folderId, boolean searchAllVersions)
			throws AlfrescoGenericException {

		List<CmisObject> documents = null;
		
		if(folderId==null)
			throw new AlfrescoGenericException("Id Folder Null");
		
		try {
			String queryString = SELECT_FROM_CMIS_DOCUMENT;

			queryString += WHERE;
			queryString += " in_folder('" + folderId + "')";
			queryString += " " + ORDER_BY_CMIS_CREATION_DATE;

			logger.info(ALFRESCO_QUERY + queryString);

			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			String errorMessage = "Error loading the documents that are in the folder : " + folderId;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}

	
	/**
	 * 
	 * @param folderName
	 * @param searchAllVersions
	 * @return
	 * @throws AlfrescoGenericException
	 */
	public List<CmisObject> getAllDocumentsThatAreInFolderName(String folderName, boolean searchAllVersions)
			throws AlfrescoGenericException {

		List<CmisObject> documents = null;

		try {
			String queryString = SELECT_FROM_CMIS_DOCUMENT;

			if (StringUtils.isNotBlank(folderName)) {
				queryString += WHERE;
			}

			if (StringUtils.isNotBlank(folderName)) {
				queryString += " in_folder('" + getFolderId(folderName) + "')";
			}

			queryString += " " + ORDER_BY_CMIS_CREATION_DATE;

			logger.info(ALFRESCO_QUERY + queryString);

			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			String errorMessage = "Error loading the documents that are in the folder : " + folderName;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(errorMessage);
		}

		return documents;
	}

	public List<CmisObject> getAllDocumentsByCreationDateRange(Calendar startDate, Calendar endDate,
			boolean searchAllVersions) throws AlfrescoGenericException {

		List<CmisObject> documents = null;

		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {

			String queryString = SELECT_FROM_CMIS_DOCUMENT;

			if (startDate != null || endDate != null) {
				queryString += WHERE;
			}

			if (startDate != null && endDate == null) {

				String startDateToString = ft.format(startDate.getTime());

				queryString += " (cmis:creationDate >= TIMESTAMP '" + startDateToString + "' )";
			}

			if (startDate == null && endDate != null) {

				String endDateToString = ft.format(endDate.getTime());

				queryString += " (cmis:creationDate <= TIMESTAMP '" + endDateToString + "' )";
			}

			if (startDate != null && endDate != null) {
				String startDateToString = ft.format(startDate.getTime());
				String endDateToString = ft.format(endDate.getTime());

				queryString += " (cmis:creationDate >= TIMESTAMP '" + startDateToString + "' "
						+ " and cmis:creationDate <= TIMESTAMP '" + endDateToString + "' )";
			}

			queryString += " " + ORDER_BY_CMIS_CREATION_DATE;

			logger.debug(ALFRESCO_QUERY + queryString);

			documents = getQueryResults(queryString, searchAllVersions);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			if (startDate != null && endDate != null) {
				String errorMessage = "Error loading documents by range date -  start date : "
						+ ft.format(startDate.getTime()) + " and end date : " + ft.format(endDate.getTime());
				logger.error(errorMessage);
				throw new AlfrescoGenericException(errorMessage);
			} else {
				String errorMessage = "Error loading documents by range date and  start date ";
				logger.error(errorMessage);
				throw new AlfrescoGenericException(errorMessage);
			}
		}

		return documents;
	}

	/**
	 * Create folder directly under target folder
	 *
	 * @param target
	 * @param createFolderName
	 * @return newly created folder
	 */
	public Folder createFolder(Folder target, String newFolderName) throws CmisUnauthorizedException {

		String sanitizedNewFolderName = StringsUtils.sanityze(newFolderName);
		// Make sure the user is allowed to create a folder under the root
		// folder
		if (!target.getAllowableActions().getAllowableActions().contains(Action.CAN_CREATE_FOLDER)) {

			String errorMessage = "Current user does not have permission to create a sub-folder in " + target.getPath();
			logger.error(errorMessage);
			throw new CmisUnauthorizedException(errorMessage);
		}

		Folder newFolder = null;

		try {

			// Check if folder already exist, if not create it
			boolean folderExists = doesFolderExist(target, sanitizedNewFolderName);

			if (!folderExists) {

				Map<String, String> props = new HashMap<>();
				props.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
				props.put(PropertyIds.NAME, sanitizedNewFolderName);
				newFolder = target.createFolder(props);
				newFolder.setAcl(new LinkedList<>());

				// ADDING A NEW ACL
				// String principal = "juansanin";
				// RepositoryInfo repositoryInfo =
				// getSession().getRepositoryInfo();
				// AclCapabilities aclCapabilities =
				// repositoryInfo.getAclCapabilities();
				//
				// Map<String, PermissionMapping> permissionMappings =
				// aclCapabilities.getPermissionMapping();
				// PermissionMapping permissionMapping =
				// permissionMappings.get(PermissionMapping.CAN_VIEW_CONTENT_OBJECT);
				//
				// List<String> permissions =
				// permissionMapping.getPermissions();
				// Ace addAce = session.getObjectFactory().createAce(principal,
				// permissions);
				//
				// List<Ace> addAces = new LinkedList<Ace>();
				// addAces.add(addAce);
				//
				// // with AclPropagation.OBJECTONLY this permission should
				// apply only to this object
				// Document doc2 = null;
				// doc2.addAcl(addAces, AclPropagation.OBJECTONLY);

				// Informazioni sui ruoli:
				// Contributore: ha diritti completi sui contenuti di cui è
				// proprietario e non può modificare né eliminare i contenuti
				// creati da altri.
				// Collaboratore: ha diritti completi sui contenuti di cui è
				// proprietario e ha il diritto di modificare ma non di
				// eliminare i contenuti creati da altri.
				// Coordinatore: ha diritti completi su tutti i contenuti, sia
				// quelli creati personalmente sia quelli creati da altri.
				// Redattore: ha il diritto di modificare le proprietà dei file
				// e di eseguire il Check In e il Check Out dei file, ma non può
				// creare contenuti personali.
				// Consumatore: ha diritti di sola lettura e non può creare
				// contenuti personali.

			} else {
				logger.debug("Folder already exist: " + sanitizedNewFolderName);

				newFolder = getFolderByParentAndFolderName(target, sanitizedNewFolderName);
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			String errorMessage = "Error creating the folder : " + sanitizedNewFolderName + UNDER_FOLDER
					+ target.getPath();
			logger.error(errorMessage);
			throw new AlfrescoGenericException(ex.getMessage());
		}

		return newFolder;
	}

	public void addAclGroupToGroup(String groupName, String folderId) {

		OperationContext operationContext = new OperationContextImpl();
		operationContext.setIncludeAcls(true);

		List<String> permissions = getPermissionsByGroupName(groupName);

		Ace addAce = getSession().getObjectFactory().createAce(groupName, permissions);

		List<Ace> addAces = new LinkedList<>();
		addAces.add(addAce);

		CmisObject obj = getSession().getObject(folderId, operationContext);
		obj.addAcl(addAces, AclPropagation.PROPAGATE);
	}

	public void addAclGroupToDocument(String groupName, String documentId) {

		OperationContext operationContext = new OperationContextImpl();
		operationContext.setIncludeAcls(true);

		List<String> permissions = getPermissionsByGroupName(groupName);

		Ace addAce = getSession().getObjectFactory().createAce(groupName, permissions);

		List<Ace> addAces = new LinkedList<>();
		addAces.add(addAce);

		CmisObject obj = getSession().getObject(documentId, operationContext);
		obj.addAcl(addAces, AclPropagation.PROPAGATE);
	}

	private List<String> getPermissionsByGroupName(String groupName) {

		RepositoryInfo repositoryInfo = getSession().getRepositoryInfo();
		AclCapabilities aclCapabilities = repositoryInfo.getAclCapabilities();

		Map<String, PermissionMapping> permissionMappings = aclCapabilities.getPermissionMapping();

		return AlfrescoUtils.getAlfrescoPermissionsByGroupName(groupName, permissionMappings);
	}

	/**
	 * Delete document
	 *
	 * @param target
	 * @param delDocName
	 */
	public void deleteDocument(Folder target, String delDocName) throws AlfrescoGenericException {
		try {
			CmisObject object = getSession().getObjectByPath(target.getPath() + "/" + delDocName);
			Document delDoc = (Document) object;
			delDoc.delete(true);
		} catch (CmisObjectNotFoundException e) {
			logger.error(e.getMessage());
			String errorMessage = "Error deleting document : " + delDocName + " in folder : " + target.getPath();
			logger.error(errorMessage);
			throw new AlfrescoGenericException(errorMessage);
		}
	}

	/**
	 * Delete document for given id
	 *
	 * @param documentId
	 * @return
	 * @throws AlfrescoGenericException
	 */
	public boolean deleteDocumentById(String documentId) throws AlfrescoGenericException {
		Document doc = null;

		try {

			OperationContext operationContext = new OperationContextImpl();
			operationContext.setIncludeAcls(true);

			CmisObject obj = getSession().getObject(documentId, operationContext);

			if (obj != null && obj instanceof Document) {
				doc = (Document) obj;
			}

			doc.delete(true);

		} catch (Exception ex) {
			String errorMessage = "Error deleting  document by id : " + documentId;
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			// throw new AlfrescoGenericException(errorMessage);
			return false;
		}

		return true;
	}




	/**
	 * Delete folder
	 *
	 * @param folderToDelete
	 *
	 */
	public void deleteFolder(Folder folderToDelete) throws AlfrescoGenericException {
		try {
			folderToDelete.deleteTree(true, UnfileObject.DELETE, true);
		} catch (CmisObjectNotFoundException e) {
			logger.error(e.getMessage());
			String errorMessage = "Error deleting folder : " + folderToDelete.getPath() + " and sub folders.";
			logger.error(errorMessage);
			throw new AlfrescoGenericException(errorMessage);
		}
	}

	/**
	 * Clean up the content of the folder
	 *
	 * @param target
	 * @param delFolderName
	 */
	public void cleanup(Folder target, String delFolderName) throws AlfrescoGenericException {
		try {
			CmisObject object = getSession().getObjectByPath(target.getPath() + delFolderName);
			Folder delFolder = (Folder) object;
			delFolder.deleteTree(true, UnfileObject.DELETE, true);
		} catch (CmisObjectNotFoundException e) {
			logger.error(e.getMessage());
			String errorMessage = "No need to clean up.";
			logger.error(errorMessage);
			throw new AlfrescoGenericException(errorMessage);
		}
	}

	/**
	 * List all folder under target
	 *
	 * @param depth
	 * @param target
	 *            The target folder
	 * @return {@link Map<Integer, String>} of folders
	 */
	public Map<Integer, String> listFolder(Integer depth, Folder target) throws AlfrescoGenericException {

		Map<Integer, String> folders = new HashMap<>();

		if (target == null) {
			logger.warn("Target folder cannot be empty..");
			return folders;
		}

		try {

			String indent = StringUtils.repeat("\t", depth);

			for (Iterator<CmisObject> it = target.getChildren().iterator(); it.hasNext();) {
				CmisObject o = it.next();
				if (BaseTypeId.CMIS_FOLDER.equals(o.getBaseTypeId())) {
					logger.debug(indent + "[Folder] " + o.getName());
					Folder folder = (Folder) o;
					folders.put(depth, folder.getName());
					listFolder(++depth, folder);
				}
			}

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			String errorMessage = "Error invoking list folder";
			logger.error(errorMessage);
			throw new AlfrescoGenericException(errorMessage);
		}

		return folders;
	}

	/**
	 * Create document with content.
	 *
	 * @param target
	 *            The target folder
	 * @param newDocName
	 *            The document name
	 * @param contentStream
	 *            The document content
	 *
	 * @return {@link Document}
	 *
	 */
	public Document createDocument(Folder target, String newDocName, ContentStream contentStream)
			throws AlfrescoGenericException {
		Document document = null;

		String sanitizedNewDocName = StringsUtils.smartTruncate(newDocName.trim());

		try {
			// Make sure the user is allowed to create a document in the passed
			// in folder
			if (!target.getAllowableActions().getAllowableActions().contains(Action.CAN_CREATE_DOCUMENT)) {
				String errorMessage = "Current user does not have permission to create a document in "
						+ target.getPath();
				logger.error(errorMessage);
				throw new CmisUnauthorizedException(errorMessage);
			}

			Map<String, String> props = new HashMap<>();
			props.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
			props.put(PropertyIds.NAME, sanitizedNewDocName);

			logger.debug("Created document : " + sanitizedNewDocName + UNDER_FOLDER + target.getName());

			document = target.createDocument(props, contentStream, VersioningState.MAJOR);

		} catch (Exception ex) {
			String errorMessage = "Error creating the document : " + sanitizedNewDocName + UNDER_FOLDER
					+ target.getName();
			logger.error(errorMessage);
			logger.error(ex.getMessage());
			throw new AlfrescoGenericException(ex.getMessage());
		}

		return document;
	}

	/**
	 * Checks if group exists.
	 *
	 * @param groupName
	 *            String group name
	 * @return true if user exists
	 * @throws AlfrescoGenericException
	 *             if error
	 */
	public boolean groupExists(final String groupName) throws AlfrescoGenericException {
		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}
		String reqURL = alfrescoHttpClient.getApiUrl() + GROUPS + groupName;
		HttpGet request = new HttpGet(reqURL);

		HttpResponse response = null;
		try {
			response = alfrescoHttpClient.executeRequest(alfrescoHttpClient, 
					alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					request);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Create a new Group.
	 *
	 * @param groupName
	 *            group name
	 * @return true if user exists
	 * @throws AlfrescoGenericException
	 *             if error
	 */
	@SuppressWarnings("unchecked")
	public boolean createGroup(final String groupName) throws AlfrescoGenericException {

		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}

		boolean groupExists = groupExists(groupName);

		if (groupExists) {
			logger.warn(GROUP + groupName + " not created because it already exists");
			return false;
		}

		String reqURL = alfrescoHttpClient.getApiUrl() + "rootgroups/" + groupName;
		logger.trace("Create group using url - " + reqURL);

		HttpPost request = new HttpPost(reqURL);
		JSONObject body = new JSONObject();
		body.put("displayName", groupName);
		HttpResponse response = null;
		try {
			response = alfrescoHttpClient.executeRequestWithEntity(alfrescoHttpClient, 
					alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					body, request);
			if (HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
				logger.trace(GROUP + groupName + " is created successfully");
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Add user to group.
	 *
	 * @param groupName
	 *            group name
	 * @param userName
	 *            String identifier
	 * @return true if user exists
	 * @throws AlfrescoGenericException
	 *             if error
	 */
	@SuppressWarnings("unchecked")
	public boolean addUserToGroup(final String groupName, final String userName) throws AlfrescoGenericException {
		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}

		boolean isUserAddedToGroup = isUserAddedToGroup(groupName, userName);

		if (isUserAddedToGroup) {
			logger.warn("User : " + userName + " is already part of group : " + groupName);
			return false;
		}

		String reqURL = alfrescoHttpClient.getApiUrl() + GROUPS + groupName + "/children/" + userName;
		HttpPost request = new HttpPost(reqURL);
		JSONObject body = new JSONObject();
		body.put(StringUtils.EMPTY, StringUtils.EMPTY);
		HttpResponse response = null;
		try {
			response = alfrescoHttpClient.executeRequestWithEntity(alfrescoHttpClient
					, alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					body, request);
			switch (response.getStatusLine().getStatusCode()) {
			case HttpStatus.SC_OK:
				logger.trace("User " + userName + WAS_ADDED_TO + groupName);
				return true;
			case HttpStatus.SC_NOT_FOUND:
				logger.trace("Root group " + groupName + " not found");
				break;
			default:
				logger.error("Unable to add user to group: " + response.toString());
				break;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			//throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Add sub group. If sub group doesn't exists it will be created.
	 *
	 * @param groupName
	 *            group name
	 * @param subGroup
	 *            sub group name
	 * @return true if subgroup is created
	 * @throws Exception
	 *             if error
	 */
	@SuppressWarnings("unchecked")
	public boolean addSubGroup(final String groupName, final String subGroup) throws AlfrescoGenericException {
		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}
		String reqURL = alfrescoHttpClient.getApiUrl() + GROUPS + groupName + "/children/GROUP_" + subGroup;
		HttpPost request = new HttpPost(reqURL);
		JSONObject body = new JSONObject();
		body.put(StringUtils.EMPTY, StringUtils.EMPTY);
		HttpResponse response;
		try {
			response = alfrescoHttpClient.executeRequestWithEntity(alfrescoHttpClient, 
					alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					body, request);
			switch (response.getStatusLine().getStatusCode()) {
			case HttpStatus.SC_CREATED:
				logger.trace("Sub group " + subGroup + WAS_ADDED_TO + groupName);
				return true;
			case HttpStatus.SC_OK:
				logger.trace("Sub group " + subGroup + WAS_ADDED_TO + groupName);
				return true;
			case HttpStatus.SC_NOT_FOUND:
				logger.trace("Root group " + groupName + " not found");
				break;
			default:
				logger.error("Unable to add sub group to group: " + response.toString());
				break;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Remove user from group
	 *
	 * @param groupName
	 *            group name
	 * @param userName
	 *            String identifier
	 * @return true if user exists
	 * @throws Exception
	 *             if error
	 */
	public boolean removeUserFromGroup(final String groupName, final String userName) throws AlfrescoGenericException {
		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}
		String reqURL = alfrescoHttpClient.getApiUrl() + GROUPS + groupName + "/children/" + userName;
		HttpDelete request = new HttpDelete(reqURL);
		HttpResponse response;
		try {
			response = alfrescoHttpClient.executeRequest(alfrescoHttpClient, 
					alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					request);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				logger.trace(USER + userName + " is removed from " + groupName);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Remove subgroup from group
	 *
	 * @param groupName
	 *            group name
	 * @param subGroup
	 *            sub group name
	 * @return true if user exists
	 * @throws Exception
	 *             if error
	 */
	public boolean removeSubgroupFromGroup(final String groupName, final String subGroup)
			throws AlfrescoGenericException {
		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}
		String reqURL = alfrescoHttpClient.getApiUrl() + GROUPS + groupName + "/children/GROUP_" + subGroup;
		HttpDelete request = new HttpDelete(reqURL);
		HttpResponse response;
		try {
			response = alfrescoHttpClient.executeRequest(alfrescoHttpClient, alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), request);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				logger.trace("Sub group: " + subGroup + " is removed from " + groupName);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Remove a root group
	 *
	 * @param groupName
	 *            String group name
	 * @return true if user exists
	 * @throws Exception
	 *             if error
	 */
	public boolean deleteGroup(final String groupName) throws AlfrescoGenericException {
		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}
		String reqURL = alfrescoHttpClient.getApiUrl() + "rootgroups/" + groupName;
		HttpDelete request = new HttpDelete(reqURL);
		HttpResponse response;
		try {
			response = alfrescoHttpClient.executeRequest(alfrescoHttpClient, alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), request);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				logger.trace(GROUP + groupName + " is removed successfully");
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Get group details
	 *
	 * @param groupName
	 *            String group
	 * @return HttpReponse 200 if ok
	 * @throws IllegalArgumentException
	 */
	private HttpResponse getGroupDetails(final String groupName) {
		if (StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(groupName)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}
		String reqURL = alfrescoHttpClient.getApiUrl() + GROUPS + groupName + "/children?alf_ticket="
				+ alfrescoHttpClient.getAlfTicket(alfrescoConfigurator.getAlfrescoSuperadminUser(), alfrescoConfigurator.getAlfrescoSuperadminPassword());
		HttpGet request = new HttpGet(reqURL);
		return alfrescoHttpClient.executeRequest(request);
	}

	/**
	 * Count users and groups added in root group
	 *
	 * @param groupName
	 *            String group name
	 * @return true if user exists
	 * @throws Exception
	 *             if error
	 */
	@SuppressWarnings("rawtypes")
	public int countAuthoritiesFromGroup(final String groupName) throws AlfrescoGenericException {
		HttpResponse response = getGroupDetails(groupName);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			try {
				HttpEntity entity = (HttpEntity) response.getEntity();
				String responseString = EntityUtils.toString((org.apache.http.HttpEntity) entity, "UTF-8");
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(responseString);
				JSONObject data = (JSONObject) obj.get("paging");
				long count = (Long) data.get("totalItems");
				return (int) count;
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new AlfrescoGenericException(e.getMessage());
			}
		}
		return 0;
	}

	/**
	 * Verify if a user is member of a group.
	 *
	 * @param groupName
	 *            String group
	 * @param userName
	 *            String user to be searched
	 * @return boolean true if user is found
	 * @throws throws
	 *             AlfrescoGenericException
	 */
	public boolean isUserAddedToGroup(final String groupName, final String userName) throws AlfrescoGenericException {
		HttpResponse response = getGroupDetails(groupName);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			List<String> users;
			try {
				users = alfrescoHttpClient.getElementsFromJsonArray(response, "data", "shortName");
				for (String user : users) {
					if (userName.equalsIgnoreCase(user)) {
						return true;
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new AlfrescoGenericException(e.getMessage());
			}
		}
		return false;
	}

	/**
	 * Upload a single file from a location on disk.
	 *
	 * @param pathInRepo
	 *            path in repository
	 * @param pathToFile
	 *            path to file
	 * @return Document uploaded file
	 * @throws IOException
	 * @throws Exception
	 *             if error
	 */
	public Document uploadFileFromFileSystemToAlfrescoFolder(String pathInRepo, final String pathToFile)
			throws IOException {

		ContentStream contentStream = null;
		String fileExtention = null;
		File file = new File(pathToFile);
		if (!file.exists() || !file.isFile()) {
			throw new UnsupportedOperationException("Invalid Path: " + file.getPath());
		}
		fileExtention = FilenameUtils.getExtension(file.getPath());
		FileInputStream fileContent = new FileInputStream(file);
		Map<String, String> properties = new HashMap<>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
		properties.put(PropertyIds.NAME, file.getName());
		Document d;
		try {
			contentStream = getSession().getObjectFactory().createContentStream(file.getName(), file.length(),
					fileExtention, fileContent);
			if (pathInRepo == null) {
				pathInRepo = StringUtils.EMPTY;
			}
			Folder repository = (Folder) getSession()
					.getObjectByPath(getSession().getRootFolder().getPath() + "/" + pathInRepo);
			d = repository.createDocument(properties, contentStream, VersioningState.MAJOR);
			return d;
		} catch (CmisObjectNotFoundException nf) {
			logger.error(nf.getMessage());
			throw new CmisRuntimeException("Invalid path in repository " + pathInRepo, nf);
		} catch (CmisContentAlreadyExistsException ae) {
			logger.error(ae.getMessage());
			throw new CmisRuntimeException("Document already exits " + file.getName(), ae);
		} finally {
			fileContent.close();

			if (contentStream != null && contentStream.getStream() != null) {
				contentStream.getStream().close();
			}
		}
	}

	public Document uploadFileFromFyleSystemToAlfrescoFolderThroughFolderName(String folderName,
			final String pathToFile) throws IOException {

		ContentStream contentStream = null;
		String fileExtention = null;
		File file = new File(pathToFile);
		if (!file.exists() || !file.isFile()) {
			throw new UnsupportedOperationException("Invalid Path: " + file.getPath());
		}
		fileExtention = FilenameUtils.getExtension(file.getPath());
		FileInputStream fileContent = new FileInputStream(file);
		Map<String, String> properties = new HashMap<>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");

		// manage a file renaming policy to prevent odd names like
		// <filename>___<uuid>.tmp (from temporary storage file service)
		String filename = file.getName().contains(DocumentUtils.DOCUMENT_SEPARATOR)
				? file.getName().split(DocumentUtils.DOCUMENT_SEPARATOR)[0] : file.getName();
		properties.put(PropertyIds.NAME, file.getName());
		Document d;
		try {
			contentStream = getSession().getObjectFactory().createContentStream(filename, file.length(), fileExtention,
					fileContent);

			if (StringUtils.isBlank(folderName)) {
				folderName = "suap";
			}

			List<CmisObject> allFoldersWithName = getAllFoldersWithName(folderName, false);

			Folder repository = null;

			if (CollectionUtils.isNotEmpty(allFoldersWithName) && (allFoldersWithName.get(0) instanceof Folder)) {
				repository = (Folder) allFoldersWithName.get(0);
			}

			if (repository == null) {
				logger.error("Document not uploaded because folder is null");
				return null;
			}

			d = repository.createDocument(properties, contentStream, VersioningState.MAJOR);
			return d;
		} catch (CmisObjectNotFoundException nf) {
			logger.error(nf.getMessage());
			throw new CmisRuntimeException("Invalid folder name ", folderName);
		} catch (CmisContentAlreadyExistsException ae) {
			logger.error(ae.getMessage());
			throw new CmisRuntimeException("Document already exits " + file.getName(), ae);
		} finally {
			fileContent.close();

			if (contentStream != null && contentStream.getStream() != null) {
				contentStream.getStream().close();
			}
		}
	}

	public Document uploadFileFromFyleSystemToAlfrescoFolderThroughFolderId(String folderId, final String pathToFile)
			throws IOException {

		ContentStream contentStream = null;
		String fileExtention = null;
		File file = new File(pathToFile);
		if (!file.exists() || !file.isFile()) {
			throw new UnsupportedOperationException("Invalid Path: " + file.getPath());
		}
		fileExtention = FilenameUtils.getExtension(file.getPath());
		FileInputStream fileContent = new FileInputStream(file);
		Map<String, String> properties = new HashMap<>();
		properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");

		// manage a file renaming policy to prevent odd names like
		// <filename>___<uuid>.tmp (from temporary storage file service)
		String filename = file.getName().contains(DocumentUtils.DOCUMENT_SEPARATOR)
				? file.getName().split(DocumentUtils.DOCUMENT_SEPARATOR)[0] : file.getName();
		properties.put(PropertyIds.NAME, file.getName());
		Document d;
		try {
			contentStream = getSession().getObjectFactory().createContentStream(filename, file.length(), fileExtention,
					fileContent);

			if (StringUtils.isBlank(folderId)) {
				String msg = "Folder id cannot be null!";
				logger.error(msg);
				throw new AlfrescoGenericException("Unable to Save Files because of: " + msg);
			}

			Folder repository = getFolderById(folderId);

			if (repository == null) {
				logger.error("Document not uploaded because folder is null");
				return null;
			}

			d = repository.createDocument(properties, contentStream, VersioningState.MAJOR);
			return d;
		} catch (CmisObjectNotFoundException nf) {
			logger.error(nf.getMessage());
			throw new CmisRuntimeException("Invalid folder id given :", folderId);
		} catch (CmisContentAlreadyExistsException ae) {
			logger.error(ae.getMessage());
			throw new CmisRuntimeException("Document already exits " + file.getName(), ae);
		} finally {
			fileContent.close();

			if (contentStream != null && contentStream.getStream() != null) {
				contentStream.getStream().close();
			}
		}
	}

	/**
	 * Create an Alfresco user on enterprise.
	 *
	 * @param userName
	 *            String identifier new user
	 * @param password
	 *            new user password
	 * @param email
	 *            new user email
	 * @param firstName
	 *            first name
	 * @param lastName
	 *            last name
	 * @return true if successful
	 * @throws URIException
	 * @throws Exception
	 */
	public boolean createUser(final String userName, final String password, final String email, final String firstName,
			final String lastName) throws URIException {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)
				|| StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser()) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())
				|| StringUtils.isEmpty(email) || StringUtils.isEmpty(firstName) || StringUtils.isEmpty(lastName)) {
			throw new IllegalArgumentException("User detail is required");
		}

		boolean userExists = userExists(userName);

		if (userExists) {
			logger.warn("User : " + userName + " not created because is already present.");
			return false;
		}

		JSONObject body = encodeUser(userName, password, firstName, lastName, email);
		String reqURL = alfrescoHttpClient.getApiUrl() + "people";
		logger.trace("Create user using Url - " + reqURL);
		HttpPost post = new HttpPost(reqURL);
		HttpResponse response;
		try {
			response = alfrescoHttpClient.executeRequestWithEntity(alfrescoHttpClient, 
					alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					body, post);
			switch (response.getStatusLine().getStatusCode()) {
			case HttpStatus.SC_OK:
				logger.trace("User created successfully: " + userName);
				return true;
			case HttpStatus.SC_CONFLICT:
				logger.trace(USER + userName + " alreary created");
				break;
			default:
				logger.error("Unable to create user: " + response.toString());
				break;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Builds a json object representing the user data.
	 *
	 * @param userName
	 *            String identifier user identifier
	 * @param password
	 *            user password
	 * @param firstName
	 *            first name
	 * @param lastName
	 *            last name
	 * @param email
	 *            email
	 * @return {@link JSONObject} of user entity
	 */
	@SuppressWarnings("unchecked")
	private JSONObject encodeUser(final String userName, final String password, final String firstName,
			final String lastName, final String email) {
		JSONObject body = new JSONObject();
		body.put("userName", userName);
		body.put("firstName", firstName);
		body.put("lastName", lastName);
		body.put("password", password);
		body.put("email", email);
		return body;
	}

	/**
	 * Checks if user already exists.
	 *
	 * @param username
	 *            user identifier
	 * @return true if user exists
	 * @throws URIException
	 * @throws Exception
	 *             if error
	 */
	public boolean userExists(final String username) throws URIException {
		String url = alfrescoHttpClient.getApiUrl() + "people/" + URIUtil.encodeWithinPath(username);
		HttpGet get = new HttpGet(url);
		HttpResponse response;
		try {
			response = alfrescoHttpClient.executeRequest(alfrescoHttpClient, 
					alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					get);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Delete a user from enterprise.
	 *
	 * @param userName
	 *            String identifier user identifier
	 * @return true if successful
	 * @throws URIException
	 * @throws Exception
	 *             if error
	 */
	public boolean deleteUser(final String userName) throws URIException {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminUser())
				|| StringUtils.isEmpty(alfrescoConfigurator.getAlfrescoSuperadminPassword())) {
			throw new IllegalArgumentException("Null Parameters: Please correct");
		}
		String url = alfrescoHttpClient.getApiUrl() + "people/" + URIUtil.encodeWithinPath(userName);
		HttpDelete httpDelete = new HttpDelete(url);
		HttpResponse response;
		try {
			response = alfrescoHttpClient.executeRequest(alfrescoHttpClient, 
					alfrescoConfigurator.getAlfrescoSuperadminUser(),
					alfrescoConfigurator.getAlfrescoSuperadminPassword(), 
					httpDelete);
			switch (response.getStatusLine().getStatusCode()) {
			case HttpStatus.SC_OK:
				logger.trace("User deleted successfully: " + userName);
				return true;
			case HttpStatus.SC_NOT_FOUND:
				throw new RuntimeException(USER + userName + " doesn't exists");
			default:
				logger.error("Unable to delete user: " + response.toString());
				break;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AlfrescoGenericException(e.getMessage());
		}

		return false;
	}

	/**
	 * Login in alfresco share
	 *
	 * @param userName
	 *            login user name
	 * @param userPass
	 *            login user password
	 * @return true for successful user login
	 * @throws IOException
	 * @throws HttpException
	 * @throws Exception
	 *             if error
	 */
	public HttpState loginUser(final String userName, final String userPass) throws HttpException, IOException {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPass)) {
			throw new IllegalArgumentException(PARAMETER_MISSING);
		}
		HttpState state = null;
		org.apache.commons.httpclient.HttpClient theClient = new org.apache.commons.httpclient.HttpClient();
		String reqURL = alfrescoHttpClient.getBaseUrl() + "share/page/dologin";
		org.apache.commons.httpclient.methods.PostMethod post = new org.apache.commons.httpclient.methods.PostMethod(
				reqURL);
		NameValuePair[] formParams;
		CookieStore cookieStore = new BasicCookieStore();
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setCookieStore(cookieStore);
		formParams = new NameValuePair[] { new NameValuePair("username", userName),
				new NameValuePair("password", userPass),
				new NameValuePair(DocumentCostants.SUCCESS, "/share/page/user/" + userName + "/dashboard"),
				new NameValuePair("failure", "/share/page/type/login?error=true") };
		post.setRequestBody(formParams);
		int postStatus = theClient.executeMethod(post);
		if (302 == postStatus) {
			state = theClient.getState();
			post.releaseConnection();
			org.apache.commons.httpclient.methods.GetMethod get = new org.apache.commons.httpclient.methods.GetMethod(
					alfrescoHttpClient.getBaseUrl() + "share/page/user/" + userName + "/dashboard");
			theClient.setState(state);
			theClient.executeMethod(get);
			get.releaseConnection();
		}
		return state;
	}

	/**
	 * Logout the current user from share.
	 *
	 * @return HttpState
	 * @throws IOException
	 * @throws HttpException
	 * @throws Exception
	 *             if error
	 */
	public HttpState logoutUser() throws HttpException, IOException {
		HttpState state;
		org.apache.commons.httpclient.HttpClient theClient = new org.apache.commons.httpclient.HttpClient();
		String reqURL = alfrescoHttpClient.getBaseUrl() + "share/page/dologout";
		org.apache.commons.httpclient.methods.PostMethod post = new org.apache.commons.httpclient.methods.PostMethod(
				reqURL);
		theClient.executeMethod(post);
		state = theClient.getState();
		return state;
	}

	/**
	 * Executes the query string provided and returns the results as a list of
	 * CmisObjects.
	 *
	 * @param queryString
	 * @param searchAllVersions
	 * @return {@link List<CmisObject>}
	 */
	private List<CmisObject> getQueryResults(String queryString, boolean searchAllVersions) {

		Session currSession = getSession();
		List<CmisObject> objList = new ArrayList<>();

		// execute query
		ItemIterable<QueryResult> results = currSession.query(queryString, searchAllVersions)
				.getPage(Integer.MAX_VALUE);

		if (results != null) {
			for (QueryResult qResult : results) {
				String objectId;
				PropertyData<?> propData = qResult.getPropertyById(CMIS_OBJECT_ID); // Atom
																					// binding
				if (propData != null) {
					objectId = (String) propData.getFirstValue();
				} else {
					objectId = qResult.getPropertyValueByQueryName("d.cmis:objectId"); // Web
																						// Services
																						// binding
				}
				CmisObject obj = currSession.getObject(currSession.createObjectId(objectId));
				objList.add(obj);
			}
		}

		return objList;
	}

	/**
	 * Executes queryString1 to retrieve a set of objectIDs which are then used
	 * in an IN predicate appended to the second query. Assumes the second query
	 * defines an alias called "d", as in "select d.cmis:objectId from
	 * cmis:document as d"
	 *
	 * @param queryString1
	 * @param queryString2
	 * @param searchAllVersions
	 * @return {@link List<CmisObject>}
	 */
	private List<CmisObject> getSubQueryResults(String queryString1, String queryString2, boolean searchAllVersions) {
		List<CmisObject> objList = new ArrayList<>();

		// execute first query
		ItemIterable<QueryResult> results = getSession().query(queryString1, searchAllVersions);
		List<String> objIdList = new ArrayList<>();

		if (results != null) {
			String objectId = null;
			for (QueryResult qResult : results) {
				objectId = StringUtils.EMPTY;
				PropertyData<?> propData = qResult.getPropertyById(CMIS_OBJECT_ID); // Atom
																					// Binding
				if (propData != null) {
					objectId = (String) propData.getFirstValue();
				} else {
					objectId = qResult.getPropertyValueByQueryName("d.cmis:objectId"); // Web
																						// Services
																						// Binding
				}
				objIdList.add(objectId);
			}
		}

		if (objIdList.isEmpty()) {
			return objList;
		}

		String queryString = queryString2 + " AND d.cmis:objectId IN " + getPredicate(objIdList);
		return getQueryResults(queryString, false);
	}

	/**
	 * Returns a comma-separated list of object IDs provided as a List.
	 *
	 * @param objectIdList
	 *            The list of object id
	 * @return String The predicate
	 */
	private String getPredicate(List<String> objectIdList) {
		StringBuilder sb = new StringBuilder("(");

		if (objectIdList != null) {
			for (int i = 0; i < objectIdList.size(); i++) {
				sb.append("'");
				sb.append(objectIdList.get(i));
				sb.append("'");
				if (i >= objectIdList.size() - 1) {
					break;
				}
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Get a CMIS Object by name from a specified folder.
	 *
	 * @param parentFolder
	 *            the parent folder where the object might exist
	 * @param objectName
	 *            the name of the object that we are looking for
	 * @return the Cmis Object if it existed, otherwise null
	 */
	private CmisObject getObject(Session session, Folder parentFolder, String objectName) {
		CmisObject object = null;

		try {
			String path2Object = parentFolder.getPath();
			if (!path2Object.endsWith("/")) {
				path2Object += "/";
			}
			path2Object += objectName;
			object = session.getObjectByPath(path2Object);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			String errorMessage = "Nothing to do, object : " + objectName + "does not exist";
			logger.error(errorMessage);
			throw new AlfrescoGenericException(errorMessage);
		}

		return object;
	}

	public Folder getFolderByParentAndFolderName(Folder parent, String folderName, boolean forceCreation) {
		Folder returnFolder = null;
		ItemIterable<CmisObject> children = parent.getChildren();
		for (CmisObject folder : children) {
			if (folder instanceof Folder) {
				Folder current = (Folder) folder;
				if (current.getName().equals(folderName)) {
					returnFolder = current;
					break;
				}
			}
		}
		if (returnFolder == null && forceCreation) {
			returnFolder = createFolder(parent, folderName);
		}

		return returnFolder;
	}

}
