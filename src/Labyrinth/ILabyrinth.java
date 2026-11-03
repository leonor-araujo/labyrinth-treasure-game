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

import DataStructures.Graphs.GraphADT;

import java.util.Iterator;

/**
 * Defines the contract for the Labyrinth structure, extending the capabilities
 * of a standard Graph Abstract Data Type (ADT).
 * It manages the chambers (vertices), corridors (edges), and special game locations.
 */
public interface ILabyrinth extends GraphADT<IChamber> {

    /**
     * Creates a connection (corridor) between two specific chambers using the provided hall object.
     *
     * @param c1   The first chamber.
     * @param c2   The second chamber.
     * @param hall The hall object representing the connection and its properties (e.g., events).
     */
    void addCorridor(IChamber c1, IChamber c2, IHall hall);

    /**
     * Retrieves the corridor object connecting two specific chambers.
     *
     * @param c1 The first chamber.
     * @param c2 The second chamber.
     * @return The {@code IHall} instance connecting them, or null if no connection exists.
     */
    IHall getCorridor(IChamber c1, IChamber c2);

    /**
     * Retrieves an iterator over the chambers directly connected to the specified current chamber.
     *
     * @param current The chamber whose neighbors are to be retrieved.
     * @return An iterator of {@code IChamber} objects representing the neighboring rooms.
     */
    Iterator<IChamber> getNeighbors(IChamber current);

    /**
     * Retrieves a specific chamber based on its unique identifier.
     *
     * @param id The integer ID of the chamber to find.
     * @return The {@code IChamber} with the matching ID, or null if not found.
     */
    IChamber getChamber(int id);

    /**
     * Identifies and retrieves the chamber designated as the treasure location.
     *
     * @return The {@code IChamber} containing the treasure.
     */
    IChamber getTreasureChamber();

    /**
     * Retrieves an iterator over all chambers designated as valid entrances.
     *
     * @return An iterator of {@code IChamber} objects marked as entrances.
     */
    Iterator<IChamber> getEntranceChambers();
}