package shortener.url.algorithm;

import org.junit.jupiter.api.Test;
import shortener.url.model.UrlPojo;

class Sha256ShortingAlgorithmTest {

	private Sha256ShortingAlgorithm<UrlPojo> algorithm = new Sha256ShortingAlgorithm<>();
	private ShortingAlgorithm<UrlPojo> abstractAlgorithm = algorithm;

	@Test
	void shortenUrl() {
	}

	@Test
	void shortenShouldThrowException() {

	}
}