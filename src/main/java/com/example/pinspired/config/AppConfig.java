package com.example.pinspired.config;

import com.example.pinspired.repositories.initializer.UserDataInitializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ApplicationRunner initializer(UserDataInitializer initializer) {
        return args -> initializer.initialize();
    }

    

}
