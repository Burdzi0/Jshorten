package shortener.url;

import org.json.JSONArray;
import org.json.JSONObject;
import shortener.url.service.algorithm.Sha256ShortingAlgorithm;
import shortener.url.service.handler.DefaultDuplicateHandlerImpl;
import shortener.url.service.repository.InMemoryUrlRepository;
import shortener.url.service.repository.UrlRepository;
import shortener.url.service.url.UrlFactory;

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.get;

public class Main {

	public void serve() {
		UrlRepository handler = new InMemoryUrlRepository(new DefaultDuplicateHandlerImpl());
		UrlFactory factory = new UrlFactory(new Sha256ShortingAlgorithm());
		handler.addUrl(factory.createUrl("http://www.google.pl", OffsetDateTime.MAX));

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(handler::deleteExpired, 30, TimeUnit.MINUTES);

		get("/", ((request, response) ->
				new JSONObject().put("links", new JSONArray(handler.findAll()))));


	}

	public static void main(String[] args) {
		new Main().serve();
	}
}
