package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 *{
 *	"document": {
 *		"key": "token",
 *		"fileType": "docx",
 *		"title": "nomedocumento",
 *		"url": "lsUrl"
 *	},
 *	"permissions": {
 *		"edit": "true",
 *		"download": "true",
 *		"review": "edit"
 *	},
 *	"documentType": "text",
 *	"editorConfig": {
 *		"callbackUrl": "getOOCallbackUrl()+idAdapter+codiceFiscale+jwt+",
 *		"user": {
 *			"id": "useridoo",
 *			"name": "codiceFiscale+@oo.com"
 *		}
 *	}
 *}
 * </pre>
 * 
 * @author arosina
 *
 */
public class OODocumentDTO {
	@JsonProperty("document")
	private OODocumentInfoDTO document;

	@JsonProperty("permissions")
	private OODocumentPermissionsDTO permissions;

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("editorConfig")
	private OODocumentEditorConfigDTO editorConfig;

	public OODocumentInfoDTO getDocument() {
		return document;
	}

	public void setDocument(OODocumentInfoDTO document) {
		this.document = document;
	}

	public OODocumentPermissionsDTO getPermissions() {
		return permissions;
	}

	public void setPermissions(OODocumentPermissionsDTO permissions) {
		this.permissions = permissions;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public OODocumentEditorConfigDTO getEditorConfig() {
		return editorConfig;
	}

	public void setEditorConfig(OODocumentEditorConfigDTO editorConfig) {
		this.editorConfig = editorConfig;
	}

}
