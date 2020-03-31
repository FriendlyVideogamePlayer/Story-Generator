import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.io.FileWriter;

public class storyGenerator {
    wordGenerator wordGen = new wordGenerator();
    int limit = 0;
    public List<String> outputStory = new LinkedList<String>();


    // Main method to run through the generation.
    public static void main(String[] args) {
        GUI sg = new GUI();
        sg.main(null);
        storyGenerator gen = new storyGenerator();
        gen.characterGeneration();

    }
    // List to get the list of sentences.
    public List<String> getList() {

        return outputStory;
    }

    // Generates a character name and adjective used to describe them. Then forms a sentence.
    public void characterGeneration() {
        // Clear the current story saved in the list. So a new story can be output in the GUI.
        outputStory.clear();
        System.out.println("-------------GUI-------------");
        // String joiners to hold the words. Decider to randomise whether the word is positive or negative.
        StringJoiner character = new StringJoiner(" ", "", ".");
        StringJoiner location = new StringJoiner(" ", "", ".");
        int decider = new Random().nextInt(2);

        String character1 = wordGen.wordGeneration(wordGen.word_names);
        character.add(character1).add("was");

        // If the RNG gave 1 -> positive adjective else negative adjective.
        if(decider == 1) {
            character.add(wordGen.wordGeneration(wordGen.word_adjectives_negative));
        } else {
            character.add(wordGen.wordGeneration(wordGen.word_adjectives_positive));
        }

        character.add(wordGen.wordGeneration(wordGen.word_species));
        // Turn the string joiner into an actual string.
        String sentence = character.toString();


        // Second sentence generation
        String character1Home = wordGen.wordGeneration(wordGen.word_location);
        location.add(character1).add(wordGen.wordGeneration(wordGen.word_lives));
        // If the RNG gave 1 -> positive description else negative description.
        if(decider == 1) {
            location.add(wordGen.wordGeneration(wordGen.word_location_description_negative));
        } else {
            location.add(wordGen.wordGeneration(wordGen.word_location_description_positive));
        }
        location.add(character1Home);
        String sentenceHome = location.toString();

        // Output sentences
        System.out.println(sentence);
        System.out.println(sentenceHome);
        outputStory.add(sentence);
        outputStory.add(sentenceHome);
        // Call next stage
        weatherGeneration(character1, character1Home);

    }

    // Generates a weather type and a characters feeling about the weather. Then forms a sentence.
    private void weatherGeneration(String character1, String character1Home) {
        // String joiners to hold the words. Decider to randomise whether the word is positive or negative. Strings to hold the words to be used later.
        StringJoiner weather = new StringJoiner(" ", "It was a ", ".");
        StringJoiner weatherFeeling = new StringJoiner(" ", "", "s.");
        int decider = new Random().nextInt(2);
        String sentance ="";
        String sentanceFeeling = "";
        // If set to true then use positive outcomes more, if set to false use negative outcomes more.
        Boolean story_tone = null;

        String wordTime = wordGen.wordGeneration(wordGen.word_time);
        // If the RNG gave 1 -> negative weather and feeling else positive weather and feeling.
        if(decider == 1) {
            String weatherWord = wordGen.wordGeneration(wordGen.word_weather_negative);
            weather.add(weatherWord).add(wordTime);
            sentance = weather.toString();
            // Second sentence
            weatherFeeling.add(character1).add(wordGen.wordGeneration(wordGen.word_feeling_negative)).add(weatherWord).add(wordTime);
            sentanceFeeling = weatherFeeling.toString();
            // Use more negative words
            story_tone = false;

        } else {
            String weatherWord = wordGen.wordGeneration(wordGen.word_weather_positive);
            weather.add(weatherWord).add(wordTime);
            sentance = weather.toString();
            // Second sentence
            weatherFeeling.add(character1).add(wordGen.wordGeneration(wordGen.word_feeling_positive)).add(weatherWord).add(wordTime);
            sentanceFeeling = weatherFeeling.toString();
            // Use more positive words
            story_tone = true;
        }

        // Output sentences
        System.out.println(sentance);
        System.out.println(sentanceFeeling);
        outputStory.add(sentance);
        outputStory.add(sentanceFeeling);
        // Call next stage
        locationGeneration(character1, character1Home, story_tone, wordTime);
    }

    // Generates a activity location and a character to meet there. Then forms a sentence.
    private void locationGeneration(String character1, String character1Home, Boolean story_tone, String wordTime) {
        // Sentence type 1 -- output 1

        // String joiners to hold the words. Decider to randomise whether the word is positive or negative.
        StringJoiner location = new StringJoiner(" ", "", ".");
        String sentenceLocation = "";

        String activityLocation = wordGen.wordGeneration(wordGen.word_location);

        // Ensure the activity location isn't the same as the characters home.
        while(activityLocation.equals(character1Home)) {
            activityLocation = wordGen.wordGeneration(wordGen.word_location);
        }

        location.add(character1).add(wordGen.wordGeneration(wordGen.word_visit));

        // if the story tone was set to be more negative from the weather then use more negative outcomes, else use more positive ones.
        if(!story_tone) {
            location.add(wordGen.wordGeneration(wordGen.word_location_description_negative));
        } else if (story_tone) {
            location.add(wordGen.wordGeneration(wordGen.word_location_description_positive));
        }

        location.add(activityLocation);
        sentenceLocation = location.toString();

        // Output sentences
        System.out.println(sentenceLocation);
        outputStory.add(sentenceLocation);

        // Call next stage
        meetingGeneration(character1, character1Home, story_tone, wordTime);
    }

    private void meetingGeneration(String character1, String character1Home, Boolean story_tone, String wordTime) {
        // String joiners to hold the words.
        StringJoiner secondCharacter = new StringJoiner(" ", "", ".");
        String sentenceCharacter = "";

        secondCharacter.add(character1).add(wordGen.wordGeneration(wordGen.word_meet));

        String character2Desc = "";
        // if the story tone was set to be more negative from the weather then use more negative outcomes, else use more positive ones.
        if(!story_tone) {
            character2Desc = wordGen.wordGeneration(wordGen.word_adjectives_negative);
            secondCharacter.add(character2Desc);
        } else if(story_tone) {
            character2Desc = wordGen.wordGeneration(wordGen.word_adjectives_positive);
            secondCharacter.add(character2Desc);
        }

        String character2 = wordGen.wordGeneration(wordGen.word_species);

        secondCharacter.add(character2);
        sentenceCharacter = secondCharacter.toString();

        // Output sentences
        System.out.println(sentenceCharacter);
        outputStory.add(sentenceCharacter);
        // Call next stage
        activityGeneration(character1, character1Home, story_tone, character2, character2Desc, wordTime);

    }

    private void activityGeneration(String character1, String character1Home, Boolean story_tone, String character2, String character2Desc, String wordTime) {
        // String joiners to hold the words. Decider to randomise whether to add extra steps.
        StringJoiner secondActivity = new StringJoiner(" ", "", ".");
        String sentenceCharacterInteraction = "";
        int decider = new Random().nextInt(2);
        int secondDecider = new Random().nextInt(2);

        // Standard check whether or not to use positive or negative words -> then RNG to see whether or not to use format 1 or 2 of the sentence
        if(!story_tone) {
            String fixCap = "The";

            secondActivity.add(fixCap).add(character2).add(wordGen.wordGeneration(wordGen.word_verb_negative)).add(character1);

        } else if(story_tone) {
            if(secondDecider == 1) {
                secondActivity.add(character1).add(wordGen.wordGeneration(wordGen.word_verbs)).add(character2Desc).add(character2);
            } else {
                String fixCap = "The";
                secondActivity.add(fixCap).add(character2).add(wordGen.wordGeneration(wordGen.word_verbs)).add(character1);
            }

        }


        sentenceCharacterInteraction = secondActivity.toString();

        // Output sentences
        System.out.println(sentenceCharacterInteraction);
        outputStory.add(sentenceCharacterInteraction);

        // Call next stage. Randomise whether to implement more stages or not, with a limit of 3 but always 2.
        limit++;
        if(decider == 1 && limit!= 3) {
            locationGeneration(character1, character1Home, story_tone, wordTime);
        }
        else if(limit == 1) {
            locationGeneration(character1, character1Home, story_tone, wordTime);
        } else {
            finalEmotionGeneration(character1, character1Home, story_tone, wordTime);
        }

    }

    private List<String> finalEmotionGeneration(String character1, String character1Home, Boolean story_tone, String wordTime) {
        // String joiners to hold the words.
        StringJoiner finalEmotion = new StringJoiner(" ", "", ".");

        finalEmotion.add(character1);

        if(!story_tone) {
            finalEmotion.add(wordGen.wordGeneration(wordGen.word_feeling_negative));

        } else if(story_tone) {
            finalEmotion.add(wordGen.wordGeneration(wordGen.word_feeling_positive));
        }

                finalEmotion.add("this").add(wordTime);

        String emotionSentence = finalEmotion.toString();

        // Output sentences
        System.out.println(emotionSentence);
        outputStory.add(emotionSentence);


        // Testing list
        //for(int i=0; i<outputStory.size(); i++) {
        //    System.out.println(outputStory.get(i));
        //}
        return outputStory;
    }

}
