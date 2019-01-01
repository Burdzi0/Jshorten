package shortener.url.controller;

import shortener.url.repository.UrlRepository;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class TemplateController {

	private final UrlRepository urlRepository;

	public TemplateController(UrlRepository urlRepository) {
		this.urlRepository = urlRepository;
		index();
		admin();
	}

	public void index() {
		get("/", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "SampleMessage");
			return new ThymeleafTemplateEngine().render(
					new ModelAndView(model, "hello")
			);
		});
	}

	public void admin() {
		get("/admin", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("all", urlRepository.findAll());
			return new ThymeleafTemplateEngine().render(
					new ModelAndView(model, "admin")
			);
		});
	}
}
