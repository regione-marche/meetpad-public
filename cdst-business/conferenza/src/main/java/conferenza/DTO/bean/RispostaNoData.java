package conferenza.DTO.bean;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value="ResponseNoData")
public class RispostaNoData {

	private String code = "200";
	private String msg;
	private List<Errore> errors = new ArrayList<Errore>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Errore> getErrors() {
		return errors;
	}

	public void setErrors(List<Errore> errors) {
		this.errors = errors;
	}

}
