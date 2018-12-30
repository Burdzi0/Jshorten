package shortener.url.service.handler;

import shortener.url.model.Url;

@FunctionalInterface
public interface DuplicateHandler {
	Url duplicate(Url url);
}
