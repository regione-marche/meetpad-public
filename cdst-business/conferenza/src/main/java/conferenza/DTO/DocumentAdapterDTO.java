package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentAdapterDTO extends DocumentoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8441716982654597567L;
	
	@JsonProperty("id")
	public String id;
	
	@JsonProperty("name")
	public String name;
	@JsonProperty("tipo")
	public String tipo;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	
	
}
