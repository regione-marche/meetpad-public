package conferenza.DTO;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "represent multipart/form-data for upload document for accreditament")
public class AccreditamentoFileDTO {

	@ApiModelProperty(name = "token1")
	private String token1;

	@ApiModelProperty(name = "token2")
	private String token2;

	@ApiModelProperty(name = "file")
	private MultipartFile file;

	@ApiModelProperty(name = "name")
	private String nome;

	@ApiModelProperty(name = "surname")
	private String cognome;

	@ApiModelProperty(name = "fiscalCode")
	private String codiceFiscale;

	@ApiModelProperty(name = "email")
	private String email;

	@ApiModelProperty(name = "role")
	private String ruoloPersona;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getRuoloPersona() {
		return ruoloPersona;
	}

	public void setRuoloPersona(String ruoloPersona) {
		this.ruoloPersona = ruoloPersona;
	}

	public String getToken1() {
		return token1;
	}

	public void setToken1(String token1) {
		this.token1 = token1;
	}

	public String getToken2() {
		return token2;
	}

	public void setToken2(String token2) {
		this.token2 = token2;
	}
	
	@Override
	public String toString() {
		return "token1: " + token1 + " - token2: " + token2 + " - nome: " + nome + " - cognome: " + cognome
				+ " - codice fiscale: " + codiceFiscale + " - email: " + email + " - ruolo persona: " + ruoloPersona;
	}

}
