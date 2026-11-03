/**
 * Nome: Maria Leonor Ferreira Araujo
 * Numero: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferreira
 * Numero: 8240332
 * Turma: LEI2T4
 */
package Challenges;

import DataStructures.Lists.ArrayUnorderedList;
import DataStructures.Queues.CircularArrayQueue;
import DataStructures.Stacks.LinkedStack;
import Labyrinth.Chamber;
import Labyrinth.IChamber;
import Player.IPlayer;
import Player.Player;
import Player.HumanPlayer;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Represents an in-game event that triggers random effects on players.
 */
public class Event implements IEvent {
    /**
     * Unique identifier of the event.
     */
    private int eventID;

    /**
     * Description of the event.
     */
    private String description;

    /**
     * Constructs a new Challenge with the specified ID and description.
     *
     * @param eventID     The unique identifier for the challenge.
     * @param description The textual description of the challenge goal.
     */
    public Event(int eventID, String description) {
        this.eventID = eventID;
        this.description = description;
    }

    /**
     * Selects and executes a random event effect on the current player or all players.
     *
     * @param allPlayers    The queue containing all players in the game.
     * @param currentPlayer The player who triggered the event.
     */
    @Override
    public void triggerEvent(CircularArrayQueue<IPlayer> allPlayers, IPlayer currentPlayer) {
        EventType[] eventTypes = EventType.values();
        int randomIndex = (int) (Math.random() * eventTypes.length);
        EventType randomEvent = eventTypes[randomIndex];

        System.out.println("Player " + currentPlayer.getName() + " in this hall you have to: " + randomEvent);

        switch (randomEvent) {
            case SWAP_ALL_POSITIONS:
                swapAllPositions(currentPlayer, allPlayers);
                break;
            case SWAP_POSITION_WITH:
                swapPositionsWith(currentPlayer, allPlayers);
                break;
            case LOSE_TURN:
                loseTurn(currentPlayer);
                break;
            case GAIN_TURN:
                gainTurn(currentPlayer);
                break;
            case RETREAT_POSITION:
                retreatPosition(currentPlayer);
                break;

        }
    }

    /**
     * Cyclically shifts the current chamber of every player to the next player's chamber.
     *
     * @param players The queue containing all participants to be shifted.
     */
    private void swapAllPositions(IPlayer currentPlayer, CircularArrayQueue<IPlayer> players) {

        ArrayUnorderedList<IPlayer> playersList = getPlayers(players);

        playersList.addToRear(currentPlayer);

        if (playersList.isEmpty() || playersList.size() < 2) {
            System.out.println("No available players to swap");
            return;
        }

        IChamber[] originalChambers = new IChamber[playersList.size()];
        int i = 0;
        for (IPlayer player : playersList) {
            originalChambers[i] = player.getCurrentChamber();
            i++;
        }

        int j = 0;
        Iterator<IPlayer> iterator = playersList.iterator();

        while (iterator.hasNext()) {
            IPlayer p = iterator.next();
            IChamber newChamber;

            if (j == playersList.size() - 1) {
                newChamber = originalChambers[0];
            } else {
                newChamber = originalChambers[j + 1];
            }

            p.setCurrentChamber(newChamber);

            p.setCheckpoint(newChamber);

            System.out.println("Player " + p.getName() + " swapped to chamber: " + newChamber.getId());
            j++;
        }
    }

    /**
     * Facilitates a position swap between the current player and another selected player.
     *
     * @param current    The player currently taking the turn.
     * @param allPlayers The queue of all players to select a target from.
     */
    private void swapPositionsWith(IPlayer current, CircularArrayQueue<IPlayer> allPlayers) {
        ArrayUnorderedList<IPlayer> availablePlayers = getAvailablePlayers(current, allPlayers);

        if (availablePlayers.isEmpty()) {
            System.out.println("No available players to swap");
            return;
        }

        int choice = -1;

        if (current instanceof HumanPlayer) {
            Scanner scanner = new Scanner(System.in);
            listOtherPlayers(availablePlayers);

            while (choice < 1 || choice > availablePlayers.size()) {
                System.out.print("Enter the number of the player you wish to swap positions with: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    System.out.println("Invalid choice");
                    scanner.next();
                }
            }
        } else {
            choice = (int) (Math.random() * availablePlayers.size()) + 1;

            System.out.println("🤖 Bot decided to swap with player #" + choice + " from the list.");
        }

        IPlayer swapped = null;
        int currentIndex = 1;
        for (IPlayer player : availablePlayers) {
            if (currentIndex == choice) {
                swapped = player;
                break;
            }
            currentIndex++;
        }

        if (swapped != null) {
            IChamber tempPosition = current.getCurrentChamber();
            current.setCurrentChamber(swapped.getCurrentChamber());
            swapped.setCurrentChamber(tempPosition);

            current.setCheckpoint(current.getCurrentChamber());
            swapped.setCheckpoint(swapped.getCurrentChamber());

            System.out.println("\n!!! Swap Positions !!!");
            System.out.println(current.getName() + " swapped with " + swapped.getName());
            System.out.println(current.getName() + " went to chamber " + current.getCurrentChamber().getDescription());
            System.out.println(swapped.getName() + " went to chamber " + swapped.getCurrentChamber().getDescription());
            System.out.println("-------------------------");
        }
    }

    /**
     * Applies a random penalty of lost turns to the player.
     *
     * @param player The player who receives the penalty.
     */
    private void loseTurn(IPlayer player) {
        int turns = (int) (Math.random() * 3);

        player.applyPenalty(turns);
        System.out.println("Player " + player.getName() + " lost " + turns + " turns.");
    }

    /**
     * Grants a random number of extra turns to the player.
     *
     * @param player The player who receives the extra turns.
     */
    private void gainTurn(IPlayer player) {
        int turns = (int) (Math.random() * 3) + 1;

        if (player instanceof Player) {
            ((Player) player).addExtraTurn(turns);
        }

        System.out.println("Player " + player.getName() + " gained " + turns + " extra turns!");
    }

    /**
     * Retrieves a list containing all players except the current one.
     *
     * @param current    The player to exclude from the list.
     * @param allPlayers The source queue of all players.
     * @return An unordered list of other players.
     */
    private ArrayUnorderedList<IPlayer> getAvailablePlayers(IPlayer current, CircularArrayQueue<IPlayer> allPlayers) {
        ArrayUnorderedList<IPlayer> playersList = new ArrayUnorderedList<>();
        int size = allPlayers.size();

        for (int i = 0; i < size; i++) {
            try {
                IPlayer player = allPlayers.dequeue();

                if (!player.equals(current)) {
                    playersList.addToRear(player);
                }
                allPlayers.enqueue(player);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return playersList;
    }

    /**
     * Converts the queue of players into a temporary unordered list.
     *
     * @param allPlayers The source queue of players.
     * @return An unordered list containing all players.
     */
    private ArrayUnorderedList<IPlayer> getPlayers(CircularArrayQueue<IPlayer> allPlayers) {
        ArrayUnorderedList<IPlayer> playersList = new ArrayUnorderedList<>();
        int size = allPlayers.size();

        for (int i = 0; i < size; i++) {
            try {
                IPlayer player = allPlayers.dequeue();
                playersList.addToRear(player);
                allPlayers.enqueue(player);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return playersList;
    }

    /**
     * Displays a numbered list of players available for interaction.
     *
     * @param playersList The list of players to display.
     */
    private void listOtherPlayers(ArrayUnorderedList<IPlayer> playersList) {
        System.out.println("\n--- Available Players ---");

        int i = 1;
        for (IPlayer player : playersList) {
            System.out.println(i + ". " + player.getName() + " (Chamber: " + player.getCurrentChamber().getDescription() + ")");
            i++;
        }
    }

    /**
     * Moves the player backwards through their path history using a stack until a limit or checkpoint is reached.
     *
     * @param player The player to move backwards.
     */
    private void retreatPosition(IPlayer player) {
        int turns = (int) (Math.random() * 3) + 1;

        LinkedStack<Chamber> pathHistoryStack = new LinkedStack<>();
        Iterator<IChamber> iterator = player.getPathHistory().iterator();

        while (iterator.hasNext()) {
            pathHistoryStack.push((Chamber) iterator.next());
        }

        for (int i = 0; i < turns; i++) {
            if (pathHistoryStack.isEmpty() || pathHistoryStack.size() == 1) {
                break;
            }

            if (player.getCheckpoint() != null && pathHistoryStack.peek() == player.getCheckpoint()) {
                System.out.println("You have reached your CheckPoint. You're safe!");
                break;
            }

            pathHistoryStack.pop();
        }

        if (!pathHistoryStack.isEmpty()) {
            Chamber destination = pathHistoryStack.peek();

            player.setCurrentChamber(destination);
        }
    }
}
