package conferenza.consolleAmministrativa.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.DefinizioneDTO;
import conferenza.DTO.PraticaDTO;
import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="ConsolleConference")
public class ConferenzaConsolleDTO {
	
	@JsonProperty("proceedingCompany")
	private LabelValue amministrazioneProcedente;
	
	@JsonProperty("conferenceManager")
	private LabelValue responsabileConferenza;
	
	@JsonProperty("procedure")
	private PraticaDTO pratica;

	@JsonProperty("definition")
	private DefinizioneDTO definizione;
	
	@JsonProperty("step")
	private Integer step;

	@JsonProperty("state")
	private LabelValue stato;

	public LabelValue getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(LabelValue amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

	public LabelValue getResponsabileConferenza() {
		return responsabileConferenza;
	}

	public void setResponsabileConferenza(LabelValue responsabileConferenza) {
		this.responsabileConferenza = responsabileConferenza;
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

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public LabelValue getStato() {
		return stato;
	}

	public void setStato(LabelValue stato) {
		this.stato = stato;
	}

}
