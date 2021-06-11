package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import java.util.Collections;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableSpringDataWebSupport
@EnableJpaRepositories(basePackages = "com.example.webapp.repositories")
public class WebappApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WebappApplication.class);
        springApplication.setDefaultProperties(Collections
                .singletonMap("server.port", "4200"));
        springApplication.run(args);
    }

}
