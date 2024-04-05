import gameoflife.GameOfLife;
import org.junit.Test;
import tileengine.TETile;
import tileengine.Tileset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

public class GameOfLifeTests {
    private static final String SAVE_FILE = "src/save.txt";
    private static final String SAVE_TEST = "tests/testFiles/saveTest.txt";
    private static final String LOAD_TEST = "tests/testFiles/loadTest.txt";

    private static final String BLANK = "patterns/blank.txt";
    private static final String GLIDERGUN = "patterns/glidergun.txt";
    private static final String HAMMERHEAD = "patterns/hammerhead.txt";
    private static final String PENTADECATHLON = "patterns/pentadecathlon.txt";

    /**
     * Compares the two states, if orientation is aligned.
     * @param student
     * @param expected
     */
    private void checkState(TETile[][] student, TETile[][] expected) {
        for (int i = 0; i < student.length; i++) {
            for (int j = 0; j < student[0].length; j++) {
                assertThat(student[i][j].description()).isEqualTo(expected[i][j].description());
            }
        }
    }

    /**
     * Checks for the state of the world before and after a single generation,
     * with dimensions of 3 by 3.
     */
    @Test
    public void oneGeneration() {
        checkIfPatternFilesAreModified();
        TETile[][] testInput = new TETile[][] {
                {Tileset.CELL, Tileset.CELL, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING}
        };

        TETile[][] result = new TETile[][] {
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING}
        };

        GameOfLife student = new GameOfLife(testInput, true);

        // Generate next state from the provided input.
        TETile[][] nextState = student.nextGeneration(testInput);

        // Height and width need to stay the same before and after the generation.
        assertThat(testInput.length).isEqualTo(nextState.length);
        assertThat(testInput[0].length).isEqualTo(nextState[0].length);

        // Check that the next generation state is as expected.
        checkState(nextState, result);
    }

    /**
     * Checks the state of the world after multiple generations.
     */
    @Test
    public void multipleGenerations() {
        checkIfPatternFilesAreModified();
        TETile[][] testInput = new TETile[][] {
                {Tileset.CELL, Tileset.CELL, Tileset.CELL, Tileset.CELL},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING, Tileset.CELL},
                {Tileset.NOTHING, Tileset.CELL, Tileset.NOTHING, Tileset.CELL},
                {Tileset.NOTHING, Tileset.CELL, Tileset.NOTHING, Tileset.CELL}
        };
        GameOfLife student = new GameOfLife(testInput, true);
        // Generate next state from the provided input.
        TETile[][] firstState = student.nextGeneration(testInput);

        /**
         * 4 4
         * 0111
         * 1001
         * 0001
         * 0000
         */
        TETile[][] generationOne = new TETile[][] {
                {Tileset.NOTHING, Tileset.CELL, Tileset.CELL, Tileset.CELL},
                {Tileset.CELL, Tileset.NOTHING, Tileset.NOTHING, Tileset.CELL},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING, Tileset.CELL},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING}
        };
        checkState(firstState, generationOne);

        /**
         * 4 4
         * 0111
         * 0101
         * 0000
         * 0000
         */
        TETile[][] secondState = student.nextGeneration(firstState);
        TETile[][] generationTwo = new TETile[][] {
                {Tileset.NOTHING, Tileset.CELL, Tileset.CELL, Tileset.CELL},
                {Tileset.NOTHING, Tileset.CELL, Tileset.NOTHING, Tileset.CELL},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING}
        };
        checkState(secondState, generationTwo);

        /**
         * 4 4
         * 0101
         * 0101
         * 0000
         * 0000
         */
        TETile[][] thirdState = student.nextGeneration(secondState);
        TETile[][] generationThree = new TETile[][] {
                {Tileset.NOTHING, Tileset.CELL, Tileset.NOTHING, Tileset.CELL},
                {Tileset.NOTHING, Tileset.CELL, Tileset.NOTHING, Tileset.CELL},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING}
        };
        checkState(thirdState, generationThree);
    }

    /**
     * Check for the save format, without generation of the next state, using a random seed
     * as the initial state.
     * @throws IOException
     */
    @Test
    public void onlySave() throws IOException {
        checkIfPatternFilesAreModified();
        /**
         * Should expect the board to be saved like in the following (not including dimensions):
         * 010
         * 011
         * 100
         * 010
         */
        TETile[][] result = new TETile[][] {
                {Tileset.NOTHING, Tileset.CELL, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.CELL, Tileset.CELL},
                {Tileset.CELL, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.CELL, Tileset.NOTHING}
        };

        GameOfLife student = new GameOfLife(result, true);
        student.saveBoard();

        String f1 = newlineReplacer(Files.readString(Paths.get(SAVE_FILE)));
        String f2 = newlineReplacer(Files.readString(Paths.get(SAVE_TEST)));

        assertWithMessage("Checks that saving works and is as expected with the given seed.")
                .that(f1.equals(f2)).isTrue();
    }

    /**
     * Check that loading works and matches expected output.
     * @throws IOException
     */
    @Test
    public void onlyLoad() throws IOException {
        checkIfPatternFilesAreModified();
        GameOfLife student = new GameOfLife(1234567, true);

        TETile[][] result = new TETile[][] {
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING}
        };
        TETile[][] loadResult = student.loadBoard(LOAD_TEST);
        checkState(loadResult, result);
    }

    /**
     * This checks if any of the pattern files have been modified. Modifications include
     * adding a newline, deleting/replacing characters, adding characters, etc.
     * DO NOT MODIFY ANYTHING IN THE FILE (otherwise testing will be harder).
     */
    private static void checkIfPatternFilesAreModified() {
        if (hashFile("boba", BLANK) % 404 != -172) {
            fail("blank.txt has been modified, please restore it back to the original version");
        }
        if (hashFile("panda express", GLIDERGUN) % 404 != -92) {
            fail("glidergun.txt has been modified, please restore it back to the original version");
        }
        if (hashFile("rickroll", HAMMERHEAD) % 404 != -252) {
            fail("hammerhead.txt has been modified, please restore it back to the original version");
        }
        if (hashFile("brrrrrr", PENTADECATHLON) % 404 != 380) {
            fail("pentadecathlon.txt has been modified. Please restore it to the original version.");
        }
        if (hashFile("stop", SAVE_TEST) % 404 != 262) {
            fail("saveTest.txt has been modified. Please restore it to the original version.");
        }
        if (hashFile("watch", LOAD_TEST) % 404 != -75) {
            fail("loadTest.txt has been modified. Please restore it to the original version.");
        }
    }

    public String newlineReplacer(String s) {
        return s.replace("\r", "");
    }

    private static int hashFile(String delimiter, String file) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (IOException e) {
            System.err.println("File does not exist: " + file);
            return 0;
        }
        BufferedReader br = new BufferedReader(fileReader);
        List<String> contents = br.lines().toList();
        return String.join(delimiter, contents).hashCode();
    }
}
