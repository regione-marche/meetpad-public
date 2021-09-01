package cdst_be_marche.adapder.integrazioni.suap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
public class IntegSuapFrontieraConfigurator {


    @Value("${integrazioni.suap.baseUrlDettaglioConferenza}")
    private String baseUrlDettaglioConferenza;
    
	@Value("${integrazioni.suap.dblink}")
	private String dblink;
	
	@Value("${integrazioni.suap.protocollo.url}")
	private String urlProtocollo;
	
	public String getBaseUrlDettaglioConferenza() {
		return baseUrlDettaglioConferenza;
	}

	public void setBaseUrlDettaglioConferenza(String baseUrlDettaglioConferenza) {
		this.baseUrlDettaglioConferenza = baseUrlDettaglioConferenza;
	}

	public String getDblink() {
		return dblink;
	}

	public void setDblink(String dblink) {
		this.dblink = dblink;
	}

	public String getUrlProtocollo() {
		return urlProtocollo;
	}

	public void setUrlProtocollo(String urlProtocollo) {
		this.urlProtocollo = urlProtocollo;
	}
	
	
	
	
	
}
