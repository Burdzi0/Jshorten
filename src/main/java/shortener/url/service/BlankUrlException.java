package shortener.url.service;

public class BlankUrlException extends Throwable {

	public BlankUrlException() {
	}

	public BlankUrlException(String message) {
		super(message);
	}
}
