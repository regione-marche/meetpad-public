package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ShortAccreditament")
public class AccreditamentoAnteprimaDTO extends IdentifiableDTO {
	
	@JsonProperty("accreditamentFlag")
	private Boolean flagAccreditato;

	public Boolean getFlagAccreditato() {
		return flagAccreditato;
	}

	public void setFlagAccreditato(Boolean flagAccreditato) {
		this.flagAccreditato = flagAccreditato;
	}

}
