package shortener.url.service.repository;

import shortener.url.model.Url;

import java.util.Optional;

public interface UrlRepository {
	void addUrl(Url url);
	int deleteExpired();
	Optional<Url> find(String signature);
	Iterable<Url> findAll();

}
