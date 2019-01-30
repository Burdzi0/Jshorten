package shortener.url.service.factory;

import shortener.url.model.Url;
import shortener.url.model.UrlPojo;

import java.time.OffsetDateTime;

public class DefaultUrlCreator implements UrlCreator {

	@Override
	public Url create(String url, OffsetDateTime expirationTime) {
		return new UrlPojo(url, expirationTime);
	}
}
