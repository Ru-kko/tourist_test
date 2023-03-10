package com.tourist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * It's a Spring Boot application to book visits to different cities 
 * @author Ru-kko
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan("com.tourist")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
