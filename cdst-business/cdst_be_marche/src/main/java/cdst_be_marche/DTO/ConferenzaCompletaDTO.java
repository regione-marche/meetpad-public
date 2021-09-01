package cdst_be_marche.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="ConferenceComplete")
public class ConferenzaCompletaDTO extends ConferenzaDTO {

	@JsonProperty("messages")
	private List<MessaggioDTO> messaggi = new ArrayList<>();
	
	@JsonProperty("events")
	private List<EventoDTO> eventi = new ArrayList<>();

	public List<MessaggioDTO> getMessaggi() {
		return messaggi;
	}

	public void setMessaggi(List<MessaggioDTO> messaggi) {
		this.messaggi = messaggi;
	}

	public List<EventoDTO> getEventi() {
		return eventi;
	}

	public void setEventi(List<EventoDTO> eventi) {
		this.eventi = eventi;
	}

}
