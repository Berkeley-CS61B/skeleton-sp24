import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

public class UnionFindTest {

    /**
     * Checks that the initial state of the disjoint sets are correct (this will pass with the skeleton
     * code, but ensure it still passes after all parts are implemented).
     */
    @Test
    public void initialStateTest() {
        UnionFind uf = new UnionFind(4);
        assertThat(uf.connected(0, 1)).isFalse();
        assertThat(uf.connected(0, 2)).isFalse();
        assertThat(uf.connected(0, 3)).isFalse();
        assertThat(uf.connected(1, 2)).isFalse();
        assertThat(uf.connected(1, 3)).isFalse();
        assertThat(uf.connected(2, 3)).isFalse();
    }

    /**
     * Checks that invalid inputs are handled correctly.
     */
    @Test
    public void illegalFindTest() {
        UnionFind uf = new UnionFind(4);
        try {
            uf.find(10);
            fail("Cannot find an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
        try {
            uf.union(1, 10);
            fail("Cannot union with an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Checks that union is done correctly (including the tie-breaking scheme).
     */
    @Test
    public void basicUnionTest() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        assertThat(uf.find(0)).isEqualTo(1);
        uf.union(2, 3);
        assertThat(uf.find(2)).isEqualTo(3);
        uf.union(0, 2);
        assertThat(uf.find(1)).isEqualTo(3);

        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);
        uf.union(4, 8);
        uf.union(4, 6);

        assertThat(uf.find(5)).isEqualTo(9);
        assertThat(uf.find(7)).isEqualTo(9);
        assertThat(uf.find(8)).isEqualTo(9);

        uf.union(9, 2);
        assertThat(uf.find(3)).isEqualTo(9);
    }

    /**
     * Unions the same item with itself. Calls on find and checks that the outputs are correct.
     */
    @Test
    public void sameUnionTest() {
        UnionFind uf = new UnionFind(4);
        uf.union(1, 1);
        for (int i = 0; i < 4; i += 1) {
            assertThat(uf.find(i)).isEqualTo(i);
        }
    }

    /**
     * Write your own tests below here to verify for correctness. The given tests are not comprehensive.
     * Specifically, you may want to write a test for path compression and to check for the correctness
     * of all methods in your implementation.
     */

}


