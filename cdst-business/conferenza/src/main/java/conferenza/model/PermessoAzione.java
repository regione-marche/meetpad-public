package conferenza.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import conferenza.model.bean.Typological;

@Entity
public class PermessoAzione extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5947603628754363751L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_EVENTO")
	private TipoEvento tipoEvento;

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

}
