package cdst_be_marche.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * cc.fk_stato, pp.id_persona, pp.nome, pp.cognome, pp.codice_fiscale,
 * ee.id_ente, ee.codice_fiscale_ente, ee.descrizione_ente, pp.email,
 * acc.fk_ruolo_persona , rp.descrizione as descrizione_ruolo_persona
 * 
 * @author guideluc
 *
 */
@Entity
@Immutable
@Table(name = "mailer_indizione")
@NamedNativeQueries({
		@NamedNativeQuery(name = "Mailer.findAllMailerDTO", query = "select cc.fk_stato,pp.id_persona,pp.nome,pp.cognome,pp.codice_fiscale,"
				+ "      ee.id_ente,ee.codice_fiscale_ente,ee.descrizione_ente," + "      pp.email,"
				+ "      acc.fk_ruolo_persona ,rp.descrizione as descrizione_ruolo_persona "
				+ "      ,cc.id_conferenza,cc.oggetto_determinazione" + "      ,cc.localizzazione_indirizzo "
				+ "      from cdst.accreditamento acc" + "      inner join cdst.persona pp on  acc.fk_persona=pp.id_persona "
				+ "      inner join cdst.partecipante pc on pc.id_partecipante=pp.id_persona "
				+ "      inner join cdst.conferenza cc on cc.id_conferenza=pc.fk_conferenza "
				+ "      inner join cdst.stato ss on ss.codice=cc.fk_stato "
				+ "      inner join cdst.ente ee on ee.id_ente=pc.fk_ente "
				+ "      inner join cdst.ruolo_partecipante rp on rp.codice=acc.fk_ruolo_persona " + "      WHERE 1=1 "
				+ "      and ss.descrizione='INDIZIONE' "
				+ "      and acc.flag_accreditato='Y'", resultClass = Mailer.class),
		@NamedNativeQuery(name = "Mailer.findAllMailerByConference", query =
					"  select * from  " +
					"  (SELECT  " +
					"      pc.id_partecipante || '-' || pp.id_persona as id,  " +
					"      cc.fk_stato,  " +
					"      pp.id_persona,  " +
					"      pp.nome,  " +
					"      pp.cognome,  " +
					"      pp.codice_fiscale,  " +
					"      ee.id_ente,  " +
					"  	  pc.id_partecipante,  " +
					"      ee.codice_fiscale_ente,  " +
					"      ee.descrizione_ente, " +
					"      cc.fk_ente_procedente, " +
					"      ammpr.descrizione_ente AS descrizione_amministrazione_procedente, " +
					"      pp.email as email ,  " +
					"      acc.fk_ruolo_persona,  " +
					"      rp.descrizione      AS descrizione_ruolo_persona, " +
					"      cc.fk_tipologia_conferenza, " +
					"      tc.descrizione AS descrizione_tipologia_conferenza, " +
					"      pc.fk_ruolo_partecipante,  " +
					"      rpart.descrizione   AS descrizione_ruolo_partecipante, " +
					"  	  pc.competenza, " +
					"      cc.id_conferenza,  " +
					"      cc.oggetto_determinazione,  " +
					"      cc.localizzazione_indirizzo, " +
					"      cc.termine_richiesta_integrazioni_conferenza, " +
					"      cc.data_termine,"+
					"      cc.termine_espressione_pareri, " +
					"      cc.prima_sessione_simultanea, " +
					"      cc.riferimento_istanza, " +
					"      cc.indirizzo_sessione_simultanea, " +
					"      cc.cap_sessione_simultanea, " +
					"      cc.fk_comune_sessione_simultanea, " +
					"      cm.descrizione AS descrizione_comune_sessione_simultanea, " +
					"      cc.fk_provincia_sessione_simultanea, " +
					"      pv.descrizione AS descrizione_provincia_sessione_simultanea, " +
					" 	   cc.orario_conferenza " +
					"  FROM  " +
					"      cdst.accreditamento acc  " +
					"      INNER JOIN cdst.persona pp ON acc.fk_persona = pp.id_persona  " +
					"      INNER JOIN cdst.partecipante pc ON pc.id_partecipante = acc.fk_partecipante  " +
					"      INNER JOIN cdst.conferenza cc ON cc.id_conferenza = pc.fk_conferenza  " +
					"      INNER JOIN cdst.stato ss ON ss.codice = cc.fk_stato  " +
					"      INNER JOIN cdst.ente ee ON ee.id_ente = pc.fk_ente  " +
					"      INNER JOIN cdst.ruolo_partecipante rpart ON rpart.codice = pc.fk_ruolo_partecipante  " +
					"      INNER JOIN cdst.ruolo_persona rp ON rp.codice = acc.fk_ruolo_persona      " +
					"      INNER JOIN cdst.tipologia_conferenza tc ON tc.codice = cc.fk_tipologia_conferenza " +
					"      INNER JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente " +
					"      LEFT JOIN cdst.comune cm ON cm.codice = cc.fk_comune_sessione_simultanea " +
					"      LEFT JOIN cdst.provincia pv ON pv.codice = cc.fk_provincia_sessione_simultanea " +
					"  WHERE  " +
					"      1 = 1  " +
					"      and acc.data_fine is null and pc.data_fine is null and cc.data_fine is null " +
					"  UNION " +
					"  SELECT  " +
					"      pc.id_partecipante || '-0' as id,  " +
					"      cc.fk_stato,  " +
					"      null as id_persona,  " +
					"      null as nome,  " +
					"      null as cognome,  " +
					"      null as codice_fiscale,  " +
					"      ee.id_ente,  " +
					"  	  pc.id_partecipante,  " +
					"      ee.codice_fiscale_ente,  " +
					"      ee.descrizione_ente, " +
					"      cc.fk_ente_procedente, " +
					"      ammpr.descrizione_ente AS descrizione_amministrazione_procedente, " +
					"      pc.pec_ente_competente as email ,  " +
					"      pc.fk_ruolo_partecipante as fk_ruolo_persona,  " +
					"      rpart.descrizione      AS descrizione_ruolo_persona, " +
					"      cc.fk_tipologia_conferenza, " +
					"      tc.descrizione AS descrizione_tipologia_conferenza, " +
					"      pc.fk_ruolo_partecipante,  " +
					"      rpart.descrizione   AS descrizione_ruolo_partecipante, " +
					"  	  pc.competenza, " +
					"      cc.id_conferenza,  " +
					"      cc.oggetto_determinazione,  " +
					"      cc.localizzazione_indirizzo, " +
					"      cc.termine_richiesta_integrazioni_conferenza, " +
					"      cc.data_termine,"+
					"      cc.termine_espressione_pareri, " +
					"      cc.prima_sessione_simultanea, " +
					"      cc.riferimento_istanza, " +
					"      cc.indirizzo_sessione_simultanea, " +
					"      cc.cap_sessione_simultanea, " +
					"      cc.fk_comune_sessione_simultanea, " +
					"      cm.descrizione AS descrizione_comune_sessione_simultanea, " +
					"      cc.fk_provincia_sessione_simultanea, " +
					"      pv.descrizione AS descrizione_provincia_sessione_simultanea, " +
					" 	   cc.orario_conferenza " +
					"  FROM  " +
					"      cdst.partecipante pc " +
					"      INNER JOIN cdst.conferenza cc ON cc.id_conferenza = pc.fk_conferenza  " +
					"      INNER JOIN cdst.stato ss ON ss.codice = cc.fk_stato  " +
					"      INNER JOIN cdst.ente ee ON ee.id_ente = pc.fk_ente  " +
					"      INNER JOIN cdst.ruolo_partecipante rpart ON rpart.codice = pc.fk_ruolo_partecipante " +
					"      INNER JOIN cdst.tipologia_conferenza tc ON tc.codice = cc.fk_tipologia_conferenza " +
					"      INNER JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente " +
					"      LEFT JOIN cdst.comune cm ON cm.codice = cc.fk_comune_sessione_simultanea " +
					"      LEFT JOIN cdst.provincia pv ON pv.codice = cc.fk_provincia_sessione_simultanea " +
					"  WHERE  " +
					"      1 = 1  " +
					"  	AND ee.id_ente <> 0 and pc.data_fine is null and cc.data_fine is null " +
					"  UNION  "+
				    " SELECT  "+
				    "     pc.id_partecipante || '-0-' || ep.email as id, "+
				    "    cc.fk_stato, "+
				    "     null as id_persona, "+
				    "     null as nome, "+
				    "     null as cognome, "+
				    "     null as codice_fiscale, "+
				    "     ee.id_ente, "+
				    "       pc.id_partecipante, "+
				    "     ee.codice_fiscale_ente, "+
				    "     ee.descrizione_ente, "+
				    "     cc.fk_ente_procedente, "+
				    "     ammpr.descrizione_ente AS descrizione_amministrazione_procedente, "+
				    "     ep.email as email , "+
				    "     pc.fk_ruolo_partecipante as fk_ruolo_persona, "+
				    "     rpart.descrizione      AS descrizione_ruolo_persona, "+
				    "     cc.fk_tipologia_conferenza, "+
				    "     tc.descrizione AS descrizione_tipologia_conferenza, "+
				    "     pc.fk_ruolo_partecipante, "+
				    "     rpart.descrizione   AS descrizione_ruolo_partecipante, "+
				    "       pc.competenza, "+
				    "     cc.id_conferenza, "+
				    "     cc.oggetto_determinazione, "+
				    "     cc.localizzazione_indirizzo, "+
				    "     cc.termine_richiesta_integrazioni_conferenza, "+
				    "      cc.data_termine,"+
				    "     cc.termine_espressione_pareri, "+
				    "     cc.prima_sessione_simultanea, "+
				    "     cc.riferimento_istanza, "+
				    "     cc.indirizzo_sessione_simultanea, "+
				    "      cc.cap_sessione_simultanea, " +
					"      cc.fk_comune_sessione_simultanea, " +
					"      cm.descrizione AS descrizione_comune_sessione_simultanea, " +
					"      cc.fk_provincia_sessione_simultanea, " +
					"      pv.descrizione AS descrizione_provincia_sessione_simultanea, " +
				    " 	  cc.orario_conferenza " +
				    " FROM "+
					" 	 cdst.email_partecipante ep "+
					"	 INNER JOIN cdst.partecipante pc ON pc.id_partecipante = ep.fk_partecipante "+
				    "     INNER JOIN cdst.conferenza cc ON cc.id_conferenza = pc.fk_conferenza "+
				    "     INNER JOIN cdst.stato ss ON ss.codice = cc.fk_stato "+
				    "     INNER JOIN cdst.ente ee ON ee.id_ente = pc.fk_ente "+
				    "     INNER JOIN cdst.ruolo_partecipante rpart ON rpart.codice = pc.fk_ruolo_partecipante "+
				    "     INNER JOIN cdst.tipologia_conferenza tc ON tc.codice = cc.fk_tipologia_conferenza "+
				    "     INNER JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente "+
				    "      LEFT JOIN cdst.comune cm ON cm.codice = cc.fk_comune_sessione_simultanea " +
					"      LEFT JOIN cdst.provincia pv ON pv.codice = cc.fk_provincia_sessione_simultanea " +
				    " WHERE "+
				    "     1 = 1 "+
				    "     AND ee.id_ente <> 0 and pc.data_fine is null and cc.data_fine is null "+
					"  ) tot " +
					"  where tot.id_conferenza = ? and tot.email is not null and trim(tot.email) <> '' ", resultClass = Mailer.class),
		@NamedNativeQuery(name = "Mailer.findAllInvitiIndizioneByConference", query = "  select "
				+ "  cc.fk_stato,pp.id_persona,pp.nome,pp.cognome,pp.codice_fiscale,  "
				+ "  ee.id_ente,ee.codice_fiscale_ente,ee.descrizione_ente, "
				+ "  pp.email,cc.id_conferenza,cc.oggetto_determinazione " + "  ,cc.localizzazione_indirizzo "
				+ "  from cdst.persona pp  " + "  inner join cdst.partecipante pc on pc.id_partecipante=pp.id_persona "
				+ "  inner join cdst.conferenza cc on cc.id_conferenza=pc.fk_conferenza  "
				+ "  inner join cdst.stato ss on ss.codice=cc.fk_stato " + "  inner join cdst.ente ee on ee.id_ente=pc.fk_ente  "
				+ "  WHERE 1=1  " + "  and ss.descrizione='INDIZIONE'  "
				+ "  and cc.id_conferenza=? ", resultClass = Mailer.class) 
		,@NamedNativeQuery(
				name = "Mailer.findAllMailerInError", 
				query = " select"+
						" pc.id_partecipante || '-' || pp.id_persona as id,"+
						" cc.fk_stato,"+
						" pp.id_persona,"+
						" pc.id_partecipante,"+  
						" pp.nome,"+
						" pp.cognome,"+
						" pp.codice_fiscale,"+  
						" ee.id_ente,"+
						" ee.codice_fiscale_ente,"+
						" ee.descrizione_ente,"+ 
						" cc.fk_ente_procedente,"+ 
						" ammpr.descrizione_ente AS descrizione_amministrazione_procedente,"+ 
						" ret.email,"+
						" cc.id_conferenza,"+
						" cc.fk_tipologia_conferenza," +						
						" tc.descrizione AS descrizione_tipologia_conferenza,"+ 
						" cc.oggetto_determinazione   ,"+
						" cc.localizzazione_indirizzo ,"+
						" cc.termine_richiesta_integrazioni_conferenza,"+ 
						" cc.termine_espressione_pareri,"+ 
						" cc.prima_sessione_simultanea,"+ 
						" cc.riferimento_istanza,"+ 
						"      cc.cap_sessione_simultanea, " +
						"      cc.fk_comune_sessione_simultanea, " +
						"      cc.fk_provincia_sessione_simultanea, " +
						" cc.indirizzo_sessione_simultanea"+
						" , pc.competenza"+
						" , pc.fk_ruolo_partecipante"+
						" ,rpart.descrizione   AS descrizione_ruolo_partecipante"+
						" ,pc.fk_ruolo_partecipante as fk_ruolo_persona" +
						" ,rpart.descrizione      AS descrizione_ruolo_persona " +
						" from  cdst.conferenza cc"+    
						" INNER JOIN cdst.tipologia_conferenza tc ON tc.codice = cc.fk_tipologia_conferenza"+ 
						" inner join cdst.REGISTRO_EMAIL_TESTATA ret on ret.fk_conferenza=cc.id_conferenza"+
						" inner join cdst.REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio"+
						" left join  cdst.partecipante pc on   pc.fk_conferenza=cc.id_conferenza"+
						" left JOIN cdst.ruolo_partecipante rpart ON rpart.codice = pc.fk_ruolo_partecipante"+  
						" left join cdst.persona pp  on pp.codice_fiscale=pc.codice_fiscale_ente_competente"+
						" left join cdst.accreditamento acc ON acc.fk_persona = pp.id_persona"+
						" left JOIN cdst.ruolo_persona rp ON rp.codice = acc.fk_ruolo_persona"+  
						" left join cdst.stato ss on ss.codice=cc.fk_stato"+   
						" inner join cdst.ente ee on ee.id_ente=pc.fk_ente"+  
						" left JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente"+ 
						" WHERE 1=1"+
						" and det.stato_email ='ERRORETRASMISSIONE'"
				       + "  and ret.email is not null and not trim(ret.email)=''  "
						, resultClass = Mailer.class) 				
		
})
public class Mailer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3795916120535157067L;
	
	@JsonProperty("fk_stato")
	private String fk_stato;
	@Id
	@JsonProperty("id")
	private String id;
	@JsonProperty("id_persona")
	private Integer id_persona;
	@JsonProperty("nome")
	private String nome;
	@JsonProperty("cognome")
	private String cognome;
	@JsonProperty("codice_fiscale")
	private String codice_fiscale;
	@JsonProperty("id_ente")
	private Integer id_ente;
	@JsonProperty("id_partecipante")
	private Integer id_partecipante;	
	@JsonProperty("codice_fiscale_ente")
	private String codice_fiscale_ente;
	@JsonProperty("descrizione_ente")
	private String descrizione_ente;
	@JsonProperty("email")
	private String email;
	@JsonProperty("fk_ruolo_persona")
	private String fk_ruolo_persona;
	@JsonProperty("descrizione_ruolo_persona")
	private String descrizione_ruolo_persona;
	@JsonProperty("fk_ruolo_partecipante")
	private String fk_ruolo_partecipante;
	@JsonProperty("descrizione_ruolo_partecipante")
	private String descrizione_ruolo_partecipante;
	@JsonProperty("id_conferenza")
	private Integer id_conferenza;
	@JsonProperty("oggetto_determinazione")
	private String oggetto_determinazione;
	
	@JsonProperty("orario_conferenza")
	private Time orario_conferenza;

	@JsonProperty("localizzazione_indirizzo")
	private String localizzazione_indirizzo;

	@JsonProperty("fk_ente_procedente")
	private Integer fk_ente_procedente;

	@JsonProperty("descrizione_amministrazione_procedente")
	private String descrizione_amministrazione_procedente;

	@JsonProperty("fk_tipologia_conferenza")
	private String fk_tipologia_conferenza;

	@JsonProperty("descrizione_tipologia_conferenza")
	private String descrizione_tipologia_conferenza;

	@JsonProperty("competenza")
	private String competenza;

	@JsonProperty("termine_richiesta_integrazioni_conferenza")
	private Date termine_richiesta_integrazioni_conferenza;
	
	@JsonProperty("data_termine")
	private Date data_termine;

	@JsonProperty("termine_espressione_pareri")
	private Date termine_espressione_pareri;

	@JsonProperty("prima_sessione_simultanea")
	private Date prima_sessione_simultanea;

	@JsonProperty("riferimento_istanza")
	private String riferimento_istanza;

	@JsonProperty("indirizzo_sessione_simultanea")
	private String indirizzo_sessione_simultanea;

	@JsonProperty("cap_sessione_simultanea")
	private String cap_sessione_simultanea;
	
	@JsonProperty("fk_comune_sessione_simultanea")
	private String fk_comune_sessione_simultanea;
	
	@JsonProperty("descrizione_comune_sessione_simultanea")
	private String descrizione_comune_sessione_simultanea;
	
	@JsonProperty("fk_provincia_sessione_simultanea")
	private String fk_provincia_sessione_simultanea;
	
	@JsonProperty("descrizione_provincia_sessione_simultanea")
	private String descrizione_provincia_sessione_simultanea;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndirizzo_sessione_simultanea() {
		return indirizzo_sessione_simultanea;
	}

	public void setIndirizzo_sessione_simultanea(String indirizzo_sessione_simultanea) {
		this.indirizzo_sessione_simultanea = indirizzo_sessione_simultanea;
	}

	public String getCap_sessione_simultanea() {
		return cap_sessione_simultanea;
	}

	public void setCap_sessione_simultanea(String cap_sessione_simultanea) {
		this.cap_sessione_simultanea = cap_sessione_simultanea;
	}
	
	public String getDescrizione_comune_sessione_simultanea() {
		return descrizione_comune_sessione_simultanea;
	}

	public void setDescrizione_comune_sessione_simultanea(String descrizione_comune_sessione_simultanea) {
		this.descrizione_comune_sessione_simultanea = descrizione_comune_sessione_simultanea;
	}

	public String getDescrizione_provincia_sessione_simultanea() {
		return descrizione_provincia_sessione_simultanea;
	}

	public void setDescrizione_provincia_sessione_simultanea(String descrizione_provincia_sessione_simultanea) {
		this.descrizione_provincia_sessione_simultanea = descrizione_provincia_sessione_simultanea;
	}

	public String getFk_comune_sessione_simultanea() {
		return fk_comune_sessione_simultanea;
	}

	public void setFk_comune_sessione_simultanea(String fk_comune_sessione_simultanea) {
		this.fk_comune_sessione_simultanea = fk_comune_sessione_simultanea;
	}

	public String getFk_provincia_sessione_simultanea() {
		return fk_provincia_sessione_simultanea;
	}

	public void setFk_provincia_sessione_simultanea(String fk_provincia_sessione_simultanea) {
		this.fk_provincia_sessione_simultanea = fk_provincia_sessione_simultanea;
	}

	public String getDescrizione_amministrazione_procedente() {
		return descrizione_amministrazione_procedente;
	}

	public void setDescrizione_amministrazione_procedente(String descrizione_amministrazione_procedente) {
		this.descrizione_amministrazione_procedente = descrizione_amministrazione_procedente;
	}

	public String getFk_tipologia_conferenza() {
		return fk_tipologia_conferenza;
	}

	public void setFk_tipologia_conferenza(String fk_tipologia_conferenza) {
		this.fk_tipologia_conferenza = fk_tipologia_conferenza;
	}

	public String getDescrizione_tipologia_conferenza() {
		return descrizione_tipologia_conferenza;
	}

	public void setDescrizione_tipologia_conferenza(String descrizione_tipologia_conferenza) {
		this.descrizione_tipologia_conferenza = descrizione_tipologia_conferenza;
	}

	public String getCompetenza() {
		return competenza;
	}

	public void setCompetenza(String competenza) {
		this.competenza = competenza;
	}

	public Date getTermine_richiesta_integrazioni_conferenza() {
		return termine_richiesta_integrazioni_conferenza;
	}

	public void setTermine_richiesta_integrazioni_conferenza(Date termine_richiesta_integrazioni_conferenza) {
		this.termine_richiesta_integrazioni_conferenza = termine_richiesta_integrazioni_conferenza;
	}

	public Date getData_termine() {
		return data_termine;
	}

	public void setData_termine(Date data_termine) {
		this.data_termine = data_termine;
	}

	public Date getTermine_espressione_pareri() {
		return termine_espressione_pareri;
	}

	public void setTermine_espressione_pareri(Date termine_espressione_pareri) {
		this.termine_espressione_pareri = termine_espressione_pareri;
	}

	public Date getPrima_sessione_simultanea() {
		return prima_sessione_simultanea;
	}

	public void setPrima_sessione_simultanea(Date prima_sessione_simultanea) {
		this.prima_sessione_simultanea = prima_sessione_simultanea;
	}

	public String getRiferimento_istanza() {
		return riferimento_istanza;
	}

	public void setRiferimento_istanza(String riferimento_istanza) {
		this.riferimento_istanza = riferimento_istanza;
	}

	public String getFk_stato() {
		return fk_stato;
	}

	public void setFk_stato(String fk_stato) {
		this.fk_stato = fk_stato;
	}

	public Integer getId_persona() {
		return id_persona;
	}

	public void setId_persona(Integer id_persona) {
		this.id_persona = id_persona;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public Integer getId_ente() {
		return id_ente;
	}

	public void setId_ente(Integer id_ente) {
		this.id_ente = id_ente;
	}

	public Integer getId_partecipante() {
		return id_partecipante;
	}

	public void setId_partecipante(Integer id_partecipante) {
		this.id_partecipante = id_partecipante;
	}

	public String getCodice_fiscale_ente() {
		return codice_fiscale_ente;
	}

	public void setCodice_fiscale_ente(String codice_fiscale_ente) {
		this.codice_fiscale_ente = codice_fiscale_ente;
	}

	public String getDescrizione_ente() {
		return descrizione_ente;
	}

	public void setDescrizione_ente(String descrizione_ente) {
		this.descrizione_ente = descrizione_ente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFk_ruolo_persona() {
		return fk_ruolo_persona;
	}

	public void setFk_ruolo_persona(String fk_ruolo_persona) {
		this.fk_ruolo_persona = fk_ruolo_persona;
	}

	public String getDescrizione_ruolo_persona() {
		return descrizione_ruolo_persona;
	}

	public void setDescrizione_ruolo_persona(String descrizione_ruolo_persona) {
		this.descrizione_ruolo_persona = descrizione_ruolo_persona;
	}

	public String getFk_ruolo_partecipante() {
		return fk_ruolo_partecipante;
	}

	public void setFk_ruolo_partecipante(String fk_ruolo_partecipante) {
		this.fk_ruolo_partecipante = fk_ruolo_partecipante;
	}

	public String getDescrizione_ruolo_partecipante() {
		return descrizione_ruolo_partecipante;
	}

	public void setDescrizione_ruolo_partecipante(String descrizione_ruolo_partecipante) {
		this.descrizione_ruolo_partecipante = descrizione_ruolo_partecipante;
	}

	public Integer getId_conferenza() {
		return id_conferenza;
	}

	public void setId_conferenza(Integer id_conferenza) {
		this.id_conferenza = id_conferenza;
	}

	public String getOggetto_determinazione() {
		return oggetto_determinazione;
	}

	public void setOggetto_determinazione(String oggetto_determinazione) {
		this.oggetto_determinazione = oggetto_determinazione;
	}

	public String getLocalizzazione_indirizzo() {
		return localizzazione_indirizzo;
	}

	public void setLocalizzazione_indirizzo(String localizzazione_indirizzo) {
		this.localizzazione_indirizzo = localizzazione_indirizzo;
	}

	public Integer getFk_ente_procedente() {
		return fk_ente_procedente;
	}

	public void setFk_ente_procedente(Integer fk_ente_procedente) {
		this.fk_ente_procedente = fk_ente_procedente;
	}

	public Time getOrario_conferenza() {
		return orario_conferenza;
	}

	public void setOrario_conferenza(Time orario_conferenza) {
		this.orario_conferenza = orario_conferenza;
	}

	/**
	 * Ritorna l'oggetto Mailer come Stringa
	 * 
	 * @return
	 * @throws JsonProcessingException
	 */
	public String getJsonString() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

}
