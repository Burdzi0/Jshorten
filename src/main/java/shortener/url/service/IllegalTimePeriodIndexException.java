package shortener.url.service;

public class IllegalTimePeriodIndexException extends RuntimeException {
	public IllegalTimePeriodIndexException() {
		super();
	}

	public IllegalTimePeriodIndexException(String message) {
		super(message);
	}

	public IllegalTimePeriodIndexException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalTimePeriodIndexException(Throwable cause) {
		super(cause);
	}

	protected IllegalTimePeriodIndexException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
