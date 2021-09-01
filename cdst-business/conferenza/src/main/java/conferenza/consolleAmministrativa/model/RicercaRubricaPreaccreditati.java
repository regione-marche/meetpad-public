package conferenza.consolleAmministrativa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RicercaRubricaPreaccreditati implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 175241333922134965L;

	@Id
	@Column(name = "ID_RUBRICA_PREACCREDITATO")
	private Integer idRubricaPreaccreditato;
	
	@Column(name = "ID_PERSONA")
	private Integer idPersona;
	
	@Column(name = "ID_ENTE")
	private Integer idEnte;
	
	@Column(name = "NOME_PREACCREDITATO")
	private String nomePreaccreditato;
	
	@Column(name = "COGNOME_PREACCREDITATO")
	private String cognomePreaccreditato;
	
	@Column(name = "CODICE_FISCALE_PREACCREDITATO")
	private String codiceFiscalePreaccreditato;
	
	@Column(name = "CODICE_TIPO_CONF_PREACCREDITATO")
	private String codiceTipoConfPreaccreditato;
	
	@Column(name = "DESCRIZIONE_TIPO_CONF_PREACCREDITATO")
	private String descrizioneTipoConfPreaccreditato;

	@Column(name = "CODICE_TIPO_PROFILO_PREACCREDITATO")
	private String codiceTipoProfiloPreaccreditato;
	
	@Column(name = "DESCRIZIONE_TIPO_PROFILO_PREACCREDITATO")
	private String descrizioneTipoProfiloPreaccreditato;
	
	@Column(name = "CODICE_FISCALE_ENTE_PREACCREDITATO")
	private String codiceFiscaleEntePreaccreditato;
	
	@Column(name = "DESCRIZIONE_ENTE_PREACCREDITATO")
	private String descrizioneEntePreaccreditato;
	
	public Integer getIdRubricaPreaccreditato() {
		return idRubricaPreaccreditato;
	}

	public void setIdRubricaPreaccreditato(Integer idRubricaPreaccreditato) {
		this.idRubricaPreaccreditato = idRubricaPreaccreditato;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}
	
	public String getNomePreaccreditato() {
		return nomePreaccreditato;
	}

	public void setNomePreaccreditato(String nomePreaccreditato) {
		this.nomePreaccreditato = nomePreaccreditato;
	}

	public String getCognomePreaccreditato() {
		return cognomePreaccreditato;
	}

	public void setCognomePreaccreditato(String cognomePreaccreditato) {
		this.cognomePreaccreditato = cognomePreaccreditato;
	}

	public String getCodiceFiscalePreaccreditato() {
		return codiceFiscalePreaccreditato;
	}

	public void setCodiceFiscalePreaccreditato(String codiceFiscalePreaccreditato) {
		this.codiceFiscalePreaccreditato = codiceFiscalePreaccreditato;
	}

	public String getCodiceTipoConfPreaccreditato() {
		return codiceTipoConfPreaccreditato;
	}

	public void setCodiceTipoConfPreaccreditato(String codiceTipoConfPreaccreditato) {
		this.codiceTipoConfPreaccreditato = codiceTipoConfPreaccreditato;
	}

	public String getDescrizioneTipoConfPreaccreditato() {
		return descrizioneTipoConfPreaccreditato;
	}

	public void setDescrizioneTipoConfPreaccreditato(String descrizioneTipoConfPreaccreditato) {
		this.descrizioneTipoConfPreaccreditato = descrizioneTipoConfPreaccreditato;
	}

	public String getCodiceTipoProfiloPreaccreditato() {
		return codiceTipoProfiloPreaccreditato;
	}

	public void setCodiceTipoProfiloPreaccreditato(String codiceTipoProfiloPreaccreditato) {
		this.codiceTipoProfiloPreaccreditato = codiceTipoProfiloPreaccreditato;
	}

	public String getDescrizioneTipoProfiloPreaccreditato() {
		return descrizioneTipoProfiloPreaccreditato;
	}

	public void setDescrizioneTipoProfiloPreaccreditato(String descrizioneTipoProfiloPreaccreditato) {
		this.descrizioneTipoProfiloPreaccreditato = descrizioneTipoProfiloPreaccreditato;
	}

	public String getCodiceFiscaleEntePreaccreditato() {
		return codiceFiscaleEntePreaccreditato;
	}

	public void setCodiceFiscaleEntePreaccreditato(String codiceFiscaleEntePreaccreditato) {
		this.codiceFiscaleEntePreaccreditato = codiceFiscaleEntePreaccreditato;
	}

	public String getDescrizioneEntePreaccreditato() {
		return descrizioneEntePreaccreditato;
	}

	public void setDescrizioneEntePreaccreditato(String descrizioneEntePreaccreditato) {
		this.descrizioneEntePreaccreditato = descrizioneEntePreaccreditato;
	}
}
