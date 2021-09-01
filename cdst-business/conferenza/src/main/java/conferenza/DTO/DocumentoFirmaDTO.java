package conferenza.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "SingDocument")
public class DocumentoFirmaDTO extends DocumentoDTO {

	@JsonProperty("idConference")
	private Integer idConferenza;

	@JsonProperty("requestReference")
	private String riferimentoIstanza;
	
	@JsonProperty("denomination")
	private String denominazione;
	
	@JsonProperty(value = "eventType")
	private LabelValue tipoEvento;
	
	@JsonProperty(value = "documentsAttachment")
	private List<DocumentoDTO>  listaDocAllegati;

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getRiferimentoIstanza() {
		return riferimentoIstanza;
	}

	public void setRiferimentoIstanza(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public LabelValue getTipoEvento() {
		return tipoEvento;
	}
	
	public void setTipoEvento(LabelValue tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	
	public List<DocumentoDTO> getListaDocAllegati() {
		return listaDocAllegati;
	}

	public void setListaDocAllegati(List<DocumentoDTO> listaDocAllegati) {
		this.listaDocAllegati = listaDocAllegati;
	}
}
