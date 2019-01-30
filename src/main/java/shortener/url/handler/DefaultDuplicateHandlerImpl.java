package shortener.url.handler;

import shortener.url.model.Url;

public class DefaultDuplicateHandlerImpl implements DuplicateHandler {

	@Override
	public Url duplicate(Url urlPojo) {
		var hash = urlPojo.getHash();
		if (hash.length() > 6) {
			var genericHash = hash.substring(0,6);
			var number = Integer.parseInt(hash.substring(6));
			urlPojo.setHash(genericHash + ++number);
		}
		return urlPojo;
	}
}
