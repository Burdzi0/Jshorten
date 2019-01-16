package shortener.url.service.validator;

public class DefaultUrlValidator implements UrlValidator {

	@Override
	public boolean validate(String url) {
		return url.startsWith("http://") || url.startsWith("https://");
	}
}
