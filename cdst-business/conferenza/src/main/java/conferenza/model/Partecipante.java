package conferenza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Where;

import conferenza.model.bean.DeletableEntity;
import conferenza.model.bean._Typological;
import conferenza.util.DbConst;

/**
 * The persistent class for the partecipante database table.
 * 
 */
@Entity
@Where(clause = "DATA_FINE is Null")
public class Partecipante extends DeletableEntity implements Serializable, _Typological {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2488683036607810004L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PARTECIPANTE")
	private Integer idPartecipante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RUOLO_PARTECIPANTE")
	private RuoloPartecipante ruoloPartecipante;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ENTE")
	private Ente ente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;
	
	@Column(name = "COMPETENZA")
	private String competenza;

	@OneToMany(mappedBy = "partecipante")
	private List<EmailPartecipante> altreEmail = new ArrayList<>();
	
	@Column(name = "DESCRIZIONE_ENTE_COMPETENTE")
	private String descEnteCompetente;
	
	@Column(name = "PEC_ENTE_COMPETENTE")
	private String pecEnteCompetente;
	
	@Column(name = "CODICE_FISCALE_ENTE_COMPETENTE")
	private String cfEnteCompetente;
	
	@Column(name = "ESPRIME_DETERMINAZIONE")
	private Boolean esprimeDeterminazione;
	
	@OneToMany(mappedBy = "partecipante")
	private List<Accreditamento> accreditati = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "EVENTO_PARTECIPANTE_VISIBILITA", joinColumns = @JoinColumn(name = "ID_PARTECIPANTE"), inverseJoinColumns = @JoinColumn(name = "ID_EVENTO"))
	private List<Evento> visibilitaPartecipanteEventi = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DOCUMENTO_PARTECIPANTE", joinColumns = @JoinColumn(name = "ID_PARTECIPANTE"), inverseJoinColumns = @JoinColumn(name = "ID_DOCUMENTO"))
	private List<Documento> visibilitaDocumenti = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PARTECIPANTE_COMPETENZA_AUTORIZZATIVA", joinColumns = @JoinColumn(name = "ID_PARTECIPANTE", 
		referencedColumnName = "ID_PARTECIPANTE"), inverseJoinColumns = @JoinColumn(name = "CODICE_COMPETENZA_AUTORIZZATIVA", 
		referencedColumnName = "CODICE", table = "COMPETENZA_AUTORIZZATIVA"))
	private List<CompetenzaAutorizzativa> listaCompAutoPerPartecipante = new ArrayList<>();
	
	public Partecipante() {
	}

	public Integer getIdPartecipante() {
		return idPartecipante;
	}

	public void setIdPartecipante(Integer idPartecipante) {
		this.idPartecipante = idPartecipante;
	}

	public Ente getEnte() {
		return this.ente;
	}

	public RuoloPartecipante getRuoloPartecipante() {
		return ruoloPartecipante;
	}

	public void setRuoloPartecipante(RuoloPartecipante ruoloPartecipante) {
		this.ruoloPartecipante = ruoloPartecipante;
	}

	public void setEnte(Ente ente) {
		this.ente = ente;
	}

	public Conferenza getConferenza() {
		return this.conferenza;
	}

	public void setConferenza(Conferenza conferenza) {
		this.conferenza = conferenza;
	}

	public String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}

	public List<EmailPartecipante> getAltreEmail() {
		return altreEmail;
	}

	public void setAltreEmail(List<EmailPartecipante> altreEmail) {
		this.altreEmail = altreEmail;
	}

	public String getDescEnteCompetente() {
		return descEnteCompetente;
	}

	public void setDescEnteCompetente(String descEnteCompetente) {
		this.descEnteCompetente = descEnteCompetente;
	}

	public String getPecEnteCompetente() {
		return pecEnteCompetente;
	}

	public void setPecEnteCompetente(String pecEnteCompetente) {
		this.pecEnteCompetente = pecEnteCompetente;
	}

	public String getCfEnteCompetente() {
		return cfEnteCompetente;
	}

	public void setCfEnteCompetente(String cfEnteCompetente) {
		this.cfEnteCompetente = cfEnteCompetente;
	}

	public Boolean getEsprimeDeterminazione() {
		return esprimeDeterminazione;
	}

	public void setEsprimeDeterminazione(Boolean esprimeDeterminazione) {
		this.esprimeDeterminazione = esprimeDeterminazione;
	}

	public List<Accreditamento> getAccreditati() {
		return accreditati;
	}

	public void setAccreditati(List<Accreditamento> accreditati) {
		this.accreditati = accreditati;
	}

	public List<Evento> getVisibilitaPartecipanteEventi() {
		return visibilitaPartecipanteEventi;
	}

	public void setVisibilitaPartecipanteEventi(List<Evento> visibilitaPartecipanteEventi) {
		this.visibilitaPartecipanteEventi = visibilitaPartecipanteEventi;
	}

	public List<Documento> getVisibilitaDocumenti() {
		return visibilitaDocumenti;
	}

	public void setVisibilitaDocumenti(List<Documento> visibilitaDocumenti) {
		this.visibilitaDocumenti = visibilitaDocumenti;
	}
	
	public List<CompetenzaAutorizzativa> getListaCompAutoPerPartecipante() {
		return listaCompAutoPerPartecipante;
	}

	public void setListaCompAutoPerPartecipante(List<CompetenzaAutorizzativa> listaCompAutoPerPartecipante) {
		this.listaCompAutoPerPartecipante = listaCompAutoPerPartecipante;
	}

	public Boolean isRichiedente() {
		return Integer.parseInt(getRuoloPartecipante().getCodice()) == DbConst.RUOLO_PARTECIPANTE_RICHIEDENTE;
	}

	public Boolean isAmministrazioneProcendete() {
		return Integer
				.parseInt(getRuoloPartecipante().getCodice()) == DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PROCEDENTE;
	}
	
	public Boolean isAmministrazionePerPareriInterni() {
		return Integer.parseInt(
				getRuoloPartecipante().getCodice()) == DbConst.RUOLO_PARTECIPANTE_AMMINISTRAZIONE_PER_PARERI_INTERNI;
	}

	@Override
	public String getCodice() {
		return getIdPartecipante().toString();
	}

	@Override
	public String getDescrizione() {
		return getEnte().getDescrizione();
	}
	
}