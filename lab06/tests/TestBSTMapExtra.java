import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/** Tests of optional parts of lab 7. */
public class TestBSTMapExtra {

    /*
     * Sanity test for keySet, only here because it's optional
     */
    @Test
    public void sanityKeySetTest() {
        BSTMap<String, Integer> b = new BSTMap<>();
        TreeSet<String> values = new TreeSet<>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
            values.add("hi" + i);
        }
        assertThat(b.size()).isEqualTo(455); //keys are there
        Set<String> keySet = b.keySet();
        assertThat(values).containsExactlyElementsIn(keySet).inOrder();
        assertThat(keySet).containsExactlyElementsIn(values).inOrder();
    }

    /* Remove Test
     *
     * Note for testRemoveRoot:
     *
     * Just checking that c is gone (perhaps incorrectly)
     * assumes that remove is BST-structure preserving.
     *
     * More exhaustive tests could be done to verify
     * implementation of remove, but that would require doing
     * things like checking for inorder vs. preorder swaps,
     * and is unnecessary in this simple BST implementation.
     */
    @Test
    public void testRemoveRoot() {
        BSTMap<String, String> q = new BSTMap<>();
        q.put("c", "a");
        q.put("b", "a");
        q.put("a", "a");
        q.put("d", "a");
        q.put("e", "a"); // a b c d e
        assertThat(q.remove("c")).isEqualTo("a");
        assertThat(q.containsKey("c")).isFalse();
        assertThat(q.remove("c")).isNull();
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("d")).isTrue();
        assertThat(q.containsKey("e")).isTrue();

    }

    /* Remove Test 2
     * test the 3 different cases of remove
     */
    @Test
    public void testRemoveThreeCases() {
        BSTMap<String,String> q = new BSTMap<>();
        q.put("c","a");
        q.put("b","a");
        q.put("a","a");
        q.put("d","a");
        q.put("e","a");                         // a b c d e
        assertThat(q.remove("e")).isNotNull();      // a b c d
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("c")).isTrue();
        assertThat(q.containsKey("d")).isTrue();
        assertThat(q.remove("c")).isNotNull();      // a b d
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("d")).isTrue();
        q.put("f","a");                         // a b d f
        assertThat(q.remove("d")).isNotNull();      // a b f
        assertThat(q.containsKey("a")).isTrue();
        assertThat(q.containsKey("b")).isTrue();
        assertThat(q.containsKey("f")).isTrue();
    }

    /* Remove Test 3
     *  Checks that remove works correctly on root nodes
     *  when the node has only 1 or 0 children on either side. */
    @Test
    public void testRemoveRootEdge() {
        BSTMap<Character, Integer> rightChild = new BSTMap<>();
        rightChild.put('A', 1);
        rightChild.put('B', 2);
        assertThat(rightChild.remove('A')).isEqualTo(1);
        for (int i = 0; i < 10; i++) {
            rightChild.put((char) ('C' + i), 3 + i);
        }
        rightChild.put('A', 100);
        assertThat(rightChild.remove('D')).isEqualTo(4);
        assertThat(rightChild.remove('G')).isEqualTo(7);
        assertThat(rightChild.remove('A')).isEqualTo(100);
        assertThat(rightChild.size()).isEqualTo(9);

        BSTMap<Character, Integer> leftChild = new BSTMap<>();
        leftChild.put('B', 1);
        leftChild.put('A', 2);
        assertThat(leftChild.remove('B')).isEqualTo(1);
        assertThat(leftChild.size()).isEqualTo(1);
        assertThat(leftChild.get('B')).isNull();

        BSTMap<Character, Integer> noChild = new BSTMap<>();
        noChild.put('Z', 15);
        assertThat(noChild.remove('Z')).isEqualTo(15);
        assertThat(noChild.size()).isEqualTo(0);
        assertThat(noChild.get('Z')).isNull();
    }

}
