package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveParticipantPreview")
public class PrecaricamentoPartecipantePreviewDTO extends IdentifiableDTO {
	
	@JsonProperty("conferenceType")
	private LabelValue tipologiaConferenza;
	
	@JsonProperty("authority")
	private LabelValue ente;
	
	@JsonProperty("email")
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
