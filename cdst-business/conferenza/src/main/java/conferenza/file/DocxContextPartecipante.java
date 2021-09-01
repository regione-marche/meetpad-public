package conferenza.file;

import java.util.ArrayList;
import java.util.List;

import conferenza.DTO.PersonaRuoloConferenzaDTO;

public class DocxContextPartecipante {

	private String ente;
	private String ruolo;
	private String pec;
	private String cf;
	private String descEnte;
	private String competenza;
	private Boolean esprimeParere;
	private Boolean flagParereEspresso;
	private Boolean flagRichiestaIntegrazioneEffettuata;
	List<PersonaRuoloConferenzaDTO> listaUtente = new ArrayList<>();
	List<String> altreEmail = new ArrayList<>();
	private List<String> competenzaAutorizzativa = new ArrayList<>();
	private List<String> listaTipoParere = new ArrayList<>();

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getDescEnte() {
		return descEnte;
	}

	public void setDescEnte(String descEnte) {
		this.descEnte = descEnte;
	}

	public String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}

	public Boolean isEsprimeParere() {
		return esprimeParere;
	}

	public void setEsprimeParere(Boolean esprimeParere) {
		this.esprimeParere = esprimeParere;
	}
	
	public Boolean getFlagParereEspresso() {
		return flagParereEspresso;
	}

	public void setFlagParereEspresso(Boolean flagParereEspresso) {
		this.flagParereEspresso = flagParereEspresso;
	}

	public Boolean getFlagRichiestaIntegrazioneEffettuata() {
		return flagRichiestaIntegrazioneEffettuata;
	}

	public void setFlagRichiestaIntegrazioneEffettuata(Boolean flagRichiestaIntegrazioneEffettuata) {
		this.flagRichiestaIntegrazioneEffettuata = flagRichiestaIntegrazioneEffettuata;
	}

	public List<PersonaRuoloConferenzaDTO> getListaUtente() {
		return listaUtente;
	}

	public void setListaUtente(List<PersonaRuoloConferenzaDTO> listaUtente) {
		this.listaUtente = listaUtente;
	}

	public List<String> getAltreEmail() {
		return altreEmail;
	}

	public void setAltreEmail(List<String> altreEmail) {
		this.altreEmail = altreEmail;
	}

	public List<String> getCompetenzaAutorizzativa() {
		return competenzaAutorizzativa;
	}

	public void setCompetenzaAutorizzativa(List<String> competenzaAutorizzativa) {
		this.competenzaAutorizzativa = competenzaAutorizzativa;
	}

	public List<String> getListaTipoParere() {
		return listaTipoParere;
	}

	public void setListaTipoParere(List<String> listaTipoParere) {
		this.listaTipoParere = listaTipoParere;
	}

	public Boolean getEsprimeParere() {
		return esprimeParere;
	}
}