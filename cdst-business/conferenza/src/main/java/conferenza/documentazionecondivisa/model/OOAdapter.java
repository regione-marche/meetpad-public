package conferenza.documentazionecondivisa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "oo_adapter")
public class OOAdapter   implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Integer id;
	
	@Column(name ="fk_registro")
	Integer  fk_registro;
	
	@Column(name ="fk_documento")
	Integer  fkDocumento;
	
	@Column(name ="id_doc_oo")
	String   id_doc_oo;
	@Column(name ="id_folder_oo")
	String   id_folder_oo;
	@Column(name ="id_user_oo")
	String   id_user_oo;
	
	
	
	public Integer getFkDocumento() {
		return fkDocumento;
	}
	public void setFkDocumento(Integer fkDocumento) {
		this.fkDocumento = fkDocumento;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFk_registro() {
		return fk_registro;
	}
	public void setFk_registro(Integer fk_registro) {
		this.fk_registro = fk_registro;
	}
	public String getId_doc_oo() {
		return id_doc_oo;
	}
	public void setId_doc_oo(String id_doc_oo) {
		this.id_doc_oo = id_doc_oo;
	}
	public String getId_folder_oo() {
		return id_folder_oo;
	}
	public void setId_folder_oo(String id_folder_oo) {
		this.id_folder_oo = id_folder_oo;
	}
	public String getId_user_oo() {
		return id_user_oo;
	}
	public void setId_user_oo(String id_user_oo) {
		this.id_user_oo = id_user_oo;
	}	
	
	
	
}
