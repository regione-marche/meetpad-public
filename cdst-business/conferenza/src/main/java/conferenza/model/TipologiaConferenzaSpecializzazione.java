package conferenza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import conferenza.model.bean.Typological;

@Entity
public class TipologiaConferenzaSpecializzazione extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8751771455697186583L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA")
	private TipologiaConferenza tipologiaConferenza;

	@Column(name = "FLAG_AUTOACCREDITAMENTO")
	private Boolean flagAutoaccreditamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_AZIONE")
	private Azione azione;

	public TipologiaConferenza getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(TipologiaConferenza tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public Boolean getFlagAutoaccreditamento() {
		return flagAutoaccreditamento;
	}

	public void setFlagAutoaccreditamento(Boolean flagAutoaccreditamento) {
		this.flagAutoaccreditamento = flagAutoaccreditamento;
	}

	public Azione getAzione() {
		return azione;
	}

	public void setAzione(Azione azione) {
		this.azione = azione;
	}

}
