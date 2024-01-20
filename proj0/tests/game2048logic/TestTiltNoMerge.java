package game2048logic;

import game2048rendering.Side;
import jh61b.grader.GradedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static com.google.common.truth.Truth.assertWithMessage;
import static game2048logic.TestUtils.checkTilt;

@Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TestTiltNoMerge {


    /*
     * ******************
     * *  TESTING TILT  *
     * ******************
     * <p>
     * The following tests determine the correctness of your `tilt`
     * method.
     */


    /** Checks that a tilt that causes no change returns false. */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Test invalid tilt output")
    @GradedTest(number = "8.1")
    public void testNoMove() {
        int[][] before = new int[][]{
                {2, 0, 2, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {2, 0, 2, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);
    }

    /** Move tiles up (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Test Up tilt")
    @GradedTest(number = "8.2")
    public void testUpNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 2},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);
    }

    /** Move adjacent tiles up (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Adjacent Up tilt")
    @GradedTest(number = "8.3")
    public void testUpAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);
    }

    /** Move non-adjacent tiles up (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Up tilt with gap")
    @GradedTest(number = "8.4")
    public void testUpNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);
    }

    /** Move non-adjacent tiles up (no merging); case 2: both tiles move. */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Up tilt with gaps")
    @GradedTest(number = "8.5")
    public void testMoveUpNonAdjacentNoMerge2() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.NORTH);
    }
    /** Move tiles right (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Right tilt")
    @GradedTest(number = "8.6")
    public void testRightNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 2, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 2},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.EAST);
    }

    /** Move adjacent tiles right (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Adjacent right tilt")
    @GradedTest(number = "8.7")
    public void testRightAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 4, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 4},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.EAST);
    }

    /** Move adjacent tiles right (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Adjacent right tilt with gap")
    @GradedTest(number = "8.8")
    public void testRightNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 4},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 4},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.EAST);
    }

    /** Move adjacent tiles right (no merging); case 2: both tiles move. */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Adjacent right tilt with gaps")
    @GradedTest(number = "8.9")
    public void testRightNonAdjacentNoMerge2() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 4, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, 4},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.EAST);
    }

    /** Move tiles down (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Down tilt")
    @GradedTest(number = "8.10")
    public void testDownNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 4, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 4, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.SOUTH);
    }

    /** Move adjacent tiles down (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Adjacent down tilt")
    @GradedTest(number = "8.11")
    public void testDownAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 4, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 4, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.SOUTH);
    }

    /** Move non-adjacent tiles down (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Down tilt with gaps")
    @GradedTest(number = "8.12")
    public void testDownNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 4, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 4, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.SOUTH);
    }

    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Left tilt")
    @GradedTest(number = "8.13")
    public void testLeftNoMerge() {
        int[][] before = new int[][]{
                {4, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {4, 0, 0, 0},
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.WEST);
    }

    /** Move adjacent tiles left (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Adjacent left tilt")
    @GradedTest(number = "8.14")
    public void testLeftAdjacentNoMerge() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {0, 4, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.WEST);
    }

    /** Move non-adjacent tiles left (no merging). */
    @Test
    @Tag("tilt-no-merge")
    @DisplayName("Left tilt with gaps")
    @GradedTest(number = "8.15")
    public void testLeftNonAdjacentNoMerge1() {
        int[][] before = new int[][]{
                {0, 0, 0, 0},
                {4, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[][] after = new int[][]{
                {0, 0, 0, 0},
                {4, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        checkTilt(new Model(before, 0), new Model(after, 0), Side.WEST);
    }
}
