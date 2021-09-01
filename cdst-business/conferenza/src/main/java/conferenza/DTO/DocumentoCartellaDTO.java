package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "DocumentFolder")
public class DocumentoCartellaDTO extends IdentifiableDTO {

	@JsonProperty("name")
	private String nomeCartella;

	@JsonProperty("files")
	private List<DocumentoDTO> documenti = new ArrayList<>();

	public String getNomeCartella() {
		return nomeCartella;
	}

	public void setNomeCartella(String nomeCartella) {
		this.nomeCartella = nomeCartella;
	}

	public List<DocumentoDTO> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<DocumentoDTO> documenti) {
		this.documenti = documenti;
	}

}
