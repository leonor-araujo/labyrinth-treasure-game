/**
 * Nome: Maria Leonor Ferreira Araujo
 * Número: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferreira
 * Número: 8240332
 * Turma: LEI2T4
 */
package Game;

import DataStructures.Lists.DoubleUnorderedLinkedList;
import Labyrinth.ILabyrinth;
import Labyrinth.IChamber;
import Player.IPlayer;
import Player.Player;
import DataStructures.Queues.CircularArrayQueue;

import java.util.Iterator;

/**
 * Manages the main game execution logic, controlling the game loop,
 * player turns, and victory conditions.
 */
public class GameControler {
    private ILabyrinth labyrinth;
    private CircularArrayQueue<IPlayer> playersQueue;
    private boolean gameRunning;
    private int turnCounter;

    /**
     * Constructs a new GameControler with the specified labyrinth and list of players.
     *
     * @param labyrinth The game map structure.
     * @param players   The queue of players participating in the game.
     */
    public GameControler(ILabyrinth labyrinth, CircularArrayQueue<IPlayer> players) {
        this.labyrinth = labyrinth;
        this.playersQueue = players;
        this.gameRunning = true;
        this.turnCounter = 0;
    }

    /**
     * Starts the main game loop, managing turns, penalties, extra turns, and checking for the winner.
     *
     * @param labyrinth The game map structure to be used.
     * @param players   The queue of players participating in the game.
     */
    public void startGame(ILabyrinth labyrinth, CircularArrayQueue<IPlayer> players) {
        this.labyrinth = labyrinth;
        this.playersQueue = players;

        if (this.labyrinth == null || this.playersQueue == null || this.playersQueue.isEmpty()) {
            System.out.println("ERROR: Game cannot start without players or labyrinth!");
            return;
        }

        System.out.println("\n*****************************************");
        System.out.println("* THE GAME HAS STARTED!                 *");
        System.out.println("*****************************************");

        while (gameRunning) {
            turnCounter++;
            IPlayer currentPlayer;

            try {
                currentPlayer = playersQueue.dequeue();
                boolean playAgain;

                do {
                    playAgain = false;

                    if (checkWinCondition(currentPlayer)) {
                        announceVictory(currentPlayer);
                        return;
                    }

                    if (!currentPlayer.canPlay()) {
                        System.out.println("\n>> Turn " + turnCounter + ": " + currentPlayer.getName() + " is penalized and loses it's turn.");
                        currentPlayer.decreasePenalty();
                        break;
                    }

                    currentPlayer.play(labyrinth, playersQueue);

                    if (checkWinCondition(currentPlayer)) {
                        announceVictory(currentPlayer);
                        return;
                    }

                    if (currentPlayer instanceof Player) {
                        Player p = (Player) currentPlayer;
                        if (p.getExtraTurns() > 0) {
                            p.useExtraTurn();
                            System.out.println("🎲 BONUS! " + p.getName() + " plays again!");
                            playAgain = true;
                        }
                    }

                } while (playAgain);

                playersQueue.enqueue(currentPlayer);

            } catch (Exception e) {
                System.out.println("Error in game cycle " + e.getMessage());
                e.printStackTrace();
                gameRunning = false;
            }
        }

        System.out.println("Final Report...");
    }

    /**
     * Declares the winner, prints the victory message, and initiates the final report generation.
     *
     * @param currentPlayer The player who found the treasure.
     */
    private void announceVictory(IPlayer currentPlayer) {
        System.out.println("\n🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉");
        System.out.println("WIN! Player " + currentPlayer.getName() + " found the tressure!");
        System.out.println("🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉🎉");
        System.out.println("Game finished in " + turnCounter + " turns.");

        playersQueue.enqueue(currentPlayer);

        generateFinalReport();

        gameRunning = false;
    }

    /**
     * Checks if the specified player has reached the chamber containing the treasure.
     *
     * @param player The player to check against the win condition.
     * @return true if the player is in the treasure chamber; false otherwise.
     */
    private boolean checkWinCondition(IPlayer player) {
        IChamber current = player.getCurrentChamber();
        return current != null && current.isTreasure();
    }

    /**
     * Generates and prints a final report detailing the history of moves for all players.
     */
    private void generateFinalReport() {
        System.out.println("\n#########################################");
        System.out.println("#          Final Report             #");
        System.out.println("#########################################");

        int size = playersQueue.size();

        for (int i = 0; i < size; i++) {
            IPlayer player;
            try {
                player = playersQueue.dequeue();

                System.out.println("\n 📜" + player.getName() + " history ");

                if (player instanceof Player) {
                    Player p = (Player) player;

                    DoubleUnorderedLinkedList<String> history = p.getLogHistory();

                    Iterator<String> it = history.iterator();

                    if (!it.hasNext()) {
                        System.out.println("   (No records found)");
                    }

                    while (it.hasNext()) {
                        System.out.println("   -> " + it.next());
                    }
                }
                playersQueue.enqueue(player);

            } catch (Exception e) {
                System.out.println("Error generating Final Report ");
            }
        }
        System.out.println("\n#########################################");
    }
}