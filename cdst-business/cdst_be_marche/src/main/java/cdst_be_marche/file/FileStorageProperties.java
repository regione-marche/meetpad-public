package cdst_be_marche.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	private String uploadDir;
	private String uploadSubdir;
	private String tokenExpireMinutes;
	private String downloadContextPath;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getUploadSubdir() {
		return uploadSubdir;
	}

	public void setUploadSubdir(String uploadSubdir) {
		this.uploadSubdir = uploadSubdir;
	}

	public String getTokenExpireMinutes() {
		return tokenExpireMinutes;
	}

	public void setTokenExpireMinutes(String tokenExpireMinutes) {
		this.tokenExpireMinutes = tokenExpireMinutes;
	}

	public String getDownloadContextPath() {
		return downloadContextPath;
	}

	public void setDownloadContextPath(String downloadContextPath) {
		this.downloadContextPath = downloadContextPath;
	}

}