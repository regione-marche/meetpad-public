package conferenza.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The persistent class for the permesso database table.
 * 
 */
@Entity
public class Permesso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5789427642873777019L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERMESSO")
	private Integer idPermesso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERMESSO_RUOLO")
	private PermessoRuolo permessoRuolo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PERMESSO_AZIONE")
	private PermessoAzione permessoAzione;

	@Column(name = "PERMISSION_STRATEGY")
	private String permissionStrategy;

	public Integer getIdPermesso() {
		return idPermesso;
	}

	public void setIdPermesso(Integer idPermesso) {
		this.idPermesso = idPermesso;
	}

	public PermessoRuolo getPermessoRuolo() {
		return permessoRuolo;
	}

	public void setPermessoRuolo(PermessoRuolo permessoRuolo) {
		this.permessoRuolo = permessoRuolo;
	}

	public PermessoAzione getPermessoAzione() {
		return permessoAzione;
	}

	public void setPermessoAzione(PermessoAzione permessoAzione) {
		this.permessoAzione = permessoAzione;
	}

	public String getPermissionStrategy() {
		return permissionStrategy;
	}

	public void setPermissionStrategy(String permissionStrategy) {
		this.permissionStrategy = permissionStrategy;
	}

}