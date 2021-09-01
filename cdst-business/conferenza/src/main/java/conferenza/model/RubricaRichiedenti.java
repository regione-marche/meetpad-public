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
public class RubricaRichiedenti implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1572002330317732426L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUBRICA_RICHIEDENTI")
	private Integer idRubricaRichiedenti;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE")
	private TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERSONA")
	private Persona persona;
	
	@Column(name = "PRINCIPALE")
	private Boolean principale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUBRICA_IMPRESE")
	private RubricaImprese impresa; 

	public Integer getIdRubricaRichiedenti() {
		return idRubricaRichiedenti;
	}

	public void setIdRubricaRichiedenti(Integer idRubricaRichiedenti) {
		this.idRubricaRichiedenti = idRubricaRichiedenti;
	}

	public TipologiaConferenzaSpecializzazione getTipologiaConferenzaSpecializzazione() {
		return tipologiaConferenzaSpecializzazione;
	}

	public void setTipologiaConferenzaSpecializzazione(
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione) {
		this.tipologiaConferenzaSpecializzazione = tipologiaConferenzaSpecializzazione;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Boolean getPrincipale() {
		return principale;
	}

	public void setPrincipale(Boolean principale) {
		this.principale = principale;
	}

	public RubricaImprese getImpresa() {
		return impresa;
	}

	public void setImpresa(RubricaImprese impresa) {
		this.impresa = impresa;
	}
	
	

}
