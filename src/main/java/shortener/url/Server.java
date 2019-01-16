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

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.staticFileLocation;

public class Server {

	public static final String STATIC_FILES_LOCATION = "/public";

	public void serve() {
		initExceptionHandler(ex -> {
			ex.printStackTrace();
			System.exit(1);
		});

		staticFileLocation(STATIC_FILES_LOCATION);

		UrlRepository<UrlPojo> urlRepository = new InMemoryUrlRepository<>(new DefaultDuplicateHandlerImpl<>());
		UrlFactory<UrlPojo> factory = new DefaultUrlFactory(new Sha256ShortingAlgorithm());
		UrlService<UrlPojo> service = new DefaultUrlServiceImpl<>(urlRepository, factory, new DefaultUrlValidator());

		TemplateController templateController = new TemplateController<>(service);
		ApiController apiController = new ApiController<>(service);

		service.save(factory.createUrl("http://www.google.pl", OffsetDateTime.MAX));

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(service::deleteExpired, 30, TimeUnit.MINUTES);
	}



}
