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

import conferenza.model.bean.DeletableEntity;

/**
 * The persistent class for the documento database table.
 * 
 */
@Entity
public class DocumentoAttachment extends DeletableEntity implements Serializable {

	private static final long serialVersionUID = -8441716982654517533L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DOCUMENTO")
	private Integer idDocumento;

	@Column(name = "NOME")
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPOLOGIA_DOCUMENTO")
	private TipologiaDocumento tipologiaDocumento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CATEGORIA_DOCUMENTO")
	private CategoriaDocumento categoriaDocumento;

	@Column(name = "VISIBILITA_RISTRETTA")
	private Boolean visibilitaRistretta;

	@Column(name = "numero_protocollo")
	private String numeroProtocollo;

	@Column(name = "data_protocollo")
	private Date dataProtocollo;
	
	@Column(name = "numero_inventario")
	private String numeroInventario;
	
	@Column(name = "competenza_territoriale")
	private String competenzaTerritoriale;
	
	@Column(name = "data_riunione")
	private Date dataRiunione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ACCREDITAMENTO")
	private Accreditamento owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DOCUMENTO_PARTECIPANTE", joinColumns = @JoinColumn(name = "ID_DOCUMENTO"), inverseJoinColumns = @JoinColumn(name = "ID_PARTECIPANTE"))
	private List<Partecipante> visibilitaPartecipanti = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "DOCUMENTO_FIRMATARIO", joinColumns = @JoinColumn(name = "ID_DOCUMENTO"), inverseJoinColumns = @JoinColumn(name = "ID_ACCREDITAMENTO"))
	private List<Accreditamento> firmatari = new ArrayList<>();

	@Column(name = "file_conforme")
	private Boolean fileConforme;
	
	@Column(name = "md5")
	private String md5;

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipologiaDocumento getTipologiaDocumento() {
		return tipologiaDocumento;
	}

	public void setTipologiaDocumento(TipologiaDocumento tipologiaDocumento) {
		this.tipologiaDocumento = tipologiaDocumento;
	}

	public CategoriaDocumento getCategoriaDocumento() {
		return categoriaDocumento;
	}

	public void setCategoriaDocumento(CategoriaDocumento categoriaDocumento) {
		this.categoriaDocumento = categoriaDocumento;
	}

	public Boolean getVisibilitaRistretta() {
		return visibilitaRistretta;
	}

	public void setVisibilitaRistretta(Boolean visibilitaRistretta) {
		this.visibilitaRistretta = visibilitaRistretta;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}

	public String getNumeroInventario() {
		return numeroInventario;
	}

	public void setNumeroInventario(String numeroInventario) {
		this.numeroInventario = numeroInventario;
	}

	public Accreditamento getOwner() {
		return owner;
	}

	public void setOwner(Accreditamento owner) {
		this.owner = owner;
	}

	public Conferenza getConferenza() {
		return conferenza;
	}

	public void setConferenza(Conferenza conferenza) {
		this.conferenza = conferenza;
	}

	public String getCompetenzaTerritoriale() {
		return competenzaTerritoriale;
	}

	public void setCompetenzaTerritoriale(String competenzaTerritoriale) {
		this.competenzaTerritoriale = competenzaTerritoriale;
	}

	public Date getDataRiunione() {
		return dataRiunione;
	}

	public void setDataRiunione(Date dataRiunione) {
		this.dataRiunione = dataRiunione;
	}

	public List<Partecipante> getVisibilitaPartecipanti() {
		return visibilitaPartecipanti;
	}

	public void setVisibilitaPartecipanti(List<Partecipante> visibilitaPartecipanti) {
		this.visibilitaPartecipanti = visibilitaPartecipanti;
	}

	public List<Accreditamento> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(List<Accreditamento> firmatari) {
		this.firmatari = firmatari;
	}

	public Boolean getFileConforme() {
		return fileConforme;
	}

	public void setFileConforme(Boolean fileConforme) {
		this.fileConforme = fileConforme;
	}

	@Column(name = "FK_DOCUMENTO")
	private Integer fkDocumento;

	public Integer getFkDocumento() {
		return fkDocumento;
	}

	public void setFkDocumento(Integer fkDocumento) {
		this.fkDocumento = fkDocumento;
	}
	
}