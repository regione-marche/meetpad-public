package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * Calcolo delle date in base alla tipologia di conferenza specializzazione,
 * visibilità orario, definizione modalità incontro
 * 
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateInstanceDTO")
public class ConferenceTemplateInstanceDTO {

	@JsonProperty("endIntegrationDate")
	private ConferenceTemplateInstanceDateDTO endIntegrationDate;

	@JsonProperty("endOpinionDate")
	private ConferenceTemplateInstanceDateDTO endOpinionDate;

	@JsonProperty("firstSessionDate")
	private ConferenceTemplateInstanceDateDTO firstSessionDate;

	@JsonProperty("expirationDate")
	private ConferenceTemplateInstanceDateDTO expirationDate;

	@JsonProperty("conferenceTime")
	private ConferenceTemplateInstanceConferenceTimeDTO conferenceTime;

	@JsonProperty("address")
	private ConferenceTemplateInstanceAddressDTO address;

	public ConferenceTemplateInstanceDateDTO getEndIntegrationDate() {
		return endIntegrationDate;
	}

	public void setEndIntegrationDate(ConferenceTemplateInstanceDateDTO endIntegrationDate) {
		this.endIntegrationDate = endIntegrationDate;
	}

	public ConferenceTemplateInstanceDateDTO getEndOpinionDate() {
		return endOpinionDate;
	}

	public void setEndOpinionDate(ConferenceTemplateInstanceDateDTO endOpinionDate) {
		this.endOpinionDate = endOpinionDate;
	}

	public ConferenceTemplateInstanceDateDTO getFirstSessionDate() {
		return firstSessionDate;
	}

	public void setFirstSessionDate(ConferenceTemplateInstanceDateDTO firstSessionDate) {
		this.firstSessionDate = firstSessionDate;
	}

	public ConferenceTemplateInstanceDateDTO getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(ConferenceTemplateInstanceDateDTO expirationDate) {
		this.expirationDate = expirationDate;
	}

	public ConferenceTemplateInstanceConferenceTimeDTO getConferenceTime() {
		return conferenceTime;
	}

	public void setConferenceTime(ConferenceTemplateInstanceConferenceTimeDTO conferenceTime) {
		this.conferenceTime = conferenceTime;
	}

	public ConferenceTemplateInstanceAddressDTO getAddress() {
		return address;
	}

	public void setAddress(ConferenceTemplateInstanceAddressDTO address) {
		this.address = address;
	}

}
