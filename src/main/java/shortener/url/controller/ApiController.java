package shortener.url.controller;

import org.json.JSONObject;
import shortener.url.model.Url;
import shortener.url.service.BlankUrlException;
import shortener.url.service.IllegalTimestampException;
import shortener.url.service.UrlFactory;
import shortener.url.service.UrlService;

import java.time.OffsetDateTime;

import static spark.Spark.*;

public class ApiController {

	private final UrlService service;

	public ApiController(UrlService service) {
		this.service = service;
		registerAll();
	}

	public void registerAll() {
		path("/api", () -> {
			hashRedirectGET();
			saveRedirectPOST();
		});
	}

	private void saveRedirectPOST() {
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
				return errorJsonMessage("The url should start with http*");
			} catch (IllegalTimestampException e) {
				response.status(400);
				return errorJsonMessage("The specified date is already expired");
			}

			service.save(url);

			return new JSONObject(url).toString();
		});
	}

	private void hashRedirectGET() {
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
