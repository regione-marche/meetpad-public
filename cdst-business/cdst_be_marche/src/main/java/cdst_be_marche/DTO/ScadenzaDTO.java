package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel("DeadlineDTO")
public class ScadenzaDTO {
	
	@JsonProperty("conferenceId")
	private Integer idConferenza;
	
	@JsonProperty("conferenceName")
	private String nomeConferenza;
	
	@JsonProperty("eventDate")
	private String dataEventoCalendario;
	
	@JsonProperty("eventType")
	private LabelValue eventoCalendario;
	
	public ScadenzaDTO() {
		super();
	}

	public ScadenzaDTO(Integer idConferenza, String nomeConferenza, String dataEventoCalendario,
			LabelValue eventoCalendario) {
		super();
		this.idConferenza = idConferenza;
		this.nomeConferenza = nomeConferenza;
		this.dataEventoCalendario = dataEventoCalendario;
		this.eventoCalendario = eventoCalendario;
	}
	public Integer getIdConferenza() {
		return idConferenza;
	}
	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}
	public String getNomeConferenza() {
		return nomeConferenza;
	}
	public void setNomeConferenza(String nomeConferenza) {
		this.nomeConferenza = nomeConferenza;
	}
	public String getDataEventoCalendario() {
		return dataEventoCalendario;
	}
	public void setDataEventoCalendario(String dataEventoCalendario) {
		this.dataEventoCalendario = dataEventoCalendario;
	}
	public LabelValue getEventoCalendario() {
		return eventoCalendario;
	}
	public void setEventoCalendario(LabelValue eventoCalendario) {
		this.eventoCalendario = eventoCalendario;
	}
	
}
