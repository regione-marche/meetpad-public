package cdst_be_marche.model;

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
public class RegistroEmailAllegato implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8331627585317116023L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_REGISTRO_EMAIL_ALLEGATO")
	private Integer idRegistroEmailAllegato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_REGISTRO_EMAIL_TESTATA")
	private RegistroEmailTestata registroEmailTestata;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DOCUMENTO")
	private Documento documento;

	public Integer getIdRegistroEmailAllegato() {
		return idRegistroEmailAllegato;
	}

	public void setIdRegistroEmailAllegato(Integer idRegistroEmailAllegato) {
		this.idRegistroEmailAllegato = idRegistroEmailAllegato;
	}

	public RegistroEmailTestata getRegistroEmailTestata() {
		return registroEmailTestata;
	}

	public void setRegistroEmailTestata(RegistroEmailTestata registroEmailTestata) {
		this.registroEmailTestata = registroEmailTestata;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}
