package conferenza.consolleAmministrativa.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveParticipantComplete")
public class PrecaricamentoPartecipanteCompletoDTO {
	
	@JsonProperty("idPreemptiveParticipant")
	private Integer idRubricaPartecipante;
	
	@JsonProperty("conferenceType")
	private LabelValue tipologiaConferenza;
	
	@JsonProperty("idAuthority")
	private Integer idEnte;
	
	@JsonProperty("fiscalCode")
	private String codiceFiscale;

	@JsonProperty("name")
	private String descrizioneEnte;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("role")
	private LabelValue ruolo;
	
	@JsonProperty("competenceAuthorization")
	private List<LabelValue> competenzaAutorizzativa = new ArrayList<>();

	public Integer getIdRubricaPartecipante() {
		return idRubricaPartecipante;
	}

	public void setIdRubricaPartecipante(Integer idRubricaPartecipante) {
		this.idRubricaPartecipante = idRubricaPartecipante;
	}

	public LabelValue getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(LabelValue tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}

	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LabelValue getRuolo() {
		return ruolo;
	}

	public void setRuolo(LabelValue ruolo) {
		this.ruolo = ruolo;
	}

	public List<LabelValue> getCompetenzaAutorizzativa() {
		return competenzaAutorizzativa;
	}

	public void setCompetenzaAutorizzativa(List<LabelValue> competenzaAutorizzativa) {
		this.competenzaAutorizzativa = competenzaAutorizzativa;
	}

}
