package shortener.url.service;

public class IllegalTimestampException extends Exception {
	public IllegalTimestampException() {
	}

	public IllegalTimestampException(String message) {
		super(message);
	}
}
