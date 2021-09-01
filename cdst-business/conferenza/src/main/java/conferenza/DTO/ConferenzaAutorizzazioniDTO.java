package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "ConferencePermissions")
public class ConferenzaAutorizzazioniDTO {

	@JsonProperty("applicantEditor")
	private ConferenzaModificaRichiedenteDTO modificaRichiedente;

	public ConferenzaModificaRichiedenteDTO getModificaRichiedente() {
		return modificaRichiedente;
	}

	public void setModificaRichiedente(ConferenzaModificaRichiedenteDTO modificaRichiedente) {
		this.modificaRichiedente = modificaRichiedente;
	}

}
