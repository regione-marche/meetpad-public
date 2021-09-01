package conferenza.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="TypologicalSearchAuthority")
public class EnteRicercaTipologicaDTO extends PageableDTO{
	
	@ApiModelProperty(name = "value")
	private String value;
	
	@ApiModelProperty(name = "istatType")
	private String tipologiaIstat;
	
	@ApiModelProperty(name = "administrativeType")
	private String tipologiaAmm;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTipologiaIstat() {
		return tipologiaIstat;
	}

	public void setIstatType(String tipologiaIstat) {
		this.tipologiaIstat = tipologiaIstat;
	}
	
	public void setTipologiaIstat(String tipologiaIstat) {
		this.tipologiaIstat = tipologiaIstat;
	}

	public String getTipologiaAmm() {
		return tipologiaAmm;
	}

	public void setTipologiaAmm(String tipologiaAmm) {
		this.tipologiaAmm = tipologiaAmm;
	}

	public void setAdministrativeType(String tipologiaAmm) {
		this.tipologiaAmm = tipologiaAmm;
	}
}
