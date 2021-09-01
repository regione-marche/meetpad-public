package conferenza.repository.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "report_mail_sample_bean")
public class ReportMailSampleBean implements Serializable {

	
	@Id
	private String id_messaggio;
	private Integer id_conferenza;
	
	private String email_destinatario;
	private String fase_email;
	
	
	public Integer getId_conferenza() {
		return id_conferenza;
	}
	public void setId_conferenza(Integer id_conferenza) {
		this.id_conferenza = id_conferenza;
	}
	public String getId_messaggio() {
		return id_messaggio;
	}
	public void setId_messaggio(String id_messaggio) {
		this.id_messaggio = id_messaggio;
	}
	public String getEmail_destinatario() {
		return email_destinatario;
	}
	public void setEmail_destinatario(String email_destinatario) {
		this.email_destinatario = email_destinatario;
	}
	public String getFase_email() {
		return fase_email;
	}
	public void setFase_email(String fase_email) {
		this.fase_email = fase_email;
	}
	
	
	
}
