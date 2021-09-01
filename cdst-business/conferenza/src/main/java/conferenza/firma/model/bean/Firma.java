package conferenza.firma.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Immutable
@Table(name ="viewfirma")
public class Firma    implements Serializable {
	
	@JsonProperty("id_documento")
	@Id
	@Column(name ="id_documento")
	Integer idDocumento;
	@JsonProperty("fk_tipo_firma")
	@Column(name ="fk_tipo_firma")
	Integer fk_tipo_firma ;
	@JsonProperty("sessione_firma")
	@Column(name ="sessione_firma")
	String sessioneFirma;
	@JsonProperty("shot")
	@Column(name ="shot")
	String shot;
	@JsonProperty("id_user")
	@Column(name ="id_user")
	Integer idUser;
	@JsonProperty("crc")
	@Column(name ="crc")
	String crc;
	@JsonProperty("stato")
	@Column(name ="stato")
	String stato;
	@JsonProperty("id_conferenza")
	@Column(name ="id_conferenza")
	Integer idConferenza;
	
	public Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Integer getFk_tipo_firma() {
		return fk_tipo_firma;
	}
	public void setFk_tipo_firma(Integer fk_tipo_firma) {
		this.fk_tipo_firma = fk_tipo_firma;
	}
	public String getSessioneFirma() {
		return sessioneFirma;
	}
	public void setSessioneFirma(String sessioneFirma) {
		this.sessioneFirma = sessioneFirma;
	}
	public String getShot() {
		return shot;
	}
	public void setShot(String shot) {
		this.shot = shot;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
