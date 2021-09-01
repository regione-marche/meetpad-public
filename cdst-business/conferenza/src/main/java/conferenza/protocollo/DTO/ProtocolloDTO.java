package conferenza.protocollo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;

public class ProtocolloDTO extends IdentifiableDTO {

	
	@JsonProperty("id_documento")
	Integer id_documento;
	@JsonProperty("fk_protocollo")
	Integer fk_protocollo;
	@JsonProperty("id_protocollo_esterno")
	Integer id_protocollo_esterno;
	@JsonProperty("fk_tipo_protocollo")
	Integer fk_tipo_protocollo;
	@JsonProperty("fk_Stati_Protocollo")
	Integer fk_Stati_Protocollo;
	@JsonProperty("error")
	String error;
	public Integer getId_documento() {
		return id_documento;
	}
	public void setId_documento(Integer id_documento) {
		this.id_documento = id_documento;
	}

	public Integer getFk_protocollo() {
		return fk_protocollo;
	}
	public void setFk_protocollo(Integer fk_protocollo) {
		this.fk_protocollo = fk_protocollo;
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
	
	
}
