package hashmap;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import static com.google.common.truth.Truth.assertThat;

/** Tests of optional parts of lab 8. */
public class TestMyHashMapExtra {

    @Test
    public void testRemove() {
        MyHashMap<String, String> q = new MyHashMap<>();
        q.put("c", "a");
        q.put("b", "a");
        q.put("a", "a");
        q.put("d", "a");
        q.put("e", "a"); // a b c d e
        assertThat(q.remove("c")).isNotNull();
        assertThat(q.remove("f")).isNull();
        assertThat(q.containsKey("c")).isFalse();
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("d")).isTrue();
        assertThat(q.containsKey("e")).isTrue();
    }

    /**
     * Remove Test 2
     * Test the 3 different cases of remove
     */
    @Test
    public void testRemoveThreeCases() {
        MyHashMap<String, String> q = new MyHashMap<>();
        q.put("c", "a");
        q.put("b", "a");
        q.put("a", "a");
        q.put("d", "a");
        q.put("e", "a");                         // a b c d e
        assertThat(q.remove("e")).isNotNull();      // a b c d
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("c")).isTrue();
        assertThat(q.containsKey("d")).isTrue();
        assertThat(q.remove("c")).isNotNull();     // a b d
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("d")).isTrue();
        q.put("f", "a");                         // a b d f
        assertThat(q.remove("d")).isNotNull();      // a b f
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("f")).isTrue();
    }

    @Test
    public void sanityKeySetTest() {
        sanityKeySetTest(new MyHashMap<>());
    }

    public static void sanityKeySetTest(MyHashMap<String, Integer> b) {
        HashSet<String> values = new HashSet<String>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
            values.add("hi" + i);
        }
        assertEquals(455, b.size()); //keys are there
        Set<String> keySet = b.keySet();
        assertThat(values).containsExactlyElementsIn(keySet);
        assertThat(keySet).containsExactlyElementsIn(values);
    }
}
