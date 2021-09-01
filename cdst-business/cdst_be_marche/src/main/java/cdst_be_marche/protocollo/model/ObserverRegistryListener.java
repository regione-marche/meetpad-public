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
@Table(name = "VIEW_SUBMIT_TO_EXTERNAL_PROTOCOL")
public class ObserverRegistryListener   implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_registro" ,insertable=false, updatable=false)
	Integer id_registro;
	
	@Column(name ="id_conferenza")
	Integer id_conferenza;
	@Column(name ="id_pratica")
	Integer id_pratica;
	@Column(name ="id_evento")
	Integer id_evento;
	@Column(name ="id_tipo_evento")
	String id_tipo_evento;
	@Column(name ="id_documento")
	Integer id_documento;
	@Column(name ="tipologia_documento")
	String  tipologia_documento;
	@Column(name ="subrscribed_event_type")
	String subrscribed_event_type;

	@Column(name ="id_observer_registry")
	Integer id_observer_registry;

	
	public Integer getId_registro() {
		return id_registro;
	}
	public void setId_registro(Integer id_registro) {
		this.id_registro = id_registro;
	}
	public Integer getId_conferenza() {
		return id_conferenza;
	}
	public void setId_conferenza(Integer id_conferenza) {
		this.id_conferenza = id_conferenza;
	}
	public Integer getId_pratica() {
		return id_pratica;
	}
	public void setId_pratica(Integer id_pratica) {
		this.id_pratica = id_pratica;
	}
	public Integer getId_evento() {
		return id_evento;
	}
	public void setId_evento(Integer id_evento) {
		this.id_evento = id_evento;
	}
	public String getId_tipo_evento() {
		return id_tipo_evento;
	}
	public void setId_tipo_evento(String id_tipo_evento) {
		this.id_tipo_evento = id_tipo_evento;
	}
	public Integer getId_documento() {
		return id_documento;
	}
	public void setId_documento(Integer id_documento) {
		this.id_documento = id_documento;
	}
	public String getTipologia_documento() {
		return tipologia_documento;
	}
	public void setTipologia_documento(String tipologia_documento) {
		this.tipologia_documento = tipologia_documento;
	}
	public String getSubrscribed_event_type() {
		return subrscribed_event_type;
	}
	public void setSubrscribed_event_type(String subrscribed_event_type) {
		this.subrscribed_event_type = subrscribed_event_type;
	}
	public Integer getId_observer_registry() {
		return id_observer_registry;
	}
	public void setId_observer_registry(Integer id_observer_registry) {
		this.id_observer_registry = id_observer_registry;
	}

	
}
