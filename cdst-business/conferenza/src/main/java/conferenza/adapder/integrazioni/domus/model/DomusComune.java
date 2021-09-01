package conferenza.adapder.integrazioni.domus.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author guideluc
 *
 */
@Entity
@Table(name = "domus_comune")
public class DomusComune implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	//è null se il praemtero isdefault is S	
	@Column(name = "CODICE_COMUNE")
	private String codiceComune;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodiceComune() {
		return codiceComune;
	}

	public void setCodiceComune(String codiceComune) {
		this.codiceComune = codiceComune;
	}
	
	
	
}
