package conferenza.consolleAmministrativa.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="PreviewProtocol")
public class ProtocolloPreviewDTO {
	@JsonProperty("idProtocollo")
	private Integer idProtocollo;
	
	@JsonProperty("error")
	private String error;
	
	@JsonProperty("documentName")
	private String documentName;

	@JsonProperty("protocolNumber")
	private String protocolNumber;
	
	@JsonProperty("protocolDate")
	private Date protocolDate;
	
	@JsonProperty("protocolState")
	private String protocolState;
	
	@JsonProperty("idDocNumber")
	private Integer idDocNumber;
	
	@JsonProperty("idConference")
	private Integer idConferenza;
	
	@JsonProperty("event")
	private String evento;

	public Integer getIdDocNumber() {
		return idDocNumber;
	}

	public void setIdDocNumber(Integer idDocNumber) {
		this.idDocNumber = idDocNumber;
	}

	public Integer getIdProtocollo() {
		return idProtocollo;
	}

	public void setIdProtocollo(Integer idProtocollo) {
		this.idProtocollo = idProtocollo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Date getProtocolDate() {
		return protocolDate;
	}

	public void setProtocolDate(Date protocolDate) {
		this.protocolDate = protocolDate;
	}

	public String getProtocolState() {
		return protocolState;
	}

	public void setProtocolState(String protocolState) {
		this.protocolState = protocolState;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

}
