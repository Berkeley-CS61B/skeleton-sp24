package puzzle;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class PuzzleTest {
    static final File PUZZLE_FILE = new File("src/puzzle/Puzzle.java");
    static final File PUZZLE_REFERENCE = new File("tests/puzzle/PuzzleReference.txt");

    @Test
    public void testPuzzle() {
        assertThat(("" + Puzzle.puzzle()).hashCode()).isEqualTo(-32772622);

        assertWithMessage("Looks like you modified Puzzle.java! Be sure to undo those changes.")
                .that(readAllLines(PUZZLE_REFERENCE)).isEqualTo(readAllLines(PUZZLE_FILE));
    }

    /** Read all lines of a file into a string list, trimming whitespace. */
    private List<String> readAllLines(File f) {
        Scanner s;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String> lst = new ArrayList<>();
        while (s.hasNextLine()) {
            String clean = s.nextLine().trim();
            if (!clean.isEmpty()) {
                lst.add(clean);
            }
        }
        return lst;
    }
}
