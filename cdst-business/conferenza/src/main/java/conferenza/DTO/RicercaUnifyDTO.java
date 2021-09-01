package conferenza.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="UnifyResearch")
public class RicercaUnifyDTO extends PageableDTO{
	
	@ApiModelProperty(name = "value")
	private String value;
	
	@ApiModelProperty(name = "state")
	private String stato;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public void setState(String stato) {
		this.stato = stato;
	}
}
