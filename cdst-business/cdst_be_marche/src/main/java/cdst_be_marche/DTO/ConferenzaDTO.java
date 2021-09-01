package cdst_be_marche.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "Conference")
public class ConferenzaDTO extends IdentifiableDTO {

	@JsonProperty("procedure")
	private PraticaDTO pratica;

	@JsonProperty("definition")
	private DefinizioneDTO definizione;

	@JsonProperty("documentation")
	private DocumentazioneDTO documentazione;

	@JsonProperty("participants")
	private List<PartecipanteDTO> partecipanti;

	private Integer step;

	@JsonProperty("state")
	private LabelValue stato;

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

}
