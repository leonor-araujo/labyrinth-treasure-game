/**
 * Nome: Maria Leonor Ferreira Araujo
 * Numero: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferrira
 * Numero: 8240332
 * Turma: LEI2T4
 */
package Labyrinth;

/**
 * Defines the contract for a chamber (node) within the labyrinth structure.
 * Implementing classes represent specific rooms that can contain challenges,
 * game objectives, or serve as entry points.
 */
public interface IChamber {

    /**
     * Retrieves the unique identifier of the chamber.
     *
     * @return The integer ID associated with this chamber.
     */
    int getId();

    /**
     * Retrieves the textual description of the chamber's environment.
     *
     * @return A string describing the chamber.
     */
    String getDescription();

    /**
     * Checks if the chamber currently holds an active challenge.
     *
     * @return true if a challenge is present; false otherwise.
     */
    boolean hasChallenge();

    /**
     * Retrieves the specific challenge object assigned to this chamber.
     *
     * @return The {@code IChallenge} instance, or null if none exists.
     */
    Challenges.IChallenge getChallenge();

    /**
     * Checks if this chamber is the designated location of the treasure.
     *
     * @return true if this contains the treasure; false otherwise.
     */
    boolean isTreasure();

    /**
     * Sets the status of this chamber regarding the treasure.
     *
     * @param isTreasure true to mark this as the treasure room; false otherwise.
     */
    void setTreasure(boolean isTreasure);

    /**
     * Checks if this chamber is a valid starting point for the game.
     *
     * @return true if this is an entrance; false otherwise.
     */
    boolean isEntrance();

    /**
     * Sets the status of this chamber as a valid game entrance.
     *
     * @param isEntrance true to mark this as an entrance; false otherwise.
     */
    void setEntrance(boolean isEntrance);
}
