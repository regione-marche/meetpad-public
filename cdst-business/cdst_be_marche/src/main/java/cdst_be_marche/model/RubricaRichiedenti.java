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
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA")
	private TipologiaConferenza tipologiaConferenza;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERSONA")
	private Persona persona;

	public Integer getIdRubricaRichiedenti() {
		return idRubricaRichiedenti;
	}

	public void setIdRubricaRichiedenti(Integer idRubricaRichiedenti) {
		this.idRubricaRichiedenti = idRubricaRichiedenti;
	}

	public TipologiaConferenza getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(TipologiaConferenza tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}
