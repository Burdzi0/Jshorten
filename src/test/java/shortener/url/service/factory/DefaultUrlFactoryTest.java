package shortener.url.service.factory;

import org.junit.jupiter.api.Test;
import shortener.url.algorithm.Sha256ShortingAlgorithm;
import shortener.url.algorithm.ShortingAlgorithm;
import shortener.url.model.UrlPojo;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultUrlFactoryTest {

	public static final String WWW_EXAMPLE_COM = "www.example.com";
	public static final OffsetDateTime EXPIRATION_TIME = OffsetDateTime.MIN;

	private ShortingAlgorithm<UrlPojo> algorithm = new Sha256ShortingAlgorithm<>();
	private UrlCreator<UrlPojo> creator = new DefaultUrlCreator();
	private UrlFactory<UrlPojo> factory = new DefaultUrlFactory<>(algorithm, creator);


	@Test
	void createUrl() {
		var manufactured = factory.createUrl(WWW_EXAMPLE_COM, EXPIRATION_TIME);

		UrlPojo created = creator.create(WWW_EXAMPLE_COM, EXPIRATION_TIME);
		created.setHash(algorithm.shortenUrl(created));

		assertEquals(created, manufactured);
	}

	@Test
	void shortenUrl() {
		var manufactured = factory.createUrl(WWW_EXAMPLE_COM, EXPIRATION_TIME);
		UrlPojo created = creator.create(WWW_EXAMPLE_COM, EXPIRATION_TIME);

		assertEquals(algorithm.shortenUrl(created), manufactured.getHash());
	}
}