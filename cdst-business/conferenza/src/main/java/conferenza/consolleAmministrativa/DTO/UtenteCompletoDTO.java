package conferenza.consolleAmministrativa.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import conferenza.model.Utente;
import io.swagger.annotations.ApiModel;

@ApiModel(value="UserComplete")
public class UtenteCompletoDTO {
	
	@JsonProperty(value = "idUser")
	private Integer idUtente;
	
	@JsonProperty(value = "name")
	private String nome;
	
	@JsonProperty(value = "surname")
	private String cognome;
	
	@JsonProperty(value = "fiscalCode")
	private String codiceFiscale;
	
	@JsonProperty(value = "prosecutingAdministration")
	private LabelValue amministrazioneProcedente;
	
	@JsonProperty("profile")
	private LabelValue profilo;
	
	@JsonProperty("email")
	private String email;

	@JsonProperty("creationOtherAuthorities")
	private Boolean delegaCreazioneAltreAmministrazioni;

	@JsonProperty("flagSignatory")
	private Boolean flagFirmatario;
	
	@JsonProperty("managersToImpersonate")
	private List<LabelValue> managersToImpersonate = new ArrayList<>();
	

	public List<LabelValue> getManagersToImpersonate() {
		return managersToImpersonate;
	}

	public void setManagersToImpersonate(List<LabelValue> managersToImpersonate) {
		this.managersToImpersonate = managersToImpersonate;
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

	public Integer getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}

	public LabelValue getProfilo() {
		return profilo;
	}

	public void setProfilo(LabelValue profilo) {
		this.profilo = profilo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getDelegaCreazioneAltreAmministrazioni() {
		return delegaCreazioneAltreAmministrazioni;
	}

	public void setDelegaCreazioneAltreAmministrazioni(Boolean delegaCreazioneAltreAmministrazioni) {
		this.delegaCreazioneAltreAmministrazioni = delegaCreazioneAltreAmministrazioni;
	}

	public Boolean getFlagFirmatario() {
		return flagFirmatario;
	}

	public void setFlagFirmatario(Boolean flagFirmatario) {
		this.flagFirmatario = flagFirmatario;
	}
	
	public LabelValue getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(LabelValue amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

}
