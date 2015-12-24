package com.smf.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by cipriach on 07.12.2015.
 */
@SpringBootApplication
public class ApplicationStarter extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationStarter.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class, args);
    }

}
