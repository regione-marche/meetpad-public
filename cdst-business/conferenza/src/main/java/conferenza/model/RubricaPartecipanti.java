package conferenza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE")
	private TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ENTE")
	private Ente ente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUOLO_PARTECIPANTE")
	private RuoloPartecipante ruoloPartecipante;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "RUBRICA_PARTECIPANTE_COMPETENZA_AUTORIZZATIVA", joinColumns = @JoinColumn(name = "ID_RUBRICA_PARTECIPANTI", 
		referencedColumnName = "ID_RUBRICA_PARTECIPANTI"), inverseJoinColumns = @JoinColumn(name = "CODICE_COMPETENZA_AUTORIZZATIVA", 
		referencedColumnName = "CODICE", table = "COMPETENZA_AUTORIZZATIVA"))
	private List<CompetenzaAutorizzativa> listaCompAutoPerRbricaPartecipante = new ArrayList<>();


	public Integer getIdRubricaPartecipanti() {
		return idRubricaPartecipanti;
	}

	public void setIdRubricaPartecipanti(Integer idRubricaPartecipanti) {
		this.idRubricaPartecipanti = idRubricaPartecipanti;
	}

	public TipologiaConferenzaSpecializzazione getTipologiaConferenzaSpecializzazione() {
		return tipologiaConferenzaSpecializzazione;
	}

	public void setTipologiaConferenzaSpecializzazione(
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione) {
		this.tipologiaConferenzaSpecializzazione = tipologiaConferenzaSpecializzazione;
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

	public List<CompetenzaAutorizzativa> getListaCompAutoPerRbricaPartecipante() {
		return listaCompAutoPerRbricaPartecipante;
	}

	public void setListaCompAutoPerRbricaPartecipante(List<CompetenzaAutorizzativa> listaCompAutoPerRbricaPartecipante) {
		this.listaCompAutoPerRbricaPartecipante = listaCompAutoPerRbricaPartecipante;
	}

}
