package cdst_be_marche.model;

import java.io.Serializable;

import cdst_be_marche.adapder.alfresco.DocumentCostants;

public class BaseRestEntity implements Serializable {

	public enum ErrorsEnum {

		GENERIC("Si è verificato un errore inaspettato"),
		
		CANNOT_DELETE_DEFAULT_VALUE("Non è possibile eliminare un elemento predefinito"),

		VALIDATION("Verificare i dati inseriti"),

		UNAUTHORIZED("Utente non autorizzato"),

		ALREADY_PRESENT("Elemento già presente"),

		TYPE_ALREADY_PRESENT("Tipo Elemento già presente"),

		FILE_NOT_SIGNED("Il file non è firmato digitalmente"),

		NOT_FOUND("Elemento non trovato");

		private String description;

		ErrorsEnum(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	private static final long serialVersionUID = 10771907461284490L;

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

	public static BaseRestEntity getSuccessResponse() {
		BaseRestEntity response = new BaseRestEntity();
		response.setStatus(DocumentCostants.SUCCESS);
		response.setMessage("Operazione eseguita con successo!");
		return response;
	}

	public static BaseRestEntity getErrorResponse() {
		BaseRestEntity response = new BaseRestEntity();
		response.setStatus("error");
		response.setCode(ErrorsEnum.GENERIC.name());
		response.setMessage(ErrorsEnum.GENERIC.getDescription());
		return response;
	}

	public static BaseRestEntity getErrorResponse(ErrorsEnum error) {
		BaseRestEntity response = new BaseRestEntity();
		response.setStatus("error");
		response.setCode(error.name());
		response.setMessage(error.getDescription());
		return response;
	}

}
