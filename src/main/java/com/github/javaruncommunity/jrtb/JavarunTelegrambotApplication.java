package com.github.javaruncommunity.jrtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JavarunTelegrambotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavarunTelegrambotApplication.class, args );
	}

}
