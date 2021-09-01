package conferenza.adapder.integrazioni.indicepa.DTO.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
{
"result":{
"cod_err": 0,
"desc_err": "",
"num_items": 2
}
 * @author guideluc
 *
 */
@JsonInclude(Include.NON_NULL)
public class IndicePaResultDTO {

	@JsonProperty("cod_err")
	Integer codErr;
	
	@JsonProperty("desc_err")
	String descErr;
	
	@JsonProperty("num_items")
	Integer numItems;

	public Integer getCodErr() {
		return codErr;
	}

	public void setCodErr(Integer codErr) {
		this.codErr = codErr;
	}

	public String getDescErr() {
		return descErr;
	}

	public void setDescErr(String descErr) {
		this.descErr = descErr;
	}

	public Integer getNumItems() {
		return numItems;
	}

	public void setNumItems(Integer numItems) {
		this.numItems = numItems;
	}
	
	
}
