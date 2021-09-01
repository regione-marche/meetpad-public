package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="ConferencePreview")
public class ConferenzaAnteprimaDTO {

	@JsonProperty("id")
	private Integer idConferenza;

	@JsonProperty("requestReference")
	private String riferimentoIstanza;

	@JsonProperty("conferenceType")
	private LabelValue tipologiaConferenza;

	@JsonProperty("endProcedureDate")
	private String termineProcedimento;

	@JsonProperty("status")
	private LabelValue stato;

	@JsonProperty("proceedingCompany")
	private EnteDTO amministrazioneProcedente;

	@JsonProperty("applicant")
	private PersonaDTO richiedente;
	
	@JsonProperty("denomination")
	private String denominazione;

	@JsonProperty("endIntegrationDate")
	private String terminePerIntegrazione;

	@JsonProperty("endDeterminesDate")
	private String terminePerLaDetermina;

	@JsonProperty("nextSessionDate")
	private String termineProssimaSessione;
	
	@JsonProperty("address")
	private String indirizzoSessioneSimultanea;
	
	@JsonProperty("profile")
	private LabelValue profilo;
	
	@JsonProperty("canEdit")
	private Boolean canEdit;
	
	@JsonProperty("canClone")
	private Boolean canClone;
	
	@JsonProperty("canDelete")
	private Boolean canDelete;
	
	@JsonProperty("canSkype")
	private Boolean canSkype;

	@JsonProperty("idParent")
	private String idParent;

	public String getIdParent() {
		return idParent;
	}

	public void setIdParent(String idParent) {
		this.idParent = idParent;
	}
	
	public LabelValue getProfilo() {
		return profilo;
	}

	public void setProfilo(LabelValue profilo) {
		this.profilo = profilo;
	}

	public String getIndirizzoSessioneSimultanea() {
		return indirizzoSessioneSimultanea;
	}

	public void setIndirizzoSessioneSimultanea(String indirizzoSessioneSimultanea) {
		this.indirizzoSessioneSimultanea = indirizzoSessioneSimultanea;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getRiferimentoIstanza() {
		return riferimentoIstanza;
	}

	public void setRiferimentoIstanza(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}

	public LabelValue getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(LabelValue tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public String getTermineProcedimento() {
		return termineProcedimento;
	}

	public void setTermineProcedimento(String termineProcedimento) {
		this.termineProcedimento = termineProcedimento;
	}

	public LabelValue getStato() {
		return stato;
	}

	public void setStato(LabelValue stato) {
		this.stato = stato;
	}

	public EnteDTO getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(EnteDTO amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

	public PersonaDTO getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(PersonaDTO richiedente) {
		this.richiedente = richiedente;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public String getTerminePerIntegrazione() {
		return terminePerIntegrazione;
	}

	public void setTerminePerIntegrazione(String terminePerIntegrazione) {
		this.terminePerIntegrazione = terminePerIntegrazione;
	}

	public String getTerminePerLaDetermina() {
		return terminePerLaDetermina;
	}

	public void setTerminePerLaDetermina(String terminePerLaDetermina) {
		this.terminePerLaDetermina = terminePerLaDetermina;
	}

	public String getTermineProssimaSessione() {
		return termineProssimaSessione;
	}

	public void setTermineProssimaSessione(String termineProssimaSessione) {
		this.termineProssimaSessione = termineProssimaSessione;
	}
	
	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Boolean getCanClone() {
		return canClone;
	}

	public void setCanClone(Boolean canClone) {
		this.canClone = canClone;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}

	public Boolean getCanSkype() {
		return canSkype;
	}

	public void setCanSkype(Boolean canSkype) {
		this.canSkype = canSkype;
	}

	public static String getColonnaOrdinamento(String colonnaOrdinamento) {
		if (colonnaOrdinamento.equals("requestReference")) {
			colonnaOrdinamento = "riferimentoIstanza";
		}
		if (colonnaOrdinamento.equals("conferenceType")) {
			colonnaOrdinamento = "tipologiaConferenza";
		}
		if (colonnaOrdinamento.equals("endProcedureDate")) {
			colonnaOrdinamento = "termineProcedimento";
		}
		if (colonnaOrdinamento.equals("status")) {
			colonnaOrdinamento = "stato";
		}
		if (colonnaOrdinamento.equals("proceedingCompany")) {
			colonnaOrdinamento = "amministrazioneProcedente";
		}
		if (colonnaOrdinamento.equals("applicant")) {
			colonnaOrdinamento = "richiedente";
		}
		if (colonnaOrdinamento.equals("endIntegrationDate")) {
			colonnaOrdinamento = "terminePerIntegrazione";
		}
		if (colonnaOrdinamento.equals("endDeterminesDate")) {
			colonnaOrdinamento = "terminePerLaDetermina";
		}
		if (colonnaOrdinamento.equals("nextSessionDate")) {
			colonnaOrdinamento = "termineProssimaSessione";
		}
		if (colonnaOrdinamento.equals("address")) {
			colonnaOrdinamento = "indirizzoSessioneSimultanea";
		}
		return colonnaOrdinamento;
	}

}
