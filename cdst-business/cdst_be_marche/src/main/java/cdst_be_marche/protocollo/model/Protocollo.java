package cdst_be_marche.protocollo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "protocollo")
@NamedNativeQueries({

	@NamedNativeQuery(name = "ObserverRegistryListener.findAllDocToSubmitToProtocol", 
			query = "SELECT EP.* \r\n" + 
					"FROM cdst.VIEW_SUBMIT_TO_EXTERNAL_PROTOCOL EP\r\n" + 
					"WHERE 1=1\r\n" + 
					"AND EP.codice=?", 			        
			        resultClass = ObserverRegistryListener.class) 
})	
public class Protocollo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1071127892610997765L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="id")
	private Integer id;
		
	@Column(name ="id_documento")
	Integer id_documento;
	@Column(name ="fk_protocollo_meetpad")
	Integer fk_protocollo_meetpad;
	@Column(name ="id_protocollo_esterno")
	Integer id_protocollo_esterno;
	@Column(name ="fk_tipo_protocollo")
	Integer fk_tipo_protocollo;
	@Column(name ="fk_Stati_Protocollo")
	Integer fk_Stati_Protocollo;
	@Column(name ="error")
	String error;
	@Column(name = "numero_protocollo")
	private String numeroProtocollo;
	@Column(name = "data_protocollo")
	private Date dataProtocollo;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_documento() {
		return id_documento;
	}
	public void setId_documento(Integer id_documento) {
		this.id_documento = id_documento;
	}
	public Integer getFk_protocollo_meetpad() {
		return fk_protocollo_meetpad;
	}
	public void setFk_protocollo_meetpad(Integer fk_protocollo_meetpad) {
		this.fk_protocollo_meetpad = fk_protocollo_meetpad;
	}
	public Integer getId_protocollo_esterno() {
		return id_protocollo_esterno;
	}
	public void setId_protocollo_esterno(Integer id_protocollo_esterno) {
		this.id_protocollo_esterno = id_protocollo_esterno;
	}
	public Integer getFk_tipo_protocollo() {
		return fk_tipo_protocollo;
	}
	public void setFk_tipo_protocollo(Integer fk_tipo_protocollo) {
		this.fk_tipo_protocollo = fk_tipo_protocollo;
	}
	public Integer getFk_Stati_Protocollo() {
		return fk_Stati_Protocollo;
	}
	public void setFk_Stati_Protocollo(Integer fk_Stati_Protocollo) {
		this.fk_Stati_Protocollo = fk_Stati_Protocollo;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}
	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}
	public Date getDataProtocollo() {
		return dataProtocollo;
	}
	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	
	
}
