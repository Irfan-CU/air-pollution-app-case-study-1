package com.cgiair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableMongoRepositories("com.cgiair.repository")
@ComponentScan("com")
@EnableEurekaClient

public class FavoriteApplication {
    public static void main(String[] args) { SpringApplication.run(FavoriteApplication.class, args); }
}
