package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ConferenceApplicantEditor")
public class ConferenzaModificaRichiedenteDTO {

	@JsonProperty("conferenceEditable")
	private Boolean conferenzaEditabile;
	
	@JsonProperty("enabled")
	private Boolean modificaAbilitata;
	
	@JsonProperty("stepList")
	private List<Integer> stepAttivi = new ArrayList<>();

	public Boolean getModificaAbilitata() {
		return modificaAbilitata;
	}

	public void setModificaAbilitata(Boolean modificaAbilitata) {
		this.modificaAbilitata = modificaAbilitata;
	}

	public List<Integer> getStepAttivi() {
		return stepAttivi;
	}

	public void setStepAttivi(List<Integer> stepAttivi) {
		this.stepAttivi = stepAttivi;
	}

	public Boolean getConferenzaEditabile() {
		return conferenzaEditabile;
	}

	public void setConferenzaEditabile(Boolean conferenzaEditabile) {
		this.conferenzaEditabile = conferenzaEditabile;
	}
	
}
