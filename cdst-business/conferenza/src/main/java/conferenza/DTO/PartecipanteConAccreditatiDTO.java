package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="ParticipantWithAccreditations")
public class PartecipanteConAccreditatiDTO {
	
	@JsonProperty("participant")
	private LabelValue partecipante;
	
	@JsonProperty("accreditations")
	private List<LabelValue> listaAccreditamenti = new ArrayList<>();

	public LabelValue getPartecipante() {
		return partecipante;
	}

	public void setPartecipante(LabelValue partecipante) {
		this.partecipante = partecipante;
	}

	public List<LabelValue> getListaAccreditamenti() {
		return listaAccreditamenti;
	}

	public void setListaAccreditamenti(List<LabelValue> listaAccreditamenti) {
		this.listaAccreditamenti = listaAccreditamenti;
	}

}
