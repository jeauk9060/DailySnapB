package com.dailysnap.summation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SummationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SummationApplication.class, args);
	}

}
