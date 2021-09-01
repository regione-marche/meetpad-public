package conferenza.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import conferenza.model.bean.Typological;

@Entity
public class RuoloPartecipante extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2041342566402806748L;
	
	@Column(name = "OBBLIGO_ESPRESSIONE_PARERE")
	private Boolean obbligoEspressioneParere;

	@OneToMany(mappedBy = "ruoloPartecipante")
	List<Partecipante> listaPartecipanti;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "RUOLO_PARTECIPANTE_RUOLO_PERSONA", joinColumns = @JoinColumn(name = "CODICE_RUOLO_PARTECIPANTE", 
		referencedColumnName = "CODICE"), inverseJoinColumns = @JoinColumn(name = "CODICE_RUOLO_PERSONA", 
		referencedColumnName = "CODICE", table = "RUOLO_PERSONA"))
	private List<RuoloPersona> listaRuoloPersonaPerRuoloPart = new ArrayList<>();
	
	public Boolean getObbligoEspressioneParere() {
		return obbligoEspressioneParere;
	}

	public void setObbligoEspressioneParere(Boolean obbligoEspressioneParere) {
		this.obbligoEspressioneParere = obbligoEspressioneParere;
	}

	public List<Partecipante> getListaPartecipanti() {
		return listaPartecipanti;
	}

	public void setListaPartecipanti(List<Partecipante> listaPartecipanti) {
		this.listaPartecipanti = listaPartecipanti;
	}
	
	public List<RuoloPersona> getListaRuoloPersonaPerRuoloPart() {
		return listaRuoloPersonaPerRuoloPart;
	}

	public void setListaRuoloPersonaPerRuoloPart(List<RuoloPersona> listaRuoloPersonaPerRuoloPart) {
		this.listaRuoloPersonaPerRuoloPart = listaRuoloPersonaPerRuoloPart;
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
