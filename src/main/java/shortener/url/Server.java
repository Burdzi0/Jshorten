package shortener.url;

import shortener.url.algorithm.Sha256ShortingAlgorithm;
import shortener.url.controller.ApiController;
import shortener.url.controller.TemplateController;
import shortener.url.handler.DefaultDuplicateHandlerImpl;
import shortener.url.model.UrlPojo;
import shortener.url.repository.InMemoryUrlRepository;
import shortener.url.repository.UrlRepository;
import shortener.url.service.DefaultUrlServiceImpl;
import shortener.url.service.UrlService;
import shortener.url.service.factory.DefaultUrlFactory;
import shortener.url.service.factory.UrlFactory;
import shortener.url.service.validator.DefaultUrlValidator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

class Server {

	private static final String STATIC_FILES_LOCATION = "/public";

	void serve() {
		initExceptionHandler(ex -> {
			ex.printStackTrace();
			System.exit(1);
		});

		var env = System.getenv("PORT");
		port(env == null ? 4567 : Integer.parseInt(env));

		staticFileLocation(STATIC_FILES_LOCATION);

		UrlRepository<UrlPojo> urlRepository = new InMemoryUrlRepository<>(new DefaultDuplicateHandlerImpl<>());
		UrlFactory<UrlPojo> factory = new DefaultUrlFactory(new Sha256ShortingAlgorithm());
		UrlService<UrlPojo> service = new DefaultUrlServiceImpl<>(urlRepository, factory, new DefaultUrlValidator());

		TemplateController templateController = new TemplateController<>(service);
		ApiController apiController = new ApiController<>(service);

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleWithFixedDelay(service::deleteExpired, 2, 10, TimeUnit.MINUTES);
	}



}
