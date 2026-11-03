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

import DataStructures.Graphs.Graph;
import DataStructures.Lists.ArrayUnorderedList;

import java.util.Iterator;

/**
 * Represents the game map as a graph structure, where chambers are vertices
 * and halls (corridors) are edges. It manages the topology of the game world.
 */
public class Labyrinth extends Graph<IChamber> implements ILabyrinth {

    /**
     * Adjacency matrix storing the Hall objects corresponding to connections between chambers.
     */
    private IHall[][] hallMatrix;

    /**
     * Constructs a new, empty Labyrinth with an initial capacity.
     */
    public Labyrinth() {
        super();
        this.hallMatrix = new IHall[10][10];
    }

    /**
     * Adds a corridor (edge) connecting two chambers in the graph and stores the
     * specific Hall object in the hall matrix.
     *
     * @param chamber1 The first chamber.
     * @param chamber2 The second chamber.
     * @param hall     The hall object representing the connection.
     */
    @Override
    public void addCorridor(IChamber chamber1, IChamber chamber2, IHall hall) {
        super.addEdge(chamber1, chamber2);

        int index1 = getIndex(chamber1);
        int index2 = getIndex(chamber2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            hallMatrix[index1][index2] = hall;
            hallMatrix[index2][index1] = hall;
        }
    }

    /**
     * Retrieves the specific Hall object connecting two chambers.
     *
     * @param chamber1 The first chamber.
     * @param chamber2 The second chamber.
     * @return The {@code IHall} instance if a connection exists; null otherwise.
     */
    @Override
    public IHall getCorridor(IChamber chamber1, IChamber chamber2) {
        int index1 = getIndex(chamber1);
        int index2 = getIndex(chamber2);

        if (indexIsValid(index1) && indexIsValid(index2)) {
            return hallMatrix[index1][index2];
        }
        return null;
    }

    /**
     * Retrieves an iterator containing all chambers directly adjacent to the specified chamber.
     *
     * @param current The chamber whose neighbors are being requested.
     * @return An iterator of {@code IChamber} objects representing the neighbors.
     */
    @Override
    public Iterator<IChamber> getNeighbors(IChamber current) {
        int index = getIndex(current);
        ArrayUnorderedList<IChamber> result = new ArrayUnorderedList<>();

        if (indexIsValid(index)) {
            Object[] rawVertices = this.getVertices();

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i]) {
                    IChamber neighbor = (IChamber) rawVertices[i];
                    result.addToRear(neighbor);
                }
            }
        }
        return result.iterator();
    }

    /**
     * Searches for and returns a chamber with the specified ID.
     *
     * @param id The unique integer identifier of the chamber.
     * @return The matching {@code IChamber}, or null if no chamber with that ID is found.
     */
    @Override
    public IChamber getChamber(int id) {
        Object[] rawVertices;
        try {
            rawVertices = this.getVertices();
        } catch (Exception e) {
            rawVertices = vertices;
        }

        for (int i = 0; i < numVertices; i++) {
            if (rawVertices[i] != null) {
                IChamber chamber = (IChamber) rawVertices[i];
                if (chamber.getId() == id) {
                    return chamber;
                }
            }
        }
        return null;
    }

    /**
     * Scans the graph to find the chamber marked as the treasure location.
     *
     * @return The {@code IChamber} containing the treasure, or null if not set.
     */
    @Override
    public IChamber getTreasureChamber() {
        Object[] rawVertices;
        try {
            rawVertices = this.getVertices();
        } catch (ClassCastException e) {
            rawVertices = vertices;
        }

        for (int i = 0; i < numVertices; i++) {
            if (rawVertices[i] != null) {
                IChamber chamber = (IChamber) rawVertices[i];

                if (chamber.isTreasure()) {
                    return chamber;
                }
            }
        }
        return null;
    }

    /**
     * Scans the graph to find all chambers marked as valid entrances.
     *
     * @return An iterator of {@code IChamber} objects that are entrances.
     */
    @Override
    public Iterator<IChamber> getEntranceChambers() {
        ArrayUnorderedList<IChamber> entrances = new ArrayUnorderedList<>();

        Object[] rawVertices;
        try {
            rawVertices = this.getVertices();
        } catch (Exception e) {
            rawVertices = vertices;
        }

        for (int i = 0; i < numVertices; i++) {
            if (rawVertices[i] != null) {
                IChamber c = (IChamber) rawVertices[i];

                if (c.isEntrance()) {
                    entrances.addToRear(c);
                }
            }
        }
        return entrances.iterator();
    }

    /**
     * Calculates the shortest path between two chambers.
     * This delegates to the superclass (Graph) BFS implementation.
     *
     * @param start  The starting chamber.
     * @param target The destination chamber.
     * @return An iterator representing the sequence of chambers in the shortest path.
     */
    @Override
    public Iterator<IChamber> iteratorShortestPath(IChamber start, IChamber target) {
        return super.iteratorShortestPath(start, target);
    }

    /**
     * Expands the capacity of the internal storage structures (both the graph's
     * adjacency matrix/vertices array and the hall matrix) when the limit is reached.
     */
    @Override
    protected void expandCapacity() {
        super.expandCapacity();

        IHall[][] largerMatrix = new IHall[hallMatrix.length * 2][hallMatrix.length * 2];

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                largerMatrix[i][j] = hallMatrix[i][j];
            }
        }
        hallMatrix = largerMatrix;
    }
}