package cdst_be_marche.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import cdst_be_marche.model.bean.Typological;

@Entity
public class RuoloPersona extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8891502690469489535L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUOLO_PARTECIPANTE")
	private RuoloPartecipante ruoloPartecipante;

	@OneToMany(mappedBy = "ruoloPersona")
	List<Accreditamento> accreditamenti;

	public RuoloPartecipante getRuoloPartecipante() {
		return ruoloPartecipante;
	}

	public void setRuoloPartecipante(RuoloPartecipante ruoloPartecipante) {
		this.ruoloPartecipante = ruoloPartecipante;
	}

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
