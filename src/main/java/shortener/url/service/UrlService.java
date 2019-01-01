package shortener.url.service;

import shortener.url.model.Url;

public interface UrlService {
	void save(Url url);
	Url createUrl();
}
