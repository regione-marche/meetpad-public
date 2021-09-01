package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="ConferenceManagerPreview")
public class ResponsabileConfPreviewDTO {
	
	@JsonProperty(value = "idPerson")
	private Integer idPersona;
	
	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "surname")
	private String cognome;
	
	@JsonProperty(value = "fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty(value = "prosecutingAdministration")
	private String amministrazioneProcedente;

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

	public String getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(String amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

}
