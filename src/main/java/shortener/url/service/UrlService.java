package shortener.url.service;

import shortener.url.model.Url;
import shortener.url.service.validator.ValidationException;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public interface UrlService<T extends Url> {
	T createUrl(String url, OffsetDateTime expirationTime) throws BlankUrlException, IllegalTimestampException, ValidationException;
	void save(T urlPojo);
	int deleteExpired();
	Optional<T> find(String signature);

	Collection<T> findAll();
}
