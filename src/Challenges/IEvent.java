package Challenges;

import DataStructures.Queues.CircularArrayQueue;
import Player.IPlayer;

/**
 * Defines the contract for events that can occur during the game.
 * Implementing classes must define the specific logic for how the event affects players.
 */
public interface IEvent {
    /**
     * Executes the specific logic associated with this event.
     *
     * @param allPlayers    The queue containing all players currently in the game.
     * @param currentPlayer The player who triggered the event or whose turn it is.
     */
    void triggerEvent(CircularArrayQueue<IPlayer> allPlayers, IPlayer currentPlayer);
}
