package conferenza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import conferenza.model.bean.DeletableEntity;
import conferenza.util.DbConst;

/**
 * The persistent class for the persona_accreditata database table.
 * 
 */
@Entity
@Where(clause = "DATA_FINE is Null")
public class Evento extends DeletableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7519370666238994336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_EVENTO")
	private Integer id;

	@Column(name = "DATA_EVENTO")
	private Date data;

	@Column(name = "PROTOCOLLO")
	private String protocollo;
	
	@Column(name = "CORPO")
	private String corpo;
	
	@Column(name = "NOTE")
	private String note;	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_EVENTO")
	private TipoEvento tipoEvento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_OGGETTO_EVENTO")
	private OggettoEvento oggettoEvento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PARTECIPANTE")
	private Partecipante mittente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DOCUMENTO")
	private Documento documento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ESITO_CHIUSURA_CONFERENZA")
	private EsitoChiusuraConferenza esitoChiusuraConferenza;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_PARERE")
	private TipoParere tipoParere;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "EVENTO_PARTECIPANTE_VISIBILITA", joinColumns = @JoinColumn(name = "ID_EVENTO"), inverseJoinColumns = @JoinColumn(name = "ID_PARTECIPANTE"))
	private List<Partecipante> visibilitaEventoPartecipanti = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_MODIFICA_DATA")
	private ModificaData modificaData;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getProtocollo() {
		return protocollo;
	}

	public void setProtocollo(String protocollo) {
		this.protocollo = protocollo;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public OggettoEvento getOggettoEvento() {
		return oggettoEvento;
	}

	public void setOggettoEvento(OggettoEvento oggettoEvento) {
		this.oggettoEvento = oggettoEvento;
	}

	public Conferenza getConferenza() {
		return conferenza;
	}

	public void setConferenza(Conferenza conferenza) {
		this.conferenza = conferenza;
	}

	public Partecipante getMittente() {
		return mittente;
	}

	public void setMittente(Partecipante mittente) {
		this.mittente = mittente;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public EsitoChiusuraConferenza getEsitoChiusuraConferenza() {
		return esitoChiusuraConferenza;
	}

	public void setEsitoChiusuraConferenza(EsitoChiusuraConferenza esitoChiusuraConferenza) {
		this.esitoChiusuraConferenza = esitoChiusuraConferenza;
	}

	public List<Partecipante> getVisibilitaEventoPartecipanti() {
		return visibilitaEventoPartecipanti;
	}

	public void setVisibilitaEventoPartecipanti(List<Partecipante> visibilitaEventoPartecipanti) {
		this.visibilitaEventoPartecipanti = visibilitaEventoPartecipanti;
	}

	public TipoParere getTipoParere() {
		return tipoParere;
	}

	public void setTipoParere(TipoParere tipoParere) {
		this.tipoParere = tipoParere;
	}
	
	public Boolean isRichiestaIntegrazione() {
		return (tipoEvento.getCodice().equals(Integer.toString(DbConst.TIPOLOGIA_EVENTO_RICHIESTA_INTEGRAZIONE)));
	}
	
	public Boolean isEspressionePareri() {
		return (tipoEvento.getCodice().equals(Integer.toString(DbConst.TIPOLOGIA_EVENTO_ESPRESSIONE_PARERI)));
	}

	public ModificaData getModificaData() {
		return modificaData;
	}

	public void setModificaData(ModificaData modificaData) {
		this.modificaData = modificaData;
	}
	
}