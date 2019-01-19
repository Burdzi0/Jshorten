package shortener.url.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public abstract class Url {

	private String url;
	private OffsetDateTime expirationTime;
	private String hash;

	public Url(String url, OffsetDateTime expirationTime) {
		this.url = url;
		this.expirationTime = expirationTime;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getUrl() {
		return url;
	}

	public OffsetDateTime getExpirationTime() {
		return expirationTime;
	}

	public String getHash() {
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;

		Url url1 = (Url) object;

		if (!Objects.equals(url, url1.url)) return false;
		if (!Objects.equals(expirationTime, url1.expirationTime))
			return false;
		return Objects.equals(hash, url1.hash);
	}

	@Override
	public int hashCode() {
		int result = url != null ? url.hashCode() : 0;
		result = 31 * result + (expirationTime != null ? expirationTime.hashCode() : 0);
		result = 31 * result + (hash != null ? hash.hashCode() : 0);
		return result;
	}
}
