package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Applicant")
public class RichiedenteDTO {

	@JsonProperty("requestReference")
	private String riferimentoIstanza;
	
	@JsonProperty("startDate")
	private String dataAvvio;
	
	@JsonProperty("name")
	private String nomeRichiedente;
	
	@JsonProperty("type")
	private LabelValue tipologia;
	
	@JsonProperty("surname")
	private String cognomeRichiedente;
	
	@JsonProperty("activity")
	private LabelValue attivita;
	
	@JsonProperty("fiscalCode")
	private String codiceFiscaleRichiedente;
	
	@JsonProperty("action")
	private LabelValue azione;
	
	private String pec;

	public String getRiferimentoIstanza() {
		return riferimentoIstanza;
	}

	public void setRiferimentoIstanza(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}

	public String getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(String dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public String getNomeRichiedente() {
		return nomeRichiedente;
	}

	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}

	public LabelValue getTipologia() {
		return tipologia;
	}

	public void setTipologia(LabelValue tipologia) {
		this.tipologia = tipologia;
	}

	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}

	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}

	public LabelValue getAttivita() {
		return attivita;
	}

	public void setAttivita(LabelValue attivita) {
		this.attivita = attivita;
	}

	
	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}


	public LabelValue getAzione() {
		return azione;
	}

	public void setAzione(LabelValue azione) {
		this.azione = azione;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

}
