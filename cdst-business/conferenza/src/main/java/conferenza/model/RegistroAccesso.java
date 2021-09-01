package conferenza.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RegistroAccesso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2744053185981754446L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_REGISTRO_ACCESSO")
	private Integer idRegistroAccesso;
	
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name = "FLAG_UTENTE_ESISTENTE")
	private Boolean flagUtenteEsistente;
	
	@Column(name = "DESCRIZIONE_TIPO_PROFILO")
	private String descrizioneTipoProfilo;
	
	@Column(name = "DATA_ACCESSO")
	private Date dataAccesso;

	public Integer getIdRegistroAccesso() {
		return idRegistroAccesso;
	}

	public void setIdRegistroAccesso(Integer idRegistroAccesso) {
		this.idRegistroAccesso = idRegistroAccesso;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Boolean getFlagUtenteEsistente() {
		return flagUtenteEsistente;
	}

	public void setFlagUtenteEsistente(Boolean flagUtenteEsistente) {
		this.flagUtenteEsistente = flagUtenteEsistente;
	}

	public String getDescrizioneTipoProfilo() {
		return descrizioneTipoProfilo;
	}

	public void setDescrizioneTipoProfilo(String descrizioneTipoProfilo) {
		this.descrizioneTipoProfilo = descrizioneTipoProfilo;
	}

	public Date getDataAccesso() {
		return dataAccesso;
	}

	public void setDataAccesso(Date dataAccesso) {
		this.dataAccesso = dataAccesso;
	}


}
