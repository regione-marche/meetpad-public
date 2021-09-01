package conferenza.model;

import java.io.Serializable;
import javax.persistence.*;

import conferenza.model.bean._Typological;

import java.util.List;

/**
 * The persistent class for the ente database table.
 * 
 */
@Entity
public class Ente implements Serializable, _Typological{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2183691904614000001L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ENTE")
	private Integer idEnte;

	@Column(name = "DESCRIZIONE_ENTE")
	private String descrizioneEnte;

	@Column(name = "PEC_ENTE")
	private String pecEnte;

	@Column(name = "CODICE_FISCALE_ENTE")
	private String codiceFiscaleEnte;
	
	@Column(name = "CODICE_IPA")
	private String codiceIpa;
	
	@Column(name = "CODICE_UFFICIO")
	private String codiceUfficio;
	
	@Column(name = "DESCRIZIONE_COMUNE")
	private String descrizioneComune;
	
	@Column(name = "CODICE_OU")
	private String codiceOu;
	
	@Column(name = "CODICE_OU_PADRE")
	private String codiceOuPadre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_REGIONE")
	private Regione regione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PROVINCIA")
	private Provincia provincia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_COMUNE")
	private Comune comune;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_ISTAT")
	private EnteTipologiaIstat enteTipoIstat;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_AMMINISTRATIVA")
	private EnteTipologiaAmministrativa enteTipoAmm;

	@Column(name = "FLAG_AMM_PROCEDENTE")
	private Boolean flagAmministrazioneProcedente;

	@Column(name = "FLAG_AMMINISTRAZIONE_PRINCIPALE")
	private Boolean flagAmministrazionePrincipale;
	
	@OneToMany(mappedBy = "ente")
	private List<Partecipante> partecipantes;

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}

	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}

	public String getPecEnte() {
		return pecEnte;
	}

	public void setPecEnte(String pecEnte) {
		this.pecEnte = pecEnte;
	}

	public String getCodiceFiscaleEnte() {
		return codiceFiscaleEnte;
	}

	public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
		this.codiceFiscaleEnte = codiceFiscaleEnte;
	}

	public Boolean getFlagAmministrazioneProcedente() {
		return flagAmministrazioneProcedente;
	}

	public void setFlagAmministrazioneProcedente(Boolean flagAmministrazioneProcedente) {
		this.flagAmministrazioneProcedente = flagAmministrazioneProcedente;
	}

	public Boolean getFlagAmministrazionePrincipale() {
		return flagAmministrazionePrincipale;
	}

	public void setFlagAmministrazionePrincipale(Boolean flagAmministrazionePrincipale) {
		this.flagAmministrazionePrincipale = flagAmministrazionePrincipale;
	}

	public String getCodiceIpa() {
		return codiceIpa;
	}

	public void setCodiceIpa(String codiceIpa) {
		this.codiceIpa = codiceIpa;
	}

	public Regione getRegione() {
		return regione;
	}

	public void setRegione(Regione regione) {
		this.regione = regione;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Comune getComune() {
		return comune;
	}

	public void setComune(Comune comune) {
		this.comune = comune;
	}	

	public EnteTipologiaIstat getEnteTipoIstat() {
		return enteTipoIstat;
	}

	public void setEnteTipoIstat(EnteTipologiaIstat enteTipoIstat) {
		this.enteTipoIstat = enteTipoIstat;
	}

	public EnteTipologiaAmministrativa getEnteTipoAmm() {
		return enteTipoAmm;
	}

	public void setEnteTipoAmm(EnteTipologiaAmministrativa enteTipoAmm) {
		this.enteTipoAmm = enteTipoAmm;
	}

	public String getCodiceUfficio() {
		return codiceUfficio;
	}

	public void setCodiceUfficio(String codiceUfficio) {
		this.codiceUfficio = codiceUfficio;
	}

	public String getDescrizioneComune() {
		return descrizioneComune;
	}

	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}

	public String getCodiceOu() {
		return codiceOu;
	}

	public void setCodiceOu(String codiceOu) {
		this.codiceOu = codiceOu;
	}

	public String getCodiceOuPadre() {
		return codiceOuPadre;
	}

	public void setCodiceOuPadre(String codiceOuPadre) {
		this.codiceOuPadre = codiceOuPadre;
	}

	public List<Partecipante> getPartecipantes() {
		return this.partecipantes;
	}

	public void setPartecipantes(List<Partecipante> partecipantes) {
		this.partecipantes = partecipantes;
	}

	public Partecipante addPartecipante(Partecipante partecipante) {
		getPartecipantes().add(partecipante);
		partecipante.setEnte(this);

		return partecipante;
	}

	public Partecipante removePartecipante(Partecipante partecipante) {
		getPartecipantes().remove(partecipante);
		partecipante.setEnte(null);

		return partecipante;
	}

	@Override
	public String getCodice() {
		return getIdEnte().toString();
	}

	@Override
	public String getDescrizione() {
		return getDescrizioneEnte();
	}

}