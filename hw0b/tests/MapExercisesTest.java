import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MapExercisesTest {

    @Test
    @Order(0)
    @DisplayName("Test letterToNumber correctness")
    public void testLetterToNum() {
        Map<Character, Integer> map = MapExercises.letterToNum();

        assertThat(map.size()).isEqualTo(26);
        for (int i = 0; i < 26; i++) {
            assertThat(map.get((char) ('a' + i))).isEqualTo(i + 1);
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test squares correctness")
    public void testSquares() {
        List<Integer> lst = List.of(1, 3, 6, 7);

        Map<Integer, Integer> map = MapExercises.squares(lst);
        assertThat(map).containsExactly(
            1, 1,
            3, 9,
            6, 36,
            7, 49
        );
    }

    @Test
    @Order(2)
    @DisplayName("Test countWords correctness")
    public void testCountWords() {
        List<String> lst = List.of(
            "hug", "hug", "hug", "hug",
            "shreyas", "shreyas", "shreyas",
            "ergun", "ergun",
            "cs61b"
        );

        Map<String, Integer> map = MapExercises.countWords(lst);

        assertThat(map).containsExactly(
            "hug", 4,
            "shreyas", 3,
            "ergun", 2,
            "cs61b", 1
        );
    }

}
