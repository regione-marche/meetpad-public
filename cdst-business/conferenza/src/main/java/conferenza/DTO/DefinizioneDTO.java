package conferenza.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Definition")
public class DefinizioneDTO {
	
	@JsonProperty("determination")
	private DeterminazioneDTO determinazione;
	
	@Valid
	@JsonProperty("instance")
	private IstanzaDTO istanza;
	
	@JsonProperty("supportContacts")
	private List<ContattoSupportoDTO> listaContattoSupporto = new ArrayList<>();
	
	public DeterminazioneDTO getDeterminazione() {
		return determinazione;
	}
	public void setDeterminazione(DeterminazioneDTO determinazione) {
		this.determinazione = determinazione;
	}
	public IstanzaDTO getIstanza() {
		return istanza;
	}
	public void setIstanza(IstanzaDTO istanza) {
		this.istanza = istanza;
	}
	public List<ContattoSupportoDTO> getListaContattoSupporto() {
		return listaContattoSupporto;
	}
	public void setListaContattoSupporto(List<ContattoSupportoDTO> listaContattoSupporto) {
		this.listaContattoSupporto = listaContattoSupporto;
	}
	
	

}
