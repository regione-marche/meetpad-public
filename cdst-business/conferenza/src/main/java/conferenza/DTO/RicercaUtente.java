package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel("UserResearch")
public class RicercaUtente extends PageableDTO{
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("administrationProceeding")
	private String amministrazioneProcedente;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(String amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}
	
	public void setAdministrationProceeding(String amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}
	
}
