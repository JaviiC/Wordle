package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.ToggleSwitch;
import org.example.wordle.WindowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainWindow {

    private static final Logger LOG = LoggerFactory.getLogger(MainWindow.class);

    @FXML
    private ToggleGroup grupoNumeroLetras;

    @FXML
    private ToggleSwitch toggleSwitchCountries, toggleSwitchFilms;

    @FXML
    private ToggleButton toggleLetras3, toggleLetras4, toggleLetras5, toggleLetras6, toggleLetras7, toggleLetras8, toggleLetras9, toggleLetras10;

    private ToggleButton ultimoSeleccionado; // Para rastrear el último botón seleccionado

    private List<ToggleButton> LISTA_TOGGLE_BUTTONS = null;

    @FXML
    public void initialize() {

        LISTA_TOGGLE_BUTTONS = List.of(toggleLetras3, toggleLetras4, toggleLetras5, toggleLetras6, toggleLetras7, toggleLetras8, toggleLetras9, toggleLetras10);

        // Escucha cambios en el grupo
        grupoNumeroLetras.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                // Si no hay ninguno seleccionado, volvemos al último seleccionado
                grupoNumeroLetras.selectToggle(oldToggle);
            } else {
                // Guardamos el último seleccionado
                ultimoSeleccionado = (ToggleButton) newToggle;
            }
        });
    }

    @FXML
    private void onPlayClick(){

        LOG.debug("The play button has been clicked");

        ToggleButton selected = LISTA_TOGGLE_BUTTONS.stream()
                .filter(ToggleButton::isSelected)
                .findFirst()
                .orElse(null);

        if(selected != null){
            LOG.debug("Button [{}] selected", selected.getText());

            String windowCss = "/styles/window.css";
            if(selected == toggleLetras3){
                WindowManager.showWindow("WORDLE | 3 Letters", "/fxml/w3letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            } else if (selected == toggleLetras4) {
                WindowManager.showWindow("WORDLE | 4 Letters", "/fxml/w4letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            } else if(selected == toggleLetras5){
                WindowManager.showWindow("WORDLE | 5 Letters", "/fxml/w5letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            } else if(selected == toggleLetras6){
                WindowManager.showWindow("WORDLE | 6 Letters", "/fxml/w6letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            } else if(selected == toggleLetras7){
                WindowManager.showWindow("WORDLE | 7 Letters", "/fxml/w7letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            } else if(selected == toggleLetras8){
                WindowManager.showWindow("WORDLE | 8 Letters", "/fxml/w8letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            } else if(selected == toggleLetras9){
                WindowManager.showWindow("WORDLE | 9 Letters", "/fxml/w9letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            } else if (selected == toggleLetras10) {
                WindowManager.showWindow("WORDLE | 10 Letters", "/fxml/w10letters.fxml", windowCss, toggleSwitchCountries.isSelected(), toggleSwitchFilms.isSelected());
            }

        }

    }

    @FXML
    private void toggleSwitchEvent(){

        LOG.debug("Toggle switch event received");

        if (toggleSwitchCountries.isSelected() || toggleSwitchFilms.isSelected()){
            toggleLetras3.setDisable(true);

            if (toggleLetras3.isSelected()) toggleLetras4.setSelected(true);

            if(toggleSwitchCountries.isSelected()) LOG.debug("Country toggle switch is activated");
            else LOG.debug("Country toggle switch is desactivated");

            if(toggleSwitchFilms.isSelected()) LOG.debug("Country toggle is activated");
            else LOG.debug("Country toggle switch is desactivated");

        } else {
            toggleLetras3.setDisable(false);
        }

    }

}