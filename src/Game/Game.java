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

/**
 * Represents the entry point for the game application.
 */
public class Game {
    /**
     * The main method that initializes the game menu and starts the game execution.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        GameMenu menu = new GameMenu();
        menu.game();
    }
}