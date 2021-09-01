package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentAdapterDTO extends DocumentoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8441716982654597567L;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	@JsonProperty("tipo")
	private String tipo;


	
	
}
