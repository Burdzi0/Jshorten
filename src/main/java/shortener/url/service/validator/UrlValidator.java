package shortener.url.service.validator;

@FunctionalInterface
public interface UrlValidator {
	boolean validate(String url);
}
