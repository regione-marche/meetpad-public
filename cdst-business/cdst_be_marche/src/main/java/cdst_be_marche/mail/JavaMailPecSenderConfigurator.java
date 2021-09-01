package cdst_be_marche.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaMailPecSenderConfigurator {

	@Value("${mailPec.host}")
	private String host;

	@Value("${mailPec.port}")
	private int port;

	@Value("${mailPec.from}")
	private String from;

	@Value("${mailPec.username}")
	private String username;

	@Value("${mailPec.password}")
	private String password;

	// POP3
	@Value("${mailPec.pop3.port}")
	private String pop3Port;

	@Value("${mailPec.pop3.host}")
	private String pop3Host;

	@Value("${mailPec.pop3.folder}")
	private String pop3Folder;

	@Value("${mailPec.enableAsyncCheckMailStatus}")
	private boolean enableAsyncCheckMailStatus;

	@Value("${mailPec.enableAsyncReinoltroMailInErrore}")
	private boolean enableAsyncReinoltroMailInErrore;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPop3Port() {
		return pop3Port;
	}

	public void setPop3Port(String pop3Port) {
		this.pop3Port = pop3Port;
	}

	public String getPop3Host() {
		return pop3Host;
	}

	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	public String getPop3Folder() {
		return pop3Folder;
	}

	public void setPop3Folder(String pop3Folder) {
		this.pop3Folder = pop3Folder;
	}

	public boolean isEnableAsyncCheckMailStatus() {
		return enableAsyncCheckMailStatus;
	}

	public void setEnableAsyncCheckMailStatus(boolean enableAsyncCheckMailStatus) {
		this.enableAsyncCheckMailStatus = enableAsyncCheckMailStatus;
	}

	public boolean isEnableAsyncReinoltroMailInErrore() {
		return enableAsyncReinoltroMailInErrore;
	}

	public void setEnableAsyncReinoltroMailInErrore(boolean enableAsyncReinoltroMailInErrore) {
		this.enableAsyncReinoltroMailInErrore = enableAsyncReinoltroMailInErrore;
	}

}
