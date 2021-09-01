package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 *	{
 *		"callbackUrl": "getOOCallbackUrl()+idAdapter+codiceFiscale+jwt+",
 *		"user": {
 *			"id": "useridoo",
 *			"name": "codiceFiscale+@oo.com"
 *		}
 *	}
 * </pre>
 * 
 * @author arosina
 *
 */
public class OODocumentEditorConfigDTO {

	@JsonProperty("callbackUrl")
	private String callbackUrl;

	@JsonProperty("user")
	private OODocumentEditorConfigUserDTO user;

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public OODocumentEditorConfigUserDTO getUser() {
		return user;
	}

	public void setUser(OODocumentEditorConfigUserDTO user) {
		this.user = user;
	}

}
