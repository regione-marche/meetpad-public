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

import cdst_be_marche.model.bean._Typological;

/**
 * Rappresenta un Responsabile di Conferenza relativo a un Ente Amministrazione
 * Proponente (flagAmministrazioneProcedente=true)
 * 
 * @author arosina
 *
 */
@Entity
public class ResponsabileConferenza implements Serializable, _Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2072757064913967364L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RESPONSABILE_CONFERENZA")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERSONA")
	private Persona persona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ENTE")
	private Ente ente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public String getCodice() {
		return getId().toString();
	}

	@Override
	public String getDescrizione() {
		if (getPersona() != null) {
			return getPersona().getNome().concat(" ").concat(getPersona().getCognome());
		} else
			return "";
	}

}
