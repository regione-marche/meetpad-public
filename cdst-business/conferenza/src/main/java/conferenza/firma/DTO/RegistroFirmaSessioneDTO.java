package conferenza.firma.DTO;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import conferenza.DTO.IdentifiableDTO;
import conferenza.firma.model.RegistroFirmaAdapter;
import conferenza.firma.model.RegistroFirmaSessione;

public class RegistroFirmaSessioneDTO  extends IdentifiableDTO {

	
	
	@JsonProperty("fk_documento")
    Integer fkDocumento ;
	
	@JsonProperty("token")
    String token ;	
	
	@JsonProperty("dt_sessione_begin")
    Date dtSessioneBegin ;	
	
	@JsonProperty("dt_sessione_end")
    Date dtSessioneEnd ;

	public Integer getFkDocumento() {
		return fkDocumento;
	}

	public void setFkDocumento(Integer fkDocumento) {
		this.fkDocumento = fkDocumento;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDtSessioneBegin() {
		return dtSessioneBegin;
	}

	public void setDtSessioneBegin(Date dtSessioneBegin) {
		this.dtSessioneBegin = dtSessioneBegin;
	}

	public Date getDtSessioneEnd() {
		return dtSessioneEnd;
	}

	public void setDtSessioneEnd(Date dtSessioneEnd) {
		this.dtSessioneEnd = dtSessioneEnd;
	}	
	

	public static RegistroFirmaSessione getRegistroFirmaSessione(RegistroFirmaSessioneDTO dto) {
		RegistroFirmaSessione sessione= new RegistroFirmaSessione();
		sessione.setId(dto.getId());
		sessione.setFkDocumento(dto.getFkDocumento());
		sessione.setToken(dto.getToken());
		sessione.setDtSessioneBegin(dto.getDtSessioneBegin());
		sessione.setDtSessioneEnd(dto.getDtSessioneEnd());
		return sessione;
	}
	
	
}
