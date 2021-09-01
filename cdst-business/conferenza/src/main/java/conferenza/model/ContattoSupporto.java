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

@Entity
public class ContattoSupporto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3053421287091290833L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CONTATTO_SUPPORTO")
	private Integer idContattoSupporto;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "TELEFONO")
	private String telefono;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;

	public Integer getIdContattoSupporto() {
		return idContattoSupporto;
	}

	public void setIdContattoSupporto(Integer idContattoSupporto) {
		this.idContattoSupporto = idContattoSupporto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Conferenza getConferenza() {
		return conferenza;
	}

	public void setConferenza(Conferenza conferenza) {
		this.conferenza = conferenza;
	}

}
