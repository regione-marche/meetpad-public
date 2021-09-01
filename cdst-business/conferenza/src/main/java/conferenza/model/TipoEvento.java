package conferenza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import conferenza.model.bean.Typological;


@Entity
public class TipoEvento extends Typological {

	private static final long serialVersionUID = -6235557326924241749L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_OGGETTO_EVENTO")
	private OggettoEvento oggettoEvento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_DOCUMENTO")
	private TipologiaDocumento tipologiaDocumento;
	
	@Column(name = "FLAG_INVIO_EMAIL")
	private Boolean flagInvioEmail;
	
	@Column(name = "FLAG_ALLEGATO")
	private Boolean flagAllegato;

	public OggettoEvento getOggettoEvento() {
		return oggettoEvento;
	}

	public void setOggettoEvento(OggettoEvento oggettoEvento) {
		this.oggettoEvento = oggettoEvento;
	}

	public TipologiaDocumento getTipologiaDocumento() {
		return tipologiaDocumento;
	}

	public void setTipologiaDocumento(TipologiaDocumento tipologiaDocumento) {
		this.tipologiaDocumento = tipologiaDocumento;
	}

	public Boolean getFlagInvioEmail() {
		return flagInvioEmail;
	}

	public void setFlagInvioEmail(Boolean flagInvioEmail) {
		this.flagInvioEmail = flagInvioEmail;
	}

	public Boolean getFlagAllegato() {
		return flagAllegato;
	}

	public void setFlagAllegato(Boolean flagAllegato) {
		this.flagAllegato = flagAllegato;
	}

}
