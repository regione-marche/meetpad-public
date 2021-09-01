package cdst_be_marche.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;

import cdst_be_marche.repository.bean.ReportMailKey;
import cdst_be_marche.repository.bean.ReportMailSampleBean;
import io.swagger.annotations.ApiModel;

/**

CREATE OR REPLACE VIEW REPORT_EMAIL
AS
with pesonafisica as (
	select distinct pc.fk_conferenza id_conferenza, pp.id_persona,codice_fiscale,
	'meetpad@emarche.it' as email_mittente,
	case  when pp.email IS NULL then 
   (CASE WHEN ep.email is null then pc.pec_ente_competente else ep.email end)
    else pp.email END as email_destinatario
	from persona pp
	left join accreditamento acc on acc.fk_persona=pp.id_persona
	left join partecipante pc on pc.id_partecipante=acc.fk_partecipante	
	left join email_partecipante ep on ep.fk_partecipante=pc.id_partecipante
	WHERE pc.fk_conferenza is NOT NULL
)
select pf.*,cc.riferimento_istanza,ret.id_messaggio, ret.subject,ret.fase_email
,det.dt_sent_email,det.stato_email
from pesonafisica pf
inner join conferenza cc on cc.id_conferenza=pf.id_conferenza
left join REGISTRO_EMAIL_TESTATA ret on ret.fk_conferenza=cc.id_conferenza
left join REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio and det.email=ret.email
WHERE 1=1
AND not coalesce( trim(pf.email_destinatario),'')='' 
order by 1,2


i campi della vista sono ..
id_conferenza
id_persona
codice_fiscale
email_mittente
email_destinatario
riferimento_istanza
id_messaggio
subject
fase_email
dt_sent_email
stato_email

 * @author guideluc
 *
 */
@Entity
@Immutable
@Table(name = "report_email")
@IdClass(ReportMailKey.class)
@NamedNativeQueries({
	@NamedNativeQuery(
			name = "ReportEmail.findIdMessaggioByConferenceId", 
			query = "select distinct VV.id_conferenza,VV.Email_Destinatario,VV.id_messaggio,VV.fase_email  from cdst.REPORT_EMAIL VV  where  1=1 AND VV.id_conferenza=?", 
			resultClass = ReportMailSampleBean.class) 	
	,@NamedNativeQuery(
			name = "ReportEmail.reportEmailByConference",
			query =
			"with registro_testata as ( "+
			"  select ret.id_messaggio, max(det.dt_sent_email) dt_sent_email "+
			"  from cdst.REGISTRO_EMAIL_TESTATA ret  "+
			"  inner  join cdst.REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio "+ 	
			"  group by ret.id_messaggio	 "+
			" ),registro as ( "+
			"  select 'meetpad@emarche.it' as email_mittente, ret.email as email_destinatario,ret.fase_email,ret.fk_conferenza id_conferenza,ret.id_messaggio,ret.subject, "+ 
			"  det.dt_sent_email,det.stato_email,det.nota  "+
			"  from cdst.REGISTRO_EMAIL_TESTATA ret  "+
			"  inner  join cdst.REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio "+ 	
			" ) "+
			" select rr.* "+ 
			" from cdst.registro_testata rt "+
			" inner join cdst.registro rr on rr.id_messaggio=rt.id_messaggio and rr.dt_sent_email=rt.dt_sent_email "+
			" where rr.id_conferenza=? "
			,resultClass = ReportEmailSample.class) 
	
	,@NamedNativeQuery(
			name = "ReportEmail.getReportEmailSampleIdMessaggio",
			query =	
			" select 'meetpad@emarche.it' as email_mittente, ret.email as email_destinatario,ret.fase_email,ret.fk_conferenza id_conferenza,det.id_messaggio,ret.subject, "+ 
					" det.dt_sent_email,det.stato_email,det.nota   "+
					" from cdst.REGISTRO_EMAIL_DETTAGLIO det "+
					" left  join  cdst.REGISTRO_EMAIL_TESTATA ret   on det.id_messaggio=ret.id_messaggio "+ 
					" where 1=1 "+
					" and det.id_messaggio=? and det.stato_email =?"
			,resultClass = ReportEmailSample.class)
	
})
@ApiModel(value="ReportEmail")
public class ReportEmail implements Serializable {

	
	private static final long serialVersionUID = 7616754770805988654L;
	
	
	
	@JsonProperty(value = "id_conferenza")
	@Column(name = "id_conferenza")
	private Integer id_conferenza;
	
	@JsonProperty(value = "id_persona")
	@Column(name = "id_persona")
	private Integer id_persona;	
	
	@JsonProperty(value = "codice_fiscale")
	@Column(name = "codice_fiscale")
	private String codice_fiscale;

	@JsonProperty(value = "email_mittente")
	@Column(name = "email_mittente")
	private String email_mittente;

	@JsonProperty(value = "email_destinatario")
	@Column(name = "email_destinatario")
	private String email_destinatario;
	
	@JsonProperty(value = "riferimento_istanza")
	@Column(name = "riferimento_istanza")
	private String riferimento_istanza;

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

	public Integer getId_persona() {
		return id_persona;
	}

	public void setId_persona(Integer id_persona) {
		this.id_persona = id_persona;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public String getEmail_mittente() {
		return email_mittente;
	}

	public void setEmail_mittente(String email_mittente) {
		this.email_mittente = email_mittente;
	}

	public String getEmail_destinatario() {
		return email_destinatario;
	}

	public void setEmail_destinatario(String email_destinatario) {
		this.email_destinatario = email_destinatario;
	}

	public String getRiferimento_istanza() {
		return riferimento_istanza;
	}

	public void setRiferimento_istanza(String riferimento_istanza) {
		this.riferimento_istanza = riferimento_istanza;
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


















