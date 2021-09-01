package conferenza.consolleAmministrativa.DTO;

import conferenza.DTO.PageableDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ConferenceManagerResearch")
public class RicercaResponsabileConferenzaDTO extends PageableDTO{
	
	@ApiModelProperty(name = "value")
	private String value;

	@ApiModelProperty(name = "prosecutingAdministration")
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
	
	public void setProsecutingAdministration(String amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}
}
