package com.bilgeadamkampanya.kampanya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@SpringBootApplication
@RestControllerAdvice
public class KampanyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KampanyaApplication.class, args);
	}

}
