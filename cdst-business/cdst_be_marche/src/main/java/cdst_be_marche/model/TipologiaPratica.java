package cdst_be_marche.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import cdst_be_marche.model.bean.Typological;

@Entity
public class TipologiaPratica extends Typological{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5198664104474823519L;
	
		@OneToMany(mappedBy = "tipologiaPratica")
		private List<Attivita> activities;

		public List<Attivita> getActivities() {
			return activities;
		}

		public void setActivities(List<Attivita> activities) {
			this.activities = activities;
		}
		
		public Attivita addAttivita(Attivita attivita) {
			getActivities().add(attivita);
			attivita.setTipologiaPratica(this);

			return attivita;
		}

		public Attivita removeAttivita(Attivita attivita) {
			getActivities().remove(attivita);
			attivita.setTipologiaPratica(null);

			return attivita;
		}

}
