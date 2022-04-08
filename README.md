# DWP Software Engineering Test

## Table of Contents

* [Brief](##brief)
* [Built With](#built-with)
* [Requirements](#requirements)
* [Installation and Setup](#installation-and-setup)
    * [Docker](#via-docker)
    * [Manual](#manual-setup)
* [Live Demo](#live-demo)
* [Endpoints](#endpoints)
    * [Additional Endpoints](#additional-endpoints)
* [Testing](#testing)
* [Assumptions](#assumptions)

## Brief

Using the language of your choice please build your own API which calls the API at https://bpdts-test-app.herokuapp.com/
, and returns people who are listed as either living in London, or whose current coordinates are within 50 miles of
London.

## Built With

* Java 11
* Spring Boot
* Lombok
* OpenAPI + Swagger UI
* Maven
* JUnit 5
* Mockito
* MockMvc

## Requirements

The solution should be runnable with nothing more than Docker installed ([see installation instructions](#via-docker)),
however I'd also recommended having the following available:

* [Java 11+](https://www.oracle.com/java/technologies/downloads/)
* [Maven](https://maven.apache.org/)
* [IntelliJ](https://www.jetbrains.com/idea/)

## Installation and Setup

Clone or download the repository and then either run via Docker or manually as described below.

### Via Docker

To run via Docker execute the following in a terminal from the project root: `docker-compose up`

This will build a container, install the Maven dependencies and then run the Spring Boot app.

Once up and running the API will be accessible from: `http://localhost:8080`

### Manual Setup

Open the project in your IDE of choice. If using IntelliJ the dependencies in `pom.xml` should be resolved
automatically.

You can also install dependencies manually via: `mvn install`.

Once the dependencies are installed, run the project in your IDE and the API will be accessible
from: `http://localhost:8080`

## Live Demo

A [deployed version](https://dwp-technical-test-rd.herokuapp.com/) of the solution is available should there be any
issues installing or running locally.

_It's deployed to a free Heroku Dyno which will sleep if unused for around 30 minutes. As such, it could take ~15
seconds for the server to wake up if not accessed in a while._

* [Swagger Documentation](https://dwp-technical-test-rd.herokuapp.com/swagger-ui/index.html)
* [All Users in London or within 50 miles](https://dwp-technical-test-rd.herokuapp.com/api/v1/users/location?name=london)
* [All Users](https://dwp-technical-test-rd.herokuapp.com/api/v1/users)
* [Single User By ID](https://dwp-technical-test-rd.herokuapp.com/api/v1/users/1)

## Endpoints

OpenAPI and Swagger UI documentation has been generated for all API endpoints.

The Swagger UI will be available from the root `http://localhost:8080` once the service is running
or [here](https://dwp-technical-test-rd.herokuapp.com/) in the Live Demo.

The API endpoints supported are also described below.

---

**Description**: Returns all users in the provided location or within 50 miles.

**URL**: `/api/v1/users/location?name={locationName}`

**Example Request**: `http://localhost:8080/api/v1/users/location?name=london`

**Note**: _only requests for **London** will return data. [See Assumptions](#assumptions) below._

**Accepts**: `GET`

**Responses**:

`200` - on success

`400` - bad request if invalid location

---

### Additional Endpoints

Although not specifically required by the brief the following endpoints have also been included.

---

**Description**: Returns all users.

**URL**: `/api/v1/users`

**Example Request**: `http://localhost:8080/api/v1/users`

**Accepts**: `GET`

**Responses**: `200` - on success

---

**Description**: Returns a single user with given ID.

**URL**: `/api/v1/users/{userId}`

**Example Request**: `http://localhost:8080/api/v1/users/1`

**Accepts**: `GET`

**Responses**:

`200` - on success

`404` - not found if invalid ID

---

## Testing

[JUnit 5](https://junit.org/junit5/), [Mockito](https://site.mockito.org/),
and [MockMvc](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/servlet/MockMvc.html)
have been used to build a suit of unit, integration and acceptance tests for this solution.

You can run the tests from inside your IDE, or from the terminal via: `mvn test`

All tests should pass and IntelliJ's test runner reports **100%** coverage.

## Assumptions

The [Haversine formula](https://en.wikipedia.org/wiki/Haversine_formula) has been used to determine the distance between
the user's coordinates and the coordinates of London (or the chosen location).

Support has been added for more locations than just London ([_see
Location.java_](https://github.com/Package/dwp-technical-test/blob/master/src/main/java/uk/gov/dwp/users/domain/Location.java))
, however the source API does not appear to return any users in or nearby these locations. As such, calling the
endpoints with a location other than London will return no data!