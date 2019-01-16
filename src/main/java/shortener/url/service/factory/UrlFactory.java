package shortener.url.service.factory;

import java.time.OffsetDateTime;

public interface UrlFactory<T> {
	T createUrl(String url, OffsetDateTime expirationTime);

	String shortenUrl(T urlPojo);
}
