package conferenza.adapder.integrazioni.createconference.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import conferenza.model.Conferenza;

@Entity
@Table(name = "integ_frontiera_conferenza")
public class IntegFrontieraConferenza {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3650316679030346752L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_INTEG_FRONTIERA_CONFERENZA")
	private Integer idIntegFrontieraConferenza;
	
	//Riferimento esterno per la Conferenza..Pratica/Fasciloco/Workflow
	@Column(name = "ID_PRATICA")
	private Integer idPratica;
	
	@Column(name = "ID_FASCICOLO")
	private String  idFascicolo;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;
	
	public IntegFrontieraConferenza() {}
	
	public IntegFrontieraConferenza(Integer idPratica, Conferenza conferenza) {
		this.idPratica = idPratica;
		this.conferenza = conferenza;
	}

	public IntegFrontieraConferenza(String  idFascicolo, Conferenza conferenza) {
		this.idFascicolo = idFascicolo;
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

	public String getIdFascicolo() {
		return idFascicolo;
	}

	public void setIdFascicolo(String idFascicolo) {
		this.idFascicolo = idFascicolo;
	}

	
}
