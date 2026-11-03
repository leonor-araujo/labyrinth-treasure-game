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

import java.util.Scanner;

/**
 * Represents a specific challenge involving a game of chance with levers.
 */
public class Lever extends Challenge {
    /**
     * Indicates whether the lever challenge has been completed.
     */
    private boolean complete;

    /**
     * Extra turns the player has available.
     */
    private final int rightLever;

    /**
     * Constructs a new Challenge with the specified ID and description.
     *
     * @param challengeID The unique identifier for the challenge.
     * @param description The textual description of the challenge goal.
     */
    public Lever(int challengeID, String description) {
        super(challengeID, description);

        this.complete = false;
        this.rightLever = (int) (Math.random() * 3) + 1;
    }

    /**
     * Initiates the challenge interaction and checks if it was solved.
     *
     * @return true if the correct lever was chosen, false otherwise.
     */
    @Override
    public boolean solveChallenge() {
        tryLever();
        return complete;
    }

    /**
     * Internal helper that prompts the user for input and verifies the choice.
     */
    private void tryLever() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Choose a lever 1, 2 or 3: ");
        int choice = sc.nextInt();

        if (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("You chose something that is not within the range 1-3");
            return;
        }

        if (choice == rightLever) {
            System.out.println("You chose the right lever. You may continue your journey.");
            complete = true;

        } else {
            System.out.println("You chose the wrong lever. Better luck next time.");
        }
    }
}
