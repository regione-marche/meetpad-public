package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * <pre>
 *   "offsetBusinessDay": "integer", --> giorni lavorativi da sommare
 *   "offsetDay": "integer", --> giorni solari da sommare
 *   "baseDate": "creationDate|endOpinionDate|endIntegrationDate|firstSessionDate", --> data da cui sommare
 *   "required": "boolean",
 *   "order": "integer" --> ordine di calcolo delle date (se d1 dipende da d2 --> d2.order < d1.order)
 * </pre>
 * 
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateInstanceDateDTO")
public class ConferenceTemplateInstanceDateDTO {

	@JsonProperty("offsetBusinessDay")
	private Integer offsetBusinessDay;

	@JsonProperty("offsetDay")
	private Integer offsetDay;

	@JsonProperty("baseDate")
	private String baseDate;

	@JsonProperty("required")
	private Boolean required;
	
	@JsonProperty("order")
	private Integer order;
	
	public Integer getOffsetBusinessDay() {
		return offsetBusinessDay;
	}

	public void setOffsetBusinessDay(Integer offsetBusinessDay) {
		this.offsetBusinessDay = offsetBusinessDay;
	}

	public Integer getOffsetDay() {
		return offsetDay;
	}

	public void setOffsetDay(Integer offsetDay) {
		this.offsetDay = offsetDay;
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
