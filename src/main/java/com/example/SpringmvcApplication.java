package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication

public class SpringmvcApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		
		SpringApplication.run(SpringmvcApplication.class, args);
	}
}
