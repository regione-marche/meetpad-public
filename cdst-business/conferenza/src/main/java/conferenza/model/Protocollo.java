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

@Entity
public class Protocollo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8600778946782037042L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOCUMENTO")
	private Documento documento;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_PROTOCOLLO")
	private RegistroDocumento registro;
	
	@Column(name = "ID_PROTOCOLLO_ESTERNO")
	private Integer idProtocolloEsterno;
	
	@Column(name = "FK_STATI_PROTOCOLLO")
	private Integer statoProtocollo;
	
	@Column(name = "ERROR")
	private String error;
	
	@Column(name = "NUMERO_PROTOCOLLO")
	private String numeroProtocollo;
	
	@Column(name = "DATA_PROTOCOLLO")
	private Date dataProtocollo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public RegistroDocumento getRegistro() {
		return registro;
	}

	public void setRegistro(RegistroDocumento registro) {
		this.registro = registro;
	}

	public Integer getIdProtocolloEsterno() {
		return idProtocolloEsterno;
	}

	public void setIdProtocolloEsterno(Integer idProtocolloEsterno) {
		this.idProtocolloEsterno = idProtocolloEsterno;
	}

	public int getStatoProtocollo() {
		return statoProtocollo;
	}

	public void setStatoProtocollo(int statoProtocollo) {
		this.statoProtocollo = statoProtocollo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
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
	
	

}
