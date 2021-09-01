package conferenza;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	private String uploadDir;
	private String tokenExpireMinutes;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getTokenExpireMinutes() {
		return tokenExpireMinutes;
	}

	public void setTokenExpireMinutes(String tokenExpireMinutes) {
		this.tokenExpireMinutes = tokenExpireMinutes;
	}

}