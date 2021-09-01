package conferenza.documentazionecondivisa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OOConfiguration {

	
    @Value("${onlyoffice.url.login}")
    private String urlLogin;

    @Value("${onlyoffice.url.createuser}")
    private String urlCreateUser;    

    @Value("${onlyoffice.url.upladfile}")
    private String urlUpladFile;    

    @Value("${onlyoffice.url.editfile}")
    private String urlEditFile;        
    
    @Value("${onlyoffice.admin.user}")
    private String adminUser;    
    
    @Value("${onlyoffice.admin.password}")
    private String adminPassword;    

    @Value("${onlyoffice.url.root}")
    private String urlRoot;    

    @Value("${onlyoffice.url.downloadfile}")
    private String urlDownloadfile; 

    @Value("${onlyoffice.url.downloadfileOO}")
    private String urlOOCommunityDownloadfile; 
    
    @Value("${onlyoffice.url.callback}")
    private String  callback;
    
    @Value("${onlyoffice.url.dockserverapi}")
    private String dockserverapi;
    
	public String getUrlDownloadfile() {
		return urlDownloadfile;
	}

	public void setUrlDownloadfile(String urlDownloadfile) {
		this.urlDownloadfile = urlDownloadfile;
	}

	public String getUrlRoot() {
		return urlRoot;
	}

	public void setUrlRoot(String urlRoot) {
		this.urlRoot = urlRoot;
	}

	public String getUrlLogin() {
		return urlLogin;
	}

	public void setUrlLogin(String urlLogin) {
		this.urlLogin = urlLogin;
	}

	public String getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getUrlCreateUser() {
		return urlCreateUser;
	}

	public void setUrlCreateUser(String urlCreateUser) {
		this.urlCreateUser = urlCreateUser;
	}

	public String getUrlUpladFile() {
		return urlUpladFile;
	}

	public void setUrlUpladFile(String urlUpladFile) {
		this.urlUpladFile = urlUpladFile;
	}

	public String getUrlEditFile() {
		return urlEditFile;
	}

	public void setUrlEditFile(String urlEditFile) {
		this.urlEditFile = urlEditFile;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getDockserverapi() {
		return dockserverapi;
	}

	public void setDockserverapi(String dockserverapi) {
		this.dockserverapi = dockserverapi;
	}

	public String getUrlOOCommunityDownloadfile() {
		return urlOOCommunityDownloadfile;
	}

	public void setUrlOOCommunityDownloadfile(String urlOOCommunityDownloadfile) {
		this.urlOOCommunityDownloadfile = urlOOCommunityDownloadfile;
	}



	
	
}
