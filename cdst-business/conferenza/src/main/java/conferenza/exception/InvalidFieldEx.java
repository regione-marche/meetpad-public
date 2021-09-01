package conferenza.exception;

import java.util.ArrayList;
import java.util.List;

import conferenza.DTO.bean.Errore;

public class InvalidFieldEx extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2554929173397049625L;
	
	private List<Errore> errors = new ArrayList<Errore>();
	
	public InvalidFieldEx(String exception) {
		super(exception);
	}
	
	public InvalidFieldEx(List<Errore> errors) {
		this.setErrors(errors);
	}

	public List<Errore> getErrors() {
		return errors;
	}

	public void setErrors(List<Errore> errors) {
		this.errors = errors;
	}

}
