package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "AccreditamentInfo")
public class AccreditamentoInfoDTO {

	@JsonProperty("autoAccreditation")
	private Boolean flagAutoAccreditamento;

	@JsonProperty("idConference")
	private Integer idConferenza;

	@JsonProperty("accreditation")
	private AccreditamentoDTO accreditamento;

	public Boolean getFlagAutoAccreditamento() {
		return flagAutoAccreditamento;
	}

	public void setFlagAutoAccreditamento(Boolean flagAutoAccreditamento) {
		this.flagAutoAccreditamento = flagAutoAccreditamento;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public AccreditamentoDTO getAccreditamento() {
		return accreditamento;
	}

	public void setAccreditamento(AccreditamentoDTO accreditamento) {
		this.accreditamento = accreditamento;
	}

}
