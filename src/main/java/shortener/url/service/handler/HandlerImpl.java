package shortener.url.service.handler;

import shortener.url.model.Url;

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
}
