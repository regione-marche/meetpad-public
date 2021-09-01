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
public class RubricaPartecipanti implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3259529407189888603L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUBRICA_PARTECIPANTI")
	private Integer idRubricaPartecipanti;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA")
	private TipologiaConferenza tipologiaConferenza;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ENTE")
	private Ente ente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUOLO_PARTECIPANTE")
	private RuoloPartecipante ruoloPartecipante;

	public Integer getIdRubricaPartecipanti() {
		return idRubricaPartecipanti;
	}

	public void setIdRubricaPartecipanti(Integer idRubricaPartecipanti) {
		this.idRubricaPartecipanti = idRubricaPartecipanti;
	}

	public TipologiaConferenza getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(TipologiaConferenza tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public Ente getEnte() {
		return ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

	public RuoloPartecipante getRuoloPartecipante() {
		return ruoloPartecipante;
	}

	public void setRuoloPartecipante(RuoloPartecipante ruoloPartecipante) {
		this.ruoloPartecipante = ruoloPartecipante;
	}

}
