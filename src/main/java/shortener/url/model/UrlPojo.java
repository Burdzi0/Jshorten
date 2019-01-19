package shortener.url.model;

import java.time.OffsetDateTime;

public class UrlPojo extends Url {

	public UrlPojo(String url, OffsetDateTime expirationTime) {
		super(url, expirationTime);
	}
}
