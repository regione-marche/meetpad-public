package conferenza.report.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class ReportAdapter {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	Integer id;
	@Column(name = "FK_REPORT")
	Integer fkReport;
	@Column(name = "TOKEN")
	String token;
	@Column(name = "REPORTPATH")
	String path;
	//serve per la gestione della cancellazione dei report generati nella cartella temporanea..
	@Column(name = "DELETED")
	String deleted;

	@Column(name = "DT_INS")
	Date dtIns;	
	
	/**
	 * Si permette la gestione di evenutali sistemi di reportistica esterna..
	 * esempio SpagoBI..andrà implementato un ulteriore Layer..
	 */
	@Column(name = "FK_EXTERN_REPORT")
	Integer fk_extern_report;
	@Column(name = "EXTERN_REPORT_TYPE")
	String extern_report_type;
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFkReport() {
		return fkReport;
	}
	public void setFkReport(Integer fkReport) {
		this.fkReport = fkReport;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public Date getDtIns() {
		return dtIns;
	}
	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	public Integer getFk_extern_report() {
		return fk_extern_report;
	}
	public void setFk_extern_report(Integer fk_extern_report) {
		this.fk_extern_report = fk_extern_report;
	}
	public String getExtern_report_type() {
		return extern_report_type;
	}
	public void setExtern_report_type(String extern_report_type) {
		this.extern_report_type = extern_report_type;
	}
	
	
	
}
