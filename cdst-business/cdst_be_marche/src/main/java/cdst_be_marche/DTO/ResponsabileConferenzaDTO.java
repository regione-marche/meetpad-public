package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="ConferenceManager")
public class ResponsabileConferenzaDTO extends LabelValue{
	
	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "surname")
	private String cognome;
	
	@JsonProperty(value = "fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty(value = "email")
	private String email;
	
	@JsonProperty(value = "profilo")
	private LabelValue profilo;
	
	public ResponsabileConferenzaDTO (String key, String value) {
		super(key, value);
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LabelValue getProfilo() {
		return profilo;
	}

	public void setProfilo(LabelValue profilo) {
		this.profilo = profilo;
	}

}
