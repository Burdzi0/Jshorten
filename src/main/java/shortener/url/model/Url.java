package shortener.url.model;

import java.time.OffsetDateTime;

public interface Url {
	String getHash();

	String getUrl();

	OffsetDateTime getExpirationTime();

	void setHash(String hash);
}
