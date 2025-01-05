package demo.checkout.exception;

public class UserCorrectableException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserCorrectableException(String message) {
		super(message);
	}

}
