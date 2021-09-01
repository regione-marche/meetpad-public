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
public class EventoPartecipante implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6413228778099317862L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EVENTO_PARTECIPANTE")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_EVENTO")
	private TipoEvento tipoEvento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUOLO_PARTECIPANTE")
	private RuoloPartecipante ruoloPartecipante;
	
	@Column(name = "FLAG_CC")
	private Boolean flagCC;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public RuoloPartecipante getRuoloPartecipante() {
		return ruoloPartecipante;
	}

	public void setRuoloPartecipante(RuoloPartecipante ruoloPartecipante) {
		this.ruoloPartecipante = ruoloPartecipante;
	}

	public Boolean getFlagCC() {
		return flagCC;
	}

	public void setFlagCC(Boolean flagCC) {
		this.flagCC = flagCC;
	}

}
