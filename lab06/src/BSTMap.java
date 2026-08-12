import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    int size = 0;

    private class BSTNode {
        K key;
        V value;
        BSTNode left, right;

        BSTNode(K k, V v, BSTNode l, BSTNode r) {
            key = k;
            value = v;
            left = l;
            right = r;
        }
    }

    private BSTNode root;

    private BSTNode putHelper(BSTNode root, K k, V v) {
        if (root == null) {
            root = new BSTNode(k, v, null, null);
            size++;
            return root;
        }
        if (root.key.compareTo(k) == 0) {
            root.value = v;
            return root;
        }
        if (root.key.compareTo(k) > 0) {
            root.left = putHelper(root.left, k, v);
        }
        if (root.key.compareTo(k) < 0) {
            root.right = putHelper(root.right, k, v);
        }
        return root;
    }

    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
    }

    private V getHelper(BSTNode root, K k) {
        if (root == null) {
            return null;
        }
        if (root.key.compareTo(k) == 0) {
            return root.value;
        }
        if (root.key.compareTo(k) > 0) {
            return getHelper(root.left, k);
        }
        return getHelper(root.right, k);
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private boolean containHelper(BSTNode root, K k) {
        if (root == null) {
            return false;
        }
        if (root.key.compareTo(k) == 0) {
            return true;
        }
        if (root.key.compareTo(k) > 0) {
            return containHelper(root.left, k);
        }
        return containHelper(root.right, k);
    }

    @Override
    public boolean containsKey(K key) {
        return containHelper(root, key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(BSTNode root) {
        if (root == null) {
            return;
        }
        K k = root.key;
        printInOrder(root.left);
        System.out.print(k + " ");
        printInOrder(root.right);
    }

    @Override
    public Set<K> keySet() {
        return new TreeSet<>(collectKeysInOrder());
    }

    private BSTNode removeHelper(BSTNode root, K k) {
        if (root == null) {
            return null;
        }
        if (k.compareTo(root.key) < 0) {
            root.left = removeHelper(root.left, k);
        }
        if (k.compareTo(root.key) > 0) {
            root.right = removeHelper(root.right, k);
        }
        if (k.compareTo(root.key) == 0) {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            BSTNode successor = findMin(root.right);
            root.key = successor.key;
            root.value = successor.value;
            root.right = removeHelper(root.right, successor.key);
        }
        return root;
    }

    private BSTNode findMin(BSTNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        if (value != null) {
            root = removeHelper(root, key);
            size--;
        }
        return value;
    }

    public List<K> collectKeysInOrder() {
        List<K> keys = new ArrayList<>();
        collectKeysInOrder(root, keys);
        return keys;
    }

    private void collectKeysInOrder (BSTNode root, List<K> keys) {
        if (root == null) {
            return;
        }
        K k = root.key;
        collectKeysInOrder(root.left, keys);
        keys.add(k);
        collectKeysInOrder(root.right, keys);
    }

    @Override
    public Iterator<K> iterator() {
        return collectKeysInOrder().iterator();
    }
}
