package conferenza.adapder.integrazioni.indicepa.DTO.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * 
{
"cod_amm": "c_d488",
"acronimo": "",
"des_amm": "Comune di Fano"
}



cod_amm": "c_d489",
"acronimo": "",
"des_amm": "Comune di Fano Adriano",


"regione": "Abruzzo",
"provincia": "TE",
"comune": "Fano Adriano",
"cap": "64044",
"indirizzo": "Corso V. Emanuele, 2",
"titolo_resp": "Sindaco",
"nome_resp": "Adolfo",
"cogn_resp": "Moriconi",
"sito_istituzionale": "www.comune.fanoadriano.te.it",
"liv_accessibili": 1,
"mail1": "protocollo@cert.comune.fanoadriano.te.it",
"mail2": "servizidemografici@comune.fanoadriano.te.it",
"mail3": "fatturapa@cert.comune.fanoadriano.te.it",
"mail4": "",
"mail5": "",
"tipologia": "Pubbliche Amministrazioni",
"categoria": "Comuni e loro Consorzi e Associazioni",
"data_accreditamento": "2010-06-01",
"cf": "92001400677"


 * @author guideluc
 *
 */
@JsonInclude(Include.NON_NULL)
public class IndicePaDataDTO {

	@JsonProperty("cod_amm")
	String codAmm;
	@JsonProperty("acronimo")
	String acronimo;
	@JsonProperty("des_amm")
	String desAmm;
	@JsonProperty("regione")
	String regione;
	@JsonProperty("provincia")
	String provincia;
	@JsonProperty("comune")
	String comune;	
	@JsonProperty("cap")
	String cap;	
	@JsonProperty("indirizzo")
	String indirizzo;
	@JsonProperty("titolo_resp")
	String titoloResp;	
	@JsonProperty("nome_resp")
	String nomeResp;
	@JsonProperty("cogn_resp")
	String cognResp;
	@JsonProperty("sito_istituzionale")
	String sitoIstituzionale;	
	@JsonProperty("liv_accessibili")
	Integer livAccessibili;
	@JsonProperty("mail1")
	String mail1;	
	@JsonProperty("mail2")
	String mail2;	
	@JsonProperty("mail3")
	String mail3;	
	@JsonProperty("mail4")
	String mail4;	
	@JsonProperty("mail5")
	String mail5;	

	@JsonProperty("tipologia")
	String tipologia;	
	@JsonProperty("categoria")
	String categoria;	
	@JsonProperty("data_accreditamento")
	String data_accreditamento;	

	@JsonProperty("cf")
	String cf;	
	
	
	
	
	public String getCodAmm() {
		return codAmm;
	}
	public void setCodAmm(String codAmm) {
		this.codAmm = codAmm;
	}
	public String getAcronimo() {
		return acronimo;
	}
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}
	public String getDesAmm() {
		return desAmm;
	}
	public void setDesAmm(String desAmm) {
		this.desAmm = desAmm;
	}
	public String getRegione() {
		return regione;
	}
	public void setRegione(String regione) {
		this.regione = regione;
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
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getTitoloResp() {
		return titoloResp;
	}
	public void setTitoloResp(String titoloResp) {
		this.titoloResp = titoloResp;
	}
	public String getNomeResp() {
		return nomeResp;
	}
	public void setNomeResp(String nomeResp) {
		this.nomeResp = nomeResp;
	}
	public String getCognResp() {
		return cognResp;
	}
	public void setCognResp(String cognResp) {
		this.cognResp = cognResp;
	}
	public String getSitoIstituzionale() {
		return sitoIstituzionale;
	}
	public void setSitoIstituzionale(String sitoIstituzionale) {
		this.sitoIstituzionale = sitoIstituzionale;
	}
	public Integer getLivAccessibili() {
		return livAccessibili;
	}
	public void setLivAccessibili(Integer livAccessibili) {
		this.livAccessibili = livAccessibili;
	}
	public String getMail1() {
		return mail1;
	}
	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}
	public String getMail2() {
		return mail2;
	}
	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}
	public String getMail3() {
		return mail3;
	}
	public void setMail3(String mail3) {
		this.mail3 = mail3;
	}
	public String getMail4() {
		return mail4;
	}
	public void setMail4(String mail4) {
		this.mail4 = mail4;
	}
	public String getMail5() {
		return mail5;
	}
	public void setMail5(String mail5) {
		this.mail5 = mail5;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getData_accreditamento() {
		return data_accreditamento;
	}
	public void setData_accreditamento(String data_accreditamento) {
		this.data_accreditamento = data_accreditamento;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	
	
}
