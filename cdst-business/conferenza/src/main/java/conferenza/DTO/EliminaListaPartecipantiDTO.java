package conferenza.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="DeleteParticipantList")
public class EliminaListaPartecipantiDTO {
	
	@JsonProperty("idParticipantList")
	List<Integer> listaIdPartecipanti;

	public List<Integer> getListaIdPartecipanti() {
		return listaIdPartecipanti;
	}

	public void setListaIdPartecipanti(List<Integer> listaIdPartecipanti) {
		this.listaIdPartecipanti = listaIdPartecipanti;
	}
	
	

}
