package conferenza.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import conferenza.model.bean.Typological;

@Entity
public class RuoloPersona extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8891502690469489535L;

	@OneToMany(mappedBy = "ruoloPersona")
	List<Accreditamento> accreditamenti;

	public List<Accreditamento> getAccreditamenti() {
		return accreditamenti;
	}

	public void setAccreditamenti(List<Accreditamento> accreditamenti) {
		this.accreditamenti = accreditamenti;
	}

	public Accreditamento addPersonaAccreditata(Accreditamento accreditamento) {
		getAccreditamenti().add(accreditamento);
		accreditamento.setRuoloPersona(this);

		return accreditamento;
	}

	public Accreditamento removePersonaAccreditata(Accreditamento accreditamento) {
		getAccreditamenti().remove(accreditamento);
		accreditamento.setRuoloPersona(null);

		return accreditamento;
	}

}
