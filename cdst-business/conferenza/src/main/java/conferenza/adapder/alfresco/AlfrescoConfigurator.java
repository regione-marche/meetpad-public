package conferenza.adapder.alfresco;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlfrescoConfigurator {
	
	
	@Value("${alfresco.superadmin.user}")
	private String alfrescoSuperadminUser;

	@Value("${alfresco.superadmin.password}")
	private String alfrescoSuperadminPassword;

	@Value("${alfresco.superadmin.baseurl}")
	private String baseUrl;

	@Value("${alfresco.baseRoot}")
	private String baseRoot;
	
	public String getAlfrescoSuperadminUser() {
		return alfrescoSuperadminUser;
	}

	public void setAlfrescoSuperadminUser(String alfrescoSuperadminUser) {
		this.alfrescoSuperadminUser = alfrescoSuperadminUser;
	}

	public String getAlfrescoSuperadminPassword() {
		return alfrescoSuperadminPassword;
	}

	public void setAlfrescoSuperadminPassword(String alfrescoSuperadminPassword) {
		this.alfrescoSuperadminPassword = alfrescoSuperadminPassword;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseRoot() {
		return baseRoot;
	}

	public void setBaseRoot(String baseRoot) {
		this.baseRoot = baseRoot;
	}
	
	
	
	
	
}
