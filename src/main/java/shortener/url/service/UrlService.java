package shortener.url.service;

import shortener.url.model.Url;
import shortener.url.service.validator.ValidationException;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public interface UrlService {
	Url createUrl(String url, OffsetDateTime expirationTime) throws BlankUrlException, IllegalTimestampException, ValidationException;

	void save(Url urlPojo);
	int deleteExpired();

	Optional<Url> find(String signature);

	Collection<Url> findAll();
}
