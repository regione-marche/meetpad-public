package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "PECResearch")
public class RicercaPecDTO extends PageableDTO{
	
	@JsonProperty("sender")
	private String mittente;
	
	@JsonProperty("recipient ")
	private String destinatario;
	
	@JsonProperty("status")
	private String emailStatus;
	
	@JsonProperty("event")
	private String idEvento;

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}
	
	public void setSender(String mittente) {
		this.mittente = mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	public void setRecipient(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}
	
	public void setStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	
	public void setEvent(String idEvento) {
		this.idEvento = idEvento;
	}

}
