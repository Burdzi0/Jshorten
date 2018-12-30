package shortener.url.model;

import java.time.OffsetDateTime;

public class Url {
	
	private String url;
	private String hash;
	private OffsetDateTime expirationTime;

	public Url(String url, OffsetDateTime expirationTime) {
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
		return "Url{" +
				"url='" + url + '\'' +
				", hash='" + hash + '\'' +
				", expirationTime=" + expirationTime +
				'}';
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;

		Url url = (Url) object;

		return hash.equals(url.hash);
	}

	@Override
	public int hashCode() {
		return hash != null ? hash.hashCode() : 0;
	}
}
