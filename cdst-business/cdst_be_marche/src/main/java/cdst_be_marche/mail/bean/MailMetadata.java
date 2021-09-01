package cdst_be_marche.mail.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cdst_be_marche.model.Documento;
import cdst_be_marche.model.Mailer;

public class MailMetadata<K> {

	String messageId;
	String token_accreditamento;
	String url_videoconferenza;
	String message;
	String url_accreditamento;// url di accreditamento

	String subject;
	String from;
	String to;
	String cc;
	String codice_fiscale;
	Integer idConferenza;
	
	String fakeRecipientAddress;
	String faseConcerenza;
	String codiceTipoEvento;
	String corpoEvento;
	
	/*
	 * campi mittente e destinatario sono descrittivi
	 */
	String mittente;
	String destinatario;
	
	String tipologiaConferenza;
	
	List<Documento> attachments = new ArrayList<Documento>();

	public MailMetadata(String fakeRecipientAddress) {
		this.fakeRecipientAddress = fakeRecipientAddress;
	}

	public MailMetadata(Mailer mailer, String fakeRecipientAddress, String pFrom, String pCC, String pSubject, String pMessage,
			String purl_accreditamento, String ptoken_accreditamento, String purl_videoconferenza) {
		this.from = pFrom;
		this.idConferenza = mailer.getId_conferenza();
		this.codice_fiscale = mailer.getCodice_fiscale();
		this.to = mailer.getEmail();
		this.fakeRecipientAddress = fakeRecipientAddress;
		this.subject = pSubject;
		this.message = pMessage;
		this.url_accreditamento = purl_accreditamento;
		this.token_accreditamento = ptoken_accreditamento;
		this.url_videoconferenza = purl_videoconferenza;
	}

	Map<String, String> parametri = new HashMap<>();

	public String getToken_accreditamento() {
		return token_accreditamento;
	}

	public void setToken_accreditamento(String token_accreditamento) {
		this.token_accreditamento = token_accreditamento;
	}

	public String getUrl_videoconferenza() {
		return url_videoconferenza;
	}

	public void setUrl_videoconferenza(String url_videoconferenza) {
		this.url_videoconferenza = url_videoconferenza;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl_accreditamento() {
		return url_accreditamento;
	}

	public void setUrl_accreditamento(String url_accreditamento) {
		this.url_accreditamento = url_accreditamento;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return fakeRecipientAddress != null ? fakeRecipientAddress : to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public Map<String, String> getParametri() {
		return parametri;
	}

	public void setParametri(Map<String, String> parametri) {
		this.parametri = parametri;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getFaseConcerenza() {
		return faseConcerenza;
	}

	public void setFaseConcerenza(String faseConcerenza) {
		this.faseConcerenza = faseConcerenza;
	}

	public String getCodiceTipoEvento() {
		return codiceTipoEvento;
	}

	public void setCodiceTipoEvento(String codiceTipoEvento) {
		this.codiceTipoEvento = codiceTipoEvento;
	}

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

	public List<Documento> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Documento> attachments) {
		this.attachments = attachments;
	}

	public String getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(String tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public String getCorpoEvento() {
		return corpoEvento;
	}

	public void setCorpoEvento(String corpoEvento) {
		this.corpoEvento = corpoEvento;
	}
	
	
	
}
