package com.marveloper.starter.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class StarterSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(StarterSpringApplication.class, args);
	}
}
