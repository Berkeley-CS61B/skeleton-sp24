package puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/* NOTE:
 * Don't modify any code in this file!
 * Otherwise, you'll probably fail the tests.
 * If you accidentally modify it, you can always
 * restore back to the skeleton code for this specific file.
 */

public class Puzzle {

    static final File ANSWER_FILE = new File("src/puzzle/answer.txt");
    static int guessThis = 0;

    public static int puzzle() {
        int answer = loadAnswer(ANSWER_FILE);

        if (isCorrect(answer)) {
            System.out.println("That's correct! Nice work!");
            return answer;
        }

        Random r = new Random();
        r.setSeed(1678_971_254);
        for (int i = 0; i < 1323; i++) {
            if (r.nextInt() == -2034104197) {
                erroringMethod(r);
            }
        }
        return 0;
    }

    /** Loads an answer from the given file.
     *  Note: Scanner behaves very similarly to the In class. */
    public static int loadAnswer(File file) {
        Scanner s;
        try {
            s = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (s.hasNextLine()) {
            if (s.hasNextInt()) {
                return s.nextInt();
            }
            s.nextLine();
        }
        throw new RuntimeException("Could not find answer in " + file);
    }

    private static boolean isCorrect(int answer) {
        return ("" + answer).hashCode() == -32772622;
    }

    private static void erroringMethod(Random r) {
        String s = null;
        System.out.println("""
                Hmm, what is the value of `guessThis` when the out-of-bounds exception is thrown?
                Replace the first line of `answer.txt` accordingly.
                Hint: Use an exception breakpoint.""");
        while (r.nextInt(100) != 10) {
            guessThis += r.nextInt();
            s = LOTS_OF_STRINGS[r.nextInt(LOTS_OF_STRINGS.length + 1)];
        }
    }

    public static void main(String[] args) {
        puzzle();
    }

    private static final String[] LOTS_OF_STRINGS = {
            "According",
            "to",
            "all",
            "known",
            "laws",
            "of",
            "aviation,",
            "there",
            "is",
            "no",
            "way",
            "a",
            "bee",
            "should",
            "be",
            "able",
            "to",
            "fly.",
            "Its",
            "wings",
            "are",
            "too",
            "small",
            "to",
            "get",
            "its",
            "fat",
            "little",
            "body",
            "off",
            "the",
            "ground."
    };
}
