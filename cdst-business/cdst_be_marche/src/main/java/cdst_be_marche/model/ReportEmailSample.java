package cdst_be_marche.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@Entity
@Immutable
@Table(name = "report_email_sample")
@ApiModel(value="ReportEmailSample")
public class ReportEmailSample implements Serializable {

	private static final long serialVersionUID = 7616754770805999654L;
	
	
	
	@JsonProperty(value = "id_conferenza")
	@Column(name = "id_conferenza")
	private Integer id_conferenza;
	

	@JsonProperty(value = "email_destinatario")
	@Column(name = "email_destinatario")
	private String email_destinatario;
	

	@Id
	@JsonProperty(value = "id_messaggio")
	@Column(name = "id_messaggio")
	private String id_messaggio;

	@JsonProperty(value = "subject")
	@Column(name = "subject")
	private String subject;

	@JsonProperty(value = "fase_email")
	@Column(name = "fase_email")
	private String fase_email;
	
	@Id
	@JsonProperty(value = "dt_sent_email")
	@Column(name = "dt_sent_email")
	private Date dt_sent_email;	
	
	@JsonProperty(value = "stato_email")
	@Column(name = "stato_email")
	private String stato_email;

	public Integer getId_conferenza() {
		return id_conferenza;
	}

	public void setId_conferenza(Integer id_conferenza) {
		this.id_conferenza = id_conferenza;
	}

	public String getEmail_destinatario() {
		return email_destinatario;
	}

	public void setEmail_destinatario(String email_destinatario) {
		this.email_destinatario = email_destinatario;
	}


	public String getId_messaggio() {
		return id_messaggio;
	}

	public void setId_messaggio(String id_messaggio) {
		this.id_messaggio = id_messaggio;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFase_email() {
		return fase_email;
	}

	public void setFase_email(String fase_email) {
		this.fase_email = fase_email;
	}


	public String getStato_email() {
		return stato_email;
	}

	public void setStato_email(String stato_email) {
		this.stato_email = stato_email;
	}

	public Date getDt_sent_email() {
		return dt_sent_email;
	}

	public void setDt_sent_email(Date dt_sent_email) {
		this.dt_sent_email = dt_sent_email;
	}		
	
	
}
