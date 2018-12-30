package shortener.url.service.handler;

import shortener.url.model.Url;

import java.util.Collection;
import java.util.Optional;

public interface Handler {
	boolean rememberUrl(Url url);
	Optional<Url> find(String params);
	Collection<Url> getAllUrls();
}
