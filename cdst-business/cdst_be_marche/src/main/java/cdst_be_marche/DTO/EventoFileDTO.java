package cdst_be_marche.DTO;

import java.util.ArrayList;
import java.util.List;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "EventFile")
public class EventoFileDTO {

	@ApiModelProperty(value = "date")
	private String data;

	@ApiModelProperty(value = "type")
	private String tipoEvento;

	@ApiModelProperty(value = "sender", example = "{\"key\":\"1\"},{\"key\":\"2\"}")
	private String mittente;

	@ApiModelProperty(value = "subject", example = "{\"key\":\"1\"},{\"key\":\"2\"}")
	private String oggettoRichiesta;
	
	@ApiModelProperty(value = "recipients")
	private List<LabelValue> listaDestinatari = new ArrayList<>();
	
	@ApiModelProperty(value = "protocol")
	private String protocollo;

	@ApiModelProperty(value = "body")
	private String corpo;
	
	@ApiModelProperty(value = "result")
	private String result;
	
	@ApiModelProperty(value = "notes")
	private String note;
	
	@ApiModelProperty(value = "determinationType")
	private String tipoParere;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getOggettoRichiesta() {
		return oggettoRichiesta;
	}

	public void setOggettoRichiesta(String oggettoRichiesta) {
		this.oggettoRichiesta = oggettoRichiesta;
	}

	public List<LabelValue> getListaDestinatari() {
		return listaDestinatari;
	}

	public void setListaDestinatari(List<LabelValue> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTipoParere() {
		return tipoParere;
	}

	public void setTipoParere(String tipoParere) {
		this.tipoParere = tipoParere;
	}

}
