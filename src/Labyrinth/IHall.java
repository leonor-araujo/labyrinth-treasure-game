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

import DataStructures.Queues.CircularArrayQueue;
import Player.IPlayer;

/**
 * Defines the contract for a connection (edge) between two chambers.
 * Implementing classes manage the events that occur when traversing the hall.
 */
public interface IHall {

    /**
     * Checks if this hall contains an active event.
     *
     * @return true if an event is assigned to this hall; false otherwise.
     */
    boolean hasEvent();

    /**
     * Executes the event logic associated with this hall on the specified player.
     *
     * @param player     The player currently traversing the hall.
     * @param allPlayers The queue of all players in the game, used if the event affects others.
     */
    void applyEvent(IPlayer player, CircularArrayQueue<IPlayer> allPlayers);
}