# API Test Automation with Serenity, Cucumber, and RestAssured

This project is an API test automation framework using Serenity, Cucumber, and RestAssured. It is designed to automate and validate API endpoints with detailed reporting and configuration options.

## Project Structure
```
api_rest_assured/
├── .gitignore
├── pom.xml
├── README.md
├── src
│   ├── main
│   └── test
│       ├── java
│       │   ├── runners
│       │   │   └── CucumberTestRunner.java
│       │   └── steps
│       │       └── CommonSteps.java
│       ├── resources
│       │   ├── features
│       │   │   ├── pet_api_tests.feature
│       │   │   └── user_api_tests.feature
│       │   └── templates
│       │       └── postCreateWithList.json
│       └── serenity.properties
```


## Dependencies

The project uses the following dependencies:

- Serenity Core: `net.serenity-bdd:serenity-core:3.6.21`
- Serenity Cucumber: `net.serenity-bdd:serenity-cucumber:3.6.21`
- Serenity JUnit: `net.serenity-bdd:serenity-junit:3.6.21`
- Cucumber Java: `io.cucumber:cucumber-java:7.11.1`
- Cucumber JUnit: `io.cucumber:cucumber-junit:7.11.1`
- JUnit: `junit:junit:4.13.2`
- RestAssured: `io.test-assured:rest-assured:5.3.0`
- JSON: `org.json:json:20210307`
- JSON Path: `io.test-assured:json-path:5.3.0`
- XML Path: `io.test-assured:xml-path:5.3.0`
- SLF4J API: `org.slf4j:slf4j-api:1.7.36`

## Running Tests

### Running All Tests

To run all tests, use the following Maven command:

```sh
mvn clean test
```



Running Specific Scenarios
You can run specific scenarios using tags. The available tags are:

@allcases: Run all test cases

### @userpositivecase: 
Run positive test cases for user API

### @usernegativecase: 
Run negative test cases for user API

### @petpositivecase: 
Run positive test cases for pet API

### @petnegativecase: 
Run negative test cases for pet API

### To run tests with a specific tag, use the following Maven command:
```sh
mvn test -Dcucumber.filter.tags="@tagname"
```
### Replace @tagname with the desired tag, for example:
```sh
mvn test -Dcucumber.filter.tags="@userpositivecase"
```
### Running Specific Feature Files
To run a specific feature file, use the following Maven command:
```sh
mvn test -Dcucumber.features="src/test/resources/features/feature_file_name.feature"
```

## Serenity Reports
After running the tests, Serenity generates a detailed report. You can find the report in the serenity directory. Open the index.html file in a web browser to view the report.

### Configuration
The serenity.properties file contains configuration settings for Serenity:
```
serenity.outputDirectory=target/serenity-reports
serenity.report.encoding=UTF-8
serenity.project.name=API Test Automation
serenity.verbosity=verbose
serenity.take.screenshots=FOR_FAILURES
serenity.reports.show.releases=false
serenity.reports.show.issues=false
serenity.report.show.manual.tests=true
serenity.outputDirectory=target/site/serenity
```