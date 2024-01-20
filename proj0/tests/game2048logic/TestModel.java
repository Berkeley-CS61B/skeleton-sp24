package game2048logic;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static com.google.common.truth.Truth.assertWithMessage;


@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TestModel {

    /*
     * ***********************
     * *  TESTING GAME OVER  *
     * ***********************
     * <p>
     * The following tests determine the correctness of your `checkGameOver`
     * method.
     */

    /** No tilt can cause a change. */
    @Test
    @Tag("model")
    @DisplayName("No valid moves")
    @GradedTest(number = "4.1")
    public void testGameOverNoChange1() {
        int[][] board = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        };
        Model model = new Model(board, 0);
        assertWithMessage("Game is over. No tilt would result in a change:"
                + model).that(model.gameOver()).isTrue();
    }

    /** The MAX_PIECE (2048) tile is on the board. */
    @Test
    @Tag("model")
    @DisplayName("Max tile present")
    @GradedTest(number = "4.2")
    public void testGameOverMaxPiece() {
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2048}
        };
        Model model = new Model(board, 0);
        assertWithMessage("Game is over. Tile with 2048 is on board:"
                + model).that(model.gameOver()).isTrue();
    }

    /** No tilt can cause a change. */
    @Test
    @Tag("model")
    @DisplayName("No valid moves 2")
    @GradedTest(number = "4.3")
    public void testGameOverNoChange2() {
        int[][] board = {
                {128, 4, 2, 4},
                {4, 32, 4, 2},
                {8, 16, 2, 8},
                {4, 32, 4, 1024}
        };
        Model model = new Model(board, 0);
        assertWithMessage("Game is over. No tilt can cause a change:"
                + model).that(model.gameOver()).isTrue();
    }

    /** Any tilt will change the board. */
    @Test
    @Tag("model")
    @DisplayName("Valid moves present")
    @GradedTest(number = "4.4")
    public void testGameNotOver1() {
        int[][] board = {
                {2, 4, 2, 2},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        };
        Model model = new Model(board, 0);
        assertWithMessage("Game isn't over. Any tilt will result in a change:"
                + model).that(model.gameOver()).isFalse();
    }

    /** A tilt right or down will change the board. */
    @Test
    @Tag("model")
    @DisplayName("Valid Right and Down moves")
    @GradedTest(number = "4.5")
    public void testGameNotOver2() {
        int[][] board = {
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 0}
        };
        Model model = new Model(board, 0);
        assertWithMessage("Game isn't over. A tilt right or down will result"
                + " in a change:" + model).that(model.gameOver()).isFalse();
    }

}
