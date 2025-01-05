package demo.checkout.exception;

public class YesNoValueRuntimeException extends UserCorrectableException {

	private static final long serialVersionUID = 1L;

	public YesNoValueRuntimeException(String message) {
		super(message);
	}
}
