import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxitem = get(0);
        for (int i = 1; i < this.size(); i++) {
            T current = get(i);
            if (c.compare(current, maxitem) > 0) {
                maxitem = current;
            }
        }
        return maxitem;
    }
}
