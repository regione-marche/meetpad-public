package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.AlfrescoDocumentAdapterDTO;

@Entity
public class AlfrescoDocumentAdapter implements Serializable {

	private static final long serialVersionUID = -8441716982654597533L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="ID")
	private Integer id;

	
	@Column(name ="DOCUMENTID")
	private String documentId;

	
	@Column(name ="NAME")
	private String name;
	
	@Column(name ="ALFRESCOID")
	private String alfrescoId;
	/**
	 * 
	 */
	@Column(name ="ALFRESCOPATH")
	private String alfrescoPath;
	
	@Column(name ="NOTE")
	private String note;

	
	@Column(name ="ALFRESCOPATHID")
	private String alfrescoPathId;

	@Column(name ="ID_PRATICA_EXT")
	private String idpraticaExt;	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
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

	public static AlfrescoDocumentAdapter fillAfrescoDocumentAdapter(AlfrescoDocumentAdapterDTO alfrescoDTO) {
		AlfrescoDocumentAdapter adapter = new AlfrescoDocumentAdapter();
		adapter.setId(alfrescoDTO.getId());
		adapter.setAlfrescoId(alfrescoDTO.getAlfrescoId());
		adapter.setAlfrescoPath(alfrescoDTO.getAlfrescoPath());
		adapter.setDocumentId(alfrescoDTO.getDocumentId());
		adapter.setName(alfrescoDTO.getName());
		adapter.setNote(alfrescoDTO.getNote());
		
		adapter.setIdpraticaExt(alfrescoDTO.getIdpraticaExt());
		adapter.setAlfrescoPathId(alfrescoDTO.getAlfrescoPathId());
		
		return adapter;
	}

}
