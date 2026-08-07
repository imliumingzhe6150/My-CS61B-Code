import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    @Override
    public void addFirst(T x) {
        Node n = new Node(x);
        n.prev = sentinel;
        n.next = sentinel.next;
        sentinel.next.prev = n;
        sentinel.next = n;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node n = new Node(x);
        n.prev = sentinel.prev;
        n.next = sentinel;
        sentinel.prev.next = n;
        sentinel.prev = n;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnlist = new ArrayList<>();
        Node p = sentinel.next;
        while (p != sentinel) {
            returnlist.add(p.item);
            p = p.next;
        }
        return returnlist;
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
        if (size == 0) {
            return null;
        }
        Node removed = sentinel.next;
        sentinel.next = removed.next;
        removed.next.prev = sentinel;
        size--;
        return removed.item;

    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node removed = sentinel.prev;
        sentinel.prev =  removed.prev;
        removed.prev.next = sentinel;
        size--;
        return removed.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;

    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        return getrecurisiveHelper(sentinel.next, index);
    }

    private T getrecurisiveHelper(Node p, int index) {
        if (index == 0) {
            return p.item;
        }
        return getrecurisiveHelper(p.next, index - 1);
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    private class Node {
        Node prev;
        Node next;
        T item;
        Node(T item) {
            this.item = item;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node current;
        public LinkedListIterator() {
            current = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            return item;
        }
    }

    @Override
    public String toString() {
        return toList().toString();
    }
}

