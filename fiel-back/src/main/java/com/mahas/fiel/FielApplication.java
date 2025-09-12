package com.mahas.fiel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.mahas")
@EntityScan(basePackages = "com.mahas.domain")
public class FielApplication {

	public static void main(String[] args) {
		SpringApplication.run(FielApplication.class, args);
	}
}
