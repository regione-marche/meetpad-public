package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EnteUfficiAppoggioCsv implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2019713351033873948L;
	
	@Id
	@Column(name = "cod_uni_ou")
	private String cod_uni_ou;
	
	@Column(name = "cod_amm")
	private String cod_amm;
	
	@Column(name = "des_ou")
	private String des_ou;
	
	@Column(name = "comune")
	private String comune;
	
	@Column(name = "nome_resp")
	private String nome_resp;
	
	@Column(name = "cogn_resp")
	private String cogn_resp;
	
	@Column(name = "Cap")
	private String cap;
	
	@Column(name = "provincia")
	private String provincia;
	
	@Column(name = "Regione")
	private String regione;
	
	@Column(name = "cod_ou")
	private String cod_ou;
	
	@Column(name = "Indirizzo")
	private String indirizzo;
	
	@Column(name = "cod_aoo")
	private String cod_aoo;
	
	@Column(name = "Tel")
	private String tel;
	
	@Column(name = "mail_resp")
	private String mail_resp;
	
	@Column(name = "tel_resp")
	private String tel_resp;
	
	@Column(name = "cod_ou_padre")
	private String cod_ou_padre;
	
	@Column(name = "Fax")
	private String fax;
	
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

	public String getCod_uni_ou() {
		return cod_uni_ou;
	}

	public void setCod_uni_ou(String cod_uni_ou) {
		this.cod_uni_ou = cod_uni_ou;
	}

	public String getCod_amm() {
		return cod_amm;
	}

	public void setCod_amm(String cod_amm) {
		this.cod_amm = cod_amm;
	}

	public String getDes_ou() {
		return des_ou;
	}

	public void setDes_ou(String des_ou) {
		this.des_ou = des_ou;
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

	public String getCod_ou() {
		return cod_ou;
	}

	public void setCod_ou(String cod_ou) {
		this.cod_ou = cod_ou;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCod_aoo() {
		return cod_aoo;
	}

	public void setCod_aoo(String cod_aoo) {
		this.cod_aoo = cod_aoo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail_resp() {
		return mail_resp;
	}

	public void setMail_resp(String mail_resp) {
		this.mail_resp = mail_resp;
	}

	public String getTel_resp() {
		return tel_resp;
	}

	public void setTel_resp(String tel_resp) {
		this.tel_resp = tel_resp;
	}

	public String getCod_ou_padre() {
		return cod_ou_padre;
	}

	public void setCod_ou_padre(String cod_ou_padre) {
		this.cod_ou_padre = cod_ou_padre;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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
	
}
