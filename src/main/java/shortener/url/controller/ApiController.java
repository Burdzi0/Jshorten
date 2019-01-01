package shortener.url.controller;

import org.json.JSONObject;
import shortener.url.model.Url;
import shortener.url.repository.UrlRepository;
import shortener.url.service.UrlFactory;

import java.time.OffsetDateTime;

import static spark.Spark.*;

public class ApiController {

	private final UrlRepository repository;
	private final UrlFactory factory;

	public ApiController(UrlRepository repository, UrlFactory factory) {
		this.repository = repository;
		this.factory = factory;
		registerAll();
	}

	public void registerAll() {
		path("/api", () -> {
			get("/:hash", (request, response) -> {
				System.out.println("Param " + request.params(":hash"));
				repository.find(request.params(":hash"))
						.ifPresent(url -> {
							response.status(302);
							response.redirect(url.getUrl());
						});
				response.redirect("/");
				return null;
			});

			post("/add", (request, response) -> {
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
				repository.addUrl(urlObject);
				return urlObject.toString();
			});
		});
	}
}
