package com.mike.healthmadeeasy.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class StartUpConfigDump {

//    @Bean
//    ApplicationRunner dumpDbConfig(Environment env) {
//
//        return args -> {
//            System.out.println("=== ACTIVE PROFILES ===");
//            for (String p : env.getActiveProfiles()) System.out.println(" - " + p);
//
//            System.out.println("=== DB CONFIG (sanity) ===");
//            System.out.println("spring.datasource.url=" + env.getProperty("spring.datasource.url"));
//            System.out.println("spring.datasource.username=" + env.getProperty("spring.datasource.username"));
//
//            String pw = env.getProperty("spring.datasource.password");
//            System.out.println("spring.datasource.password.present=" + (pw != null && !pw.isBlank()));
//            System.out.println("spring.datasource.password.length=" + (pw == null ? 0 : pw.length()));
//        };
//
//    }

}
