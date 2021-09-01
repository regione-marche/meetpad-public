package conferenza.adapder.integrazioni.suap.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IntegSuapFrontieraEntiDTO {
	
	@JsonProperty("id")
	private Integer id;
	
	@JsonProperty("ente_nome")
	private String ente_nome;

	@JsonProperty("ente_pec")
	private String ente_pec;
	
	@JsonProperty("ente_codice_comune")
	private String ente_codice_comune;
	
	@JsonProperty("ente_comune")
	private String ente_comune;
	
	@JsonProperty("ente_codice_provincia")
	private String ente_codice_provincia;
	
	@JsonProperty("ente_provincia")
	private String ente_provincia;	

	@JsonProperty("ente_codice_regione")
	private String ente_codice_regione;	
	
	@JsonProperty("ente_regione")
	private String ente_regione;

	@JsonProperty("id_pratica")
	private Integer id_pratica;

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId_pratica() {
		return id_pratica;
	}

	public void setId_pratica(Integer id_pratica) {
		this.id_pratica = id_pratica;
	}

	public String getEnte_nome() {
		return ente_nome;
	}

	public void setEnte_nome(String ente_nome) {
		this.ente_nome = ente_nome;
	}

	public String getEnte_pec() {
		return ente_pec;
	}

	public void setEnte_pec(String ente_pec) {
		this.ente_pec = ente_pec;
	}

	public String getEnte_codice_comune() {
		return ente_codice_comune;
	}

	public void setEnte_codice_comune(String ente_codice_comune) {
		this.ente_codice_comune = ente_codice_comune;
	}

	public String getEnte_comune() {
		return ente_comune;
	}

	public void setEnte_comune(String ente_comune) {
		this.ente_comune = ente_comune;
	}

	public String getEnte_codice_provincia() {
		return ente_codice_provincia;
	}

	public void setEnte_codice_provincia(String ente_codice_provincia) {
		this.ente_codice_provincia = ente_codice_provincia;
	}

	public String getEnte_provincia() {
		return ente_provincia;
	}

	public void setEnte_provincia(String ente_provincia) {
		this.ente_provincia = ente_provincia;
	}

	public String getEnte_codice_regione() {
		return ente_codice_regione;
	}

	public void setEnte_codice_regione(String ente_codice_regione) {
		this.ente_codice_regione = ente_codice_regione;
	}

	public String getEnte_regione() {
		return ente_regione;
	}

	public void setEnte_regione(String ente_regione) {
		this.ente_regione = ente_regione;
	}	
		
}
