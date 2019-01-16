package shortener.url.model;

import java.time.OffsetDateTime;

public class UrlPojo implements Url {

	private String url;
	private String hash;
	private OffsetDateTime expirationTime;

	public UrlPojo(String url, OffsetDateTime expirationTime) {
		this.url = url;
		this.expirationTime = expirationTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public OffsetDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(OffsetDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public String toString() {
		return "UrlPojo{" +
				"url='" + url + '\'' +
				", hash='" + hash + '\'' +
				", expirationTime=" + expirationTime +
				'}';
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;

		UrlPojo urlPojo = (UrlPojo) object;

		return hash.equals(urlPojo.hash);
	}

	@Override
	public int hashCode() {
		return hash != null ? hash.hashCode() : 0;
	}
}
