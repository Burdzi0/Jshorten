# Jshorten #

### Customizable url shortener that can run anywhere ###

[![Build Status](https://travis-ci.org/Burdzi0/Jshorten.svg?branch=master)](https://travis-ci.org/Burdzi0/Jshorten)
[![codecov](https://codecov.io/gh/Burdzi0/Jshorten/branch/master/graph/badge.svg)](https://codecov.io/gh/Burdzi0/Jshorten)

#### Description ###

Jshorten is an url shortener made purely in Java 11.  This project is supposed to be an easily-extendable url shortener to make the job as fast as You need it.
It needs unit testing.

#### Frameworks and libraries ####

* [SparkJava](http://sparkjava.com/)  - lightweight web framework
* Thymeleaf - frontend template engine
* org.json - json library
* org.slf4j - logging library
* Gradle - build tool

#### Running the app ####

Requirements:

* Java 11
* Gradle

In order to start app simply clone it, enter the directory of cloned project and execute in terminal:

```shell
gradle jar
java -jar build/libs/UrlShortener-1.0-SNAPSHOT.jar 
```

The app is ready to be deployed to Heroku. It uses either environment variable `PORT` (as Heroku requires)  whenever possible, otherwise default `Spark ` port: `4567`

#### Customization 

In order to create your own version of this project you have to create class which would extend `Url` abstract class. You can use default implementation `UrlPojo`.
`Server` class uses default implementations or objects passed using methods as `repository(arg), service(arg), ...`.

| Class | Default implementation  | Arguments |
|-------|-------------------------|-----------|
| Url | UrlPojo |`String` url, `OffsetDateTime` expirationTime | 
| UrlRepository<T>  | InMemoryUrlRepository<T> | DuplicateHandler<T> |
| DuplicateHandler<T> | DefaultDuplicateHandlerImpl<T> | X |
| UrlFactory<T> | DefaultUrlFactory<T> | ShortingAlgorithm<T> algorithm, UrlCreator<T> creator |
| UrlService<T> | DefaultUrlServiceImpl | UrlRepository<T> repository, UrlFactory<T> factory, UrlValidator validator |
| UrlValidator | DefaultUrlValidator | X |


The default version ([available here](https://jshorten.herokuapp.com/)).
