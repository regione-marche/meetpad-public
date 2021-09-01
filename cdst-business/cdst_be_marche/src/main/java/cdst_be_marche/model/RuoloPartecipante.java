package cdst_be_marche.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import cdst_be_marche.model.bean.Typological;

@Entity
public class RuoloPartecipante extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2041342566402806748L;

	@OneToMany(mappedBy = "ruoloPartecipante")
	List<Partecipante> listaPartecipanti;

	@OneToMany(mappedBy = "ruoloPartecipante")
	private List<RuoloPersona> ruoliPersona;

	public List<Partecipante> getListaPartecipanti() {
		return listaPartecipanti;
	}

	public void setListaPartecipanti(List<Partecipante> listaPartecipanti) {
		this.listaPartecipanti = listaPartecipanti;
	}

	public List<RuoloPersona> getRuoliPersona() {
		return ruoliPersona;
	}

	public void setRuoliPersona(List<RuoloPersona> ruoliPersona) {
		this.ruoliPersona = ruoliPersona;
	}

	public Partecipante addPartecipante(Partecipante partecipante) {
		getListaPartecipanti().add(partecipante);
		partecipante.setRuoloPartecipante(this);

		return partecipante;
	}

	public Partecipante removePartecipante(Partecipante partecipante) {
		getListaPartecipanti().remove(partecipante);
		partecipante.setRuoloPartecipante(null);

		return partecipante;
	}

}
