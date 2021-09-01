package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Company")
public class ImpresaDTO extends IdentifiableDTO {

	@JsonProperty("denomination")
	private String denominazione;

	@JsonProperty("fiscalCode")
	private String cf;

	@JsonProperty("vatNumber")
	private String partitaIva;

	@JsonProperty("legalForm")
	private LabelValue formaGiuridica;

	@JsonProperty("area")
	private LabelValue regione;

	@JsonProperty("province")
	private LabelValue provincia;

	@JsonProperty("city")
	private LabelValue comune;

	@JsonProperty("address")
	private String indirizzo;

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

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
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

}
