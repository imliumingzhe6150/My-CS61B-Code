import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkedListDeque61BTest {

    // ==================== Iterator Tests ====================

    @Nested
    @DisplayName("iterator()")
    class IteratorTests {

        @Test
        @DisplayName("iterates over elements in correct order")
        void testIteratorOrder() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("a");
            deque.addLast("b");
            deque.addLast("c");

            Iterator<String> it = deque.iterator();
            assertThat(it.hasNext()).isTrue();
            assertThat(it.next()).isEqualTo("a");
            assertThat(it.hasNext()).isTrue();
            assertThat(it.next()).isEqualTo("b");
            assertThat(it.hasNext()).isTrue();
            assertThat(it.next()).isEqualTo("c");
            assertThat(it.hasNext()).isFalse();
        }

        @Test
        @DisplayName("foreach loop compiles and iterates correctly")
        void testForEachLoop() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("front");
            deque.addLast("middle");
            deque.addLast("back");

            StringBuilder sb = new StringBuilder();
            for (String s : deque) {
                sb.append(s);
            }
            assertThat(sb.toString()).isEqualTo("frontmiddleback");
        }

        @Test
        @DisplayName("hasNext returns false on empty deque")
        void testIteratorEmpty() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            Iterator<String> it = deque.iterator();
            assertThat(it.hasNext()).isFalse();
        }

        @Test
        @DisplayName("addFirst elements iterated in correct order")
        void testIteratorAddFirst() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addFirst("a");
            deque.addFirst("b");
            deque.addFirst("c");

            // c was added last to front, so it should be first
            Iterator<String> it = deque.iterator();
            assertThat(it.next()).isEqualTo("c");
            assertThat(it.next()).isEqualTo("b");
            assertThat(it.next()).isEqualTo("a");
        }

        @Test
        @DisplayName("next() on exhausted iterator throws NoSuchElementException")
        void testNextThrowsWhenExhausted() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("a");

            Iterator<String> it = deque.iterator();
            it.next(); // consume "a"

            assertThrows(NoSuchElementException.class, it::next);
        }
    }

    // ==================== Equals Tests ====================

    @Nested
    @DisplayName("equals()")
    class EqualsTests {

        @Test
        @DisplayName("same object is equal to itself")
        void testEqualsSameObject() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("a");
            deque.addLast("b");

            assertThat(deque.equals(deque)).isTrue();
        }

        @Test
        @DisplayName("two LinkedListDeque61B with same elements are equal")
        void testEqualsSameElements() {
            Deque61B<String> d1 = new LinkedListDeque61B<>();
            Deque61B<String> d2 = new LinkedListDeque61B<>();

            d1.addLast("front");
            d1.addLast("middle");
            d1.addLast("back");

            d2.addLast("front");
            d2.addLast("middle");
            d2.addLast("back");

            assertThat(d1.equals(d2)).isTrue();
        }

        @Test
        @DisplayName("two empty deques are equal")
        void testEqualsBothEmpty() {
            Deque61B<String> d1 = new LinkedListDeque61B<>();
            Deque61B<String> d2 = new LinkedListDeque61B<>();

            assertThat(d1.equals(d2)).isTrue();
        }

        @Test
        @DisplayName("deques with different sizes are not equal")
        void testEqualsDifferentSize() {
            Deque61B<String> d1 = new LinkedListDeque61B<>();
            Deque61B<String> d2 = new LinkedListDeque61B<>();

            d1.addLast("a");
            d1.addLast("b");

            d2.addLast("a");

            assertThat(d1.equals(d2)).isFalse();
        }

        @Test
        @DisplayName("deques with same size but different elements are not equal")
        void testEqualsDifferentElements() {
            Deque61B<String> d1 = new LinkedListDeque61B<>();
            Deque61B<String> d2 = new LinkedListDeque61B<>();

            d1.addLast("a");
            d1.addLast("b");

            d2.addLast("a");
            d2.addLast("c");

            assertThat(d1.equals(d2)).isFalse();
        }

        @Test
        @DisplayName("deques with same elements in different order are not equal")
        void testEqualsDifferentOrder() {
            Deque61B<String> d1 = new LinkedListDeque61B<>();
            Deque61B<String> d2 = new LinkedListDeque61B<>();

            d1.addLast("a");
            d1.addLast("b");

            d2.addLast("b");
            d2.addLast("a");

            assertThat(d1.equals(d2)).isFalse();
        }

        @Test
        @DisplayName("LinkedListDeque61B equals ArrayDeque61B with same elements")
        void testEqualsCrossType() {
            Deque61B<String> lld = new LinkedListDeque61B<>();
            Deque61B<String> ad = new ArrayDeque61B<>();

            lld.addLast("x");
            lld.addLast("y");

            ad.addLast("x");
            ad.addLast("y");

            assertThat(lld.equals(ad)).isTrue();
            assertThat(ad.equals(lld)).isTrue();
        }

        @Test
        @DisplayName("not equal to null")
        void testEqualsNull() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("a");

            assertThat(deque.equals(null)).isFalse();
        }

        @Test
        @DisplayName("not equal to non-Deque61B object")
        void testEqualsDifferentType() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("a");

            assertThat(deque.equals("not a deque")).isFalse();
        }
    }

    // ==================== ToString Tests ====================

    @Nested
    @DisplayName("toString()")
    class ToStringTests {

        @Test
        @DisplayName("empty deque returns []")
        void testToStringEmpty() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            assertThat(deque.toString()).isEqualTo("[]");
        }

        @Test
        @DisplayName("single element returns [x]")
        void testToStringSingle() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("front");
            assertThat(deque.toString()).isEqualTo("[front]");
        }

        @Test
        @DisplayName("multiple elements return [x, y, z]")
        void testToStringMultiple() {
            Deque61B<String> deque = new LinkedListDeque61B<>();
            deque.addLast("front");
            deque.addLast("middle");
            deque.addLast("back");
            assertThat(deque.toString()).isEqualTo("[front, middle, back]");
        }

        @Test
        @DisplayName("integers formatted correctly")
        void testToStringIntegers() {
            Deque61B<Integer> deque = new LinkedListDeque61B<>();
            deque.addLast(1);
            deque.addLast(2);
            deque.addLast(3);
            assertThat(deque.toString()).isEqualTo("[1, 2, 3]");
        }
    }
}
