package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel("User")
public class UtenteDTO {

	@JsonProperty("name")
	private String nome;

	@JsonProperty("lastname")
	private String cognome;

	@JsonProperty("fiscalCode")
	private String codiceFiscale;

	@JsonProperty("profile")
	private LabelValue profilo;

	@JsonProperty("creationOtherAuthorities")
	private Boolean delegaCreazioneAltreAmministrazioni;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public LabelValue getProfilo() {
		return profilo;
	}

	public void setProfilo(LabelValue profilo) {
		this.profilo = profilo;
	}

	public Boolean getDelegaCreazioneAltreAmministrazioni() {
		return delegaCreazioneAltreAmministrazioni;
	}

	public void setDelegaCreazioneAltreAmministrazioni(Boolean delegaCreazioneAltreAmministrazioni) {
		this.delegaCreazioneAltreAmministrazioni = delegaCreazioneAltreAmministrazioni;
	}

}
