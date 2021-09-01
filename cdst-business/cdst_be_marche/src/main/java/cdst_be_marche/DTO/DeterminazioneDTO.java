package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Determination")
public class DeterminazioneDTO {
	
	@JsonProperty("determinationObject")
	private String oggettoDeterminazione;

	public String getOggettoDeterminazione() {
		return oggettoDeterminazione;
	}

	public void setOggettoDeterminazione(String oggettoDeterminazione) {
		this.oggettoDeterminazione = oggettoDeterminazione;
	}

}
