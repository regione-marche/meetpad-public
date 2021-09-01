package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * informazioni su Impresa e Persona fisica richiedente principale dell'impresa
 * @author arosina
 *
 */
@ApiModel(value = "CompanySectionDTO")
public class RubricaImpresaDTO {
	
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
