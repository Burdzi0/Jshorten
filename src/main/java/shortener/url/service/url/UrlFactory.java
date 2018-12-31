package shortener.url.service.url;

import shortener.url.model.Url;
import shortener.url.service.algorithm.ShortingAlgorithm;

import java.time.OffsetDateTime;

public class UrlFactory {

	private ShortingAlgorithm algorithm;

	public UrlFactory(ShortingAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Url createUrl(String url, OffsetDateTime expirationTime) {
		Url urlObject = new Url(url, expirationTime);
		urlObject.setHash(shortenUrl(urlObject));
		return urlObject;
	}

	public String shortenUrl(Url url) {
		return algorithm.shortenUrl(url);
	}
}
