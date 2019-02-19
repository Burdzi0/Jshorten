package shortener.url.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shortener.url.model.Url;
import shortener.url.repository.UrlRepository;
import shortener.url.service.factory.UrlFactory;
import shortener.url.service.validator.UrlStatus;
import shortener.url.service.validator.UrlValidator;
import shortener.url.service.validator.ValidationException;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public class DefaultUrlServiceImpl<T extends Url> implements UrlService<T> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private UrlRepository<T> repository;
	private UrlFactory<T> factory;
	private UrlValidator validator;

	public DefaultUrlServiceImpl(UrlRepository<T> repository, UrlFactory<T> factory, UrlValidator validator) {
		this.repository = repository;
		this.factory = factory;
		this.validator = validator;
	}

	@Override
	public T createUrl(String url, OffsetDateTime expirationTime) throws BlankUrlException, IllegalTimestampException, ValidationException {
		log.info("Creating url: " + url + ", expiration time: " + expirationTime);

		if (url == null || url.isEmpty() || url.isBlank())
			throw new BlankUrlException("The url is null, blank or empty");

		var status = validator.validate(url);

		if (status == UrlStatus.WRONG)
			throw new ValidationException(url + " is not a valid url");

		if (status == UrlStatus.NO_PROTOCOL) {
			url = "http://" + url;
		}

		if (expirationTime == null || expirationTime.isBefore(OffsetDateTime.now()))
			throw new IllegalTimestampException("ExpirationTime is null or has already passed");

		return factory.createUrl(url, expirationTime);
	}

	@Override
	public void save(T urlPojo) {
		repository.addUrl(urlPojo);
	}

	@Override
	public int deleteExpired() {
		return repository.deleteExpired();
	}

	@Override
	public Optional<T> find(String signature) {
		return repository.find(signature);
	}

	@Override
	public Collection<T> findAll() {
		return repository.findAll();
	}
}
