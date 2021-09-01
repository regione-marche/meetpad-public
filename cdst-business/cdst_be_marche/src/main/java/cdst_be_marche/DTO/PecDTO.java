package cdst_be_marche.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.DTO.bean.LabelValue;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "PEC")
public class PecDTO {
	
	@JsonProperty("sender")
	private String mittente;
	
	@JsonProperty("recipient")
	private String destinatario;
	
	@JsonProperty("recipientPec")
	private String emailDestinatario;
	
	@JsonProperty("status")
	private LabelValue emailStatus;
	
	@JsonProperty("statusMessage")
	private String messaggioStato;
	
	@JsonProperty("event")
	private LabelValue idEvento;
	
	@JsonProperty("sentDate")
	private String dataInvio;

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public LabelValue getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(LabelValue emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getMessaggioStato() {
		return messaggioStato;
	}

	public void setMessaggioStato(String messaggioStato) {
		this.messaggioStato = messaggioStato;
	}

	public LabelValue getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(LabelValue idEvento) {
		this.idEvento = idEvento;
	}

	public String getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(String dataInvio) {
		this.dataInvio = dataInvio;
	}
	

	
	public static String getColonnaOrdinamento(String colonnaOrdinamento) {
		if (colonnaOrdinamento.equals("sender")) {
			colonnaOrdinamento = "mittente";
		}
		if (colonnaOrdinamento.equals("recipient")) {
			colonnaOrdinamento = "destinatario";
		}
		if (colonnaOrdinamento.equals("status")) {
			colonnaOrdinamento = "emailStatus";
		}
		if (colonnaOrdinamento.equals("event")) {
			colonnaOrdinamento = "idEvento";
		}
		if (colonnaOrdinamento.equals("recipientPec")) {
			colonnaOrdinamento = "emailDestinatario";
		}
		if (colonnaOrdinamento.equals("sentDate")) {
			colonnaOrdinamento = "dataInvio";
		}
		if (colonnaOrdinamento.equals("statusMessage")) {
			colonnaOrdinamento = "messaggioStato";
		}
		return colonnaOrdinamento;
	}

}
