package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.PageableDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveCompanySearch")
public class PrecaricamentoImpresaRicerca extends PageableDTO{
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("conferenceType")
	private String tipologiaConferenza;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(String tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}
	
	public void setConferenceType(String tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

}
