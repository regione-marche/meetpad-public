package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveApplicant")
public class PrecaricamentoRichiedenteDTO {
	
	@JsonProperty("idApplicant")
	private Integer idRichiedente;
	
	@JsonProperty("name")
	private String nome;

	@JsonProperty("surname")
	private String cognome;

	@JsonProperty("fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("principalApplicant")
	private Boolean flagRichPrincipale;
	
	@JsonProperty("removeApplicant")
	private Boolean flagDissociaRichiedente;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getFlagRichPrincipale() {
		return flagRichPrincipale;
	}

	public void setFlagRichPrincipale(Boolean flagRichPrincipale) {
		this.flagRichPrincipale = flagRichPrincipale;
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

	public Boolean getFlagDissociaRichiedente() {
		return flagDissociaRichiedente;
	}

	public void setFlagDissociaRichiedente(Boolean flagDissociaRichiedente) {
		this.flagDissociaRichiedente = flagDissociaRichiedente;
	}

}
