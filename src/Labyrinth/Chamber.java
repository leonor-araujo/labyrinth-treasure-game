/**
 * Nome: Maria Leonor Ferreira Araujo
 * Número: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferreira
 * Número: 8240332
 * Turma: LEI2T4
 */
package Labyrinth;


import Challenges.IChallenge;

/**
 * Represents a node (room) within the Labyrinth graph structure.
 * A chamber has a unique ID, a description, and may contain a challenge,
 * the game treasure, or serve as an entrance.
 */
public class Chamber implements IChamber {
    private int id;
    private String description;
    private IChallenge challenge;
    private boolean isTreasure;
    private boolean isEntrance;

    /**
     * Constructs a new Chamber with the specified attributes.
     * By default, the chamber is neither a treasure room nor an entrance.
     *
     * @param id          The unique integer identifier for this chamber.
     * @param description A textual description of the room's appearance or context.
     * @param challenge   The challenge assigned to this room, or null if there is none.
     */
    public Chamber(int id, String description, IChallenge challenge) {
        this.id = id;
        this.description = description;
        this.challenge = challenge;
        this.isTreasure = false;
        this.isEntrance = false;
    }

    /**
     * Retrieves the unique identifier of the chamber.
     *
     * @return The integer ID.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Retrieves the textual description of the chamber.
     *
     * @return The description string.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if this chamber contains an active challenge.
     *
     * @return true if a challenge is assigned; false otherwise.
     */
    @Override
    public boolean hasChallenge() {
        return challenge != null;
    }

    /**
     * Retrieves the specific challenge object associated with this chamber.
     *
     * @return The {@code IChallenge} instance, or null if none exists.
     */
    public IChallenge getChallenge() {
        return this.challenge;
    }

    /**
     * Checks if this chamber contains the treasure (winning condition).
     *
     * @return true if this is the treasure room; false otherwise.
     */
    @Override
    public boolean isTreasure() {
        return this.isTreasure;
    }

    /**
     * Marks or unmarks this chamber as the location of the treasure.
     * Note: A chamber cannot be both an entrance and a treasure room.
     * If set to true, {@code isEntrance} will automatically be set to false.
     *
     * @param isTreasure true to designate this as the treasure room.
     */
    @Override
    public void setTreasure(boolean isTreasure) {
        if (this.isEntrance) {
            this.isEntrance = false;
        }
        this.isTreasure = isTreasure;
    }

    /**
     * Checks if this chamber is a valid starting point for players.
     *
     * @return true if this is an entrance; false otherwise.
     */
    @Override
    public boolean isEntrance() {
        return this.isEntrance;
    }

    /**
     * Marks or unmarks this chamber as an entrance.
     * Note: A chamber cannot be both an entrance and a treasure room.
     * If set to true, {@code isTreasure} will automatically be set to false.
     *
     * @param isEntrance true to designate this as a starting point.
     */
    @Override
    public void setEntrance(boolean isEntrance) {
        if (this.isTreasure) {
            this.isTreasure = false;
        }
        this.isEntrance = isEntrance;
    }
}

