package org.js.programmingwindowapplications;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Main2 extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // Launch JavaFX application
        launch(args);
    }

    @Override
    public void init() throws Exception {
        // Start Spring Boot in a separate thread
        springContext = SpringApplication.run(AnimalShelterApplication.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Start the JavaFX application, and the Spring Boot context is already initialized
        System.out.println("Starting JavaFX UI...");
        // Your JavaFX setup and UI code here
    }

    @Override
    public void stop() throws Exception {
        // Ensure that Spring Boot shuts down gracefully
        springContext.close();
    }

    @SpringBootApplication
    public static class AnimalShelterApplication {
        public static void main(String[] args) {
            // This method is not used because Spring Boot is already launched by the JavaFX init method
        }
    }
}
