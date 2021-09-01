package conferenza.adapder.integrazioni.paleo.DTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.DocumentAdapterDTO;
import conferenza.adapder.integrazioni.paleo.model.PaleoRegistryAdapter;

/**
 * 
 * @author guideluc
 *
 */
public class PaleoDocumentAdapterDTO  extends DocumentAdapterDTO {

	@JsonProperty("ID")
	private Integer id;
	
	//Intrinsecamente contiene il tipo conferenza
	//se è null non è stata creata la conferenza
	//..registroDocummenti>idConderenza>fk_tipollogica_conferenza
	@JsonProperty("FK_REGISTRO_DOCUMENTI")
	private Integer fk_registroDocummenti;
	
	//questo serve per il download
	@JsonProperty("ID_PALEO_DOC")
	private Integer idPaleoDoc;

	//questo è un numero logico; un id doc..ma non è usato per il download!?!?
	@JsonProperty("ID_PALEO_NUMERODOC")
	private Integer idPaleoNumeroDoc;

	
	@JsonProperty("ID_PALEO_PROTOCOLLO")
	private Integer idPaleoProtocollo;
	@JsonProperty("PALEO_PROTOCOLLO_DATA")
	private String paleoProtocolloData;
	
	//reference con PaoloFascicoloTipoConferenza  ()
	@JsonProperty("FK_PALEO_FASCICOLO")
	private String fkPaleoFascicolo;
	
	@JsonProperty("ID_PALEO_REGISTRO")
	private String idPaleoRegistro;
	

	@JsonProperty( "PALEO_OGGETTO")
	private String paleoOggetto;
	@JsonProperty("PALEO_TIPO_PROTOCOLLO")
	private String paleoTipoProtocollo;
	@JsonProperty("PALEO_INSTALLAZIONE")
	private String paleoInstallazione;
	@JsonProperty("PALEO_SEGNATURA_PROTOCOLLO")
	private String paleoSegnaturaProtocollo;
	
	@JsonProperty("PALEO_ERROR")
	private String paleoError;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFk_registroDocummenti() {
		return fk_registroDocummenti;
	}
	public void setFk_registroDocummenti(Integer fk_registroDocummenti) {
		this.fk_registroDocummenti = fk_registroDocummenti;
	}
	public Integer getIdPaleoDoc() {
		return idPaleoDoc;
	}
	public void setIdPaleoDoc(Integer idPaleoDoc) {
		this.idPaleoDoc = idPaleoDoc;
	}
	public Integer getIdPaleoNumeroDoc() {
		return idPaleoNumeroDoc;
	}
	public void setIdPaleoNumeroDoc(Integer idPaleoNumeroDoc) {
		this.idPaleoNumeroDoc = idPaleoNumeroDoc;
	}
	public Integer getIdPaleoProtocollo() {
		return idPaleoProtocollo;
	}
	public void setIdPaleoProtocollo(Integer idPaleoProtocollo) {
		this.idPaleoProtocollo = idPaleoProtocollo;
	}
	public String getPaleoProtocolloData() {
		return paleoProtocolloData;
	}
	public void setPaleoProtocolloData(String paleoProtocolloData) {
		this.paleoProtocolloData = paleoProtocolloData;
	}
	public String getFkPaleoFascicolo() {
		return fkPaleoFascicolo;
	}
	public void setFkPaleoFascicolo(String fkPaleoFascicolo) {
		this.fkPaleoFascicolo = fkPaleoFascicolo;
	}
	public String getIdPaleoRegistro() {
		return idPaleoRegistro;
	}
	public void setIdPaleoRegistro(String idPaleoRegistro) {
		this.idPaleoRegistro = idPaleoRegistro;
	}
	public String getPaleoOggetto() {
		return paleoOggetto;
	}
	public void setPaleoOggetto(String paleoOggetto) {
		this.paleoOggetto = paleoOggetto;
	}
	public String getPaleoTipoProtocollo() {
		return paleoTipoProtocollo;
	}
	public void setPaleoTipoProtocollo(String paleoTipoProtocollo) {
		this.paleoTipoProtocollo = paleoTipoProtocollo;
	}
	public String getPaleoInstallazione() {
		return paleoInstallazione;
	}
	public void setPaleoInstallazione(String paleoInstallazione) {
		this.paleoInstallazione = paleoInstallazione;
	}
	public String getPaleoSegnaturaProtocollo() {
		return paleoSegnaturaProtocollo;
	}
	public void setPaleoSegnaturaProtocollo(String paleoSegnaturaProtocollo) {
		this.paleoSegnaturaProtocollo = paleoSegnaturaProtocollo;
	}

	public String getPaleoError() {
		return paleoError;
	}
	public void setPaleoError(String paleoError) {
		this.paleoError = paleoError;
	}
	
	public static PaleoDocumentAdapterDTO fillPaleoDocumentAdapterDTO(PaleoRegistryAdapter adapter ) {
		PaleoDocumentAdapterDTO dto = new PaleoDocumentAdapterDTO();
		dto.setFk_registroDocummenti(adapter.getFk_registroDocummenti());
		dto.setDataProtocollo(adapter.getPaleoProtocolloData());
		dto.setFkPaleoFascicolo(adapter.getFkPaleoFascicolo());
		dto.setId(adapter.getId());
		dto.setIdPaleoDoc(adapter.getIdPaleoDoc());
		dto.setIdPaleoNumeroDoc(adapter.getIdPaleoNumeroDoc());
		dto.setIdPaleoProtocollo(adapter.getIdPaleoProtocollo());
		dto.setIdPaleoRegistro(adapter.getIdPaleoRegistro());
		dto.setPaleoOggetto(adapter.getPaleoOggetto());
		dto.setPaleoProtocolloData(adapter.getPaleoProtocolloData());
		dto.setPaleoSegnaturaProtocollo(adapter.getPaleoSegnaturaProtocollo());		
		return dto;
	}
	
}
