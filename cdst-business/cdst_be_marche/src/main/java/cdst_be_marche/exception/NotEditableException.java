package cdst_be_marche.exception;

public class NotEditableException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEditableException(String exception) {
		super(exception);
	}

}
