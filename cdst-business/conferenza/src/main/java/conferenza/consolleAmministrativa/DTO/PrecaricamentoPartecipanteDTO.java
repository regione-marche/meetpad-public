package conferenza.consolleAmministrativa.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveParticipant")
public class PrecaricamentoPartecipanteDTO extends IdentifiableDTO {
	
	@JsonProperty("conferenceType")
	private LabelValue tipologiaConferenza;
	
	@JsonProperty("authority")
	private LabelValue ente;
	
	@JsonProperty("participantRole")
	private LabelValue ruoloPartecipante;
	
	@JsonProperty("competenceAuthorization")
	private List<LabelValue> competenzaAutorizzativa = new ArrayList<>();

	public LabelValue getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(LabelValue tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public LabelValue getEnte() {
		return ente;
	}

	public void setEnte(LabelValue ente) {
		this.ente = ente;
	}

	public LabelValue getRuoloPartecipante() {
		return ruoloPartecipante;
	}

	public void setRuoloPartecipante(LabelValue ruoloPartecipante) {
		this.ruoloPartecipante = ruoloPartecipante;
	}

	public List<LabelValue> getCompetenzaAutorizzativa() {
		return competenzaAutorizzativa;
	}

	public void setCompetenzaAutorizzativa(List<LabelValue> competenzaAutorizzativa) {
		this.competenzaAutorizzativa = competenzaAutorizzativa;
	}

}
