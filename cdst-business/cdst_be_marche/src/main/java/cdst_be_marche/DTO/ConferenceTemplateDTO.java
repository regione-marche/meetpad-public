package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ConferenceTemplateDTO")
public class ConferenceTemplateDTO {
	
	@JsonProperty("company")
	private ImpresaDTO impresaDTO;
	
	@JsonProperty("applicant")
	private RichiedenteDTO richiedenteDTO;
	
	public ImpresaDTO getImpresaDTO() {
		return impresaDTO;
	}
	public void setImpresaDTO(ImpresaDTO impresaDTO) {
		this.impresaDTO = impresaDTO;
	}
	public RichiedenteDTO getRichiedenteDTO() {
		return richiedenteDTO;
	}
	public void setRichiedenteDTO(RichiedenteDTO richiedenteDTO) {
		this.richiedenteDTO = richiedenteDTO;
	}

}
