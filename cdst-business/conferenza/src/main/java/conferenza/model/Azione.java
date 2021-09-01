package conferenza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import conferenza.model.bean.Typological;

@Entity
public class Azione extends Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1988433584039032472L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ATTIVITA")
	private Attivita attivita;
	
	@Column(name = "DESCRIZIONE_LUNGA")
	private String descrizione_lunga;

	public Attivita getAttivita() {
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}

	public String getDescrizione_lunga() {
		return descrizione_lunga;
	}

	public void setDescrizione_lunga(String descrizione_lunga) {
		this.descrizione_lunga = descrizione_lunga;
	}

}
