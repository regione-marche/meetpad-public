package cdst_be_marche.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CruscottoPec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2366815247102269091L;
	
	@Id
	@Column(name = "ID_EMAIL_TESTATA")
	private Integer id;

	@Column(name = "ID_MESSAGGIO")
	private String idMessaggio;

	@Column(name = "ID_CONFERENZA")
	private Integer idConferenza;
	
	@Column(name = "MITTENTE")
	private String mittente;
	
	@Column(name = "DESTINATARIO")
	private String destinatario;
	
	@Column(name = "EMAIL_DESTINATARIO")
	private String emailDestinatario;
	
	@Column(name = "CODICE_TIPO_EVENTO")
	private String codiceTipoEvento;
	
	@Column(name = "DESCR_TIPO_EVENTO")
	private String descrTipoEvento;
	
	@Column(name = "STATUS_MESSAGE")
	private String statusMessage;
	
	@Column(name = "CODICE_STATO_PEC")
	private String codiceStatoPec;
	
	@Column(name = "DESCR_STATO_PEC")
	private String descrStatoPec;
	
	@Column(name = "DATA_INVIO")
	private Date dataInvio;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
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

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public String getCodiceTipoEvento() {
		return codiceTipoEvento;
	}

	public void setCodiceTipoEvento(String codiceTipoEvento) {
		this.codiceTipoEvento = codiceTipoEvento;
	}

	public String getDescrTipoEvento() {
		return descrTipoEvento;
	}

	public void setDescrTipoEvento(String descrTipoEvento) {
		this.descrTipoEvento = descrTipoEvento;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getCodiceStatoPec() {
		return codiceStatoPec;
	}

	public void setCodiceStatoPec(String codiceStatoPec) {
		this.codiceStatoPec = codiceStatoPec;
	}

	public String getDescrStatoPec() {
		return descrStatoPec;
	}

	public void setDescrStatoPec(String descrStatoPec) {
		this.descrStatoPec = descrStatoPec;
	}

	public Date getDataInvio() {
		return dataInvio;
	}

	public void setDataInvio(Date dataInvio) {
		this.dataInvio = dataInvio;
	}
	

}
