package shortener.url.service.validator;

@FunctionalInterface
public interface UrlValidator {
	UrlStatus validate(String url);
}
