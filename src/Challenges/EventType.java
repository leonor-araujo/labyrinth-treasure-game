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
 * Defines the various types of events that can occur during the game.
 */
public enum EventType {
    /**
     * Represents an event where the player skips their next turn.
     */
    LOSE_TURN,

    /**
     * Represents an event where the player exchanges places with another specific entity.
     */
    SWAP_POSITION_WITH,

    /**
     * Represents a chaotic event where every player changes their position.
     */
    SWAP_ALL_POSITIONS,

    /**
     * Represents an event where the player is forced to move back a certain number of positions.
     */
    RETREAT_POSITION,

    /**
     * Represents an event where the player is granted an extra turn.
     */
    GAIN_TURN;
}
