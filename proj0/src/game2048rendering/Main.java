package game2048rendering;

import game2048logic.Model;

/** The main class for the 2048 game.
 *  @author P. N. Hilfinger
 */
public class Main {
    /** Probability of choosing 2 as random tile (as opposed to 4). */
    static final double TILE2_PROBABILITY = 0.9;

    /** Number of squares on the side of a board. */
    static final int BOARD_SIZE = 4;

    /** Random seed. Ignored if 0. */
    static final long RANDOM_SEED = 0;

    /** If true, the custom start is used. Otherwise, the board starts off blank. */
    static final boolean USE_CUSTOM_START = false;

    /** Custom starting state of the game. Useful for debugging. */
    static final Model CUSTOM_START = new Model(new int[][]{
            {2, 0, 2, 128},
            {0, 0, 8, 0},
            {8, 64, 0, 128},
            {4, 64, 8, 256},
    }, 0);

    public static void main(String[] args) {
        Model model = USE_CUSTOM_START ? CUSTOM_START : new Model(BOARD_SIZE);

        GUI gui = new GUI("2048 61B", model);
        gui.display(true);

        Game game = new Game(model, gui, TILE2_PROBABILITY, RANDOM_SEED);
        try {
            game.playGame(USE_CUSTOM_START);
            while (game.playing()) {
                game.playGame(false);
            }
        } catch (IllegalStateException excp) {
            System.err.printf("Internal error: %s%n", excp.getMessage());
            System.exit(1);
        }

        System.exit(0);
    }

}
