package conferenza.model;

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
@Table(name = "view_error_protocol")
public class DatiProtocolloError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -508017895829562418L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id_registro" ,insertable=false, updatable=false)
	Integer id_registro;
	
	@Column(name ="id_conferenza")
	Integer id_conferenza;
	
	@Column(name ="id_documento")
	Integer id_documento;

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

	public Integer getId_documento() {
		return id_documento;
	}

	public void setId_documento(Integer id_documento) {
		this.id_documento = id_documento;
	}
	
	
	
}
