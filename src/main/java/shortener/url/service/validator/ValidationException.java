package shortener.url.service.validator;

public class ValidationException extends Exception {

	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}
}
