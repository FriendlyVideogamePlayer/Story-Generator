import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class wordGenerator {
    // Variables used to store the text file locations to be read later.
    public static String word_adjectives_positive = "src/word_txt/word_adjectives_positive.txt";
    public static String word_adjectives_negative = "src/word_txt/word_adjectives_negative.txt";
    public static String word_connectives = "src/word_txt/word_connectives.txt";
    public static String word_feeling_positive = "src/word_txt/word_feeling_positive.txt";
    public static String word_lives = "src/word_txt/word_lives.txt";
    public static String word_feeling_negative = "src/word_txt/word_feeling_negative.txt";
    public static String word_location = "src/word_txt/word_location.txt";
    public static String word_location_description_negative = "src/word_txt/word_location_description_negative.txt";
    public static String word_location_description_positive = "src/word_txt/word_location_description_positive.txt";
    public static String word_meet = "src/word_txt/word_meet.txt";
    public static String word_names = "src/word_txt/word_names.txt";
    public static String word_nouns = "src/word_txt/word_nouns.txt";
    public static String word_species = "src/word_txt/word_species.txt";
    public static String word_time = "src/word_txt/word_time.txt";
    public static String word_verbs = "src/word_txt/word_verbs.txt";
    public static String word_verb_negative = "src/word_txt/word_verb_negative.txt";
    public static String word_visit = "src/word_txt/word_visit.txt";
    public static String word_weather_positive = "src/word_txt/word_weather_positive.txt";
    public static String word_weather_negative = "src/word_txt/word_weather_negative.txt";
    // List used to store the words taken from one of the above word lists
    List<String> words = null;

    // Main method, simply runs the word generation method.
    public static void main(String[] args) {
        wordGenerator gen = new wordGenerator();
        gen.wordGeneration(word_names);

    }

    // Word generator. Generates a word at random from the selected text file.
    public String wordGeneration(String fileType) {
        String returnedWord = "";
        // Try to read the text file containing words, throw an exception if file can't be opened.
        try {
            words = Files.readAllLines(Paths.get(fileType), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Word file cannot be opened.");
            System.exit(0);
        }
        // Rnadom line to take a word from
        int randomWordNum = (int) (Math.random() * words.size());

        // Try to take a random word from the text file, throw an exception if the file is empty.
        try {
            returnedWord = words.get(randomWordNum);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No words found in text file.");
            System.exit(0);
        }

        // fix grammatical error that may occur. Fix the 'a'/'an' and ensure verbs generally make sense.
        if (fileType == word_verbs) {
            returnedWord += " with";
        }
        if (fileType == word_adjectives_positive || fileType == word_adjectives_negative || fileType == word_location_description_negative || fileType == word_location_description_positive) {
            String tempWord = returnedWord;
            char isVowel = returnedWord.charAt(0);
            if (isVowel == 'a' || isVowel == 'e' || isVowel == 'i' || isVowel == 'o' || isVowel == 'u') {
                returnedWord = "an " + tempWord;
            } else {
                returnedWord = "a " + tempWord;
            }
        }

        return returnedWord;
    }

}
