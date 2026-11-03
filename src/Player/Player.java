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


import DataStructures.Lists.ArrayUnorderedList;
import DataStructures.Lists.DoubleUnorderedLinkedList;
import DataStructures.Queues.CircularArrayQueue;
import Labyrinth.IChamber;
import Labyrinth.ILabyrinth;

/**
 * Abstract base class representing a player navigating a labyrinth.
 * Manages current and previous chambers, checkpoints, movement history,
 * penalties, and action logs.
 */
public abstract class Player implements IPlayer {

    /**
     * Player name
     */
    protected String name;

    /**
     * Current chamber where the player is located
     */
    protected IChamber currentChamber;

    /**
     * Previously visited chamber
     */
    private IChamber previousChamber;

    /**
     * Saved checkpoint chamber
     */
    private IChamber checkpoint;

    /**
     * Ordered list storing the player's path history
     */
    protected ArrayUnorderedList<IChamber> pathHistory;

    /**
     * Number of turns the player can play again on the same turn
     */
    private int extraTurns;

    /**
     * Number of turns the player must skip due to penalties
     */
    private int skipTurns;

    /**
     * Log of events and actions taken by the player
     */
    private DoubleUnorderedLinkedList<String> logHistory;

    /**
     * Constructs a new player starting in the given chamber.
     *
     * @param name         Player's name
     * @param startChamber Initial chamber
     */
    public Player(String name, IChamber startChamber) {
        this.name = name;
        this.currentChamber = startChamber;
        this.previousChamber = null;
        this.extraTurns = 0;
        this.skipTurns = 0;
        this.pathHistory = new ArrayUnorderedList<>();
        this.pathHistory.addToRear(startChamber);
        this.logHistory = new DoubleUnorderedLinkedList<>();
        this.logHistory.addToRear("Start: " + startChamber.getId() + "(" + startChamber.getDescription() + ")");
    }

    /**
     * @return the player's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the chamber where the player currently is
     */
    @Override
    public IChamber getCurrentChamber() {
        return currentChamber;
    }

    /**
     * Updates the player's current chamber and logs the movement.
     *
     * @param newChamber New chamber to move into
     */
    @Override
    public void setCurrentChamber(IChamber newChamber) {
        this.previousChamber = this.currentChamber;
        pathHistory.addToRear(currentChamber);
        this.currentChamber = newChamber;

        String prevId = (previousChamber != null) ? (previousChamber.getId() + "(" + previousChamber.getDescription() + ")") : "Start";
        this.logHistory.addToRear("Moved: " + prevId + " -> " + newChamber.getId() + "(" + newChamber.getDescription() + ")");
    }

    /**
     * @return the previously visited chamber
     */
    @Override
    public IChamber getPreviousChamber() {
        return previousChamber;
    }

    /**
     * @return the saved checkpoint chamber
     */
    @Override
    public IChamber getCheckpoint() {
        return checkpoint;
    }

    /**
     * Sets a new checkpoint for the player.
     *
     * @param chamber Chamber to mark as checkpoint
     */
    @Override
    public void setCheckpoint(IChamber chamber) {
        this.checkpoint = chamber;
    }

    /**
     * Adds a specified number of extra turns to the player's pool.
     *
     * @param number The number of bonus turns to grant.
     */
    @Override
    public void addExtraTurn(int number) {
        this.extraTurns += number;
    }

    /**
     * Retrieves the current number of extra turns available.
     *
     * @return The count of extra turns.
     */
    @Override
    public int getExtraTurns() {
        return this.extraTurns;
    }

    /**
     * Consumes one extra turn from the pool, if available.
     */
    @Override
    public void useExtraTurn() {
        if (this.extraTurns > 0) this.extraTurns--;
    }

    /**
     * Checks if the player is allowed to play.
     * A player can play only when penalty skip turns are zero or less.
     *
     * @return true if the player can take a turn
     */
    @Override
    public boolean canPlay() {
        return skipTurns <= 0;
    }

    /**
     * Applies a penalty causing the player to skip turns.
     *
     * @param turns Number of turns to skip
     */
    @Override
    public void applyPenalty(int turns) {
        if (turns > 0) {
            this.skipTurns += turns;

            System.out.println(this.name + " lost " + turns + " turn(s)!");
        }
    }

    /**
     * Decreases the penalty counter each turn the player waits.
     */
    @Override
    public void decreasePenalty() {
        if (this.skipTurns > 0) {
            this.skipTurns--;
            System.out.println(this.name + " is waiting... (" + this.skipTurns + " left)");
        }
    }

    /**
     * Returns the complete history of actions performed by the player.
     *
     * @return the full action log
     */
    public DoubleUnorderedLinkedList<String> getLogHistory() {
        return this.logHistory;
    }

    /**
     * Abstract method defining the behavior of the player during a turn.
     * Must be implemented by subclasses.
     *
     * @param labyrinth  The labyrinth in which the player moves
     * @param allPlayers Queue containing all players in the game
     */
    public abstract void play(ILabyrinth labyrinth, CircularArrayQueue<IPlayer> allPlayers);

    /**
     * Retrieves the sequence of chambers visited by the player.
     *
     * @return An iterable collection of visited chambers.
     */
    @Override
    public Iterable<IChamber> getPathHistory() {
        return this.pathHistory;
    }
}
