package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

/**
 * La rubricaImprese (companySection) è una lista che contiene un elenco di coppie
 * Impresa/Richiedente dove il richiedente è la persona fisica rappresentante
 * per l'impresa (nel caso di più richiedenti è il richiedente principale)
 * 
 * il richiedenteDTO (applicant) è comunque valorizzato con il richiedente di default (nel
 * caso in cui non venga selezionata nessuna impresa, o non ci siano imprese in rubrica)
 * 
 * @author arosina
 *
 */
@ApiModel(value = "ConferenceTemplateProcedureDTO")
public class ConferenceTemplateProcedureDTO {
	
	@JsonProperty("applicant")
	private RichiedenteDTO richiedenteDTO;
	
	@JsonProperty("companySection")
	private List<RubricaImpresaDTO> rubricaImprese = new ArrayList<>();
	
	public RichiedenteDTO getRichiedenteDTO() {
		return richiedenteDTO;
	}
	
	public void setRichiedenteDTO(RichiedenteDTO richiedenteDTO) {
		this.richiedenteDTO = richiedenteDTO;
	}
	
	public List<RubricaImpresaDTO> getRubricaImprese() {
		return rubricaImprese;
	}
	
	public void setRubricaImprese(List<RubricaImpresaDTO> rubricaImprese) {
		this.rubricaImprese = rubricaImprese;
	}

}
