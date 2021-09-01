package conferenza.adapder.integrazioni.domus.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**

 * @author guideluc
    id integer NOT NULL DEFAULT nextval('domus_registry_adapter_testa_seq'::regclass),    
	id_conferenza integer,
	id_pratica integer,
	fk_codice_comune character varying(255),
	dt_ins  timestamp 
 */
@Entity
@Table(name = "domus_registry_adapter_testa")
public class DomusRegistryAdapterTesta implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "id_conferenza")
	private Integer idConferenza;
	
	@Column(name = "id_pratica")
	private Integer idPratica;
	
	@Column(name = "fk_codice_comune")
	private String fk_CodiceComune;
	
	@Column(name = "dt_ins")
	private Date dtIns;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdConferenza() {
		return idConferenza;
	}

	public void setIdConferenza(Integer idConferenza) {
		this.idConferenza = idConferenza;
	}

	public Integer getIdPratica() {
		return idPratica;
	}

	public void setIdPratica(Integer idPratica) {
		this.idPratica = idPratica;
	}

	public String getFk_CodiceComune() {
		return fk_CodiceComune;
	}

	public void setFk_CodiceComune(String fk_CodiceComune) {
		this.fk_CodiceComune = fk_CodiceComune;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}	
	
	
}
