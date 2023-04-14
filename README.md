
# Security User Registration

This project was already make to apply knowledge's of Spring Boot 3.0, TDD, Clean Code, Spring boot 3.0, Exception Handling, Open API, Basic Hibernate, Jwt token Generate, Password Encoder, Jacoco Unit Test (Junit5).

# How to run
Go to download git command project then with java 17 installed you can go to root of project and run this command (gradle bootRun) and go to urls (Open Api) to view rest endpoints usage:

- [@Project URL Base](http://localhost:8080/swagger-ui/index.html#/user-controller/create)
Using postman you can test endpoints, this project uses H2 in memory database.
- [Visualizer database] (http://localhost:8080/h2/login.jsp)
- database: jdbc:h2:mem:user
- user: h2
- password: h2
- To Run unit test, start command line and run this code (gradle test jacocoTestReport jacocoTestCoverageVerification) this command is not necessary install gradle because it has gradle on project, when command is completed you can go on project root folder to (build/jacocoHtml/index.html) to review coverage of code (unit test), actually is configured to test 0.95% of code.  
## Acknowledgements

- [Spring Boot 3.0](https://spring.io/blog/2022/05/24/preparing-for-spring-boot-3-0)
- [Open Api](https://springdoc.org/v2/)
- [TDD](https://www.paradigmadigital.com/dev/tdd-como-metodologia-de-diseno-de-software/)


## Appendix

This microservice can you use to learn a little bit about how  to make REST Api endpoints with Spring boot.



## Authors

- [@GandhyCuasapas](https://github.com/LeandriT)


## Badges

Licences Spring Boot, Open Api

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)

