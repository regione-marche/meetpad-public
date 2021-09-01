package cdst_be_marche.util.model;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;





/**
{
	"http:\/\/wso2.org\/claims\/username": "DLCGDU71D16C632S",
	"http:\/\/wso2.org\/claims\/enduser": "DLCGDU71D16C632S@carbon.super",
	"sub": "BNCMRA80A01B354C"
}
 * @author arosina
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class TokenJWT_min implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2462135377218544687L;
	
	@JsonProperty("http://wso2.org/claims/username")
	private String username;
	@JsonProperty("http://wso2.org/claims/enduser")
	private String enduser;
	@JsonProperty("sub")
	private String sub;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEnduser() {
		return enduser;
	}
	public void setEnduser(String enduser) {
		this.enduser = enduser;
	}	
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
	/**
	 * Ritorna l'oggetto TokenJWT come Stringa
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getJsonString() 
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this); 		
	} 	
	
	/**
	 * 
	 * @param sJson
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static TokenJWT_min getObj(String sJson) 
			throws JsonParseException, JsonMappingException, IOException {		
		ObjectMapper mapper = new ObjectMapper();
		return  mapper.readValue(sJson, TokenJWT_min.class);
	}
	
}
