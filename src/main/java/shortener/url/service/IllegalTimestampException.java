package shortener.url.service;

public class IllegalTimestampException extends Throwable {
	public IllegalTimestampException() {
	}

	public IllegalTimestampException(String message) {
		super(message);
	}
}
