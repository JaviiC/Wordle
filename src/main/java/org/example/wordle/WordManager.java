package org.example.wordle;

import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WordManager {

    private static final Logger LOG = LoggerFactory.getLogger(WordManager.class);

    public static List<Color> verifyWord(String userWord, String originalWord){

        LOG.debug("Verifying user word...");

        List<Color> colorList = new ArrayList<>();
        fillOutColorList(userWord, originalWord, colorList);

        for (int i = 0; i < userWord.length(); i++) {
            char letter = userWord.charAt(i);

            /*
            --- LÓGICA PARA PINTAR DE AMARILLO LAS LETRAS ---
            Se recuperan los números de las posiciones donde la letra aparece "letter" aparece tanto en la palabra ORIGINAL como USUARIO.
                (solo si las posiciones no son verdes, ya que en tal caso las letras ya estarían en su posición)
            Recupera el mínimo del nº de apariciones de la letra "letter" entre la palabra ORIGINAL y la palabra del USUARIO (minPositions).
             */

            List<Integer> positionUserLetters = getPositions(colorList, userWord, letter);
            List<Integer> positionOriginalLetters = getPositions(colorList, originalWord, letter);
            int minPositions = Math.min(positionUserLetters.size(), positionOriginalLetters.size());

            //Bucle "minPositions" veces sobre las posiciones de la palabra de USUARIO para y cambiar al amarillo "minPositions" veces
            for (int j = 0; j < minPositions; j++) {
                colorList.set(positionUserLetters.get(j), Color.YELLOW);
            }

        }
        return colorList;
    }

    private static void fillOutColorList(String userWord, String originalWord, List<Color> colorList) {
        LOG.debug("Filling out the color list whit GREEN and GREY");
        for (int i = 0; i < userWord.length(); i++) {
            if(userWord.charAt(i) == originalWord.charAt(i))
                colorList.add(Color.GREEN);
            else
                colorList.add(Color.LIGHTSLATEGREY);
        }
    }

    private static List<Integer> getPositions(List<Color> colorList, String originalWord, char letter){

        LOG.debug("Getting positions of letter [{}]", letter);
        List<Integer> positionList = new ArrayList<>();

        for (int i = 0; i < originalWord.length(); i++) {
            if(originalWord.charAt(i) == letter && colorList.get(i) != Color.GREEN)
                positionList.add(i);
        }
        LOG.trace("Positions collected: [{}]", positionList);
        return positionList;
    }

    public static String generateWord(Byte wordLength, Boolean countryToggle, Boolean filmToggle){
        return FileManager.loadWordFromFile(wordLength, countryToggle, filmToggle);
    }

}