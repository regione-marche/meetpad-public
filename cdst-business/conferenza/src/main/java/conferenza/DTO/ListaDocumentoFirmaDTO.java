package conferenza.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.bean.Lista;
import io.swagger.annotations.ApiModel;

@ApiModel(value="SignDocumentList")
public class ListaDocumentoFirmaDTO extends Lista<DocumentoFirmaDTO>{
	
	@JsonProperty("idDocumentList")
	List<Integer> listaIdDocumenti;

	@JsonProperty("idManagerCST")
	private String idResponsabileCST;
	
	@JsonProperty("calamaioRemoteUsername")
	private String calamaioRemoteUsername;
	
	@JsonProperty("calamaioRemotePassword")
	private String calamaioRemotePassword;
	
	@JsonProperty("calamaioRemoteOTP")
	private String calamaioRemoteOTP;
	
	@JsonProperty("calamaioRemoteDomain")
	private String calamaioRemoteDomain;
	
	@JsonProperty("padesCades")
	private Boolean padesCades;
	
	public List<Integer> getListaIdDocumenti() {
		return listaIdDocumenti;
	}

	public void setListaIdDocumenti(List<Integer> listaIdDocumenti) {
		this.listaIdDocumenti = listaIdDocumenti;
	}
	
	public String getIdResponsabileCST() {
		return idResponsabileCST;
	}

	public void setIdResponsabileCST(String idResponsabileCST) {
		this.idResponsabileCST = idResponsabileCST;
	}
	
	public String getCalamaioRemoteUsername() {
		return calamaioRemoteUsername;
	}

	public void setCalamaioRemoteUsername(String calamaioRemoteUsername) {
		this.calamaioRemoteUsername = calamaioRemoteUsername;
	}

	public String getCalamaioRemotePassword() {
		return calamaioRemotePassword;
	}

	public void setCalamaioRemotePassword(String calamaioRemotePassword) {
		this.calamaioRemotePassword = calamaioRemotePassword;
	}

	public String getCalamaioRemoteOTP() {
		return calamaioRemoteOTP;
	}

	public void setCalamaioRemoteOTP(String calamaioRemoteOTP) {
		this.calamaioRemoteOTP = calamaioRemoteOTP;
	}

	public String getCalamaioRemoteDomain() {
		return calamaioRemoteDomain;
	}

	public void setCalamaioRemoteDomain(String calamaioRemoteDomain) {
		this.calamaioRemoteDomain = calamaioRemoteDomain;
	}

	public Boolean getPadesCades() {
		return padesCades;
	}

	public void setPadesCades(Boolean padesCades) {
		this.padesCades = padesCades;
	}
	
	
}
