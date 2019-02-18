package shortener.url.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BlankUrlExceptionTest {

	@Test
	void throwTest() {
		assertThrows(BlankUrlException.class, () -> {
			throw new BlankUrlException();
		});

		assertThrows(BlankUrlException.class, () -> {
			throw new BlankUrlException("Message");
		}, "Message");
	}

}