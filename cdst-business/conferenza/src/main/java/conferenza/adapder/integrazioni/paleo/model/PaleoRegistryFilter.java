package conferenza.adapder.integrazioni.paleo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
CREATE SEQUENCE IF NOT EXISTS cdst.paleo_registry_filter_seq;


CREATE TABLE IF NOT EXISTS cdst.paleo_registry_filter(
	id integer NOT NULL DEFAULT nextval('registro_firma_adapter_seq'::regclass),
	condizione character varying(255), 
	fk_tipologia_conferenza integer, 
	tipofiltro character varying(255) default 'TIPOCONFERENZA', 	
	codiceriferimento  character varying(255), 
	descrizione character varying(255), 
	CONSTRAINT paleo_registry_filter_pkey PRIMARY KEY (id)
);

 * @author guideluc
 *
 */
@Entity
@Table(name = "paleo_registry_filter")
public class PaleoRegistryFilter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "condizione")
	private String  condizione;

	@Column(name = "fk_tipologia_conferenza")
	private Integer  fkTipologiaConferenza;
	
	@Column(name = "tipofiltro")
	private String  tipofiltro;
	
	@Column(name = "codiceriferimento")
	private String  codiceriferimento;
	
	@Column(name = "descrizione")
	private String  descrizione;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCondizione() {
		return condizione;
	}

	public void setCondizione(String condizione) {
		this.condizione = condizione;
	}

	public Integer getFkTipologiaConferenza() {
		return fkTipologiaConferenza;
	}

	public void setFkTipologiaConferenza(Integer fkTipologiaConferenza) {
		this.fkTipologiaConferenza = fkTipologiaConferenza;
	}

	public String getTipofiltro() {
		return tipofiltro;
	}

	public void setTipofiltro(String tipofiltro) {
		this.tipofiltro = tipofiltro;
	}

	public String getCodiceriferimento() {
		return codiceriferimento;
	}

	public void setCodiceriferimento(String codiceriferimento) {
		this.codiceriferimento = codiceriferimento;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
}
