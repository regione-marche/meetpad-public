package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Procedure")
public class PraticaDTO {
	
	@JsonProperty("applicant")
	private RichiedenteDTO richiedente;
	
	@JsonProperty("localization")
	private LocalizzazioneDTO localizzazione;
	
	@JsonProperty("company")
	private ImpresaDTO compagnia;
	
	public RichiedenteDTO getRichiedente() {
		return richiedente;
	}
	public void setRichiedente(RichiedenteDTO richiedente) {
		this.richiedente = richiedente;
	}
	public LocalizzazioneDTO getLocalizzazione() {
		return localizzazione;
	}
	public void setLocalizzazione(LocalizzazioneDTO localizzazione) {
		this.localizzazione = localizzazione;
	}
	public ImpresaDTO getCompagnia() {
		return compagnia;
	}
	public void setCompagnia(ImpresaDTO compagnia) {
		this.compagnia = compagnia;
	}

}
