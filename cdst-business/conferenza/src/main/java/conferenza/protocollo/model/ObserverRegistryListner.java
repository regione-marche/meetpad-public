package conferenza.protocollo.model;

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
public class ObserverRegistryListner   implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_registro")
	Integer id_registro;
	
	@Column(name ="id_conferenza")
	Integer id_conferenza;
	@Column(name ="id_pratica")
	Integer id_pratica;
	@Column(name ="id_fascicolo")
	String id_fascicolo;	
	@Column(name ="id_evento")
	Integer id_evento;
	@Column(name ="id_tipo_evento")
	String id_tipo_evento;
	@Column(name ="id_documento")
	Integer id_documento;
	@Column(name ="tipologia_documento")
	String  tipologia_documento;

	
}
