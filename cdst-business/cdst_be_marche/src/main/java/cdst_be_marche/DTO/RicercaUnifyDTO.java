package cdst_be_marche.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="UnifyResearch")
public class RicercaUnifyDTO extends PageableDTO{
	
	@ApiModelProperty(name = "value")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

}
