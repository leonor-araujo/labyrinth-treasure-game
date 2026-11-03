/**
 * Nome: Maria Leonor Ferreira Araujo
 * Número: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferreira
 * Número: 8240332
 * Turma: LEI2T4
 */

package Challenges;

import IO.RiddleLoader;
import IO.RiddleQuestion;

import java.util.Scanner;

/**
 * Represents a specific type of Challenge involving a riddle question.
 * Users must answer a multiple-choice question correctly to pass.
 */
public class Riddle extends Challenge {
    private RiddleQuestion currentQuestion;

    /**
     * Constructs a new Challenge with the specified ID and description.
     *
     * @param challengeID The unique identifier for the challenge.
     * @param description The textual description of the challenge goal.
     */
    public Riddle(int challengeID, String description) {
        super(challengeID, description);
    }

    /**
     * Executes the logic required to attempt this specific challenge.
     *
     * @return true if the riddle is solved correctly; false otherwise.
     */
    @Override
    public boolean solveChallenge() {
        return solveRiddle();
    }

    /**
     * Manages the interactive process of loading a random riddle, displaying
     * options to the user, and validating the chosen answer.
     *
     * @return true if the correct answer is selected or if no riddle is found (free pass); false if the answer is incorrect.
     */
    private boolean solveRiddle() {
        this.currentQuestion = RiddleLoader.getRandomRiddleQuestion();

        if (this.currentQuestion == null) {
            System.out.println("You got lucky, you have a free pass thru this chamber.");
            return true;
        }

        System.out.println("Welcome to this Riddle! In order to open this chamber you need to choose the right answer to the following question.");
        System.out.println(currentQuestion.getRiddleQuestion());

        String[] options = currentQuestion.getAnswerOptions();

        for (int i = 0; i < options.length; i++) {
            System.out.println("  " + (i + 1) + ". " + options[i]);
        }

        Scanner scan = new Scanner(System.in);
        int choice = -1;

        while (choice < 1 || choice > 4) {
            System.out.println("Please choose a number between 1 and 4.");
            String input = scan.nextLine();

            try {
                choice = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println("You entered an invalid number.");
            }
        }

        String chosenAnswer = currentQuestion.getOptionNumber(choice - 1);

        if (currentQuestion.isCorrectAnswer(chosenAnswer)) {
            System.out.println("You got the right Answer.");
            return true;

        } else {
            System.out.println("You got the wrong Answer.");
            return false;
        }
    }
}
