package conferenza.DTO;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "represent multipart/form-data for upload document")
public class DocumentoFileDTO {

	@ApiModelProperty(name = "file")
	private MultipartFile file;
	
	@ApiModelProperty(name = "attachments")
	private MultipartFile[] attachments;
	
	@ApiModelProperty(name = "url")
	private String url;
	
	@ApiModelProperty(name = "name")
	private String nomeFile;

	@ApiModelProperty(name = "type")
	private String tipoDocumento;

	@ApiModelProperty(name = "category")
	private String categoria;

	@ApiModelProperty(name = "visibility", example = "[{\"key\":\"1\"},{\"key\":\"2\"}]")
	private String visibilitaPartecipanti;
	
	@ApiModelProperty(name = "signatories", example = "[{\"key\":\"1\"},{\"key\":\"2\"}]")
	private String firmatari;
	
	@ApiModelProperty(name = "protocolNumber")
	private String numeroProtocollo;
	
	@ApiModelProperty(name = "protocolDate")
	private String dataProtocollo;
	
	@ApiModelProperty(name = "inventoryNumber")
	private String numeroInventario;
	
	@ApiModelProperty(name = "cityReference")
	private String competenzaTerritoriale;
	
	@ApiModelProperty(name = "model")
	private String modello;
	
	@ApiModelProperty(name = "meetingDate")
	private String dataRiunione;

	@ApiModelProperty(name = "signed")
	private Boolean firmato;
			
	@ApiModelProperty(name = "fileComplient")
	private Boolean fileConforme;
	
	@ApiModelProperty(name = "fileNameCalamaio")
	private String nomeFilePostCalamaio;

	public String getNomeFilePostCalamaio() {
		return nomeFilePostCalamaio;
	}

	public void setNomeFilePostCalamaio(String nomeFilePostCalamaio) {
		this.nomeFilePostCalamaio = nomeFilePostCalamaio;
	}

	public Boolean getFirmato() {
		return firmato;
	}

	public void setFirmato(Boolean firmato) {
		this.firmato = firmato;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
	
	public String getCompetenzaTerritoriale() {
		return competenzaTerritoriale;
	}

	public void setCompetenzaTerritoriale(String competenzaTerritoriale) {
		this.competenzaTerritoriale = competenzaTerritoriale;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getDataRiunione() {
		return dataRiunione;
	}

	public void setDataRiunione(String dataRiunione) {
		this.dataRiunione = dataRiunione;
	}

	public String getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(String firmatari) {
		this.firmatari = firmatari;
	}
	
	public Boolean getFileConforme() {
		return fileConforme;
	}

	public void setFileConforme(Boolean fileConforme) {
		this.fileConforme = fileConforme;
	}

	public MultipartFile[] getAttachments() {
		return attachments;
	}

	public void setAttachments(MultipartFile[] attachments) {
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "DocumentoFileDTO [file=" + file + ", url=" + url + ", nomeFile=" + nomeFile + ", tipoDocumento="
				+ tipoDocumento + ", categoria=" + categoria + ", visibilitaPartecipanti=" + visibilitaPartecipanti
				+ ", firmatari=" + firmatari + ", numeroProtocollo=" + numeroProtocollo + ", dataProtocollo="
				+ dataProtocollo + ", numeroInventario=" + numeroInventario + ", competenzaTerritoriale="
				+ competenzaTerritoriale + ", modello=" + modello + ", dataRiunione=" + dataRiunione + ", firmato="
				+ firmato + ", fileConforme=" + fileConforme + "]";
	}

	

}
