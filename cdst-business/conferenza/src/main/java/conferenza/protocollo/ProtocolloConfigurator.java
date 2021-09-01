package conferenza.protocollo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProtocolloConfigurator {

    @Value("${protocollo.utenteprotocollo}")
    private String utenteprotocollo;
    
    @Value("${protocollo.enableservice}")
    private String enableservice;

	public String getUtenteprotocollo() {
		return utenteprotocollo;
	}

	public void setUtenteprotocollo(String utenteprotocollo) {
		this.utenteprotocollo = utenteprotocollo;
	}

	public String getEnableservice() {
		return enableservice;
	}

	public void setEnableservice(String enableservice) {
		this.enableservice = enableservice;
	}
    
    
    
}
