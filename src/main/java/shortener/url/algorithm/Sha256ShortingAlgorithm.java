package shortener.url.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shortener.url.model.Url;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256ShortingAlgorithm<T extends Url> implements ShortingAlgorithm<T> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public String shortenUrl(T urlPojo) {
		if (urlPojo.getHash() != null)
			throw new IllegalStateException("Hash is not null!");

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		if (md == null)
			throw new IllegalStateException("SHA-256 not found");

		md.update(urlPojo.getUrl().getBytes(StandardCharsets.UTF_8));
		md.update(urlPojo.getExpirationTime().toString().getBytes(StandardCharsets.UTF_8));

		var hashInBytes = md.digest();
		// bytes to hex
		StringBuilder sb = new StringBuilder();
		for (byte b : hashInBytes) {
			sb.append(String.format("%02x", b));
		}

		return sb.substring(0, 6);
	}
}
