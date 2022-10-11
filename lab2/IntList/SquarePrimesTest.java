package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimes1() {
        //assert
        IntList lst = IntList.of(2 ,3 ,4 ,5 ,6 ,7 ,23 ,47 ,97);

        //act
        boolean changed = IntListExercises.squarePrimes(lst);
        //assert
        assertEquals("4 -> 9 -> 4 -> 25 -> 6 -> 49 -> 529 -> 2209 -> 9409", lst.toString());
        assertTrue(changed);

    }

    @Test
    public void testSquarePrimes2() {
        IntList lst = IntList.of(4,8,17,2,1,23,4);

        //act
        boolean changed = IntListExercises.squarePrimes(lst);
        //assert
        assertEquals("4 -> 8 -> 289 -> 4 -> 1 -> 529 -> 4", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes3() {
        IntList lst = IntList.of(0,1,0,1);

        //act
        boolean changed = IntListExercises.squarePrimes(lst);
        //assert
        assertEquals("0 -> 1 -> 0 -> 1", lst.toString());
        assertFalse(changed);
    }
    @Test
    public void testSquarePrimes4() {
        IntList lst = IntList.of(0, 2, 3, 0);

        //act
        boolean changed = IntListExercises.squarePrimes(lst);
        //assert
        assertEquals("0 -> 4 -> 9 -> 0", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes5() {
        IntList lst = IntList.of(1,1,7);

        //act
        boolean changed = IntListExercises.squarePrimes(lst);
        //assert
        assertEquals("1 -> 1 -> 49", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes6() {
        IntList lst = IntList.of(7, 1, 1);

        //act
        boolean changed = IntListExercises.squarePrimes(lst);
        //assert
        assertEquals("49 -> 1 -> 1", lst.toString());
        assertTrue(changed);
    }

}
