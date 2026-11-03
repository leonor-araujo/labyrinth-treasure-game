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

/**
 * Enumerates the distinct functional roles a chamber can play within the labyrinth structure.
 */
public enum ChamberType {
    /**
     * Represents a standard room with no special start or end game properties.
     */
    NORMAL,

    /**
     * Represents a valid starting point for players entering the labyrinth.
     */
    ENTRANCE,

    /**
     * Represents the final goal of the game where the treasure is located.
     */
    TREASURE
}
