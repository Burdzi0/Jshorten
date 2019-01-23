# Jshorten #

### Customizable url shortener that can run anywhere ###

#### Description ###

Jshorten is an url shortener made purely in Java 11.  This project is supposed to be an easily-extendable url shortener to make the job as fast as You need it.
This project **isn't finished yet**. It needs unit testing and refactoring. 

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

```bash
gradle jar
java -jar build/libs/UrlShortener-1.0-SNAPSHOT.jar 
```

The app is ready to be deployed to Heroku. It uses either environment variable `PORT` (as Heroku requires)  whenever possible, otherwise default `Spark ` port: `4567`

#### Customization 

The project is being developed with priority to be fully extendable and customizable. At this moment there customization isn't as easy as planned. 

A short note about customization will be added shortly after refactoring.