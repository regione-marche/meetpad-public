package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "PersonConferenceRole")
public class PersonaRuoloConferenzaDTO extends PersonaDTO {

	@JsonProperty("profile")
	private LabelValue profilo;

	public LabelValue getProfilo() {
		return profilo;
	}

	public void setProfilo(LabelValue profilo) {
		this.profilo = profilo;
	}

}
