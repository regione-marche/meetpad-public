package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.PageableDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="CompanySearch")
public class ImpresaRicerca extends PageableDTO{
	
	@JsonProperty(value = "value")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
