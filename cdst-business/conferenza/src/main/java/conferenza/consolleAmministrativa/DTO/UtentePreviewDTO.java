package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreviewUser")
public class UtentePreviewDTO {
	
	@JsonProperty(value = "idUser")
	private Integer idUtente;
	
	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "surname")
	private String cognome;
	
	@JsonProperty(value = "fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty(value = "prosecutingAdministration")
	private LabelValue amministrazioneProcedente;

	public Integer getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
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

	public LabelValue getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(LabelValue amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

	
}
