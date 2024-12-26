package org.example.wordle;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.controllers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WindowManager {

    private static final Logger LOG = LoggerFactory.getLogger(WindowManager.class);

    private static Stage primaryStage;
    private static Boolean countryToggleState;
    private static Boolean filmToggleState;

    protected static void setStage(Stage stage) {
        LOG.info("Setting the primary stage to [{}]", stage);
        primaryStage = stage;
    }

    public static void showWindow(String title, String fxmlPath, String cssPath, Boolean countryToggle, Boolean filmToggle) {

        countryToggleState = countryToggle; filmToggleState = filmToggle;

        LOG.info("Showing new window --> [{}] in [{}]...", title, fxmlPath);

        if (primaryStage == null) {
            LOG.error("The primaryStage is not set. Call setPrimaryStage() before using showView().");
            throw new IllegalStateException("PrimaryStage is not configured");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
        Scene scene;

        try {

            scene = new Scene(fxmlLoader.load(), 650, 740);
            scene.getStylesheets().add(MainApplication.class.getResource(cssPath).toExternalForm());

            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    Object object = fxmlLoader.getController();
                    instanceRespectiveWindow(event, object);
                }

            });

            primaryStage.setTitle(title);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void instanceRespectiveWindow(KeyEvent event, Object object) {

        if(object instanceof W3Letters){
            W3Letters w3 = (W3Letters) object;
            w3.handleKeyPressed(event);
        } else if (object instanceof W4Letters) {
            W4Letters w4 = (W4Letters) object;
            w4.handleKeyPressed(event);
        } else if (object instanceof W5Letters) {
            W5Letters w5 = (W5Letters) object;
            w5.handleKeyPressed(event);
        } else if (object instanceof W6Letters) {
            W6Letters w6 = (W6Letters) object;
            w6.handleKeyPressed(event);
        } else if (object instanceof W7Letters) {
            W7Letters w7 = (W7Letters) object;
            w7.handleKeyPressed(event);
        } else if (object instanceof W8Letters) {
            W8Letters w8 = (W8Letters) object;
            w8.handleKeyPressed(event);
        } else if (object instanceof W9Letters) {
            W9Letters w9 = (W9Letters) object;
            w9.handleKeyPressed(event);
        } else if (object instanceof W10Letters) {
            W10Letters w10 = (W10Letters) object;
            w10.handleKeyPressed(event);
        }

    }

    public static Boolean getCountryToggleState(){
        return countryToggleState;
    }

    public static Boolean getFilmToggleState(){
        return filmToggleState;
    }

}
