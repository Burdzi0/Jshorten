package shortener.url.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shortener.url.handler.DefaultDuplicateHandlerImpl;
import shortener.url.model.UrlPojo;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryUrlRepositoryTest {

	private InMemoryUrlRepository<UrlPojo> repository;
	private static UrlPojo url;
	private static final String hash = "ABCDEF";

	@BeforeAll
	static void createUrlPojo() {
		url = new UrlPojo("www.example.com", OffsetDateTime.MIN);
		url.setHash(hash);
	}

	@BeforeEach
	void initRepository() {
		repository = new InMemoryUrlRepository<>(new DefaultDuplicateHandlerImpl<>());
		repository.addUrl(url);
	}

	@Test
	void addUrl() {
		assertEquals(url, repository.find(hash).get());
	}

	@Test
	void addDuplicate() {
		repository.addUrl(url);
		assertEquals(2, repository.findAll().size());
		assertTrue(repository.find(hash + "1").isPresent());
	}

	@Test
	void deleteExpired() {
		var deleted = repository.deleteExpired();

		assertEquals(1, deleted);
		assertTrue(repository.find(hash).isEmpty());
		assertEquals(0, repository.findAll().size());
	}

	@Test
	void find() {
		assertEquals(url, repository.find(hash).get());
	}

	@Test
	void findAll() {
		assertEquals(1, repository.findAll().size());
	}
}