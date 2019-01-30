package shortener.url.service.factory;

import shortener.url.algorithm.ShortingAlgorithm;
import shortener.url.model.Url;

import java.time.OffsetDateTime;

public class DefaultUrlFactory implements UrlFactory {

	private ShortingAlgorithm algorithm;
	private UrlCreator creator;

	public DefaultUrlFactory(ShortingAlgorithm algorithm, UrlCreator creator) {
		this.algorithm = algorithm;
		this.creator = creator;
	}

	@Override
	public Url createUrl(String url, OffsetDateTime expirationTime) {
		Url urlPojoObject = creator.create(url, expirationTime);
		urlPojoObject.setHash(shortenUrl(urlPojoObject));
		return urlPojoObject;
	}

	@Override
	public String shortenUrl(Url urlPojo) {
		return algorithm.shortenUrl(urlPojo);
	}
}
