package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "AccreditamentPreview")
public class AccreditamentoDTO extends PersonaRuoloConferenzaDTO {

	@JsonProperty("accreditamentFlag")
	private Boolean flagAccreditato;

	@JsonProperty("inviteFlag")
	private Boolean flagInvitato;
	
	@JsonProperty("rejectedFlag")
	private Boolean flagRifiuto;
	
	@JsonProperty("rejectedDescription")
	private String descrizioneRifiuto;

	@JsonProperty("idConference")
	private Integer idConferenza;

	@JsonProperty("idParticipant")
	private Integer idPartecipante;
	
	@JsonProperty("participantDescription")
	private String descrizionePartecipante;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	public Boolean getFlagAccreditato() {
		return flagAccreditato;
	}

	public void setFlagAccreditato(Boolean flagAccreditato) {
		this.flagAccreditato = flagAccreditato;
	}

	public Boolean getFlagInvitato() {
		return flagInvitato;
	}

	public void setFlagInvitato(Boolean flagInvitato) {
		this.flagInvitato = flagInvitato;
	}

	public Boolean getFlagRifiuto() {
		return flagRifiuto;
	}

	public void setFlagRifiuto(Boolean flagRifiuto) {
		this.flagRifiuto = flagRifiuto;
	}

	public String getDescrizioneRifiuto() {
		return descrizioneRifiuto;
	}

	public void setDescrizioneRifiuto(String descrizioneRifiuto) {
		this.descrizioneRifiuto = descrizioneRifiuto;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public Integer getIdPartecipante() {
		return idPartecipante;
	}

	public void setIdPartecipante(Integer idPartecipante) {
		this.idPartecipante = idPartecipante;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescrizionePartecipante() {
		return descrizionePartecipante;
	}

	public void setDescrizionePartecipante(String descrizionePartecipante) {
		this.descrizionePartecipante = descrizionePartecipante;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
