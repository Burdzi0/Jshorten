package shortener.url.service.factory;

import shortener.url.model.Url;

import java.time.OffsetDateTime;

public interface UrlFactory {
	Url createUrl(String url, OffsetDateTime expirationTime);

	String shortenUrl(Url urlPojo);
}
