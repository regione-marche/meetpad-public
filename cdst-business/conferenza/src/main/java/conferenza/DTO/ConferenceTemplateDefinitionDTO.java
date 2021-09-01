package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * Definizione della conferenza
 * 
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateDefinitionDTO")
public class ConferenceTemplateDefinitionDTO {

	@JsonProperty("instance")
	private ConferenceTemplateInstanceDTO conferenceTemplateInstanceDTO;

	public ConferenceTemplateInstanceDTO getConferenceTemplateInstanceDTO() {
		return conferenceTemplateInstanceDTO;
	}

	public void setConferenceTemplateInstanceDTO(ConferenceTemplateInstanceDTO conferenceTemplateInstanceDTO) {
		this.conferenceTemplateInstanceDTO = conferenceTemplateInstanceDTO;
	}

}
