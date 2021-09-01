package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Localization")
public class LocalizzazioneDTO {
	
	@JsonProperty("province")
	private LabelValue provincia;
	
	@JsonProperty("city")
	private LabelValue comune;
	
	@JsonProperty("address")
	private String indirizzo;

	public LabelValue getProvincia() {
		return provincia;
	}

	public void setProvincia(LabelValue provincia) {
		this.provincia = provincia;
	}

	public LabelValue getComune() {
		return comune;
	}

	public void setComune(LabelValue comune) {
		this.comune = comune;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

}
