package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="SendIndictionMail")
public class InvioMailIndizioneDTO {
	
	@JsonProperty(value = "idAuthority")
	private Integer idEnte;
	
	@JsonProperty(value = "email")
	private String email;

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
