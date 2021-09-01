package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.PageableDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveDelegateSearch")
public class PrecaricamentoDelegatoRicerca extends PageableDTO{
	
	@JsonProperty("value")
	private String value;
	
	@JsonProperty("company")
	private String impresa;
	
	@JsonProperty("linkToCompany")
	private Boolean collegamentoAImpresa;
	
	@JsonProperty("conferenceType")
	private String codiceTipoConf;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getImpresa() {
		return impresa;
	}

	public void setImpresa(String impresa) {
		this.impresa = impresa;
	}
	
	public void setCompany(String impresa) {
		this.impresa = impresa;
	}

	public String getCodiceTipoConf() {
		return codiceTipoConf;
	}

	public void setCodiceTipoConf(String codiceTipoConf) {
		this.codiceTipoConf = codiceTipoConf;
	}
	
	public void setConferenceType(String codiceTipoConf) {
		this.codiceTipoConf = codiceTipoConf;
	}

	public Boolean getCollegamentoAImpresa() {
		return collegamentoAImpresa;
	}

	public void setCollegamentoAImpresa(Boolean collegamentoAImpresa) {
		this.collegamentoAImpresa = collegamentoAImpresa;
	}
	
	public void setLinkToCompany(Boolean collegamentoAImpresa) {
		this.collegamentoAImpresa = collegamentoAImpresa;
	}

}
