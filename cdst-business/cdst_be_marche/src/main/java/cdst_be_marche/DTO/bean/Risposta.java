package cdst_be_marche.DTO.bean;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Response")
public class Risposta<D extends Object> extends RispostaNoData {

	private D data;

	public D getData() {
		return data;
	}

	public void setData(D data) {
		this.data = data;
	}
}
