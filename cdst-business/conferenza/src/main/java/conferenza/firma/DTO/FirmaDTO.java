package conferenza.firma.DTO;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;


@JsonInclude(Include.NON_EMPTY)
public class FirmaDTO  extends IdentifiableDTO {
	
	@JsonProperty("idDocumento")	
	Integer idDocumento;
	@JsonProperty("fk_tipo_firma")
	Integer fk_tipo_firma;
	@JsonProperty("padesCades")
	Boolean padesCades;
	@JsonProperty("calamaioRemota")
	String calamaioRemota;
	@JsonProperty("idUser")
	Integer idUser;
	@JsonProperty("fileName")
	String fileName;
	@JsonProperty("sessioneFirma")
	String sessioneFirma;
	@JsonProperty("shot")
	String shot;
	
	@JsonInclude(Include.NON_EMPTY)
	@JsonProperty("resource")
	Resource resource;
	@JsonProperty("crc")
	String crc;
	@JsonProperty("stato")
	String stato;
	@JsonProperty("downloadToken")
	String downloadToken;
	@JsonProperty("idConferenza")
	Integer idConferenza;
	@JsonProperty("callbackbody")
	String callbackbody;
	
	//aggiunto per poter creare un NUOVO documento da parte del responsabile..
	MultipartFile file;
	
	public Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Integer getFk_tipo_firma() {
		return fk_tipo_firma;
	}
	public void setFk_tipo_firma(Integer fk_tipo_firma) {
		this.fk_tipo_firma = fk_tipo_firma;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSessioneFirma() {
		return sessioneFirma;
	}
	public void setSessioneFirma(String sessioneFirma) {
		this.sessioneFirma = sessioneFirma;
	}
	public String getShot() {
		return shot;
	}
	public void setShot(String shot) {
		this.shot = shot;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDownloadToken() {
		return downloadToken;
	}
	public void setDownloadToken(String downloadToken) {
		this.downloadToken = downloadToken;
	}
	public Integer getIdConferenza() {
		return idConferenza;
	}
	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getCallbackbody() {
		return callbackbody;
	}
	public void setCallbackbody(String callbackbody) {
		this.callbackbody = callbackbody;
	}
	public Boolean getPadesCades() {
		return padesCades;
	}
	public void setPadesCades(Boolean padesCades) {
		this.padesCades = padesCades;
	}
	public String getCalamaioRemota() {
		return calamaioRemota;
	}
	public void setCalamaioRemota(String calamaioRemota) {
		this.calamaioRemota = calamaioRemota;
	}
	
	
	
}
