import jh61b.utils.Reflection;
import org.checkerframework.checker.signature.qual.DotSeparatedIdentifiersOrPrimitiveType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

    @Test
    @DisplayName("get returns correct element")
    void testGet() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        assertThat(deque.get(0)).isEqualTo("a");  // 队首
        assertThat(deque.get(1)).isEqualTo("b");  // 中间
        assertThat(deque.get(2)).isEqualTo("c");  // 队尾
    }

    @Test
    @DisplayName("get returns null for invalid index")
    void testGetOutOfBounds() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("a");

        assertThat(deque.get(-1)).isNull();       // 负数 → null
        assertThat(deque.get(1)).isNull();        // 超出 → null（只有 1 个元素）
        assertThat(deque.get(100)).isNull();      // 远远超出 → null
    }

    @Test
    @DisplayName("isEmpty returns true for new deque, false after add")
    void testIsEmpty() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        assertThat(deque.isEmpty()).isTrue();   // 新建的 deque 是空的

        deque.addLast("a");
        assertThat(deque.isEmpty()).isFalse();  // 加了元素就不空了
    }

    @Test
    @DisplayName("size returns correct count")
    void testSize() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        assertThat(deque.size()).isEqualTo(0);

        deque.addLast("a");
        assertThat(deque.size()).isEqualTo(1);

        deque.addLast("b");
        assertThat(deque.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("toList returns elements in order")
    void testToList() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        List<String> list = deque.toList();
        assertThat(list).containsExactly("a", "b", "c");
    }

    @Test
    @DisplayName("removeFirst returns and removes front element")
    void testRemoveFirst() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        assertThat(deque.removeFirst()).isEqualTo("a");  // 移除队首
        assertThat(deque.size()).isEqualTo(2);           // 只剩 2 个
        assertThat(deque.get(0)).isEqualTo("b");          // b 变成新队首
    }

    @Test
    @DisplayName("removeLast returns and removes back element")
    void testRemoveLast() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        assertThat(deque.removeLast()).isEqualTo("c");   // 移除队尾
        assertThat(deque.size()).isEqualTo(2);
        assertThat(deque.get(1)).isEqualTo("b");          // b 变成新队尾
    }

    @Test
    @DisplayName("removeLast on empty deque returns null")
    void testRemoveLastEmpty() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        assertThat(deque.removeLast()).isNull();
    }
}
