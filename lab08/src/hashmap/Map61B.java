package hashmap;

import java.util.Set;

/* Your implementation should implement this interface. To do so,
 * append "implements Map61B<K, V>" to the end of your "public class..."
 * declaration, though you can and should use other type parameters when
 * necessary.
 */
public interface Map61B<K, V> extends Iterable<K> {

    /** Associates the specified value with the specified key in this map.
     *  If the map already contains the specified key, replaces the key's mapping
     *  with the value specified. */
    void put(K key, V value);

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    V get(K key);

    /** Returns whether this map contains a mapping for the specified key. */
    boolean containsKey(K key);

    /** Returns the number of key-value mappings in this map. */
    int size();

    /** Removes every mapping from this map. */
    void clear();

    /** Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException. */
    Set<K> keySet();

    /** Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    V remove(K key);
}
