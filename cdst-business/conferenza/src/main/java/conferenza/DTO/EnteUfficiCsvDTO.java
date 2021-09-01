package conferenza.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnteUfficiCsvDTO {
	
	@JsonProperty("Cap")
	String cap;	
	
	@JsonProperty("Regione")
	String regione;
	
	@JsonProperty("Indirizzo")
	String indirizzo;
	
	@JsonProperty("Cf")
	String cf;
	
	@JsonProperty("Tel")
	String tel;
	
	@JsonProperty("Fax")
	String fax;
	
	String cod_ou;
	String cod_aoo;
	String cod_uni_ou;
	String provincia;
	String comune;
	String cod_amm;	
	String des_ou;
	String nome_resp;
	String cogn_resp;
	String mail_resp;
	String tel_resp;
	String cod_ou_padre;
	String mail1;	
	String tipo_mail1;	
	String mail2;	
	String tipo_mail2;	
	String mail3;	
	String tipo_mail3;
	
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCod_ou() {
		return cod_ou;
	}
	public void setCod_ou(String cod_ou) {
		this.cod_ou = cod_ou;
	}
	public String getCod_aoo() {
		return cod_aoo;
	}
	public void setCod_aoo(String cod_aoo) {
		this.cod_aoo = cod_aoo;
	}
	public String getCod_uni_ou() {
		return cod_uni_ou;
	}
	public void setCod_uni_ou(String cod_uni_ou) {
		this.cod_uni_ou = cod_uni_ou;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
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
