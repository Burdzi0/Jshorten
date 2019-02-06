package shortener.url.service.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultUrlValidatorTest {

	private UrlValidator validator = new DefaultUrlValidator();
	private final String HTTP_GOOD_EXAMPLE = "http://www.example.com/";
	private final String HTTP_GOOD_WITHOUT_WWW_EXAMPLE = "http://example.com/";
	private final String HTTPS_GOOD_EXAMPLE = "https://www.example.com/";
	private final String HTTPS_GOOD_WITHOUT_WWW_EXAMPLE = "https://example.com/";
	private final String NO_PROTOCOL_EXAMPLE = "www.example.com";
	private final String NO_PROTOCOL_WITHOUT_WWW_EXAMPLE = "example.com";
	private final String BAD_EXAMPLE = "definitely not a href";
	private final String EMPTY_EXAMPLE = "";

	@Test
	void validateGood() {
		assertEquals(UrlStatus.GOOD, validator.validate(HTTP_GOOD_EXAMPLE));
		assertEquals(UrlStatus.GOOD, validator.validate(HTTPS_GOOD_EXAMPLE));
		assertEquals(UrlStatus.GOOD, validator.validate(HTTP_GOOD_WITHOUT_WWW_EXAMPLE));
		assertEquals(UrlStatus.GOOD, validator.validate(HTTPS_GOOD_WITHOUT_WWW_EXAMPLE));
	}

	@Test
	void validateNoProtocol() {
		assertEquals(UrlStatus.NO_PROTOCOL, validator.validate(NO_PROTOCOL_EXAMPLE));
		assertEquals(UrlStatus.NO_PROTOCOL, validator.validate(NO_PROTOCOL_WITHOUT_WWW_EXAMPLE));
	}

	@Test
	void validateBad() {
		assertEquals(UrlStatus.WRONG, validator.validate(BAD_EXAMPLE));
		assertEquals(UrlStatus.WRONG, validator.validate(EMPTY_EXAMPLE));
	}

	@Test
	void validateNull() {
		assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
	}
}