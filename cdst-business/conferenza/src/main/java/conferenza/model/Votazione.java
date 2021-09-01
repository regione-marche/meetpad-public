package conferenza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Where;

import conferenza.model.bean.DeletableEntity;

/**
 * The persistent class for the votazione database table.
 * 
 */
@Entity
@Where(clause = "DATA_FINE is Null")
public class Votazione extends DeletableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4722246981923717396L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_VOTAZIONE")
	private Integer idVotazione;

	@Column(name = "ARGOMENTO")
	private String argomento;

	@Column(name = "DATA_SCADENZA")
	private Date dataScadenza;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_VOTAZIONE")
	private VotazioneTipo tipoVotazione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CRITERIO_VOTAZIONE")
	private VotazioneCriterio criterioVotazione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_STATO_VOTAZIONE")
	private VotazioneStato statoVotazione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ESITO_VOTAZIONE")
	private VotazioneEsito esitoVotazione;

	@OneToMany(mappedBy = "votazione")
	private List<VotazioneVoto> voti = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_votazione_dati", referencedColumnName = "id_votazione_dati")
	private VotazioneDati dati;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "VOTAZIONE_PARTECIPANTE", joinColumns = @JoinColumn(name = "ID_VOTAZIONE"), inverseJoinColumns = @JoinColumn(name = "ID_PARTECIPANTE"))
	private List<Partecipante> votanti = new ArrayList<>();
	
	public VotazioneDati getDati() {
		if(dati == null) {
			this.dati = new VotazioneDati();
		}
		return dati;
	}

	public void setDati(VotazioneDati dati) {
		this.dati = dati;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIdVotazione() {
		return idVotazione;
	}

	public void setIdVotazione(Integer idVotazione) {
		this.idVotazione = idVotazione;
	}

	public String getArgomento() {
		return argomento;
	}

	public void setArgomento(String argomento) {
		this.argomento = argomento;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Conferenza getConferenza() {
		return conferenza;
	}

	public void setConferenza(Conferenza conferenza) {
		this.conferenza = conferenza;
	}

	public VotazioneTipo getTipoVotazione() {
		return tipoVotazione;
	}

	public void setTipoVotazione(VotazioneTipo tipoVotazione) {
		this.tipoVotazione = tipoVotazione;
	}

	public VotazioneCriterio getCriterioVotazione() {
		return criterioVotazione;
	}

	public void setCriterioVotazione(VotazioneCriterio criterioVotazione) {
		this.criterioVotazione = criterioVotazione;
	}

	public VotazioneStato getStatoVotazione() {
		return statoVotazione;
	}

	public void setStatoVotazione(VotazioneStato statoVotazione) {
		this.statoVotazione = statoVotazione;
	}

	public VotazioneEsito getEsitoVotazione() {
		return esitoVotazione;
	}

	public void setEsitoVotazione(VotazioneEsito esitoVotazione) {
		this.esitoVotazione = esitoVotazione;
	}

	public List<VotazioneVoto> getVoti() {
		return voti;
	}

	public void setVoti(List<VotazioneVoto> voti) {
		this.voti = voti;
	}

	public List<Partecipante> getVotanti() {
		return votanti;
	}

	public void setVotanti(List<Partecipante> votanti) {
		this.votanti = votanti;
	}
	
	

}