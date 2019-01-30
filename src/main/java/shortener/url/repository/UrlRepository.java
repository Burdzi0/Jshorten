package shortener.url.repository;

import shortener.url.model.Url;

import java.util.Collection;
import java.util.Optional;

public interface UrlRepository {
	void addUrl(Url urlPojo);
	int deleteExpired();

	Optional<Url> find(String signature);

	Collection<Url> findAll();
}
