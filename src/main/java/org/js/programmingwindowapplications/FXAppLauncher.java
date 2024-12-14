package org.js.programmingwindowapplications;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FXAppLauncher extends javafx.application.Application {
    private static final Logger logger = Logger.getLogger(FXAppLauncher.class.getName());

    private static ApplicationContext springContext;

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(AnimalShelterApplication.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.log (Level.INFO  ,"Hello are you here?");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/js/programmingwindowapplications/animalshelterUI/login-panel.fxml"));
//        loader.setControllerFactory(springContext::getBean);



        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Spring Boot JavaFX Application");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (springContext instanceof AutoCloseable closeable) {
            closeable.close();
        }
        Platform.exit();
    }


    public static void launchApp(String[] args) {
        springContext = new AnnotationConfigApplicationContext(Application.class);
        launch(args);
    }
    @SpringBootApplication
    public static class AnimalShelterApplication {
        public static void main(String[] args) {
            // This method is not used because Spring Boot is already launched by the JavaFX init method
        }
    }

}

