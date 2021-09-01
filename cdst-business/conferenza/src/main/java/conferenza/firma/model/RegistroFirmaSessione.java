package conferenza.firma.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

/**
    id integer NOT NULL DEFAULT nextval('registro_firma_adapter_sessione_seq'::regclass),
    fk_documento integer,
    token character varying(255),    
	dt_sessione_begin date not null default CURRENT_DATE,
	dt_sessione_end date ,
 * @author guideluc
 *
 */
@Entity
@Table(name = "registro_firma_adapter_sessione")
public class RegistroFirmaSessione   implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)	
	@Column(name ="id",unique=true, nullable = false)
    Integer id ;//integer NOT NULL DEFAULT nextval('registro_firma_adapter_sessione_seq'::regclass),
	
	@Column(name ="fk_documento")
    Integer fkDocumento ;
	
	@Column(name ="token")
    String token ;	
	
	@Temporal(TemporalType.TIMESTAMP)	
	@Column(name ="dt_sessione_begin",nullable = false,columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    Date dtSessioneBegin = new Date();	
	
	@Column(name ="dt_sessione_end")
    Date dtSessioneEnd ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkDocumento() {
		return fkDocumento;
	}

	public void setFkDocumento(Integer fkDocumento) {
		this.fkDocumento = fkDocumento;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDtSessioneBegin() {
		return dtSessioneBegin;
	}

	public void setDtSessioneBegin(Date dtSessioneBegin) {
		this.dtSessioneBegin = dtSessioneBegin;
	}

	public Date getDtSessioneEnd() {
		return dtSessioneEnd;
	}

	public void setDtSessioneEnd(Date dtSessioneEnd) {
		this.dtSessioneEnd = dtSessioneEnd;
	}	
	
	
}
