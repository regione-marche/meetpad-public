package conferenza.documentazionecondivisa.DTO.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
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
 * @author guideluc
 *
 */
public class OOUserResponse {
	@JsonProperty("id")
	String id;//": "1687ec33-b8f9-4e5c-b527-870983692c8f",
	@JsonProperty("userName")
	String userName;//": "guidodl1",
	@JsonProperty("isVisitor")
	Boolean  isVisitor;//": false,
	@JsonProperty("firstName")
	String firstName;//": "Guidodl",
	@JsonProperty("lastName")
	String lastName;//": "YAHOO",
	@JsonProperty("email")
	String email;//": "guidodl@eng.it",
	@JsonProperty("status")
	Integer status;//": 1,
	@JsonProperty("activationStatus")
	Integer activationStatus;//": 2,
	@JsonProperty("terminated")
	Boolean terminated;//": null,
	@JsonProperty("department")
	String department;//": "",
	@JsonProperty("workFrom")
	String workFrom;//": "2019-01-23T00:00:00.0000000Z",
	@JsonProperty("displayName")
	String displayName;//": "Guidodl YAHOO",
	@JsonProperty("title")
	String title;//": "ing",
	@JsonProperty("avatarMedium")
	String avatarMedium;//": "/skins/default/images/default_user_photo_size_48-48.png",
	@JsonProperty("avatar")
	String avatar;//": "/skins/default/images/default_user_photo_size_82-82.png",
	@JsonProperty("isOnline")
	Boolean isOnline;//": false,
	@JsonProperty("isAdmin")
	Boolean isAdmin;//": false,
	@JsonProperty("isLDAP")
	Boolean isLDAP;//": false,
	@JsonProperty("isOwner")
	Boolean isOwner;//": false,
	@JsonProperty("isSSO")
	Boolean isSSO;//": false,
	@JsonProperty("avatarSmall")
	String avatarSmall;//": "/skins/default/images/default_user_photo_size_32-32.png",
	@JsonProperty("profileUrl")
	String profileUrl;//": "http://192.168.157.130:8082/products/people/profile.aspx?user=guidodl1"
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Boolean getIsVisitor() {
		return isVisitor;
	}
	public void setIsVisitor(Boolean isVisitor) {
		this.isVisitor = isVisitor;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getActivationStatus() {
		return activationStatus;
	}
	public void setActivationStatus(Integer activationStatus) {
		this.activationStatus = activationStatus;
	}
	public Boolean getTerminated() {
		return terminated;
	}
	public void setTerminated(Boolean terminated) {
		this.terminated = terminated;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getWorkFrom() {
		return workFrom;
	}
	public void setWorkFrom(String workFrom) {
		this.workFrom = workFrom;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAvatarMedium() {
		return avatarMedium;
	}
	public void setAvatarMedium(String avatarMedium) {
		this.avatarMedium = avatarMedium;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Boolean getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Boolean isOnline) {
		this.isOnline = isOnline;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Boolean getIsLDAP() {
		return isLDAP;
	}
	public void setIsLDAP(Boolean isLDAP) {
		this.isLDAP = isLDAP;
	}
	public Boolean getIsOwner() {
		return isOwner;
	}
	public void setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
	}
	public Boolean getIsSSO() {
		return isSSO;
	}
	public void setIsSSO(Boolean isSSO) {
		this.isSSO = isSSO;
	}
	public String getAvatarSmall() {
		return avatarSmall;
	}
	public void setAvatarSmall(String avatarSmall) {
		this.avatarSmall = avatarSmall;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

		
}
