package shortener.url.controller;

import shortener.url.model.Url;
import shortener.url.service.*;
import shortener.url.service.validator.ValidationException;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

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
	}

	public void index() {
		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			return new ThymeleafTemplateEngine().render(
					new ModelAndView(model, "index")
			);
		});
	}

	public void admin() {
		get("/admin", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("all", service.findAll());
			return new ThymeleafTemplateEngine().render(
					new ModelAndView(model, "admin")
			);
		});
	}

	public void save() {
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
				return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
			} catch (ValidationException e) {
				model.put("message", "It is not a valid url!");
				return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
			} catch (BlankUrlException e) {
				model.put("message", "Url field can't be empty!");
				return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
			}

			url.ifPresent(urlObj -> {
				service.save(urlObj);
				model.put("bareUrl", "http://localhost:4567/" + urlObj.getHash());
				model.put("time", offsetTime);
			});

			return new ThymeleafTemplateEngine().render(
					new ModelAndView(model, "save")
			);
		});
	}

	private void hashRedirect() {
		get("/:hash", (request, response) -> {
			service.find(request.params(":hash"))
					.ifPresent(url -> {
						response.status(302);
						response.redirect(url.getUrl());
						halt();
					});
			response.redirect("/");
			return "Redirecting to home page";
		});
	}

	private int getTimePeriod(int time) {
		return TimePeriod.getTimeFromIndex(time);
	}

	private void timePeriodExceptionHandler() {
		exception(IllegalTimePeriodIndex.class, (exception, request, response) ->
				response.redirect("/"));
	}
}
