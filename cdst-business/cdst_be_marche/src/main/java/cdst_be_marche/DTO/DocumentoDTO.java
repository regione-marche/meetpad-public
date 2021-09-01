package cdst_be_marche.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Document")
public class DocumentoDTO extends IdentifiableDTO {

	@JsonProperty("name")
	private String nomeFile;

	@JsonProperty("type")
	private LabelValue tipoDocumento;

	@JsonProperty("category")
	private LabelValue categoria;

	@JsonProperty("url")
	private String url;

	@JsonProperty("visibility")
	private List<LabelValue> visibilitaPartecipanti = new ArrayList<>();
	
	@JsonProperty("protocolNumber")
	private String numeroProtocollo;
	
	@JsonProperty("protocolDate")
	private String dataProtocollo;
	
	@JsonProperty("inventoryNumber")
	private String numeroInventario;

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public LabelValue getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(LabelValue tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public LabelValue getCategoria() {
		return categoria;
	}

	public void setCategoria(LabelValue categoria) {
		this.categoria = categoria;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<LabelValue> getVisibilitaPartecipanti() {
		return visibilitaPartecipanti;
	}

	public void setVisibilitaPartecipanti(List<LabelValue> visibilitaPartecipanti) {
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
