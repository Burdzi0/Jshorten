package shortener.url.service;

import shortener.url.model.Url;
import shortener.url.repository.UrlRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public class DefaultUrlServiceImpl implements UrlService {

	private UrlRepository repository;
	private UrlFactory factory;

	public DefaultUrlServiceImpl(UrlRepository repository, UrlFactory factory) {
		this.repository = repository;
		this.factory = factory;
	}

	@Override
	public Url createUrl(String url, OffsetDateTime expirationTime) throws BlankUrlException, IllegalTimestampException {
		if (url.isBlank() || url.isEmpty())
			throw new BlankUrlException();

		if (expirationTime.isBefore(OffsetDateTime.now()))
			throw new IllegalTimestampException();

		return factory.createUrl(url, expirationTime);
	}

	@Override
	public void save(Url url) {
		repository.addUrl(url);
	}

	@Override
	public int deleteExpired() {
		return repository.deleteExpired();
	}

	@Override
	public Optional<Url> find(String signature) {
		return repository.find(signature);
	}

	@Override
	public Iterable<Url> findAll() {
		return repository.findAll();
	}
}
