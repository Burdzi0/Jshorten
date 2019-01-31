package shortener.url;

import shortener.url.algorithm.Sha256ShortingAlgorithm;
import shortener.url.algorithm.ShortingAlgorithm;
import shortener.url.controller.ApiController;
import shortener.url.controller.TemplateController;
import shortener.url.handler.DefaultDuplicateHandlerImpl;
import shortener.url.handler.DuplicateHandler;
import shortener.url.model.Url;
import shortener.url.repository.InMemoryUrlRepository;
import shortener.url.repository.UrlRepository;
import shortener.url.service.DefaultUrlServiceImpl;
import shortener.url.service.UrlService;
import shortener.url.service.factory.DefaultUrlFactory;
import shortener.url.service.factory.UrlCreator;
import shortener.url.service.factory.UrlFactory;
import shortener.url.service.validator.DefaultUrlValidator;
import shortener.url.service.validator.UrlValidator;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static spark.Spark.*;

class Server<T extends Url> {

	private static final String STATIC_FILES_LOCATION = "/public";
	private UrlRepository<T> repository;
	private UrlFactory<T> factory;
	private UrlService<T> service;
	private UrlCreator<T> creator;
	private ShortingAlgorithm<T> algorithm;
	private DuplicateHandler<T> duplicateHandler;
	private UrlValidator validator;

	public Server(UrlCreator<T> creator) {
		if (creator == null)
			throw new IllegalArgumentException("Creator is null!");
		this.creator = creator;
	}

	public void serve() {
		initExceptionHandler(ex -> {
			ex.printStackTrace();
			System.exit(1);
		});

		portConfig();

		staticFileLocation(STATIC_FILES_LOCATION);

		repository = prepareRepository();
		factory = prepareFactory();
		service = prepareService();

		TemplateController<T> templateController = new TemplateController<>(service);
		ApiController<T> apiController = new ApiController<>(service);

		deleteScheduledJob();
	}

	private UrlService<T> prepareService() {
		return service == null ? new DefaultUrlServiceImpl<>(repository, factory, prepareValidator()) : service;
	}

	private UrlValidator prepareValidator() {
		return validator == null ? new DefaultUrlValidator() : validator;
	}

	private UrlRepository<T> prepareRepository() {
		return repository == null ? new InMemoryUrlRepository<>(prepareDuplicateHandler()) : repository;
	}

	private DuplicateHandler<T> prepareDuplicateHandler() {
		return duplicateHandler == null ? new DefaultDuplicateHandlerImpl<>() : duplicateHandler;
	}

	private UrlFactory<T> prepareFactory() {
		return factory == null ? new DefaultUrlFactory<>(prepareAlgorithm(), creator) : factory;
	}

	private ShortingAlgorithm<T> prepareAlgorithm() {
		return algorithm == null ? new Sha256ShortingAlgorithm<>() : algorithm;
	}

	private void portConfig() {
		var env = System.getenv("PORT");
		port(env == null ? 4567 : Integer.parseInt(env));
	}

	private void deleteScheduledJob() {
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleWithFixedDelay(service::deleteExpired, 2, 10, TimeUnit.MINUTES);
	}

	public Server<T> repository(UrlRepository<T> repository) {
		this.repository = repository;
		return this;
	}

	public Server<T> service(UrlService<T> service) {
		this.service = service;
		return this;
	}

	public Server<T> factory(UrlFactory<T> factory) {
		this.factory = factory;
		return this;
	}

	public Server<T> shortingAlgorithm(ShortingAlgorithm<T> algorithm) {
		this.algorithm = algorithm;
		return this;
	}

	public Server<T> duplicateHandler(DuplicateHandler<T> duplicateHandler) {
		this.duplicateHandler = duplicateHandler;
		return this;
	}

	public Server<T> creator(UrlCreator<T> creator) {
		this.creator = creator;
		return this;
	}

	public Server<T> validator(UrlValidator validator) {
		this.validator = validator;
		return this;
	}
}
