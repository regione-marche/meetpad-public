package cdst_be_marche.model;

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
 * Rappresenta una posizione fisica in cui si trova il file del documento
 * (path nel file system oppure identificativo nel documentale)
 * @author arosina
 *
 */
@Entity
public class RegistroDocumento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8199731340244890379L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FONTE")
	private RegistroDocumentoFonte fonte;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPO")
	private RegistroDocumentoTipo tipo;

	@Column(name = "DATA")
	private Date data;

	@Column(name = "ENTE")
	private String ente;

	/**
	 * path nel file system o identificativo nel documentale
	 */
	@Column(name = "RIF_ESTERNO")
	private String riferimentoEsterno;

	@Column(name = "TIPO_ESTERNO")
	private String tipoEsterno;

	@Column(name = "DATA_ESTERNO")
	private Date dataEsterno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DOCUMENTO")
	private Documento documento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegistroDocumentoFonte getFonte() {
		return fonte;
	}

	public void setFonte(RegistroDocumentoFonte fonte) {
		this.fonte = fonte;
	}

	public RegistroDocumentoTipo getTipo() {
		return tipo;
	}

	public void setTipo(RegistroDocumentoTipo tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getRiferimentoEsterno() {
		return riferimentoEsterno;
	}

	public void setRiferimentoEsterno(String riferimentoEsterno) {
		this.riferimentoEsterno = riferimentoEsterno;
	}

	public String getTipoEsterno() {
		return tipoEsterno;
	}

	public void setTipoEsterno(String tipoEsterno) {
		this.tipoEsterno = tipoEsterno;
	}

	public Date getDataEsterno() {
		return dataEsterno;
	}

	public void setDataEsterno(Date dataEsterno) {
		this.dataEsterno = dataEsterno;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

}
