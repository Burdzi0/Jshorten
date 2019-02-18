package shortener.url.service;

public class BlankUrlException extends Throwable {

	public BlankUrlException() {
		super();
	}

	public BlankUrlException(String message) {
		super(message);
	}
}
