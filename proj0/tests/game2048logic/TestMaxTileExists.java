package game2048logic;
import game2048rendering.Board;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static com.google.common.truth.Truth.assertWithMessage;

/** Tests the maxTileExists() method of Model.
 *
 * @author Omar Khan
 */
@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TestMaxTileExists {
    /** Note that this isn't a possible board state. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test empty board")
    @GradedTest(number = "3.1")
    public void testEmptyBoard() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };

        Model m = new Model(rawVals, 0);
        assertWithMessage("Board is empty\n" + m.getBoard()).that(m.maxTileExists()).isFalse();
    }

    /** Tests a full board with no max piece. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test no max piece")
    @GradedTest(number = "3.2")
    public void testFullBoardNoMax() {
        int[][] rawVals = new int[][] {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
        };

        Model m = new Model(rawVals, 0);
        assertWithMessage("No 2048 tile on board\n" + m.getBoard()).that(m.maxTileExists()).isFalse();
    }

    /** Tests a full board with the max piece. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test board with max piece")
    @GradedTest(number = "3.3")
    public void testFullBoardMax() {
        int[][] rawVals = new int[][] {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2048},
        };

        Model m = new Model(rawVals, 0);
        assertWithMessage("One 2048 tile on board\n" + m.getBoard()).that(m.maxTileExists()).isTrue();
    }

    /** Tests multiple max pieces. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test board with multiple max pieces")
    @GradedTest(number = "3.4")
    public void testMultipleMax() {
        int[][] rawVals = new int[][] {
                {2, 2, 2, 2},
                {2, 2048, 0, 0},
                {0, 0, 0, 2},
                {0, 0, 2, 2048},
        };

        Model m = new Model(rawVals, 0);
        assertWithMessage("Two 2048 tiles on board\n" + m.getBoard()).that(m.maxTileExists()).isTrue();

    }

    /** Tests when the max piece is in the top right corner. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test board with max piece in top right corner")
    @GradedTest(number = "3.5")
    public void testTopRightCorner() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 2048},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        Model m = new Model(rawVals, 0);
        assertWithMessage("One 2048 tile on board\n" + m.getBoard()).that(m.maxTileExists()).isTrue();
    }

    /** Tests when the max piece is in the top left corner. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test board with max piece in top left corner")
    @GradedTest(number = "3.6")
    public void testTopLeftCorner() {
        int[][] rawVals = new int[][] {
                {2048, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        Model m = new Model(rawVals, 0);
        assertWithMessage("One 2048 tile on board\n" + m.getBoard()).that(m.maxTileExists()).isTrue();
    }

    /** Tests when the max piece is in the bottom left corner. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test board with max piece in the bottom left corner")
    @GradedTest(number = "3.7")
    public void testBottomLeftCorner() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2048, 0, 0, 0}
        };

        Model m = new Model(rawVals, 0);

        assertWithMessage("One 2048 tile on board\n" + m.getBoard()).that(m.maxTileExists()).isTrue();
    }

    /** Tests when the max piece is in the bottom right corner. */
    @Test
    @Tag("max-tile")
    @DisplayName("Test board with max piece in the bottom right corner")
    @GradedTest(number = "3.8")
    public void testBottomRightCorner() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2048}
        };

        Model m = new Model(rawVals, 0);

        assertWithMessage("One 2048 tile on board\n" + m.getBoard()).that(m.maxTileExists()).isTrue();
    }

}
