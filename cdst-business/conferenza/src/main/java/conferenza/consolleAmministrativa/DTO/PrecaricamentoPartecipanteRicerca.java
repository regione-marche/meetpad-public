package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.PageableDTO;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveParticipantSearch")
public class PrecaricamentoPartecipanteRicerca extends PageableDTO{
	
	@JsonProperty("name")
	private String nome;
	
	@JsonProperty("conferenceType")
	private String codiceTipoConf;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setName(String nome) {
		this.nome = nome;
	}

	public String getCodiceTipoConf() {
		return codiceTipoConf;
	}

	public void setCodiceTipoConf(String codiceTipoConf) {
		this.codiceTipoConf = codiceTipoConf;
	}
	
	public void setConferenceType(String codiceTipoConf) {
		this.codiceTipoConf = codiceTipoConf;
	}

}
