package conferenza.adapder.integrazioni.indicepa.DTO;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import conferenza.adapder.integrazioni.indicepa.DTO.bean.IndicePaDataDTO;
import conferenza.adapder.integrazioni.indicepa.DTO.bean.IndicePaResultDTO;


@JsonInclude(Include.NON_NULL)
public class IndicePaSelectDTO {

	IndicePaResultDTO result;
	IndicePaDataDTO data;
	public IndicePaResultDTO getResult() {
		return result;
	}
	public void setResult(IndicePaResultDTO result) {
		this.result = result;
	}
	public IndicePaDataDTO getData() {
		return data;
	}
	public void setData(IndicePaDataDTO data) {
		this.data = data;
	}
	
	
}
