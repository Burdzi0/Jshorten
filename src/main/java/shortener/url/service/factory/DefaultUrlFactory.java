package shortener.url.service.factory;

import shortener.url.algorithm.ShortingAlgorithm;
import shortener.url.model.UrlPojo;

import java.time.OffsetDateTime;

public class DefaultUrlFactory implements UrlFactory<UrlPojo> {

	private ShortingAlgorithm<UrlPojo> algorithm;

	public DefaultUrlFactory(ShortingAlgorithm<UrlPojo> algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public UrlPojo createUrl(String url, OffsetDateTime expirationTime) {
		UrlPojo urlPojoObject = new UrlPojo(url, expirationTime);
		urlPojoObject.setHash(shortenUrl(urlPojoObject));
		return urlPojoObject;
	}

	@Override
	public String shortenUrl(UrlPojo urlPojo) {
		return algorithm.shortenUrl(urlPojo);
	}
}
