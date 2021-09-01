package conferenza.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import conferenza.model.bean.Typological;

@Entity
public class TipologiaConferenza extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2099159083791539396L;

	@Column(name = "FLAG_ORARIO_VISIBILE")
	private Boolean flagOrarioVisibile;

	@Column(name = "FLAG_MODALITA_INCONTRO_MODIFICABILE")
	private Boolean flagModalitaIncontroModificabile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_MODALITA")
	private Modalita modalita;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CATEGORIA_DOCUMENTO_TIPOLOGIA_CONFERENZA", joinColumns = @JoinColumn(name = "CODICE_TIPOLOGIA_CONFERENZA", referencedColumnName = "CODICE"), inverseJoinColumns = @JoinColumn(name = "CODICE_CATEGORIA_DOCUMENTO", referencedColumnName = "CODICE", table = "CATEGORIA_DOCUMENTO"))
	private List<CategoriaDocumento> listaCategoriaDocPerTipologiaConf = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "COMPETENZA_AUTORIZZATIVA_TIPOLOGIA_CONFERENZA", joinColumns = @JoinColumn(name = "CODICE_TIPOLOGIA_CONFERENZA", referencedColumnName = "CODICE"), inverseJoinColumns = @JoinColumn(name = "CODICE_COMPETENZA_AUTORIZZATIVA", referencedColumnName = "CODICE", table = "COMPETENZA_AUTORIZZATIVA"))
	private List<CompetenzaAutorizzativa> listaCompAutoPerTipologiaConf = new ArrayList<>();

	public Boolean getFlagOrarioVisibile() {
		return flagOrarioVisibile;
	}

	public void setFlagOrarioVisibile(Boolean flagOrarioVisibile) {
		this.flagOrarioVisibile = flagOrarioVisibile;
	}

	public Boolean getFlagModalitaIncontroModificabile() {
		return flagModalitaIncontroModificabile;
	}

	public void setFlagModalitaIncontroModificabile(Boolean flagModalitaIncontroModificabile) {
		this.flagModalitaIncontroModificabile = flagModalitaIncontroModificabile;
	}

	public Modalita getModalita() {
		return modalita;
	}

	public void setModalita(Modalita modalita) {
		this.modalita = modalita;
	}

	public List<CategoriaDocumento> getListaCategoriaDocPerTipologiaConf() {
		return listaCategoriaDocPerTipologiaConf;
	}

	public void setListaCategoriaDocPerTipologiaConf(List<CategoriaDocumento> listaCategoriaDocPerTipologiaConf) {
		this.listaCategoriaDocPerTipologiaConf = listaCategoriaDocPerTipologiaConf;
	}

	public List<CompetenzaAutorizzativa> getListaCompAutoPerTipologiaConf() {
		return listaCompAutoPerTipologiaConf;
	}

	public void setListaCompAutoPerTipologiaConf(List<CompetenzaAutorizzativa> listaCompAutoPerTipologiaConf) {
		this.listaCompAutoPerTipologiaConf = listaCompAutoPerTipologiaConf;
	}
}
