package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Authority")
public class EnteDTO extends IdentifiableDTO {

	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "description")
	private String descrizione;
	
	@JsonProperty(value = "pec")
	private String pec;
	
	@JsonProperty(value = "fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty(value = "competence")
	private String competenza;
	
	@JsonProperty(value = "role")
	private LabelValue ruolo;
	
	@JsonProperty(value = "flagProsecutingAdministration")
	private Boolean flagAmmProc;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}

	public LabelValue getRuolo() {
		return ruolo;
	}

	public void setRuolo(LabelValue ruolo) {
		this.ruolo = ruolo;
	}

	public Boolean getFlagAmmProc() {
		return flagAmmProc;
	}

	public void setFlagAmmProc(Boolean flagAmmProc) {
		this.flagAmmProc = flagAmmProc;
	}
	
}
