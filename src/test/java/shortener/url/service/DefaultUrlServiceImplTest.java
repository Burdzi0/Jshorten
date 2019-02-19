package shortener.url.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shortener.url.algorithm.Sha256ShortingAlgorithm;
import shortener.url.handler.DefaultDuplicateHandlerImpl;
import shortener.url.model.UrlPojo;
import shortener.url.repository.InMemoryUrlRepository;
import shortener.url.service.factory.DefaultUrlCreator;
import shortener.url.service.factory.DefaultUrlFactory;
import shortener.url.service.factory.UrlFactory;
import shortener.url.service.validator.DefaultUrlValidator;
import shortener.url.service.validator.ValidationException;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DefaultUrlServiceImplTest {

	public static final String WWW_EXAMPLE_COM = "http://www.example.com";
	public static final String WWW_EXAMPLE_COM_WITHOUT_PROTOCOL = "www.example.com";
	public static final OffsetDateTime EXPIRATION_TIME = OffsetDateTime.MAX;

	private UrlFactory<UrlPojo> factory = new DefaultUrlFactory<>(new Sha256ShortingAlgorithm<>(), new DefaultUrlCreator());
	private InMemoryUrlRepository<UrlPojo> repository;
	private DefaultUrlServiceImpl<UrlPojo> service;

	@BeforeEach
	void initService() {
		repository = new InMemoryUrlRepository<>(new DefaultDuplicateHandlerImpl<>());
		service = new DefaultUrlServiceImpl<>(repository, factory, new DefaultUrlValidator());
	}

	@Test
	void createUrl() throws BlankUrlException, IllegalTimestampException, ValidationException {
		UrlPojo created = saveAndGetUrlPojo(EXPIRATION_TIME);

		var manufactured = factory.createUrl(WWW_EXAMPLE_COM, EXPIRATION_TIME);

		assertEquals(manufactured, created);
	}

	@Test
	void shouldAddDefaultProtocolToUrl() throws BlankUrlException, IllegalTimestampException, ValidationException {
		UrlPojo created = saveAndGetUrlPojo(EXPIRATION_TIME);

		var manufactured = factory.createUrl(WWW_EXAMPLE_COM, EXPIRATION_TIME);

		assertEquals(manufactured.getUrl(), created.getUrl());
	}

	@Test
	void shouldThrowBlankUrlExceptionForInvalidUrlString() {
		assertThrows(BlankUrlException.class, () -> {
			service.createUrl(null, EXPIRATION_TIME);
		});

		assertThrows(BlankUrlException.class, () -> {
			service.createUrl("", EXPIRATION_TIME);
		});

		assertThrows(BlankUrlException.class, () -> {
			service.createUrl("      ", EXPIRATION_TIME);
		});
	}

	@Test
	void shouldThrowIllegalTimestampExceptionForOutdatedOrNullExpirationTime() {
		assertThrows(IllegalTimestampException.class, () -> {
			service.createUrl(WWW_EXAMPLE_COM, OffsetDateTime.MIN);
		});

		assertThrows(IllegalTimestampException.class, () -> {
			service.createUrl(WWW_EXAMPLE_COM, null);
		});

		assertThrows(IllegalTimestampException.class, () -> {
			service.createUrl(WWW_EXAMPLE_COM, OffsetDateTime.now().minusMinutes(5));
		});
	}

	@Test
	void shouldThrowValidationExceptionForWrongUrl() {
		assertThrows(ValidationException.class, () -> {
			service.createUrl("sadf asd fasd", OffsetDateTime.MIN);
		});
	}

	@Test
	void save() throws BlankUrlException, IllegalTimestampException, ValidationException {
		UrlPojo url = saveAndGetUrlPojo(EXPIRATION_TIME);

		assertTrue(service.find(url.getHash()).isPresent());
		assertEquals(url, service.find(url.getHash()).get());
		assertEquals(1, service.findAll().size());
		assertTrue(service.findAll().contains(url));
	}

	private UrlPojo saveAndGetUrlPojo(OffsetDateTime expirationTime) throws BlankUrlException, IllegalTimestampException, ValidationException {
		var url = service.createUrl(WWW_EXAMPLE_COM, expirationTime);
		service.save(url);
		return url;
	}

	@Test
	void deleteExpired() throws BlankUrlException, IllegalTimestampException, ValidationException, InterruptedException {
		UrlPojo url = saveAndGetUrlPojo(OffsetDateTime.now().plusSeconds(5));

		Thread.sleep(10000);

		var deleted = service.deleteExpired();

		assertEquals(1, deleted);
		assertTrue(service.find(url.getHash()).isEmpty());
	}

	@Test
	void find() throws BlankUrlException, IllegalTimestampException, ValidationException {
		UrlPojo url = saveAndGetUrlPojo(EXPIRATION_TIME);

		assertTrue(service.find(url.getHash()).isPresent());
		assertEquals(url, service.find(url.getHash()).get());

		assertFalse(service.find("000000").isPresent());
	}

	@Test
	void findAll() throws BlankUrlException, IllegalTimestampException, ValidationException {
		UrlPojo url = saveAndGetUrlPojo(EXPIRATION_TIME);

		assertEquals(1, service.findAll().size());
		assertTrue(service.findAll().contains(url));
	}
}