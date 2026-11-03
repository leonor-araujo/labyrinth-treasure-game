/**
 * Nome: Maria Leonor Ferreira Araujo
 * Numero: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferrira
 * Numero: 8240332
 * Turma: LEI2T4
 */
package Player;

import DataStructures.Queues.CircularArrayQueue;
import Labyrinth.IChamber;
import Labyrinth.ILabyrinth;

/**
 * Interface defining the basic behavior expected from any player
 * navigating the labyrinth. Implementations handle movement,
 * penalties, and gameplay actions.
 */
public interface IPlayer {

    /**
     * Returns the player's name.
     *
     * @return the name of the player
     */
    String getName();

    /**
     * Returns the chamber where the player currently is.
     *
     * @return the current chamber
     */
    IChamber getCurrentChamber();

    /**
     * Updates the player's current chamber.
     *
     * @param chamber new chamber to move into
     */
    void setCurrentChamber(IChamber chamber);

    /**
     * Returns the chamber the player was in before the current one.
     *
     * @return the previous chamber
     */
    IChamber getPreviousChamber();

    /**
     * Returns the chamber where the player was when he swap positions with another player.
     *
     * @return the checkpoint chamber
     */
    IChamber getCheckpoint();

    /**
     * Updates the chamber the player get sent to when he swap positions with another player.
     *
     * @param chamber new checkpoint chamber
     */
    void setCheckpoint(IChamber chamber);

    /**
     * Grants additional turns to the player, allowing them to play multiple times in sequence.
     *
     * @param number The number of extra turns to add.
     */
    void addExtraTurn(int number);

    /**
     * Retrieves the number of extra turns the player currently has accumulated.
     *
     * @return The count of bonus turns available.
     */
    int getExtraTurns();

    /**
     * Consumes one extra turn from the player's accumulated pool.
     * This is typically called when the player chooses to use a bonus action.
     */
    void useExtraTurn();

    /**
     * Checks if the player is allowed to play this turn.
     *
     * @return true if the player can play
     */
    boolean canPlay();

    /**
     * Applies a penalty that forces the player to skip turns.
     *
     * @param turns number of turns to skip
     */
    void applyPenalty(int turns);

    /**
     * Reduces the penalty counter when the player waits a turn.
     */
    void decreasePenalty();

    /**
     * Executes the player's action for the current turn.
     *
     * @param labyrinth  the labyrinth in which the player moves
     * @param allPlayers queue containing all players
     */
    void play(ILabyrinth labyrinth, CircularArrayQueue<IPlayer> allPlayers);

    /**
     * Retrieves the history of locations visited by the player throughout the game.
     *
     * @return An iterable collection of chambers representing the path taken.
     */
    Iterable<IChamber> getPathHistory();
}