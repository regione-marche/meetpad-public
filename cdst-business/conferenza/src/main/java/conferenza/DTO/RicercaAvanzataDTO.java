package conferenza.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="AdvancedSearch")
public class RicercaAvanzataDTO extends PageableDTO {

	@ApiModelProperty(name = "state")
	private String stato;

	@ApiModelProperty(name = "conferenceType")
	private String tipologiaConferenza;

	@ApiModelProperty(name = "requestReference")
	private String riferimentoIstanza;

	@ApiModelProperty(name = "applicantFiscalCode")
	private String cfRichiedente;

	@ApiModelProperty(name = "province")
	private String provincia;

	@ApiModelProperty(name = "city")
	private String comune;
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public void setState(String stato) {
		this.stato = stato;
	}

	public String getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(String tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}
	
	public void setConferenceType(String tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public String getRiferimentoIstanza() {
		return riferimentoIstanza;
	}

	public void setRiferimentoIstanza(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}
	
	public void setRequestReference(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}

	public String getCfRichiedente() {
		return cfRichiedente;
	}

	public void setCfRichiedente(String cfRichiedente) {
		this.cfRichiedente = cfRichiedente;
	}
	
	public void setApplicantFiscalCode(String cfRichiedente) {
		this.cfRichiedente = cfRichiedente;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public void setProvince(String provincia) {
		this.provincia = provincia;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}
	
	public void setCity(String comune) {
		this.comune = comune;
	}
	
	public String getColonnaOrdinamento(String colonnaOrdinamento) {
		if (colonnaOrdinamento.equals("state")) {
			colonnaOrdinamento = "stato";
		}
		if (colonnaOrdinamento.equals("conferenceType")) {
			colonnaOrdinamento = "tipologiaConferenza";
		}
		if (colonnaOrdinamento.equals("requestReference")) {
			colonnaOrdinamento = "riferimentoIstanza";
		}
		if (colonnaOrdinamento.equals("applicantFiscalCode")) {
			colonnaOrdinamento = "cfRichiedente";
		}
		if (colonnaOrdinamento.equals("province")) {
			colonnaOrdinamento = "provincia";
		}
		if (colonnaOrdinamento.equals("city")) {
			colonnaOrdinamento = "comune";
		}
		return colonnaOrdinamento;
	}
}
