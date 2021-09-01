package conferenza.model;

import java.io.Serializable;
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

import org.hibernate.annotations.Where;

import conferenza.model.bean.DeletableNotAuditEntity;
import conferenza.model.bean._Typological;
import conferenza.util.DbConst;

@Entity
@Where(clause = "DATA_FINE is Null")
public class Utente extends DeletableNotAuditEntity implements Serializable, _Typological {

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
	
	@Column(name = "flag_firmatario")
	private Boolean flagFirmatario;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			  name = "gruppo_utenti", 
			  joinColumns = @JoinColumn(name = "fk_utente"), 
			  inverseJoinColumns = @JoinColumn(name = "fk_utente_responsabile"))
	private List<Utente> gruppoUtenti;
	
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
	
	public Boolean getFlagFirmatario() {
		return flagFirmatario;
	}

	public void setFlagFirmatario(Boolean flagFirmatario) {
		this.flagFirmatario = flagFirmatario;
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

	public Boolean isResponsabile() {
		if (getProfilo() != null)
			return getProfilo().getTipoProfilo().getCodice()
					.equals(new Integer(DbConst.TIPO_PROFILO_RESPONSABILE_CONFERENZA).toString());
		return false;
	}

	public List<Utente> getGruppoUtenti() {
		return gruppoUtenti;
	}

	public void setGruppoUtenti(List<Utente> gruppo_utenti) {
		this.gruppoUtenti = gruppo_utenti;
	}

	@Override
	public String getCodice() {
		return getIdUtente().toString();
	}

	@Override
	public String getDescrizione() {
		return getCognome() + " " + getNome() + " (" +getCodiceFiscale() + " - " +getEmail() + ")";
	}
}
