package game2048logic;
import game2048rendering.Board;
import game2048rendering.Side;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static com.google.common.truth.Truth.assertWithMessage;
import static game2048logic.TestUtils.checkTilt;

/** Tests methods on N by N (not 4 by 4) models
 *
 * @author Samuel Berkun, Ergun Acikoz
 */
@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TestNbyN {
    /** Tilting an empty 1 by 1 */
    @Test
    @Tag("NxN")
    @DisplayName("The ants go marching")
    @GradedTest(number = "5.1")
    public void testOne() {
        int[][] before = new int[][] {
                {0},
        };
        int[][] after = new int[][] {
                {0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);
    }

    @Test
    @Tag("NxN")
    @DisplayName("Non-tilt methods")
    @GradedTest(number = "5.2")
    public void testNonTiltMethods() {

        int[][] rawVals = new int[][] {
                {32, 4, 2},
                {64, 2, 4},
                {0, 4, 2},
        };
        Model m = new Model(rawVals, 0);
        assertWithMessage("N = 3, TestEmptySpace - (0,0) is empty\n" + m.getBoard()).that(m.emptySpaceExists()).isTrue();

        rawVals = new int[][] {
                {0, 0},
                {0, 2048},
        };
        m = new Model(rawVals, 0);
        assertWithMessage("N = 2, TestMaxTileExists - One 2048 tile on board\n" + m.getBoard()).that(m.maxTileExists()).isTrue();

        rawVals = new int[][] {
                {32, 4, 2, 4, 8},
                {64, 2, 4, 2, 16},
                {32, 4, 2, 4, 8},
                {64, 2, 4, 2, 16},
                {32, 4, 2, 4, 8},
        };
        m = new Model(rawVals, 0);
        assertWithMessage("N = 5, TestAtLeastOneMoveExists - No move exists\n" + m.getBoard()).that(m.atLeastOneMoveExists()).isFalse();

    }

    @Test
    @Tag("NxN")
    @DisplayName("Non-merged tilts for N = 1, 2, 3")
    @GradedTest(number = "5.3")
    public void testSmallNonMergedTilts() {
        int[][] before;
        int[][] after;

        before = new int[][] {
                {0},
        };
        after = new int[][] {
                {0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);

        before = new int[][] {
                {0, 0},
                {2, 2},
        };
        after = new int[][] {
                {2, 2},
                {0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);

        before = new int[][] {
                {0, 2},
                {2, 0},
        };
        after = new int[][] {
                {2, 0},
                {2, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.WEST);

        before = new int[][] {
                {4, 0, 4},
                {2, 16, 2},
                {0, 0, 8},
        };
        after = new int[][] {
                {0, 0, 4},
                {4, 0, 2},
                {2, 16, 8},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.SOUTH);
    }


    /** Tilts for N = 1, 2, 3 */
    @Test
    @Tag("NxN")
    @DisplayName("Tilts for N = 1, 2, 3")
    @GradedTest(number = "5.4")
    public void testSmallTilts() {
        int[][] before;
        int[][] after;

        before = new int[][] {
                {4},
        };
        after = new int[][] {
                {4},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);

        before = new int[][] {
                {2, 2},
                {0, 2},
        };
        after = new int[][] {
                {4, 0},
                {2, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 4), Side.WEST);

        before = new int[][] {
                {8, 0, 2},
                {0, 0, 2},
                {0, 0, 2},
        };
        after = new int[][] {
                {0, 0, 0},
                {0, 0, 2},
                {8, 0, 4},
        };
        checkTilt(new Model(before, 0), new Model(after, 4), Side.SOUTH);
    }

    /** gameOver for N = 1, 2, 3 */
    @Test
    @Tag("NxN")
    @DisplayName("Tilts for N = 1, 2, 3")
    @GradedTest(number = "5.5")
    public void testSmallGameOver() {
        Model model;

        model = new Model(new int[][]{
                {0}
        }, 0);
        assertWithMessage("Game is not over. Empty space exists:"
                + model).that(model.gameOver()).isFalse();


        model = new Model(new int[][]{
                {2}
        }, 0);
        assertWithMessage("Game is over. No tilt would result in a change:"
                + model).that(model.gameOver()).isTrue();


        model = new Model(new int[][]{
                {2, 2},
                {4, 8}
        }, 0);
        assertWithMessage("Game is not over. A tilt left or right would result in a merge:"
                + model).that(model.gameOver()).isFalse();

        model = new Model(new int[][]{
                {2, 4},
                {4, 2}
        }, 0);
        assertWithMessage("Game is over. No tilt would result in a change:"
                + model).that(model.gameOver()).isTrue();

        model = new Model(new int[][]{
                {2, 2, 2},
                {2, 2, 1024},
                {2, 2, 2}
        }, 0);
        assertWithMessage("Game is not over. A tilt in any direction would result in a merge:"
                + model).that(model.gameOver()).isFalse();

        model = new Model(new int[][]{
                {2, 2, 2},
                {2, 2, 2048},
                {2, 2, 2}
        }, 0);
        assertWithMessage("Game is over. Max tile exists:"
                + model).that(model.gameOver()).isTrue();
    }


    /** Tilt and gameOver for N = 20 */
    @Test
    @GradedTest(name = "TestNbyN: Large", number = "5.6")
    public void testLarge() {
        int[][] before = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 0, 4, 4, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 4, 4, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 4, 4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 4, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 4, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 4, 0, 0, 0, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 4, 0, 4, 4, 4, 4, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        int[][] after = new int[][] {
                {8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        Model beforeModel = new Model(before, 0);
        assertWithMessage("Game is not over. Empty space exists:"
                + beforeModel).that(beforeModel.gameOver()).isFalse();
        checkTilt(beforeModel, new Model(after, 312), Side.WEST);
    }


}
