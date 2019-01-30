package shortener.url.service.factory;

import shortener.url.model.Url;

import java.time.OffsetDateTime;

@FunctionalInterface
public interface UrlCreator {
	Url create(String url, OffsetDateTime expirationTime);
}
