package conferenza.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="GeographicalSearchAuthority")
public class EnteRicercaGeograficaDTO extends PageableDTO{
	
	@ApiModelProperty(name = "value")
	private String value;
	
	@ApiModelProperty(name = "city")
	private String comune;
	
	@ApiModelProperty(name = "province")
	private String provincia;
	
	@ApiModelProperty(name = "area")
	private String regione;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public void setCity(String comune) {
		this.comune = comune;
	}
	
	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public void setProvince(String provincia) {
		this.provincia = provincia;
	}
	
	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}
	
	public void setArea(String regione) {
		this.regione = regione;
	}

}
