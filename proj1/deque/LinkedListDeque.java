package deque;

public class LinkedListDeque <T> implements Deque <T>{

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
    public T get(int n) {
        int i = 0;
        IntNode p = sentinel.next;

        while (i != n) {
            if (p.next != sentinel) {
                p = p.next;
                i++;
            } else {
                return null;
            }
        }
        return p.item;
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

    public IntNode sentinelNext() {
        return sentinel.next;
    }

    public Iterable <T> iterable() {
        return null;
    }

    public boolean equals(Object o){
        return false;
    }

    public T lastItem() {
        return sentinel.prev.item;
    }
    public T firstItem() {
        if (size == 0) {
            return null;
        }
        return sentinel.next.item;
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> L1 = new LinkedListDeque<Integer>();
        for (int i = 0; i <= 20; i++) {
            L1.addLast(i);
        }
        System.out.println(L1.lastItem());
    }
}

