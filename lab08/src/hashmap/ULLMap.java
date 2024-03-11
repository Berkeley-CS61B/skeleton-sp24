package hashmap;

import java.util.Iterator;
import java.util.Set;

/**
 * A data structure that uses a linked list to store pairs of keys and values.
 * Any key must appear at most once in the dictionary, but values may appear multiple
 * times. Key operations are get(key), put(key, value), and contains(key) methods. The value
 * associated to a key is the value in the last call to put with that key.
 */
public class ULLMap<K, V>  implements Map61B<K, V> {
    int size = 0;

    /** Returns the value corresponding to KEY or null if no such value exists. */
    public V get(K key) {
        if (list == null) {
            return null;
        }
        Node lookup = list.get(key);
        if (lookup == null) {
            return null;
        }
        return lookup.val;
    }

    @Override
    public int size() {
        return size;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        list = null;
    }

    /**
     * Inserts the key-value pair of KEY and VALUE into this map,
     * replacing the previous value associated to KEY, if any.
     */
    public void put(K key, V val) {
        if (list != null) {
            Node lookup = list.get(key);
            if (lookup == null) {
                list = new Node(key, val, list);
                size = size + 1;
            } else {
                lookup.val = val;
            }
        } else {
            list = new Node(key, val, list);
            size = size + 1;
        }
    }

    /**
     * Returns true if and only if this dictionary contains KEY as the
     * key of some key-value pair.
     */
    public boolean containsKey(K key) {
        if (list == null) {
            return false;
        }
        return list.get(key) != null;
    }

    @Override
    public Iterator<K> iterator() {
        return new ULLMapIter();
    }

    /**
     * Keys and values are stored in a linked list of Node objects.
     * This variable stores the first Node in this linked list.
     */
    private Node list;

    /**
     * Represents one node in the linked list that stores the key-value pairs
     * in the dictionary.
     */
    private class Node {

        /**
         * Stores KEY as the key in this key-value pair, VAL as the value, and
         * NEXT as the next node in the linked list.
         */
        Node(K k, V v, Node n) {
            key = k;
            val = v;
            next = n;
        }

        /**
         * Returns the Node in this linked list of key-value pairs whose key
         * is equal to KEY, or null if no such Node exists.
         */
        Node get(K k) {
            if (k != null && k.equals(key)) {
                return this;
            }
            if (next == null) {
                return null;
            }
            return next.get(k);
        }

        /** Stores the key of the key-value pair of this node in the list. */
        K key;
        /** Stores the value of the key-value pair of this node in the list. */
        V val;
        /** Stores the next Node in the linked list. */
        Node next;

    }

    /** An iterator that iterates over the keys of the dictionary. */
    private class ULLMapIter implements Iterator<K> {

        /**
         * Create a new ULLMapIter by setting cur to the first node in the
         * linked list that stores the key-value pairs.
         */
        ULLMapIter() {
            cur = list;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public K next() {
            K ret = cur.key;
            cur = cur.next;
            return ret;
        }


        /** Stores the current key-value pair. */
        private Node cur;

    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

}

