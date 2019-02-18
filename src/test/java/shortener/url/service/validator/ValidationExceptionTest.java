package shortener.url.service.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationExceptionTest {

	@Test
	void throwTest() {
		assertThrows(ValidationException.class, () -> {
			throw new ValidationException();
		});

		assertThrows(ValidationException.class, () -> {
			throw new ValidationException("Message");
		}, "Message");
	}

}