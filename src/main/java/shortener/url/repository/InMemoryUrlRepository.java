package shortener.url.repository;

import shortener.url.model.Url;
import shortener.url.handler.DuplicateHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUrlRepository implements UrlRepository {

	private Map<String, Url> remembered = new HashMap<>();
	private DuplicateHandler duplicateHandler;

	public InMemoryUrlRepository(DuplicateHandler duplicateHandler) {
		this.duplicateHandler = duplicateHandler;
	}

	@Override
	public Optional<Url> find(final String param) {
		return Optional.ofNullable(remembered.get(param));
	}

	@Override
	public Iterable<Url> findAll() {
		return remembered.values();
	}

	@Override
	public void addUrl(Url url) {
		while (remembered.putIfAbsent(url.getHash(), url) == null) {
			url = duplicateHandler.duplicate(url);
		}
	}

	@Override
	public int deleteExpired() {
		var expired = 0;

		var entrySet = remembered.entrySet();
		OffsetDateTime timestamp;
		for (Map.Entry<String, Url> entry: entrySet) {
			timestamp = entry.getValue().getExpirationTime();
			if (OffsetDateTime.now().isAfter(timestamp)) {
				remembered.remove(entry.getKey(), entry.getValue());
				expired++;
			}
		}
		return expired;
	}
}
