package cdst_be_marche.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Il Profilo lega un TipoProfilo ad un Ente. Gli Utenti possono avere 1 o più
 * profili associati.
 * 
 * @author arosina
 *
 */
@Entity
public class Profilo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3395078252899436412L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFILO")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_PROFILO", nullable=false)
	private TipoProfilo tipoProfilo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_ENTE", nullable=false)
	private Ente amministrazioneProcedente;

	@OneToMany(mappedBy = "profilo")
	private List<Utente> utenti;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoProfilo getTipoProfilo() {
		return tipoProfilo;
	}

	public void setTipoProfilo(TipoProfilo tipoProfilo) {
		this.tipoProfilo = tipoProfilo;
	}

	public Ente getAmministrazioneProcedente() {
		return amministrazioneProcedente;
	}

	public void setAmministrazioneProcedente(Ente amministrazioneProcedente) {
		this.amministrazioneProcedente = amministrazioneProcedente;
	}

	public List<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(List<Utente> utenti) {
		this.utenti = utenti;
	}

	public Utente addUtente(Utente utente) {
		getUtenti().add(utente);
		utente.setProfilo(this);

		return utente;
	}

	public Utente removeUtente(Utente utente) {
		getUtenti().remove(utente);
		utente.setProfilo(null);

		return utente;
	}

}
