package framework.webservice.exception;

public class JsonProcessingRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 7757809008271506561L;

	/**
	 *
	 * @param message
	 *            - exception message
	 * @param cause
	 *            - reason of exception
	 */
	public JsonProcessingRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
