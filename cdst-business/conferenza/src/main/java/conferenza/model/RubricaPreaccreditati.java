package conferenza.model;

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
public class RubricaPreaccreditati implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 770734879884601300L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUBRICA_PREACCREDITATO")
	private Integer idRubricaPreaccreditato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUOLO_PERSONA")
	private RuoloPersona ruoloPersona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE")
	private TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERSONA")
	private Persona persona;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ENTE")
	private Ente ente;

	public Integer getIdRubricaPreaccreditato() {
		return idRubricaPreaccreditato;
	}

	public void setIdRubricaPreaccreditato(Integer idRubricaPreaccreditato) {
		this.idRubricaPreaccreditato = idRubricaPreaccreditato;
	}

	public RuoloPersona getRuoloPersona() {
		return ruoloPersona;
	}

	public void setRuoloPersona(RuoloPersona ruoloPersona) {
		this.ruoloPersona = ruoloPersona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public Ente getEnte() {
		return ente;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}
	
	public TipologiaConferenzaSpecializzazione getTipologiaConferenzaSpecializzazione() {
		return tipologiaConferenzaSpecializzazione;
	}

	public void setTipologiaConferenzaSpecializzazione(
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione) {
		this.tipologiaConferenzaSpecializzazione = tipologiaConferenzaSpecializzazione;
	}
}
