package shortener.url.service.validator;

import java.net.MalformedURLException;
import java.net.URL;

public class DefaultUrlValidator implements UrlValidator {

	@Override
	public UrlStatus validate(String url) {
		var result = UrlStatus.GOOD;
		try {
			if (!url.startsWith("http")) {
				url = "http://" + url;
				result = UrlStatus.NO_PROTOCOL;
			}
			new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			result = UrlStatus.WRONG;
		}
		return result;
	}
}
