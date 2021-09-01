package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import conferenza.mail.bean.MailMetadata;

/**
 * REGISTRO_EMAIL_TESTATA ID_EMAIL_TESTATA FK_CONFERENZA EMAIL SUBJECT
 * FASE_EMAIL --Indizione! Integrazioni | Votazione ID_MESSAGGIO FK_TIPO_EVENTO
 * 
 * @author guideluc
 *
 */

@Entity
@Table(name = "REGISTRO_EMAIL_TESTATA")
public class RegistroEmailTestata implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3345719795538152523L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EMAIL_TESTATA")
	private Integer idEmailTestata;

	@Column(name = "FK_CONFERENZA")
	private Integer idConferenza;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "FASE_EMAIL")
	private String faseEmail;

	@Column(name = "ID_MESSAGGIO")
	private String idMessaggio;

	@Column(name = "FK_TIPO_EVENTO")
	private String codiceTipoEvento;

	@Column(name = "MITTENTE")
	private String mittente;

	@Column(name = "DESTINATARIO")
	private String destinatario;
	
	@Column(name = "CORPO_MAIL")
	private String corpoMail;

	public Integer getIdEmailTestata() {
		return idEmailTestata;
	}

	public void setIdEmailTestata(Integer idEmailTestata) {
		this.idEmailTestata = idEmailTestata;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFaseEmail() {
		return faseEmail;
	}

	public void setFaseEmail(String faseEmail) {
		this.faseEmail = faseEmail;
	}

	public String getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
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

	public String getCorpoMail() {
		return corpoMail;
	}

	public void setCorpoMail(String corpoMail) {
		this.corpoMail = corpoMail;
	}

	public static RegistroEmailTestata getRegistroEmailTestata(MailMetadata metadata) {
		RegistroEmailTestata registro = new RegistroEmailTestata();
		registro.setEmail(metadata.getTo());
		registro.setFaseEmail(metadata.getFaseConcerenza());
		registro.setIdConferenza(metadata.getIdConferenza());
		registro.setIdMessaggio(metadata.getMessageId());
		registro.setSubject(metadata.getSubject());
		registro.setCodiceTipoEvento(metadata.getCodiceTipoEvento());
		registro.setMittente(metadata.getMittente());
		registro.setDestinatario(metadata.getDestinatario());
		registro.setCorpoMail(metadata.getMessage());
		return registro;
	}
}
