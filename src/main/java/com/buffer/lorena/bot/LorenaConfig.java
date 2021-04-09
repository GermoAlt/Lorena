package com.buffer.lorena.bot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Lorena config.
 */
@Configuration
@ConfigurationProperties(prefix = "lorena")
public class LorenaConfig {
    private String token;
}
