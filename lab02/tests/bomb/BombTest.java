package bomb;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.List;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BombTest {
    // DO NOT MODIFY THIS FILE
    // You won't be able to find any passwords here, sorry!
    public static final String BOMB_FILE = "src/bomb/Bomb.java";
    public static String[] lines;

    @Test
    @Tag("phase0")
    @DisplayName("Bomb Phase 0")
    public void testBombPhase0() {
        getBombMainOutputUntil(0);
        assertWithMessage("Phase 0 incorrect").that(lines[0].split("\"")[1].hashCode())
                .isEqualTo(-777276206);
    }

    @Test
    @Tag("phase1")
    @DisplayName("Bomb Phase 1")
    public void testBombPhase1() {
        getBombMainOutputUntil(1);
        assertWithMessage("Phase 1 incorrect").that(lines[1].split("\"")[1].hashCode())
                .isEqualTo(1729584786);
    }

    @Test
    @Tag("phase2")
    @DisplayName("Bomb Phase 2")
    public void testBombPhase2() {
        getBombMainOutputUntil(2);
        assertWithMessage("Phase 2 incorrect")
                .that(lines[2].split("\"")[1].split(" ")[0].hashCode())
                .isEqualTo(1097364068);
    }

    /** Runs up to the given phase in BombMain and modifies the lines variable to have its output.*/
    public static void getBombMainOutputUntil(int phase) {
        checkIfModified();

        PrintStream systemErr = System.err;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        BombMain.answers(new String[]{"" + phase});
        System.setErr(systemErr);

        String output = outputStream.toString();
        lines = output.split("\r?\n");
    }

    private static void checkIfModified() {
        if (hashBomb("cheese", BOMB_FILE) % 891 != 404) {
            fail("Bomb.java has been modified. Please restore it to the original version.");
        }
    }

    private static int hashBomb(String delimiter, String file) {
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
