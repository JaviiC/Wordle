package org.example.wordle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FileManager {

    private static final Logger LOG = LoggerFactory.getLogger(WordManager.class);
    private static final Random random = new Random();

    private static final String WORDS_PATH_FILE = "/words.txt";

    private static final String SEP = " ";

    public static String loadWordFromFile(Byte wordLength, Boolean countryToggle, Boolean filmToggle) {

        LOG.debug("Generating a word with length [{}]...", wordLength);

        String word;
        String[] commonWords = null, countries = null, films = null;
        boolean first = true, second = true, third = true;

        try (InputStream inputStream = FileManager.class.getResourceAsStream(WORDS_PATH_FILE);
             BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream))){

            String line;
            while ((line = bf.readLine()) != null){

                String[] lineSplited = line.split(SEP);

                if(first) {

                    if(lineSplited[0].equals(String.valueOf(wordLength))) {
                        commonWords = lineSplited;
                        commonWords = Arrays.copyOfRange(commonWords, 1, commonWords.length);
                    }
                    if(lineSplited[0].equals("10")) first = false;

                } else if(second) {

                    if(lineSplited[0].equals(String.valueOf(wordLength))) {
                        countries = lineSplited;
                        countries = Arrays.copyOfRange(countries, 1, countries.length);
                    }
                    if(lineSplited[0].equals("10")) second = false;


                } else if (third) {

                    if(lineSplited[0].equals(String.valueOf(wordLength))) {
                        films = lineSplited;
                        films = Arrays.copyOfRange(films, 1, films.length);
                    }
                    if(lineSplited[0].equals("10")) third = false;

                }

            }

            LOG.debug("Options selected: Countries --> [{}], Films --> [{}]", countryToggle, filmToggle);

            if (countryToggle || filmToggle) {

                if (countryToggle && filmToggle) {
                    List<String> words = new ArrayList<>();
                    words.addAll(Arrays.asList(countries));
                    words.addAll(Arrays.asList(films));
                    word = words.get(random.nextInt(words.size()));
                } else if (countryToggle) {
                    word = countries[random.nextInt(countries.length-1)];
                } else {
                    word = films[random.nextInt(films.length-1)];
                }

            } else {
                word = commonWords[random.nextInt(commonWords.length-1)];
            }

        } catch (FileNotFoundException e) {
            LOG.error("The file [{}] was not found", WORDS_PATH_FILE);
            return null;
        } catch (IOException e) {
            LOG.error("Error loading file [{}]", WORDS_PATH_FILE);
            return null;
        }
        LOG.info("Word generated --> [{}]", word);
        return word;
    }

}
