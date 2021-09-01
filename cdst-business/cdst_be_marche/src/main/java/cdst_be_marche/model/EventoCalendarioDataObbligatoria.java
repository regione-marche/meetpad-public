package cdst_be_marche.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EventoCalendarioDataObbligatoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3896691856036383488L;
	
	@Id
	@Column(name = "ID_EVENTO_CALENDARIO_DATA_OBBLIGATORIA")
	private Integer idDataObbligatoria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA")
	private TipologiaConferenza tipologiaConferenza;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_EVENTI_CALENDARIO")
	private EventiCalendario eventiCalendario;
	
	@Column(name = "FLAG_OBBLIGATORIO")
	private Boolean flagObbligatorio;
	
	@Column(name = "MESSAGGIO_ERRORE")
	private String messaggioErrore;
	
	@Column(name = "CAMPO_ERRORE")
	private String campoErrore;
	
	public Integer getIdDataObbligatoria() {
		return idDataObbligatoria;
	}

	public void setIdDataObbligatoria(Integer idDataObbligatoria) {
		this.idDataObbligatoria = idDataObbligatoria;
	}

	public TipologiaConferenza getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(TipologiaConferenza tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public EventiCalendario getEventiCalendario() {
		return eventiCalendario;
	}

	public void setEventiCalendario(EventiCalendario eventiCalendario) {
		this.eventiCalendario = eventiCalendario;
	}

	public Boolean getFlagObbligatorio() {
		return flagObbligatorio;
	}

	public void setFlagObbligatorio(Boolean flagObbligatorio) {
		this.flagObbligatorio = flagObbligatorio;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}

	public String getCampoErrore() {
		return campoErrore;
	}

	public void setCampoErrore(String campoErrore) {
		this.campoErrore = campoErrore;
	}

}
