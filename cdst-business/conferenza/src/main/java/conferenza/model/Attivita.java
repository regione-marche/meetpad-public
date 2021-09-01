package conferenza.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import conferenza.model.bean.Typological;

@Entity
public class Attivita extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3345719795538152523L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_PRATICA")
	private TipologiaPratica tipologiaPratica;

	public TipologiaPratica getTipologiaPratica() {
		return tipologiaPratica;
	}

	public void setTipologiaPratica(TipologiaPratica tipologiaPratica) {
		this.tipologiaPratica = tipologiaPratica;
	}
	
	@OneToMany(mappedBy = "attivita")
	private List<Azione> azioni;

	public List<Azione> getAzioni() {
		return azioni;
	}

	public void setAzioni(List<Azione> azioni) {
		this.azioni = azioni;
	}
	
	public Azione addAzione(Azione azione) {
		getAzioni().add(azione);
		azione.setAttivita(this);

		return azione;
	}

	public Azione removeAzione(Azione azione) {
		getAzioni().remove(azione);
		azione.setAttivita(null);

		return azione;
	}

}
