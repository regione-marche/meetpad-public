package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveApplicantPreview")
public class PrecaricamentoRichiedentePreviewDTO {
	
	@JsonProperty("idApplicant")
	private Integer idRichiedente;
	
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

	public Integer getIdRichiedente() {
		return idRichiedente;
	}

	public void setIdRichiedente(Integer idRichiedente) {
		this.idRichiedente = idRichiedente;
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
	
	
}
