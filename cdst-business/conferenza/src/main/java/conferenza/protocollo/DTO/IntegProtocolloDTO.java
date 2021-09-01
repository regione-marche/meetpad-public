package conferenza.protocollo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.protocollo.model.ObserverRegistryListener;

public class IntegProtocolloDTO
{
  @JsonProperty("id")
  private Integer id;
  @JsonProperty("id_paratica")
  private Integer idPratica;
  @JsonProperty("id_conferenza")
  private Integer id_conferenza;
  @JsonProperty("codice_protocollo")
  String codiceProtocollo;
  @JsonProperty("data_protocollo")
  String dataProtocollo;
  @JsonProperty("tipo_richiesta_protocollo")
  String tipoRichiestaProtocollo = "I";
  @JsonProperty("tipo_protocollo")
  String tipoProtocollo;
  @JsonProperty("cf_utente")
  String cf_utente;
  @JsonProperty("nome_utente")
  String nome_utente;
  @JsonProperty("cognome_utente")
  String cognome_utente;
  @JsonProperty("subject")
  String subject;
  @JsonProperty("descrizAllegato")
  String descrizAllegato;
  @JsonProperty("id_alfresco")
  String alfrescoID;
  @JsonProperty("note")
  String note;
  @JsonProperty("esito_protocollo")
  String esitoProtollazione;
  @JsonProperty("tipologia_documento")
  String tipologia_documento;
  @JsonProperty("codiceRubricaDestinatario")
  String codiceRubricaDestinatario;
  @JsonProperty("codiceRegistro")
  String codiceRegistro;
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Integer getIdPratica()
  {
    return this.idPratica;
  }
  
  public Integer getId_conferenza() {
	return id_conferenza;
}

public void setId_conferenza(Integer id_conferenza) {
	this.id_conferenza = id_conferenza;
}

public void setIdPratica(Integer idPratica)
  {
    this.idPratica = idPratica;
  }
  
  public String getCodiceProtocollo()
  {
    return this.codiceProtocollo;
  }
  
  public void setCodiceProtocollo(String codiceProtocollo)
  {
    this.codiceProtocollo = codiceProtocollo;
  }
  
  public String getDataProtocollo()
  {
    return this.dataProtocollo;
  }
  
  public void setDataProtocollo(String dataProtocollo)
  {
    this.dataProtocollo = dataProtocollo;
  }
  
  public String getTipoProtocollo()
  {
    return this.tipoProtocollo;
  }
  
  public void setTipoProtocollo(String tipoProtocollo)
  {
    this.tipoProtocollo = tipoProtocollo;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public String getAlfrescoID()
  {
    return this.alfrescoID;
  }
  
  public void setAlfrescoID(String alfrescoID)
  {
    this.alfrescoID = alfrescoID;
  }
  
  public String getNote()
  {
    return this.note;
  }
  
  public void setNote(String note)
  {
    this.note = note;
  }
  
  public String getEsitoProtollazione()
  {
    return this.esitoProtollazione;
  }
  
  public void setEsitoProtollazione(String esitoProtollazione)
  {
    this.esitoProtollazione = esitoProtollazione;
  }
  
  public String getTipoRichiestaProtocollo()
  {
    return this.tipoRichiestaProtocollo;
  }
  
  public void setTipoRichiestaProtocollo(String tipoRichiestaProtocollo)
  {
    this.tipoRichiestaProtocollo = tipoRichiestaProtocollo;
  }
  
  public String getDescrizAllegato()
  {
    return this.descrizAllegato;
  }
  
  public void setDescrizAllegato(String descrizAllegato)
  {
    this.descrizAllegato = descrizAllegato;
  }
  
  public String getCf_utente()
  {
    return this.cf_utente;
  }
  
  public String getNome_utente() 
  {
	return nome_utente;
  }

  public void setNome_utente(String nome_utente) 
  {
	this.nome_utente = nome_utente;
  }

  public String getCognome_utente() 
  {
	return cognome_utente;
  }

  public void setCognome_utente(String cognome_utente) 
  {
	this.cognome_utente = cognome_utente;
  }

  public String getTipologia_documento() 
  {
	return tipologia_documento;
  }

  public void setTipologia_documento(String tipologia_documento) 
  {
	this.tipologia_documento = tipologia_documento;
  }

  public void setCf_utente(String cf_utente)
  {
    this.cf_utente = cf_utente;
  }
  
  
public String getCodiceRubricaDestinatario() {
	return codiceRubricaDestinatario;
}

public void setCodiceRubricaDestinatario(String codiceRubricaDestinatario) {
	this.codiceRubricaDestinatario = codiceRubricaDestinatario;
}

public String getCodiceRegistro() {
	return codiceRegistro;
}

public void setCodiceRegistro(String codiceRegistro) {
	this.codiceRegistro = codiceRegistro;
}

/**
 * DLG: trovata uan regressione nel set dell'id Fascicolo!!?!?
 * @param obesever
 * @return
 */
public static IntegProtocolloDTO fillIntegProtocolloDTO(ObserverRegistryListener obesever)
  {
    IntegProtocolloDTO integDTO = new IntegProtocolloDTO();
    integDTO.setIdPratica(obesever.getId_pratica());
    if(obesever.getId_fascicolo()!=null &&!"".equals(obesever.getId_fascicolo()))
    	integDTO.setCodiceProtocollo(obesever.getId_fascicolo());
    
    return integDTO;
  }
  
  
}