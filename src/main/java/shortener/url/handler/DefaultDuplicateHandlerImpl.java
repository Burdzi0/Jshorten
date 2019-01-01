package shortener.url.handler;

import shortener.url.model.Url;

public class DefaultDuplicateHandlerImpl implements DuplicateHandler {

	@Override
	public Url duplicate(Url url) {
		var hash = url.getHash();
		if (hash.length() > 6) {
			var genericHash = hash.substring(0,6);
			var number = Integer.parseInt(hash.substring(6));
			url.setHash(genericHash + ++number);
		}
		return url;
	}
}
