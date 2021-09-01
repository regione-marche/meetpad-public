package conferenza.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
	private String uploadDir;
	private String uploadSubdir;
	private String tokenExpireMinutes;
	private String downloadContextPath;
	private String delegateDir;
	private String delegateSubdir;

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

	public String getDelegateDir() {
		return delegateDir;
	}

	public void setDelegateDir(String delegateDir) {
		this.delegateDir = delegateDir;
	}

	public String getDelegateSubdir() {
		return delegateSubdir;
	}

	public void setDelegateSubdir(String delegateSubdir) {
		this.delegateSubdir = delegateSubdir;
	}
}