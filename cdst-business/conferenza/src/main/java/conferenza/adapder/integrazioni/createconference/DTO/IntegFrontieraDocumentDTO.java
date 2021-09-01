package conferenza.adapder.integrazioni.createconference.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Oggetto Astratto per intecettare la documentazione dal livello Adapter verso il registro
 * @author guideluc
 *
 */
public class IntegFrontieraDocumentDTO {
	@JsonProperty("id")
	private Integer id;

	@JsonProperty("id_pratica")
	private Integer id_pratica;
	
	@JsonProperty("id_alfresco")
	private String id_alfresco;

	@JsonProperty("id_paleo")
	private String id_paleo;

	@JsonProperty("id_fs")
	private String id_fs;
	
	
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

	public String getId_paleo() {
		return id_paleo;
	}

	public void setId_paleo(String id_paleo) {
		this.id_paleo = id_paleo;
	}

	public String getId_fs() {
		return id_fs;
	}

	public void setId_fs(String id_fs) {
		this.id_fs = id_fs;
	}

}
