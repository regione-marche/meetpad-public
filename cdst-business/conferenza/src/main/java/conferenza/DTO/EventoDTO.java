package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Event")
public class EventoDTO extends IdentifiableDTO {
	
	@JsonProperty(value = "date")
	private String data;
	
	@JsonProperty(value = "type")
	private LabelValue tipoEvento;
	
	@JsonProperty(value = "sender")
	private String mittente;
	
	@JsonProperty(value = "subject")
	private LabelValue oggettoRichiesta;
	
	@JsonProperty(value = "statusDocument")
	private String statoFirma;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LabelValue getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(LabelValue tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public LabelValue getOggettoRichiesta() {
		return oggettoRichiesta;
	}

	public void setOggettoRichiesta(LabelValue oggettoRichiesta) {
		this.oggettoRichiesta = oggettoRichiesta;
	}
	
	public String getStatoFirma() {
		return statoFirma;
	}

	public void setStatoFirma(String statoFirma) {
		this.statoFirma = statoFirma;
	}
}
