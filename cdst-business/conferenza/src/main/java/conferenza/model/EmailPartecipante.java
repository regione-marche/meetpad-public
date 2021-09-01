package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Email alternativa per il partecipante
 * 
 */
@Entity
public class EmailPartecipante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5897854535117982858L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EMAIL_PARTECIPANTE")
	private Integer id;
	
	@Column(name = "EMAIL")
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PARTECIPANTE")
	private Partecipante partecipante;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Partecipante getPartecipante() {
		return partecipante;
	}

	public void setPartecipante(Partecipante partecipante) {
		this.partecipante = partecipante;
	}
	
	

}