package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove () {
        AListNoResizing <Integer> expected = new AListNoResizing<>();
        BuggyAList <Integer> Buggy = new BuggyAList<>();

        expected.addLast(4);
        expected.addLast(5);
        expected.addLast(6);

        Buggy.addLast(4);
        Buggy.addLast(5);
        Buggy.addLast(6);

        assertEquals(expected.size(), Buggy.size());
        assertEquals(expected.removeLast(), Buggy.removeLast());
        assertEquals(expected.removeLast(), Buggy.removeLast());
        assertEquals(expected.removeLast(), Buggy.removeLast());

    }

    @Test
    public void testRemoveLast1000 () {
        AListNoResizing <Integer> expected = new AListNoResizing<>();
        BuggyAList <Integer> Buggy = new BuggyAList<>();

        for (int i = 0; i < 1000; i++) {
            expected.addLast(i);
            Buggy.addLast(i);
            assertEquals(expected.getLast(), Buggy.getLast());
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(expected.removeLast(), Buggy.removeLast());
        }

    }

    @Test
    public void randomizedTest () {
        AListNoResizing <Integer> expected = new AListNoResizing<>();
        BuggyAList <Integer> Buggy = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                expected.addLast(randVal);
                Buggy.addLast(randVal);

            } else if (operationNumber == 1 && expected.size() > 0) {
                // get last
                assertEquals(expected.getLast(), Buggy.getLast());


            } else if (operationNumber == 2 && expected.size() > 0) {
                // remove last
                assertEquals(expected.removeLast(), Buggy.removeLast());

            }
        }
    }




}
