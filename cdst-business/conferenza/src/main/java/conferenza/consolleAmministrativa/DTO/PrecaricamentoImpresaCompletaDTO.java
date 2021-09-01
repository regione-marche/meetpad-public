package conferenza.consolleAmministrativa.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveCompanyComplete")
public class PrecaricamentoImpresaCompletaDTO {
	
	@JsonProperty("conferenceType")
	private LabelValue tipologiaConferenza;
	
	@JsonProperty("name")
	private String nome;
	
	@JsonProperty("fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty("vatNumber")
	private String partitaIva;
	
	@JsonProperty("address")
	private String indirizzo;
	
	@JsonProperty("legalForm")
	private LabelValue formaGiuridica;
	
	@JsonProperty("area")
	private LabelValue regione;
	
	@JsonProperty("province")
	private LabelValue provincia;
	
	@JsonProperty("city")
	private LabelValue comune;
	
	@JsonProperty("editableConferenceStep")
	private Boolean stepConferenzaModificabili;
	
	@JsonProperty("stepList")
	private List<Integer> stepAttivi = new ArrayList<>();

	public String getNome() {
		return nome;
	}

	public LabelValue getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(LabelValue tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public LabelValue getFormaGiuridica() {
		return formaGiuridica;
	}

	public void setFormaGiuridica(LabelValue formaGiuridica) {
		this.formaGiuridica = formaGiuridica;
	}

	public LabelValue getRegione() {
		return regione;
	}

	public void setRegione(LabelValue regione) {
		this.regione = regione;
	}

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

	public Boolean getStepConferenzaModificabili() {
		return stepConferenzaModificabili;
	}

	public void setStepConferenzaModificabili(Boolean stepConferenzaModificabili) {
		this.stepConferenzaModificabili = stepConferenzaModificabili;
	}

	public List<Integer> getStepAttivi() {
		return stepAttivi;
	}

	public void setStepAttivi(List<Integer> stepAttivi) {
		this.stepAttivi = stepAttivi;
	}

}
