package shortener.url.service.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlStatusTest {

	@Test
	void getStatus() {
		assertEquals(-1, UrlStatus.WRONG.getStatus());
		assertEquals(0, UrlStatus.GOOD.getStatus());
		assertEquals(1, UrlStatus.NO_PROTOCOL.getStatus());
	}
}