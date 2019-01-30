package shortener.url.service.factory;

import java.time.OffsetDateTime;

public interface UrlCreator<T> {
	T create(String url, OffsetDateTime expirationTime);
}
