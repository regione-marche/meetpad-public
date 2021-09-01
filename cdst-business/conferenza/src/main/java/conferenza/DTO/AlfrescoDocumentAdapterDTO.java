package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author guideluc
 *
 * id           -- id della tabella AlfrescoDocumentAdapter
 * documentId   -- FK della tabella DocumentAdapter
 * alfrescoId   -- id di Alfresco 
 * alfrescoPath -- Path relativo del File su Alfresco
 */
public class AlfrescoDocumentAdapterDTO extends DocumentAdapterDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8441716982654597566L;
	
	@JsonProperty("id")
	private Integer id;

	
	@JsonProperty("name")
	private String name;
	@JsonProperty("alfrescoId")
	private String alfrescoId;
	/**
	 * 
	 */
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("alfrescoPath")
	private String alfrescoPath;		
	
	@JsonProperty("note")
	private String note;
	
	@JsonProperty("alfrescoPathId")
	private String alfrescoPathId;

	@JsonProperty("idPraticaExt")
	private String idpraticaExt;	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlfrescoId() {
		return alfrescoId;
	}
	public void setAlfrescoId(String alfrescoId) {
		this.alfrescoId = alfrescoId;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getAlfrescoPath() {
		return alfrescoPath;
	}
	public void setAlfrescoPath(String alfrescoPath) {
		this.alfrescoPath = alfrescoPath;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAlfrescoPathId() {
		return alfrescoPathId;
	}
	public void setAlfrescoPathId(String alfrescoPathId) {
		this.alfrescoPathId = alfrescoPathId;
	}
	public String getIdpraticaExt() {
		return idpraticaExt;
	}
	public void setIdpraticaExt(String idpraticaExt) {
		this.idpraticaExt = idpraticaExt;
	}
	
	
	
	
	
}
