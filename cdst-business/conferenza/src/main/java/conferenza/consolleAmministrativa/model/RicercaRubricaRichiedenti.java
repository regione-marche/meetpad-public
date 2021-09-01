package conferenza.consolleAmministrativa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RicercaRubricaRichiedenti implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2848159998375122788L;
	
	@Id
	@Column(name = "ID_RUBRICA_RICHIEDENTI")
	private Integer idRubricaRichiedenti;
	
	@Column(name = "ID_PERSONA")
	private Integer idPersona;
	
	@Column(name = "NOME_RICHIEDENTE")
	private String nomeRichiedente;
	
	@Column(name = "COGNOME_RICHIEDENTE")
	private String cognomeRichiedente;
	
	@Column(name = "CODICE_FISCALE_RICHIEDENTE")
	private String codiceFiscaleRichiedente;
	
	@Column(name = "CODICE_TIPO_CONF_RUBRICA_RICHIEDENTI")
	private String codiceTipoConfRubricaRichiedenti;
	
	@Column(name = "DESCRIZIONE_TIPO_CONF_RUBRICA_RICHIEDENTI")
	private String descrizioneTipoConfRubricaRichiedenti;
	
	@Column(name = "CODICE_TIPO_CONF_RUBRICA_IMPRESE")
	private String codiceTipoConfRubricaImprese;
	
	@Column(name = "DESCRIZIONE_TIPO_CONF_RUBRICA_IMPRESE")
	private String descrizioneTipoConfRubricaImprese;
	
	@Column(name = "ID_IMPRESA")
	private Integer idImpresa;
	
	@Column(name = "DESCRIZIONE_IMPRESA")
	private String descrizioneImpresa;

	public Integer getIdRubricaRichiedenti() {
		return idRubricaRichiedenti;
	}

	public void setIdRubricaRichiedenti(Integer idRubricaRichiedenti) {
		this.idRubricaRichiedenti = idRubricaRichiedenti;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
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

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getCodiceTipoConfRubricaRichiedenti() {
		return codiceTipoConfRubricaRichiedenti;
	}

	public void setCodiceTipoConfRubricaRichiedenti(String codiceTipoConfRubricaRichiedenti) {
		this.codiceTipoConfRubricaRichiedenti = codiceTipoConfRubricaRichiedenti;
	}

	public String getDescrizioneTipoConfRubricaRichiedenti() {
		return descrizioneTipoConfRubricaRichiedenti;
	}

	public void setDescrizioneTipoConfRubricaRichiedenti(String descrizioneTipoConfRubricaRichiedenti) {
		this.descrizioneTipoConfRubricaRichiedenti = descrizioneTipoConfRubricaRichiedenti;
	}

	public String getCodiceTipoConfRubricaImprese() {
		return codiceTipoConfRubricaImprese;
	}

	public void setCodiceTipoConfRubricaImprese(String codiceTipoConfRubricaImprese) {
		this.codiceTipoConfRubricaImprese = codiceTipoConfRubricaImprese;
	}

	public String getDescrizioneTipoConfRubricaImprese() {
		return descrizioneTipoConfRubricaImprese;
	}

	public void setDescrizioneTipoConfRubricaImprese(String descrizioneTipoConfRubricaImprese) {
		this.descrizioneTipoConfRubricaImprese = descrizioneTipoConfRubricaImprese;
	}

	public Integer getIdImpresa() {
		return idImpresa;
	}

	public void setIdImpresa(Integer idImpresa) {
		this.idImpresa = idImpresa;
	}

	public String getDescrizioneImpresa() {
		return descrizioneImpresa;
	}

	public void setDescrizioneImpresa(String descrizioneImpresa) {
		this.descrizioneImpresa = descrizioneImpresa;
	}

}
