package shortener.url.service;

public class BlankUrlException extends Exception {

	public BlankUrlException() {
		super();
	}

	public BlankUrlException(String message) {
		super(message);
	}
}
