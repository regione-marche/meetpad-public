package conferenza.exception;


public class DocumentoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DocumentoException(String exceptionMessage) {
	    super(exceptionMessage);
	}
	
	public DocumentoException(String exceptionMessage, Exception e) {
	    super(exceptionMessage, e);
	}

}
