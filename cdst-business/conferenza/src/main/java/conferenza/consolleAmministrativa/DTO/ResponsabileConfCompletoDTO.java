package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="ConferenceManagerComplete")
public class ResponsabileConfCompletoDTO {
	
	@JsonProperty(value = "idPerson")
	private Integer idPersona;
	
	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "surname")
	private String cognome;
	
	@JsonProperty(value = "fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty(value = "email")
	private String email;
	
	@JsonProperty(value = "prosecutingAdministration")
	private LabelValue amministrazioneProcedente;

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
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

	public LabelValue getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(LabelValue amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

}
