package deque;

public interface Deque <T>{
     void addFirst(T item);

     void addLast(T item);

     default boolean isEmpty() {
          return size() == 0;
     }

     int size();

     void printDeque();

     T removeFirst();

     boolean equals(Object o);

     T removeLast();

     T get(int index);
}
