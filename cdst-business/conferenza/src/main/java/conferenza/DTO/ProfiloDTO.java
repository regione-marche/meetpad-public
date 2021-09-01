package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Profile")
public class ProfiloDTO extends IdentifiableDTO {
	
	@JsonProperty("profileType")
	private LabelValue tipoProfilo;
	
	@JsonProperty("administrationProceeding")
	private LabelValue amministrazioneProcedente;

	public LabelValue getTipoProfilo() {
		return tipoProfilo;
	}

	public void setTipoProfilo(LabelValue tipoProfilo) {
		this.tipoProfilo = tipoProfilo;
	}

	public LabelValue getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(LabelValue amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

}
