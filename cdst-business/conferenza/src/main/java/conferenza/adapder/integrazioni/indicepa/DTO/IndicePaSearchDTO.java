package conferenza.adapder.integrazioni.indicepa.DTO;

import conferenza.adapder.integrazioni.indicepa.DTO.bean.IndicePaResultDTO;

import java.util.List;

import conferenza.adapder.integrazioni.indicepa.DTO.bean.IndicePaDataDTO;
/**
 * 
 * @author guideluc
 *
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class IndicePaSearchDTO {

	IndicePaResultDTO result;
	List<IndicePaDataDTO> data;
	public IndicePaResultDTO getResult() {
		return result;
	}
	public void setResult(IndicePaResultDTO result) {
		this.result = result;
	}
	public List<IndicePaDataDTO> getData() {
		return data;
	}
	public void setData(List<IndicePaDataDTO> data) {
		this.data = data;
	}

	
}
