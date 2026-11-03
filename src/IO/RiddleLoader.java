/**
 * Nome: Maria Leonor Ferreira Araujo
 * Número: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferreira
 * Número: 8240332
 * Turma: LEI2T4
 */
package IO;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import DataStructures.Lists.ArrayUnorderedList;

/**
 * Utility class responsible for loading riddle questions from a JSON source
 * and providing random access to them for game challenges.
 */
public class RiddleLoader {
    /**
     * Represents the root structure of the JSON file containing an array of questions.
     * Used for Gson deserialization.
     */
    private class JsonRoot {
        JsonQuestion[] questions;
    }

    /**
     * Represents the JSON structure for a single question, including the text and answer options.
     */
    private class JsonQuestion {
        String question;
        String[] options;
    }

    /**
     * The internal list holding all loaded riddle questions.
     */
    private static ArrayUnorderedList<RiddleQuestion> questions;

    /**
     * Parses the specified JSON file to populate the list of available riddle questions.
     *
     * @param filePath The file path to the JSON file containing the riddles.
     */
    public static void load(String filePath) {
        Gson gson = new Gson();
        questions = new ArrayUnorderedList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JsonRoot data = gson.fromJson(reader, JsonRoot.class);

            if (data != null && data.questions != null) {
                for (JsonQuestion jsonQuestion : data.questions) {

                    if (jsonQuestion.question == null || jsonQuestion.options == null) {
                        System.out.println("⚠️ Invalid JSON");
                        continue;
                    }

                    RiddleQuestion riddleQuestion = new RiddleQuestion(jsonQuestion.question, jsonQuestion.options);
                    questions.addToRear(riddleQuestion);
                }
                System.out.println("Loaded " + questions.size() + " questions");
            }
        } catch (IOException e) {
            System.out.println("Failed to load Riddle questions from file: " + e.getMessage());
        }
    }

    /**
     * Retrieves a randomly selected riddle question from the loaded list.
     *
     * @return A {@code RiddleQuestion} instance, or {@code null} if the list is empty or not initialized.
     */
    public static RiddleQuestion getRandomRiddleQuestion() {
        if (questions == null || questions.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * questions.size());

        Iterator<RiddleQuestion> iterator = questions.iterator();
        int currentIndex = 0;

        while (iterator.hasNext()) {
            RiddleQuestion question = iterator.next();
            if (currentIndex == randomIndex) {
                return question;
            }
            currentIndex++;
        }

        return null;
    }
}
