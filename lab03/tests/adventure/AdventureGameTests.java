package adventure;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import helpers.CaptureSystemOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertWithMessage;

@CaptureSystemOutput
public class AdventureGameTests {
    static final String DATA_PATH = "tests/data/";
    static final Class<?> BEE_CLASS = BeeCountingStage.class;
    static final Class<?> SPECIES_CLASS = SpeciesListStage.class;
    static final Class<?> PALINDROME_CLASS = PalindromeStage.class;
    static final Class<?> MACHINE_CLASS = MachineStage.class;
    static final Class<?> GAME_CLASS = AdventureGame.class;

    /** Returns a game starting at the given stage class for the given input file. */
    private AdventureGame getGameStartingAt(Class<?> stageClass) {
        In in = new In(new File(DATA_PATH + stageClass.getSimpleName() + "/input.txt"));
        StdRandom.setSeed(1337);
        AdventureStage stage;
        try {
            stage = (AdventureStage) stageClass.getConstructor(In.class).newInstance(in);
        } catch (InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return new AdventureGame(in, stage);
    }

    private void compareOutputToExpected(Class<?> clazz, CaptureSystemOutput.OutputCapture capture) {
        String expected = new In(new File(DATA_PATH + clazz.getSimpleName() + "/answers.txt")).readAll();
        String cleanedExpected = expected.replace("\r\n", "\n").strip();
        String cleanedCapture = capture.toString().replace("\r\n", "\n").strip();

        assertWithMessage("Game outputs for " + clazz.getSimpleName() + " did not match")
                .that(cleanedCapture).isEqualTo(cleanedExpected);
    }

    private static Stream<Arguments> argumentsForTestStage() {
        return Stream.of(
                Arguments.of(Named.of("BeeCountingStage", BEE_CLASS)),
                Arguments.of(Named.of("SpeciesListStage", SPECIES_CLASS)),
                Arguments.of(Named.of("PalindromeStage", PALINDROME_CLASS)),
                Arguments.of(Named.of("MachineStage", MACHINE_CLASS))
        );
    }

    @DisplayName("Individual stage tests")
    @ParameterizedTest
    @MethodSource("argumentsForTestStage")
    public void testStage(Class<?> stage, CaptureSystemOutput.OutputCapture capture) {
        getGameStartingAt(stage).handleStage();
        compareOutputToExpected(stage, capture);
    }

}