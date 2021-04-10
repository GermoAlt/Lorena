package com.buffer.lorena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * The type Lorena application.
 */
@SpringBootApplication
public class LorenaApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
		SpringApplication.run(LorenaApplication.class, args);
	}
}
