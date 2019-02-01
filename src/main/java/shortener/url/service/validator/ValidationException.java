package shortener.url.service.validator;

public class ValidationException extends Throwable {

	public ValidationException() {
	}

	public ValidationException(String message) {
		super(message);
	}
}
