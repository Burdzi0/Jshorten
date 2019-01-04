package shortener.url;

import shortener.url.algorithm.Sha256ShortingAlgorithm;
import shortener.url.controller.ApiController;
import shortener.url.controller.TemplateController;
import shortener.url.handler.DefaultDuplicateHandlerImpl;
import shortener.url.repository.InMemoryUrlRepository;
import shortener.url.repository.UrlRepository;
import shortener.url.service.DefaultUrlServiceImpl;
import shortener.url.service.UrlFactory;
import shortener.url.service.UrlService;

import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.initExceptionHandler;
import static spark.Spark.staticFiles;

public class Server {

	public static final String STATIC_FILES_LOCATION = "/public";

	public void serve() {
		initExceptionHandler(ex -> {
			ex.printStackTrace();
			System.exit(1);
		});

		staticFiles.location(STATIC_FILES_LOCATION);

		UrlRepository urlRepository = new InMemoryUrlRepository(new DefaultDuplicateHandlerImpl());
		UrlFactory factory = new UrlFactory(new Sha256ShortingAlgorithm());
		UrlService service = new DefaultUrlServiceImpl(urlRepository, factory);

		TemplateController templateController = new TemplateController(service);
		ApiController apiController = new ApiController(service);

		service.save(factory.createUrl("http://www.google.pl", OffsetDateTime.MAX));

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(service::deleteExpired, 30, TimeUnit.MINUTES);
	}



}
