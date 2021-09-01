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

import conferenza.model.bean._Typological;

/**
 * Rappresenta una voce del filtro delle tipologia della conferenza specializzazione
 * 
 * @author mfunaro
 *
 */
@Entity
public class SelezioneTipologiaConferenza implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2072767064914967364L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SELEZIONE_TIPOLOGIA_CONFERENZA")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_UTENTE")
	private Utente utente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CODICE")
	private TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public TipologiaConferenzaSpecializzazione getTipologiaConferenzaSpecializzazione() {
		return tipologiaConferenzaSpecializzazione;
	}

	public void setTipologiaConferenzaSpecializzazione(TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione) {
		this.tipologiaConferenzaSpecializzazione = tipologiaConferenzaSpecializzazione;
	}


}
