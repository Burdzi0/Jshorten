package shortener.url.controller;

import org.json.JSONObject;
import shortener.url.model.Url;
import shortener.url.repository.UrlRepository;
import shortener.url.service.BlankUrlException;
import shortener.url.service.IllegalTimestampException;
import shortener.url.service.UrlFactory;
import shortener.url.service.UrlService;

import java.time.OffsetDateTime;

import static spark.Spark.*;

public class ApiController {

	private final UrlService service;
	private final UrlFactory factory;

	public ApiController(UrlService service, UrlFactory factory) {
		this.service = service;
		this.factory = factory;
		registerAll();
	}

	public void registerAll() {
		path("/api", () -> {
			get("/:hash", (request, response) -> {
				service.find(request.params(":hash"))
						.ifPresent(url -> {
							response.status(302);
							response.redirect(url.getUrl());
						});
				return new JSONObject().put("message", "The link does not exist").toString();
			});

			post("/add", (request, response) -> {
				JSONObject object = new JSONObject(request.body());
				Url url;
				try {
					url = service.createUrl(
							object.getString("url"),
							OffsetDateTime.parse(object.getString("expirationTime"))
					);
				} catch (BlankUrlException e) {
					response.status(400);
					return new JSONObject().put("message", "The url should start with http*").toString();
				} catch (IllegalTimestampException e) {
					response.status(400);
					return new JSONObject().put("message", "The specified date is already expired").toString();
				}

				service.save(url);

				return new JSONObject(url).toString();
			});
		});
	}
}
