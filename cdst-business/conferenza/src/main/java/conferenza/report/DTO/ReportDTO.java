package conferenza.report.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang.StringEscapeUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.report.model.Report;

public class ReportDTO {

	@JsonProperty( "ID")
	Integer id;
	@JsonProperty("CODICE")
	String codice;
	@JsonProperty("DESCRIZIONE")
	String descrizione;
	@JsonProperty("TIPOREPORT")
	String tiporeport;
	@JsonProperty("MODELLO")
	String modello;
	@JsonProperty("FK_PARAMETRO")
	Integer fk_parametro;
	@JsonProperty( "PARAMETRODEFAULT")
	String parametrodefault;
	
	@JsonProperty( "NOTE")
	String note;
	
	@JsonProperty( "VISIBILITA")
	String visibilita;
	
	@JsonProperty( "FILENAME")
	String filename;

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

	public Integer getFk_parametro() {
		return fk_parametro;
	}

	public void setFk_parametro(Integer fk_parametro) {
		this.fk_parametro = fk_parametro;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	public String getVisibilita() {
		return visibilita;
	}

	public void setVisibilita(String visibilita) {
		this.visibilita = visibilita;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public static ReportDTO fillReportDTO(Report model) {
		ReportDTO dto = new ReportDTO();
		dto.setId(model.getId());
		dto.setCodice(model.getCodice());
		dto.setDescrizione(model.getDescrizione());
		dto.setModello(model.getModello());
		dto.setParametrodefault(model.getParametrodefault());
		dto.setVisibilita(model.getVisibilita());
		dto.setNote( StringEscapeUtils.unescapeXml(model.getNote()));
		//dto.setFilename(filename);
		return dto;
	} 

	/**
	 * 
	 * @param lista
	 * @return
	 */
	public static List<ReportDTO> fillListReportDTO(List<Report> lista) {
		if(lista==null || lista.isEmpty())
			return null;
		List<ReportDTO> listDTO= new ArrayList<ReportDTO>();
		for(Report model : lista) {
			listDTO.add(fillReportDTO(model));			
		}
		return listDTO;
	}
	
}
