package conferenza.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@JsonInclude(Include.NON_NULL)
@ApiModel(value = "Conference")
public class ConferenzaDTO extends IdentifiableDTO {

	@JsonProperty("creatorTaxCode")
	private String codiceFiscaleCreatore;
	
	@JsonProperty("procedure")
	private PraticaDTO pratica;

	@JsonProperty("definition")
	private DefinizioneDTO definizione;

	@JsonProperty("documentation")
	private DocumentazioneDTO documentazione;

	@JsonProperty("participants")
	private List<PartecipanteDTO> partecipanti;
	
	@JsonProperty("enableApplicantEdit")
	private Boolean flagAbilitaModificaRichiedente;

	private Integer step;

	@JsonProperty("state")
	private LabelValue stato;

	@JsonProperty("geomapGuid")
	private String geomapGuid;
	
	@JsonProperty("foglioMappale")
	private String foglioMappale;
	
	@JsonProperty("clonedFromId")
	private String clonedFromId;
	
	@JsonProperty("isImpersonatedAdmin")
	private boolean isImpersonatedAdmin;
	
	public boolean isImpersonatedAdmin() {
		return isImpersonatedAdmin;
	}

	public void setImpersonatedAdmin(boolean isImpersonatedAdmin) {
		this.isImpersonatedAdmin = isImpersonatedAdmin;
	}

	public String getClonedFromId() {
		return clonedFromId == null?"":clonedFromId;
	}

	public void setClonedFromId(String clonedFromId) {
		this.clonedFromId = clonedFromId;
	}

	public String getFoglioMappale() {
		return foglioMappale;
	}

	public void setFoglioMappale(String foglioMappale) {
		this.foglioMappale = foglioMappale;
	}

	public String getGeomapGuid() {
		return geomapGuid;
	}

	public void setGeomapGuid(String geomapGuid) {
		this.geomapGuid = geomapGuid;
	}

	public LabelValue getStato() {
		return stato;
	}

	public void setStato(LabelValue stato) {
		this.stato = stato;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public PraticaDTO getPratica() {
		return pratica;
	}

	public void setPratica(PraticaDTO pratica) {
		this.pratica = pratica;
	}

	public DefinizioneDTO getDefinizione() {
		return definizione;
	}

	public void setDefinizione(DefinizioneDTO definizione) {
		this.definizione = definizione;
	}

	public DocumentazioneDTO getDocumentazione() {
		return documentazione;
	}

	public void setDocumentazione(DocumentazioneDTO documentazione) {
		this.documentazione = documentazione;
	}

	public List<PartecipanteDTO> getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(List<PartecipanteDTO> partecipanti) {
		this.partecipanti = partecipanti;
	}

	public Boolean getFlagAbilitaModificaRichiedente() {
		return flagAbilitaModificaRichiedente;
	}

	public void setFlagAbilitaModificaRichiedente(Boolean flagAbilitaModificaRichiedente) {
		this.flagAbilitaModificaRichiedente = flagAbilitaModificaRichiedente;
	}

	public String getCodiceFiscaleCreatore() {
		return codiceFiscaleCreatore;
	}

	public void setCodiceFiscaleCreatore(String codiceFiscaleCreatore) {
		this.codiceFiscaleCreatore = codiceFiscaleCreatore;
	}


	
	
	

}
