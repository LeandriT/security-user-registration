# Security User Registration

The Security User registration project was carried out with technologies (Java 17, Spring Boot 3.0.5, Hibernate, H2 database, Docker, OpenApi, Junit5, Jacocot test verification) development practices such as TDD, Clean Code were used, SOLID.
The project consists of registering user data with a list of telephone numbers at the same time, a JWT token is generated, the email and password are validated. The CRUD of the User model was performed with methods such as (POST (create user), GET (paginate user), GET (show user), PUT (update user)).
# How to run

To run the project you should first clone the project (already have git binaries installed on your local host), proceed with the commands:
1. gradle clean build
2. gradle bootRun (if you want to test the api with java)
3. docker build -t user-registration . (package the application in a docker container)
4. docker run -p 8080:8080 user-registration (to run with docker and expose port 8080 for your tests locally).
5. gradle test jacocoTestReport jacocoTestCoverageVerification (to verify the unit test coverage percentage this generates a path (build/jacocoHtml/index.html) inside the project root which shows the code that has been covered with the tests).
6. You want to view the in-memory database h2 (http://localhost:8080/h2/login.jsp) database url:
   jdbc:h2:mem:user, user: h2, password: h2
7. To view, test the endpoints that it has through openapi go to the http://localhost:8080/swagger-ui/index.html#/user-controller/create
8. 

## Acknowledgements

- [Spring Boot 3.0](https://spring.io/blog/2022/05/24/preparing-for-spring-boot-3-0)
- [Open Api](https://springdoc.org/v2/)
- [TDD](https://semaphoreci.com/blog/test-driven-development)
## API Reference

#### Get all users

```http
  GET /v1/users?page=0&size=10
```

| Parameter | Type  | Description                       |
|:----------|:------|:----------------------------------|
| `page`    | `int` | **By default 0**. Example: 0,1,2       |
| `size`    | `int` | **By default 20**. Example: 0,1,2 |

#### Get User

```http
  GET /v1/users/${uuid}
```

| Parameter | Type   | Description                       |
|:----------|:-------|:----------------------------------|
| `uuid`    | `UUID` | **Required**. Id of user to fetch |

#### Post User

```http
  POST /v1/users
  with body ${user}
```
| Parameter | Type     | Description        |
|:----------|:---------|:-------------------|
| `name`    | `string` | Name of user       |
| `email`    | `string` | email of user       |
| `password`    | `string` | password of user       |
| `phones`    | `list`   | list phone of user |



| Parameter | Type     | Description        |
|:----------|:---------|:-------------------|
| `number`    | `string` | number of phone    |
| `city_code`    | `unt`    | city_code of phone |
| `country_code`    | `int`    | country_code of phone       |



#### PUT User

```http
  POST /v1/users/${uuid}
  with body ${user}
```
| Parameter | Type     | Description        |
|:----------|:---------|:-------------------|
| `name`    | `string` | Name of user       |
| `email`    | `string` | email of user       |
| `password`    | `string` | password of user       |
| `phones`    | `list`   | list phone of user |



| Parameter | Type     | Description        |
|:----------|:---------|:-------------------|
| `number`    | `string` | number of phone    |
| `city_code`    | `unt`    | city_code of phone |
| `country_code`    | `int`    | country_code of phone       |




## Appendix

This microservice can you use to learn a little bit about how to make REST Api endpoints with Spring boot.

## Authors

- [@GandhyCuasapas](https://github.com/LeandriT)
