package shortener.url.service.url;

import shortener.url.model.Url;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256UrlFactory extends UrlAbstractFactory {

	@Override
	public String calculateHash(Url url) {
		if (url.getHash() != null)
			throw new IllegalStateException("Hash is not null!");
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(url.getUrl().getBytes(StandardCharsets.UTF_8));
		md.update(url.getExpirationTime().toString().getBytes(StandardCharsets.UTF_8));

		var hashInBytes = md.digest();
		// bytes to hex
		StringBuilder sb = new StringBuilder();
		for (byte b : hashInBytes) {
			sb.append(String.format("%02x", b));
		}
		System.out.println(sb.toString());
		return sb.toString().substring(0,6);
	}

}
