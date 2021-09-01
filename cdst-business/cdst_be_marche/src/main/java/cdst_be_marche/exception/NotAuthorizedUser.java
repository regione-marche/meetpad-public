package cdst_be_marche.exception;

public class NotAuthorizedUser extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7040020785681558201L;

	public NotAuthorizedUser(String exception) {
		super(exception);
	}

}
