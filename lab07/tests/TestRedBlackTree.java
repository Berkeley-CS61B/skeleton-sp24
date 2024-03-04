import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;



public class TestRedBlackTree {

    /*
     * Test class for RedBlackTree.java
     *
     * We've provided LLRB Tree representations after every operations in this file as comments to help you debug.
     *
     *
     * Black Nodes are represented with () and red nodes are represented with ()*
     * Left children are listed before right children.
     */

    /*
    Tests for a very basic case of rotating right. This does not check for color flips, but only if the nodes are in the proper
    place after rotating right. Note that we have not provided any basic tests for rotate left, but implementation details for
    rotate right and rotate left should be symmetrical.
     */
    @Test
    public void testBasicRotateRight() {
        // Insert 10, 9, 8
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        assertThat(rbtree.root).isNull();

        RedBlackTree.RBTreeNode<Integer> node1 = new RedBlackTree.RBTreeNode<>(true, 10, null, null);
        RedBlackTree.RBTreeNode<Integer> node2 = new RedBlackTree.RBTreeNode<>(false, 9, null, null);
        RedBlackTree.RBTreeNode<Integer> node3 = new RedBlackTree.RBTreeNode<>(false, 8, null, null);
        node1.left = node2;
        node2.left = node3;

        RedBlackTree.RBTreeNode<Integer> newRoot = rbtree.rotateRight(node1);
        assertThat(newRoot.item).isEqualTo(9);
        assertThat(newRoot.right.item).isEqualTo(10);
        assertThat(newRoot.left.item).isEqualTo(8);
    }

    @Test
    public void testInsertSimple() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        /*
        LLRB Tree representation:

         */
        assertThat(rbtree.root).isNull();


        rbtree.insert(10);
        
        /*
        LLRB Tree representation:
           (10)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(10);

        // left
        assertThat(rbtree.root.left).isNull();

        // right
        assertThat(rbtree.root.right).isNull();

        rbtree.insert(5);

        /*
        LLRB Tree representation:
            (10)
            └── (5)*

         */


        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(10);

        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isFalse();
        assertThat(rbtree.root.left.item).isEqualTo(5);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5) in order").that(callsToFlipColors).isEqualTo(0);
        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5) in order").that(callsToRotateLeft).isEqualTo(0);
        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5) in order").that(callsToRotateRight).isEqualTo(0);

    }

    @Test
    public void testInsertFlipColor() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        rbtree.insert(10);
        rbtree.insert(5);
        rbtree.insert(15);

        /*
        LLRB Tree Representation:
            (10)
            ├── (5)
            └── (15)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(10);


        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(5);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(15);

        // right.left
        assertThat(rbtree.root.right.left).isNull();

        // right.right
        assertThat(rbtree.root.right.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 5, 15) in order").that(callsToFlipColors).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Left after inserting (10, 5, 15) in order").that(callsToRotateLeft).isEqualTo(0);
        assertWithMessage("Number of Calls to Rotate Right after inserting (10, 5, 15) in order").that(callsToRotateRight).isEqualTo(0);
    }


    @Test
    public void testInsertRotateLeft() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        rbtree.insert(10);
        rbtree.insert(15);

        /*
        LLRB Tree Representation:
            (15)
            └── (10)*

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(15);


        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isFalse();
        assertThat(rbtree.root.left.item).isEqualTo(10);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 15) in order").that(callsToFlipColors).isEqualTo(0);
        assertWithMessage("Number of Calls to Rotate Left after inserting (10, 15) in order").that(callsToRotateLeft).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Right after inserting (10, 15) in order").that(callsToRotateRight).isEqualTo(0);
    }


    @Test
    public void testInsertRotateRight() {

        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();
        rbtree.insert(10);
        rbtree.insert(5);
        rbtree.insert(3);

        /*
        LLRB Tree Representation:
            (5)
            └── (3)
            └── (10)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(5);


        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(3);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();


        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(10);

        // right.left
        assertThat(rbtree.root.right.left).isNull();

        // right.right
        assertThat(rbtree.root.right.right).isNull();

        // Not possible to test rotate right without calling color flip in the same insert, if implemented correctly
        assertWithMessage("Number of Calls to Flip Colors after inserting (5, 3, 10) in order").that(callsToFlipColors).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Left after inserting (5, 3, 10) in order").that(callsToRotateLeft).isEqualTo(0);
        assertWithMessage("Number of Calls to Rotate Right after inserting (5, 3, 10) in order").that(callsToRotateRight).isEqualTo(1);
    }


    @Test
    public void testInsertAllFixes() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        rbtree.insert(10);
        rbtree.insert(5);
        rbtree.insert(7);

        /*
        LLRB Tree Representation:
            (7)
            ├── (5)
            └── (10)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(7);

        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(5);

        // left.left
        assertThat(rbtree.root.left.left).isNull();

        // left.right
        assertThat(rbtree.root.left.right).isNull();

        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(10);

        // right.left
        assertThat(rbtree.root.right.left).isNull();

        // right.right
        assertThat(rbtree.root.right.right).isNull();

        assertWithMessage("Number of Calls to Flip Colors after inserting (10, 7, 5) in order").that(callsToFlipColors).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Left after inserting (10, 7, 5) in order").that(callsToRotateLeft).isEqualTo(1);
        assertWithMessage("Number of Calls to Rotate Right after inserting (10, 7, 5) in order").that(callsToRotateRight).isEqualTo(1);
    }


    @Test
    public void testInsertUpwardPropagation() {
        RedBlackTree<Integer> rbtree = new TestableRedBlackTree();

        rbtree.insert(5);
        rbtree.insert(11);
        rbtree.insert(3);
        rbtree.insert(9);
        rbtree.insert(7);
        rbtree.insert(1);
        rbtree.insert(2);

        /*
        LLRB Tree Representation:
            (5)
            ├── (2)
            │   ├── (1)
            │   └── (3)
            └── (9)
                ├── (7)
                └── (11)

         */

        // root
        assertThat(rbtree.root).isNotNull();
        assertThat(rbtree.root.isBlack).isTrue();
        assertThat(rbtree.root.item).isEqualTo(5);

        // left
        assertThat(rbtree.root.left).isNotNull();
        assertThat(rbtree.root.left.isBlack).isTrue();
        assertThat(rbtree.root.left.item).isEqualTo(2);

        // left.left
        assertThat(rbtree.root.left.left).isNotNull();
        assertThat(rbtree.root.left.left.isBlack).isTrue();
        assertThat(rbtree.root.left.left.item).isEqualTo(1);

        // left.right
        assertThat(rbtree.root.left.right).isNotNull();
        assertThat(rbtree.root.left.right.isBlack).isTrue();
        assertThat(rbtree.root.left.right.item).isEqualTo(3);

        // right
        assertThat(rbtree.root.right).isNotNull();
        assertThat(rbtree.root.right.isBlack).isTrue();
        assertThat(rbtree.root.right.item).isEqualTo(9);

        // right.left
        assertThat(rbtree.root.right.left).isNotNull();
        assertThat(rbtree.root.right.left.isBlack).isTrue();
        assertThat(rbtree.root.right.left.item).isEqualTo(7);

        // right.right
        assertThat(rbtree.root.right.right).isNotNull();
        assertThat(rbtree.root.right.right.isBlack).isTrue();
        assertThat(rbtree.root.right.right.item).isEqualTo(11);

        assertWithMessage("Number of Calls to Flip Colors after inserting (5, 11, 3, 9, 7, 1, 2) in order").that(callsToFlipColors).isEqualTo(4);
        assertWithMessage("Number of Calls to Rotate Left after inserting (5, 11, 3, 9, 7, 1, 2) in order").that(callsToRotateLeft).isEqualTo(3);
        assertWithMessage("Number of Calls to Rotate Right after inserting (5, 11, 3, 9, 7, 1, 2) in order").that(callsToRotateRight).isEqualTo(4);
        
    }


    /*
     * Just super neat class to test the number of times your LLRB Tree implementation makes calls to it's
     * "fixing" operations.
     */
    class TestableRedBlackTree extends RedBlackTree<Integer> {

        @Override
        void flipColors(RBTreeNode<Integer> node) {
            callsToFlipColors++;
            super.flipColors(node);
        }

        @Override
        RBTreeNode<Integer> rotateRight(RBTreeNode<Integer> node) {
            callsToRotateRight++;
            return super.rotateRight(node);
        }

        @Override
        RBTreeNode<Integer> rotateLeft(RBTreeNode<Integer> node) {
            callsToRotateLeft++;
            return super.rotateLeft(node);
        }
        
    }

    private int callsToFlipColors = 0;
    private int callsToRotateRight = 0;
    private int callsToRotateLeft = 0;
}
