package org.example.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.wordle.WindowManager;
import org.example.wordle.WordManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


public class W8Letters {

    private static final Logger LOG = LoggerFactory.getLogger(W8Letters.class);

    private final StringBuilder USER_WORD = new StringBuilder();
    private final Byte WORD_LENGTH = 8, TOTAL_ATTEMPTS = 6;
    private Byte ATTEMPT = 0;
    private boolean gameOver = false;

    private final Boolean COUNTRY_TOGGLE = WindowManager.getCountryToggleState();
    private final Boolean FILM_TOGGLE = WindowManager.getFilmToggleState();

    private final String WORD = WordManager.generateWord(WORD_LENGTH, COUNTRY_TOGGLE, FILM_TOGGLE);

    private static Label[][] labelsTable;

    @FXML
    Button mainButton;

    @FXML
    Label gameOverLabel;

    @FXML
    Label l00, l01, l02, l03, l04, l05, l06, l07,
            l10, l11, l12, l13, l14, l15, l16, l17,
            l20, l21, l22, l23, l24, l25, l26, l27,
            l30, l31, l32, l33, l34, l35, l36, l37,
            l40, l41, l42, l43, l44, l45, l46, l47,
            l50, l51, l52, l53, l54, l55, l56, l57;

    @FXML
    Button qButton, wButton, eButton, rButton, tButton, yButton, uButton, iButton, oButton, pButton,
            aButton, sButton, dButton, fButton, gButton, hButton, jButton, kButton, lButton, enieButton,
            zButton, xButton, cButton, vButton, bButton, nButton, mButton,
            deleteButton, enterButton;

    @FXML
    private void initialize(){
        labelsTable = new Label[][]{
                {l00, l01, l02, l03, l04, l05, l06, l07},
                {l10, l11, l12, l13, l14, l15, l16, l17},
                {l20, l21, l22, l23, l24, l25, l26, l27},
                {l30, l31, l32, l33, l34, l35, l36, l37},
                {l40, l41, l42, l43, l44, l45, l46, l47},
                {l50, l51, l52, l53, l54, l55, l56, l57}
        };

    }

    @FXML
    private void onMainButtonClick(){
        USER_WORD.delete(0, USER_WORD.length());
        WindowManager.showWindow("WORDLE | Home", "/fxml/mainwindow.fxml", "/styles/main.css", null, null);
    }

    @FXML
    private void onDeleteButtonClick(){
        LOG.info("Delete button was clicked");
        deleteLetterLogic();
    }

    private void deleteLetterLogic() {
        if (gameOver) return;

        if (!USER_WORD.isEmpty()){
            labelsTable[ATTEMPT][USER_WORD.length()-1].setText("");
            char letter = USER_WORD.charAt(USER_WORD.length()-1);
            USER_WORD.deleteCharAt(USER_WORD.length()-1);
            LOG.warn("Letter [{}] was deleted", letter);
        } else
            LOG.warn("The USER WORD is empty");
    }

    @FXML
    private void onEnterButtonClick() {
        LOG.info("Enter button was clicked");
        enterWordLogic();
    }

    private void enterWordLogic() {

        if (USER_WORD.isEmpty()) {
            LOG.warn("The word is empty");
        } else if (USER_WORD.length() < WORD_LENGTH) {
            LOG.warn("You must to complete the word");
        } else if(!gameOver){
            LOG.info("WORD --> [{}]", USER_WORD);

            //Method to verify the word and get the color list
            List<Color> colorList = WordManager.verifyWord(USER_WORD.toString(), WORD);

            //Method to fill the background labels with the result of the word
            fillBackground(ATTEMPT, colorList);

            if(USER_WORD.toString().equals(WORD)){
                LOG.info("You win! You guess de word --> [{}]", WORD);
                gameOver = true;
                gameOverLabel.setText("You Win! The word was successfully entered!");
                return;
            }

            ATTEMPT++;

            if(!ATTEMPT.equals(TOTAL_ATTEMPTS)){
                USER_WORD.delete(0, USER_WORD.length());
            } else {
                LOG.info("Game Over. The word was [{}]", WORD);
                gameOver = true;
                gameOverLabel.setText("You were close... The word was [" + WORD + "]");
            }

        }
    }

    private void fillBackground(Byte attempt, List<Color> colorList) {

        Timeline timeline = new Timeline();

        for (int i = 0; i < colorList.size(); i++) {
            String cssColor = colorList.get(i).toString().replace("0x", "#");
            Label label = labelsTable[attempt][i];

            // Crear una KeyFrame para cada label
            int index = i; // Necesario para usar en una expresión lambda
            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(200 * (i + 1)), // Intervalo progresivo
                    event -> {
                        // Añadir efecto de transparencia
                        label.setStyle("-fx-background-color: " + cssColor + "; -fx-opacity: 0.0;");
                        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), label);
                        fadeIn.setFromValue(0.0);
                        fadeIn.setToValue(1.0);
                        fadeIn.play();
                    }
            );
            // Agregar el KeyFrame al Timeline
            timeline.getKeyFrames().add(keyFrame);
        }

        // Reproducir la animación
        timeline.play();

        /*
        for (int i = 0; i < colorList.size(); i++) {
            String cssColor = colorList.get(i).toString().replace("0x", "#");
            labelsTable[attempt][i].setStyle("-fx-background-color: " + cssColor + ";");
        }
        */
    }

    @FXML
    private void onLetterButtonClick(ActionEvent event){

        //Si la palabra ya tiene la máxima longitud, no se escribe más
        if(USER_WORD.length() == WORD_LENGTH) return;
        //Si los intentos ya igualaron/superaron al total NO prosigue
        if (ATTEMPT >= TOTAL_ATTEMPTS) return;
        //si el juego acabo NO prosigue
        if(gameOver) return;

        Button clickedButton = (Button)event.getSource();
        String letter = clickedButton.getText();

        LOG.info("Letter clicked: [{}]", letter);

        //Añade la letra al StringBuilder
        USER_WORD.append(letter);

        //Escribe la letra en el label correspondiente
        labelsTable[ATTEMPT][USER_WORD.length()-1].setText(letter);

    }

    public void handleKeyPressed(KeyEvent event){

        LOG.info("[{}] key pressed",event.getCode());

        String letter = event.getCode().toString();

        //Si la palabra ya tiene la máxima longitud, y se intenta escribir una letra:
        if(USER_WORD.length() == WORD_LENGTH && !letter.equals("ENTER") && !letter.equals("BACK_SPACE")) return;
        //Si los intentos ya igualaron/superaron al total NO prosigue
        if (ATTEMPT >= TOTAL_ATTEMPTS) return;
        //si el juego acabo NO prosigue
        if(gameOver) return;

        if(letter.matches("[a-zA-Z]")) {
            USER_WORD.append(letter.toUpperCase());
            //Escribe la letra en el label correspondiente
            labelsTable[ATTEMPT][USER_WORD.length()-1].setText(letter);
        } else if(letter.equals("ENTER")){
            enterWordLogic();
        } else if(letter.equals("BACK_SPACE")){
            deleteLetterLogic();
        }

    }

    public void setCountryToggle(boolean countryToggle){

    }

    public void setFilmToggle(boolean filmToggle){

    }

}
