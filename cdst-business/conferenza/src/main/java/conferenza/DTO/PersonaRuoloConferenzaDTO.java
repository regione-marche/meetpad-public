package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "PersonConferenceRole")
public class PersonaRuoloConferenzaDTO extends PersonaDTO {

	@JsonProperty("profile")
	private LabelValue profilo;
	
	@JsonProperty("principalApplicant")
	private Boolean flagRichPrincipale;

	public LabelValue getProfilo() {
		return profilo;
	}

	public void setProfilo(LabelValue profilo) {
		this.profilo = profilo;
	}

	public Boolean getFlagRichPrincipale() {
		return flagRichPrincipale;
	}

	public void setFlagRichPrincipale(Boolean flagRichPrincipale) {
		this.flagRichPrincipale = flagRichPrincipale;
	} 

}
