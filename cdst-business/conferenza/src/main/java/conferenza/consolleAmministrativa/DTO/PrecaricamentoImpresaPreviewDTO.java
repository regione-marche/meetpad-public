package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="PreemptiveCompanyPreview")
public class PrecaricamentoImpresaPreviewDTO {
	
	@JsonProperty("idPreemptiveCompany")
	private Integer idRubricaImprese;
	
	@JsonProperty("idCompany")
	private Integer idImpresa;
	
	@JsonProperty("name")
	private String nome;
	
	@JsonProperty("fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty("vatNumber")
	private String partitaIva;
	
	@JsonProperty("conferenceType")
	private LabelValue tipologiaConferenza;

	public Integer getIdRubricaImprese() {
		return idRubricaImprese;
	}

	public void setIdRubricaImprese(Integer idRubricaImprese) {
		this.idRubricaImprese = idRubricaImprese;
	}

	public Integer getIdImpresa() {
		return idImpresa;
	}

	public void setIdImpresa(Integer idImpresa) {
		this.idImpresa = idImpresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public LabelValue getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(LabelValue tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

}
