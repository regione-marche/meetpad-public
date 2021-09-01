package conferenza.adapder.integrazioni.suap.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;

public class IntegSuapFrontieraDocumentDTO {
	
	@JsonProperty("id")
	private Integer id;

	@JsonProperty("id_pratica")
	private Integer id_pratica;
	
	@JsonProperty("id_alfresco")
	private String id_alfresco;
	
	@JsonProperty("nome")
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_pratica() {
		return id_pratica;
	}

	public void setId_pratica(Integer id_pratica) {
		this.id_pratica = id_pratica;
	}

	public String getId_alfresco() {
		return id_alfresco;
	}

	public void setId_alfresco(String id_alfresco) {
		this.id_alfresco = id_alfresco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
