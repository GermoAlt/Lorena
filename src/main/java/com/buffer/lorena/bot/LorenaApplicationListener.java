package com.buffer.lorena.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class LorenaApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    Logger logger = LogManager.getLogger(LorenaApplicationListener.class);
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("ITS ALIVE!");
    }
}
