package conferenza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GruppoUtenti  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -464590587829044245L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gruppo")
	private Integer id_gruppo;

	@Column(name = "fk_utente")
	private Integer fkUtente;

	@Column(name = "fk_utente_responsabile")
	private Integer fkUtenteResponsabile;
	
	public Integer getId_gruppo() {
		return id_gruppo;
	}

	public void setId_gruppo(Integer id_gruppo) {
		this.id_gruppo = id_gruppo;
	}

	public Integer getFk_utente() {
		return fkUtente;
	}

	public void setFk_utente(Integer fk_utente) {
		this.fkUtente = fk_utente;
	}

	public Integer getFk_utente_responsabile() {
		return fkUtenteResponsabile;
	}

	public void setFk_utente_responsabile(Integer fk_utente_responsabile) {
		this.fkUtenteResponsabile = fk_utente_responsabile;
	}
}
