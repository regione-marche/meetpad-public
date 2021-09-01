package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "SupportContact")
public class ContattoSupportoDTO extends IdentifiableDTO{
	
	@JsonProperty("name")
	private String nome;
	
	@JsonProperty("surname")
	private String cognome;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("phone")
	private String telefono;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
