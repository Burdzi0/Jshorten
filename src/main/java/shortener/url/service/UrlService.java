package shortener.url.service;

import shortener.url.model.Url;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface UrlService {
	Url createUrl(String url, OffsetDateTime expirationTime) throws BlankUrlException, IllegalTimestampException;
	void save(Url url);
	int deleteExpired();
	Optional<Url> find(String signature);
	Iterable<Url> findAll();
}
