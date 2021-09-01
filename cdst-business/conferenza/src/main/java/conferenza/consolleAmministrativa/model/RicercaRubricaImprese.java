package conferenza.consolleAmministrativa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RicercaRubricaImprese implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5187294421850788603L;
	
	@Id
	@Column(name = "ID_RUBRICA_IMPRESE")
	private Integer idRubricaImprese;
	
	@Column(name = "ID_IMPRESA")
	private Integer idImpresa;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name = "PARTITA_IVA")
	private String partitaIva;
	
	@Column(name = "CODICE_TIPOLOGIA_CONFERENZA")
	private String codiceTipologiaConferenza;
	
	@Column(name = "DESCRIZIONE_TIPOLOGIA_CONFERENZA")
	private String descrizioneTipologiaConferenza;

	public Integer getIdRubricaImprese() {
		return idRubricaImprese;
	}

	public void setIdRubricaImprese(Integer idRubricaImprese) {
		this.idRubricaImprese = idRubricaImprese;
	}

	public Integer getIdImpresa() {
		return idImpresa;
	}

	public void setIdImpresa(Integer idImpresa) {
		this.idImpresa = idImpresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
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

}
