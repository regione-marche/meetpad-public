package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.PageableDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="AuthoritySearch")
public class EnteRicerca extends PageableDTO {
	
	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "flagProsecutingAdministration")
	private Boolean flagAmmProc;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setName(String nome) {
		this.nome = nome;
	}

	public Boolean getFlagAmmProc() {
		return flagAmmProc;
	}

	public void setFlagAmmProc(Boolean flagAmmProc) {
		this.flagAmmProc = flagAmmProc;
	}
	
	public void setFlagProsecutingAdministration(Boolean flagAmmProc) {
		this.flagAmmProc = flagAmmProc;
	}

}
