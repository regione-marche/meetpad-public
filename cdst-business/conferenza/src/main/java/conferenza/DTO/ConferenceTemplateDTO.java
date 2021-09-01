package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * Dati che dipendono dal tipo conferenza specializzazione scelto
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateDTO")
public class ConferenceTemplateDTO {
	
	@JsonProperty("procedure")
	private ConferenceTemplateProcedureDTO conferenceTemplateProcedureDTO;
	
	@JsonProperty("definition")
	private ConferenceTemplateDefinitionDTO conferenceTemplateDefinitionDTO;
	
	@JsonProperty("participant")
	private ConferenceTemplateParticipantDTO conferenceTemplateParticipantDTO;

	public ConferenceTemplateProcedureDTO getConferenceTemplateProcedureDTO() {
		return conferenceTemplateProcedureDTO;
	}

	public void setConferenceTemplateProcedureDTO(ConferenceTemplateProcedureDTO conferenceTemplateProcedureDTO) {
		this.conferenceTemplateProcedureDTO = conferenceTemplateProcedureDTO;
	}

	public ConferenceTemplateDefinitionDTO getConferenceTemplateDefinitionDTO() {
		return conferenceTemplateDefinitionDTO;
	}

	public void setConferenceTemplateDefinitionDTO(ConferenceTemplateDefinitionDTO conferenceTemplateDefinitionDTO) {
		this.conferenceTemplateDefinitionDTO = conferenceTemplateDefinitionDTO;
	}

	public ConferenceTemplateParticipantDTO getConferenceTemplateParticipantDTO() {
		return conferenceTemplateParticipantDTO;
	}

	public void setConferenceTemplateParticipantDTO(ConferenceTemplateParticipantDTO conferenceTemplateParticipantDTO) {
		this.conferenceTemplateParticipantDTO = conferenceTemplateParticipantDTO;
	}
	

}
