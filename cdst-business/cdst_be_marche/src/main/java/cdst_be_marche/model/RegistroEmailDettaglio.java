package cdst_be_marche.model;

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
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * 
 * 
 * ID_EMAIL_DETTAGLIO EMAIL ID_MESSAGGIO STATO_EMAIL   --TRASMESSO
 * (DEFAULT),CONSEGNATO,ACCETTATO,ERRORE,NOTA DT_SENT_EMAIL NOTA
 * 
 * 
 * @author guideluc
 *
 */
@Entity
@Immutable
@Table(name = "REGISTRO_EMAIL_DETTAGLIO")
public class RegistroEmailDettaglio implements Serializable {

	private static final long serialVersionUID = 3345719795538152523L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EMAIL_DETTAGLIO")
	private Integer idEmailDettaglio;

	@Column(name = "ID_MESSAGGIO")
	private String idMessaggio;

	@Column(name = "STATO_EMAIL")
	private String statoEmail;

	@Column(name = "NOTA")
	private String nota;

	@Column(name = "DT_SENT_EMAIL")
	private Date dtSentEmail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_REGISTRO_EMAIL_TESTATA")
	private RegistroEmailTestata registroEmailTestata;

	public RegistroEmailDettaglio() {

	}

	public RegistroEmailDettaglio(String idMessaggio, String statoEmail, Date dtSentEmail, String nota, RegistroEmailTestata registroTestata) {
		this.idMessaggio = idMessaggio;
		this.statoEmail = statoEmail;
		this.dtSentEmail = dtSentEmail;
		this.nota = nota;
		this.registroEmailTestata = registroTestata;

	}

	public Integer getIdEmailDettaglio() {
		return idEmailDettaglio;
	}

	public void setIdEmailDettaglio(Integer idEmailDettaglio) {
		this.idEmailDettaglio = idEmailDettaglio;
	}

	public String getIdMessaggio() {
		return idMessaggio;
	}

	public void setIdMessaggio(String idMessaggio) {
		this.idMessaggio = idMessaggio;
	}

	public String getStatoEmail() {
		return statoEmail;
	}

	public void setStatoEmail(String statoEmail) {
		this.statoEmail = statoEmail;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public Date getDtSentEmail() {
		return dtSentEmail;
	}

	public void setDtSentEmail(Date dtSentEmail) {
		this.dtSentEmail = dtSentEmail;
	}

	public RegistroEmailTestata getRegistroEmailTestata() {
		return registroEmailTestata;
	}

	public void setRegistroEmailTestata(RegistroEmailTestata registroEmailTestata) {
		this.registroEmailTestata = registroEmailTestata;
	}

}
