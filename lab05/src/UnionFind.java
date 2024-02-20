public class UnionFind {
    // TODO: Instance variables

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        return -1;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return -1;
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return false;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        return -1;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
    }

}
