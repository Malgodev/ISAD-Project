package com.malgo.malgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.malgo.malgo.repository")
public class MalgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MalgoApplication.class, args);
	}

}
