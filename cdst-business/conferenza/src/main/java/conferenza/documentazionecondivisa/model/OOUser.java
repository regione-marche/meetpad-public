package conferenza.documentazionecondivisa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oo_user")
public class OOUser implements Serializable {

	@Id
	@Column(name ="id_user_oo")
	String   id_user_oo;

	
	@Column(name ="cf_user")
	String   cf_user;


	public String getCf_user() {
		return cf_user;
	}

	public void setCf_user(String cf_user) {
		this.cf_user = cf_user;
	}

	public String getId_user_oo() {
		return id_user_oo;
	}

	public void setId_user_oo(String id_user_oo) {
		this.id_user_oo = id_user_oo;
	}

	
	
}

