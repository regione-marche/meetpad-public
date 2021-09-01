package cdst_be_marche.DTO.bean;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value="List")
public class Lista<D extends Object> {

	private List<D> list = new ArrayList<D>();
	private Long totalNumber;

	public List<D> getList() {
		return list;
	}

	public void setList(List<D> list) {
		this.list = list;
	}

	public Long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Long totalNumber) {
		this.totalNumber = totalNumber;
	}

}
