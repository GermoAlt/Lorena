package com.buffer.lorena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.LogManager;
import java.util.logging.Logger;

@EnableJpaRepositories(basePackages = "com.buffer.lorena.db.entity")
@ComponentScan("com.buffer.lorena.db")
@SpringBootApplication
public class LorenaApplication {
	public static void main(String[] args) {
		SpringApplication.run(LorenaApplication.class, args);
		System.out.println("\n ITS ALIVE! \n");
	}

}
