package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Documentation")
public class DocumentazioneDTO {

	@JsonProperty("additional")
	private List<DocumentoCartellaDTO> documentiAggiuntivi = new ArrayList<>();

	@JsonProperty("interection")
	private List<DocumentoCartellaDTO> documentiInterazione = new ArrayList<>();

	@JsonProperty("indiction")
	private List<DocumentoDTO> documentiIndizione = new ArrayList<>();

	@JsonProperty("accreditation")
	private List<DocumentoDTO> documentiAccreditamento = new ArrayList<>();
	
	@JsonProperty("preliminary")
	private List<DocumentoCartellaDTO> documentiPreIstruttoria = new ArrayList<>();

	@JsonProperty("shared")
	private List<DocumentoCartellaDTO> documentiCondivisi = new ArrayList<>();
	
	@JsonProperty("signature")
	private List<DocumentoCartellaDTO> documentiFirma = new ArrayList<>();

	public List<DocumentoCartellaDTO> getDocumentiAggiuntivi() {
		return documentiAggiuntivi;
	}

	public void setDocumentiAggiuntivi(List<DocumentoCartellaDTO> documentiAggiuntivi) {
		this.documentiAggiuntivi = documentiAggiuntivi;
	}
	
	public List<DocumentoCartellaDTO> getDocumentiInterazione() {
		return documentiInterazione;
	}

	public void setDocumentiInterazione(List<DocumentoCartellaDTO> documentiInterazione) {
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

	public List<DocumentoCartellaDTO> getDocumentiPreIstruttoria() {
		return documentiPreIstruttoria;
	}

	public void setDocumentiPreIstruttoria(List<DocumentoCartellaDTO> documentiPreIstruttoria) {
		this.documentiPreIstruttoria = documentiPreIstruttoria;
	}


	public List<DocumentoCartellaDTO> getDocumentiCondivisi() {
		return documentiCondivisi;
	}

	public void setDocumentiCondivisi(List<DocumentoCartellaDTO> documentiCondivisi) {
		this.documentiCondivisi = documentiCondivisi;
	}

	public List<DocumentoCartellaDTO> getDocumentiFirma() {
		return documentiFirma;
	}

	public void setDocumentiFirma(List<DocumentoCartellaDTO> documentiFirma) {
		this.documentiFirma = documentiFirma;
	}

}
