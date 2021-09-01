package cdst_be_marche.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cdst_be_marche.model.bean.Typological;

@Entity
public class Provincia extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8375125550923242815L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FK_REGIONE")
	private Regione regione;

	@OneToMany(mappedBy = "provincia")
	private List<Comune> comuni;

	public Regione getRegione() {
		return regione;
	}

	public void setRegione(Regione regione) {
		this.regione = regione;
	}

	public List<Comune> getComuni() {
		return comuni;
	}

	public void setComuni(List<Comune> comuni) {
		this.comuni = comuni;
	}

}
