package cdst_be_marche.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Immutable
@Table(name = "registro_email_errori")
@NamedNativeQueries({
	@NamedNativeQuery(name = "ReportEmail.findAllMailInError"
			, query = "select ret.* " + 
			" from cdst.registro_email_errori ret" + 
			" left join  cdst.registro_email_errorirecuperati det on det.id_messaggio=ret.id_messaggio" + 
			" where 1=1" + 
			" and det.id_messaggio is null "
		+   " and ret.id_conferenza=? and ret.email_destinatario =?", resultClass = MailError.class)
})	
public class MailError  implements Serializable {

	@Id
	@JsonProperty("id_conferenza")
	private Integer id_conferenza;
	
	@JsonProperty("id_messaggio")
	private String id_messaggio;
	
	@JsonProperty("email_destinatario")
	private String email_destinatario;
	
	@JsonProperty("tentativi")
	private Long tentativi;

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

	public Long getTentativi() {
		return tentativi;
	}

	public void setTentativi(Long tentativi) {
		this.tentativi = tentativi;
	}

	public String getId_messaggio() {
		return id_messaggio;
	}

	public void setId_messaggio(String id_messaggio) {
		this.id_messaggio = id_messaggio;
	}

	
	
	
	
}
