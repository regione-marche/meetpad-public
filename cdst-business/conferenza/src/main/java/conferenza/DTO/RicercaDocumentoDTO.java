package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "DocumentResearch")
public class RicercaDocumentoDTO extends PageableDTO{
	
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
	
	@JsonProperty("signatories")
	private List<LabelValue> firmatari = new ArrayList<>();

	@JsonProperty("protocolNumber")
	private String numeroProtocollo;
	
	@JsonProperty("protocolDate")
	private String dataProtocollo;
	
	@JsonProperty("inventoryNumber")
	private String numeroInventario;
	
	@JsonProperty("cityReference")
	private String competenzaTerritoriale;
	
	@JsonProperty("folder")
	private String cartella;
	
	@JsonProperty("meetingDate")
	private String dataRiunione;
	
	@JsonProperty("status")
	private String stato;
	
	@JsonProperty("owner")
	private PersonaDTO proprietario;
	
	@JsonProperty("fileComplient")
	private Boolean fileConforme;
	
	@JsonProperty("idManagerCST")
	private String idResponsabileCST;
	
	private String md5;

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

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

	public String getCompetenzaTerritoriale() {
		return competenzaTerritoriale;
	}

	public void setCompetenzaTerritoriale(String competenzaTerritoriale) {
		this.competenzaTerritoriale = competenzaTerritoriale;
	}

	public String getCartella() {
		return cartella;
	}

	public void setCartella(String cartella) {
		this.cartella = cartella;
	}
	
	public String getDataRiunione() {
		return dataRiunione;
	}

	public void setDataRiunione(String dataRiunione) {
		this.dataRiunione = dataRiunione;
	}

	public List<LabelValue> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(List<LabelValue> firmatari) {
		this.firmatari = firmatari;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	

	public PersonaDTO getProprietario() {
		return proprietario;
	}

	public void setProprietario(PersonaDTO proprietario) {
		this.proprietario = proprietario;
	}

	public Boolean getFileConforme() {
		return fileConforme;
	}

	public void setFileConforme(Boolean fileConforme) {
		this.fileConforme = fileConforme;
	}
	
	public String getIdResponsabileCST() {
		return idResponsabileCST;
	}

	public void setIdResponsabileCST(String idResponsabileCST) {
		this.idResponsabileCST = idResponsabileCST;
	}
}
