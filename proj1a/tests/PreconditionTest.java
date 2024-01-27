import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.fail;

/** Tests that the LinkedListDeque61B class is structured correctly.
 *  @author Noah Adhikari */
public class PreconditionTest {

    /** Returns the inner class of lld. Asserts there is exactly one inner class. */
    private static Class<?> getLldInnerClass() {
        Class<?>[] innerClasses = LinkedListDeque61B.class.getDeclaredClasses();
        assertWithMessage("LinkedListDeque61B should have exactly one inner class").that(innerClasses).hasLength(1);
        return innerClasses[0];
    }

    /** Returns a stream of all fields in c that are not primitives, synthetic, generic (Object),
     *  or of type nodeClass. */
    private static Stream<Field> getBadFields(Class<?> c, Class<?> nodeClass) {
        return Reflection.getFields(c)
                .filter(f -> !(f.getType().isPrimitive()
                        || f.getType().equals(nodeClass)
                        || f.isSynthetic()
                        || f.getType().equals(Object.class)));
    }

    @Test
    @Order(0)
    @DisplayName("LinkedListDeque61B is structured and generified properly")
    public void genericTest() {
        Class<?> lldClass = LinkedListDeque61B.class;
        int lldNumParams = lldClass.getTypeParameters().length;
        assertWithMessage("LinkedListDeque61B should be generified with one type parameter")
                .that(lldNumParams).isEqualTo(1);
        Class<?>[] innerClasses = lldClass.getDeclaredClasses();
        assertWithMessage("LinkedListDeque61B should have exactly one inner class").that(innerClasses).hasLength(1);
        Class<?> nodeClass = innerClasses[0];
        assertWithMessage("Inner class of LinkedListDeque61B should not be generic. " +
                "(Use the generic type from the outer class?)")
                .that(nodeClass.getTypeParameters()).isEmpty();

        // Convoluted check that value field of node is actually generic instead of Object
        LinkedListDeque61B<Integer> lld = new LinkedListDeque61B<>();
        Field[] fields = lld.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getType().equals(nodeClass)) {
                try {
                    f.setAccessible(true);
                    Object node = f.get(lld);
                    for (Field innerField : node.getClass().getDeclaredFields())
                        if (innerField.getType().equals(Object.class)) { // value field
                            innerField.setAccessible(true);
                            assertWithMessage("Value field of node should be generic")
                                    .that(innerField.getGenericType())
                                    .isNotEqualTo(Object.class);
                        }
                } catch (IllegalAccessException e) {
                    fail(e.getMessage());
                }
            }
        }
    }

    @Test
    @Order(1)
    @DisplayName("LinkedListDeque61B follows a strict doubly-linked topology")
    public void doublyLinkedTest() {
        Class<?> nodeClass = getLldInnerClass();
        Map<Class<?>, Integer> typeCounts = new TreeMap<>(Comparator.comparing(Class::getSimpleName));
        for (Field f : nodeClass.getDeclaredFields()) {
            if (!f.isSynthetic()) {
                typeCounts.merge(f.getType(), 1, Integer::sum);
            }
        }
        assertWithMessage("Node class does not contain exactly two fields of type Node")
                .that(typeCounts.get(nodeClass)).isEqualTo(2);
        assertWithMessage("Node class does not contain exactly one generic value field")
                .that(typeCounts.get(Object.class)).isEqualTo(1);
    }

    @Test
    @Order(2)
    @DisplayName("LinkedListDeque61B has no fields besides nodes and primitives")
    public void noNonTrivialFieldsTest() {
        Class<?> nodeClass = getLldInnerClass();
        Stream<Field> badLldFields = getBadFields(LinkedListDeque61B.class, nodeClass);
        Stream<Field> badNodeFields = getBadFields(nodeClass, nodeClass);
        List<Field> badFields = Stream.concat(badLldFields, badNodeFields).toList();


        String msg = badFields.stream()
                .map(f -> f.getType().getSimpleName() + " " + f.getName())
                .reduce("", (a, b) -> a + "\n\t" + b);

        assertWithMessage("Found fields that are not nodes or primitives, or contain fields that are not nodes or " +
                "primitives:" + msg).that(badFields).isEmpty();
    }

    @Test
    @Order(3)
    @DisplayName("LinkedListDeque61B has only an empty constructor")
    public void noNonTrivialConstructorsTest() {
        Constructor<?>[] ctors = LinkedListDeque61B.class.getConstructors();
        assertWithMessage("Found more than one constructor in LinkedListDeque61B").that(ctors).hasLength(1);
        assertWithMessage("LinkedListDeque61B constructor has more than zero arguments").that(ctors[0].getParameterCount()).isEqualTo(0);
    }
}