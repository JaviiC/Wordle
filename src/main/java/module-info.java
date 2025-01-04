module org.example.wordle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.slf4j;

    exports org.example.wordle;
    exports org.example.controllers;
    opens org.example.wordle to javafx.fxml;
    opens org.example.controllers to javafx.fxml;
}