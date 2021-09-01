package cdst_be_marche.protocollo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "observer_registry")
public class ObserverRegistry   implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Integer id;
	
	@Column(name ="codice")
	String codice;
	@Column(name ="subrscribed_event_type")
	String subrscribed_event_type ;
	@Column(name ="nome")
	String name ;
	@Column(name ="class")
	String classname;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getSubrscribed_event_type() {
		return subrscribed_event_type;
	}
	public void setSubrscribed_event_type(String subrscribed_event_type) {
		this.subrscribed_event_type = subrscribed_event_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}	
	
	
}
