package cdst_be_marche.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cdst_be_marche.model.bean.Typological;

@Entity
public class Azione extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1988433584039032472L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ATTIVITA")
	private Attivita attivita;

	public Attivita getAttivita() {
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}

}
