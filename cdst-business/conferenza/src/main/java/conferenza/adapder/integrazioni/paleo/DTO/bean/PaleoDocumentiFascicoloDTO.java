package conferenza.adapder.integrazioni.paleo.DTO.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaleoDocumentiFascicoloDTO {

	//questo serve per il download
	@JsonProperty("fileCode")
	private String codiceDocumento;

	//questo è un numero logico; un id doc..ma non è usato per il download!?!?
	@JsonProperty("fileDescription")
	private String descrizioneDocumento;

	public String getCodiceDocumento() {
		return codiceDocumento;
	}

	public void setCodiceDocumento(String codiceDocumento) {
		this.codiceDocumento = codiceDocumento;
	}

	public String getDescrizioneDocumento() {
		return descrizioneDocumento;
	}

	public void setDescrizioneDocumento(String descrizioneDocumento) {
		this.descrizioneDocumento = descrizioneDocumento;
	}

	
	
}
