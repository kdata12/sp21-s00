package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void basicOperation() {
        ArrayDeque<String> list = new ArrayDeque<String>();
        list.addFirst("Kevin");
        list.addLast("Kevin's dog");
        list.addLast("Tyna");
        list.removeLast();
        list.addFirst("Tyna");
        assertEquals("Tyna", list.get(0));
        assertEquals("Kevin's dog", list.removeLast());
        assertEquals("Tyna", list.removeFirst());
        assertEquals("Kevin", list.removeFirst());
        assertEquals(0, list.size());

    }

    @Test
    public void addLastRemoveAll() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        int n = 100;
        for (int i = 0; i <= n; i++) {
            list.addLast(i);
        }
        assertEquals(100, list.printRear().intValue());
        for (int i = 0; i < 101; i++) {
            list.removeLast();
        }
        assertEquals(true, list.isEmpty());
    }

    @Test
    public void add100LastRemoveFirst() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        int n = 100;
        for (int i = 1; i <= n; i++) {
            list.addLast(i);
        }
        for (int i = 1; i <= n; i++) {
            list.removeFirst();
        }
        assertEquals(true, list.isEmpty());
    }
    @Test
    public void resizeMiddle() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        int n = 10;
        list.addLast(3);
        list.addLast(2);
        list.addLast(1);
        for (int i = 4; i <= n; i++) {
            list.addFirst(i);
        }
        for (int i = 1; i <= n; i++) {
            list.removeFirst();
        }
        assertEquals(true, list.isEmpty());
    }

    @Test
    public void multipleAddRemove() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        int N = 100_000;
        for (int i = 0; i < N; i ++) {
            int operationNumber = StdRandom.uniform(1, 5);
            if (operationNumber == 1) {
                list.addLast(i);
            } else if (operationNumber == 2) {
                list.addFirst(i);
            } else if (operationNumber == 3 && list.size() != 0) {
                list.removeLast();
            } else if (operationNumber == 4 && !list.isEmpty()) {
                list.removeFirst();
            }
        }

    }

    @Test
    public void getFromAddLast() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        int n = 128;
        for (int i = 0; i < n; i++) {
            list.addLast(i);
        }
        for (int i = 0; i < n; i++) {
            assertEquals(i, (int) list.get(i));
        }
    }

    @Test
    public void getFromAddFront() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        list.addLast(0);
        list.addLast(1);
        list.addLast(2);
        for(int i = 3; i < 50; i++) {
            list.addFirst(i);
        }
        // get first
        assertEquals(49, (int) list.get(0));
        // get last items
        assertEquals(2, (int) list.get(list.size()-1));
        assertEquals(1, (int) list.get(48));
        assertEquals(0, (int) list.get(47));
        assertEquals(3, (int) list.get(46));
        assertEquals(4, (int) list.get(45));
        // get randoms
        assertEquals(40, (int) list.get(9));
        assertEquals(37, (int) list.get(12));
        assertEquals(29, (int) list.get(20));
        assertEquals(19, (int) list.get(30));
        assertEquals(20, (int) list.get(29));
        assertEquals(30, (int) list.get(19));
        assertEquals(34, (int) list.get(15));
        assertEquals(25, (int) list.get(24));
    }
    @Test
    public void emptyListGetNull() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        assertEquals(null,list.get(0));
    }

    @Test
    public void multipleTypes() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        ArrayDeque<Boolean> list2 = new ArrayDeque<Boolean>();
        ArrayDeque<String> list3 = new ArrayDeque<String>();

        list.addLast(3);
        list2.addLast(true);
        list3.addLast("dog");
    }

    @Test
    public void sizeTest() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        int n = 100_000;
        for (int i = 0; i < n; i++) {
            list.addLast(i);
        }
        assertEquals(100_000, list.size());
    }

    @Test
    public void bigOperation() {
        ArrayDeque<Integer> list = new ArrayDeque<Integer>();
        int n = 100_00;

        for (int i = 0; i < n; i++) {
            int opNum = StdRandom.uniform(1,6);
            if (opNum == 1){
                list.addLast(i);
            } else if (opNum == 2) {
                list.addFirst(i);
            } else if (opNum == 3 && !list.isEmpty()){
                int k = list.printFront();
                assertEquals(k , (int) list.removeFirst());
            } else if (opNum == 4 && !list.isEmpty()) {
                int k = list.printRear();
                assertEquals(k , (int) list.removeLast());
            } else if (opNum == 5 && list.isEmpty()) {
                assertEquals(null, list.get(2));
            }
        }
    }

    @Test
    public void arrayEquals1() {
        ArrayDeque<Integer> l1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> l2 = new ArrayDeque<Integer>();
        for(int i = 0; i < 100; i++) {
            l1.addLast(i);
            l2.addLast(i);
        }
        assertEquals(true, l1.equals(l2));
    }

    @Test
    public void arrayEquals2() {
        ArrayDeque<Integer> l1 = new ArrayDeque<Integer>();
        ArrayDeque<Integer> l2 = new ArrayDeque<Integer>();
        for(int i = 0; i < 10; i++) {
            l1.addLast(i);
        }
        l2.addLast(1);
        assertEquals(false, l1.equals(l2));
    }
}
