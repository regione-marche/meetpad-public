package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateInstanceConferenceTimeDTO")
public class ConferenceTemplateInstanceConferenceTimeDTO {

	@JsonProperty("visible")
	private Boolean visible;

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

}
