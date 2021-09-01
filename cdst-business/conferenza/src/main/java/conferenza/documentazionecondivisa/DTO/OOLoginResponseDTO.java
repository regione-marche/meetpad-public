package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.documentazionecondivisa.DTO.bean.OOResponseDTO;

/**
{
"count": 1,
"status": 0,
"statusCode": 201,
"response":{
"token": "qsn/tG/HdRQkMwodjR3WWK2+xPCN9SRQH67+ebe9KGTr12wgoma47k7tByS0IGvyGU63pfqMuni/yqO6WRmnPRLFuGNssGY5xtnF/gLwuASjzWcFLtFjJQ+vrWHh5pcLox7HlkX61rpkNGVtpPZ8kw==",
"expires": "2020-02-04T15:36:19.2745430Z",
"sms": false,
"phoneNoise": null
}
} 
 * 
 * */
public class OOLoginResponseDTO {
	
	@JsonProperty("count")
	Integer count;
	@JsonProperty("status")
	Integer status;//": 0,"
	@JsonProperty("statusCode")
	Integer statusCode; //": 201,
	@JsonProperty("response")
	OOResponseDTO response;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public OOResponseDTO getResponse() {
		return response;
	}
	public void setResponse(OOResponseDTO response) {
		this.response = response;
	}
	

	
	
}
