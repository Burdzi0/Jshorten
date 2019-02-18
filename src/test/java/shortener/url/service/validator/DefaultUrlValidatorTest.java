package shortener.url.service.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultUrlValidatorTest {

	private final String EXAMPLE_DOMAIN = "example.com";

	private final String GOOD_HTTP_ONLY_URL = "http://" + EXAMPLE_DOMAIN;
	private final String GOOD_HTTPS_ONLY_URL = "https://" + EXAMPLE_DOMAIN;

	private final String NO_PROTOCOL_WWW_ONLY = "www." + EXAMPLE_DOMAIN;

	private final String WRONG_URL = "asdfg hjkiu ytrew";

	private UrlValidator validator = new DefaultUrlValidator();

	@Test
	void validateGood() {
		assertEquals(UrlStatus.GOOD, validator.validate(GOOD_HTTP_ONLY_URL));
		assertEquals(UrlStatus.GOOD, validator.validate(GOOD_HTTPS_ONLY_URL));
	}

	@Test
	void validateNoProtocol() {
		assertEquals(UrlStatus.NO_PROTOCOL, validator.validate(EXAMPLE_DOMAIN));
		assertEquals(UrlStatus.NO_PROTOCOL, validator.validate(NO_PROTOCOL_WWW_ONLY));
	}

	@Test
	void validateWrong() {
		assertEquals(UrlStatus.WRONG, validator.validate(WRONG_URL));
	}
}