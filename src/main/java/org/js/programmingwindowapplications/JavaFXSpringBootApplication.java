package org.js.programmingwindowapplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(proxyBeanMethods = false)
public class JavaFXSpringBootApplication {
    public static void main(String[] args) {
        FXAppLauncher.launchApp(args);
//        SpringApplication.run(Test.class, args);
    }
}
