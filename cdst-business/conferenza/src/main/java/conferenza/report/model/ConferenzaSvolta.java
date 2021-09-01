package conferenza.report.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;




public class ConferenzaSvolta  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "tipo_conferenza")
	private String  tipoConferenza;
	@Column(name = "numero_conferenze_svolte")
	private BigInteger numeroConfrenzeSvolte;
	@Column(name = "totale_enti_partecipanti")
	private BigInteger totaleEntiPartecipanti;
	
	
	public ConferenzaSvolta(String tipoConferenza, BigInteger numeroConfrenzeSvolte,
			BigInteger totaleEntiPartecipanti) {
		super();
		this.tipoConferenza = tipoConferenza;
		this.numeroConfrenzeSvolte = numeroConfrenzeSvolte;
		this.totaleEntiPartecipanti = totaleEntiPartecipanti;
	}
	public String getTipoConferenza() {
		return tipoConferenza;
	}
	public void setTipoConferenza(String tipoConferenza) {
		this.tipoConferenza = tipoConferenza;
	}
	public BigInteger getNumeroConfrenzeSvolte() {
		return numeroConfrenzeSvolte;
	}
	public void setNumeroConfrenzeSvolte(BigInteger numeroConfrenzeSvolte) {
		this.numeroConfrenzeSvolte = numeroConfrenzeSvolte;
	}
	public BigInteger getTotaleEntiPartecipanti() {
		return totaleEntiPartecipanti;
	}
	public void setTotaleEntiPartecipanti(BigInteger totaleEntiPartecipanti) {
		this.totaleEntiPartecipanti = totaleEntiPartecipanti;
	}
	
	
	

}
