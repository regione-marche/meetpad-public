package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.documentazionecondivisa.DTO.bean.OOFileResponseODT;

/**
 * 
 * @author guideluc
 *
 */
public class OOUploadFileReesponseDTO {

	@JsonProperty("count")
	Integer count;
	@JsonProperty("status")
	Integer status;	
	@JsonProperty("statusCode")
	Integer statusCode;
	@JsonProperty("response")
	OOFileResponseODT response;
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
	public OOFileResponseODT getResponse() {
		return response;
	}
	public void setResponse(OOFileResponseODT response) {
		this.response = response;
	}
	
}
