package shortener.url.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shortener.url.model.Url;
import shortener.url.service.BlankUrlException;
import shortener.url.service.IllegalTimestampException;
import shortener.url.service.UrlService;
import shortener.url.service.validator.ValidationException;

import java.time.OffsetDateTime;
import java.util.Collection;

import static spark.Spark.*;

public class ApiController<T extends Url> {

	private Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final UrlService<T> service;

	public ApiController(UrlService<T> service) {
		this.service = service;
		registerAll();
	}

	private void registerAll() {
		after("/api/*", (request, response) -> response.type("application/json"));
		log.info("Registering ApiController...");
		path("/api", () -> {
			log.info("Registering admin endpoint");
			admin();
			log.info("Registering admin/file endpoint");
			adminAsFile();
			log.info("Registering redirect endpoint");
			hashRedirect();
			log.info("Registering save endpoint");
			save();
		});
		log.info("ApiController registered");
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

	private void adminAsFile() {
		get("/admin/file", (request, response) -> {
			Collection<T> all = service.findAll();
			response.header("Content-Disposition", "attachment");

			var jsonArrayAsString = new JSONArray(all).toString();
			var raw = response.raw();
			raw.getOutputStream().write(jsonArrayAsString.getBytes());
			raw.getOutputStream().flush();
			raw.getOutputStream().close();
			return raw;
		});
	}

	private void hashRedirect() {
		get("/:hash", (request, response) -> {
			var url = service.find(request.params(":hash"));
			return url.map(JSONObject::new).orElse(errorJsonMessage("The link does not exist"));
		});
	}

	private JSONObject errorJsonMessage(String s) {
		return new JSONObject().put("error", s);
	}
}
