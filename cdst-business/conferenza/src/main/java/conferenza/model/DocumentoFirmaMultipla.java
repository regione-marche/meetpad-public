package conferenza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import conferenza.model.bean.DeletableEntity;

/**
 * The persistent class for the documento_firma_multipla database table.
 * 
 */
@Entity
public class DocumentoFirmaMultipla implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 852568858714128842L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "FK_DOCUMENTO")
	private Integer idDocumento;

	@Column(name = "FK_STATO")
	private String stato;
	
	@Column(name = "FK_RESPONSABILE_FIRMA")
	private Integer idResponsabileFirma;
	
	@Column(name = "FK_REGISTRO")
	private Integer idRegistro;
	
	@Column(name = "dt_firma_ins")
	private Date dataFirmaIns;
	
	@Column(name = "dt_firma_var")
	private Date dataFirmaVar;
	
	@Column(name = "fk_utente_creatore")
	private Integer idUtenteCreatore;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Integer getIdResponsabileFirma() {
		return idResponsabileFirma;
	}

	public void setIdResponsabileFirma(Integer idResponsabileFirma) {
		this.idResponsabileFirma = idResponsabileFirma;
	}

	public Integer getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Date getDataFirmaIns() {
		return dataFirmaIns;
	}

	public void setDataFirmaIns(Date dataFirmaIns) {
		this.dataFirmaIns = dataFirmaIns;
	}

	public Date getDataFirmaVar() {
		return dataFirmaVar;
	}

	public void setDataFirmaVar(Date dataFirmaVar) {
		this.dataFirmaVar = dataFirmaVar;
	}
	
	public Integer getIdUtenteCreatore() {
		return idUtenteCreatore;
	}

	public void setIdUtenteCreatore(Integer idUtenteCreatore) {
		this.idUtenteCreatore = idUtenteCreatore;
	}

}