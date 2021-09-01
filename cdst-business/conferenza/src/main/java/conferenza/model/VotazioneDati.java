package conferenza.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * The persistent class for the voto database table.
 * 
 */
@Entity
public class VotazioneDati implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 293366114489739545L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_VOTAZIONE_DATI")
	private Integer idVotazioneDati;

	@Column(name = "DATA")
	private Date data;

	@OneToOne(mappedBy = "dati")
	private Votazione votazione;

	public Integer getIdVotazioneDati() {
		return idVotazioneDati;
	}

	public void setIdVotazioneDati(Integer idVotazioneDati) {
		this.idVotazioneDati = idVotazioneDati;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Votazione getVotazione() {
		return votazione;
	}

	public void setVotazione(Votazione votazione) {
		this.votazione = votazione;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}