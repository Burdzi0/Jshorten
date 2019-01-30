package shortener.url.repository;

import shortener.url.handler.DuplicateHandler;
import shortener.url.model.Url;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUrlRepository<T extends Url> implements UrlRepository<T> {

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
	}

	@Override
	public int deleteExpired() {
		var expired = 0;

		var entrySet = remembered.entrySet();
		OffsetDateTime timestamp;
		for (Map.Entry<String, T> entry : entrySet) {
			timestamp = entry.getValue().getExpirationTime();
			if (OffsetDateTime.now().isAfter(timestamp)) {
				remembered.remove(entry.getKey(), entry.getValue());
				expired++;
			}
		}
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
