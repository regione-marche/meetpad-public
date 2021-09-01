package conferenza.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "autenticazione")
public class AutenticazioneProperties {
	private Boolean utenteAuditFittizio;
	private String cfUtenteAuditFittizio;
	private Boolean utenteFittizio;
	private String cfUtenteFittizio;
	private Boolean verificaToken;
	private String tokenFilterExcludeUrls;
	
	public Boolean getUtenteAuditFittizio() {
		return utenteAuditFittizio;
	}

	public void setUtenteAuditFittizio(Boolean utenteAuditFittizio) {
		this.utenteAuditFittizio = utenteAuditFittizio;
	}

	public String getCfUtenteAuditFittizio() {
		return cfUtenteAuditFittizio;
	}

	public void setCfUtenteAuditFittizio(String cfUtenteAuditFittizio) {
		this.cfUtenteAuditFittizio = cfUtenteAuditFittizio;
	}

	public Boolean getUtenteFittizio() {
		return utenteFittizio;
	}

	public void setUtenteFittizio(Boolean utenteFittizio) {
		this.utenteFittizio = utenteFittizio;
	}

	public String getCfUtenteFittizio() {
		return cfUtenteFittizio;
	}

	public void setCfUtenteFittizio(String cfUtenteFittizio) {
		this.cfUtenteFittizio = cfUtenteFittizio;
	}

	public Boolean getVerificaToken() {
		return verificaToken;
	}

	public void setVerificaToken(Boolean verificaToken) {
		this.verificaToken = verificaToken;
	}

	public String getTokenFilterExcludeUrls() {
		return tokenFilterExcludeUrls;
	}

	public void setTokenFilterExcludeUrls(String tokenFilterExcludeUrls) {
		this.tokenFilterExcludeUrls = tokenFilterExcludeUrls;
	}

}