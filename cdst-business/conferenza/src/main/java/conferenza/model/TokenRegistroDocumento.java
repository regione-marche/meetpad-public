package conferenza.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import conferenza.util.RandomUtil;

@Entity
public class TokenRegistroDocumento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8197334815699238167L;

	public TokenRegistroDocumento() {

	}

	public TokenRegistroDocumento(RegistroDocumento registroDocumento, Date scadenza) {
		this.token = RandomUtil.getRandomToken();
		this.registroDocumento = registroDocumento;
		this.scadenza = scadenza;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TOKEN")
	private Integer idToken;

	@Column(name = "TOKEN")
	private String token;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_REGISTRO")
	private RegistroDocumento registroDocumento;

	@Column(name = "SCADENZA")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date scadenza;

	public Integer getIdToken() {
		return idToken;
	}

	public void setIdToken(Integer idToken) {
		this.idToken = idToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RegistroDocumento getRegistroDocumento() {
		return registroDocumento;
	}

	public void setRegistroDocumento(RegistroDocumento registroDocumento) {
		this.registroDocumento = registroDocumento;
	}

	public Date getScadenza() {
		return scadenza;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}

}
