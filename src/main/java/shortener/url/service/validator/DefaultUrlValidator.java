package shortener.url.service.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class DefaultUrlValidator implements UrlValidator {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public UrlStatus validate(String url) {
		log.info("Validating url: " + url);
		var result = UrlStatus.GOOD;
		if (!url.startsWith("http")) {
			log.info("Url has no protocol");
			url = "http://" + url;
			result = UrlStatus.NO_PROTOCOL;
		}
		try {
			new URL(url).toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			log.info(url + " has wrong format");
			result = UrlStatus.WRONG;
		}
		return result;
	}
}
