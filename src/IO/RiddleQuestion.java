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

import java.util.Random;

/**
 * Represents a single riddle question unit, containing the question text,
 * the correct answer, and a set of shuffled options for the user to choose from.
 */
public class RiddleQuestion {
    private String riddleQuestion;
    private String[] answerOptions;
    private String rightAnswer;

    /**
     * Constructs a new RiddleQuestion.
     * Note: The first element of the rawOption array is assumed to be the correct answer.
     * The options are automatically shuffled upon creation.
     *
     * @param riddleQuestion The text of the question to be asked.
     * @param rawOption      An array of possible answers, where the first element (index 0) is the correct one.
     */
    public RiddleQuestion(String riddleQuestion, String[] rawOption) {
        this.riddleQuestion = riddleQuestion;
        this.rightAnswer = rawOption[0];
        this.answerOptions = shuffleArray(rawOption);
    }

    /**
     * Randomizes the order of the answer options to ensure the correct answer
     * is not always in the same position.
     *
     * @param array The original array of answer strings.
     * @return A new array containing the same strings in a random order.
     */
    private String[] shuffleArray(String[] array) {
        String[] newArray = array.clone();

        Random random = new Random();

        for (int i = newArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            String temp = newArray[index];
            newArray[index] = newArray[i];
            newArray[i] = temp;
        }

        return newArray;
    }

    /**
     * Retrieves the text of the riddle question.
     *
     * @return The question string.
     */
    public String getRiddleQuestion() {
        return riddleQuestion;
    }

    /**
     * Retrieves the array of shuffled answer options.
     *
     * @return An array of strings representing the possible answers.
     */
    public String[] getAnswerOptions() {
        return answerOptions;
    }

    /**
     * Verifies if the provided answer string matches the correct answer for this riddle.
     * The comparison is case-insensitive.
     *
     * @param answer The answer selected by the user.
     * @return true if the answer is correct; false otherwise.
     */
    public boolean isCorrectAnswer(String answer) {
        return answer.equalsIgnoreCase(this.rightAnswer);
    }

    /**
     * Retrieves the answer text at a specific index in the shuffled options array.
     *
     * @param index The 0-based index of the option to retrieve.
     * @return The answer string at the specified index, or null if the index is out of bounds.
     */
    public String getOptionNumber(int index) {
        if (index >= 0 && index < answerOptions.length) {
            return answerOptions[index];
        }

        return null;
    }

}
