package cdst_be_marche.util.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
			{
				"aud":[
					"95EHR8rLzdyMkYC5LYEhm9ZTOfga",
					"http:\/\/org.wso2.apimgt\/gateway"
					],
				"sub":"MNTFNC72D52E783O",
				"azp":"95EHR8rLzdyMkYC5LYEhm9ZTOfga",
				"amr":[],
				"iss":"https:\/\/wso2.meetpad-dev.eng:9447\/oauth2\/token",
				"exp":1539195195,
				"iat":1539191595,
				"nonce":"eee5bacc-d536-43b1-abce-b0b9b9da8b74"
			}
 * @author guideluc
 *
 */
public class TokenJWT implements Serializable {  

	@JsonProperty("aud")	
	private List<String> aud;	
	
	@JsonProperty("sub")	
	private String sub;
	
	@JsonProperty("azp")	
	private String azp;
	
	@JsonProperty("amr")	
	private List<String> amr;	
	
	@JsonProperty("iss")	
	private String iss;
	
	@JsonProperty("exp")	
	private String exp;

	@JsonProperty("iat")	
	private String iat;	
	
	@JsonProperty("nonce")	
	private String nonce;

	public List<String> getAud() {
		return aud;
	}

	public void setAud(List<String> aud) {
		this.aud = aud;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getAzp() {
		return azp;
	}

	public void setAzp(String azp) {
		this.azp = azp;
	}

	public List<String> getAmr() {
		return amr;
	}

	public void setAmr(List<String> amr) {
		this.amr = amr;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getIat() {
		return iat;
	}

	public void setIat(String iat) {
		this.iat = iat;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
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
	public static TokenJWT getObj(String sJson) 
			throws JsonParseException, JsonMappingException, IOException {		
		ObjectMapper mapper = new ObjectMapper();
		return  mapper.readValue(sJson, TokenJWT.class);
	}
}
