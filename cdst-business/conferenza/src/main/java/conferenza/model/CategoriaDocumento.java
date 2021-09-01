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
public class CategoriaDocumento extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2057158764632727727L;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CATEGORIA_DOCUMENTO_TIPOLOGIA_CONFERENZA", 
	
joinColumns = @JoinColumn(name = "CODICE_CATEGORIA_DOCUMENTO", referencedColumnName = "CODICE"), 
inverseJoinColumns = @JoinColumn(name = "CODICE_TIPOLOGIA_CONFERENZA", referencedColumnName = "CODICE", table = "TIPOLOGIA_CONFERENZA"))
	
	private List<TipologiaConferenza> listaTipologiaConfPerCategoriaDoc = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_EVENTO")
	private TipoEvento tipoEvento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_DOCUMENTO")
	private TipoEvento tipologiaDocumento;
	
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public TipoEvento getTipologiaDocumento() {
		return tipologiaDocumento;
	}

	public void setTipologiaDocumento(TipoEvento tipologiaDocumento) {
		this.tipologiaDocumento = tipologiaDocumento;
	}

	public List<TipologiaConferenza> getListaTipologiaConfPerCategoriaDoc() {
		return listaTipologiaConfPerCategoriaDoc;
	}

	public void setListaTipologiaConfPerCategoriaDoc(List<TipologiaConferenza> listaTipologiaConfPerCategoriaDoc) {
		this.listaTipologiaConfPerCategoriaDoc = listaTipologiaConfPerCategoriaDoc;
	}

}
