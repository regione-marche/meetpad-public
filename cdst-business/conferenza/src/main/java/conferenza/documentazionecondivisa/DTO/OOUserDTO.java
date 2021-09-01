package conferenza.documentazionecondivisa.DTO;

/**
{
  "isVisitor":"false",
  "email":"guidodl@eng.it",
  "firstname":"Guidodl",
  "lastname":"YAHOO",
  "title":"ing"
}
 * @author guideluc
 *
 */
public class OOUserDTO {

	  Boolean isVisitor=new Boolean(false);	//false
	  String email;			//guidodl@eng.it
	  String firstname;		//Guidodl
	  String lastname;		//YAHOO
	  String title="Utente Conferenza";			//ing
	  String id;
	  
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIsVisitor() {
		return isVisitor;
	}
	public void setIsVisitor(Boolean isVisitor) {
		this.isVisitor = isVisitor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	  
	
}
