package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Address")
public class IndirizzoSessioneSimultaneaDTO {
	
	@JsonProperty("type")
	private LabelValue modalita;
	
	@JsonProperty("address")
	private String riferimento;
	
	@JsonProperty("cap")
	private String cap;
	
	@JsonProperty("city")
	private LabelValue comune;
	
	@JsonProperty("province")
	private LabelValue provincia;

	public LabelValue getModalita() {
		return modalita;
	}

	public void setModalita(LabelValue modalita) {
		this.modalita = modalita;
	}
	
	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public LabelValue getComune() {
		return comune;
	}

	public void setComune(LabelValue comune) {
		this.comune = comune;
	}

	public LabelValue getProvincia() {
		return provincia;
	}

	public void setProvincia(LabelValue provincia) {
		this.provincia = provincia;
	}


}
