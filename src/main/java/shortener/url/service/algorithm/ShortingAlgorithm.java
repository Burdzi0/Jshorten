package shortener.url.service.algorithm;

import shortener.url.model.Url;

@FunctionalInterface
public interface ShortingAlgorithm {
	String shortenUrl(Url url);
}
