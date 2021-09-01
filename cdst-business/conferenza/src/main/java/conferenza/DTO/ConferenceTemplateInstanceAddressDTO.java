package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * "type": "1|2", // 1= online, 2= fisica,
 * "disabled" // true=modifica bloccata
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateInstanceAddressDTO")
public class ConferenceTemplateInstanceAddressDTO {

	@JsonProperty("type")
	private String type;

	@JsonProperty("disabled")
	private Boolean disabled;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

}
