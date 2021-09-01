package cdst_be_marche.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cdst_be_marche.model.bean.Typological;

@Entity
public class Comune extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1251663020872561400L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PROVINCIA")
	private Provincia provincia;

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

}
