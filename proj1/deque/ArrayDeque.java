package deque;

public class ArrayDeque <T> {
    private int size;
    private T[] items;
    private int front;
    private int rear;

    /* linear array */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    public void resize(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(items,0, newArray,0, size);
        items = newArray;
    }
    /* enqueue front of array */
    public void addFirst(T x) {
    }

    /** Inserts X into the back of the list. */
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = x;
        size += 1;
    }

    /** Returns the item from the back of the list. */
    public T getLast() {
        return items[size - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public T get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public T removeLast() {
        double usageRatio = size / (double) items.length;
        double minimumUsage = 0.25;
        if (items.length >= 16 && usageRatio < minimumUsage){
            resize(items.length / 2);
        }

        T k = getLast();
        items[size-1] = null;
        size -= 1;

        return k;
    }

    private int itemLength() {
        return items.length;
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> a1 = new ArrayDeque<Integer>();

        for (int i = 0; i < 11; i++) {
            a1.addLast(i);
        }
        for (int k = 1; k < 7; k++) {
            a1.removeLast();
        }

        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        System.out.println(a1.itemLength());
    }
}
