package shortener.url.algorithm;

import shortener.url.model.Url;

@FunctionalInterface
public interface ShortingAlgorithm<T extends Url> {
	String shortenUrl(T urlPojo);
}
