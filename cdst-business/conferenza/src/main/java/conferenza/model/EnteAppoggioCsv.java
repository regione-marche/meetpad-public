package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EnteAppoggioCsv implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3310907648889499488L;

	@Id
	@Column(name = "cod_amm")
	private String cod_amm;
	
	@Column(name = "des_amm")
	private String des_amm;
	
	@Column(name = "comune")
	private String comune;
	
	@Column(name = "nome_resp")
	private String nome_resp;
	
	@Column(name = "cogn_resp")
	private String cogn_resp;
	
	@Column(name = "cap")
	private String cap;
	
	@Column(name = "provincia")
	private String provincia;
	
	@Column(name = "regione")
	private String regione;
	
	@Column(name = "sito_istituzionale")
	private String sito_istituzionale;
	
	@Column(name = "indirizzo")
	private String indirizzo;
	
	@Column(name = "titolo_resp")
	private String titolo_resp;
	
	@Column(name = "tipologia_istat")
	private String tipologia_istat;
	
	@Column(name = "tipologia_amm")
	private String tipologia_amm;
	
	@Column(name = "acronimo")
	private String acronimo;
	
	@Column(name = "cf_validato")
	private String cf_validato;
	
	@Column(name = "cf")
	private String cf;
	
	@Column(name = "mail1")
	private String mail1;
	
	@Column(name = "tipo_mail1")
	private String tipo_mail1;
	
	@Column(name = "mail2")
	private String mail2;
	
	@Column(name = "tipo_mail2")
	private String tipo_mail2;
	
	@Column(name = "mail3")
	private String mail3;
	
	@Column(name = "tipo_mail3")
	private String tipo_mail3;
	
	@Column(name = "mail4")
	private String mail4;
	
	@Column(name = "tipo_mail4")
	private String tipo_mail4;
	
	@Column(name = "mail5")
	private String mail5;
	
	@Column(name = "tipo_mail5")
	private String tipo_mail5;
	
	@Column(name = "url_facebook")
	private String url_facebook;
	
	@Column(name = "url_twitter")
	private String url_twitter;
	
	@Column(name = "url_googleplus")
	private String url_googleplus;
	
	@Column(name = "url_youtube")
	private String url_youtube;
	
	@Column(name = "liv_accessibili")
	private String liv_accessibili;

	public String getCod_amm() {
		return cod_amm;
	}

	public void setCod_amm(String cod_amm) {
		this.cod_amm = cod_amm;
	}

	public String getDes_amm() {
		return des_amm;
	}

	public void setDes_amm(String des_amm) {
		this.des_amm = des_amm;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getNome_resp() {
		return nome_resp;
	}

	public void setNome_resp(String nome_resp) {
		this.nome_resp = nome_resp;
	}

	public String getCogn_resp() {
		return cogn_resp;
	}

	public void setCogn_resp(String cogn_resp) {
		this.cogn_resp = cogn_resp;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getRegione() {
		return regione;
	}

	public void setRegione(String regione) {
		this.regione = regione;
	}

	public String getSito_istituzionale() {
		return sito_istituzionale;
	}

	public void setSito_istituzionale(String sito_istituzionale) {
		this.sito_istituzionale = sito_istituzionale;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTitolo_resp() {
		return titolo_resp;
	}

	public void setTitolo_resp(String titolo_resp) {
		this.titolo_resp = titolo_resp;
	}

	public String getTipologia_istat() {
		return tipologia_istat;
	}

	public void setTipologia_istat(String tipologia_istat) {
		this.tipologia_istat = tipologia_istat;
	}

	public String getTipologia_amm() {
		return tipologia_amm;
	}

	public void setTipologia_amm(String tipologia_amm) {
		this.tipologia_amm = tipologia_amm;
	}

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public String getCf_validato() {
		return cf_validato;
	}

	public void setCf_validato(String cf_validato) {
		this.cf_validato = cf_validato;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getMail1() {
		return mail1;
	}

	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}

	public String getTipo_mail1() {
		return tipo_mail1;
	}

	public void setTipo_mail1(String tipo_mail1) {
		this.tipo_mail1 = tipo_mail1;
	}

	public String getMail2() {
		return mail2;
	}

	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}

	public String getTipo_mail2() {
		return tipo_mail2;
	}

	public void setTipo_mail2(String tipo_mail2) {
		this.tipo_mail2 = tipo_mail2;
	}

	public String getMail3() {
		return mail3;
	}

	public void setMail3(String mail3) {
		this.mail3 = mail3;
	}

	public String getTipo_mail3() {
		return tipo_mail3;
	}

	public void setTipo_mail3(String tipo_mail3) {
		this.tipo_mail3 = tipo_mail3;
	}

	public String getMail4() {
		return mail4;
	}

	public void setMail4(String mail4) {
		this.mail4 = mail4;
	}

	public String getTipo_mail4() {
		return tipo_mail4;
	}

	public void setTipo_mail4(String tipo_mail4) {
		this.tipo_mail4 = tipo_mail4;
	}

	public String getMail5() {
		return mail5;
	}

	public void setMail5(String mail5) {
		this.mail5 = mail5;
	}

	public String getTipo_mail5() {
		return tipo_mail5;
	}

	public void setTipo_mail5(String tipo_mail5) {
		this.tipo_mail5 = tipo_mail5;
	}

	public String getUrl_facebook() {
		return url_facebook;
	}

	public void setUrl_facebook(String url_facebook) {
		this.url_facebook = url_facebook;
	}

	public String getUrl_twitter() {
		return url_twitter;
	}

	public void setUrl_twitter(String url_twitter) {
		this.url_twitter = url_twitter;
	}

	public String getUrl_googleplus() {
		return url_googleplus;
	}

	public void setUrl_googleplus(String url_googleplus) {
		this.url_googleplus = url_googleplus;
	}

	public String getUrl_youtube() {
		return url_youtube;
	}

	public void setUrl_youtube(String url_youtube) {
		this.url_youtube = url_youtube;
	}

	public String getLiv_accessibili() {
		return liv_accessibili;
	}

	public void setLiv_accessibili(String liv_accessibili) {
		this.liv_accessibili = liv_accessibili;
	}

}
