package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Authority")
public class EnteDTO extends IdentifiableDTO {

	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "description")
	private String descrizione;
	
	@JsonProperty(value = "pec")
	private String pec;
	
	@JsonProperty(value = "fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty(value = "competence")
	private String competenza;
	
	@JsonProperty(value = "role")
	private LabelValue ruolo;
	
	@JsonProperty(value = "flagProsecutingAdministration")
	private Boolean flagAmmProc;
	
	@JsonProperty(value = "flagPrincipalAdministration")
	private Boolean flagAmmPrincipale;
	
	@JsonProperty(value = "ipaCode")
	private String codiceIpa;
	
	@JsonProperty(value = "officeCode")
	private String codiceUfficio;
	
	@JsonProperty(value = "area")
	private LabelValue regione;
	
	@JsonProperty(value = "province")
	private LabelValue provincia;
	
	@JsonProperty(value = "city")
	private LabelValue comune;
	
	@JsonProperty(value = "cityDescription")
	private String descrizioneComune;
	
	@JsonProperty(value = "istatType")
	private LabelValue tipologiaIstat;
	
	@JsonProperty(value = "administrativeType")
	private LabelValue tipologiaAmm;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}

	public LabelValue getRuolo() {
		return ruolo;
	}

	public void setRuolo(LabelValue ruolo) {
		this.ruolo = ruolo;
	}

	public Boolean getFlagAmmProc() {
		return flagAmmProc;
	}

	public void setFlagAmmProc(Boolean flagAmmProc) {
		this.flagAmmProc = flagAmmProc;
	}

	public Boolean getFlagAmmPrincipale() {
		return flagAmmPrincipale;
	}

	public void setFlagAmmPrincipale(Boolean flagAmmPrincipale) {
		this.flagAmmPrincipale = flagAmmPrincipale;
	}

	public String getCodiceIpa() {
		return codiceIpa;
	}

	public void setCodiceIpa(String codiceIpa) {
		this.codiceIpa = codiceIpa;
	}

	public String getCodiceUfficio() {
		return codiceUfficio;
	}

	public void setCodiceUfficio(String codiceUfficio) {
		this.codiceUfficio = codiceUfficio;
	}

	public LabelValue getRegione() {
		return regione;
	}

	public void setRegione(LabelValue regione) {
		this.regione = regione;
	}

	public LabelValue getProvincia() {
		return provincia;
	}

	public void setProvincia(LabelValue provincia) {
		this.provincia = provincia;
	}

	public LabelValue getComune() {
		return comune;
	}

	public void setComune(LabelValue comune) {
		this.comune = comune;
	}

	public LabelValue getTipologiaIstat() {
		return tipologiaIstat;
	}

	public void setTipologiaIstat(LabelValue tipologiaIstat) {
		this.tipologiaIstat = tipologiaIstat;
	}

	public LabelValue getTipologiaAmm() {
		return tipologiaAmm;
	}

	public void setTipologiaAmm(LabelValue tipologiaAmm) {
		this.tipologiaAmm = tipologiaAmm;
	}

	public String getDescrizioneComune() {
		return descrizioneComune;
	}

	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}
	
}
