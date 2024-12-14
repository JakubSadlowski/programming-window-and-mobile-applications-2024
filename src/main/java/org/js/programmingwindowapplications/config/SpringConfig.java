package org.js.programmingwindowapplications.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public Dupa getDupa(){
        return new Dupa();
    }

    public static class Dupa {

    }

}
