package shortener.url.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shortener.url.model.Url;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256ShortingAlgorithm<T extends Url> implements ShortingAlgorithm<T> {

	public static final String SHA_256 = "SHA-256";
	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public String shortenUrl(T urlPojo) {
		if (urlPojo.getHash() != null)
			throw new IllegalStateException("Hash is not null!");

		MessageDigest messageDigest = obtainMessageDigest(SHA_256);
		messageDigest.update(urlPojo.getUrl().getBytes(StandardCharsets.UTF_8));
		messageDigest.update(urlPojo.getExpirationTime().toString().getBytes(StandardCharsets.UTF_8));

		var hashInBytes = messageDigest.digest();

		String hex = bytesToHex(hashInBytes);

		return hex.substring(0, 6);
	}

	private MessageDigest obtainMessageDigest(String algorithm) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		if (messageDigest == null)
			throw new IllegalStateException(algorithm + " not found");
		return messageDigest;
	}

	private String bytesToHex(byte[] hashInBytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : hashInBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
}
