package cdst_be_marche.DTO;

import io.swagger.annotations.ApiModel;

@ApiModel(value="JsonResponse")
public class RispostaJson {
	
	private String status;
	private String message;
	private String code;
	private Object data;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
