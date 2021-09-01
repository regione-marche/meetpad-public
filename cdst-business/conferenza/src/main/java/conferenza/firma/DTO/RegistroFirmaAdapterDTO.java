package conferenza.firma.DTO;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;
import conferenza.firma.model.RegistroFirmaAdapter;

public class RegistroFirmaAdapterDTO  extends IdentifiableDTO {

		
	@JsonProperty("fk_registro")
	Integer fkRegistro ;//integer,
	@JsonProperty("id_documento")
    Integer idDocumento ;//character varying(255),
	@JsonProperty("stato")
	String stato ;//character varying(255) DEFAULT 'LOCKED',
	@JsonProperty("id_user")
	Integer idUser ;//character varying(255),
	@JsonProperty("token")
	String token ;//character varying(255),
	@JsonProperty("fase")
	String fase ;//character varying(255),
	@JsonProperty("crc")
	String crc ;//character varying(500),
	@JsonProperty("dt_firma_ins")
	Date dt_firma_ins ;//date not null default CURRENT_DATE,
	@JsonProperty("dt_firma_var")
	Date dt_firma_var ;//date,
	@JsonProperty("fk_tipo_firma")
	Integer fk_tipo_firma ;//integer,	
	@JsonProperty("shot")
	String shot ;
	
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
	
	public static RegistroFirmaAdapter getRegistroFirmaAdapter(RegistroFirmaAdapterDTO dto) {
		RegistroFirmaAdapter adapter=new RegistroFirmaAdapter();
		adapter.setCrc(dto.getCrc());
		adapter.setDt_firma_ins(dto.getDt_firma_ins());
		adapter.setDt_firma_var(dto.getDt_firma_var());
		adapter.setFase(dto.getFase());
		adapter.setFk_tipo_firma(dto.getFk_tipo_firma());
		adapter.setFkRegistro(dto.getFkRegistro());
		adapter.setId(dto.getId());
		adapter.setIdDocumento(dto.getIdDocumento());
		adapter.setIdUser(dto.getIdUser());
		adapter.setStato(dto.getStato());
		adapter.setToken(dto.getToken());
		adapter.setShot(dto.getShot());		
		return adapter;
	}

	public static FirmaDTO getFirmaDTO(RegistroFirmaAdapterDTO dto) {
		FirmaDTO firma=new FirmaDTO();
		firma.setFk_tipo_firma(dto.getFk_tipo_firma());
		firma.setIdDocumento(dto.getIdDocumento());
		firma.setIdUser(dto.getIdUser());
		firma.setSessioneFirma(dto.getToken());
		firma.setShot(dto.getShot());
		return firma;
	}

	
}
