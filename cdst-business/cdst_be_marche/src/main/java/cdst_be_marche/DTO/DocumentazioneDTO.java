package cdst_be_marche.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Documentation")
public class DocumentazioneDTO {

	@JsonProperty("additional")
	private List<DocumentoDTO> documentiAggiuntivi = new ArrayList<>();

	@JsonProperty("interection")
	private List<DocumentoDTO> documentiInterazione = new ArrayList<>();

	@JsonProperty("indiction")
	private List<DocumentoDTO> documentiIndizione = new ArrayList<>();

	@JsonProperty("accreditation")
	private List<DocumentoDTO> documentiAccreditamento = new ArrayList<>();
	
	@JsonProperty("preliminary")
	private List<DocumentoDTO> documentiPreIstruttoria = new ArrayList<>();

	public List<DocumentoDTO> getDocumentiAggiuntivi() {
		return documentiAggiuntivi;
	}

	public void setDocumentiAggiuntivi(List<DocumentoDTO> documentiAggiuntivi) {
		this.documentiAggiuntivi = documentiAggiuntivi;
	}

	public List<DocumentoDTO> getDocumentiInterazione() {
		return documentiInterazione;
	}

	public void setDocumentiInterazione(List<DocumentoDTO> documentiInterazione) {
		this.documentiInterazione = documentiInterazione;
	}

	public List<DocumentoDTO> getDocumentiIndizione() {
		return documentiIndizione;
	}

	public void setDocumentiIndizione(List<DocumentoDTO> documentiIndizione) {
		this.documentiIndizione = documentiIndizione;
	}

	public List<DocumentoDTO> getDocumentiAccreditamento() {
		return documentiAccreditamento;
	}

	public void setDocumentiAccreditamento(List<DocumentoDTO> documentiAccreditamento) {
		this.documentiAccreditamento = documentiAccreditamento;
	}

	public List<DocumentoDTO> getDocumentiPreIstruttoria() {
		return documentiPreIstruttoria;
	}

	public void setDocumentiPreIstruttoria(List<DocumentoDTO> documentiPreIstruttoria) {
		this.documentiPreIstruttoria = documentiPreIstruttoria;
	}

}
