package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Preaccreditation")
public class PreaccreditatoDTO {
	
	@JsonProperty("idPreaccreditation")
	private Integer idPreaccreditato;
	
	@JsonProperty("name")
	private String nome;

	@JsonProperty("surname")
	private String cognome;

	@JsonProperty("fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("conferenceType")
	private LabelValue tipoConf;

	@JsonProperty("profileType")
	private LabelValue tipoProfilo;

	@JsonProperty("authority")
	private LabelValue ente;
	
	public Integer getIdPreaccreditato() {
		return idPreaccreditato;
	}

	public void setIdPreaccreditato(Integer idPreaccreditato) {
		this.idPreaccreditato = idPreaccreditato;
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

	public LabelValue getTipoConf() {
		return tipoConf;
	}

	public void setTipoConf(LabelValue tipoConf) {
		this.tipoConf = tipoConf;
	}

	public LabelValue getTipoProfilo() {
		return tipoProfilo;
	}

	public void setTipoProfilo(LabelValue tipoProfilo) {
		this.tipoProfilo = tipoProfilo;
	}
	
	public LabelValue getEnte() {
		return ente;
	}

	public void setEnte(LabelValue ente) {
		this.ente = ente;
	}
}
