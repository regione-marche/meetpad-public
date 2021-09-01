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
	id integer NOT NULL DEFAULT nextval('domus_registry_adapter_allegati_seq'::regclass),    
	fk_protocollo integer,
	isPrincipale  character varying(1),
	idDocuemto  integer,
	signature character varying(255),
	nome_file character varying(255),
	fk_registro_documento integer,
	dt_ins timestamp 
 */
@Entity
@Table(name = "domus_registry_adapter_allegati")
public class DomusRegistryAdapterAllegati implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "fk_protocollo")
	private Integer fkProtocollo;
	
	@Column(name = "is_principale")
	private String isPrincipale;
	
	@Column(name = "id_documento")
	private String idDocumento;
	
	@Column(name = "signature")
	private String signature;	

	
	@Column(name = "id_file")
	private Integer idFile;		
	
	//probabilmente non serve ..il registro_documento ha l'id del file ..ed documento  ha il nome del file..
	@Deprecated
	@Column(name = "nome_file")
	private String nomeFile;
	
	@Column(name = "fk_registro_documento")
	private Integer fkRegistroDocumento;

	@Column(name = "dt_ins")
	private Date dtIns;		
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkProtocollo() {
		return fkProtocollo;
	}

	public void setFkProtocollo(Integer fkProtocollo) {
		this.fkProtocollo = fkProtocollo;
	}

	public String getIsPrincipale() {
		return isPrincipale;
	}

	public void setIsPrincipale(String isPrincipale) {
		this.isPrincipale = isPrincipale;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public Integer getFkRegistroDocumento() {
		return fkRegistroDocumento;
	}

	public void setFkRegistroDocumento(Integer fkRegistroDocumento) {
		this.fkRegistroDocumento = fkRegistroDocumento;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Integer getIdFile() {
		return idFile;
	}

	public void setIdFile(Integer idFile) {
		this.idFile = idFile;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	
	
	
}
