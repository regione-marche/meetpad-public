package conferenza.DTO.bean;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Error")
public class Errore {
	
	private List<String> fields = new ArrayList<String>();
	private String msg;
	private String code;
	private String step;
	
	public Errore() {
	}
	
	public Errore(String code, String message) {
		this.code = code;
		this.msg = message;
	}
	public List<String> getFields() {
		return fields;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	
	public static Errore getErrorSingleField(String field, String message) {
		Errore e = new Errore();
		e.setMsg(message);
		e.getFields().add(field);
		e.setCode("422");
		return e;
	}

}
