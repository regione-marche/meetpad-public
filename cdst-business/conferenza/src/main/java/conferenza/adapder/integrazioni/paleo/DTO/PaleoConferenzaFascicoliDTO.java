package conferenza.adapder.integrazioni.paleo.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.adapder.integrazioni.paleo.DTO.bean.PaleoDocumentiFascicoloDTO;


public class PaleoConferenzaFascicoliDTO {

	
	//questo serve per il download
	@JsonProperty("files")
	private List<PaleoDocumentiFascicoloDTO> listaFascicoliPaleo;	
	
	@JsonProperty("conferenceType")
	private String tipoConferenza;
	
	@JsonProperty("conferenceId")
	private String idConferenza;

	@JsonProperty("folderCode")
	private String codiceFascicolo;

	//questo è un numero logico; un id doc..ma non è usato per il download!?!?
	@JsonProperty("folderDescription")
	private String descrizioneFascicolo;
	
	@JsonProperty("manager")
	private String responsabile;
	
	@JsonProperty("administration")
	private String amministrazionePrecedente;
	
	
	
	
	public String getResponsabile() {
		return responsabile;
	}

	public void setResponsabile(String responsabile) {
		this.responsabile = responsabile;
	}

	public String getAmministrazionePrecedente() {
		return amministrazionePrecedente;
	}

	public void setAmministrazionePrecedente(String amministrazionePrecedente) {
		this.amministrazionePrecedente = amministrazionePrecedente;
	}

	public List<PaleoDocumentiFascicoloDTO> getListaFascicoliPaleo() {
		return listaFascicoliPaleo;
	}

	public void setListaFascicoliPaleo(List<PaleoDocumentiFascicoloDTO> listaFascicoliPaleo) {
		this.listaFascicoliPaleo = listaFascicoliPaleo;
	}

	public String getTipoConferenza() {
		return tipoConferenza;
	}

	public void setTipoConferenza(String tipoConferenza) {
		this.tipoConferenza = tipoConferenza;
	}

	public String getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(String idConferenza) {
		this.idConferenza = idConferenza;
	}

	public String getCodiceFascicolo() {
		return codiceFascicolo;
	}

	public void setCodiceFascicolo(String codiceFascicolo) {
		this.codiceFascicolo = codiceFascicolo;
	}

	public String getDescrizioneFascicolo() {
		return descrizioneFascicolo;
	}

	public void setDescrizioneFascicolo(String descrizioneFascicolo) {
		this.descrizioneFascicolo = descrizioneFascicolo;
	}


	
}
