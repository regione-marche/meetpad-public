package conferenza.adapder.integrazioni.paleo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**

CREATE SEQUENCE paleo_registry_allegati_adapter_seq;

ALTER SEQUENCE paleo_registry_allegati_adapter_seq OWNER TO cdst;

CREATE table paleo_registry_allegati_adapter (

    id integer NOT NULL DEFAULT nextval('paleo_registry_allegati_adapter_seq'::regclass),
    ID_ALLEGATO integer,    
    FK_PALEO_DOC character varying(255),
	NOME_ALLEGATO  character varying(255),
	CONSTRAINT paleo_registry_allegati_adapter_pkey PRIMARY KEY (id)
);


 * 
 * 
 * @author guideluc
 *
 */
@Entity
@Table(name = "paleo_registry_allegati_adapter")
public class PaleoRegistryAllegatiAdapter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	//Intrinsecamente contiene il tipo conferenza
	//se è null non è stata creata la conferenza
	//..registroDocummenti>idConderenza>fk_tipollogica_conferenza
	@Column(name = "ID_ALLEGATO")
	private Integer idAllegato;
	
	//questo serve per il download
	@Column(name = "FK_PALEO_ADAPTER")
	private Integer fkPaleoAdapter;

	//questo serve per il download
	@Column(name = "NOME_ALLEGATO")
	private String nomeAllegato;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public Integer getFkPaleoAdapter() {
		return fkPaleoAdapter;
	}

	public void setFkPaleoAdapter(Integer fkPaleoAdapter) {
		this.fkPaleoAdapter = fkPaleoAdapter;
	}
	
	
	
}
