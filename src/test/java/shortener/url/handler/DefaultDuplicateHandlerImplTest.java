package shortener.url.handler;

import shortener.url.model.UrlPojo;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultDuplicateHandlerImplTest {

	private final String exampleUrl = "www.example.com";
	private final OffsetDateTime exampleTime = OffsetDateTime.now();
	private final DuplicateHandler<UrlPojo> handler = new DefaultDuplicateHandlerImpl<>();

	void firstDuplicate() {
		var pojo = new UrlPojo(exampleUrl, exampleTime);

		assertEquals(pojo.getHash() + "1", handler.duplicate(pojo).getHash());
	}
}