package conferenza.DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value="Voting")
public class VotazioneDTO extends IdentifiableDTO {

	@JsonProperty("subject")
	private String oggetto;

	@JsonProperty("votingType")
	private LabelValue tipoVoto;
	
	@JsonProperty("endVotingDate")
	private Date dataScadenzaVotazione;

	@JsonProperty("votingDate")
	private Date dataVotazione;
	
	@JsonProperty("votingRule")
	private LabelValue criterioVotazione;
	
	@JsonProperty("votingState")
	private LabelValue statoVotazione;
	
	@JsonProperty("votingResult")
	private LabelValue esitoVotazione;
	
	@JsonProperty("visibility")
	private LabelValue elencoPossibiliVotanti;
	
	@JsonProperty("vote")
	private List<VotoDTO> voto;
	
	@JsonProperty("canVote")
	private Boolean canVote;
	
	@JsonProperty("canEdit")
	private Boolean canEdit;
	
	@JsonProperty("canClose")
	private Boolean canClose;
	
	@JsonProperty("canStart")
	private Boolean canStart;
	
	@JsonProperty("canDelete")
	private Boolean canDelete;

	@JsonProperty("canEvaluate")
	private Boolean canEvaluate;
		
	public Date getDataVotazione() {
		return dataVotazione;
	}

	public void setDataVotazione(Date dataVotazione) {
		this.dataVotazione = dataVotazione;
	}

	public Boolean getCanEvaluate() {
		return canEvaluate;
	}

	public void setCanEvaluate(Boolean canEvaluate) {
		this.canEvaluate = canEvaluate;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public LabelValue getTipoVoto() {
		return tipoVoto;
	}

	public void setTipoVoto(LabelValue tipoVoto) {
		this.tipoVoto = tipoVoto;
	}

	public Date getDataScadenzaVotazione() {
		return dataScadenzaVotazione;
	}

	public void setDataScadenzaVotazione(Date dataScadenzaVotazione) {
		this.dataScadenzaVotazione = dataScadenzaVotazione;
	}

	public LabelValue getCriterioVotazione() {
		return criterioVotazione;
	}

	public void setCriterioVotazione(LabelValue criterioVotazione) {
		this.criterioVotazione = criterioVotazione;
	}

	public LabelValue getStatoVotazione() {
		return statoVotazione;
	}

	public void setStatoVotazione(LabelValue statoVotazione) {
		this.statoVotazione = statoVotazione;
	}

	public LabelValue getEsitoVotazione() {
		return esitoVotazione;
	}

	public void setEsitoVotazione(LabelValue esitoVotazione) {
		this.esitoVotazione = esitoVotazione;
	}

	public LabelValue getElencoPossibiliVotanti() {
		return elencoPossibiliVotanti;
	}

	public void setElencoPossibiliVotanti(LabelValue elencoPossibiliVotanti) {
		this.elencoPossibiliVotanti = elencoPossibiliVotanti;
	}

	public List<VotoDTO> getVoto() {
		return voto;
	}

	public void setVoto(List<VotoDTO> voto) {
		this.voto = voto;
	}
	
	public void addVoto(VotoDTO voto) {
		if(this.voto == null) {
			this.voto = new ArrayList<>();
		}
		this.voto.add(voto);
	}
	
	public Boolean getCanVote() {
		return canVote;
	}

	public void setCanVote(Boolean canVote) {
		this.canVote = canVote;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Boolean getCanClose() {
		return canClose;
	}

	public void setCanClose(Boolean canClose) {
		this.canClose = canClose;
	}

	public Boolean getCanStart() {
		return canStart;
	}

	public void setCanStart(Boolean canStart) {
		this.canStart = canStart;
	}

	public Boolean getCanDelete() {
		return canDelete;
	}

	public void setCanDelete(Boolean canDelete) {
		this.canDelete = canDelete;
	}
} 
