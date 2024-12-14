package org.js.programmingwindowapplications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(proxyBeanMethods = false)
public class Application {
    public static void main(String[] args) {
//        SpringApplication.run(FXAppLauncher.class, args);
//        FXAppLauncher.launchApp(args);
        javafx.application.Application.launch(FXAppLauncher.class, args);
    }
}
