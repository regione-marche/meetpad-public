package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmailPec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -464590787429098245L;
	
	@Id
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PEC")
	private Boolean pec;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getPec() {
		return pec;
	}

	public void setPec(Boolean pec) {
		this.pec = pec;
	}

}
