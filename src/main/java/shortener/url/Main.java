package shortener.url;

import org.json.JSONArray;
import org.json.JSONObject;
import shortener.url.model.Url;
import shortener.url.service.handler.DefaultHandlerImpl;
import shortener.url.service.handler.Handler;
import shortener.url.service.handler.HandlerImpl;
import shortener.url.service.url.Sha256UrlFactory;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static spark.Spark.*;

public class Main {

	public void serve() {
		Handler handler = new HandlerImpl(new DefaultHandlerImpl());
		handler.rememberUrl(new Sha256UrlFactory().createUrl("http://www.google.pl", OffsetDateTime.MAX));

		get("/", ((request, response) ->
				new JSONObject().put("links", new JSONArray(handler.getAllUrls()))));

		path("/api", () -> {
			get("/:hash", (request, response) -> {
				System.out.println("Param " + request.params(":hash"));
				handler.find(request.params(":hash"))
						.ifPresent(url -> {
							response.status(302);
							response.redirect(url.getUrl());
						});
				response.redirect("/");
				return null;
			});

			post("/add", (request, response) -> {
				System.out.println(request.body());
				JSONObject object = new JSONObject(request.body());
				var url  = object.getString("url");
				var time = OffsetDateTime.parse(object.getString("expirationTime"));
				Url urlObject = new Sha256UrlFactory().createUrl(url, time);
				handler.rememberUrl(urlObject);
				System.out.println(urlObject);
				return urlObject.toString();
			});
		});
	}

	public static void main(String[] args) {
		new Main().serve();
	}
}
