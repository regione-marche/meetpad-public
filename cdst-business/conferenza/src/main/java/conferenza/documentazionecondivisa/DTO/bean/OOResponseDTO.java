package conferenza.documentazionecondivisa.DTO.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author guideluc
 *
 */
public class OOResponseDTO {
	
	@JsonProperty("token")
	String token;		//"qsn/tG/HdRQkMwodjR3WWK2+xPCN9SRQH67+ebe9KGTr12wgoma47k7tByS0IGvyGU63pfqMuni/yqO6WRmnPRLFuGNssGY5xtnF/gLwuASjzWcFLtFjJQ+vrWHh5pcLox7HlkX61rpkNGVtpPZ8kw==",
	@JsonProperty("expires")
	String expires; // "2020-02-04T15:36:19.2745430Z",
	@JsonProperty("sms")
	Boolean sms ;//": false,
	@JsonProperty("phoneNoise")
	String phoneNoise;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
	public Boolean getSms() {
		return sms;
	}
	public void setSms(Boolean sms) {
		this.sms = sms;
	}
	public String getPhoneNoise() {
		return phoneNoise;
	}
	public void setPhoneNoise(String phoneNoise) {
		this.phoneNoise = phoneNoise;
	}
	
	
	
}
