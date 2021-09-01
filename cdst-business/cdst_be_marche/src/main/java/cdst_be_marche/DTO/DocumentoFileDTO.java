package cdst_be_marche.DTO;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "represent multipart/form-data for upload document")
public class DocumentoFileDTO {

	@ApiModelProperty(name = "file")
	private MultipartFile file;

	@ApiModelProperty(name = "name")
	private String nomeFile;

	@ApiModelProperty(name = "type")
	private String tipoDocumento;

	@ApiModelProperty(name = "category")
	private String categoria;

	@ApiModelProperty(name = "visibility", example = "[{\"key\":\"1\"},{\"key\":\"2\"}]")
	private String visibilitaPartecipanti;
	
	@ApiModelProperty(name = "protocolNumber")
	private String numeroProtocollo;
	
	@ApiModelProperty(name = "protocolDate")
	private String dataProtocollo;
	
	@ApiModelProperty(name = "inventoryNumber")
	private String numeroInventario;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getVisibilitaPartecipanti() {
		return visibilitaPartecipanti;
	}

	public void setVisibilitaPartecipanti(String visibilitaPartecipanti) {
		this.visibilitaPartecipanti = visibilitaPartecipanti;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getNumeroInventario() {
		return numeroInventario;
	}

	public void setNumeroInventario(String numeroInventario) {
		this.numeroInventario = numeroInventario;
	}

}
