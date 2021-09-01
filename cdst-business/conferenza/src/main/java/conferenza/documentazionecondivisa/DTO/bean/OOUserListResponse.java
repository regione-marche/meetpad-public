package conferenza.documentazionecondivisa.DTO.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OOUserListResponse {

	@JsonProperty("count")
	Integer count;
	@JsonProperty("status")
	Integer status;//": 0,"
	@JsonProperty("statusCode")
	Integer statusCode; //": 201,
	@JsonProperty("response")
	List<OOUserResponse>  userList;
	
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
	public List<OOUserResponse> getUserList() {
		return userList;
	}
	public void setUserList(List<OOUserResponse> userList) {
		this.userList = userList;
	}
	
	
	
	
}
