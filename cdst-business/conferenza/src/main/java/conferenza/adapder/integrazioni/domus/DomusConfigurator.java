package conferenza.adapder.integrazioni.domus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


/**
 * 
domus:
	connection:
		timeout: 1000
		url:	http://arturo3.regionemarche.intra/DomusSismaServices/DomusSismaMISService.svc
		user:	christian_de angelis
		password: DNGCRS85T31A462U
 * 
 * 
 * 
 * @author guideluc
 *
 */
@Lazy
@Configuration
public class DomusConfigurator {

	
    @Value("${domus.connection.url}")
    private String domusConnectionUrl;

    @Value("${domus.connection.timeout}")
    private String domusConnectionTimeout;
    
    
    @Value("${domus.connection.user}")
    private String domusConnectionUser;    

    @Value("${domus.connection.password}")
    private String domusConnectionPassword;
    
    @Value("${domus.connection.stato}")
    private String domusConnectionStatus;
    
    @Value("${domus.ambiente}")
    private String domusAmbiente;

    //TIPO_CONFERENZA_DOMUS_DEFUALT
    @Value("${domus.tipoconferenza}")
    private String domusTipoConferenzaDefualt;
    
	/**
     * enable asincronous service - {S,N}
     */
    @Value("${domus.asincronousservice}")
    private String domusEnableAsincronous;
    
    @Value("${domus.contattiSupporto}")
    private String contattiSupporto;
 

	public String getDomusTipoConferenzaDefualt() {
		return domusTipoConferenzaDefualt;
	}

	public void setDomusTipoConferenzaDefualt(String domusTipoConferenzaDefualt) {
		this.domusTipoConferenzaDefualt = domusTipoConferenzaDefualt;
	}

	public String getDomusAmbiente() {
		return domusAmbiente;
	}

	public void setDomusAmbiente(String domusAmbiente) {
		this.domusAmbiente = domusAmbiente;
	}

	public String getDomusConnectionStatus() {
		return domusConnectionStatus;
	}

	public void setDomusConnectionStatus(String domusConnectionStatus) {
		this.domusConnectionStatus = domusConnectionStatus;
	}

	public String getDomusConnectionUrl() {
		return domusConnectionUrl;
	}

	public void setDomusConnectionUrl(String domusConnectionUrl) {
		this.domusConnectionUrl = domusConnectionUrl;
	}

	public String getDomusConnectionTimeout() {
		return domusConnectionTimeout;
	}

	public void setDomusConnectionTimeout(String domusConnectionTimeout) {
		this.domusConnectionTimeout = domusConnectionTimeout;
	}

	public String getDomusConnectionUser() {
		return domusConnectionUser;
	}

	public void setDomusConnectionUser(String domusConnectionUser) {
		this.domusConnectionUser = domusConnectionUser;
	}

	public String getDomusConnectionPassword() {
		return domusConnectionPassword;
	}

	public void setDomusConnectionPassword(String domusConnectionPassword) {
		this.domusConnectionPassword = domusConnectionPassword;
	}

	public String getDomusEnableAsincronous() {
		return domusEnableAsincronous;
	}

	public void setDomusEnableAsincronous(String domusEnableAsincronous) {
		this.domusEnableAsincronous = domusEnableAsincronous;
	}
    
	public String getContattiSupporto() {
		return contattiSupporto;
	}

	public void setContattiSupporto(String contattiSupporto) {
		this.contattiSupporto = contattiSupporto;
	}
    
    
    
}
