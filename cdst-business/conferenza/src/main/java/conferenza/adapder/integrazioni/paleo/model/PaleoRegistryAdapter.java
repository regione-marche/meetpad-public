package conferenza.adapder.integrazioni.paleo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import conferenza.model.Mailer;

/**
 * Livello adapter verso Paleo
 * @author guideluc
 *
 */
@Entity
@Table(name = "paleo_registry_adapter")
@NamedNativeQueries({
	@NamedNativeQuery(name = "PaleoRegistryAdapter.getAdapterByByRifExternoREgistro", query = "select pa.id,rd.id as fk_registro_documenti,pa.id_paleo_doc,pa.id_paleo_numerodoc,pa.id_paleo_protocollo,pa.fk_paleo_fascicolo,pa.id_paleo_registro,pa.paleo_tipo_protocollo,pa.paleo_installazione,pa.paleo_installazione,pa.paleo_segnatura_protocollo from paleo_registry_allegati_adapter ra inner join paleo_registry_adapter pa on pa.id=ra.fk_paleo_adapter inner join registro_documento rd on rd.rif_esterno=cast(ra.id as varchar)  where 1=1 and rd.rif_esterno=?", resultClass = PaleoRegistryAdapter.class)
})		
public class PaleoRegistryAdapter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	//Intrinsecamente contiene il tipo conferenza
	//se è null non è stata creata la conferenza
	//..registroDocummenti>idConderenza>fk_tipollogica_conferenza
	@Column(name = "FK_REGISTRO_DOCUMENTI")
	private Integer fk_registroDocummenti;
	
	//questo serve per il download
	@Column(name = "ID_PALEO_DOC")
	private Integer idPaleoDoc;

	//questo è un numero logico; un id doc..ma non è usato per il download!?!?
	@Column(name = "ID_PALEO_NUMERODOC")
	private Integer idPaleoNumeroDoc;

	
	@Column(name = "ID_PALEO_PROTOCOLLO")
	private Integer idPaleoProtocollo;
	@Column(name = "PALEO_PROTOCOLLO_DATA")
	private String paleoProtocolloData;
	
	//reference con PaoloFascicoloTipoConferenza  ()
	@Column(name = "FK_PALEO_FASCICOLO")
	private String fkPaleoFascicolo;
	
	@Column(name = "ID_FASCICOLO_TIPOCONFERENZA")
	private Integer idFascicoloTipoConferenza;	
	
	@Column(name = "ID_PALEO_REGISTRO")
	private String idPaleoRegistro;
	

	@Column(name = "PALEO_OGGETTO")
	private String paleoOggetto;
	@Column(name = "PALEO_TIPO_PROTOCOLLO")
	private String paleoTipoProtocollo;
	@Column(name = "PALEO_INSTALLAZIONE")
	private String paleoInstallazione;
	@Column(name = "PALEO_SEGNATURA_PROTOCOLLO")
	private String paleoSegnaturaProtocollo;
	
	
	public Integer getIdFascicoloTipoConferenza() {
		return idFascicoloTipoConferenza;
	}

	public void setIdFascicoloTipoConferenza(Integer idFascicoloTipoConferenza) {
		this.idFascicoloTipoConferenza = idFascicoloTipoConferenza;
	}

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

	public Integer getIdPaleoProtocollo() {
		return idPaleoProtocollo;
	}

	public void setIdPaleoProtocollo(Integer idPaleoProtocollo) {
		this.idPaleoProtocollo = idPaleoProtocollo;
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

	public Integer getIdPaleoNumeroDoc() {
		return idPaleoNumeroDoc;
	}

	public void setIdPaleoNumeroDoc(Integer idPaleoNumeroDoc) {
		this.idPaleoNumeroDoc = idPaleoNumeroDoc;
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

	public String getPaleoProtocolloData() {
		return paleoProtocolloData;
	}

	public void setPaleoProtocolloData(String paleoProtocolloData) {
		this.paleoProtocolloData = paleoProtocolloData;
	}
	
	
}
