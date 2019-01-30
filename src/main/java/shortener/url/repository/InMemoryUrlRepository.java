package shortener.url.repository;

import shortener.url.handler.DuplicateHandler;
import shortener.url.model.Url;

import java.time.OffsetDateTime;
import java.util.Collection;
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
	public void addUrl(Url urlPojo) {
		while (remembered.putIfAbsent(urlPojo.getHash(), urlPojo) == null) {
			urlPojo = duplicateHandler.duplicate(urlPojo);
		}
	}

	@Override
	public int deleteExpired() {
		var expired = 0;

		var entrySet = remembered.entrySet();
		OffsetDateTime timestamp;
		for (Map.Entry<String, Url> entry : entrySet) {
			timestamp = entry.getValue().getExpirationTime();
			if (OffsetDateTime.now().isAfter(timestamp)) {
				remembered.remove(entry.getKey(), entry.getValue());
				expired++;
			}
		}
		return expired;
	}

	@Override
	public Optional<Url> find(String signature) {
		return Optional.ofNullable(remembered.get(signature));
	}

	@Override
	public Collection<Url> findAll() {
		return remembered.values();
	}
}
