package cdst_be_marche.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Participant")
public class PartecipanteDTO extends IdentifiableDTO {

	@JsonProperty("authority")
	private LabelValue ente;

	@JsonProperty("role")
	private LabelValue ruolo;

	@JsonProperty("pec")
	private String pec;

	@JsonProperty("fiscalCode")
	private String cf;

	@JsonProperty("description")
	private String descEnte;

	@JsonProperty("competence")
	private String competenza;

	@JsonProperty("determination")
	private Boolean esprimeParere;

	@JsonProperty("users")
	List<PersonaRuoloConferenzaDTO> listaUtente = new ArrayList<>();

	@JsonProperty("emails")
	List<String> altreEmail = new ArrayList<>();

	public LabelValue getEnte() {
		return ente;
	}

	public void setEnte(LabelValue ente) {
		this.ente = ente;
	}

	public LabelValue getRuolo() {
		return ruolo;
	}

	public void setRuolo(LabelValue ruolo) {
		this.ruolo = ruolo;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getDescEnte() {
		return descEnte;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}

	public Boolean isEsprimeParere() {
		return esprimeParere;
	}

	public void setEsprimeParere(Boolean esprimeParere) {
		this.esprimeParere = esprimeParere;
	}

	public List<PersonaRuoloConferenzaDTO> getListaUtente() {
		return listaUtente;
	}

	public void setListaUtente(List<PersonaRuoloConferenzaDTO> listaUtente) {
		this.listaUtente = listaUtente;
	}

	public List<String> getAltreEmail() {
		return altreEmail;
	}

	public void setAltreEmail(List<String> altreEmail) {
		this.altreEmail = altreEmail;
	}

}
