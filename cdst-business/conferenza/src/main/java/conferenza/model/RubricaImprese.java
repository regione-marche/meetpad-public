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
public class RubricaImprese implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7541047094293144964L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RUBRICA_IMPRESE")
	private Integer idRubricaImprese;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_CONFERENZA_SPECIALIZZAZIONE")
	private TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_IMPRESA")
	private Impresa impresa;

	@Column(name = "STEP_MODIFICABILI")
	private Boolean stepConferenzaModificabili;

	@Column(name = "LISTA_STEP_MODIFICABILI")
	private String listaStepConferenzaModificabili;

	public Integer getIdRubricaImprese() {
		return idRubricaImprese;
	}

	public void setIdRubricaImprese(Integer idRubricaImprese) {
		this.idRubricaImprese = idRubricaImprese;
	}

	public TipologiaConferenzaSpecializzazione getTipologiaConferenzaSpecializzazione() {
		return tipologiaConferenzaSpecializzazione;
	}

	public void setTipologiaConferenzaSpecializzazione(
			TipologiaConferenzaSpecializzazione tipologiaConferenzaSpecializzazione) {
		this.tipologiaConferenzaSpecializzazione = tipologiaConferenzaSpecializzazione;
	}

	public Impresa getImpresa() {
		return impresa;
	}

	public void setImpresa(Impresa impresa) {
		this.impresa = impresa;
	}

	public Boolean getStepConferenzaModificabili() {
		return stepConferenzaModificabili;
	}

	public void setStepConferenzaModificabili(Boolean stepConferenzaModificabili) {
		this.stepConferenzaModificabili = stepConferenzaModificabili;
	}

	public String getListaStepConferenzaModificabili() {
		return listaStepConferenzaModificabili;
	}

	public void setListaStepConferenzaModificabili(String listaStepConferenzaModificabili) {
		this.listaStepConferenzaModificabili = listaStepConferenzaModificabili;
	}

}
