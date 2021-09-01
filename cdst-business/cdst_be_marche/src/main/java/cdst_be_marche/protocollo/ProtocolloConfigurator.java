package cdst_be_marche.protocollo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProtocolloConfigurator {

    @Value("${protocollo.utenteprotocollo}")
    private String utenteprotocollo;

	public String getUtenteprotocollo() {
		return utenteprotocollo;
	}

	public void setUtenteprotocollo(String utenteprotocollo) {
		this.utenteprotocollo = utenteprotocollo;
	}
    
    
    
}
