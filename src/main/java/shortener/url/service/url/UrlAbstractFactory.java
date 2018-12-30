package shortener.url.service.url;

import shortener.url.model.Url;

import java.time.OffsetDateTime;

public abstract class UrlAbstractFactory {

	public Url createUrl(String url, OffsetDateTime expirationTime) {
		Url urlObject = new Url(url, expirationTime);
		urlObject.setHash(calculateHash(urlObject));
		return urlObject;
	}

	public abstract String calculateHash(Url url);
}
