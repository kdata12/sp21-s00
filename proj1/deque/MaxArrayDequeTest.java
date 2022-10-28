package deque;

import org.junit.Test;
import java.util.Comparator;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void twoArrayTest() {
        Comparator<Integer> comp = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return 1;
                } else if (o1 < o2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        MaxArrayDeque l1 = new MaxArrayDeque(comp);

        for(int i = 0; i < 1000; i++) {
            l1.addLast(i);
        }

        assertEquals(999, l1.max());
        assertEquals(999, l1.max(comp));
    }

}
