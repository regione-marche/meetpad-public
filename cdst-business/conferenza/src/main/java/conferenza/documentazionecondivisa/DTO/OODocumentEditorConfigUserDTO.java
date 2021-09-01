package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 *		{
 *			"id": "useridoo",
 *			"name": "codiceFiscale+@oo.com"
 *		}
 * </pre>
 * 
 * @author arosina
 *
 */
public class OODocumentEditorConfigUserDTO {

	@JsonProperty("id")
	private String id;

	@JsonProperty("name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
