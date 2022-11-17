package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable,V> implements Map61B<K,V> {

    private class BSTNode{
        public K key;
        public V value;
        public BSTNode right, left;
        public int size;

        public BSTNode(K key, V value, BSTNode right, BSTNode left) {
            this.key = key;
            this.value = value;
            this.right = right;
            this.left = left;
            this.size = 0;

        }
    }
    /* size of BSTMap */
    private int size;

    /* root node */
    private BSTNode root;

    /* BSTMap constructor. Initially creates an empty BSTNode with null values. */
    public BSTMap() {
        root = new BSTNode(null, null, null, null);
        size = 0;
    }

    @Override
    public void clear() {
        root = new BSTNode(null, null, null, null);
    }

    @Override
    public boolean containsKey(K key) {
        return containsHelper(key, root);
    }

    private boolean containsHelper(K key, BSTNode node){
        if (key == null) {
            throw new IllegalArgumentException("calls containsKey() with a null key");
        }
        if (node == null || root.key == null) {
            return false;
        }
        if (key.compareTo(node.key) == 0) {
            return true;
        }
        int cmp = key.compareTo(node.key);
        boolean res = false;
        if (cmp < 0) {
            res = containsHelper(key, node.left);
            return res;
        }
        else if (cmp > 0) {
            res = containsHelper(key, node.right);
            return res;
        }
        return false;
    }

    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    private V getHelper(K key, BSTNode node) {
        if (node == null || root.key == null) {
            return null;
        }
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        int cmp = key.compareTo(node.key);
        V res;
        if (cmp < 0) {
            res =getHelper(key, node.left);
            return res;
        }
        else if (cmp > 0) {
            res = getHelper(key, node.right);
            return res;
        }
        return node.value;
    }

    private V getHelper2(K key, BSTNode node) {
        return null;
    }


    @Override
    public int size() {
        return root.size;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("call put() with null key");
        }
        root = put(key, value, root);
        root.size += 1;
    }

    private BSTNode put(K key, V value, BSTNode node){
        if (node == null) {
            return new BSTNode(key, value, null, null);
        }
        if (root.key == null) {
            root.key = key;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(key, value, node.left);
        }
        else if (cmp > 0) {
            node.right = put(key, value, node.right);
        }
        else {
            node.value = value;
        }
        return node;
    }

    public void printInOrder(){

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {

    }
}
