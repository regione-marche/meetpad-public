package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RubricaDelegati implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015463461081765932L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUBRICA_DELEGATO")
	private Integer idRubricaDelegato;
	
	@Column(name = "RIF_ESTERNO")
	private String riferimentoEsterno;
	
	@Column(name = "NOME_DOCUMENTO")
	private String nomeDocumento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE")
	private TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERSONA")
	private Persona persona;
	
	public Integer getIdRubricaDelegato() {
		return idRubricaDelegato;
	}

	public void setIdRubricaDelegato(Integer idRubricaDelegato) {
		this.idRubricaDelegato = idRubricaDelegato;
	}

	public TipologiaConferenzaSpecializzazione getTipologiaConferenzaSpecializzazione() {
		return tipologiaConferenzaSpecializzazione;
	}

	public void setTipologiaConferenzaSpecializzazione(
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione) {
		this.tipologiaConferenzaSpecializzazione = tipologiaConferenzaSpecializzazione;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
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
