/**
 * Name: Maria Leonor Ferreira Araujo
 * Number: 8240331
 * Class: LEI2T4
 * <p>
 * Name: Miguel Brandao Ferreira
 * Number: 8240332
 * Class: LEI2T4
 */
package Player;

import DataStructures.Lists.ArrayUnorderedList;
import DataStructures.Queues.CircularArrayQueue;
import Labyrinth.IChamber;
import Labyrinth.IHall;
import Labyrinth.ILabyrinth;

import java.util.Iterator;

/**
 * Represents an automated player (bot) that moves through the labyrinth
 * according to a selected strategy. Bots may move randomly or attempt to
 * follow the shortest path to the treasure chamber.
 */
public class BotPlayer extends Player {

    /** The movement strategy used by the bot */
    private StrategyType strategy;

    /**
     * Creates a bot player with a given strategy.
     *
     * @param name          bot's name
     * @param startChamber  starting chamber
     * @param strategy      movement strategy (RANDOM or SHORTEST_PATH)
     */
    public BotPlayer(String name, IChamber startChamber, StrategyType strategy) {
        super(name, startChamber);
        this.strategy = strategy;
    }

    /**
     * Executes the bot's turn based on its strategy. Bots may:
     * - choose a random neighboring chamber, or
     * - attempt to follow the shortest path to the treasure.
     *
     * @param labyrinth   the labyrinth environment
     * @param allPlayers  queue of all players in the game
     */
    @Override
    public void play(ILabyrinth labyrinth, CircularArrayQueue<IPlayer> allPlayers) {
        System.out.println("\n--- Bot Turn: " + this.name + " (" + strategy + ") ---");

        IChamber nextChamber = null;

        if (this.strategy == StrategyType.SHORTEST_PATH) {
            IChamber treasure = labyrinth.getTreasureChamber();

            if (treasure != null) {
                Iterator<IChamber> path = labyrinth.iteratorShortestPath(this.currentChamber, treasure);

                if (path != null && path.hasNext()) {
                    path.next();
                    if (path.hasNext()) {
                        nextChamber = path.next();
                    }
                }
            }
        }

        if (nextChamber == null) {
            Iterator<IChamber> it = labyrinth.getNeighbors(currentChamber);
            ArrayUnorderedList<IChamber> neighbors = new ArrayUnorderedList<>();
            while (it.hasNext()) neighbors.addToRear(it.next());

            if (!neighbors.isEmpty()) {
                int randomIndex = (int) (Math.random() * neighbors.size());

                Iterator<IChamber> selectionIt = neighbors.iterator();
                int currentIndex = 0;
                while (selectionIt.hasNext()) {
                    IChamber c = selectionIt.next();
                    if (currentIndex == randomIndex) {
                        nextChamber = c;
                        break;
                    }
                    currentIndex++;
                }
            }
        }

        if (nextChamber != null) {
            IHall hall = labyrinth.getCorridor(this.currentChamber, nextChamber);

            this.setCurrentChamber(nextChamber);
            System.out.println("Bot moved to: " + nextChamber.getDescription());

            if (hall != null && hall.hasEvent()) {
                System.out.println("⚡ Bot triggered an event!");
                hall.applyEvent(this, allPlayers);
            }

            if (!this.canPlay()) {
                System.out.println("⛔ You lost your turn! Stopping current action.");
                return;
            }

        } else {
            System.out.println("Bot " + this.name + " is stuck! (No moves available)");
        }
    }
}