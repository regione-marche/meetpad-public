package conferenza.mail.bean;

import java.util.Date;
import java.util.Hashtable;

import javax.mail.Message;

public class MailStatus {
	
	String  messageId;
	String  messageIdServer;
	String  messageStatus;
	Date    messageSentDate;
	String messageNote;
	Message messageMail; 	
	

	public MailStatus() {	
	}

	public MailStatus(
			String  messageId,
			String  messageStatus,
			Date    messageSentDate,
			String pMessageNote,
			Message messageMail
	) {
		this.messageId=messageId;
		this.messageStatus=messageStatus;
		this.messageSentDate=messageSentDate;
		this.messageMail=messageMail; 	
		this.messageNote=pMessageNote;
	}

	
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public Date getMessageSentDate() {
		return messageSentDate;
	}

	public void setMessageSentDate(Date messageSentDate) {
		this.messageSentDate = messageSentDate;
	}

	public Message getMessage() {
		return messageMail;
	}

	public void setMessage(Message message) {
		this.messageMail = message;
	}

	public String getMessageIdServer() {
		return messageIdServer;
	}

	public void setMessageIdServer(String messageIdServer) {
		this.messageIdServer = messageIdServer;
	}

	public String getMessageNote() {
		return messageNote;
	}

	public void setMessageNote(String messageNote) {
		this.messageNote = messageNote;
	}


	
	
	
}
