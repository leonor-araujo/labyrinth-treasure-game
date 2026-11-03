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

/**
 * Represents an abstract base class for defining challenges.
 */
public abstract class Challenge implements IChallenge {
    /**
     * Unique identifier of the challenge.
     */
    private int challengeID;

    /**
     * Text describing what the challenge is about.
     */
    private String description;

    /**
     * Constructs a new Challenge with the specified ID and description.
     *
     * @param challengeID The unique identifier for the challenge.
     * @param description The textual description of the challenge goal.
     */
    public Challenge(int challengeID, String description) {
        this.challengeID = challengeID;
        this.description = description;
    }

    /**
     * Retrieves the unique identifier associated with this challenge.
     *
     * @return The unique challenge ID.
     */
    public int getChallengeID() {
        return challengeID;
    }

    /**
     * Updates the unique identifier for this challenge.
     *
     * @param challengeID The new ID to assign.
     */
    public void setChallengeID(int challengeID) {
        this.challengeID = challengeID;
    }

    /**
     * Retrieves the textual description of the challenge.
     *
     * @return The description of the challenge.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the textual description of the challenge.
     *
     * @param description The new description text to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
