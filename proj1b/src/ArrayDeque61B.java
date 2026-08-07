import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize();
        }
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize();
        }
        items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }

        return list;
    }

    @Override
    public boolean isEmpty() {

        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Deque61B<?> other) {
            if (this.size() != other.size()) {
                return false;
            }
            Iterator<T> it1 = this.iterator();
            Iterator<?> it2 = other.iterator();
            while (it1.hasNext()) {
                if (!it1.next().equals(it2.next())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public T removeFirst() {
        if (items.length >= 16 && size <= items.length / 4) {
            resizeDown();
        }
        if (size == 0) {
            return null;
        }
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        T x = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return x;
    }

    @Override
    public T removeLast() {
        if (items.length >= 16 && size <= items.length / 4) {
            resizeDown();
        }
        if (size == 0) {
            return null;
        }
        nextLast = Math.floorMod(nextLast - 1, items.length);
        T x = items[nextLast];
        items[nextLast] = null;
        size--;
        return x;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[Math.floorMod(index + nextFirst + 1,  items.length)];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resize() {
        T[] newitems = (T[]) new Object[items.length * 2];
        for (int i = 0; i < size; i++) {
            newitems[i] = get(i);
        }

        nextFirst = newitems.length - 1;
        nextLast  = size;
        items = newitems;
    }

    private void resizeDown() {
        T[] newitems = (T[]) new Object[items.length / 2];
        for (int i = 0; i < size; i++) {
            newitems[i] = get(i);
        }
        nextFirst = newitems.length - 1;
        nextLast  = size;
        items = newitems;
    }

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;

        ArrayDequeIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = get(index);
            index++;
            return item;
        }
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}
