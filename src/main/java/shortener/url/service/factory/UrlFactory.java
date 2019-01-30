package shortener.url.service.factory;

import shortener.url.model.Url;

import java.time.OffsetDateTime;

public interface UrlFactory<T extends Url> {
	T createUrl(String url, OffsetDateTime expirationTime);
	String shortenUrl(T urlPojo);
}
