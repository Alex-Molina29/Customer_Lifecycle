package com.bancobogota.customersKata.shared.infrastructure;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupLogger.class);

    @Value("${app.log-message}")
    private String logMessage;

    @Value("${server.port}")
    private String port;

    @Override
    public void run(String... args) {
        log.info("=== {} === (puerto: {})", logMessage, port);
    }

}
