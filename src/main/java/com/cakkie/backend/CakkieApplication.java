package com.cakkie.backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;


@SpringBootApplication
public class CakkieApplication {
	@PostConstruct
	public void init() {
		// Set JVM default time zone to Vietnam
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		System.out.println("Time zone set to Asia/Ho_Chi_Minh");
	}

	public static void main(String[] args) {
		SpringApplication.run(CakkieApplication.class, args);
	}

}
