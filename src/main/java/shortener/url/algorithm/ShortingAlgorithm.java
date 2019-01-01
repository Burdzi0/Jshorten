package shortener.url.algorithm;

import shortener.url.model.Url;

@FunctionalInterface
public interface ShortingAlgorithm {
	String shortenUrl(Url url);
}
