package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RicercaProtocollo extends PageableDTO{
	
	@JsonProperty("value")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
