package conferenza.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import conferenza.util.RandomUtil;

@Entity
@NamedNativeQueries({
		@NamedNativeQuery(name = "TockenConference.findByTKN", query = "select  tk.* "
				+ "      from cdst.tocken_conference tk" + "      WHERE 1=1 " + "      and tk.TKN1=? "
				+ "      and tk.TKN2=?", resultClass = TockenConference.class),
		@NamedNativeQuery(name = "TockenConference.findInfoVideoconferenza", query = "select cc.* from cdst.conferenza cc where cc.id_conferenza=?", resultClass = Conferenza.class)

})
public class TockenConference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3582379221161665845L;

	public TockenConference() {

	}

	public TockenConference(String email, Integer idEnte, Integer idPartecipante, Integer idConferenza, String lsCF,
			String pFase) {
		String lsTOKEN1 = RandomUtil.getRandomToken();
		String lsTOKEN2 = RandomUtil.getRandomToken();
		this.setTKN1(lsTOKEN1);
		this.setTKN2(lsTOKEN2);
		this.setEmail(email);
		this.setIdEnte(idEnte);
		this.setIdPartecipante(idPartecipante);
		this.setIdConferenza(idConferenza);
		this.setCodice_fiscale(lsCF);
		this.setTentativi(new Integer(1));
		this.setFase(pFase);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TOKEN")
	private Integer idToken;

	@Column(name = "TKN1")
	private String TKN1;

	@Column(name = "TKN2")
	private String TKN2;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "codice_fiscale")
	private String codice_fiscale;

	@Column(name = "ID_ENTE")
	private Integer idEnte;

	@Column(name = "ID_PARTECIPANTE")
	private Integer idPartecipante;

	@Column(name = "ID_CONFERENZA")
	private Integer idConferenza;

	@Column(name = "TENTATIVI")
	private Integer tentativi;

	@Column(name = "DT_FINE_VALIDITA")
	private Date dtFineValidita;

	@Column(name = "FASE")
	private String fase;

	public Integer getIdToken() {
		return idToken;
	}

	public void setIdToken(Integer idToken) {
		this.idToken = idToken;
	}

	public String getTKN1() {
		return TKN1;
	}

	public void setTKN1(String tKN1) {
		TKN1 = tKN1;
	}

	public String getTKN2() {
		return TKN2;
	}

	public void setTKN2(String tKN2) {
		TKN2 = tKN2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public Integer getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(Integer idEnte) {
		this.idEnte = idEnte;
	}

	public Integer getIdPartecipante() {
		return idPartecipante;
	}

	public void setIdPartecipante(Integer idPartecipante) {
		this.idPartecipante = idPartecipante;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public Integer getTentativi() {
		return tentativi;
	}

	public void setTentativi(Integer tentativi) {
		this.tentativi = tentativi;
	}

	public Date getDtFineValidita() {
		return dtFineValidita;
	}

	public void setDtFineValidita(Date dtFineValidita) {
		this.dtFineValidita = dtFineValidita;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

}
