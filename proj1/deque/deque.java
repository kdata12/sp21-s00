package deque;

public interface deque <T>{
     void addFirst(T item);

     void addLast(T item);

     boolean isEmpty();

     int size();

     void printDeque();

     T removeFirst();

     T removeLast();

     T get(int index);
}
