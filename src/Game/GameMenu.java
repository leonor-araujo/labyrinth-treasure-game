/**
 * Nome: Maria Leonor Ferreira Araujo
 * Número: 8240331
 * Turma: LEI2T4
 * <p>
 * Nome: Miguel Brandao Ferreira
 * Número: 8240332
 * Turma: LEI2T4
 */
package Game;

import DataStructures.Lists.ArrayUnorderedList;
import IO.LabyrinthLoader;
import Labyrinth.ILabyrinth;
import Labyrinth.IChamber;
import Player.*;
import DataStructures.Queues.CircularArrayQueue;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Manages the initial game menus, including mode selection, difficulty settings,
 * resource loading (maps and questions), and player configuration.
 */
public class GameMenu {
    private static final String PATH_EASY_LABYRINTH = "easyLabyrinth.json";
    private static final String PATH_MEDIUM_LABYRINTH = "mediumLabyrinth.json";
    private static final String PATH_HARD_LABYRINTH = "hardLabyrinth.json";

    private static final String PATH_QUESTIONS_GENERAL = "questions.json";
    private static final String PATH_QUESTIONS_IT = "questionsED.json";

    private Scanner scanner;

    /**
     * Constructs a new GameMenu and initializes the input scanner.
     */
    public GameMenu() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main menu and handles the primary loop for starting a new game or exiting.
     */
    public void game() {
        boolean running = true;

        while (running) {
            System.out.println("\n============================");
            System.out.println("   WELCOME TO THE GAME!!!   ");
            System.out.println("============================");
            System.out.println("1. ⚔️  START NEW GAME");
            System.out.println("2. 🚪  EXIT");
            System.out.print("\n>> Select an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    showTopicMenu();
                    break;
                case "2":
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }

    /**
     * Displays the menu for selecting the category of questions (Riddles) to be used in the game.
     */
    private void showTopicMenu() {
        boolean choosing = true;

        while (choosing) {
            System.out.println("\n-----------------------------");
            System.out.println("     SELECT GAME MODE        ");
            System.out.println("-----------------------------");
            System.out.println("1. 🧠 General Knowledge (Normal)");
            System.out.println("2. 💻 IT Student Edition (Data Structures)");
            System.out.println("3. 🔙 Back to Main Menu");
            System.out.print("\n>> Choose mode: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":

                    showDifficultyMenu(PATH_QUESTIONS_GENERAL);
                    choosing = false;
                    break;
                case "2":

                    showDifficultyMenu(PATH_QUESTIONS_IT);
                    choosing = false;
                    break;
                case "3":
                    choosing = false;
                    break;
                default:
                    System.out.println("❌ Invalid mode. Choose 1 or 2 or 3.");
            }
        }
    }

    /**
     * Displays the menu for selecting the labyrinth difficulty level.
     *
     * @param questionsPath The file path to the selected question/riddle JSON file.
     */
    private void showDifficultyMenu(String questionsPath) {
        boolean choosing = true;

        while (choosing) {
            System.out.println("\n-----------------------------");
            System.out.println("   SELECT DIFFICULTY LEVEL   ");
            System.out.println("-----------------------------");
            System.out.println("1. 🟢 Easy");
            System.out.println("2. 🟡 Medium");
            System.out.println("3. 🔴 Hard");
            System.out.println("4. 🔙 Back to Topic Selection");
            System.out.print("\n>> Choose level: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    setupAndStartGame(PATH_EASY_LABYRINTH, questionsPath);
                    choosing = false;
                    break;
                case "2":
                    setupAndStartGame(PATH_MEDIUM_LABYRINTH, questionsPath);
                    choosing = false;
                    break;
                case "3":
                    setupAndStartGame(PATH_HARD_LABYRINTH, questionsPath);
                    choosing = false;
                    break;
                case "4":
                    choosing = false;
                    break;
                default:
                    System.out.println("❌ Invalid difficulty. Choose 1, 2, 3 or 4.");
            }
        }
    }


    /**
     * Orchestrates the setup process: loads the specific riddle and labyrinth files,
     * initializes players, and passes control to the GameControler.
     *
     * @param mapPath       The file path to the selected labyrinth JSON file.
     * @param questionsPath The file path to the selected question/riddle JSON file.
     */
    private void setupAndStartGame(String mapPath, String questionsPath) {
        System.out.println("\n... Setting up game...");

        System.out.println("Loading riddles from: " + questionsPath);
        try {
            IO.RiddleLoader.load(questionsPath);
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Could not load questions.");
            e.printStackTrace();
        }

        System.out.println("Loading Labyrinth from: " + mapPath);

        ILabyrinth labyrinth = LabyrinthLoader.loadLabyrinth(mapPath);

        if (labyrinth == null) {
            System.out.println("❌ Critical Error: Could not load map at: " + mapPath);
            System.out.println("(Run the game from the repository root so the JSON files can be found)");
            return;
        }

        CircularArrayQueue<IPlayer> playersQueue = setupPlayers(labyrinth);

        if (playersQueue.isEmpty()) {
            System.out.println("❌ Error: No players created. Game cancelled.");
            return;
        }

        GameControler game = new GameControler(labyrinth, playersQueue);
        game.startGame(labyrinth, playersQueue);
    }

    /**
     * Interactive method to configure the number of players, their names, types (Human/Bot),
     * and starting positions within the labyrinth.
     *
     * @param labyrinth The loaded labyrinth instance, used to validate entrance chambers.
     * @return A queue containing all configured players ready to start the game.
     */
    private CircularArrayQueue<IPlayer> setupPlayers(ILabyrinth labyrinth) {
        CircularArrayQueue<IPlayer> queue = new CircularArrayQueue<>();

        Iterator<IChamber> chambersIterator = labyrinth.getEntranceChambers();
        ArrayUnorderedList<IChamber> availableEntrances = new ArrayUnorderedList<>();

        while (chambersIterator.hasNext()) {
            availableEntrances.addToRear(chambersIterator.next());
        }

        if (availableEntrances.isEmpty()) {
            System.out.println("❌ Error: No entrance chambers found in the map!");
            return queue;
        }

        int numPlayers = 0;
        while (numPlayers < 1 || numPlayers > 4) {
            System.out.print("\n>> How many players? (1-4): ");
            try {
                numPlayers = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }

        for (int i = 1; i <= numPlayers; i++) {
            System.out.println("\n--------------------------------");
            System.out.println("   CONFIGURING PLAYER " + i);
            System.out.println("--------------------------------");

            System.out.print("Name: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) name = "Player " + i;

            System.out.println("\nType:");
            System.out.println(" 1. Human");
            System.out.println(" 2. Bot (Random)");
            System.out.println(" 3. Bot (Shortest Path)");

            int type = 0;
            while (type < 1 || type > 3) {
                System.out.print(">> Choose type: ");
                try {
                    type = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                }
            }

            IChamber selectedEntrance = null;

            System.out.println("\nSelect a starting location:");

            Iterator<IChamber> optionsIt = availableEntrances.iterator();
            while (optionsIt.hasNext()) {
                IChamber c = optionsIt.next();
                System.out.println("  [" + c.getId() + "] - " + c.getDescription());
            }

            while (selectedEntrance == null) {
                System.out.print(">> Enter Chamber ID: ");
                try {
                    int choiceId = Integer.parseInt(scanner.nextLine());

                    IChamber temp = labyrinth.getChamber(choiceId);

                    if (temp != null && temp.isEntrance()) {
                        selectedEntrance = temp;
                    } else {
                        System.out.println("❌ Invalid entrance ID. Pick from the list.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a number.");
                }
            }

            IPlayer newPlayer;
            switch (type) {
                case 1:
                    newPlayer = new HumanPlayer(name, selectedEntrance);
                    break;
                case 2:
                    newPlayer = new BotPlayer(name + " (Bot)", selectedEntrance, StrategyType.RANDOM);
                    break;
                case 3:
                    newPlayer = new BotPlayer(name + " (Bot)", selectedEntrance, StrategyType.SHORTEST_PATH);
                    break;
                default:
                    newPlayer = new HumanPlayer(name, selectedEntrance);
            }

            queue.enqueue(newPlayer);
            System.out.println("✅ " + name + " is starting at: " + selectedEntrance.getDescription());
        }

        return queue;
    }
}