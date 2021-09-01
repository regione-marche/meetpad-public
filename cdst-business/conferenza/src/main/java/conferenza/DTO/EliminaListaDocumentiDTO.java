package conferenza.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;


@ApiModel(value="DeleteDocumentList")
public class EliminaListaDocumentiDTO {


	@JsonProperty("idDocumentList")
	List<Integer> listaIdDocumenti;

	public List<Integer> getListaIdDocumenti() {
		return listaIdDocumenti;
	}

	public void setListaIdDocumenti(List<Integer> listaIdDocumenti) {
		this.listaIdDocumenti = listaIdDocumenti;
	}
	
	
}
