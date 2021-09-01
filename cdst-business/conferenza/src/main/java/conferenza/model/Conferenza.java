package conferenza.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import conferenza.model.bean.DeletableEntity;
import conferenza.util.DbConst;

/**
 * The persistent class for the conferenza database table.
 * 
 */
@Entity
@Where(clause = "DATA_FINE is Null")
public class Conferenza extends DeletableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7616754770805992654L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CONFERENZA")
	private Integer idConferenza;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ENTE_PROCEDENTE")
	private Ente amministrazioneProcedente;

	@Column(name = "CF_RESPONSABILE_CONFERENZA")
	private String codiceFiscaleResponsabileConferenza;

	@Column(name = "CF_CREATORE_CONFERENZA")
	private String codiceFiscaleCreatoreConferenza;

	@Column(name = "DATA_AVVIO")
	private Date dataAvvio;

	@Column(name = "RIFERIMENTO_ISTANZA")
	private String riferimentoIstanza;

	@Column(name = "NOME_RICHIEDENTE")
	private String nomeRichiedente;

	@Column(name = "COGNOME_RICHIEDENTE")
	private String cognomeRichiedente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_PRATICA")
	private TipologiaPratica tipologiaPratica;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ATTIVITA")
	private Attivita attivita;

	@Column(name = "CODICE_FISCALE_RICHIEDENTE")
	private String codiceFiscaleRichiedente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_AZIONE")
	private Azione azione;

	@Column(name = "PEC")
	private String pec;
	
	@Column(name = "ORARIO_CONFERENZA")
	private Time orarioConferenza;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_LOCALIZZAZIONE_PROVINCIA")
	private Provincia localizzazioneProvincia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_LOCALIZZAZIONE_COMUNE")
	private Comune localizzazioneComune;

	@Column(name = "LOCALIZZAZIONE_INDIRIZZO")
	private String localizzazioneIndirizzo;

	@Column(name = "IMPRESA_DENOMINAZIONE")
	private String impresaDenominazione;

	@Column(name = "IMPRESA_CODICE_FISCALE")
	private String impresaCodiceFiscale;

	@Column(name = "IMPRESA_PARTITA_IVA")
	private String impresaPartitaIva;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_IMPRESA_FORMA_GIURIDICA")
	private FormaGiuridica impresaFormaGiuridica;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_IMPRESA_REGIONE")
	private Regione impresaRegione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_IMPRESA_PROVINCIA")
	private Provincia impresaProvincia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_IMPRESA_COMUNE")
	private Comune impresaComune;

	@Column(name = "IMPRESA_INDIRIZZO")
	private String impresaIndirizzo;

	@Column(name = "OGGETTO_DETERMINAZIONE")
	private String oggettoDeterminazione;

	@Column(name = "DATA_CREAZIONE_PRATICA")
	private Date dataCreazionePratica;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE")
	private TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione;

	@Column(name = "TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA")
	private Date termineRichiestaIntegrazioniConferenza;

	@Column(name = "TERMINE_ESPRESSIONE_PARERI")
	private Date termineEspressionePareri;

	@Column(name = "PRIMA_SESSIONE_SIMULTANEA")
	private Date primaSessioneSimultanea;

	@Column(name = "DATA_TERMINE")
	private Date dataTermine;

	@Column(name = "INDIRIZZO_SESSIONE_SIMULTANEA")
	private String indirizzoSessioneSimultanea;
	
	@Column(name = "CAP_SESSIONE_SIMULTANEA")
	private String capSessioneSimultanea;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_COMUNE_SESSIONE_SIMULTANEA")
	private Comune comuneSessioneSimultanea;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PROVINCIA_SESSIONE_SIMULTANEA")
	private Provincia provinciaSessioneSimultanea;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_MODALITA_SESSIONE_SIMULTANEA")
	private Modalita modalita;

	@Column(name = "STEP")
	private Integer step;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA_PADRE")
	private Conferenza conferenzaPadre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_STATO")
	private Stato stato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_IMPRESA")
	private Impresa impresa;
	
	@Column(name = "FLAG_ABILITA_MODIFICA_RICHIEDENTE")
	private Boolean flagAbilitaModificaRichiedente;

	@OneToMany(mappedBy = "conferenza")
	private List<ModificaData> modificaData;
	
	@OneToMany(mappedBy = "conferenza")
	private List<Partecipante> partecipantes = new ArrayList<>();

	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTAZIONE_AGGIUNTIVA + "'")
	private List<Documento> documentiAggiuntivi;

	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_COMUNICAZIONE_GENERICA + "'")
	private List<Documento> comunicazioniGeneriche;

	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_INTERAZIONI + "'")
	private List<Documento> documentiInterazioni;

	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_INDIZIONE + "'")
	private List<Documento> documentiIndizione;

	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_ACCREDITAMENTO + "'")
	private List<Documento> documentiAccreditamento;
	
	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_PRE_ISTRUTTORIA + "'")
	private List<Documento> documentiPreIstruttoria;
	
	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_DOCUMENTO_VERBALE_RIUNIONE + "'")
	private List<Documento> verbaleRiunione;
	
	@OneToMany(mappedBy = "conferenza")
	@Where(clause = "FK_TIPOLOGIA_DOCUMENTO = '" + DbConst.TIPOLOGIA_DOCUMENTO_DETERMINA + "'")
	private List<Documento> determina;
	
	@OneToMany(mappedBy = "conferenza")
	private List<Evento> eventi = new ArrayList<>();

	@OneToMany(mappedBy = "conferenza")
	private List<Votazione> votazioni = new ArrayList<>();

	@Column(name = "geomapGuid")
	private String geomapGuid;
	
	@Column(name = "FOGLIO_MAPPALE")
	private String foglioMappale;
	
	public String getFoglioMappale() {
		return foglioMappale;
	}

	public void setFoglioMappale(String foglioMappale) {
		this.foglioMappale = foglioMappale;
	}

	public String getGeomapGuid() {
		return geomapGuid;
	}

	public void setGeomapGuid(String geomapGuid) {
		this.geomapGuid = geomapGuid;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public Ente getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(Ente amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

	public String getCodiceFiscaleResponsabileConferenza() {
		return codiceFiscaleResponsabileConferenza;
	}

	public void setCodiceFiscaleResponsabileConferenza(String codiceFiscaleResponsabileConferenza) {
		this.codiceFiscaleResponsabileConferenza = codiceFiscaleResponsabileConferenza;
	}

	public String getCodiceFiscaleCreatoreConferenza() {
		return codiceFiscaleCreatoreConferenza;
	}

	public void setCodiceFiscaleCreatoreConferenza(String codiceFiscaleCreatoreConferenza) {
		this.codiceFiscaleCreatoreConferenza = codiceFiscaleCreatoreConferenza;
	}

	public Date getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(Date dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public String getRiferimentoIstanza() {
		return riferimentoIstanza;
	}

	public void setRiferimentoIstanza(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}

	public String getNomeRichiedente() {
		return nomeRichiedente;
	}

	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}

	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}

	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}

	public TipologiaPratica getTipologiaPratica() {
		return tipologiaPratica;
	}

	public void setTipologiaPratica(TipologiaPratica tipologiaPratica) {
		this.tipologiaPratica = tipologiaPratica;
	}

	public Attivita getAttivita() {
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public Azione getAzione() {
		return azione;
	}

	public void setAzione(Azione azione) {
		this.azione = azione;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public Time getOrarioConferenza() {
		return orarioConferenza;
	}

	public void setOrarioConferenza(Time orarioConferenza) {
		this.orarioConferenza = orarioConferenza;
	}

	public Provincia getLocalizzazioneProvincia() {
		return localizzazioneProvincia;
	}

	public void setLocalizzazioneProvincia(Provincia localizzazioneProvincia) {
		this.localizzazioneProvincia = localizzazioneProvincia;
	}

	public Comune getLocalizzazioneComune() {
		return localizzazioneComune;
	}

	public void setLocalizzazioneComune(Comune localizzazioneComune) {
		this.localizzazioneComune = localizzazioneComune;
	}

	public String getLocalizzazioneIndirizzo() {
		return localizzazioneIndirizzo;
	}

	public void setLocalizzazioneIndirizzo(String localizzazioneIndirizzo) {
		this.localizzazioneIndirizzo = localizzazioneIndirizzo;
	}

	public String getImpresaDenominazione() {
		return impresaDenominazione;
	}

	public void setImpresaDenominazione(String impresaDenominazione) {
		this.impresaDenominazione = impresaDenominazione;
	}

	public String getImpresaCodiceFiscale() {
		return impresaCodiceFiscale;
	}

	public void setImpresaCodiceFiscale(String impresaCodiceFiscale) {
		this.impresaCodiceFiscale = impresaCodiceFiscale;
	}

	public String getImpresaPartitaIva() {
		return impresaPartitaIva;
	}

	public void setImpresaPartitaIva(String impresaPartitaIva) {
		this.impresaPartitaIva = impresaPartitaIva;
	}

	public FormaGiuridica getImpresaFormaGiuridica() {
		return impresaFormaGiuridica;
	}

	public void setImpresaFormaGiuridica(FormaGiuridica impresaFormaGiuridica) {
		this.impresaFormaGiuridica = impresaFormaGiuridica;
	}

	public Regione getImpresaRegione() {
		return impresaRegione;
	}

	public void setImpresaRegione(Regione impresaRegione) {
		this.impresaRegione = impresaRegione;
	}

	public Provincia getImpresaProvincia() {
		return impresaProvincia;
	}

	public void setImpresaProvincia(Provincia impresaProvincia) {
		this.impresaProvincia = impresaProvincia;
	}

	public Comune getImpresaComune() {
		return impresaComune;
	}

	public void setImpresaComune(Comune impresaComune) {
		this.impresaComune = impresaComune;
	}

	public String getImpresaIndirizzo() {
		return impresaIndirizzo;
	}

	public void setImpresaIndirizzo(String impresaIndirizzo) {
		this.impresaIndirizzo = impresaIndirizzo;
	}

	public String getOggettoDeterminazione() {
		return oggettoDeterminazione;
	}

	public void setOggettoDeterminazione(String oggettoDeterminazione) {
		this.oggettoDeterminazione = oggettoDeterminazione;
	}

	public Date getDataCreazionePratica() {
		return dataCreazionePratica;
	}

	public void setDataCreazionePratica(Date dataCreazionePratica) {
		this.dataCreazionePratica = dataCreazionePratica;
	}

	public TipologiaConferenzaSpecializzazione getTipologiaConferenzaSpecializzazione() {
		return tipologiaConferenzaSpecializzazione;
	}

	public void setTipologiaConferenzaSpecializzazione(
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione) {
		this.tipologiaConferenzaSpecializzazione = tipologiaConferenzaSpecializzazione;
	}

	public Date getTermineRichiestaIntegrazioniConferenza() {
		return termineRichiestaIntegrazioniConferenza;
	}

	public void setTermineRichiestaIntegrazioniConferenza(Date termineRichiestaIntegrazioniConferenza) {
		this.termineRichiestaIntegrazioniConferenza = termineRichiestaIntegrazioniConferenza;
	}

	public Date getTermineEspressionePareri() {
		return termineEspressionePareri;
	}

	public void setTermineEspressionePareri(Date termineEspressionePareri) {
		this.termineEspressionePareri = termineEspressionePareri;
	}

	public Date getPrimaSessioneSimultanea() {
		return primaSessioneSimultanea;
	}

	public void setPrimaSessioneSimultanea(Date primaSessioneSimultanea) {
		this.primaSessioneSimultanea = primaSessioneSimultanea;
	}

	public Date getDataTermine() {
		return dataTermine;
	}

	public void setDataTermine(Date dataTermine) {
		this.dataTermine = dataTermine;
	}

	public String getIndirizzoSessioneSimultanea() {
		return indirizzoSessioneSimultanea;
	}

	public void setIndirizzoSessioneSimultanea(String indirizzoSessioneSimultanea) {
		this.indirizzoSessioneSimultanea = indirizzoSessioneSimultanea;
	}

	public Modalita getModalita() {
		return modalita;
	}

	public void setModalita(Modalita modalita) {
		this.modalita = modalita;
	}

	public String getCapSessioneSimultanea() {
		return capSessioneSimultanea;
	}

	public void setCapSessioneSimultanea(String capSessioneSimultanea) {
		this.capSessioneSimultanea = capSessioneSimultanea;
	}
	
	public Comune getComuneSessioneSimultanea() {
		return comuneSessioneSimultanea;
	}

	public void setComuneSessioneSimultanea(Comune comuneSessioneSimultanea) {
		this.comuneSessioneSimultanea = comuneSessioneSimultanea;
	}

	public Provincia getProvinciaSessioneSimultanea() {
		return provinciaSessioneSimultanea;
	}

	public void setProvinciaSessioneSimultanea(Provincia provinciaSessioneSimultanea) {
		this.provinciaSessioneSimultanea = provinciaSessioneSimultanea;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Conferenza getConferenzaPadre() {
		return conferenzaPadre;
	}

	public void setConferenzaPadre(Conferenza conferenzaPadre) {
		this.conferenzaPadre = conferenzaPadre;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public Impresa getImpresa() {
		return impresa;
	}

	public void setImpresa(Impresa impresa) {
		this.impresa = impresa;
	}
	
	public Boolean getFlagAbilitaModificaRichiedente() {
		return flagAbilitaModificaRichiedente;
	}

	public void setFlagAbilitaModificaRichiedente(Boolean flagAbilitaModificaRichiedente) {
		this.flagAbilitaModificaRichiedente = flagAbilitaModificaRichiedente;
	}

	public List<Partecipante> getPartecipantes() {
		return partecipantes;
	}

	public void setPartecipantes(List<Partecipante> partecipantes) {
		this.partecipantes = partecipantes;
	}

	public List<Documento> getDocumentiAggiuntivi() {
		return documentiAggiuntivi;
	}

	public void setDocumentiAggiuntivi(List<Documento> documentiAggiuntivi) {
		this.documentiAggiuntivi = documentiAggiuntivi;
	}

	public List<Documento> getComunicazioniGeneriche() {
		return comunicazioniGeneriche;
	}

	public void setComunicazioniGeneriche(List<Documento> comunicazioniGeneriche) {
		this.comunicazioniGeneriche = comunicazioniGeneriche;
	}

	public List<Documento> getDocumentiInterazioni() {
		return documentiInterazioni;
	}

	public void setDocumentiInterazioni(List<Documento> documentiInterazioni) {
		this.documentiInterazioni = documentiInterazioni;
	}

	public List<Documento> getDocumentiIndizione() {
		return documentiIndizione;
	}

	public void setDocumentiIndizione(List<Documento> documentiIndizione) {
		this.documentiIndizione = documentiIndizione;
	}

	public List<Documento> getDocumentiAccreditamento() {
		return documentiAccreditamento;
	}

	public void setDocumentiAccreditamento(List<Documento> documentiAccreditamento) {
		this.documentiAccreditamento = documentiAccreditamento;
	}

	public List<Documento> getDocumentiPreIstruttoria() {
		return documentiPreIstruttoria;
	}

	public void setDocumentiPreIstruttoria(List<Documento> documentiPreIstruttoria) {
		this.documentiPreIstruttoria = documentiPreIstruttoria;
	}

	public List<Documento> getVerbaleRiunione() {
		return verbaleRiunione;
	}

	public void setVerbaleRiunione(List<Documento> verbaleRiunione) {
		this.verbaleRiunione = verbaleRiunione;
	}

	public List<Documento> getDetermina() {
		return determina;
	}

	public void setDetermina(List<Documento> determina) {
		this.determina = determina;
	}

	public Partecipante addPartecipante(Partecipante partecipante) {
		getPartecipantes().add(partecipante);
		partecipante.setConferenza(this);

		return partecipante;
	}

	public Partecipante removePartecipante(Partecipante partecipante) {
		getPartecipantes().remove(partecipante);
		partecipante.setConferenza(null);

		return partecipante;
	}

	public List<Evento> getEventi() {
		return eventi;
	}

	public void setEventi(List<Evento> eventi) {
		this.eventi = eventi;
	}
	
	
	public List<Votazione> getVotazioni() {
		return votazioni;
	}

	public void setVotazioni(List<Votazione> votazioni) {
		this.votazioni = votazioni;
	}

	public List<ModificaData> getModificaData() {
		return modificaData;
	}

	public void setModificaData(List<ModificaData> modificaData) {
		this.modificaData = modificaData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	

}