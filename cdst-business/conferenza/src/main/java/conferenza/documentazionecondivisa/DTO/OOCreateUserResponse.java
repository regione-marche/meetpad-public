package conferenza.documentazionecondivisa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.documentazionecondivisa.DTO.bean.OOUserResponse;

/**
{
"count": 1,
"status": 0,
"statusCode": 201,
"response":{
	"id": "1687ec33-b8f9-4e5c-b527-870983692c8f",
	"userName": "guidodl1",
	"isVisitor": false,
	"firstName": "Guidodl",
	"lastName": "YAHOO",
	"email": "guidodl@eng.it",
	"status": 1,
	"activationStatus": 2,
	"terminated": null,
	"department": "",
	"workFrom": "2019-01-23T00:00:00.0000000Z",
	"displayName": "Guidodl YAHOO",
	"title": "ing",
	"avatarMedium": "/skins/default/images/default_user_photo_size_48-48.png",
	"avatar": "/skins/default/images/default_user_photo_size_82-82.png",
	"isOnline": false,
	"isAdmin": false,
	"isLDAP": false,
	"isOwner": false,
	"isSSO": false,
	"avatarSmall": "/skins/default/images/default_user_photo_size_32-32.png",
	"profileUrl": "http://192.168.157.130:8082/products/people/profile.aspx?user=guidodl1"
}
}
 * @author guideluc
 *
 */
public class OOCreateUserResponse {
	@JsonProperty("count")
	Integer count;
	@JsonProperty("status")
	Integer status;//": 0,"
	@JsonProperty("statusCode")
	Integer statusCode; //": 201,
	@JsonProperty("response")
	OOUserResponse response;
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
	public OOUserResponse getResponse() {
		return response;
	}
	public void setResponse(OOUserResponse response) {
		this.response = response;
	}
	
	
}
