# About
This little project is Martin's solution to Belong's code test.
It is a fully operating application written in Java 11 and heavily utilizing SpringBoot.
The data is stored in in-memory H2 database and are accessed via JPA and SpringData.
The application provides REST interface for manipulating Phone numbers.

# Run&Play
The application can be started by running main method in class `CustomerServiceApplication`
or it can be started from command line by executing `mvn spring-boot:run`

Once the application is up and running the Swagger UI can be accessed on `http://localhost:8080/swagger-ui`

Class `TestDataService` will create two Customers, and some Phone numbers in DB after boot. 
This data can be accessed in Swagger UI. There's one non-active number which can be activated in Swagger UI.

# Tests
Run Unit Tests:
`mvn clean test`

Run Unit and Integration Tests:
`mvn clean verify`
