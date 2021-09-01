package conferenza.file;

import java.util.ArrayList;
import java.util.List;

public class DocxContext {
	
	private String entePartecipante;
	private String ammProc;
	private String tipologiaConferenza;
	private Integer idConferenza;
	private String riferimentoIstanza;
	private String oggetto;
	private String data;
	private String ora;
	private String luogo;
	private String termineRichiestaIntegrazioni;
	private String termineEspressionePareri;
	private String urlDocumentazione;
	private String nomeRichiedente;
	private String cognomeRichiedente;
	private String nomeResponsabile;
	private String cognomeResponsabile;
	private String denominazione;
	private List<String> entiInvitati;
	private String dataAvvio;
	private String numeroProtocollo;
	private String dataProtocollo;
	private String codiceFiscaleRichiedente;
	private String localizzazioneIndirizzo;
	private String localizzazioneComune;
	private String localizzazioneProvincia;
	private String pecRichiedente;
	private List<DocxContextPartecipante> partecipantes = new ArrayList<>();
	private String dataAnno;
	private String dataMese;
	private String dataGiorno;
	private String foglioMappale;
	private List<String> partecipantiInizioTemplate;
	
	private String siglaProvincia;
	

	public String getEntePartecipante() {
		return entePartecipante;
	}

	public void setEntePartecipante(String entePartecipante) {
		this.entePartecipante = entePartecipante;
	}

	public String getAmmProc() {
		return ammProc;
	}

	public void setAmmProc(String ammProc) {
		this.ammProc = ammProc;
	}

	public String getTipologiaConferenza() {
		return tipologiaConferenza;
	}

	public void setTipologiaConferenza(String tipologiaConferenza) {
		this.tipologiaConferenza = tipologiaConferenza;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getRiferimentoIstanza() {
		return riferimentoIstanza;
	}

	public void setRiferimentoIstanza(String riferimentoIstanza) {
		this.riferimentoIstanza = riferimentoIstanza;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String getTermineRichiestaIntegrazioni() {
		return termineRichiestaIntegrazioni;
	}

	public void setTermineRichiestaIntegrazioni(String termineRichiestaIntegrazioni) {
		this.termineRichiestaIntegrazioni = termineRichiestaIntegrazioni;
	}

	public String getTermineEspressionePareri() {
		return termineEspressionePareri;
	}

	public void setTermineEspressionePareri(String termineEspressionePareri) {
		this.termineEspressionePareri = termineEspressionePareri;
	}

	public String getUrlDocumentazione() {
		return urlDocumentazione;
	}

	public void setUrlDocumentazione(String urlDocumentazione) {
		this.urlDocumentazione = urlDocumentazione;
	}

	public String getNomeRichiedente() {
		return nomeRichiedente;
	}

	public void setNomeRichiedente(String nomeRichiedente) {
		this.nomeRichiedente = nomeRichiedente;
	}

	public String getCognomeRichiedente() {
		return cognomeRichiedente;
	}

	public void setCognomeRichiedente(String cognomeRichiedente) {
		this.cognomeRichiedente = cognomeRichiedente;
	}

	public String getNomeResponsabile() {
		return nomeResponsabile;
	}

	public void setNomeResponsabile(String nomeResponsabile) {
		this.nomeResponsabile = nomeResponsabile;
	}

	public String getCognomeResponsabile() {
		return cognomeResponsabile;
	}

	public void setCognomeResponsabile(String cognomeResponsabile) {
		this.cognomeResponsabile = cognomeResponsabile;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public List<String> getEntiInvitati() {
		return entiInvitati;
	}

	public void setEntiInvitati(List<String> entiInvitati) {
		this.entiInvitati = entiInvitati;
	}

	public String getDataAvvio() {
		return dataAvvio;
	}

	public void setDataAvvio(String dataAvvio) {
		this.dataAvvio = dataAvvio;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(String dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}
	
	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getLocalizzazioneIndirizzo() {
		return localizzazioneIndirizzo;
	}

	public void setLocalizzazioneIndirizzo(String localizzazioneIndirizzo) {
		this.localizzazioneIndirizzo = localizzazioneIndirizzo;
	}

	public String getLocalizzazioneComune() {
		return localizzazioneComune;
	}

	public void setLocalizzazioneComune(String localizzazioneComune) {
		this.localizzazioneComune = localizzazioneComune;
	}

	public String getLocalizzazioneProvincia() {
		return localizzazioneProvincia;
	}

	public void setLocalizzazioneProvincia(String localizzazioneProvincia) {
		this.localizzazioneProvincia = localizzazioneProvincia;
	}

	public String getPecRichiedente() {
		return pecRichiedente;
	}

	public void setPecRichiedente(String pecRichiedente) {
		this.pecRichiedente = pecRichiedente;
	}

	public List<DocxContextPartecipante> getPartecipantes() {
		return partecipantes;
	}

	public void setPartecipantes(List<DocxContextPartecipante> partecipantes) {
		this.partecipantes = partecipantes;
	}

	public String getDataAnno() {
		return dataAnno;
	}

	public void setDataAnno(String dataAnno) {
		this.dataAnno = dataAnno;
	}

	public String getDataMese() {
		return dataMese;
	}

	public void setDataMese(String dataMese) {
		this.dataMese = dataMese;
	}

	public String getDataGiorno() {
		return dataGiorno;
	}

	public void setDataGiorno(String dataGiorno) {
		this.dataGiorno = dataGiorno;
	}
	
	public String getFoglioMappale() {
		return foglioMappale;
	}

	public void setFoglioMappale(String foglioMappale) {
		this.foglioMappale = foglioMappale;
	}
	
	public List<String> getPartecipantiInizioTemplate() {
		return partecipantiInizioTemplate;
	}

	public void setPartecipantiInizioTemplate(List<String> partecipantiInizioTemplate) {
		this.partecipantiInizioTemplate = partecipantiInizioTemplate;
	}

	public String getSiglaProvincia() {
		return siglaProvincia;
	}

	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}

	

}
