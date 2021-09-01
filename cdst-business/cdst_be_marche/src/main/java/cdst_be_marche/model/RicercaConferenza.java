package cdst_be_marche.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

@Entity
@Where(clause = "DATA_FINE is Null")
public class RicercaConferenza implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9197186699552495142L;
	
	@Id
	@Column(name = "ID_CONFERENZA")
	private Integer idConferenza;
	
	@Column(name = "RIFERIMENTO_ISTANZA")
	private String riferimentoIstanza;
	
	@Column(name = "CODICE_TIPOLOGIA_CONFERENZA")
	private String codiceTipologiaConferenza;
	
	@Column(name = "DESCRIZIONE_TIPOLOGIA_CONFERENZA")
	private String descrizioneTipologiaConferenza;
	
	@Column(name = "CODICE_STATO")
	private String codiceStato;
	
	@Column(name = "DESCRIZIONE_STATO")
	private String descrizioneStato;
	
	@Column(name = "CODICE_FISCALE_RICHIEDENTE")
	private String codiceFiscaleRichiedente;
	
	@Column(name = "NOME_RICHIEDENTE")
	private String nomeRichiedente;
	
	@Column(name = "COGNOME_RICHIEDENTE")
	private String cognomeRichiedente;
	
	@Column(name = "CF_RESPONSABILE_CONFERENZA")
	private String codiceFiscaleResponsabileConferenza;
	
	@Column(name = "CF_CREATORE_CONFERENZA")
	private String codiceFiscaleCreatoreConferenza;
	
	@Column(name = "CODICE_PROVINCIA")
	private String codiceProvincia;
	
	@Column(name = "DESCRIZIONE_PROVINCIA")
	private String descrizioneProvincia;
	
	@Column(name = "CODICE_COMUNE")
	private String codiceComune;
	
	@Column(name = "DESCRIZIONE_COMUNE")
	private String descrizioneComune;
	
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
	
	@Column(name = "ID_ENTE")
	private Integer idEnte;
	
	@Column(name = "DESCRIZIONE_AMMINISTRAZIONE_PROCEDENTE")
	private String descrizioneAmmProcedente;
	
	@Column(name = "DATA_FINE")
	private Date dataFine;

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getRiferimentoIstanza() {
		return riferimentoIstanza;
	}

	public void setRiferimentoIstanza(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}

	public String getCodiceTipologiaConferenza() {
		return codiceTipologiaConferenza;
	}

	public void setCodiceTipologiaConferenza(String codiceTipologiaConferenza) {
		this.codiceTipologiaConferenza = codiceTipologiaConferenza;
	}

	public String getDescrizioneTipologiaConferenza() {
		return descrizioneTipologiaConferenza;
	}

	public void setDescrizioneTipologiaConferenza(String descrizioneTipologiaConferenza) {
		this.descrizioneTipologiaConferenza = descrizioneTipologiaConferenza;
	}

	public String getCodiceStato() {
		return codiceStato;
	}

	public void setCodiceStato(String codiceStato) {
		this.codiceStato = codiceStato;
	}

	public String getDescrizioneStato() {
		return descrizioneStato;
	}

	public void setDescrizioneStato(String descrizioneStato) {
		this.descrizioneStato = descrizioneStato;
	}

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
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

	public String getCodiceProvincia() {
		return codiceProvincia;
	}

	public void setCodiceProvincia(String codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}

	public String getDescrizioneProvincia() {
		return descrizioneProvincia;
	}

	public void setDescrizioneProvincia(String descrizioneProvincia) {
		this.descrizioneProvincia = descrizioneProvincia;
	}

	public String getCodiceComune() {
		return codiceComune;
	}

	public void setCodiceComune(String codiceComune) {
		this.codiceComune = codiceComune;
	}

	public String getDescrizioneComune() {
		return descrizioneComune;
	}

	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
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

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

	public String getDescrizioneAmmProcedente() {
		return descrizioneAmmProcedente;
	}

	public void setDescrizioneAmmProcedente(String descrizioneAmmProcedente) {
		this.descrizioneAmmProcedente = descrizioneAmmProcedente;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
}
