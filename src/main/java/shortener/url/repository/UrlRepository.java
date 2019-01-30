package shortener.url.repository;

import shortener.url.model.Url;

import java.util.Collection;
import java.util.Optional;

public interface UrlRepository<T extends Url> {
	void addUrl(T urlPojo);
	int deleteExpired();
	Optional<T> find(String signature);

	Collection<T> findAll();
}
