package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel("User")
public class UtenteDTO {

	@JsonProperty("name")
	private String nome;

	@JsonProperty("lastname")
	private String cognome;

	@JsonProperty("fiscalCode")
	private String codiceFiscale;

	@JsonProperty("profile")
	private LabelValue profilo;
	
	@JsonProperty("email")
	private String email;

	@JsonProperty("creationOtherAuthorities")
	private Boolean delegaCreazioneAltreAmministrazioni;
	
	@JsonProperty("flagSignatory")
	private Boolean flagFirmatario;
	
	@JsonProperty("idUser")
	private Integer idUtente;
	
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
	
	public Integer getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Integer idUtente) {
		this.idUtente = idUtente;
	}
}
