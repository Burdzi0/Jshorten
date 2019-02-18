package shortener.url.service.factory;

import org.junit.jupiter.api.Test;
import shortener.url.model.UrlPojo;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultUrlCreatorTest {

	private UrlCreator<UrlPojo> creator = new DefaultUrlCreator();

	private final String url = "www.example.com";
	private final OffsetDateTime expirationTime = OffsetDateTime.MIN;
	private final String hash = "ABCDEF";

	@Test
	void create() {
		UrlPojo pojo = new UrlPojo(url, expirationTime);
		pojo.setHash(hash);

		UrlPojo created = creator.create(url, expirationTime);
		created.setHash(hash);
		assertEquals(pojo, created);
	}
}