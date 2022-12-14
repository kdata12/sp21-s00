package deque;

import java.util.Iterator;

public class LinkedListDeque <T> implements Deque <T>, Iterable<T>{

    private class IntNode {
        public T item;
        public IntNode next;
        public IntNode prev;

        public IntNode (T i, IntNode n, IntNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private IntNode sentinel;
    private int size;

    /** sentinel node */
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel.prev;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x){
        sentinel = new IntNode(null, null, null);
        sentinel.next = new IntNode(x, sentinel, sentinel);
        sentinel.prev = sentinel;
        size = 1;
    }

    @Override
    public void addFirst(T x) {
        if (size > 0) {
            sentinel.next = new IntNode(x, sentinel.next, sentinel);
            sentinel.next.next.prev = sentinel.next;
            size += 1;
            return;
        }
        sentinel.next = new IntNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        sentinel.prev.next = new IntNode(x, sentinel, sentinel.prev);
        sentinel.prev = sentinel.prev.next;

        size += 1;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        IntNode k = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;

        return k.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        IntNode k = sentinel.prev;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return k.item;
    }

    @Override
    public int size() {
        return size;
    }

    /** get item at the given index */
    @Override
    public T get(int index) {
        int i = 0;
        IntNode p = sentinel.next;

        while (i != index) {
            if (p.next != sentinel) {
                p = p.next;
                i++;
            } else {
                return null;
            }
        }
        return p.item;
    }

    private T getRecursiveHelper(int index, IntNode node, int start) {
        if (start == index) {
            return node.item;
        }
        start += 1;
        return getRecursiveHelper(index, node.next, start);
    }

    public T getRecursive(int index) {
        IntNode node = sentinel.next;
        int startIndex = 0;

        if (index < 0) {
            return null;
        }

        return getRecursiveHelper(index, node, startIndex);
    }

    @Override
    public void printDeque() {
        IntNode p = sentinel.next;
        while (p.next != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        if (p.next == sentinel){
            System.out.println(p.item + " ");
        }
        System.out.println();
    }

    @Override
    public Iterator<T> iterator(){
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private int wizPos;

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T item = get(wizPos);
            wizPos += 1;
            return item;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> oa = (Deque<T>) o;
        if (oa.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i += 1) {
            if (!(oa.get(i).equals(this.get(i)))) {
                return false;
            }
        }
        return true;
    }

    private T lastItem() {
        return sentinel.prev.item;
    }

    private T firstItem() {
        if (size == 0) {
            return null;
        }
        return sentinel.next.item;
    }

}

