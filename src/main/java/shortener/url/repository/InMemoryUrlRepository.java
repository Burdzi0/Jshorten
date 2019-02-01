package shortener.url.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shortener.url.handler.DuplicateHandler;
import shortener.url.model.Url;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUrlRepository<T extends Url> implements UrlRepository<T> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private Map<String, T> remembered = new HashMap<>();
	private DuplicateHandler<T> duplicateHandler;

	public InMemoryUrlRepository(DuplicateHandler<T> duplicateHandler) {
		this.duplicateHandler = duplicateHandler;
	}

	@Override
	public void addUrl(T urlPojo) {
		while (remembered.putIfAbsent(urlPojo.getHash(), urlPojo) == null) {
			urlPojo = duplicateHandler.duplicate(urlPojo);
		}
		log.info("Added " + urlPojo + " to repository");
	}

	@Override
	public int deleteExpired() {
		var expired = 0;

		log.info("Removing expired links [" + LocalDateTime.now() + "]");
		var entrySet = remembered.entrySet();
		OffsetDateTime timestamp;
		for (Map.Entry<String, T> entry : entrySet) {
			timestamp = entry.getValue().getExpirationTime();
			if (OffsetDateTime.now().isAfter(timestamp)) {
				log.info("Removing " + entry.getValue());
				remembered.remove(entry.getKey(), entry.getValue());
				expired++;
			}
		}

		log.info("Removed links: " + expired);
		return expired;
	}

	@Override
	public Optional<T> find(String signature) {
		return Optional.ofNullable(remembered.get(signature));
	}

	@Override
	public Collection<T> findAll() {
		return remembered.values();
	}
}
