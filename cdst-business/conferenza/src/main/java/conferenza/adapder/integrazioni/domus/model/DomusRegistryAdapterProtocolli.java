package conferenza.adapder.integrazioni.domus.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**

 * @author guideluc
	id integer NOT NULL DEFAULT nextval('domus_registry_adapter_protocolli_seq'::regclass),    
	fk_testata integer,
	idDocuemto character varying(255),
	signature  character varying(255),
	dt_ins timestamp   
 */
@Entity
@Table(name = "domus_registry_adapter_protocolli")
public class DomusRegistryAdapterProtocolli implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "fk_testata")
	private Integer fkTestata;
	
	/**
	 * E' il docNumber di Domus
	 */
	@Column(name = "id_documento")
	private String idDocumento;
	
	@Column(name = "signature")
	private String signature;	
	
	@Column(name = "dt_ins")
	private Date dtIns;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkTestata() {
		return fkTestata;
	}

	public void setFkTestata(Integer fkTestata) {
		this.fkTestata = fkTestata;
	}

	public String getIdDocuemto() {
		return idDocumento;
	}

	public void setIdDocuemto(String idDocuemto) {
		this.idDocumento = idDocuemto;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	
	
	
	
	
}
