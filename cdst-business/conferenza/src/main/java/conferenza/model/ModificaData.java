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
public class ModificaData implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8600778946782037042L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MODIFICA_DATA")
	private Integer idModificaData;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_CONFERENZA")
	private Conferenza conferenza;
		
	@Column(name = "DATA")
	private Date data;
	
	@Column(name = "DATA_NEW")
	private Date dataNew;
	
	@Column(name = "DATA_MODIFICA")
	private Date dataModifica;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_TIPO_DATA")
	private TipoData tipoData;
	
	@Column(name = "ORDINE")
	private Integer ordine;
	
	
	public Integer getIdModificaData() {
		return idModificaData;
	}

	public void setIdModificaData(Integer idModificaData) {
		this.idModificaData = idModificaData;
	}

	public Conferenza getConferenza() {
		return conferenza;
	}

	public void setConferenza(Conferenza conferenza) {
		this.conferenza = conferenza;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoData getTipoData() {
		return tipoData;
	}

	public void setTipoData(TipoData tipoData) {
		this.tipoData = tipoData;
	}
	
	public Date getDataNew() {
		return dataNew;
	}

	public void setDataNew(Date dataNew) {
		this.dataNew = dataNew;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}
	
	
}
