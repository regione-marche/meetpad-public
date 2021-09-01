package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Vote")
public class VotoDTO {
	
	@JsonProperty("participant")
	private LabelValue partecipante;
	
	@JsonProperty("vote")
	private Boolean voto;
	
	@JsonProperty("date")
	private String dataVotazione;
	
	@JsonProperty("detail")
	private String motivazione;
	
	public LabelValue getPartecipante() {
		return partecipante;
	}

	public void setPartecipante(LabelValue partecipante) {
		this.partecipante = partecipante;
	}

	public Boolean getVoto() {
		return voto;
	}

	public void setVoto(Boolean voto) {
		this.voto = voto;
	}

	public String getDataVotazione() {
		return dataVotazione;
	}

	public void setDataVotazione(String dataVotazione) {
		this.dataVotazione = dataVotazione;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

}
