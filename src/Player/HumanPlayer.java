/**
 * Name: Maria Leonor Ferreira Araujo
 * Number: 8240331
 * Class: LEI2T4
 * <p>
 * Name: Miguel Brandao Ferreira
 * Number: 8240332
 * Class: LEI2T4
 */
package Player;

import DataStructures.Lists.ArrayUnorderedList;
import DataStructures.Queues.CircularArrayQueue;
import Labyrinth.IChamber;
import Labyrinth.IHall;
import Labyrinth.ILabyrinth;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Represents a human-controlled player. The player receives prompts,
 * chooses corridors manually, and must handle challenges or events
 * encountered inside the labyrinth.
 */
public class HumanPlayer extends Player {

    /** Scanner used to read user input */
    private Scanner scanner;

    /** Indicates whether the player is trapped inside the current chamber */
    private boolean isTrapped;

    /**
     * Creates a new human player starting at the given chamber.
     *
     * @param name          player's name
     * @param startChamber  initial chamber
     */
    public HumanPlayer(String name, IChamber startChamber) {
        super(name, startChamber);
        this.scanner = new Scanner(System.in);
        this.isTrapped = false;

        this.pathHistory = new ArrayUnorderedList<>();
    }

    /**
     * Executes the player's turn. If the player is trapped, they must resolve
     * the chamber's challenge. Otherwise, they select one of the available
     * neighboring chambers and move toward it, dealing with any events that
     * occur in connecting halls.
     *
     * @param labyrinth   the labyrinth environment
     * @param allPlayers  queue containing all players
     */
    @Override
    public void play(ILabyrinth labyrinth, CircularArrayQueue<IPlayer> allPlayers) {
        System.out.println("\n========================================");
        System.out.println("TURN: " + this.name);
        System.out.println("Chamber: " + currentChamber.getDescription()
                + " (ID: " + currentChamber.getId() + ")");
        System.out.println("========================================");

        if (this.isTrapped) {
            System.out.println("⚠ You are TRAPPED in this chamber! Solve the challenge to escape.");

            if (currentChamber.hasChallenge()) {
                boolean success = currentChamber.getChallenge().solveChallenge();

                if (success) {
                    System.out.println("✔ You succeeded! You may move on your NEXT turn.");
                    this.isTrapped = false;
                } else {
                    System.out.println("❌ You failed again. You remain trapped.");
                }
            } else {
                this.isTrapped = false;
            }
            return;
        }

        Iterator<IChamber> neighborsIt = labyrinth.getNeighbors(currentChamber);
        ArrayUnorderedList<IChamber> options = new ArrayUnorderedList<>();

        int count = 0;
        System.out.println("Available paths:");

        while (neighborsIt.hasNext()) {
            IChamber c = neighborsIt.next();
            options.addToRear(c);
            System.out.println(" [" + count + "] - Go to: " + c.getDescription());
            count++;
        }

        if (options.isEmpty()) {
            System.out.println("You are stuck! No exits available.");
            return;
        }

        int choice = -1;
        while (choice < 0 || choice >= options.size()) {
            System.out.print(">> Choose a path number: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 0 || choice >= options.size()) {
                    System.out.println("Invalid number. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }

        IChamber targetChamber = null;
        Iterator<IChamber> selectionIt = options.iterator();
        int currentIndex = 0;

        while (selectionIt.hasNext()) {
            IChamber temp = selectionIt.next();
            if (currentIndex == choice) {
                targetChamber = temp;
                break;
            }
            currentIndex++;
        }

        if (targetChamber != null) {

            IHall corridor = labyrinth.getCorridor(this.currentChamber, targetChamber);

            this.setCurrentChamber(targetChamber);
            System.out.println("\nYou entered the chamber: " + targetChamber.getDescription());

            if (corridor != null && corridor.hasEvent()) {
                System.out.println("\n⚡ Unexpected event in the corridor!");

                corridor.applyEvent(this, allPlayers);

                if (!this.canPlay()) {
                    System.out.println("⛔ You lost your turn! Stopping current action.");
                    return;
                }

                if (this.currentChamber != targetChamber) {
                    System.out.println("⚠ The event moved you to a different location!");
                    return;
                }
            }

            if (targetChamber.hasChallenge()) {
                System.out.println("\nThis chamber contains a challenge!");

                boolean success = targetChamber.getChallenge().solveChallenge();

                if (success) {
                    System.out.println("Challenge completed!");
                    this.isTrapped = false;
                } else {
                    System.out.println("❌ You failed the challenge! You are now trapped.");
                    this.isTrapped = true;
                }
            }
        }
    }
}

