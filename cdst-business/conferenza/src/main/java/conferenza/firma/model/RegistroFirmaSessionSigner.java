package conferenza.firma.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author guideluc
 *
 */
/**
 * @author guideluc
 *
 */
@Entity
@Table(name = "registro_firma_sessione_signer")
public class RegistroFirmaSessionSigner  implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)	
	@Column(name ="id",unique=true, nullable = false)
    Integer id ;//integer NOT NULL DEFAULT nextval('registro_firma_adapter_sessione_seq'::regclass),
	
	@Column(name ="fk_sessione")
    String fkSessione;

	//id del signer da verificare!?!?!
	@Column(name ="signer")
    Integer signer;
	
	//id del responsabile
	@Column(name ="owner")
    Integer owner;
	
	@Column(name ="dt_ins")
    Date dt_ins = new Date();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFkSessione() {
		return fkSessione;
	}

	public void setFkSessione(String fkSessione) {
		this.fkSessione = fkSessione;
	}

	public Integer getSigner() {
		return signer;
	}

	public void setSigner(Integer signer) {
		this.signer = signer;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public Date getDt_ins() {
		return dt_ins;
	}

	public void setDt_ins(Date dt_ins) {
		this.dt_ins = dt_ins;
	}
	
	
	
}
