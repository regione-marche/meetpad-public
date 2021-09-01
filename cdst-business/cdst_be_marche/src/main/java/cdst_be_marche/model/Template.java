package cdst_be_marche.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Template implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6117777726671324597L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TEMPLATE")
	private Integer id;
	
	@Column(name = "NOME_TEMPLATE")
	private String nomeTemplate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA")
	private TipologiaConferenza tipologiaConferenza;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_EVENTO")
	private TipoEvento tipoEvento;
	
	@Column(name = "NOME_TEMPLATE_DOWNLOAD")
	private String nomeTemplateDownload;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeTemplate() {
		return nomeTemplate;
	}

	public void setNomeTemplate(String nomeTemplate) {
		this.nomeTemplate = nomeTemplate;
	}

	public TipologiaConferenza getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(TipologiaConferenza tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getNomeTemplateDownload() {
		return nomeTemplateDownload;
	}

	public void setNomeTemplateDownload(String nomeTemplateDownload) {
		this.nomeTemplateDownload = nomeTemplateDownload;
	}

}
