package shortener.url.service;

import shortener.url.model.Url;
import shortener.url.repository.UrlRepository;
import shortener.url.service.factory.UrlFactory;
import shortener.url.service.validator.UrlValidator;
import shortener.url.service.validator.ValidationException;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public class DefaultUrlServiceImpl implements UrlService {

	private UrlRepository repository;
	private UrlFactory factory;
	private UrlValidator validator;

	public DefaultUrlServiceImpl(UrlRepository repository, UrlFactory factory, UrlValidator validator) {
		this.repository = repository;
		this.factory = factory;
		this.validator = validator;
	}

	@Override
	public Url createUrl(String url, OffsetDateTime expirationTime) throws BlankUrlException, IllegalTimestampException, ValidationException {
		if (url.isBlank() || url.isEmpty())
			throw new BlankUrlException();

		if (!validator.validate(url))
			throw new ValidationException();

		if (expirationTime.isBefore(OffsetDateTime.now()))
			throw new IllegalTimestampException();

		return factory.createUrl(url, expirationTime);
	}

	@Override
	public void save(Url urlPojo) {
		repository.addUrl(urlPojo);
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
	public Collection<Url> findAll() {
		return repository.findAll();
	}
}
