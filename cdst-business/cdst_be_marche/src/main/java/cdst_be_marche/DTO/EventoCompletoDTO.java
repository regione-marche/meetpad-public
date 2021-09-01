package cdst_be_marche.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import cdst_be_marche.model.Partecipante;
import io.swagger.annotations.ApiModel;

@ApiModel(value="EventDetail")
public class EventoCompletoDTO extends EventoDTO {
	
	@JsonProperty(value = "protocolNumber")
	private String numeroProtocollo;
	
	@JsonProperty(value = "body")
	private String corpo;
	
	@JsonProperty(value = "administration")
	private String amministrazione;
	
	@JsonProperty(value = "notes")
	private String note;
	
	@JsonProperty(value = "conference")
	private Integer idConferenza;
	
	@JsonProperty(value = "result")
	private LabelValue result;
	
	@JsonProperty(value = "document")
	private DocumentoDTO documentoDTO;
	
	@JsonProperty(value = "determinationType")
	private LabelValue tipoParere;
	
	@JsonProperty(value = "recipients")
	List<LabelValue> listaDestinatari = new ArrayList<>();

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getAmministrazione() {
		return amministrazione;
	}

	public void setAmministrazione(String amministrazione) {
		this.amministrazione = amministrazione;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public DocumentoDTO getDocumentoDTO() {
		return documentoDTO;
	}

	public void setDocumentoDTO(DocumentoDTO documentoDTO) {
		this.documentoDTO = documentoDTO;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LabelValue getResult() {
		return result;
	}

	public void setResult(LabelValue result) {
		this.result = result;
	}

	public List<LabelValue> getListaDestinatari() {
		return listaDestinatari;
	}

	public void setListaDestinatari(List<LabelValue> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}

	public LabelValue getTipoParere() {
		return tipoParere;
	}

	public void setTipoParere(LabelValue tipoParere) {
		this.tipoParere = tipoParere;
	}
}
