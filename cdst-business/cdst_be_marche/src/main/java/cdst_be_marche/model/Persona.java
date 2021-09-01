package cdst_be_marche.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import cdst_be_marche.model.bean._Typological;

/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
public class Persona implements Serializable, _Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3365244766604697613L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERSONA")
	private Integer idPersona;

	@Column(name = "CODICE_FISCALE", nullable=false)
	private String codiceFiscale;

	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "EMAIL")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String cf) {
		this.codiceFiscale = cf;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getCodice() {
		return getCodiceFiscale();
	}

	@Override
	public String getDescrizione() {
		String nome = getNome() == null ? "" : getCognome();
		String cognome = getCognome() == null ? "" : getNome();
		
		return nome.concat(" ").concat(cognome);
	}

}