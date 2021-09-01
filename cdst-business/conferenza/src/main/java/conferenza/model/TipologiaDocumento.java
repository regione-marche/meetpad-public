package conferenza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import conferenza.model.bean.Typological;

@Entity
public class TipologiaDocumento extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3426342159877376534L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_SEZIONE_DOCUMENTAZIONE")
	private SezioneDocumentazione sezioneDocumentazione;
	
	@Column(name = "FLAG_UPLOAD_CONSOLLE")
	private Boolean flagUploadConsolle;

	public SezioneDocumentazione getSezioneDocumentazione() {
		return sezioneDocumentazione;
	}

	public void setSezioneDocumentazione(SezioneDocumentazione sezioneDocumentazione) {
		this.sezioneDocumentazione = sezioneDocumentazione;
	}

	public Boolean getFlagUploadConsolle() {
		return flagUploadConsolle;
	}

	public void setFlagUploadConsolle(Boolean flagUploadConsolle) {
		this.flagUploadConsolle = flagUploadConsolle;
	}

}
