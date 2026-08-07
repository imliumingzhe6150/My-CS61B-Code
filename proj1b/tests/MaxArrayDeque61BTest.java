import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;

public class MaxArrayDeque61BTest {

    // 按字符串长度比较
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    @DisplayName("max() returns max by constructor comparator")
    void testMaxWithStoredComparator() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addLast("hi");         // 长度 2
        mad.addLast("hello");      // 长度 5
        mad.addLast("hey");        // 长度 3

        assertThat(mad.max()).isEqualTo("hello");  // 最长的是 "hello"
    }

    @Test
    @DisplayName("max() on empty queue returns null")
    void testMaxEmpty() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());

        assertThat(mad.max()).isNull();  // 空的 → null
    }

    @Test
    @DisplayName("max() using naturalOrder with Integers")
    void testMaxWithNaturalOrder() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(Comparator.<Integer>naturalOrder());
        mad.addLast(5);
        mad.addLast(2);
        mad.addLast(99);
        mad.addLast(17);

        assertThat(mad.max()).isEqualTo(99);  // 自然顺序最大是 99
    }

    @Test
    @DisplayName("max(Comparator) with different comparator")
    void testMaxWithDifferentComparator() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addLast("hi");
        mad.addLast("hello");
        mad.addLast("hey");

        // 用自然顺序（字典序）来找最大，而不是长度
        assertThat(mad.max(Comparator.<String>naturalOrder())).isEqualTo("hi");
        // 字典序："hello" < "hey" < "hi" → "hi" 最大
    }

    @Test
    @DisplayName("max(Comparator) on empty queue returns null")
    void testMaxWithComparatorEmpty() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());

        assertThat(mad.max(Comparator.<String>naturalOrder())).isNull();  // 空 → null
    }

    @Test
    @DisplayName("max() with addFirst adds at front")
    void testMaxWithAddFirst() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("a");              // 长度 1
        mad.addFirst("banana");        // 长度 6
        mad.addFirst("pear");          // 长度 4

        // 顺序: pear, banana, a — 最长是 banana
        assertThat(mad.max()).isEqualTo("banana");
    }

    @Test
    @DisplayName("max() with single element")
    void testMaxSingleElement() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(Comparator.<Integer>naturalOrder());
        mad.addLast(42);

        assertThat(mad.max()).isEqualTo(42);
    }

    @Test
    @DisplayName("max() with equal elements returns one of them")
    void testMaxWithEqualElements() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addLast("dog");   // 长度 3
        mad.addLast("cat");   // 长度 3
        mad.addLast("bat");   // 长度 3

        // 长度相同，返回任意一个都行
        String max = mad.max();
        assertThat(max).isNotNull();
        assertThat(max.length()).isEqualTo(3);
    }

    @Test
    @DisplayName("max() after add and remove")
    void testMaxAfterRemove() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(Comparator.<Integer>naturalOrder());
        mad.addLast(10);
        mad.addLast(100);
        mad.addLast(50);

        assertThat(mad.max()).isEqualTo(100);

        mad.removeLast();    // 移除 50
        mad.removeFirst();   // 移除 10

        assertThat(mad.max()).isEqualTo(100);  // 只剩 100
    }
}
