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

import cdst_be_marche.util.DbConst;

@Entity
public class Utente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1525534230374165138L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UTENTE")
	private Integer idUtente;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "COGNOME")
	private String cognome;

	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name = "EMAIL")
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PROFILO")
	private Profilo profilo;

	@Column(name = "flag_delega_altre_amministrazioni")
	private Boolean delegaCreazioneAltreAmministrazioni;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Profilo getProfilo() {
		return profilo;
	}

	public void setProfilo(Profilo profilo) {
		this.profilo = profilo;
	}

	public Integer getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}

	public Boolean getDelegaCreazioneAltreAmministrazioni() {
		return delegaCreazioneAltreAmministrazioni;
	}

	public void setDelegaCreazioneAltreAmministrazioni(Boolean delegaCreazioneAltreAmministrazioni) {
		this.delegaCreazioneAltreAmministrazioni = delegaCreazioneAltreAmministrazioni;
	}

	public boolean isCreatore() {
		if (getProfilo() != null)
			return getProfilo().getTipoProfilo().getCodice()
					.equals(new Integer(DbConst.TIPO_PROFILO_CREATORE_CDS).toString());
		return false;
	}
	
	public boolean isAbilitatoCreazioneAltreAmministrazioni() {
		return isCreatore() && getDelegaCreazioneAltreAmministrazioni();
	}

}
