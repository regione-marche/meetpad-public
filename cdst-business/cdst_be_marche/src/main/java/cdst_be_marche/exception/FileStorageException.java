package cdst_be_marche.exception;

public class FileStorageException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -172118838995687212L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}