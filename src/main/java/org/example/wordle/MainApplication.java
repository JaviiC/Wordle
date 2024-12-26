package org.example.wordle;

import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainApplication extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        LOG.info("Initializing application...");
        WindowManager.setStage(stage);
        WindowManager.showWindow("WORDLE | Home", "/fxml/mainwindow.fxml", "/styles/main.css", null, null);
    }

    @Override
    public void stop(){
        LOG.info("Close the application...");
    }

    public static void main(String[] args) {
        launch();
    }

}