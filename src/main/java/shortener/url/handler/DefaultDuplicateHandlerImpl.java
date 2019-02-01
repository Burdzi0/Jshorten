package shortener.url.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shortener.url.model.Url;

public class DefaultDuplicateHandlerImpl<T extends Url> implements DuplicateHandler<T> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public T duplicate(T urlPojo) {
		var hash = urlPojo.getHash();
		if (hash.length() > 6) {
			log.info("Resolving duplicate: " + hash);
			var genericHash = hash.substring(0,6);
			var number = Integer.parseInt(hash.substring(6));
			urlPojo.setHash(genericHash + ++number);
		}
		return urlPojo;
	}
}
