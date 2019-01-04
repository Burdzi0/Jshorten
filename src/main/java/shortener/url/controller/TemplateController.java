package shortener.url.controller;

import shortener.url.service.UrlService;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class TemplateController {

	private final UrlService service;

	public TemplateController(UrlService service) {
		this.service = service;
		index();
		admin();
		save();
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

			String url = request.queryParams("bareUrl");
			String date = request.queryParams("date");

			System.out.println(date);
			System.out.println(url);

			model.put("bareUrl", url);
			model.put("date", date);

			System.out.println(request.body());

			return new ThymeleafTemplateEngine().render(
					new ModelAndView(model, "save")
			);
		});
	}
}
