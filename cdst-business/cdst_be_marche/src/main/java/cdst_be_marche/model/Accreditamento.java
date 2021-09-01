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

import org.hibernate.annotations.Where;

import cdst_be_marche.model.bean.DeletableEntity;
import cdst_be_marche.util.DbConst;

/**
 * The persistent class for the persona_accreditata database table.
 * 
 */
@Entity
@Where(clause = "DATA_FINE is Null")
public class Accreditamento extends DeletableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519370666238994336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ACCREDITAMENTO")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PARTECIPANTE")
	private Partecipante partecipante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERSONA")
	private Persona persona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUOLO_PERSONA", nullable=false)
	private RuoloPersona ruoloPersona;

	@Column(name = "FLAG_ACCREDITATO")
	private Boolean flagAccreditato;

	@Column(name = "FLAG_INVITATO")
	private Boolean flagInvitato;
	
	@Column(name = "FLAG_RIFIUTO")
	private Boolean flagRifiuto;
	
	@Column(name = "DESCRIZIONE_RIFIUTO")
	private String descrizioneRifiuto;

	public Accreditamento() {
	}

	public RuoloPersona getRuoloPersona() {
		return ruoloPersona;
	}

	public void setRuoloPersona(RuoloPersona ruoloPersona) {
		this.ruoloPersona = ruoloPersona;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Partecipante getPartecipante() {
		return this.partecipante;
	}

	public void setPartecipante(Partecipante partecipante) {
		this.partecipante = partecipante;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Boolean getFlagAccreditato() {
		return flagAccreditato;
	}

	public void setFlagAccreditato(Boolean flagAccreditato) {
		this.flagAccreditato = flagAccreditato;
	}

	public Boolean getFlagInvitato() {
		return flagInvitato;
	}

	public void setFlagInvitato(Boolean flagInvitato) {
		this.flagInvitato = flagInvitato;
	}

	public Boolean getFlagRifiuto() {
		return flagRifiuto;
	}

	public void setFlagRifiuto(Boolean flagRifiuto) {
		this.flagRifiuto = flagRifiuto;
	}

	public String getDescrizioneRifiuto() {
		return descrizioneRifiuto;
	}

	public void setDescrizioneRifiuto(String descrizioneRifiuto) {
		this.descrizioneRifiuto = descrizioneRifiuto;
	}

	public boolean isResponsabileConferenza() {
		return Integer.parseInt(getRuoloPersona().getCodice()) == DbConst.RUOLO_PERSONA_RESPONSABILE_CONFERENZA;
	}
	
	public boolean isRichiedente() {
		return Integer.parseInt(getRuoloPersona().getCodice()) == DbConst.RUOLO_PERSONA_RICHIEDENTE;
	}

}