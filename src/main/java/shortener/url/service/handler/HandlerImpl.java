package shortener.url.service.handler;

import shortener.url.model.Url;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HandlerImpl implements Handler {

	private Map<String, Url> remembered = new HashMap<>();
	private DuplicateHandler duplicateHandler;

	public HandlerImpl(DuplicateHandler duplicateHandler) {
		this.duplicateHandler = duplicateHandler;
	}

	@Override
	public boolean rememberUrl(Url url) {
		while (remembered.putIfAbsent(url.getHash(), url) == null) {
			url = duplicateHandler.duplicate(url);
		}
		return true;
	}

	@Override
	public Optional<Url> find(final String param) {
		return Optional.ofNullable(remembered.get(param));
	}

	@Override
	public Collection<Url> getAllUrls() {
		return remembered.values();
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
