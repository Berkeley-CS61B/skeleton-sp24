package game2048logic;

import game2048rendering.Side;
import game2048rendering.Tile;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static com.google.common.truth.Truth.assertWithMessage;
import static game2048logic.TestUtils.checkTilt;;

@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TestMultipleMoves {

    /*
     * *************************
     * *  MULTIPLE MOVE TESTS  *
     * *************************
     * <p>
     * The following tests will call the `tilt` method multiple times and check
     * the correctness of the board after each move. You shouldn't expect these
     * tests to pass until all of the above tests pass.
     */



    /** Will test multiple moves on the Model. */
    @Test
    @Tag("multiple-moves")
    @DisplayName("Multiple moves")
    @GradedTest(number = "9.1")
    public void testMultipleMoves1() {
        Model model = new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2}
        }, 0);

        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 2}
        }, 0), Side.EAST);

        model.addTile(Tile.create(2, 3, 1));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }, 4), Side.NORTH);

        model.addTile(Tile.create(2, 0, 1));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 2},
                {0, 0, 0, 2},
                {0, 0, 0, 0}
        }, 4), Side.EAST);

        model.addTile(Tile.create(4, 2, 0));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 4, 4},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }, 8), Side.NORTH);

        model.addTile(Tile.create(4, 0, 3));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 4, 8}
        }, 16), Side.SOUTH);
    }

    /** Will test multiple moves on the Model that end the game. */
    @Test
    @Tag("multiple-moves")
    @DisplayName("Multiple moves and end behavior")
    @GradedTest(number = "9.2")
    public void testMultipleMoves2() {
        Model model = new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 256, 256, 0},
                {1024, 0, 0, 512}
        }, 0);

        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 512},
                {0, 0, 1024, 512}
        }, 512), Side.EAST);

        model.addTile(Tile.create(2, 0, 0));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 1024, 1024}
        }, 1536), Side.SOUTH);

        model.addTile(Tile.create(2, 0, 1));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {2, 2048, 0, 0}
        }, 3584), Side.WEST);
        assertWithMessage("Game is over. Tile with 2048 is on board:"
                + model).that(model.gameOver()).isTrue();
    }

    /** Will test multiple moves on the Model. */
    @Test
    @Tag("multiple-moves")
    @DisplayName("Multiple Moves 2")
    @GradedTest(number = "9.3")
    public void testMultipleMoves3() {
        Model model = new Model(new int[][]{
                {0, 2, 2, 0},
                {4, 0, 4, 0},
                {4, 0, 8, 0},
                {8, 0, 0, 0}
        }, 10);

        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 8},
                {0, 0, 4, 8},
                {0, 0, 0, 8}
        }, 22), Side.EAST);

        model.addTile(Tile.create(2, 1, 2));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 8},
                {0, 2, 4, 16}
        }, 38), Side.SOUTH);

        model.addTile(Tile.create(2, 1, 1));
        checkTilt(model, new Model(new int[][]{
                {0, 4, 4, 4},
                {0, 0, 0, 8},
                {0, 0, 0, 16},
                {0, 0, 0, 0}
        }, 42), Side.NORTH);

        model.addTile(Tile.create(4, 0, 0));
        checkTilt(model, new Model(new int[][]{
                {4, 4, 4, 4},
                {0, 0, 0, 8},
                {0, 0, 0, 16},
                {0, 0, 0, 0}
        }, 42), Side.NORTH);

        model.addTile(Tile.create(2, 3, 0));
        checkTilt(model, new Model(new int[][]{
                {0, 0, 8, 8},
                {0, 0, 0, 8},
                {0, 0, 0, 16},
                {0, 0, 0, 2}
        }, 58), Side.EAST);
    }

    @Test
    @Tag("multiple-moves")
    @DisplayName("Consecutive merges onto same tile")
    @GradedTest(number = "9.4")
    public void testMergesOneTile() {
        Model model = new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0}
        }, 0);

        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 2},
                {0, 0, 0, 0}
        }, 0), Side.EAST);

        model.addTile(Tile.create(4, 3, 0));

        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }, 4), Side.NORTH);

        checkTilt(model, new Model(new int[][]{
                {0, 0, 0, 8},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }, 12), Side.NORTH);
    }

}
