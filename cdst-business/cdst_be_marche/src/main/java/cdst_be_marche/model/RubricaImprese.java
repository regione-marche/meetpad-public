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
public class RubricaImprese implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7541047094293144964L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUBRICA_IMPRESE")
	private Integer idRubricaImprese;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA")
	private TipologiaConferenza tipologiaConferenza;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_IMPRESA")
	private Impresa impresa;

	public Integer getIdRubricaImprese() {
		return idRubricaImprese;
	}

	public void setIdRubricaImprese(Integer idRubricaImprese) {
		this.idRubricaImprese = idRubricaImprese;
	}

	public TipologiaConferenza getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(TipologiaConferenza tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public Impresa getImpresa() {
		return impresa;
	}

	public void setImpresa(Impresa impresa) {
		this.impresa = impresa;
	}

}
