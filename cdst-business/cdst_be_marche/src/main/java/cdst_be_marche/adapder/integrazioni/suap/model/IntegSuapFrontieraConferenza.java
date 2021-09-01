package cdst_be_marche.adapder.integrazioni.suap.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cdst_be_marche.model.Conferenza;

@Entity
@Table(name = "integ_frontiera_conferenza")
public class IntegSuapFrontieraConferenza implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3650316679029346752L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_INTEG_FRONTIERA_CONFERENZA")
	private Integer idIntegFrontieraConferenza;
	
	@Column(name = "ID_PRATICA")
	private Integer idPratica;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;
	
	public IntegSuapFrontieraConferenza() {}
	
	public IntegSuapFrontieraConferenza(Integer idPratica, Conferenza conferenza) {
		this.idPratica = idPratica;
		this.conferenza = conferenza;
	}

	public Integer getIdIntegFrontieraConferenza() {
		return idIntegFrontieraConferenza;
	}

	public void setIdIntegFrontieraConferenza(Integer idIntegFrontieraConferenza) {
		this.idIntegFrontieraConferenza = idIntegFrontieraConferenza;
	}

	public Integer getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(Integer idPratica) {
		this.idPratica = idPratica;
	}

	public Conferenza getConferenza() {
		return conferenza;
	}

	public void setConferenza(Conferenza conferenza) {
		this.conferenza = conferenza;
	}
	

}
