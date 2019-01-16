package shortener.url.algorithm;

@FunctionalInterface
public interface ShortingAlgorithm<T> {
	String shortenUrl(T urlPojo);
}
