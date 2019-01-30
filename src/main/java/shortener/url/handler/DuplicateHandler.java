package shortener.url.handler;

import shortener.url.model.Url;

@FunctionalInterface
public interface DuplicateHandler<T extends Url> {
	T duplicate(T urlPojo);
}
