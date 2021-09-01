package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PrecaricamentoDelegatoPreview")
public class PrecaricamentoDelegatoPreviewDTO extends IdentifiableDTO {
	
	@JsonProperty("idDelegate")
	private Integer idDelegato;
	
	@JsonProperty("name")
	private String nome;

	@JsonProperty("surname")
	private String cognome;

	@JsonProperty("fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty("company")
	private LabelValue impresa;
	
	@JsonProperty("conferenceType")
	private LabelValue tipoConf;
	
	@JsonProperty("documentName")
	private String nomeDocumento;
	
	@JsonProperty("url")
	private String url;

	public Integer getIdDelegato() {
		return idDelegato;
	}

	public void setIdDelegato(Integer idDelegato) {
		this.idDelegato = idDelegato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public LabelValue getImpresa() {
		return impresa;
	}

	public void setImpresa(LabelValue impresa) {
		this.impresa = impresa;
	}

	public LabelValue getTipoConf() {
		return tipoConf;
	}

	public void setTipoConf(LabelValue tipoConf) {
		this.tipoConf = tipoConf;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
