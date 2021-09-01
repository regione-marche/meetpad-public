package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ConferenceTemplateListDTO")
public class ConferenceTemplateListDTO {
	
	@JsonProperty("companies")
	private List<ImpresaDTO> listaImpresaDTO = new ArrayList<>();
	
	@JsonProperty("applicants")
	private List<RichiedenteDTO> listaRichiedenteDTO = new ArrayList<>();

	public List<ImpresaDTO> getListaImpresaDTO() {
		return listaImpresaDTO;
	}

	public void setListaImpresaDTO(List<ImpresaDTO> listaImpresaDTO) {
		this.listaImpresaDTO = listaImpresaDTO;
	}

	public List<RichiedenteDTO> getListaRichiedenteDTO() {
		return listaRichiedenteDTO;
	}

	public void setListaRichiedenteDTO(List<RichiedenteDTO> listaRichiedenteDTO) {
		this.listaRichiedenteDTO = listaRichiedenteDTO;
	}

}
