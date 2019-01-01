package shortener.url.controller;

import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

public class TemplateController {

	public TemplateController() {
		index();
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
}
