package shortener.url.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import shortener.url.model.Url;
import shortener.url.service.BlankUrlException;
import shortener.url.service.IllegalTimestampException;
import shortener.url.service.UrlService;
import shortener.url.service.validator.ValidationException;

import java.time.OffsetDateTime;
import java.util.Collection;

import static spark.Spark.*;

public class ApiController<T extends Url> {

	private final UrlService<T> service;

	public ApiController(UrlService<T> service) {
		this.service = service;
		registerAll();
	}

	public void registerAll() {
		after("/api/*", (request, response) -> response.type("application/json"));
		path("/api", () -> {
			admin();
			hashRedirect();
			save();
		});
	}

	private void save() {
		post("/add", (request, response) -> {
			JSONObject object = new JSONObject(request.body());
			T urlPojo;
			try {
				urlPojo = service.createUrl(
						object.getString("url"),
						OffsetDateTime.parse(object.getString("expirationTime"))
				);
			} catch (BlankUrlException | ValidationException e) {
				response.status(400);
				return errorJsonMessage("The url should start with http*");
			} catch (IllegalTimestampException e) {
				response.status(400);
				return errorJsonMessage("The specified date has already expired");
			}

			service.save(urlPojo);

			return new JSONObject(urlPojo).toString();
		});
	}

	private void admin() {
		get("/admin", (request, response) -> {
			Collection<T> all = service.findAll();
			return new JSONArray(all);
		});
	}

	private void hashRedirect() {
		get("/:hash", (request, response) -> {
			service.find(request.params(":hash"))
					.ifPresent(url -> {
						response.status(302);
						response.redirect(url.getUrl());
					});
			return errorJsonMessage("The link does not exist");
		});
	}

	private String errorJsonMessage(String s) {
		return new JSONObject().put("error", s).toString();
	}
}
