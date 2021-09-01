package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TipologiaConferenzaDataDefinizione implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5234661742077006659L;

	@Id
	@Column(name = "ID_TIPOLOGIA_CONFERENZA_DATE_DEFINIZIONE")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA")
	private TipologiaConferenza tipologiaConferenza;

	@Column(name = "CAMPO_DATA_CALCOLATO")
	private String campoDataCalcolato;

	@Column(name = "FLAG_OBBLIGATORIO")
	private Boolean flagObbligatorio;

	@Column(name = "OFFSET_GG_LAVORATIVI")
	private Integer offsetGiorniLavorativi;

	@Column(name = "OFFSET_GG_SOLARI")
	private Integer offsetGiorniSolari;

	@Column(name = "CAMPO_DATA_BASE")
	private String campoDataBase;

	@Column(name = "ORDINE")
	private Integer order;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipologiaConferenza getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(TipologiaConferenza tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public String getCampoDataCalcolato() {
		return campoDataCalcolato;
	}

	public void setCampoDataCalcolato(String campoDataCalcolato) {
		this.campoDataCalcolato = campoDataCalcolato;
	}

	public Boolean getFlagObbligatorio() {
		return flagObbligatorio;
	}

	public void setFlagObbligatorio(Boolean flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}

	public Integer getOffsetGiorniLavorativi() {
		return offsetGiorniLavorativi;
	}

	public void setOffsetGiorniLavorativi(Integer offsetGiorniLavorativi) {
		this.offsetGiorniLavorativi = offsetGiorniLavorativi;
	}

	public Integer getOffsetGiorniSolari() {
		return offsetGiorniSolari;
	}

	public void setOffsetGiorniSolari(Integer offsetGiorniSolari) {
		this.offsetGiorniSolari = offsetGiorniSolari;
	}

	public String getCampoDataBase() {
		return campoDataBase;
	}

	public void setCampoDataBase(String campoDataBase) {
		this.campoDataBase = campoDataBase;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

}
