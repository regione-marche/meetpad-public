package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="PreviewAuthority")
public class EntePreviewDTO {
	
	@JsonProperty(value = "idAuthority")
	private Integer idEnte;
	
	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "flagProsecutingAdministration")
	private Boolean flagAmmProc;
	
	@JsonProperty(value = "pec")
	private String pec;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getFlagAmmProc() {
		return flagAmmProc;
	}

	public void setFlagAmmProc(Boolean flagAmmProc) {
		this.flagAmmProc = flagAmmProc;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

}
