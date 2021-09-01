package conferenza.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "DateUpdate")
public class ModificaDataDTO {

	@JsonProperty("code")
	private String codice;
		
	@JsonProperty("description")
	private String descrizone;

	@JsonProperty("date")
	private String data;
	
	@JsonProperty("dateNew")
	private String dataNew;
	
	@JsonProperty("editDate")
	private String dataModifica;
	
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizone() {
		return descrizone;
	}

	public void setDescrizone(String descrizone) {
		this.descrizone = descrizone;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataNew() {
		return dataNew;
	}

	public void setDataNew(String dataNew) {
		this.dataNew = dataNew;
	}

	public String getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(String dataModifica) {
		this.dataModifica = dataModifica;
	}

	
	
	
}
