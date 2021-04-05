package com.buffer.lorena.bot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lorena")
public class LorenaConfig {
    private String token;
}
