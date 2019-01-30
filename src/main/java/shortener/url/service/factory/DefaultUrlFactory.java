package shortener.url.service.factory;

import shortener.url.algorithm.ShortingAlgorithm;
import shortener.url.model.Url;

import java.time.OffsetDateTime;

public class DefaultUrlFactory<T extends Url> implements UrlFactory<T> {

	private ShortingAlgorithm<T> algorithm;
	private UrlCreator<T> creator;

	public DefaultUrlFactory(ShortingAlgorithm<T> algorithm, UrlCreator<T> creator) {
		this.algorithm = algorithm;
		this.creator = creator;
	}

	@Override
	public T createUrl(String url, OffsetDateTime expirationTime) {
		T urlObj = creator.create(url, expirationTime);
		urlObj.setHash(shortenUrl(urlObj));
		return urlObj;
	}

	@Override
	public String shortenUrl(T urlPojo) {
		return algorithm.shortenUrl(urlPojo);
	}

	public ShortingAlgorithm<T> getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(ShortingAlgorithm<T> algorithm) {
		this.algorithm = algorithm;
	}

	public UrlCreator<T> getCreator() {
		return creator;
	}

	public void setCreator(UrlCreator<T> creator) {
		this.creator = creator;
	}
}
