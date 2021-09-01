package conferenza.consolleAmministrativa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RicercaRubricaDelegati implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1493024837718411987L;

	@Id
	@Column(name = "ID_RUBRICA_DELEGATO")
	private Integer idRubricaDelegato;
	
	@Column(name = "ID_PERSONA")
	private Integer idPersona;
	
	@Column(name = "NOME_DELEGATO")
	private String nomeDelegato;
	
	@Column(name = "COGNOME_DELEGATO")
	private String cognomeDelegato;
	
	@Column(name = "CODICE_FISCALE_DELEGATO")
	private String codiceFiscaleDelegato;
	
	@Column(name = "CODICE_TIPO_CONF_RUBRICA_DELEGATI")
	private String codiceTipoConfRubricaDelegati;
	
	@Column(name = "DESCRIZIONE_TIPO_CONF_RUBRICA_DELEGATI")
	private String descrizioneTipoConfRubricaDelegati;
	
	@Column(name = "RIF_ESTERNO")
	private String riferimentoEsterno;
	
	@Column(name = "NOME_DOCUMENTO")
	private String nomeDocumento;
	
	public Integer getIdRubricaDelegato() {
		return idRubricaDelegato;
	}

	public void setIdRubricaDelegato(Integer idRubricaDelegato) {
		this.idRubricaDelegato = idRubricaDelegato;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getNomeDelegato() {
		return nomeDelegato;
	}

	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}

	public String getCognomeDelegato() {
		return cognomeDelegato;
	}

	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}

	public String getCodiceFiscaleDelegato() {
		return codiceFiscaleDelegato;
	}

	public void setCodiceFiscaleDelegato(String codiceFiscaleDelegato) {
		this.codiceFiscaleDelegato = codiceFiscaleDelegato;
	}

	public String getCodiceTipoConfRubricaDelegati() {
		return codiceTipoConfRubricaDelegati;
	}

	public void setCodiceTipoConfRubricaDelegati(String codiceTipoConfRubricaDelegati) {
		this.codiceTipoConfRubricaDelegati = codiceTipoConfRubricaDelegati;
	}

	public String getDescrizioneTipoConfRubricaDelegati() {
		return descrizioneTipoConfRubricaDelegati;
	}

	public void setDescrizioneTipoConfRubricaDelegati(String descrizioneTipoConfRubricaDelegati) {
		this.descrizioneTipoConfRubricaDelegati = descrizioneTipoConfRubricaDelegati;
	}

	public String getRiferimentoEsterno() {
		return riferimentoEsterno;
	}

	public void setRiferimentoEsterno(String riferimentoEsterno) {
		this.riferimentoEsterno = riferimentoEsterno;
	}
	
	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
}
