package shortener.url.controller;

import shortener.url.model.Url;
import shortener.url.service.*;
import shortener.url.service.validator.ValidationException;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static spark.Spark.*;

public class TemplateController<T extends Url> {

	private final UrlService<T> service;

	public TemplateController(UrlService<T> service) {
		this.service = service;
		index();
		admin();
		save();
		hashRedirect();
		timePeriodExceptionHandler();
		notFoundRedirect();
	}

	private void index() {
		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			return render(model, "index");
		});
	}

	private void admin() {
		get("/admin", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("all", service.findAll());
			return render(model, "admin");
		});
	}

	private void save() {
		post("/save", (request, response) -> {
			Map<String, Object> model = new HashMap<>();

			String urlToShorten = request.queryParams("url");
			var time = Integer.parseInt(request.queryParams("time"));

			var timePeriod = getTimePeriod(time);
			var offsetTime = OffsetDateTime.now().plusMinutes(timePeriod);


			Optional<T> url;
			try {
				url = Optional.ofNullable(service.createUrl(urlToShorten, offsetTime));
			} catch (IllegalTimestampException e) {
				model.put("message", "Ooops! Something went wrong, contact me please at github.com/Burdzi0");
				return render(model, "index");
			} catch (ValidationException e) {
				model.put("message", "It is not a valid url!");
				return render(model, "index");
			} catch (BlankUrlException e) {
				model.put("message", "Url field can't be empty!");
				return render(model, "index");
			}

			url.ifPresent(urlObj -> {
				service.save(urlObj);
				model.put("showUrl", createRelativeUrl(request.raw(), urlObj.getHash()));
				model.put("time", offsetTime);
			});

			return render(model, "save");
		});
	}

	private String createRelativeUrl(HttpServletRequest request, String hash) {
		StringBuffer url = request.getRequestURL();
		String uri = request.getRequestURI();
		String host = url.substring(0, url.indexOf(uri));
		return host + "/" + hash;
	}

	private void hashRedirect() {
		get("/:hash", (request, response) -> {
			service.find(request.params(":hash"))
					.ifPresent(url -> {
						response.status(302);
						response.redirect(url.getUrl());
						halt();
					});
			Map<String, Object> model = new HashMap<>();
			model.put("message", "Link does not exist!");
			return render(model, "index");
		});
	}

	private void notFoundRedirect() {
		notFound((request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "Url does not exist!");
			return render(model, "index");
		});
	}

	private String render(Map<String, Object> model, String save) {
		return new ThymeleafTemplateEngine()
				.render(
				new ModelAndView(model, save)
		);
	}

	private int getTimePeriod(int time) {
		return TimePeriod.getTimeFromIndex(time);
	}

	private void timePeriodExceptionHandler() {
		exception(IllegalTimePeriodIndex.class, (exception, request, response) ->
				response.redirect("/"));
	}
}
