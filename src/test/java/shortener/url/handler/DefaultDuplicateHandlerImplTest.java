package shortener.url.handler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shortener.url.model.UrlPojo;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDuplicateHandlerImplTest {

	private DuplicateHandler<UrlPojo> handler = new DefaultDuplicateHandlerImpl<>();
	private UrlPojo pojoWithoutNumber;
	private UrlPojo pojoWithNumber;

	@BeforeEach
	void init() {
		pojoWithoutNumber = new UrlPojo("www.url.com", OffsetDateTime.now());
		pojoWithoutNumber.setHash("ABCDEF");

		pojoWithNumber = new UrlPojo("www.url.com", OffsetDateTime.now());
		pojoWithNumber.setHash("ABCDEF1");
	}

	@Test
	void duplicate() {
		assertEquals("ABCDEF1", handler.duplicate(pojoWithoutNumber).getHash());
		assertEquals("ABCDEF2", handler.duplicate(pojoWithNumber).getHash());
	}
}