package conferenza.adapder.integrazioni.paleo.adapter;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Configuration;


@Configuration
public class PaleoClientConfiguration {

	/**
	 * {PROD,SVIL ..i parametri possono essere su db che con flyway ha script unici..}
	 */
    @Value("${paleo.ambiente}")
    private String ambiente;
	
    @Value("${paleo.url}")
    private String url;
    
    @Value("${paleo.token.user}")
    private String urlLoginUser;
	
    @Value("${paleo.token.password}")
    private String urlLoginPswd;

    //OPERATORE
    @Value("${paleo.operatore.codiceUO}")
    private String operatoreCodiceUO;
    @Value("${paleo.operatore.nome}")
    private String operatoreNome;
    @Value("${paleo.operatore.cognome}")
    private String operatoreCognome;
    @Value("${paleo.operatore.ruolo}")
    private String operatoreRuolo;
    
    //OPERATORE USR
    @Value("${paleo.operatoreUSR.codiceUO}")
    private String operatoreUSRCodiceUO;
    @Value("${paleo.operatoreUSR.nome}")
    private String operatoreUSRNome;
    @Value("${paleo.operatoreUSR.cognome}")
    private String operatoreUSRCognome;
    @Value("${paleo.operatoreUSR.ruolo}")
    private String operatoreUSRRuolo;
    
    @Value("${paleo.escludiMail:NO}")
    private String paleoEscludiMail;
    
    @Value("${paleo.trasmissione.ragione:NOINVIO}")
    private String trasmRagione;
    @Value("${paleo.trasmissione.ragioneUsr:NOINVIO}")
    private String trasmRagioneUSR;
    @Value("${paleo.trasmissione.ruoloDestinatario}")
    private String TrasmRuoloDestinatario;
    @Value("${paleo.trasmissione.ruoloDestinatarioUsr}")
    private String TrasmRuoloDestinatarioUSR;
    @Value("${paleo.trasmissione.noteGenerali}")
    private String trasmNoteGenerali;
    @Value("${paleo.trasmissione.noteGeneraliUsr}")
    private String trasmNoteGeneraliUSR;
    @Value("${paleo.trasmissione.utenti:DEFAULT}")
    private String trasmUtenti;
    @Value("${paleo.trasmissione.utentiUsr:DEFAULT}")
    private String trasmUtentiUSR;
    @Value("${paleo.comunicazioneGenerica}")
    private String comunicazioneGenerica;
    @Value("${paleo.richiestaIntegrazioni}")
    private String richiestaIntegrazioni;
    @Value("${paleo.espressionePareri}")
    private String espressionePareri;

	public String getTrasmUtenti() {
		return trasmUtenti;
	}

	public void setTrasmUtenti(String trasmUtenti) {
		this.trasmUtenti = trasmUtenti;
	}

	public String getTrasmUtentiUSR() {
		return trasmUtentiUSR;
	}

	public void setTrasmUtentiUSR(String trasmUtentiUSR) {
		this.trasmUtentiUSR = trasmUtentiUSR;
	}

	public String getTrasmRagione() {
		return trasmRagione;
	}

	public void setTrasmRagione(String trasmRagione) {
		this.trasmRagione = trasmRagione;
	}

	public String getTrasmRagioneUSR() {
		return trasmRagioneUSR;
	}

	public void setTrasmRagioneUSR(String trasmRagioneUSR) {
		this.trasmRagioneUSR = trasmRagioneUSR;
	}

	public String getTrasmRuoloDestinatario() {
		return TrasmRuoloDestinatario;
	}

	public void setTrasmRuoloDestinatario(String trasmRuoloDestinatario) {
		TrasmRuoloDestinatario = trasmRuoloDestinatario;
	}

	public String getTrasmRuoloDestinatarioUSR() {
		return TrasmRuoloDestinatarioUSR;
	}

	public void setTrasmRuoloDestinatarioUSR(String trasmRuoloDestinatarioUSR) {
		TrasmRuoloDestinatarioUSR = trasmRuoloDestinatarioUSR;
	}

	public String getTrasmNoteGenerali() {
		return trasmNoteGenerali;
	}

	public void setTrasmNoteGenerali(String trasmNoteGenerali) {
		this.trasmNoteGenerali = trasmNoteGenerali;
	}

	public String getTrasmNoteGeneraliUSR() {
		return trasmNoteGeneraliUSR;
	}

	public void setTrasmNoteGeneraliUSR(String trasmNoteGeneraliUSR) {
		this.trasmNoteGeneraliUSR = trasmNoteGeneraliUSR;
	}

	public String getPaleoEscludiMail() {
		return paleoEscludiMail;
	}

	public void setPaleoEscludiMail(String paleoEscludiMail) {
		this.paleoEscludiMail = paleoEscludiMail;
	}

    public String getOperatoreUSRCodiceUO() {
		return operatoreUSRCodiceUO;
	}

	public void setOperatoreUSRCodiceUO(String operatoreUSRCodiceUO) {
		this.operatoreUSRCodiceUO = operatoreUSRCodiceUO;
	}

	public String getOperatoreUSRNome() {
		return operatoreUSRNome;
	}

	public void setOperatoreUSRNome(String operatoreUSRNome) {
		this.operatoreUSRNome = operatoreUSRNome;
	}

	public String getOperatoreUSRCognome() {
		return operatoreUSRCognome;
	}

	public void setOperatoreUSRCognome(String operatoreUSRCognome) {
		this.operatoreUSRCognome = operatoreUSRCognome;
	}

	public String getOperatoreUSRRuolo() {
		return operatoreUSRRuolo;
	}

	public void setOperatoreUSRRuolo(String operatoreUSRRuolo) {
		this.operatoreUSRRuolo = operatoreUSRRuolo;
	}

	@Value("${paleo.urlUSR}")
    private String urlUSR;
	
    @Value("${paleo.tokenUSR.user}")
    private String urlLoginUserUSR;
	
    @Value("${paleo.tokenUSR.password}")
    private String urlLoginPswdUSR;
    
    
	public String getUrlUSR() {
		return urlUSR;
	}

	public void setUrlUSR(String urlUSR) {
		this.urlUSR = urlUSR;
	}

	public String getUrlLoginUserUSR() {
		return urlLoginUserUSR;
	}

	public void setUrlLoginUserUSR(String urlLoginUserUSR) {
		this.urlLoginUserUSR = urlLoginUserUSR;
	}

	public String getUrlLoginPswdUSR() {
		return urlLoginPswdUSR;
	}

	public void setUrlLoginPswdUSR(String urlLoginPswdUSR) {
		this.urlLoginPswdUSR = urlLoginPswdUSR;
	}

	public String getUrlLoginUser() {
		return urlLoginUser;
	}

	public void setUrlLoginUser(String urlLoginUser) {
		this.urlLoginUser = urlLoginUser;
	}

	public String getUrlLoginPswd() {
		return urlLoginPswd;
	}

	public void setUrlLoginPswd(String urlLoginPswd) {
		this.urlLoginPswd = urlLoginPswd;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getOperatoreCodiceUO() {
		return operatoreCodiceUO;
	}

	public void setOperatoreCodiceUO(String operatoreCodiceUO) {
		this.operatoreCodiceUO = operatoreCodiceUO;
	}

	public String getOperatoreNome() {
		return operatoreNome;
	}

	public void setOperatoreNome(String operatoreNome) {
		this.operatoreNome = operatoreNome;
	}

	public String getOperatoreCognome() {
		return operatoreCognome;
	}

	public void setOperatoreCognome(String operatoreCognome) {
		this.operatoreCognome = operatoreCognome;
	}

	public String getOperatoreRuolo() {
		return operatoreRuolo;
	}

	public void setOperatoreRuolo(String operatoreRuolo) {
		this.operatoreRuolo = operatoreRuolo;
	}

	public String getComunicazioneGenerica() {
		return comunicazioneGenerica;
	}

	public void setComunicazioneGenerica(String comunicazioneGenerica) {
		this.comunicazioneGenerica = comunicazioneGenerica;
	}

	public String getRichiestaIntegrazioni() {
		return richiestaIntegrazioni;
	}

	public void setRichiestaIntegrazioni(String richiestaIntegrazioni) {
		this.richiestaIntegrazioni = richiestaIntegrazioni;
	}

	public String getEspressionePareri() {
		return espressionePareri;
	}

	public void setEspressionePareri(String espressionePareri) {
		this.espressionePareri = espressionePareri;
	}

    
}
