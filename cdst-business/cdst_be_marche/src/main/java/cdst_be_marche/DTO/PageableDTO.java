package cdst_be_marche.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Pageable")
public class PageableDTO {

	@ApiModelProperty(name = "recordForPage", value = "number of record for page")
	private Integer numRecordPag;

	@ApiModelProperty(name = "pageNumber", value = "page number")
	private Integer pagVisualizzata;

	@ApiModelProperty(name = "orderColumn", value = "order column")
	private String colonnaOrdinamento;

	@ApiModelProperty(name = "orderDirection", value = "order direction", allowableValues = "ASC, DESC")
	private String dirOrdinamento;

	public Integer getNumRecordPag() {
		return numRecordPag;
	}

	public void setNumRecordPag(Integer numRecordPag) {
		this.numRecordPag = numRecordPag;
	}

	public void setRecordForPage(Integer numRecordPag) {
		this.numRecordPag = numRecordPag;
	}

	public Integer getPagVisualizzata() {
		return pagVisualizzata;
	}

	public void setPagVisualizzata(Integer pagVisualizzata) {
		this.pagVisualizzata = pagVisualizzata;
	}

	public void setPageNumber(Integer pagVisualizzata) {
		this.pagVisualizzata = pagVisualizzata;
	}

	public String getColonnaOrdinamento() {
		return colonnaOrdinamento;
	}

	public void setColonnaOrdinamento(String colonnaOrdinamento) {
		this.colonnaOrdinamento = colonnaOrdinamento;
	}

	public void setOrderColumn(String colonnaOrdinamento) {
		this.colonnaOrdinamento = colonnaOrdinamento;
	}

	public String getDirOrdinamento() {
		return dirOrdinamento;
	}

	public void setDirOrdinamento(String dirOrdinamento) {
		this.dirOrdinamento = dirOrdinamento;
	}

	public void setOrderDirection(String dirOrdinamento) {
		this.dirOrdinamento = dirOrdinamento;
	}

}
