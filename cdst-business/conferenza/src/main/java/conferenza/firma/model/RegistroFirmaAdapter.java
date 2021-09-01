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

import org.hibernate.annotations.Immutable;

/**
CREATE TABLE cdst.registro_firma_adapter (
    id integer NOT NULL DEFAULT nextval('registro_firma_adapter_seq'::regclass),
    fk_registro integer,
    id_documento character varying(255),    
	stato character varying(255) DEFAULT 'LOCKED',    
    id_user character varying(255),
	token character varying(255),
	fase character varying(255),
	crc character varying(500),
	dt_firma_ins date not null default CURRENT_DATE,
	dt_firma_var date,
	fk_tipo_firma integer,
    CONSTRAINT registro_firma_adapter_pkey PRIMARY KEY (id),
    CONSTRAINT fk_tipo_firma FOREIGN KEY (fk_tipo_firma) REFERENCES cdst.tipo_firma (id) MATCH SIMPLE,
	CONSTRAINT fk_stato FOREIGN KEY (stato) REFERENCES cdst.stato_firma (stato) MATCH SIMPLE	
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

 * @author guideluc
 *
 */
@Entity
@Table(name = "registro_firma_adapter")
public class RegistroFirmaAdapter    implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)	
	@Column(name ="id",unique=true, nullable = false)	
    Integer id ;//integer NOT NULL DEFAULT nextval('registro_firma_adapter_seq'::regclass),
	@Column(name ="fk_registro")
	Integer fkRegistro ;//integer,
	@Column(name ="id_documento")
    Integer idDocumento ;//character varying(255),
	@Column(name ="stato")
	String stato ;//character varying(255) DEFAULT 'LOCKED',
	@Column(name ="id_user")
	Integer idUser ;//character varying(255),
	@Column(name ="token")
	String token ;//character varying(255),
	@Column(name ="fase")
	String fase ;//character varying(255),
	@Column(name ="crc")
	String crc ;//character varying(500),
	@Column(name ="dt_firma_ins")
	Date dt_firma_ins ;//date not null default CURRENT_DATE,
	@Column(name ="dt_firma_var")
	Date dt_firma_var ;//date,
	@Column(name ="fk_tipo_firma")
	Integer fk_tipo_firma ;//integer,	
	
	@Column(name ="shot")
	String shot ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFkRegistro() {
		return fkRegistro;
	}
	public void setFkRegistro(Integer fkRegistro) {
		this.fkRegistro = fkRegistro;
	}
	public Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public Date getDt_firma_ins() {
		return dt_firma_ins;
	}
	public void setDt_firma_ins(Date dt_firma_ins) {
		this.dt_firma_ins = dt_firma_ins;
	}
	public Date getDt_firma_var() {
		return dt_firma_var;
	}
	public void setDt_firma_var(Date dt_firma_var) {
		this.dt_firma_var = dt_firma_var;
	}
	public Integer getFk_tipo_firma() {
		return fk_tipo_firma;
	}
	public void setFk_tipo_firma(Integer fk_tipo_firma) {
		this.fk_tipo_firma = fk_tipo_firma;
	}
	public String getShot() {
		return shot;
	}
	public void setShot(String shot) {
		this.shot = shot;
	}
	
}
