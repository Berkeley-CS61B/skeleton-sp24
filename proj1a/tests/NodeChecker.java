import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.fail;

public class NodeChecker {

    /**
     * This method checks whether a given class is a linked node or not.
     *
     * @param clazz        the class you want to check
     * @param doublyLinked whether or not the list <em>can</em> be doubly linked
     */
    public static boolean isNode(Class<?> clazz, boolean doublyLinked) {
        // Get fields
        SortedSet<String> fields = Stream
                .of(clazz.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .map(Field::getName)
                .collect(Collectors.toCollection(TreeSet::new));

        boolean hasData = false;
        int nodeFields = 0;

        // Check fields
        for (String field : fields) {
            Field f = null;
            try {
                f = clazz.getDeclaredField(field);
                f.setAccessible(true);
            } catch (NoSuchFieldException ex) {
                ex.printStackTrace();
                fail();
            }

            if (f.getType().equals(clazz)) {
                // Linked to another node
                nodeFields++;
                if (nodeFields == 2 && !doublyLinked) {
                    // Returns false if the list is doubly linked
                    return false;
                } else if (nodeFields == 3) {
                    // Don't allow triply linked and up
                    return false;
                }
            } else if (f.getType().equals(Object.class)) {
                // Has a generic type to store data
                if (hasData) {
                    // Checks for multiple data fields
                    return false;
                }
                hasData = true;
            } else {
                return false;
            }
        }

        // Get constructors
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        // Checks arguments to the constructors
        for (Constructor<?> c : constructors) {
            boolean hasGenericArgument = false;
            int nodeArguments = 0;
            Class<?>[] paramTypes = c.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i == 0 && !Modifier.isStatic(clazz.getModifiers())) {
                    continue;
                }
                Class<?> type = paramTypes[i];
                if (type.equals(Object.class)) {
                    if (hasGenericArgument) {
                        // Checks for multiple value arguments
                        return false;
                    }
                    hasGenericArgument = true;
                } else if (type.equals(clazz)) {
                    // Pointers to self are fine.
                    nodeArguments++;
                    if (nodeArguments == 2 && !doublyLinked) {
                        return false;
                    } else if (nodeArguments == 3) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        return hasData && ((nodeFields == 2) == doublyLinked);
    }

    /**
     * This method performs a node check on every internal class.
     *
     * @param clazz        the class you want to check
     * @param doublyLinked whether or not the list <em>can</em> be doubly linked
     */
    public static void assertUniqueNodeClass(Class<?> clazz, boolean doublyLinked) {
        Class<?>[] classes = clazz.getDeclaredClasses();
        boolean foundNode = false;
        for (Class<?> c : classes) {
            if (isNode(c, doublyLinked)) {
                if (foundNode) {
                    fail("Multiple node classes found in " + clazz.getName() + ". You should only have 1!");
                }
                foundNode = true;
            }
        }

        if (!foundNode) {
            fail("Found 0 node classes in " + clazz.getName());
        }
    }

    /**
     * This method gets a valid, internal node class from a given class.
     *
     * @param clazz        the class you want to check
     * @param doublyLinked whether or not the list <em>can</em> be doubly linked
     * @return the node class
     */
    public static Class<?> getNodeClass(Class<?> clazz, boolean doublyLinked) {
        for (Class<?> c : clazz.getDeclaredClasses()) {
            if (isNode(c, doublyLinked)) {
                return c;
            }
        }
        return null;
    }

    /**
     * This method gets fields of specified type from a given class.
     *
     * @param clazz the class you want to check
     * @param type  the type of field you want
     * @return a list of fields matching the given type
     */
    private static List<Field> getFields(Class<?> clazz, Class<?> type) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> namedFields = new ArrayList<>();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getType().equals(type)) {
                namedFields.add(f);
            }
        }
        return namedFields;
    }

    /**
     * This method checks whether iterating through a list forwards and backwards
     * returns the same values.
     *
     * @param deque the deque you want to check
     * @param <T>   the generic type of the data field in a linked node
     */
    public static <T> void assertNextPrevConsistency(Deque61B<T> deque) {
        if (deque.size() == 0) {
            // Too many topologies to handle here, so just skip.
            return;
        }
        // Grab the linked node class and possible pointers to the head and tail
        Class<?> nodeClass = getNodeClass(deque.getClass(), true);
        if (nodeClass == null) {
            fail("Found 0 doubly-linked node classes in " + deque.getClass().getName());
        }
        List<Field> dequePointers = getFields(deque.getClass(), nodeClass);

        if (dequePointers.size() == 1) {
            // Working with a sentinel node, so just double up
            // The "end" is the same as the "start".
            dequePointers.add(dequePointers.get(0));
        }
        assertWithMessage("Deque61B does not have 1 or 2 node fields").that(dequePointers.size()).isEqualTo(2);
        try {
            Field startField = dequePointers.get(0);
            Field endField = dequePointers.get(1);
            Object start = startField.get(deque);
            Object end = endField.get(deque);

            List<Field> pointers = getFields(nodeClass, nodeClass);
            Field nextField = pointers.get(0);
            Field prevField = pointers.get(1);

            int steps = 1;
            Object curr = nextField.get(start);
            while (curr != null && curr != end && steps <= deque.size()) {
                Object currNext = nextField.get(curr);
                Object currPrev = prevField.get(curr);

                if (currNext != null) {
                    String message = "After following the " + nextField.getName() + " pointer " + steps + " times from "
                            + startField.getName() + ", curr." + nextField.getName() + "." + prevField.getName()
                            + " is not curr";
                    assertWithMessage(message)
                            .that(prevField.get(currNext))
                            .isSameInstanceAs(curr);
                }
                if (currPrev != null) {
                    String message = "After following the " + nextField.getName() + " pointer " + steps + " times from "
                            + startField.getName() + ", curr." + prevField.getName() + "." + nextField.getName()
                            + " is not curr";
                    assertWithMessage(message)
                            .that(nextField.get(currPrev))
                            .isSameInstanceAs(curr);
                }

                curr = currNext;
                steps++;
            }
            if (curr != null && curr != end) {
                fail("Followed the " + nextField.getName() + " pointer " + steps + " times from "
                        + startField.getName() + " in deque of size "
                        + deque.size() + ", but did not reach end pointer " + endField.getName());
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            fail("Internal error.");
        }
    }

}
