package conferenza.documentazionecondivisa.DTO.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
"folderId": 7,
"version": 2,
"versionGroup": 1,
"contentLength": "770.1 KB",
"pureContentLength": 788586,
"fileStatus": 0,
"viewUrl": "http://mydocker.org:8082/products/files/httphandlers/filehandler.ashx?action=download&fileid=18",
"webUrl": "http://mydocker.org:8082/products/files/doceditor.aspx?fileid=18",
"fileType": 7,
"fileExst": ".pdf",
"comment": "Uploaded",
"id": 18,
"title": "authInstructions.pdf",
"access": 0,
"shared": false,
"rootFolderType": 5,
"updatedBy":{"id": "9503bed9-2828-4daf-ae7b-dac9a0dc76d7", "displayName": "Guido De Luca", "avatarSmall": "http://mydocker.org:8082/skins/default/images/default_user_photo_size_200-200.png",…},
"created": "2019-02-05T09:31:03.0000000Z",
"createdBy":{"id": "9503bed9-2828-4daf-ae7b-dac9a0dc76d7", "displayName": "Guido De Luca", "avatarSmall": "http://mydocker.org:8082/skins/default/images/default_user_photo_size_200-200.png",…},
"updated": "2019-02-05T09:38:28.0000000Z",
"providerItem": false
 * @author guideluc
 *
 */
public class OOFileResponseODT {

	@JsonProperty("folderId")
	Integer folderId; //": 7,
	@JsonProperty("version")
	Integer version; //": 2,
	@JsonProperty("versionGroup")
	Integer versionGroup; //": 1,
	@JsonProperty("contentLength")
	String contentLength; //": "770.1 KB",
	@JsonProperty("pureContentLength")
	Integer pureContentLength; //": 788586,
	@JsonProperty("fileStatus")
	Integer fileStatus; //": 0,
	@JsonProperty("viewUrl")
	String  viewUrl; //": "http://mydocker.org:8082/products/files/httphandlers/filehandler.ashx?action=download&fileid=18",
	@JsonProperty("webUrl")
	String  webUrl; //": "http://mydocker.org:8082/products/files/doceditor.aspx?fileid=18",
	@JsonProperty("fileType")
	Integer fileType; //": 7,
	@JsonProperty("fileExst")
	String fileExst; //": ".pdf",
	@JsonProperty("comment")
	String comment; //": "Uploaded",
	@JsonProperty("id")
	Integer id; //": 18,
	@JsonProperty("title")
	String title; //": "authInstructions.pdf",
	@JsonProperty("access")
	Integer access; //": 0,
	@JsonProperty("shared")
	Boolean shared; //": false,
	@JsonProperty("rootFolderType")
	Integer rootFolderType; //": 5,
	@JsonProperty("updatedBy")
	OOUserResponse updatedBy; //":{"id": "9503bed9-2828-4daf-ae7b-dac9a0dc76d7", "displayName": "Guido De Luca", "avatarSmall": "http://mydocker.org:8082/skins/default/images/default_user_photo_size_200-200.png",…},
	@JsonProperty("created")
	String created; //": "2019-02-05T09:31:03.0000000Z",
	@JsonProperty("createdBy")
	OOUserResponse createdBy; //":{"id": "9503bed9-2828-4daf-ae7b-dac9a0dc76d7", "displayName": "Guido De Luca", "avatarSmall": "http://mydocker.org:8082/skins/default/images/default_user_photo_size_200-200.png",…},
	@JsonProperty("updated")
	String updated; //": "2019-02-05T09:38:28.0000000Z",
	@JsonProperty("providerItem")
	Boolean providerItem; //": false
	public Integer getFolderId() {
		return folderId;
	}
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getVersionGroup() {
		return versionGroup;
	}
	public void setVersionGroup(Integer versionGroup) {
		this.versionGroup = versionGroup;
	}
	public String getContentLength() {
		return contentLength;
	}
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}
	public Integer getPureContentLength() {
		return pureContentLength;
	}
	public void setPureContentLength(Integer pureContentLength) {
		this.pureContentLength = pureContentLength;
	}
	public Integer getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(Integer fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public String getFileExst() {
		return fileExst;
	}
	public void setFileExst(String fileExst) {
		this.fileExst = fileExst;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getAccess() {
		return access;
	}
	public void setAccess(Integer access) {
		this.access = access;
	}
	public Boolean getShared() {
		return shared;
	}
	public void setShared(Boolean shared) {
		this.shared = shared;
	}
	public Integer getRootFolderType() {
		return rootFolderType;
	}
	public void setRootFolderType(Integer rootFolderType) {
		this.rootFolderType = rootFolderType;
	}
	public OOUserResponse getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(OOUserResponse updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public OOUserResponse getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(OOUserResponse createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public Boolean getProviderItem() {
		return providerItem;
	}
	public void setProviderItem(Boolean providerItem) {
		this.providerItem = providerItem;
	}
	
	
}
