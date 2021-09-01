package conferenza.consolleAmministrativa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RicercaResponsabileConferenza implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4525683009012209844L;
	
	@Id
	@Column(name = "ID_RESPONSABILE_CONFERENZA")
	private Integer idResponsabileConferenza;
	
	@Column(name = "ID_AMMINISTRAZIONE_PROCEDENTE")
	private Integer idAmmProcedente;
	
	@Column(name = "AMMINISTRAZIONE_PROCEDENTE")
	private String ammProcedente;
	
	@Column(name = "ID_PERSONA_RESPONSABILE")
	private Integer idPersonaResponsabile;
	
	@Column(name = "NOME_RESPONSABILE")
	private String nomeResponsabile;
	
	@Column(name = "COGNOME_RESPONSABILE")
	private String cognomeResponsabile;
	
	@Column(name = "CODICE_FISCALE_RESPONSABILE")
	private String codiceFiscaleResponsabile;

	public Integer getIdResponsabileConferenza() {
		return idResponsabileConferenza;
	}

	public void setIdResponsabileConferenza(Integer idResponsabileConferenza) {
		this.idResponsabileConferenza = idResponsabileConferenza;
	}

	public Integer getIdAmmProcedente() {
		return idAmmProcedente;
	}

	public void setIdAmmProcedente(Integer idAmmProcedente) {
		this.idAmmProcedente = idAmmProcedente;
	}

	public String getAmmProcedente() {
		return ammProcedente;
	}

	public void setAmmProcedente(String ammProcedente) {
		this.ammProcedente = ammProcedente;
	}

	public Integer getIdPersonaResponsabile() {
		return idPersonaResponsabile;
	}

	public void setIdPersonaResponsabile(Integer idPersonaResponsabile) {
		this.idPersonaResponsabile = idPersonaResponsabile;
	}

	public String getNomeResponsabile() {
		return nomeResponsabile;
	}

	public void setNomeResponsabile(String nomeResponsabile) {
		this.nomeResponsabile = nomeResponsabile;
	}

	public String getCognomeResponsabile() {
		return cognomeResponsabile;
	}

	public void setCognomeResponsabile(String cognomeResponsabile) {
		this.cognomeResponsabile = cognomeResponsabile;
	}

	public String getCodiceFiscaleResponsabile() {
		return codiceFiscaleResponsabile;
	}

	public void setCodiceFiscaleResponsabile(String codiceFiscaleResponsabile) {
		this.codiceFiscaleResponsabile = codiceFiscaleResponsabile;
	}

}
