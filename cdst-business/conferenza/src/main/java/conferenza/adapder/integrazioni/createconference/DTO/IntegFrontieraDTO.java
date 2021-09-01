package conferenza.adapder.integrazioni.createconference.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class IntegFrontieraDTO {
	@JsonProperty("id_procedimento")
	private Integer id_procedimento;

	@JsonProperty("id_fascicolo")
	private String id_fascicolo;
	
	@JsonProperty("nome_procedimento")
	private String nome_procedimento;
	
	@JsonProperty("id_ente")
	private Integer id_ente;	
	@JsonProperty("id_responsabile")
	private Integer id_responsabile;	
	
	@JsonProperty("ente_id")
	private Integer ente_id;		
	@JsonProperty("ente_nome")
	private String ente_nome;
	@JsonProperty("ente_pec")
	private String ente_pec;
	

	@JsonProperty("responsabile_nome")
	private String responsabile_nome;
	@JsonProperty("responsabile_cognome")
	private String responsabile_cognome;
	@JsonProperty("responsabile_cf")
	private String responsabile_cf;
	@JsonProperty("responsabile_pec")
	private String responsabile_pec;	
	@JsonProperty("responsabile_mail")
	private String responsabile_mail;
	
	@JsonProperty("richiedente_nome")
	private String richiedente_nome;
	@JsonProperty("richiedente_cognome")
	private String richiedente_cognome;
	@JsonProperty("richiedente_cf")
	private String richiedente_cf;
	@JsonProperty("richiedente_pec")
	private String richiedente_pec;
	@JsonProperty("richiedente_mail")
	private String richiedente_mail;
	
	@JsonProperty("richiedente_comune_nome")
	private String richiedente_comune_nome;
	@JsonProperty("richiedente_comune_istat")
	private String richiedente_comune_istat;
	@JsonProperty("richiedente_provincia_nome")
	private String richiedente_provincia_nome;	
	@JsonProperty("richiedente_provincia_istat")
	private String richiedente_provincia_istat;	
				

	@JsonProperty("tipologia")
	private String tipologia;
	@JsonProperty("attivita")
	private String attivita;
	@JsonProperty("azione")
	private String azione;
	
	@JsonProperty("listaDocumenti")
	private List<IntegFrontieraDocumentDTO> listaDocumenti;
	
	@JsonProperty("enteProcedente")
	IntegFrontieraEntiDTO enteProcedente; 
	
	@JsonProperty("listaEntiPartecipanti")
	List<IntegFrontieraEntiDTO> entiPartecipanti;

	public String getId_fascicolo() {
		return id_fascicolo;
	}
	public void setId_fascicolo(String id_fascicolo) {
		this.id_fascicolo = id_fascicolo;
	}
	public Integer getId_procedimento() {
		return id_procedimento;
	}
	public void setId_procedimento(Integer id_procedimento) {
		this.id_procedimento = id_procedimento;
	}
	public String getNome_procedimento() {
		return nome_procedimento;
	}
	public void setNome_procedimento(String nome_procedimento) {
		this.nome_procedimento = nome_procedimento;
	}
	
	public Integer getId_ente() {
		return id_ente;
	}
	public void setId_ente(Integer id_ente) {
		this.id_ente = id_ente;
	}
	public Integer getId_responsabile() {
		return id_responsabile;
	}
	public void setId_responsabile(Integer id_responsabile) {
		this.id_responsabile = id_responsabile;
	}
	public Integer getEnte_id() {
		return ente_id;
	}
	public void setEnte_id(Integer ente_id) {
		this.ente_id = ente_id;
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
	public String getResponsabile_nome() {
		return responsabile_nome;
	}
	public void setResponsabile_nome(String responsabile_nome) {
		this.responsabile_nome = responsabile_nome;
	}
	public String getResponsabile_cognome() {
		return responsabile_cognome;
	}
	public void setResponsabile_cognome(String responsabile_cognome) {
		this.responsabile_cognome = responsabile_cognome;
	}
	public String getResponsabile_cf() {
		return responsabile_cf;
	}
	public void setResponsabile_cf(String responsabile_cf) {
		this.responsabile_cf = responsabile_cf;
	}
	public String getResponsabile_pec() {
		return responsabile_pec;
	}
	public void setResponsabile_pec(String responsabile_pec) {
		this.responsabile_pec = responsabile_pec;
	}
	public String getResponsabile_mail() {
		return responsabile_mail;
	}
	public void setResponsabile_mail(String responsabile_mail) {
		this.responsabile_mail = responsabile_mail;
	}
	public String getRichiedente_nome() {
		return richiedente_nome;
	}
	public void setRichiedente_nome(String richiedente_nome) {
		this.richiedente_nome = richiedente_nome;
	}
	public String getRichiedente_cognome() {
		return richiedente_cognome;
	}
	public void setRichiedente_cognome(String richiedente_cognome) {
		this.richiedente_cognome = richiedente_cognome;
	}
	public String getRichiedente_cf() {
		return richiedente_cf;
	}
	public void setRichiedente_cf(String richiedente_cf) {
		this.richiedente_cf = richiedente_cf;
	}
	public String getRichiedente_pec() {
		return richiedente_pec;
	}
	public void setRichiedente_pec(String richiedente_pec) {
		this.richiedente_pec = richiedente_pec;
	}
	public String getRichiedente_mail() {
		return richiedente_mail;
	}
	public void setRichiedente_mail(String richiedente_mail) {
		this.richiedente_mail = richiedente_mail;
	}

	public List<IntegFrontieraDocumentDTO> getListaDocumenti() {
		return listaDocumenti;
	}

	public void setListaDocumenti(List<IntegFrontieraDocumentDTO> listaDocumenti) {
		this.listaDocumenti = listaDocumenti;
	}	
	
	public List<IntegFrontieraEntiDTO> getEntiPartecipanti() {
		return entiPartecipanti;
	}

	public void setEntiPartecipanti(List<IntegFrontieraEntiDTO> entiPartecipanti) {
		this.entiPartecipanti = entiPartecipanti;
	}

	
	public IntegFrontieraEntiDTO getEnteProcedente() {
		return enteProcedente;
	}

	public void setEnteProcedente(IntegFrontieraEntiDTO enteProcedente) {
		this.enteProcedente = enteProcedente;
	}
	
	public String getRichiedente_comune_nome() {
		return richiedente_comune_nome;
	}

	public void setRichiedente_comune_nome(String richiedente_comune_nome) {
		this.richiedente_comune_nome = richiedente_comune_nome;
	}

	public String getRichiedente_comune_istat() {
		return richiedente_comune_istat;
	}

	public void setRichiedente_comune_istat(String richiedente_comune_istat) {
		this.richiedente_comune_istat = richiedente_comune_istat;
	}

	public String getRichiedente_provincia_nome() {
		return richiedente_provincia_nome;
	}

	public void setRichiedente_provincia_nome(String richiedente_provincia_nome) {
		this.richiedente_provincia_nome = richiedente_provincia_nome;
	}

	public String getRichiedente_provincia_istat() {
		return richiedente_provincia_istat;
	}

	public void setRichiedente_provincia_istat(String richiedente_provincia_istat) {
		this.richiedente_provincia_istat = richiedente_provincia_istat;
	}


	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getAttivita() {
		return attivita;
	}
	public void setAttivita(String attivita) {
		this.attivita = attivita;
	}
	public String getAzione() {
		return azione;
	}
	public void setAzione(String azione) {
		this.azione = azione;
	}

	
}
