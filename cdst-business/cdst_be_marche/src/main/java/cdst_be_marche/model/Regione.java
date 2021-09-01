package cdst_be_marche.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import cdst_be_marche.model.bean.Typological;

@Entity
public class Regione extends Typological{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4650961643800443255L;
	
	@OneToMany(mappedBy = "regione")
	private List<Provincia> provincie;

	public List<Provincia> getProvincie() {
		return provincie;
	}

	public void setProvincie(List<Provincia> provincie) {
		this.provincie = provincie;
	}

}
