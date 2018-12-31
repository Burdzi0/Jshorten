package shortener.url;

import org.json.JSONArray;
import org.json.JSONObject;
import shortener.url.model.Url;
import shortener.url.service.algorithm.Sha256ShortingAlgorithm;
import shortener.url.service.handler.DefaultDuplicateHandlerImpl;
import shortener.url.service.handler.Handler;
import shortener.url.service.handler.HandlerImpl;
import shortener.url.service.url.UrlFactory;

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

public class Main {

	public void serve() {
		Handler handler = new HandlerImpl(new DefaultDuplicateHandlerImpl());
		UrlFactory factory = new UrlFactory(new Sha256ShortingAlgorithm());
		handler.rememberUrl(factory.createUrl("http://www.google.pl", OffsetDateTime.MAX));

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(handler::deleteExpired, 30, TimeUnit.MINUTES);

		get("/", ((request, response) ->
				new JSONObject().put("links", new JSONArray(handler.getAllUrls()))));

		path("/api", () -> {
			get("/:hash", (request, response) -> {
				System.out.println("Param " + request.params(":hash"));
				handler.find(request.params(":hash"))
						.ifPresent(url -> {
							response.status(302);
							response.redirect(url.getUrl());
						});
				response.redirect("/");
				return null;
			});

			post("/add", (request, response) -> {
				System.out.println(request.body());
				JSONObject object = new JSONObject(request.body());
				var url  = object.getString("url");
				if (!url.startsWith("http")) {
					response.status(400);
					response.body(new JSONObject().put("message", "The url should start with http*").toString());
					return null;
				}

				var time = OffsetDateTime.parse(object.getString("expirationTime"));
				if (time.isBefore(OffsetDateTime.now())) {
					response.status(400);
					response.body(new JSONObject().put("message", "The specified date is already expired").toString());
					return null;
				}
				Url urlObject = factory.createUrl(url, time);
				handler.rememberUrl(urlObject);
				System.out.println(urlObject);
				return urlObject.toString();
			});
		});
	}

	public static void main(String[] args) {
		new Main().serve();
	}
}
