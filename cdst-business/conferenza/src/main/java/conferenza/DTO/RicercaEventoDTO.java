package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RicercaEventoDTO extends PageableDTO {
	
	@JsonProperty("eventType")
	private String tipoEvento;
	
	@JsonProperty("participantRole")
	private String ruoloPartecipante;
	
	@JsonProperty("idAuthority")
	private String ente;

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public void setEventType(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getRuoloPartecipante() {
		return ruoloPartecipante;
	}

	public void setRuoloPartecipante(String ruoloPartecipante) {
		this.ruoloPartecipante = ruoloPartecipante;
	}
	
	public void setParticipantRole(String ruoloPartecipante) {
		this.ruoloPartecipante = ruoloPartecipante;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	public void setIdAuthority(String ente) {
		this.ente = ente;
	}

}
