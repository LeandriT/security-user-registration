package com.security.securityuserregistration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@Slf4j
public class SecurityUserRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityUserRegistrationApplication.class, args);
    }

}
