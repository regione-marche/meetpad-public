package conferenza.consolleAmministrativa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RicercaRubricaPartecipanti implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4855219572969719986L;

	@Id
	@Column(name = "ID_RUBRICA_PARTECIPANTI")
	private Integer idRubricaPartecipanti;
	
	@Column(name = "ID_ENTE")
	private Integer idEnte;
	
	@Column(name = "DESCRIZIONE_ENTE")
	private String nomeEnte;
	
	@Column(name = "CODICE_FISCALE_ENTE")
	private String codiceFiscaleEnte;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "CODICE_TIPOLOGIA_CONFERENZA")
	private String codiceTipologiaConferenza;
	
	@Column(name = "DESCRIZIONE_TIPOLOGIA_CONFERENZA")
	private String descrizioneTipologiaConferenza;
	
	@Column(name = "CODICE_RUOLO_PARTECIPANTE")
	private String codiceRuoloPartecipante;
	
	@Column(name = "DESCRIZIONE_RUOLO_PARTECIPANTE")
	private String descrizioneRuoloPartecipante;

	public Integer getIdRubricaPartecipanti() {
		return idRubricaPartecipanti;
	}

	public void setIdRubricaPartecipanti(Integer idRubricaPartecipanti) {
		this.idRubricaPartecipanti = idRubricaPartecipanti;
	}

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

	public String getNomeEnte() {
		return nomeEnte;
	}

	public void setNomeEnte(String nomeEnte) {
		this.nomeEnte = nomeEnte;
	}

	public String getCodiceFiscaleEnte() {
		return codiceFiscaleEnte;
	}

	public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {
		this.codiceFiscaleEnte = codiceFiscaleEnte;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCodiceRuoloPartecipante() {
		return codiceRuoloPartecipante;
	}

	public void setCodiceRuoloPartecipante(String codiceRuoloPartecipante) {
		this.codiceRuoloPartecipante = codiceRuoloPartecipante;
	}

	public String getDescrizioneRuoloPartecipante() {
		return descrizioneRuoloPartecipante;
	}

	public void setDescrizioneRuoloPartecipante(String descrizioneRuoloPartecipante) {
		this.descrizioneRuoloPartecipante = descrizioneRuoloPartecipante;
	}

}
