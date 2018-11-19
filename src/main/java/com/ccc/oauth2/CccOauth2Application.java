package com.ccc.oauth2;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableJpaRepositories("com.ccc.oauth2.controller")
@EntityScan(basePackages = "com.ccc.oauth2.dto")
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
public class CccOauth2Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CccOauth2Application.class, args);
		System.out.println("Oauth2 Application Started");
	}
	
	@PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
    }
}
