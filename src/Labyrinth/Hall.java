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

import Challenges.IEvent;
import DataStructures.Queues.CircularArrayQueue;
import Player.IPlayer;

/**
 * Represents a connection (edge) between two chambers in the labyrinth.
 * A hall may contain an event that is triggered when a player passes through it.
 */
public class Hall implements IHall {

    private IEvent hallEvent;

    /**
     * Constructs a new Hall with a specific event.
     *
     * @param hallEvent The event associated with this hall, or null if there is no event.
     */
    public Hall(IEvent hallEvent) {
        this.hallEvent = hallEvent;
    }

    /**
     * Checks if this hall contains an active event.
     *
     * @return true if an event is assigned to this hall; false otherwise.
     */
    public boolean hasEvent() {
        return hallEvent != null;
    }

    /**
     * Executes the event logic associated with this hall on the specified player.
     * If no event exists, no action is taken.
     *
     * @param player     The player currently traversing the hall.
     * @param allPlayers The queue of all players in the game, used if the event affects others.
     */
    public void applyEvent(IPlayer player, CircularArrayQueue<IPlayer> allPlayers) {
        if (hasEvent()) {
            hallEvent.triggerEvent(allPlayers, player);
        }
    }
}
