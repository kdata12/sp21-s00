package deque;

import java.util.Iterator;

public class ArrayDeque <T> implements Deque<T>, Iterable<T>{
    private int size;
    private T[] items;
    private int frontPointer = -1;
    private int rearPointer = -1;

    /* linear array */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    private void resizeTraditional(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(items, 0, newArray, 0, size);
        items = newArray;
    }

    private void resizeMiddle(int capacity) {
        T[] newArray = (T[]) new Object[capacity];
        System.arraycopy(items, 0, newArray, 0, rearPointer + 1);
        System.arraycopy(items,frontPointer, newArray,newArray.length - (size - frontPointer), size - frontPointer);
        frontPointer = newArray.length - (size - frontPointer);
        items = newArray;

    }

    private void resize(int capacity){
        if (frontPointer == 0 && rearPointer == items.length - 1) {
            resizeTraditional(capacity);
        } else if (frontPointer == rearPointer + 1) {
            resizeMiddle(capacity);
        }
    }

    /* enqueue front of array */
    private boolean checkQueueFull() {
        return (frontPointer == 0 && rearPointer == items.length - 1) || (frontPointer == rearPointer + 1);
    }

    private boolean checkQueueEmpty() {
        return (frontPointer == -1 && rearPointer == -1);
    }

    /* start frontPointer and rearPointer at 0 when queue is empty
       assign <T> x to items[frontPointer].
     */
    private void queueEmptyMove(T x) {
        frontPointer = 0;
        rearPointer = 0;
        items[frontPointer] = x;
        size += 1;
    }

    /* front pointer is at position 0, move pointer to last position */
    private void frontPointerToLast(T x) {
        frontPointer = items.length - 1;
        items[frontPointer] = x;
        size += 1;
    }
    /* move front pointer backward */
    private void frontPointerDecrement(T x) {
        frontPointer -= 1;
        items[frontPointer] = x;
        size += 1;
    }
    /* Move rear pointer to first position */
    private void rearPointerToFirst(T x) {
        rearPointer = 0;
        items[rearPointer] = x;
        size += 1;
    }

    /* move rear pointer up by 1 */
    private void rearPointerIncrement(T x) {
        rearPointer += 1;
        items[rearPointer] = x;
        size += 1;
    }

    /* Enqueue front <T> x to the front position */
    @Override
    public void addFirst(T x) {
        if (checkQueueFull()) {
            resize(size * 2);
        }
        if (checkQueueEmpty()) {
            queueEmptyMove(x);
        } else if (frontPointer == 0) {  //check if position 0 is occupied
            frontPointerToLast(x);
        } else {
            frontPointerDecrement(x);
        }
    }

    /** Inserts X into the back of the list. */
    @Override
    public void addLast(T x) {
        if (checkQueueFull()) {
            resize(size * 2);
        }
        if (checkQueueEmpty()) {
            queueEmptyMove(x);
        } else if (rearPointer == items.length - 1) {
            rearPointerToFirst(x);
        } else {
            rearPointerIncrement(x);
        }
    }

    @Override
    public void printDeque() {
        int i = frontPointer;
        if (size == 0) {
            System.out.println("Array deque is empty.");
            return;
        }

        while (i != rearPointer){
            System.out.print(items[i] + " ");
            i = (i+1) % items.length;
        }
        System.out.print(items[rearPointer]);
        System.out.println();
    }

    /** Gets the ith item in the list (0 is the front). */
    @Override
    public T get(int i){
        if (size == 0) {
            return null;
        }

        if (i == 0) {
            return items[frontPointer];
        } else if (i == size - 1){
            return items[rearPointer];
        } else if (frontPointer == rearPointer){
            return items[frontPointer];
        } else {
            if (frontPointer == 0) {
                return items[i];
            } else if (i + frontPointer >= items.length) {
                int k = rearPointer - (size - i) + 1;
                return items[k];
            } else {
                return items[frontPointer + i];
            }
        }
    }

    /** Returns the number of items in the list. */
    @Override
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item.
    private void usageOptimizer() {
        double usageRatio = size / (double) items.length;
        double minimumUsage = 0.25;
        if (items.length >= 16 && usageRatio < minimumUsage){
            resize(items.length / 2);
        }
    }
     */
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

    private void removeRearItem() {
        if (frontPointer == -1 && rearPointer == -1) {
            isEmpty();
        } else if (frontPointer == rearPointer) {
            frontPointer = -1;
            rearPointer = -1;
        } else if (rearPointer == 0) {
            rearPointer = items.length - 1;
        } else {
            rearPointer -= 1;
        }

        size -= 1;
    }

    private void removeFrontItem() {
        if (frontPointer == -1 && rearPointer == -1) {
            isEmpty();
        } else if (frontPointer == rearPointer) {
            frontPointer = -1;
            rearPointer = -1;
        } else if (frontPointer == items.length - 1) {
            frontPointer = 0;
        } else {
            frontPointer += 1;
        }

        size -= 1;
    }

    @Override
    public T removeLast() {
        //usageOptimizer();

        T rearItem = items[rearPointer];
        items[rearPointer] = null;
        removeRearItem();

        return rearItem;
    }

    @Override
    public T removeFirst() {
        //usageOptimizer();
        T frontItem = items[frontPointer];
        items[frontPointer] = null;
        removeFrontItem();
        return frontItem;
    }

    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
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
}
