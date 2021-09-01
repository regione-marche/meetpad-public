package conferenza.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The persistent class for the voto database table.
 * 
 */
@Entity
public class VotazioneVoto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 293366114489739545L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_VOTAZIONE_VOTO")
	private Integer idVotazioneVoto;

	@Column(name = "VOTO")
	private Boolean voto;

	@Column(name = "MOTIVAZIONE")
	private String motivazione;

	@Column(name = "DATA_VOTO")
	private Date dataVoto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_VOTAZIONE")
	private Votazione votazione;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PARTECIPANTE")
	private Partecipante partecipante;

	public Integer getIdVotazioneVoto() {
		return idVotazioneVoto;
	}

	public void setIdVotazioneVoto(Integer idVotazioneVoto) {
		this.idVotazioneVoto = idVotazioneVoto;
	}

	public Boolean getVoto() {
		return voto;
	}

	public void setVoto(Boolean voto) {
		this.voto = voto;
	}

	public String getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(String motivazione) {
		this.motivazione = motivazione;
	}

	public Date getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(Date dataVoto) {
		this.dataVoto = dataVoto;
	}

	public Votazione getVotazione() {
		return votazione;
	}

	public void setVotazione(Votazione votazione) {
		this.votazione = votazione;
	}

	public Partecipante getPartecipante() {
		return partecipante;
	}

	public void setPartecipante(Partecipante partecipante) {
		this.partecipante = partecipante;
	}

}