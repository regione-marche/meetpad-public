package conferenza.DTO;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="IntegrationsFile")
public class IntegrazioneFileDTO {
	
	@ApiModelProperty(value = "protocolNumber")
	private String numeroProtocollo;
	
	@ApiModelProperty(value = "authority")
	private String amministrazione;
	
	@ApiModelProperty(value = "objectRequest", example = "{\"key\":\"1\"},{\"key\":\"2\"}")
	private String oggettoRichiesta;
	
	@ApiModelProperty(value = "body")
	private String corpo;
	
	@ApiModelProperty(value = "file")
	private MultipartFile file;

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getAmministrazione() {
		return amministrazione;
	}

	public void setAmministrazione(String amministrazione) {
		this.amministrazione = amministrazione;
	}

	public String getOggettoRichiesta() {
		return oggettoRichiesta;
	}

	public void setOggettoRichiesta(String oggettoRichiesta) {
		this.oggettoRichiesta = oggettoRichiesta;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
