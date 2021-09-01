package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;
import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreaccreditatiPreview")
public class PreaccreditatiPreviewDTO extends IdentifiableDTO {
	
	@JsonProperty("idAccreditation")
	private Integer idAccreditato;
	
	@JsonProperty("name")
	private String nome;

	@JsonProperty("surname")
	private String cognome;

	@JsonProperty("fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty("idEnte")
	private Integer idEnte;
	
	@JsonProperty("profileType")
	private LabelValue tipoProfilo;
	
	@JsonProperty("conferenceType")
	private LabelValue tipoConf;
	
	@JsonProperty("authority")
	private LabelValue ente;

	public Integer getIdAccreditato() {
		return idAccreditato;
	}

	public void setIdAccreditato(Integer idAccreditato) {
		this.idAccreditato = idAccreditato;
	}

	public LabelValue getTipoProfilo() {
		return tipoProfilo;
	}

	public void setTipoProfilo(LabelValue tipoProfilo) {
		this.tipoProfilo = tipoProfilo;
	}

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
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

	public LabelValue getTipoConf() {
		return tipoConf;
	}

	public void setTipoConf(LabelValue tipoConf) {
		this.tipoConf = tipoConf;
	}

	public LabelValue getEnte() {
		return ente;
	}

	public void setEnte(LabelValue ente) {
		this.ente = ente;
	}

}
