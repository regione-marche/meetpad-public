package conferenza.firma;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirmaConfigurator {

	
    @Value("${file.upload-dir}")
    private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
    //@Value("${protocollo.utenteprotocollo}")
	//private String utenteprotocollo;
	
}
