package shortener.url.algorithm;

import shortener.url.model.UrlPojo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256ShortingAlgorithm implements ShortingAlgorithm<UrlPojo> {

	@Override
	public String shortenUrl(UrlPojo urlPojo) {
		if (urlPojo.getHash() != null)
			throw new IllegalStateException("Hash is not null!");
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
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
