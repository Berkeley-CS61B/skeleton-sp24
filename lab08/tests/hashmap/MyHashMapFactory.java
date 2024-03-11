package hashmap;

import java.util.Collection;

/** This is a helper factory class that allows us to test different bucket types
 * without having to write a separate test for each one. You don't need to understand
 * how this works.
 *
 * Please don't modify this class unless you REALLY know what you're doing.
 *
 * @author Noah Adhikari, Spring 2023
 */
public class MyHashMapFactory {

    /** Returns a MyHashMap with the specified bucket type.
     * @param bucketType the type of bucket to use
     */
    public static <K, V> MyHashMap<K, V> createBucketedMap(Class<? extends Collection> bucketType) {
        return new MyHashMap<>() {
            @Override
            protected Collection<Node> createBucket() {
                try {
                    return bucketType.getConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            // for the timing tests
            @Override
            public String toString() {
                return "MyHashMap with " + bucketType.getSimpleName() + " buckets";
            }
        };
    }
}
