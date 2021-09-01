package conferenza.report.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Per la gestione dei Report: - dati dell'entità Logica..
 * id 			Integer	progressivo 			
 * codice 		String  codice mnemonico 
 * descrizione  String  dati contenuti 
 * tiporeport	String  {report (pdf),opendata (csv)}
 * dt_ins default Sysdate
 * modello	String  nome jasper del template che lo implementa			
 * fk_parametro integer chiave per report_parametri
 * parametrodefault String eventuale parametro di default..
 * 
 * 
 * al report và agganciato il livello adapter che a sua volta sarà legato al registro degli eventi..come token ..
 * 
 * 
 * 
 * @author guideluc
 *
 */
@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Integer id;
	@Column(name = "CODICE")
	String codice;
	@Column(name = "DESCRIZIONE")
	String descrizione;
	@Column(name = "TIPOREPORT")
	String tiporeport;
	@Column(name = "MODELLO")
	String modello;
	@Column(name = "note")
	String  note;
	@Column(name = "PARAMETRODEFAULT")
	String parametrodefault;
	
	@Column(name = "VISIBILITA")
	String visibilita;	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getTiporeport() {
		return tiporeport;
	}
	public void setTiporeport(String tiporeport) {
		this.tiporeport = tiporeport;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getParametrodefault() {
		return parametrodefault;
	}
	public void setParametrodefault(String parametrodefault) {
		this.parametrodefault = parametrodefault;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getVisibilita() {
		return visibilita;
	}
	public void setVisibilita(String visibilita) {
		this.visibilita = visibilita;
	}
	

	
}
