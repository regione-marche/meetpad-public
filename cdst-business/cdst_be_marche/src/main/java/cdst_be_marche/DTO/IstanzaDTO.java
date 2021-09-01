package cdst_be_marche.DTO;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Instance")
public class IstanzaDTO {

	@JsonProperty("requestReference")
	private String rifIstanza;

	@JsonProperty("creationDate")
	private String dataCreazionePratica;

	@JsonProperty("conferenceType")
	private LabelValue tipologiaConferenza;

	@NotNull
	@JsonProperty("endIntegrationDate")
	private String termineRichiestaIntegrazioniConferenza;

	@NotNull
	@JsonProperty("endOpinionDate")
	private String termineEspressionePareri;

	@NotNull
	@JsonProperty("firstSessionDate")
	private String primaSessioneSimultanea;

	@NotNull
	@JsonProperty("expirationDate")
	private String dataTermine;

	@JsonProperty("address")
	private IndirizzoSessioneSimultaneaDTO indirizzo_sessione_simultanea;
	
	@JsonProperty("conferenceTime")
	private String orarioConferenza;

	public String getRifIstanza() {
		return rifIstanza;
	}

	public void setRifIstanza(String rifIstanza) {
		this.rifIstanza = rifIstanza;
	}

	public String getDataCreazionePratica() {
		return dataCreazionePratica;
	}

	public void setDataCreazionePratica(String dataCreazionePratica) {
		this.dataCreazionePratica = dataCreazionePratica;
	}

	public LabelValue getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(LabelValue tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public String getTermineRichiestaIntegrazioniConferenza() {
		return termineRichiestaIntegrazioniConferenza;
	}

	public void setTermineRichiestaIntegrazioniConferenza(String termineRichiestaIntegrazioniConferenza) {
		this.termineRichiestaIntegrazioniConferenza = termineRichiestaIntegrazioniConferenza;
	}

	public String getTermineEspressionePareri() {
		return termineEspressionePareri;
	}

	public void setTermineEspressionePareri(String termineEspressionePareri) {
		this.termineEspressionePareri = termineEspressionePareri;
	}

	public String getPrimaSessioneSimultanea() {
		return primaSessioneSimultanea;
	}

	public void setPrimaSessioneSimultanea(String primaSessioneSimultanea) {
		this.primaSessioneSimultanea = primaSessioneSimultanea;
	}

	public String getDataTermine() {
		return dataTermine;
	}

	public void setDataTermine(String dataTermine) {
		this.dataTermine = dataTermine;
	}

	public IndirizzoSessioneSimultaneaDTO getIndirizzo_sessione_simultanea() {
		return indirizzo_sessione_simultanea;
	}

	public void setIndirizzo_sessione_simultanea(IndirizzoSessioneSimultaneaDTO indirizzo_sessione_simultanea) {
		this.indirizzo_sessione_simultanea = indirizzo_sessione_simultanea;
	}

	public String getOrarioConferenza() {
		return orarioConferenza;
	}

	public void setOrarioConferenza(String orarioConferenza) {
		this.orarioConferenza = orarioConferenza;
	}

}
