package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.PageableDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreaccreditationSearch")
public class PreaccreditatoRicerca extends PageableDTO{
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("idEnte")
	private Integer idEnte;

	@JsonProperty("conferenceType")
	private String codiceTipoConf;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}
	
	public String getCodiceTipoConf() {
		return codiceTipoConf;
	}

	public void setCodiceTipoConf(String codiceTipoConf) {
		this.codiceTipoConf = codiceTipoConf;
	}
}
